package org.parker.retargetableassembler.preprocessor.directives;

import org.parker.retargetableassembler.preprocessor.PreProcessor;
import org.parker.retargetableassembler.preprocessor.directives.control.IF;
import org.parker.retargetableassembler.preprocessor.directives.control.REP;
import org.parker.retargetableassembler.preprocessor.directives.message.MSG;
import org.parker.retargetableassembler.preprocessor.directives.other.INCLUDE;
import org.parker.retargetableassembler.preprocessor.directives.other.MACRO;
import org.parker.retargetableassembler.preprocessor.lex.jflex.LexSymbol;

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

        directiveAtlasMap.put("macro", new MACRO.DEFINITION());
        directiveAtlasMap.put("unmacro", new MACRO.UNDEFINITION());
        directiveAtlasMap.put("endmacro", new DirectiveStranglerError("found endmacro without macro"));

        directiveAtlasMap.put("emsg", new MSG.EMSG());
        directiveAtlasMap.put("wmsg", new MSG.WMSG());
        directiveAtlasMap.put("mmsg", new MSG.MMSG());
        directiveAtlasMap.put("emsge", new MSG.EMSGE());
        directiveAtlasMap.put("wmsge", new MSG.WMSGE());
        directiveAtlasMap.put("mmsge", new MSG.MMSGE());

        directiveAtlasMap.put("define", new DirectiveStranglerError("define is not implemented yet"));
        directiveAtlasMap.put("asg", new DirectiveStranglerError("asg is not implemented yet"));
        directiveAtlasMap.put("unasg", new DirectiveStranglerError("unasg is not implemented yet"));
        directiveAtlasMap.put("undefine", new DirectiveStranglerError("undefine is not implemented yet"));

        directiveAtlasMap.put("include", new INCLUDE());
    }

    public boolean hasDirective(String atlas){
        return directiveAtlasMap.containsKey(atlas) && !(directiveAtlasMap.get(atlas) instanceof DirectiveStranglerError);
    }

    public void handleDirective(String atlas, LexSymbol root, PreProcessor preProcessor) {
        directiveAtlasMap.get(atlas).init(root, preProcessor);
    }

    public boolean hasStranglerDirective(String atlas) {
        return directiveAtlasMap.containsKey(atlas) && (directiveAtlasMap.get(atlas) instanceof DirectiveStranglerError);
    }

    public void handleStranglerDirective(String atlas, LexSymbol root, PreProcessor preProcessor){
        directiveAtlasMap.get(atlas).init(root, preProcessor);
    }

}