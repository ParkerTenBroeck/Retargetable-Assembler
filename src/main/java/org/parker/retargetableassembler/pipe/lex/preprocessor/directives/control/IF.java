package org.parker.retargetableassembler.pipe.lex.preprocessor.directives.control;

import org.parker.retargetableassembler.pipe.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.lex.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.lex.preprocessor.directives.PreProcessorDirective;
import org.parker.retargetableassembler.pipe.lex.preprocessor.util.scanners.IteratorStack;
import org.parker.retargetableassembler.pipe.lex.preprocessor.util.scanners.PeekEverywhereIterator;
import org.parker.retargetableassembler.pipe.lex.preprocessor.util.scanners.PeekEverywhereIteratorAbstract;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.parker.retargetableassembler.pipe.lex.preprocessor.util.buffers.BufferUtils.EnsureNextLine;

public class IF implements PreProcessorDirective {

    private static final Logger LOGGER = Logger.getLogger("PreProcessor");

    @Override
    public void init(IteratorStack<LexSymbol> scanner, PreProcessor pp) {
        scanner.push_iterator_stack(new ifScanner(scanner.peek_iterator_stack()));
    }

    private class ifScanner extends PeekEverywhereIteratorAbstract<LexSymbol> {

        private final PeekEverywhereIterator<LexSymbol> ss;
        private boolean controlState;
        private boolean exit;
        private boolean hasNext;
        private byte state;
        private short level;

        public ifScanner(PeekEverywhereIterator<LexSymbol> ss) {
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
                if(symbol.sym == LexSymbol.DOT && ss.peek_behind(1).sym == LexSymbol.LINE_TERMINATOR){
                    LexSymbol peek = ss.peek_ahead();
                    if(peek.sym == LexSymbol.IDENTIFIER){
                        if(peek.value.equals("elseif") && level == 0){
                            if(controlState){
                                exit = true;
                                break;
                            }else{
                                state = 1;
                                ss.next();
                                controlState = (Boolean) ss.next().value;
                                EnsureNextLine(ss);
                                symbol = ss.next();
                                continue;
                            }

                        }else if(peek.value.equals("else") && level == 0){
                            if(controlState){
                                exit = true;
                                break;
                            }else{
                                state = 2;
                                controlState = !controlState;
                                ss.next();
                                EnsureNextLine(ss);
                                symbol = ss.next();
                                continue;
                            }

                        }else if(peek.value.equals("endif")){
                            if(level == 0) {
                                exit = true;
                                break;
                            }else{
                                level --;
                            }
                        }else if(peek.value.equals("if")){
                            level ++;
                        }

                    }
                }
                if(controlState){
                    return symbol;
                }else{
                    symbol = ss.next();
                }
            }

            while(state == 1 && !exit){
                if(symbol.sym == LexSymbol.DOT && ss.peek_behind(1).sym == LexSymbol.LINE_TERMINATOR){
                    LexSymbol peek = ss.peek_ahead();
                    if(peek.value.equals("elseif") && level == 0){
                        if(controlState){
                            exit = true;
                            break;
                        }else{
                            state = 1;
                            ss.next();
                            controlState = (Boolean) ss.next().value;
                            EnsureNextLine(ss);
                            symbol = ss.next();
                            continue;
                        }

                    }else if(peek.value.equals("else") && level == 0){
                        if(controlState){
                            exit = true;
                            break;
                        }else{
                            state = 2;
                            controlState = !controlState;
                            ss.next();
                            EnsureNextLine(ss);
                            symbol = ss.next();
                            continue;
                        }
                    }else if(peek.value.equals("endif")){
                        if(level == 0) {
                            exit = true;
                            break;
                        }else{
                            level --;
                        }
                    }else if(peek.value.equals("if")){
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
                if(symbol.sym == LexSymbol.DOT && ss.peek_behind(1).sym == LexSymbol.LINE_TERMINATOR){
                    LexSymbol peek = ss.peek_ahead();
                    if (peek.value.equals("elseif") && level == 0) {
                        exit = true;
                        break;
                    } else if (peek.value.equals("else") && level == 0){
                        exit = true;
                        break;
                    } else if(peek.value.equals("endif")){
                        if(level == 0) {
                            exit = true;
                            break;
                        }else{
                            level --;
                        }
                    }else if(peek.value.equals("if")){
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
                if(symbol.sym == LexSymbol.DOT && ss.peek_behind(1).sym == LexSymbol.LINE_TERMINATOR) {
                    LexSymbol peek = ss.peek_ahead();
                    if(peek.sym == LexSymbol.IDENTIFIER) {
                        if (peek.value.equals("elseif") && level == 0 && state >= 2) {
                            LOGGER.log(Level.WARNING, "elseif after else");
                            EnsureNextLine(ss, false);
                        } else if (peek.value.equals("else") && level == 0) {
                            if(state >= 2) {
                                LOGGER.log(Level.WARNING, "else after else");
                            }
                            ss.next();
                            EnsureNextLine(ss);
                            state = 2;
                        } else if (peek.value.equals("endif")) {
                            ss.next();
                            EnsureNextLine(ss);
                            hasNext =  false;
                            return new LexSymbol(); //EOF
                        }
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
