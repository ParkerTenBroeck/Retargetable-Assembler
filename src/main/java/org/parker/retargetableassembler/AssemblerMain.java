package org.parker.retargetableassembler;

import org.parker.retargetableassembler.assembler.AssemblerSettings;
import org.parker.retargetableassembler.util.cmdline.ArgumentParser;


import java.io.File;
import java.nio.charset.StandardCharsets;
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
        AssemblerSettings as = new AssemblerSettings();
        as = new ArgumentParser<>(singleDashSettings, doubleDashSettings)
                .generateSettingsFromArguments(as, args, report);
        if(as == null){
            report.reportError("Failed to parse arguments aborting assembly");
            return false;
        }

        //actually calling assembler

        return true;
    }

    public static Map<Character, ArgumentParser.SettingHandler<AssemblerSettings>> singleDashSettings = new HashMap<>();
    public static Map<String, ArgumentParser.SettingHandler<AssemblerSettings>> doubleDashSettings = new HashMap<>();

    static{
        singleDashSettings.put('g', new ArgumentParser.SettingHandler<AssemblerSettings>
                (false,false, false) {
            @Override
            public void handleSetting(AssemblerSettings as, String arguments, Report report) {
                as.includeDebugInfo = true;
            }
        });

        singleDashSettings.put('a', new ArgumentParser.SettingHandler<AssemblerSettings>
                (false,false, false) {
            @Override
            public void handleSetting(AssemblerSettings as, String arguments, Report report) {
                as.noPreProcessor = true;
            }
        });

        singleDashSettings.put('o', new ArgumentParser.SettingHandler<AssemblerSettings>
                (false, true, false) {
            @Override
            public void handleSetting(AssemblerSettings as, String arguments, Report report) {
                as.outputFiles = ArgumentParser.generateFilesFromString(arguments, report);
            }
        });
        singleDashSettings.put('i', new ArgumentParser.SettingHandler<AssemblerSettings>
                (true, true, false) {
            @Override
            public void handleSetting(AssemblerSettings as, String arguments, Report report) {
                as.inputFiles = ArgumentParser.generateFilesFromString(arguments, report);
            }

            @Override
            public boolean verifySettings(AssemblerSettings as, Report report) {
                if(as.outputFiles == null){
                    as.outputFiles = new File[as.inputFiles.length];
                    for(int i = 0; i < as.outputFiles.length; i ++){
                        String s = as.inputFiles[i].getPath();
                        for(int j = s.length() - 1; j >= 0; j --){
                            if(s.charAt(j) == '.'){
                                byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
                                bytes[j] = '_';
                                s = new String(bytes);
                                break;
                            }
                            if(s.charAt(j) == '/' || s.charAt(j) == '\\'){
                                break;
                            }
                        }
                        s += ".o";
                        as.outputFiles[i] = new File(s);
                    }
                    return true;
                }else if(as.inputFiles.length == as.outputFiles.length){
                    return true;
                }
                report.reportError("Number of output files do not match number of input files");
                return false;
            }
        });

        doubleDashSettings.put("limit-stack", new ArgumentParser.SettingHandler<AssemblerSettings>
                (false, true, false) {
            @Override
            public void handleSetting(AssemblerSettings as, String arguments, Report report) {
                try {
                    as.maxStack = Integer.parseInt(arguments);
                }catch (Exception ignore){
                    report.reportError("Invalid Integer given for limit-stack");
                }
            }
        });

        doubleDashSettings.put("preinc", new ArgumentParser.SettingHandler<AssemblerSettings>
                (false, true, false) {
            @Override
            public void handleSetting(AssemblerSettings as, String arguments, Report report) {
                as.preInclude = ArgumentParser.generateFilesFromString(arguments, report);
            }
        });
    }

}
