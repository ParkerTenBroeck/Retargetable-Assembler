package org.parker.retargetableassembler.pipe.preprocessor.directives.control;

import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.preprocessor.directives.PreProcessorDirective;
import org.parker.retargetableassembler.pipe.preprocessor.util.PreProcessorOutputFilter;
import org.parker.retargetableassembler.pipe.util.iterators.IteratorStack;
import org.parker.retargetableassembler.pipe.util.iterators.PeekEverywhereIterator;
import org.parker.retargetableassembler.pipe.util.iterators.PeekEverywhereIteratorAbstract;

import java.util.ArrayList;

public final class REP implements PreProcessorDirective {


    @Override
    public void init(LexSymbol root, PreProcessor pp) {

        pp.getIteratorStack().push_iterator_stack(new loopIterator(root, pp));
    }

    private class loopIterator extends PeekEverywhereIteratorAbstract<LexSymbol> implements PreProcessorOutputFilter {

        LexSymbol root;

        private PreProcessor pp;
        private IteratorStack<LexSymbol> stack;
        private PeekEverywhereIterator<LexSymbol> last;
        ArrayList<LexSymbol> loop = new ArrayList<>();

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
            this.count = ((Number)stack.next().value).intValue();
            stack.next();
        }

        @Override
        public boolean hasNext() {
            return !exit;
        }

        @Override
        protected LexSymbol next_peekless() {

            if(requestExit){
                if(stack.peek_iterator_stack().equals(this)){
                    exit = true;
                }
            }
            if(buildingLoop){
                if(exit){
                    LexSymbol s = last.next();

                    while(!(s.sym == LexSymbol.DIRECTIVE && s.value.equals("endrep"))){
                        if(s.sym == LexSymbol.EOF){

                            pp.report().reportError("rep never terminated", root);
                            return s;
                        }
                        s = last.next();
                    }
                    return last.next();

                }else {
                    LexSymbol s = last.next();
                    if (s.sym == LexSymbol.DIRECTIVE && s.value.equals("endrep") && stack.peek_iterator_stack().equals(this)) {
                        buildingLoop = false;
                    }else{
                        loop.add(s);
                        return s;
                    }
                }
            }
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
            LexSymbol s = loop.get(loopIndex);
            loopIndex ++;
            return s;
        }

        @Override
        public LexSymbol filterOutput(LexSymbol symbol) {
            if(symbol.sym == LexSymbol.DIRECTIVE && symbol.value.equals("exitrep")){
                this.requestExit = true;
                return null;
            }else if(symbol.sym == LexSymbol.DIRECTIVE && symbol.value.equals("endrep")){
                buildingLoop = false;
                loop.remove(loop.size() - 1);
                return null;
            }else{
                return symbol;
            }
        }
    }
}
