package org.parker.retargetableassembler.pipe.preprocessor.util;

import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.LexSymbol;

public interface PreProcessorOutputFilter {

    /**
     *
     * @param symbol the input symbol
     * @return the return can either be the given symbol or null to discard the current symbol from output and request another
     */
    LexSymbol filterOutput(LexSymbol symbol);

}
