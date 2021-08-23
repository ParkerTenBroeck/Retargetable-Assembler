package org.parker.retargetableassembler.pipe.preprocessor.directives.control;

import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.preprocessor.directives.PreProcessorDirective;
import org.parker.retargetableassembler.pipe.preprocessor.util.BufferUtils;
import org.parker.retargetableassembler.pipe.util.iterators.PeekEverywhereIterator;
import org.parker.retargetableassembler.pipe.util.iterators.PeekEverywhereIteratorAbstract;

import static org.parker.retargetableassembler.pipe.preprocessor.util.BufferUtils.EnsureNextLine;

public final class IF implements PreProcessorDirective {

    @Override
    public void init(LexSymbol root, PreProcessor pp) {
        pp.getIteratorStack().push_iterator_stack(new ifIterator(root, pp));
    }

    private static class ifIterator extends PeekEverywhereIteratorAbstract<LexSymbol> {

        PreProcessor pp;
        private LexSymbol root;
        private final PeekEverywhereIterator<LexSymbol> ss;
        private boolean controlState;
        private boolean exit;
        private boolean hasNext;
        private byte state;
        private short level;

        public ifIterator(LexSymbol root, PreProcessor pp) {
            this.ss = pp.getIteratorStack().peek_iterator_stack();
            this.pp = pp;
            this.root = root;

            BufferUtils.LineTerminatorIterator line = BufferUtils.tillLineTerminator(ss, false);
            LexSymbol temp;
            if(line.hasNext()){
                temp = line.next();
                if(temp.sym == LexSymbol.BOOLEAN_LITERAL){
                    controlState = (Boolean) temp.value;
                    state = 0;
                    exit = false;
                    hasNext = true;
                    level = 0;
                }else{
                    pp.report().unexpectedTokenError(temp, LexSymbol.BOOLEAN_LITERAL);
                }
                if(line.hasNext()){
                    pp.report().unexpectedTokenError(temp);
                }
            }else{
                pp.report().reportError("expression syntax error", ss.peek_behind());
            }
            line.toLineTerminator();
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
                }else if(symbol.sym == LexSymbol.EOF){
                    pp.report().reportError("if without endif", root);
                    return symbol;
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
                }else if(symbol.sym == LexSymbol.EOF){
                    pp.report().reportError("if without endif", root);
                    return symbol;
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
                }else if(symbol.sym == LexSymbol.EOF){
                    pp.report().reportError("if without endif", root);
                    return symbol;
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
                        pp.report().reportError("elseif after else", symbol);
                        //LOGGER.log(Level.WARNING, "elseif after else");
                        EnsureNextLine(ss, false);
                    } else if (symbol.value.equals("else") && level == 0) {
                        if(state >= 2) {
                            pp.report().reportError("else after else", symbol);
                            //LOGGER.log(Level.WARNING, "else after else");
                        }
                        EnsureNextLine(ss);
                        state = 2;
                    } else if (symbol.value.equals("endif")) {
                        EnsureNextLine(ss);
                        hasNext =  false;
                        return new LexSymbol(); //EOF
                    }
                }else if(symbol.sym == LexSymbol.EOF){
                    pp.report().reportError("if without endif", root);
                    return symbol;
                }
                symbol = ss.next();
            }

            hasNext =  false;
            return new LexSymbol();//EOF fall case
        }
    }
}
