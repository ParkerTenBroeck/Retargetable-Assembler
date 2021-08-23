package org.parker.retargetableassembler.pipe.preprocessor.util;

import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.LexSymbol;

import java.util.Iterator;
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

        if(symbol.sym != LexSymbol.LINE_TERMINATOR && report){
            LOGGER.log(Level.SEVERE, "sheesh");
        }else{
            return;
        }
        while(symbol.sym != LexSymbol.LINE_TERMINATOR && ss.hasNext()){
            symbol = ss.next();
        }
        return;
    }

    public static LineTerminatorIterator tillLineTerminator(Iterator<LexSymbol> iterator, boolean includeLineTerminator) {
        return new LineTerminatorIterator(iterator, includeLineTerminator);
    }

    public static class LineTerminatorIterator implements Iterator<LexSymbol> {

        private LexSymbol next;
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
            if(next == null || next.sym == LexSymbol.EOF){
                return false;
            }else{
                if(next.sym == LexSymbol.LINE_TERMINATOR && includeLineTerminator){
                    next = null;
                    return true;
                }else{
                   return next.sym != LexSymbol.LINE_TERMINATOR;
                }
            }
        }

        @Override
        public LexSymbol next() {
            LexSymbol curr = next;

            if(next == null || next.sym == LexSymbol.LINE_TERMINATOR || next.sym == LexSymbol.EOF){
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
