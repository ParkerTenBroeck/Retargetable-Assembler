package org.parker.retargetableassembler.pipe.util.iterators;

import java.util.Iterator;

public class PeekBehindIteratorWrapper<T> extends PeekBehindIteratorAbstract<T> {

    private Iterator<T> iterator;

    public PeekBehindIteratorWrapper(Iterator<T> iterator){
        super();
        this.iterator = iterator;
    }

    public PeekBehindIteratorWrapper(Iterator<T> iterator, int size){
        super(size);
        this.iterator = iterator;
    }

    public PeekBehindIteratorWrapper(Iterator<T> iterator, T fill){
        super(16);
        this.iterator = iterator;
    }

    public PeekBehindIteratorWrapper(Iterator<T> iterator, int size, T fill){
        super(size, fill);
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
