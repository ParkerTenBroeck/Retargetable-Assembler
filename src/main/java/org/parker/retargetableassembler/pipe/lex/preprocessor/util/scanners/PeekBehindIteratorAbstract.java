package org.parker.retargetableassembler.pipe.lex.preprocessor.util.scanners;

import org.parker.retargetableassembler.pipe.lex.preprocessor.util.buffers.CircularBuffer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Iterator;

public abstract class PeekBehindIteratorAbstract<T> implements PeekBehindIterator<T> {

    private CircularBuffer<T> lookBehindBuffer = new CircularBuffer<>();

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
    public int getMaxLookBehind() {
        return lookBehindBuffer.getSize();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        //throw new NotImplementedException();
    }
}
