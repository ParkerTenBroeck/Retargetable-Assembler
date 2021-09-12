package org.parker.retargetableassembler.pipe;

import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.util.AssemblerLogLevel;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerReport implements Report{

    private static final Logger LOGGER = Logger.getLogger("PipeLine_Report");


    @Override
    public void report(String message, Exception e, LexSymbol parent, ReportLevel level) {
        Level l;
        String levelID;
        switch (level){
            case message:
                l = AssemblerLogLevel.ASSEMBLER_MESSAGE;
                levelID = "Message";
                break;
            case warning:
                l = AssemblerLogLevel.ASSEMBLER_WARNING;
                levelID = "Warning";
                break;
            case error:
                l = AssemblerLogLevel.ASSEMBLER_ERROR;
                levelID = "Error";
                break;
            case codeError: default:
                l = Level.SEVERE;
                levelID = "Severe Error";
                break;
        }
        message = levelID + ": " + message;
        if(parent != null){
            message = parent.getFile().getPath() + ":" + parent.getLine() + ": " + message;
            parent = parent.getParent();
            while(parent != null){
                message += "\n" + parent.getFile().getPath() + ":" + parent.getLine() + ": ... from " +
                        LexSymbol.terminalNames[parent.sym] + " " + parent.value + " defined here";
                parent = parent.getParent();
            }
        }

        System.out.println(message);
        if(e!=null)e.printStackTrace();
    }
}
