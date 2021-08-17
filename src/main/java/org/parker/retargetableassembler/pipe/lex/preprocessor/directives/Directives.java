package org.parker.retargetableassembler.pipe.lex.preprocessor.directives;

import org.parker.retargetableassembler.pipe.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.lex.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.lex.preprocessor.directives.control.IF;
import org.parker.retargetableassembler.pipe.lex.preprocessor.directives.other.Include;
import org.parker.retargetableassembler.pipe.lex.preprocessor.util.scanners.IteratorStack;

import java.util.HashMap;
import java.util.Map;

public class Directives {
    public Map<String, PreProcessorDirective> directiveAtlasMap = new HashMap<>();

    public Directives(){
        directiveAtlasMap.put("if", new IF());
        directiveAtlasMap.put("else", new DirectiveError("found else without if"));
        directiveAtlasMap.put("elseif", new DirectiveError("found elseif without if"));
        directiveAtlasMap.put("endif", new DirectiveError("found endif without if"));
        directiveAtlasMap.put("include", new Include());
    }

    public boolean hasDirective(String atlas){
        return directiveAtlasMap.containsKey(atlas);
    }

    public void handleDirective(String atlas, IteratorStack<LexSymbol> scannerStack, PreProcessor preProcessor) {
        directiveAtlasMap.get(atlas).init(scannerStack, preProcessor);
    }
}
