package org.parker.retargetableassembler.pipe.util.iterators;

import org.parker.retargetableassembler.pipe.util.buffers.CircularBuffer;

import java.util.ArrayDeque;

public abstract class PeekEverywhereIteratorAbstract<T> implements PeekEverywhereIterator<T>{

    private CircularBuffer<T> lookBehindBuffer;
    private ArrayDeque<T> lookAheadQueue;
    private int maxLookAhead = 16;

    public PeekEverywhereIteratorAbstract(){
        this(16, 16, null);
    }

    public PeekEverywhereIteratorAbstract(int maxLookAhead, int maxLookBehind){
        this(maxLookAhead, maxLookBehind, null);
    }

    public PeekEverywhereIteratorAbstract(T maxLookBehindFill){
        this(16, 16, maxLookBehindFill);
    }

    public PeekEverywhereIteratorAbstract(int maxLookAhead, int maxLookBehind, T maxLookBehindFill){
        this.maxLookAhead = maxLookAhead;

        this.lookBehindBuffer = new CircularBuffer<>(maxLookBehind, maxLookBehindFill);
        this.lookAheadQueue = new ArrayDeque<>(maxLookAhead);
    }

    public int getPeekQueueSize(){
        return lookAheadQueue.size();
    }

    @Override
    public void setMaxLookBehind(int max, T fill) {
        lookBehindBuffer.resize(max, fill);
    }

    @Override
    public int getMaxLookBehind() {
        return lookBehindBuffer.getSize();
    }

    @Override
    public void setMaxLookAhead(int max) {
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
    public T peek_behind(int amount){
        return lookBehindBuffer.get(amount);
    }

    @Override
    public T next() {
        T next;

        if(lookAheadQueue.size() > 0){
            next = lookAheadQueue.removeFirst();
        }else{
            next = next_peekless();
        }
        lookBehindBuffer.add(next);
        return next;
    }

    @Override
    public abstract boolean hasNext();

    protected abstract T next_peekless();

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void fill_peek_behind(T fill) {
        lookBehindBuffer.fill(fill);
    }
}
