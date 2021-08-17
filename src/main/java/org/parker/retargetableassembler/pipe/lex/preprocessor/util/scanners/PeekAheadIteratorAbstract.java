package org.parker.retargetableassembler.pipe.lex.preprocessor.util.scanners;

import java.util.ArrayDeque;
import java.util.Iterator;

public abstract class PeekAheadIteratorAbstract<T> implements PeekAheadIterator {

    private ArrayDeque<T> peekQueue = new ArrayDeque<>();
    private int maxLookAhead = 32;

    public int getPeekQueueSize(){
        return peekQueue.size();
    }

    @Override
    public void setMaxLookAhead(int max) {
        if(max > peekQueue.size()){
            throw new IndexOutOfBoundsException("new size exceeds current size");
        }
        maxLookAhead = max;
    }

    @Override
    public int getMaxLookAhead() {
        return maxLookAhead;
    }

    @Override
    public T peek_ahead(int amount) {
        if(amount > maxLookAhead){
            throw new IndexOutOfBoundsException();
        }
        amount -= peekQueue.size();
        while(amount >= 0){
            peekQueue.addLast(next_peekless());
            amount--;
        }
        return peekQueue.stream().skip(peekQueue.size() + amount).iterator().next();
    }


    @Override
    public abstract boolean hasNext();

    protected abstract T next_peekless();

    @Override
    public T next() {

        if(peekQueue.size() > 0){
            return peekQueue.removeFirst();
        }
        return next_peekless();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
