package org.parker.retargetableassembler.preprocessor.directives.control;

import org.parker.retargetableassembler.preprocessor.PreProcessor;
import org.parker.retargetableassembler.preprocessor.directives.PreProcessorDirective;
import org.parker.retargetableassembler.preprocessor.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.preprocessor.util.PreProcessorOutputFilter;
import org.parker.retargetableassembler.util.iterators.IteratorStack;
import org.parker.retargetableassembler.util.iterators.PeekEverywhereIterator;
import org.parker.retargetableassembler.util.iterators.PeekEverywhereIteratorAbstract;

import java.util.ArrayList;

public final class REP implements PreProcessorDirective {


    @Override
    public void init(LexSymbol root, PreProcessor pp) {

        try {
            pp.getIteratorStack().push_iterator_stack(new loopIterator(root, pp));
        }catch (Brulony e){

        }
    }
    protected class Brulony extends RuntimeException{

    }

    private class loopIterator extends PeekEverywhereIteratorAbstract<LexSymbol> implements PreProcessorOutputFilter {

        LexSymbol root;

        private PreProcessor pp;
        private IteratorStack<LexSymbol> stack;
        private PeekEverywhereIterator<LexSymbol> last;
        ArrayList<LexSymbol> loop = new ArrayList<>();

        private int iterations;
        private int count;
        boolean buildingLoop = true;
        int loopIndex = 0;
        boolean exit = false;
        boolean requestExit = false;

        public loopIterator(LexSymbol root, PreProcessor pp){
            this.root = root;
            this.pp = pp;
            this.stack = pp.getIteratorStack();
            this.last = stack.peek_iterator_stack();
            {
                PreProcessor.EvaluatedExpression tmp = pp.evaluateExpression();
                if(tmp.val instanceof Integer || tmp.val instanceof Long || tmp.val instanceof Short || tmp.val instanceof  Byte){
                    this.count = ((Number)tmp.val).intValue();
                    if(count < 0){
                        pp.report().reportError("Cannot have a negative value for loop num", stack.peek_behind());
                    }
                }else{
                    count = -1;
                    pp.report().reportError("Cannot use '" + tmp.getClass().getSimpleName() + "' for num iterations", tmp.expression.toSymbols());
                }
            }
            this.iterations = count;
            stack.next();
        }

        @Override
        public boolean hasNext() {
            return !exit;
        }

        @Override
        protected LexSymbol next_peekless() {

            if(exit) return new LexSymbol();
            if(count <= 0){
                exit = true;
            }

            if(requestExit){
                if(stack.peek_iterator_stack().equals(this)){
                    exit = true;
                }
            }
            if(buildingLoop){
                if(exit){
                    LexSymbol s = last.next();

                    while(!(s.getSym() == LexSymbol.DIRECTIVE && s.getValue().equals("endrep"))){
                        if(s.getSym() == LexSymbol.EOF){

                            pp.report().reportError("rep never terminated", root);
                            return  processSymbol(s);
                        }
                        s = last.next();
                    }
                    return new LexSymbol();

                }else {
                    LexSymbol s = last.next();
                    if (s.getSym() == LexSymbol.DIRECTIVE && s.getValue().equals("endrep") && stack.peek_iterator_stack().equals(this)) {
                        buildingLoop = false;
                        count --;
                    }else{
                        loop.add(s);
                        return processSymbol(s);
                    }
                }
            }
            if(exit) return new LexSymbol();
            if(loop.size() == 0 || count <= 0 || exit == true){
                exit = true;
                return new LexSymbol();
            }
            while(loopIndex >= loop.size()){
                count --;
                loopIndex = 0;
                if(count <= 0){
                    exit = true;
                    return new LexSymbol();
                }
            }
            LexSymbol sym = loop.get(loopIndex);
            sym = processSymbol(sym);
            loopIndex ++;
            return sym;
        }

        private LexSymbol processSymbol(LexSymbol sym){
            if(sym.getSym() == LexSymbol.IDENTIFIER){
                if(sym.getValue().toString().matches("[$]*\\$_r")){
                    if(sym.getValue().toString().length() > 3){
                        sym = sym.setValue(sym.getValue().toString().substring(1));
                    }else{
                        sym = sym.setSymValue(LexSymbol.INTEGER_LITERAL, iterations - count);
                    }
                }
            }
            return sym;
        }

        @Override
        public LexSymbol filterOutput(LexSymbol symbol) {
            if(symbol.getSym() == LexSymbol.DIRECTIVE && symbol.getValue().equals("exitrep")){
                this.requestExit = true;
                return null;
            }else if(symbol.getSym() == LexSymbol.DIRECTIVE && symbol.getValue().equals("endrep")){
                buildingLoop = false;
                count --;
                loop.remove(loop.size() - 1);
                return null;
            }else{
                return symbol;
            }
        }
    }
}
