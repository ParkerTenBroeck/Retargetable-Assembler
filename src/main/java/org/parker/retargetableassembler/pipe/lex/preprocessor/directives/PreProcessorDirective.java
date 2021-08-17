package org.parker.retargetableassembler.pipe.lex.preprocessor.directives;

import org.parker.retargetableassembler.pipe.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.lex.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.lex.preprocessor.util.scanners.IteratorStack;

public interface PreProcessorDirective {

    void init(IteratorStack<LexSymbol> scanner, PreProcessor pp) ;

}
