package org.parker.retargetableassembler.pipe.preprocessor.directives;

import org.parker.retargetableassembler.pipe.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.preprocessor.directives.control.IF;
import org.parker.retargetableassembler.pipe.preprocessor.directives.control.REP;
import org.parker.retargetableassembler.pipe.preprocessor.directives.other.INCLUDE;
import org.parker.retargetableassembler.pipe.util.iterators.IteratorStack;

import java.util.HashMap;
import java.util.Map;

public class Directives {
    public Map<String, PreProcessorDirective> directiveAtlasMap = new HashMap<>();

    public Directives(){
        directiveAtlasMap.put("if", new IF());
        directiveAtlasMap.put("else", new DirectiveStranglerError("found else without if"));
        directiveAtlasMap.put("elseif", new DirectiveStranglerError("found elseif without if"));
        directiveAtlasMap.put("endif", new DirectiveStranglerError("found endif without if"));

        directiveAtlasMap.put("rep", new REP());
        directiveAtlasMap.put("exitrep", new DirectiveStranglerError("found exitrep without rep"));
        directiveAtlasMap.put("endrep", new DirectiveStranglerError("found endrep without rep"));

        directiveAtlasMap.put("macro", new DirectiveStranglerError("macro is not implemented yet"));
        directiveAtlasMap.put("endm", new DirectiveStranglerError("found endmacro without macro"));

        directiveAtlasMap.put("emsg", new DirectiveStranglerError("emsg is not implemented yet"));
        directiveAtlasMap.put("wmsg", new DirectiveStranglerError("wmsg is not implemented yet"));
        directiveAtlasMap.put("mmsg", new DirectiveStranglerError("mmsg is not implemented yet"));

        directiveAtlasMap.put("define", new DirectiveStranglerError("define is not implemented yet"));
        directiveAtlasMap.put("asg", new DirectiveStranglerError("asg is not implemented yet"));
        directiveAtlasMap.put("unasg", new DirectiveStranglerError("unasg is not implemented yet"));
        directiveAtlasMap.put("undefine", new DirectiveStranglerError("undefine is not implemented yet"));

        directiveAtlasMap.put("include", new INCLUDE());
    }

    public boolean hasDirective(String atlas){
        return directiveAtlasMap.containsKey(atlas) && !(directiveAtlasMap.get(atlas) instanceof DirectiveStranglerError);
    }

    public void handleDirective(String atlas, IteratorStack<LexSymbol> scannerStack, PreProcessor preProcessor) {
        directiveAtlasMap.get(atlas).init(scannerStack, preProcessor);
    }

    public boolean hasStranglerDirective(String atlas) {
        return directiveAtlasMap.containsKey(atlas) && (directiveAtlasMap.get(atlas) instanceof DirectiveStranglerError);
    }

    public void handleStranglerDirective(String atlas, IteratorStack<LexSymbol> scannerStack, PreProcessor preProcessor){
        directiveAtlasMap.get(atlas).init(scannerStack, preProcessor);
    }

}
