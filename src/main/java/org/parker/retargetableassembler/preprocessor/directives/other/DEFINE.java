package org.parker.retargetableassembler.preprocessor.directives.other;

import org.parker.retargetableassembler.preprocessor.PreProcessor;
import org.parker.retargetableassembler.preprocessor.directives.PreProcessorDirective;
import org.parker.retargetableassembler.preprocessor.lex.jflex.LexSymbol;

import java.util.HashMap;
import java.util.Map;

public final class DEFINE implements PreProcessorDirective {

    private Map<String, LexSymbol> NameTokenMap = new HashMap();

    @Override
    public void init(LexSymbol root, PreProcessor pp) {
        LexSymbol name = pp.getIteratorStack().next();
        LexSymbol sym = pp.getIteratorStack().next();
    }
}
