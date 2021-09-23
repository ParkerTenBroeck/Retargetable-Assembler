package org.parker.retargetableassembler.pipe.preprocessor.directives;

import org.parker.retargetableassembler.pipe.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.LexSymbol;

public interface PreProcessorDirective {

    void init(LexSymbol root, PreProcessor pp);

}
