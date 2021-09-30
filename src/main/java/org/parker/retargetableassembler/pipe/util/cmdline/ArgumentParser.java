package org.parker.retargetableassembler.pipe.util.cmdline;

import org.parker.retargetableassembler.pipe.Report;
import org.parker.retargetableassembler.pipe.util.iterators.StringCharacterIterator;

import java.io.File;
import java.util.*;

public class ArgumentParser <T>{

    private Map<Character, SettingHandler<T>> singleDashSettings;
    private Map<String, SettingHandler<T>> doubleDashSettings;

    public ArgumentParser(){
        singleDashSettings = new HashMap<>();
        doubleDashSettings = new HashMap<>();
    }

    public void putSingleDashSettingHandler(char id, SettingHandler<T> setting){
        singleDashSettings.put(id, setting);
    }
    public void removeSingleDashSettingHandler(char id){
        singleDashSettings.remove(id);
    }

    public void putDoubleDashSettingHandler(String id, SettingHandler<T> setting){
        doubleDashSettings.put(id, setting);
    }
    public void removeDoubleDashSettingHandler(String id){
        doubleDashSettings.remove(id);
    }

    public ArgumentParser(Map<Character, SettingHandler<T>> singleDashSettings, Map<String, SettingHandler<T>> doubleDashSettings){
        this.singleDashSettings = singleDashSettings;
        this.doubleDashSettings = doubleDashSettings;
    }

    public static File[] generateFilesFromString(String input, Report report){
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

    public synchronized T generateSettingsFromArguments(T settings, String args, Report report){
        Set<Character> usedSingleDashSetting = new HashSet<>();
        Set<String> usedDoubleDashSetting = new HashSet<>();

        for(Map.Entry<Character, SettingHandler<T>> s: singleDashSettings.entrySet()){
            s.getValue().setDefaultValue(settings, report);
        }
        for(Map.Entry<String, SettingHandler<T>> s: doubleDashSettings.entrySet()){
            s.getValue().setDefaultValue(settings, report);
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
                            ds.handleSetting(settings, sb.toString(), report);
                            usedDoubleDashSetting.add(setting);
                            sb = null;
                        }else{
                            ds.handleSetting(settings, null, report);
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
                                ss.handleSetting(settings, sb.toString(), report);
                                usedSingleDashSetting.add(c);
                                sb = null;
                            }else{
                                ss.handleSetting(settings, null, report);
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

        for(Map.Entry<Character, SettingHandler<T>> s: singleDashSettings.entrySet()){
            if(usedSingleDashSetting.contains(s.getKey())){
                if(!s.getValue().verifySettings(settings, report)) return null;
            }else{
                if(s.getValue().required){
                    report.reportError("Single dash argument: '" + s.getKey() + "' never used but is required");
                    return null;
                }
            }
        }
        for(Map.Entry<String, SettingHandler<T>> s: doubleDashSettings.entrySet()){
            if(usedDoubleDashSetting.contains(s.getKey())){
                if(!s.getValue().verifySettings(settings, report)) return null;
            }else{
                if(s.getValue().required){
                    report.reportError("Double dash argument: '" + s.getKey() + "' never used but is required");
                    return null;
                }
            }
        }

        return settings;
    }

    public abstract static class SettingHandler<T> {

        public final boolean required;
        public final boolean hasArguments;
        public final boolean allowMultiple;

        public SettingHandler(boolean required, boolean hasArguments, boolean allowMultiple){
            this.required = required;
            this.hasArguments = hasArguments;
            this.allowMultiple = allowMultiple;
        }

        public abstract void handleSetting(T as, String arguments, Report report);

        public void setDefaultValue(T as, Report report){

        }

        public boolean verifySettings(T as, Report report){
            return true;
        }
    }
}
