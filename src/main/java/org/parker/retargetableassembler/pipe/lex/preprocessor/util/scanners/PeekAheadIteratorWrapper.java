package org.parker.retargetableassembler.pipe.lex.preprocessor.util.scanners;

import java.util.ArrayDeque;
import java.util.Iterator;

public class PeekAheadIteratorWrapper<T> extends PeekAheadIteratorAbstract<T> {

    private Iterator<T> iterator;

    public PeekAheadIteratorWrapper(Iterator<T> iterator){
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        if(iterator == null){
            return false;
        }else{
            return iterator.hasNext();
        }
    }

    @Override
    protected T next_peekless(){
        if(iterator == null){
            return null;
        }
        if(iterator.hasNext()){
            return iterator.next();
        }else{
            return null;
        }
    }
}
