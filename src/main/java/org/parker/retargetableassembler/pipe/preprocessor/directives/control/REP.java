package org.parker.retargetableassembler.pipe.preprocessor.directives.control;

import org.parker.retargetableassembler.pipe.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.preprocessor.directives.PreProcessorDirective;
import org.parker.retargetableassembler.pipe.preprocessor.util.PreProcessorOutputFilter;
import org.parker.retargetableassembler.pipe.util.iterators.IteratorStack;
import org.parker.retargetableassembler.pipe.util.iterators.PeekEverywhereIterator;
import org.parker.retargetableassembler.pipe.util.iterators.PeekEverywhereIteratorAbstract;

import java.util.ArrayList;

public final class REP implements PreProcessorDirective {


    @Override
    public void init(IteratorStack<LexSymbol> iterator, PreProcessor pp) {

        iterator.push_iterator_stack(new loopIterator(iterator, pp));
    }

    private class loopIterator extends PeekEverywhereIteratorAbstract<LexSymbol> implements PreProcessorOutputFilter {

        private PreProcessor pp;
        private IteratorStack<LexSymbol> stack;
        private PeekEverywhereIterator<LexSymbol> last;
        private int count = 0;

        boolean buildingLoop = true;
        ArrayList<LexSymbol> loop = new ArrayList<>();
        int loopIndex = 0;
        boolean exit = false;
        boolean requestExit = false;

        public loopIterator(IteratorStack<LexSymbol> iterator, PreProcessor pp){
            this.pp = pp;
            this.stack = iterator;
            this.last = iterator.peek_iterator_stack();
            this.count = ((Number)iterator.next().value).intValue();
            iterator.next();
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
                            throw new RuntimeException("REP not terminated");
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
            if(!buildingLoop){

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
            return new LexSymbol();//i dont think this should be reached
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
