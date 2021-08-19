package org.parker.retargetableassembler.pipe.preprocessor.directives.control;

import org.parker.retargetableassembler.pipe.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.preprocessor.directives.PreProcessorDirective;
import org.parker.retargetableassembler.pipe.util.iterators.IteratorStack;
import org.parker.retargetableassembler.pipe.util.iterators.PeekEverywhereIterator;
import org.parker.retargetableassembler.pipe.util.iterators.PeekEverywhereIteratorAbstract;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.parker.retargetableassembler.pipe.preprocessor.util.BufferUtils.EnsureNextLine;

public final class IF implements PreProcessorDirective {

    private static final Logger LOGGER = Logger.getLogger("PreProcessor");

    @Override
    public void init(IteratorStack<LexSymbol> iterator, PreProcessor pp) {
        iterator.push_iterator_stack(new ifIterator(iterator.peek_iterator_stack()));
    }

    private class ifIterator extends PeekEverywhereIteratorAbstract<LexSymbol> {

        private final PeekEverywhereIterator<LexSymbol> ss;
        private boolean controlState;
        private boolean exit;
        private boolean hasNext;
        private byte state;
        private short level;

        public ifIterator(PeekEverywhereIterator<LexSymbol> ss) {
            this.ss = ss;
            if(ss.peek_ahead().sym == LexSymbol.BOOLEAN_LITERAL){
                controlState = (Boolean) ss.next().value;
                ss.next();
                state = 0;
                exit = false;
                hasNext = true;
                level = 0;
            }else{
                throw new RuntimeException("must be boolean found: " + ss.next());
            }
        }


        @Override
        public boolean hasNext() {
            return ss.hasNext() && hasNext;
        }

        @Override
        protected LexSymbol next_peekless() {
            LexSymbol symbol = ss.next();

            while(state == 0 && !exit){
                if(symbol.sym == LexSymbol.DIRECTIVE){
                    if(symbol.value.equals("elseif") && level == 0){
                        if(controlState){
                            exit = true;
                            break;
                        }else{
                            state = 1;
                            controlState = (Boolean) ss.next().value;
                            EnsureNextLine(ss);
                            symbol = ss.next();
                            continue;
                        }

                    }else if(symbol.value.equals("else") && level == 0){
                        if(controlState){
                            exit = true;
                            break;
                        }else{
                            state = 2;
                            controlState = !controlState;
                            EnsureNextLine(ss);
                            symbol = ss.next();
                            continue;
                        }

                    }else if(symbol.value.equals("endif")){
                        if(level == 0) {
                            exit = true;
                            break;
                        }else{
                            level --;
                        }
                    }else if(symbol.value.equals("if")){
                        level ++;
                    }
                }
                if(controlState){
                    return symbol;
                }else{
                    symbol = ss.next();
                }
            }

            while(state == 1 && !exit){
                if(symbol.sym == LexSymbol.DIRECTIVE){
                    if(symbol.value.equals("elseif") && level == 0){
                        if(controlState){
                            exit = true;
                            break;
                        }else{
                            state = 1;
                            controlState = (Boolean) ss.next().value;
                            EnsureNextLine(ss);
                            symbol = ss.next();
                            continue;
                        }

                    }else if(symbol.value.equals("else") && level == 0){
                        if(controlState){
                            exit = true;
                            break;
                        }else{
                            state = 2;
                            controlState = !controlState;
                            EnsureNextLine(ss);
                            symbol = ss.next();
                            continue;
                        }
                    }else if(symbol.value.equals("endif")){
                        if(level == 0) {
                            exit = true;
                            break;
                        }else{
                            level --;
                        }
                    }else if(symbol.value.equals("if")){
                        level ++;
                    }
                }
                if(controlState){
                    return symbol;
                }else{
                    symbol = ss.next();
                }
            }

            while(state == 2 && !exit){
                if(symbol.sym == LexSymbol.DIRECTIVE){
                    if (symbol.value.equals("elseif") && level == 0) {
                        exit = true;
                        break;
                    } else if (symbol.value.equals("else") && level == 0){
                        exit = true;
                        break;
                    } else if(symbol.value.equals("endif")){
                        if(level == 0) {
                            exit = true;
                            break;
                        }else{
                            level --;
                        }
                    }else if(symbol.value.equals("if")){
                        level ++;
                    }
                }
                if(controlState){
                    return symbol;
                }else{
                    symbol = ss.next();
                }
            }

            while(exit){
                if(symbol.sym == LexSymbol.DIRECTIVE) {
                    if (symbol.value.equals("elseif") && level == 0 && state >= 2) {
                        LOGGER.log(Level.WARNING, "elseif after else");
                        EnsureNextLine(ss, false);
                    } else if (symbol.value.equals("else") && level == 0) {
                        if(state >= 2) {
                            LOGGER.log(Level.WARNING, "else after else");
                        }
                        EnsureNextLine(ss);
                        state = 2;
                    } else if (symbol.value.equals("endif")) {
                        EnsureNextLine(ss);
                        hasNext =  false;
                        return new LexSymbol(); //EOF
                    }
                }else if(symbol.sym == LexSymbol.EOF){
                    throw new RuntimeException("if without endif");
                }
                symbol = ss.next();
            }

            hasNext =  false;
            return new LexSymbol();//EOF fall case
        }
    }
}
