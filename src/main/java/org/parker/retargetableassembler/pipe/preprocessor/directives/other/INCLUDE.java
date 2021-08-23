package org.parker.retargetableassembler.pipe.preprocessor.directives.other;

import org.parker.retargetableassembler.pipe.preprocessor.lex.cup.AssemblerSym;
import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.AssemblerScanner;
import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.AssemblerScannerPreProcessor;
import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.preprocessor.directives.PreProcessorDirective;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

public final class INCLUDE implements PreProcessorDirective {

    @Override
    public void init(LexSymbol root, PreProcessor pp) {

        if(pp.getIteratorStack().peek_ahead().sym == AssemblerSym.STRING_LITERAL){
            LexSymbol s = pp.getIteratorStack().next();
            pp.getIteratorStack().next();
            String path = (String) s.value;
            File f = s.getFile();
            f = new File(f.getParent(), path);
            if(!f.exists()){
                f = new File(path);
            }
            if(!f.exists()){
                pp.report().reportError("File: '" + f.getPath() + "' does not exist", root);
                return;
            }

            Iterator<LexSymbol> as = null;
            try {
                as = new AssemblerScanner(new FileReader(f), f);
            } catch (FileNotFoundException e) {
                pp.report().reportError("Failed to load file cause: " + e.getMessage(), root);
                return;
            }
            as = new AssemblerScannerPreProcessor(as);
            pp.getIteratorStack().push_iterator_stack(as);
        }else{
        }
    }
}
