package org.parker.retargetableassembler.pipe.lex.preprocessor.util.scanners;

import java.util.Iterator;

public class PeekEverywhereIteratorWrapper<T> extends PeekEverywhereIteratorAbstract<T>{

    private Iterator<T> iterator;

    public PeekEverywhereIteratorWrapper(Iterator<T> iterator){
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
