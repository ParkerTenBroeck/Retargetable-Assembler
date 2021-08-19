package org.parker.retargetableassembler.pipe.util.iterators;

import org.parker.retargetableassembler.pipe.util.buffers.CircularBuffer;

public abstract class PeekBehindIteratorAbstract<T> implements PeekBehindIterator<T> {

    private CircularBuffer<T> lookBehindBuffer;


    public PeekBehindIteratorAbstract(){
        this(16, null);
    }

    public PeekBehindIteratorAbstract(int size){
        this(size, null);
    }

    public PeekBehindIteratorAbstract(T fill){
        this(16, null);
    }

    public PeekBehindIteratorAbstract(int size, T fill){
        lookBehindBuffer = new CircularBuffer<>(size);
        lookBehindBuffer.fill(fill);
    }

    @Override
    public abstract boolean hasNext();

    protected abstract T next_peekless();

    @Override
    public T peek_behind(int amount){
        return lookBehindBuffer.get(amount);
    }

    @Override
    public T next() {
        T next = next_peekless();
        lookBehindBuffer.add(next);
        return next;
    }

    @Override
    public void setMaxLookBehind(int max) {
        lookBehindBuffer.resize(max);
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
    public void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear_peek_behind() {
        lookBehindBuffer.clear();
    }

    @Override
    public void fill_peek_behind(T fill) {
        lookBehindBuffer.clear();
        lookBehindBuffer.fill(fill);
    }
}
