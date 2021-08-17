package org.parker.retargetableassembler.pipe.lex.preprocessor.util.scanners;

import org.parker.retargetableassembler.pipe.lex.preprocessor.util.buffers.CircularBuffer;

import java.util.ArrayDeque;
import java.util.Iterator;

public class PeekBehindIteratorWrapper<T> extends PeekBehindIteratorAbstract<T> {

    private Iterator<T> iterator;

    public PeekBehindIteratorWrapper(Iterator<T> iterator){
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
