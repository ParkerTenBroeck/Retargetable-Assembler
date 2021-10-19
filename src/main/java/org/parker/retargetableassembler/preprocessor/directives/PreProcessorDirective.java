package org.parker.retargetableassembler.preprocessor.directives;

import org.parker.retargetableassembler.preprocessor.PreProcessor;
import org.parker.retargetableassembler.preprocessor.lex.jflex.LexSymbol;

public interface PreProcessorDirective {

    void init(LexSymbol root, PreProcessor pp);

}
