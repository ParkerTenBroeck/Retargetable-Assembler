package org.parker.retargetableassembler.pipe.preprocessor.lex.jflex;

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

        if(s.getSym() == LexSymbol.EOF){
            if(!atEOF && !atBOL){
                atEOF = true;
                last = s;
                s = s.setSym(LexSymbol.LINE_TERMINATOR);
            }else {
                atEOF = true;
                last = s;
            }
        }

        while(s.getSym() == LexSymbol.WHITESPACE){
            s = iterator.next();
        }

        if(atBOL && s.getSym() == LexSymbol.IDENTIFIER){
            if(iterator.peek_ahead().getSym() == LexSymbol.COLON){
                LexSymbol colon = iterator.next();
                s = LexSymbol.combine(LexSymbol.LABEL, s.getValue(), s, colon);
            }
        }
        if(atBOL || this.peek_behind().getSym() == LexSymbol.LABEL){
            if(s.getSym() == LexSymbol.IDENTIFIER){
                if(s.getValue().toString().startsWith(".")){
                    s = s.setSymValue(LexSymbol.DIRECTIVE, s.getValue().toString().substring(1));
                }else{
                    s = s.setSym(LexSymbol.INSTRUCTION);
                }
            }
        }

        atBOL = s.getSym() == LexSymbol.LINE_TERMINATOR;

        return s;
    }
}
