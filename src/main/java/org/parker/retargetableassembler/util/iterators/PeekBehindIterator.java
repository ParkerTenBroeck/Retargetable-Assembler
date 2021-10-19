package org.parker.retargetableassembler.util.iterators;

import java.util.Iterator;

public interface PeekBehindIterator<T> extends Iterator<T> {


    default void setMaxLookBehind(int max){
        setMaxLookBehind(max, null);
    }
    void setMaxLookBehind(int max, T fill);
    int getMaxLookBehind();

    default T peek_behind(){
        return peek_behind(0);
    }

    T peek_behind(int amount);

    default void clear_peek_behind(){
        fill_peek_behind(null);
    }
    void fill_peek_behind(T fill);

}
