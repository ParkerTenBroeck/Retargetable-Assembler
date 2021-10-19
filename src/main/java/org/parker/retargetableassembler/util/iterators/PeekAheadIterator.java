package org.parker.retargetableassembler.util.iterators;

import java.util.Iterator;

public interface PeekAheadIterator<T> extends Iterator<T> {

    void setMaxLookAhead(int max);
    int getMaxLookAhead();

    default T peek_ahead(){
        return peek_ahead(0);
    }

    T peek_ahead(int amount);

}