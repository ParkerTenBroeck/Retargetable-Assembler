package org.parker.retargetableassembler.pipe.preprocessor.util;

import org.parker.retargetableassembler.pipe.lex.jflex.LexSymbol;

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
}
