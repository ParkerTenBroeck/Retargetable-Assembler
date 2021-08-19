package org.parker.retargetableassembler.pipe.preprocessor.directives.message;

import org.parker.retargetableassembler.pipe.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.preprocessor.directives.PreProcessorDirective;
import org.parker.retargetableassembler.pipe.util.iterators.IteratorStack;
import org.parker.retargetableassembler.util.AssemblerLogLevel;

import java.util.logging.Logger;

public final class EMSG implements PreProcessorDirective {

    private static final Logger LOGGER = Logger.getLogger("PreProcessor");

    @Override
    public void init(IteratorStack<LexSymbol> iterator, PreProcessor pp) {
        LOGGER.log(AssemblerLogLevel.ASSEMBLER_ERROR, "");
    }
}
