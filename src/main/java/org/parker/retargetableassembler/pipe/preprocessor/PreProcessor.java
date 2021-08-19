package org.parker.retargetableassembler.pipe.preprocessor;

import org.parker.retargetableassembler.pipe.lex.cup.AssemblerSym;
import org.parker.retargetableassembler.pipe.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.preprocessor.directives.Directives;
import org.parker.retargetableassembler.pipe.preprocessor.util.PreProcessorOutputFilter;
import org.parker.retargetableassembler.pipe.util.iterators.IteratorStack;

import java.util.ArrayList;
import java.util.Iterator;

public class PreProcessor implements Iterator<LexSymbol>{

    private Directives directives = new Directives();
    private ArrayList<PreProcessorOutputFilter> filterStack = new ArrayList<>();
    private boolean atBOL = true;

    private IteratorStack<LexSymbol> iteratorStack = new IteratorStack<LexSymbol>(){

        {
            this.setMaxScannerStackSize(200);
        }

        @Override
        public void push_iterator_stack(Iterator<LexSymbol> iterator) {
            super.push_iterator_stack(iterator);
            if(iterator instanceof  PreProcessorOutputFilter){
                filterStack.add((PreProcessorOutputFilter) iterator);
            }
        }

        @Override
        protected void onPop(Iterator<LexSymbol> popped) {
            if(filterStack.size() <=0 ){
                return;
            }
            if(filterStack.get(filterStack.size() - 1).equals(popped)){
                filterStack.remove(popped);
            }
        }

        @Override
        protected LexSymbol next_peekless() {
            LexSymbol t = super.next_peekless();

                while (t == null || t.sym == LexSymbol.EOF) {
                    LexSymbol peek = super.peek_ahead();
                    if (peek == null) {
                        super.next_peekless();
                        return t;
                    } else {
                        t = super.next_peekless();
                    }
                }

            return t;
        }
    };

    private boolean atEOF = false;




    @Override
    public LexSymbol next() {

        LexSymbol s = null;

        while(s == null || (s.sym == LexSymbol.LINE_TERMINATOR && this.atBOL)) {
            s = iteratorStack.next();
            if (s.sym == AssemblerSym.EOF) {
                atEOF = true;
                return s;
            }
            while (s.sym == AssemblerSym.DIRECTIVE && directives.hasDirective((String) s.value)) {
                directives.handleDirective((String) s.value, iteratorStack, this);
                s = iteratorStack.next();
            }

            for (int i = filterStack.size() - 1; i >= 0; i--) {
                s = filterStack.get(i).filterOutput(s);
                if (s == null) {
                    break;
                }
            }

            if(s != null) {
                while (s.sym == AssemblerSym.DIRECTIVE && directives.hasStranglerDirective((String) s.value)) {
                    directives.handleStranglerDirective((String) s.value, iteratorStack, this);
                    s = iteratorStack.next();
                }
            }
        }
        atBOL = s.sym == LexSymbol.LINE_TERMINATOR;

        return s;
    }

    public boolean hasNext(){
        return atEOF;
    }

    public void clear(){
        iteratorStack.clear_peek_behind();
        atEOF = false;
    }

    public void start(Iterator<LexSymbol> s){
        clear();
        iteratorStack.push_iterator_stack(s);
    }
}
