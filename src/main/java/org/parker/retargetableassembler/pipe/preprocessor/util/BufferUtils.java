package org.parker.retargetableassembler.pipe.preprocessor.util;

import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.util.iterators.PeekEverywhereIterator;
import org.parker.retargetableassembler.pipe.util.iterators.PeekEverywhereIteratorAbstract;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BufferUtils {

    private static Logger LOGGER = Logger.getLogger("PreProcessor");

    /**
     * This method ensures the next token is after a LINE_TERMINATION
     * This method will create an error if the next token is NOT a LINE_TERMINATION
     */
    public static void EnsureNextLine(Iterator<LexSymbol> ss) {
        EnsureNextLine(ss, true);
    }

    public static void EnsureNextLine(Iterator<LexSymbol> ss, boolean report) {
        LexSymbol symbol = ss.next();

        if(symbol.getSym() != LexSymbol.LINE_TERMINATOR && report){
            LOGGER.log(Level.SEVERE, "sheesh");
        }else{
            return;
        }
        while(symbol.getSym() != LexSymbol.LINE_TERMINATOR && ss.hasNext()){
            symbol = ss.next();
        }
        return;
    }

    public static<T> List<T> iteratorToArrayList(Iterator<T> i){
        ArrayList<T> tmp = new ArrayList<>();
        i.forEachRemaining(o -> tmp.add(o));
        return tmp;
    }

    public static<T> Iterator<T> iteratorFromList(List<T> list){
        return new Iterator<T>() {
            final T[] data;
            int index = 0;
            {
                data = list.toArray((T[])new Object[0]);
            }
            @Override
            public boolean hasNext() {
                return index >= data.length;
            }

            @Override
            public T next() {
                if(index >= data.length) return data[data.length - 1];
                return data[index++];
            }
        };
    }

    public static<T> PeekEverywhereIterator<T> peekEverywhereIteratorFromList(List<T> list){
        return new PeekEverywhereIteratorAbstract<T>() {
            final T[] data;
            int index = 0;
            {
                data = list.toArray((T[])new Object[0]);
            }
            @Override
            public boolean hasNext() {
                return index < data.length && index >= 0;
            }

            @Override
            protected T next_peekless() {
                if(index >= data.length) return data[index - 1];
                return data[index++];
            }

        };
    }

    public static LineTerminatorIterator tillLineTerminator(Iterator<LexSymbol> iterator, boolean includeLineTerminator) {
        return new LineTerminatorIterator(iterator, includeLineTerminator);
    }

    public static class LineTerminatorIterator implements Iterator<LexSymbol> {

        private LexSymbol next;
        private LexSymbol last;
        private Iterator<LexSymbol> iterator;
        private boolean includeLineTerminator;


        public LineTerminatorIterator(Iterator<LexSymbol> iterator, boolean includeLineTerminator){
            this.iterator = iterator;
            this.includeLineTerminator = includeLineTerminator;

            if(iterator != null && iterator.hasNext()){
                next = iterator.next();
            }else{
                next = null;
            }
        }

        @Override
        public boolean hasNext() {
            if(next == null || next.getSym() == LexSymbol.EOF){
                return false;
            }else{
                if(next.getSym() == LexSymbol.LINE_TERMINATOR && includeLineTerminator){
                    last = next;
                    next = null;
                    return true;
                }else{
                   return next.getSym() != LexSymbol.LINE_TERMINATOR;
                }
            }
        }

        @Override
        public LexSymbol next() {
            LexSymbol curr = next;

            if(next == null || next.getSym() == LexSymbol.LINE_TERMINATOR || next.getSym() == LexSymbol.EOF){
                if(includeLineTerminator) return last;
                return next;
            }

            if(iterator != null && iterator.hasNext()){
                next = iterator.next();
            }else{
                next = null;
            }

            return curr;
        }

        public void toLineTerminator(){
            while(this.hasNext()){
                this.next();
            }
        }

    }
}
