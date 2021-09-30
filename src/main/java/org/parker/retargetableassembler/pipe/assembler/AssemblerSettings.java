package org.parker.retargetableassembler.pipe.assembler;

import java.io.File;
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
}
