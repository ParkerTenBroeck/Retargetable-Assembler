package org.parker.retargetableassembler.util.iterators;

import java.util.ArrayDeque;

public abstract class PeekAheadIteratorAbstract<T> implements PeekAheadIterator {

    private ArrayDeque<T> lookAheadQueue;
    private int maxLookAhead = 16;

    public PeekAheadIteratorAbstract(){
        this(16);
    }

    public PeekAheadIteratorAbstract(int maxLookAhead){
        this.lookAheadQueue = new ArrayDeque<>(maxLookAhead);
    }

    public int getPeekQueueSize(){
        return lookAheadQueue.size();
    }

    @Override
    public void setMaxLookAhead(int max) {
        if(max > lookAheadQueue.size()){
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
        amount -= lookAheadQueue.size();
        while(amount >= 0){
            lookAheadQueue.addLast(next_peekless());
            amount--;
        }
        return lookAheadQueue.stream().skip(lookAheadQueue.size() + amount).iterator().next();
    }


    @Override
    public abstract boolean hasNext();

    protected abstract T next_peekless();

    @Override
    public T next() {

        if(lookAheadQueue.size() > 0){
            return lookAheadQueue.removeFirst();
        }
        return next_peekless();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }



}
