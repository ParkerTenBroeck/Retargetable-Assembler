package org.parker.retargetableassembler.pipe;

import org.parker.retargetableassembler.pipe.assembler.AssemblerSettings;
import org.parker.retargetableassembler.pipe.util.iterators.StringCharacterIterator;

import java.io.File;
import java.util.*;

public class AssemblerMain {

    public static void main(String... args){
        String argsSingle = String.join(" ", args);
        boolean b = assemble(argsSingle, new LoggerReport());
        System.exit(b ? 0 : -1);
    }

    public static boolean assemble(String args, Report report){
        if(args.equals("")){
            report.reportMessage("help message");
            return false;
        }
        AssemblerSettings as = generateSettingsFromArguments(args, report);
        if(as == null){
            report.reportError("Failed to parse arguments aborting assembly");
            return false;
        }

        //actually calling assembler

        return true;
    }

    public static Map<Character, SettingHandler> singleDashSettings = new HashMap<>();
    public static Map<String, SettingHandler> doubleDashSettings = new HashMap<>();

    static{
        singleDashSettings.put('g', new SettingHandler(false, false) {
            @Override
            public void handleSetting(AssemblerSettings as, String arguments, Report report) {
                as.includeDebugInfo = true;
            }
        });

        singleDashSettings.put('a', new SettingHandler(false, false) {
            @Override
            public void handleSetting(AssemblerSettings as, String arguments, Report report) {
                as.noPreProcessor = true;
            }
        });

        singleDashSettings.put('o', new SettingHandler(true, false) {
            @Override
            public void handleSetting(AssemblerSettings as, String arguments, Report report) {
                as.outputFiles = generateFilesFromString(arguments, report);
            }
        });
        singleDashSettings.put('i', new SettingHandler(true, false) {
            @Override
            public void handleSetting(AssemblerSettings as, String arguments, Report report) {
                as.inputFiles = generateFilesFromString(arguments, report);
            }
        });

        doubleDashSettings.put("limit-stack", new SettingHandler(true, false) {
            @Override
            public void handleSetting(AssemblerSettings as, String arguments, Report report) {
                try {
                    as.maxStack = Integer.parseInt(arguments);
                }catch (Exception ignore){
                    report.reportError("Invalid Integer given for limit-stack");
                }
            }
        });

        doubleDashSettings.put("preinc", new SettingHandler(true, false) {
            @Override
            public void handleSetting(AssemblerSettings as, String arguments, Report report) {
                as.preInclude = generateFilesFromString(arguments, report);
            }
        });
    }

    private static File[] generateFilesFromString(String input, Report report){
        StringCharacterIterator iterator = new StringCharacterIterator(input);
        ArrayList<File> files = new ArrayList<>();

        while(iterator.hasNext()){
            StringBuilder path = new StringBuilder();
            if(iterator.peek_ahead() == ' '){
                iterator.next();
                continue;
            }else if(iterator.peek_ahead() == '"'){
                iterator.next();
                while(iterator.peek_ahead() != '"'){
                    path.append(iterator.next());
                    if(!iterator.hasNext()){
                        report.reportError("Unterminated quote in filepath");
                        return null;
                    }
                }
                iterator.next();
            }else{
                while(iterator.hasNext() && iterator.peek_ahead() != ' '){
                    path.append(iterator.next());
                }
            }
            files.add(new File(path.toString()));
        }
        return files.toArray(new File[0]);
    }

