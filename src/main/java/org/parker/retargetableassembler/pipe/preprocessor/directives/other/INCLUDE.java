package org.parker.retargetableassembler.pipe.preprocessor.directives.other;

import org.parker.retargetableassembler.pipe.lex.cup.AssemblerSym;
import org.parker.retargetableassembler.pipe.lex.jflex.AssemblerScanner;
import org.parker.retargetableassembler.pipe.lex.jflex.AssemblerScannerPreProcessor;
import org.parker.retargetableassembler.pipe.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.preprocessor.directives.PreProcessorDirective;
import org.parker.retargetableassembler.pipe.util.iterators.IteratorStack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

public final class INCLUDE implements PreProcessorDirective {

    @Override
    public void init(IteratorStack<LexSymbol> iterator, PreProcessor pp) {

        if(iterator.peek_ahead().sym == AssemblerSym.STRING_LITERAL){
            LexSymbol s = iterator.next();
            String path = (String) s.value;
            File f = s.getFile();
            f = new File(f.getParent(), path);
            if(!f.exists()){
                f = new File(path);
            }
            if(!f.exists()){
                throw new RuntimeException(new FileNotFoundException("cannot find: " + f.getAbsolutePath()));
            }

            Iterator<LexSymbol> as = null;
            try {
                as = new AssemblerScanner(new FileReader(f), f);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            as = new AssemblerScannerPreProcessor(as);
            iterator.push_iterator_stack(as);
        }else{
        }
    }
}
