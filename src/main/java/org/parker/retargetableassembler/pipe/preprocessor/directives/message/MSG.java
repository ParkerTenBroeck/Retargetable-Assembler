package org.parker.retargetableassembler.pipe.preprocessor.directives.message;

import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.preprocessor.directives.PreProcessorDirective;
import org.parker.retargetableassembler.pipe.preprocessor.util.BufferUtils;

public final class MSG {

    private enum Level{
        message,
        warning,
        error;
    }

    public static final class MMSG implements PreProcessorDirective {
        @Override
        public void init(LexSymbol root, PreProcessor pp) {
            LOG(root, pp, Level.message);
        }
    }

    public static final class WMSG implements PreProcessorDirective {
        @Override
        public void init(LexSymbol root, PreProcessor pp) {
            LOG(root, pp, Level.warning);
        }
    }

    public static final class EMSG implements PreProcessorDirective {

        @Override
        public void init(LexSymbol root, PreProcessor pp) {
            LOG(root, pp, Level.error);
        }
    }

    public static void LOG(LexSymbol root, PreProcessor pp, Level logLevel){
        BufferUtils.LineTerminatorIterator line = BufferUtils.tillLineTerminator(pp.getIteratorStack(), false);
        while(line.hasNext()){
            LexSymbol s = line.next();
            if(s.sym == LexSymbol.STRING_LITERAL){
                switch (logLevel){
                    case message:
                        pp.report().reportMessage(s.value.toString());
                        break;
                    case warning:
                        pp.report().reportWarning(s.value.toString(), root);
                        break;
                    case error:
                        pp.report().reportError(s.value.toString(), root);
                        break;
                }
            }else{
                line.toLineTerminator();
                throw new RuntimeException("Not a string");
            }
            if(line.hasNext()){
                s = line.next();
                if(s.sym != LexSymbol.COMMA){
                    line.toLineTerminator();
                    throw new RuntimeException(s.toString() + " is not a comma");
                }
            }else{
                break;
            }
        }
    }
}
