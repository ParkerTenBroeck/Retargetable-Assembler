package org.parker.retargetableassembler.pipe.preprocessor.directives;

import org.parker.retargetableassembler.pipe.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.util.iterators.IteratorStack;
import org.parker.retargetableassembler.util.AssemblerLogLevel;

import java.util.logging.Logger;

public class DirectiveStranglerError implements PreProcessorDirective{

    private final static Logger LOGGER = Logger.getLogger("PreProcessor");

    private final String message;

    public DirectiveStranglerError(String message){
        this.message = message;
    }

    @Override
    public void init(IteratorStack<LexSymbol> iterator, PreProcessor pp) {
        LOGGER.log(AssemblerLogLevel.SEVERE, message);
    }
}
