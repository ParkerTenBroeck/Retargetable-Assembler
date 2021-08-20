package org.parker.retargetableassembler.pipe.preprocessor.directives.message;

import org.parker.retargetableassembler.pipe.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.preprocessor.directives.PreProcessorDirective;
import org.parker.retargetableassembler.pipe.preprocessor.util.BufferUtils;
import org.parker.retargetableassembler.pipe.util.iterators.IteratorStack;
import org.parker.retargetableassembler.util.AssemblerLogLevel;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MSG {


    private static final Logger LOGGER = Logger.getLogger("PreProcessor");

    public static final class MMSG implements PreProcessorDirective {
        @Override
        public void init(IteratorStack<LexSymbol> iterator, PreProcessor pp) {
            LOG(iterator, AssemblerLogLevel.ASSEMBLER_MESSAGE);
        }
    }

    public static final class WMSG implements PreProcessorDirective {
        @Override
        public void init(IteratorStack<LexSymbol> iterator, PreProcessor pp) {
            LOG(iterator, AssemblerLogLevel.ASSEMBLER_WARNING);
        }
    }

    public static final class EMSG implements PreProcessorDirective {

        @Override
        public void init(IteratorStack<LexSymbol> iterator, PreProcessor pp) {
            LOG(iterator, AssemblerLogLevel.ASSEMBLER_ERROR);
        }
    }

    public static void LOG(Iterator<LexSymbol> iterator, Level logLevel){
        BufferUtils.LineTerminatorIterator line = BufferUtils.tillLineTerminator(iterator, false);
        while(line.hasNext()){
            LexSymbol s = line.next();
            if(s.sym == LexSymbol.STRING_LITERAL){
                LOGGER.log(logLevel, s.value.toString());
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
