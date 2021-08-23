package org.parker.retargetableassembler.pipe;

import org.parker.retargetableassembler.util.AssemblerLogLevel;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerReport implements Report{

    private static final Logger LOGGER = Logger.getLogger("PipeLine_Report");

    @Override
    public void reportCodeError(String message){
        LOGGER.log(Level.SEVERE, message);
    }

    @Override
    public void reportCodeError(Exception e){
        LOGGER.log(Level.SEVERE, "", e);
    }

    @Override
    public void reportCodeError(String message, Exception e){
        LOGGER.log(Level.SEVERE, message, e);
    }

    @Override
    public void reportError(String message){
        LOGGER.log(AssemblerLogLevel.ASSEMBLER_ERROR, message);
    }

    @Override
    public void reportWarning(String message){
        LOGGER.log(AssemblerLogLevel.ASSEMBLER_WARNING, message);
    }

    @Override
    public void reportMessage(String message){
        LOGGER.log(AssemblerLogLevel.ASSEMBLER_MESSAGE, message);
    }
}
