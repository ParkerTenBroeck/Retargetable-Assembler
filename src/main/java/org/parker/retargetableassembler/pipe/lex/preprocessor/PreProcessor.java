package org.parker.retargetableassembler.pipe.lex.preprocessor;

import org.parker.retargetableassembler.pipe.lex.cup.AssemblerSym;
import org.parker.retargetableassembler.pipe.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.lex.preprocessor.directives.Directives;
import org.parker.retargetableassembler.pipe.lex.preprocessor.util.scanners.IteratorStack;

import java.util.Iterator;

public class PreProcessor implements Iterator<LexSymbol>{

    private Directives directives = new Directives();

    private IteratorStack<LexSymbol> scannerStack = new IteratorStack<LexSymbol>(){
        @Override
        protected LexSymbol next_peekless() {
            LexSymbol t = super.next_peekless();

            while(t.sym == LexSymbol.EOF){
                LexSymbol peek = super.peek_ahead();
                if(peek == null){
                    super.next_peekless();
                    return t;
                }else{
                    t = super.next_peekless();
                }
            }

            return t;
        }
    };
    private boolean atEOF = false;




    @Override
    public LexSymbol next() {
        LexSymbol s = scannerStack.next();
        if(s.sym == AssemblerSym.EOF){
            atEOF = true;
            return s;
        }
        if(s.sym == AssemblerSym.DOT &&
                scannerStack.peek_ahead().sym == AssemblerSym.IDENTIFIER){

            LexSymbol directiveSymbol = scannerStack.peek_ahead();
            if(directiveSymbol.sym == LexSymbol.IDENTIFIER){
                if(directives.hasDirective((String)directiveSymbol.value)){
                    scannerStack.next();
                    directives.handleDirective((String)directiveSymbol.value, scannerStack, this);
                    s = scannerStack.next();
                }
            }
        }
        return s;
    }

    public boolean hasNext(){
        return atEOF;
    }

    public void clear(){
        scannerStack.clear();
        atEOF = false;
    }

    public void start(Iterator<LexSymbol> s){
        clear();
        scannerStack.push_iterator_stack(s);
    }
}
