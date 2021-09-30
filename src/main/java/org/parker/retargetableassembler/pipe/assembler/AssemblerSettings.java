package org.parker.retargetableassembler.pipe.assembler;

import org.parker.retargetableassembler.pipe.AssemblerMain;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AssemblerSettings {
    public File[] inputFiles;
    public File[] outputFiles;

    public String[] includePaths;
    public File[] preInclude;
    public String[] prePramga;

    public Map<String, Object> extraSettings = new HashMap<>();

    //log/report
    public boolean showOutput = true;
    public boolean enableWarnings = true;

    //debug
    public boolean includeDebugInfo = false;
    public boolean includeLineInfo = false;

    //pre processor
    public boolean preProcessorOnly = false;
    public boolean noPreProcessor = false;

    //limits
    public int maxStack = -1;
    public int maxLines = -1;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AssemblerSettings){
            AssemblerSettings as = (AssemblerSettings) obj;
            boolean equal = true;

            equal &= maxLines == as.maxLines;
            equal &= maxStack == as.maxStack;

            equal &= noPreProcessor == as.noPreProcessor;
            equal &= preProcessorOnly == as.preProcessorOnly;

            equal &= includeLineInfo == as.includeLineInfo;
            equal &= includeDebugInfo == as.includeDebugInfo;

            equal &= enableWarnings == as.enableWarnings;
            equal &= showOutput == as.showOutput;

            equal &= extraSettings.equals(as.extraSettings);

            equal &= Arrays.equals(inputFiles, as.inputFiles);
            equal &= Arrays.equals(outputFiles, as.outputFiles);

            equal &= Arrays.equals(includePaths, as.includePaths);
            equal &= Arrays.equals(preInclude, as.preInclude);
            equal &= Arrays.equals(prePramga, as.prePramga);

            return equal;
        }
        return false;
    }
}
