package org.parker.retargetableassembler.pipe.preprocessor.directives;

import org.parker.retargetableassembler.pipe.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.util.iterators.IteratorStack;

public interface PreProcessorDirective {

    void init(IteratorStack<LexSymbol> iterator, PreProcessor pp);

}
