package org.parker.retargetableassembler.util.iterators;

import java.util.Iterator;

public class PeekEverywhereIteratorWrapper<T> extends PeekEverywhereIteratorAbstract<T>{

    private Iterator<T> iterator;

    public PeekEverywhereIteratorWrapper(Iterator<T> iterator){
        super();
        this.iterator = iterator;
    }

    public PeekEverywhereIteratorWrapper(Iterator<T> iterator, int maxLookAhead, int maxLookBehind){
        super(maxLookAhead, maxLookBehind);
        this.iterator = iterator;
    }

    public PeekEverywhereIteratorWrapper(Iterator<T> iterator, T maxLookBehindFill){
        super(maxLookBehindFill);
        this.iterator = iterator;
    }

    public PeekEverywhereIteratorWrapper(Iterator<T> iterator, int maxLookAhead, int maxLookBehind, T maxLookBehindFill){
        super(maxLookAhead, maxLookBehind, maxLookBehindFill);
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