    private static AssemblerSettings generateSettingsFromArguments(String args, Report report){
        AssemblerSettings as = new AssemblerSettings();

        Set<Character> usedSingleDashSetting = new HashSet<>();
        Set<String> usedDoubleDashSetting = new HashSet<>();

        for(Map.Entry<Character, SettingHandler> s: singleDashSettings.entrySet()){
            s.getValue().setDefaultValue(as, report);
        }
        for(Map.Entry<String, SettingHandler> s: doubleDashSettings.entrySet()){
            s.getValue().setDefaultValue(as, report);
        }

        StringCharacterIterator iterator = new StringCharacterIterator(args);

        while(iterator.hasNext()){
            if(iterator.next() == '-'){
                boolean doubleDash = iterator.peek_ahead() == '-';
                if(doubleDash){
                    iterator.next();

                    StringBuilder sb = new StringBuilder();
                    while(iterator.hasNext() && iterator.peek_ahead() != ' ') sb.append(iterator.next()); //generate setting
                    String setting = sb.toString();

                    sb = new StringBuilder();
                    while(iterator.hasNext() && iterator.peek_ahead() != '-') sb.append(iterator.next()); //generate setting

                    if(doubleDashSettings.containsKey(setting)){
                        SettingHandler ds = doubleDashSettings.get(setting);
                        if(!ds.allowMultiple && usedDoubleDashSetting.contains(setting)){
                            report.reportError("Double dash setting: '" + setting +"' cannot be used more than once");
                            return null;
                        }
                        if(ds.hasArguments) {
                            ds.handleSetting(as, sb.toString(), report);
                            usedDoubleDashSetting.add(setting);
                            sb = null;
                        }else{
                            ds.handleSetting(as, null, report);
                            usedDoubleDashSetting.add(setting);
                        }
                    }else{
                        report.reportError("Unrecognized double dash setting: '" + setting + "'");
                    }

                    if(sb != null){
                        if(sb.toString().trim().equals(""))
                            report.reportWarning("Arguments found for double dash setting but were not used: '" + sb.toString() + "'");
                    }

                }else{
                    ArrayList<Character> characterArrayList = new ArrayList<>();
                    while(iterator.hasNext() && iterator.peek_ahead() != ' '){
                        characterArrayList.add(iterator.next());
                    }
                    StringBuilder sb = new StringBuilder();
                    while(iterator.hasNext() && iterator.peek_ahead() != '-') sb.append(iterator.next()); //generate arguments

                    for(Character  c: characterArrayList){
                        if(singleDashSettings.containsKey(c)){
                            SettingHandler ss = singleDashSettings.get(c);
                            if(!ss.allowMultiple && usedSingleDashSetting.contains(c)){
                                report.reportError("Single dash setting: '" + c +"' cannot be used more than once");
                                return null;
                            }
                            if(ss.hasArguments){
                                if(sb == null){
                                    report.reportError("Cannot have multiple single dash settings that have arguments in a bunch");
                                    return null;
                                }
                                ss.handleSetting(as, sb.toString(), report);
                                usedSingleDashSetting.add(c);
                                sb = null;
                            }else{
                                ss.handleSetting(as, null, report);
                                usedSingleDashSetting.add(c);
                            }
                        }else{
                            report.reportError("Unrecognized single dash setting: '" + c + "'");
                            return null;
                        }
                    }
                    if(sb != null){
                        if(!sb.toString().trim().equals(""))
                            report.reportWarning("Arguments found for single dash setting but were not used: '" + sb.toString() + "'");
                    }
                }
            }else{
                report.reportError("Invalid arguments: " + iterator.peek_behind());
                return null;
            }
        }

        for(Map.Entry<Character, SettingHandler> s: singleDashSettings.entrySet()){
            if(!s.getValue().verifySettings(as, report)){
                return null;
            }
        }
        for(Map.Entry<String, SettingHandler> s: doubleDashSettings.entrySet()){
            if(!s.getValue().verifySettings(as, report)){
                return null;
            }
        }

        return as;
    }

    public abstract static class SettingHandler {
        public final boolean hasArguments;
        public final boolean allowMultiple;

        public SettingHandler(boolean hasArguments, boolean allowMultiple){
            this.hasArguments = hasArguments;
            this.allowMultiple = allowMultiple;
        }

        public abstract void handleSetting(AssemblerSettings as, String arguments, Report report);

        public void setDefaultValue(AssemblerSettings as, Report report){

        }

        public boolean verifySettings(AssemblerSettings as, Report report){
            return true;
        }
    }

}
