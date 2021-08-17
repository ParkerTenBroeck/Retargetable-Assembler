package org.parker.retargetableassembler.pipe.lex.preprocessor.directives;

import org.parker.retargetableassembler.pipe.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.lex.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.lex.preprocessor.util.scanners.IteratorStack;
import org.parker.retargetableassembler.util.AssemblerLogLevel;

import java.util.logging.Logger;

public class DirectiveError implements PreProcessorDirective{

    private final static Logger LOGGER = Logger.getLogger("PreProcessor");

    private final String message;

    public DirectiveError(String message){
        this.message = message;
    }

    @Override
    public void init(IteratorStack<LexSymbol> scanner, PreProcessor pp) {
        LOGGER.log(AssemblerLogLevel.SEVERE, message);
    }
}
