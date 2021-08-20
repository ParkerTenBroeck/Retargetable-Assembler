package org.parker.retargetableassembler.pipe.lex.jflex;

import org.parker.retargetableassembler.pipe.util.iterators.PeekEverywhereIterator;
import org.parker.retargetableassembler.pipe.util.iterators.PeekEverywhereIteratorAbstract;
import org.parker.retargetableassembler.pipe.util.iterators.PeekEverywhereIteratorWrapper;

import java.util.Iterator;

public class AssemblerScannerPreProcessor extends PeekEverywhereIteratorAbstract<LexSymbol> {

    private PeekEverywhereIterator<LexSymbol> iterator;
    private boolean atBOL = true;
    private LexSymbol last = null;
    private boolean atEOF = false;
    private boolean hasNext = true;

    public AssemblerScannerPreProcessor(Iterator<LexSymbol> iterator){
        super(new LexSymbol());
        if(iterator instanceof PeekEverywhereIterator) {
            this.iterator = (PeekEverywhereIterator<LexSymbol>) iterator;
        }else{
            this.iterator = new PeekEverywhereIteratorWrapper<>(iterator, new LexSymbol());
        }

    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    protected LexSymbol next_peekless() {
        LexSymbol s;
        if(atEOF){
            hasNext = false;
            return last;
        }else{
            s = iterator.next();
        }

        if(s.sym == LexSymbol.EOF){
            atEOF = true;
            last = s;
            s = new LexSymbol(s.getFile(), LexSymbol.LINE_TERMINATOR, s.getLine(), s.getColumn(), s.getCharPos(), s.getSize(),s.left, s.right, s.value);
        }

        while(s.sym == LexSymbol.WHITESPACE){
            s = iterator.next();
        }

        if(atBOL || this.peek_behind().sym == LexSymbol.LABEL){
            if(s.sym == LexSymbol.DOT && iterator.peek_ahead().sym == LexSymbol.IDENTIFIER){
                LexSymbol ident = iterator.next();
                s = LexSymbol.combine(LexSymbol.DIRECTIVE, ident.value, s, ident);
            }
        }else if(atBOL && s.sym == LexSymbol.IDENTIFIER){
            if(iterator.peek_ahead().sym == LexSymbol.COLON){
                LexSymbol colon = iterator.next();
                s = LexSymbol.combine(LexSymbol.DIRECTIVE, s.value, s, colon);
            }else if(iterator.peek_ahead().sym == LexSymbol.WHITESPACE){
                iterator.next(); //disregard white space
                s.sym = LexSymbol.INSTRUCTION;
            }
        }

        atBOL = s.sym == LexSymbol.LINE_TERMINATOR;

        return s;
    }
}
