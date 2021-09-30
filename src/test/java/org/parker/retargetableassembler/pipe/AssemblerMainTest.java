package org.parker.retargetableassembler.pipe;

import org.junit.Assert;
import org.junit.Test;
import org.parker.retargetableassembler.pipe.assembler.AssemblerSettings;
import org.parker.retargetableassembler.pipe.util.cmdline.ArgumentParser;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssemblerMainTest {

    @Test
    public void testArguments(){
        SaveReport report = new SaveReport();

        ArgumentParser<AssemblerSettings> ap =
                new ArgumentParser<>(AssemblerMain.singleDashSettings, AssemblerMain.doubleDashSettings);

        for(Map.Entry<String, AssemblerSettings> e: argumentsToSettingsMap.entrySet()){
            AssemblerSettings as = ap.generateSettingsFromArguments(new AssemblerSettings(), e.getKey(), report);
            Assert.assertEquals(e.getValue(), as);
        }

        report.flushToReport(new LoggerReport());
    }

    static Map<String, AssemblerSettings> argumentsToSettingsMap = new HashMap<>();

    static{
        AssemblerSettings as = new AssemblerSettings();
        as.inputFiles = new File[]{new File("/test.asm"), new File("/test space/wow.asm")};
        as.outputFiles = new File[]{new File("/test.o"), new File("/test space/wow.o")};
        as.preInclude = new File[]{new File("preincfile.asm")};
        as.includeDebugInfo = true;
        as.noPreProcessor = true;
        argumentsToSettingsMap.put("-i /test.asm \"/test space/wow.asm\"-o /test.o \"/test space/wow.o\" -ga --preinc preincfile.asm",
                as);

        as = new AssemblerSettings();
        as.inputFiles = new File[]{new File("/test.asm"), new File("/test space/wow.asm")};
        as.outputFiles = new File[]{new File("/test_asm.o"), new File("/test space/wow_asm.o")};
        as.preInclude = new File[]{new File("preincfile.asm")};
        as.includeDebugInfo = true;
        as.noPreProcessor = true;
        argumentsToSettingsMap.put("-i /test.asm \"/test space/wow.asm\"-ga --preinc preincfile.asm",
                as);

        //as = new AssemblerSettings();
        //argumentsToSettingsMap.put("-i /test.asm \"/test space/wow.asm\"-o /test.o \"/test space/wow.o\" -ga --preinc preincfile.asm",
        //        as);
    }
}