package org.parker.retargetableassembler.preprocessor.directives;

import org.parker.retargetableassembler.preprocessor.PreProcessor;
import org.parker.retargetableassembler.preprocessor.lex.jflex.LexSymbol;

import java.util.logging.Logger;

public final class DirectiveStranglerError implements PreProcessorDirective{

    private final static Logger LOGGER = Logger.getLogger("PreProcessor");

    private final String message;

    public DirectiveStranglerError(String message){
        this.message = message;
    }

    @Override
    public void init(LexSymbol root, PreProcessor pp) {
        pp.report().reportError(message, root);
    }
}
