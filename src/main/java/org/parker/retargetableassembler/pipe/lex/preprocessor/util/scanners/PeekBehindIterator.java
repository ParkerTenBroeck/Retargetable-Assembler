package org.parker.retargetableassembler.pipe.lex.preprocessor.util.scanners;

import java.util.Iterator;

public interface PeekBehindIterator<T> extends Iterator<T> {


    void setMaxLookBehind(int max);
    int getMaxLookBehind();

    default T peek_behind(){
        return peek_behind(0);
    }

    T peek_behind(int amount);

}
