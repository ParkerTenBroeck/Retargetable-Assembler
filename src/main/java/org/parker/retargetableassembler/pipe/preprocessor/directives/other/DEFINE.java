package org.parker.retargetableassembler.pipe.preprocessor.directives.other;

import org.parker.retargetableassembler.pipe.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.preprocessor.directives.PreProcessorDirective;
import org.parker.retargetableassembler.pipe.util.iterators.IteratorStack;

import java.util.HashMap;
import java.util.Map;

public final class DEFINE implements PreProcessorDirective {

    private Map<String, LexSymbol> NameTokenMap = new HashMap();

    @Override
    public void init(IteratorStack<LexSymbol> iterator, PreProcessor pp) {
        LexSymbol name = iterator.next();
        LexSymbol sym = iterator.next();
    }
}
