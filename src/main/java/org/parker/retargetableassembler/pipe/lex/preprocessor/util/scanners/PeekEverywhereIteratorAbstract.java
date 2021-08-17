package org.parker.retargetableassembler.pipe.lex.preprocessor.util.scanners;

import org.parker.retargetableassembler.pipe.lex.preprocessor.util.buffers.CircularBuffer;

import java.util.ArrayDeque;
import java.util.Iterator;

public abstract class PeekEverywhereIteratorAbstract<T> implements PeekEverywhereIterator<T>{

    private CircularBuffer<T> lookBehindBuffer = new CircularBuffer<>();
    private ArrayDeque<T> peekQueue = new ArrayDeque<>();
    private int maxLookAhead = 32;

    public int getPeekQueueSize(){
        return peekQueue.size();
    }

    @Override
    public void setMaxLookBehind(int max) {
        lookBehindBuffer.resize(max);
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
        amount -= peekQueue.size();
        while(amount >= 0){
            peekQueue.addLast(next_peekless());
            amount--;
        }
        return peekQueue.stream().skip(peekQueue.size() + amount).iterator().next();
    }

    @Override
    public T peek_behind(int amount){
        return lookBehindBuffer.get(amount);
    }

    @Override
    public T next() {
        T next;

        if(peekQueue.size() > 0){
            next = peekQueue.removeFirst();
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
}
