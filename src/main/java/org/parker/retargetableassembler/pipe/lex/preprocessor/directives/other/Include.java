package org.parker.retargetableassembler.pipe.lex.preprocessor.directives.other;

import org.parker.retargetableassembler.pipe.lex.cup.AssemblerSym;
import org.parker.retargetableassembler.pipe.lex.jflex.AssemblerScanner;
import org.parker.retargetableassembler.pipe.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.lex.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.lex.preprocessor.directives.PreProcessorDirective;
import org.parker.retargetableassembler.pipe.lex.preprocessor.util.scanners.IteratorStack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

public class Include implements PreProcessorDirective {

    @Override
    public void init(IteratorStack<LexSymbol> scanner, PreProcessor pp) {

        if(scanner.peek_ahead(0).sym == AssemblerSym.STRING_LITERAL){
            LexSymbol s = scanner.next();
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
            scanner.push_iterator_stack(as);
        }
    }
}
