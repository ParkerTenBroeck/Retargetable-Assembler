package org.parker.retargetableassembler.preprocessor.directives.other;

import org.parker.retargetableassembler.preprocessor.lex.cup.AssemblerSym;
import org.parker.retargetableassembler.preprocessor.lex.jflex.AssemblerScanner;
import org.parker.retargetableassembler.preprocessor.lex.jflex.AssemblerScannerPreProcessor;
import org.parker.retargetableassembler.preprocessor.PreProcessor;
import org.parker.retargetableassembler.preprocessor.directives.PreProcessorDirective;
import org.parker.retargetableassembler.preprocessor.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.preprocessor.util.BufferUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

public final class INCLUDE implements PreProcessorDirective {

    @Override
    public void init(LexSymbol root, PreProcessor pp) {

        String path;

        if(pp.getIteratorStack().peek_ahead().getSym() == AssemblerSym.STRING_LITERAL){
            LexSymbol s = pp.getIteratorStack().next();
            pp.getIteratorStack().next();
            path = (String) s.getValue();
        }else if(pp.getIteratorStack().peek_ahead().getSym() == AssemblerSym.LT){
            pp.getIteratorStack().next();
            LexSymbol s = pp.getIteratorStack().next();
            if(s.getSym() != AssemblerSym.IDENTIFIER){
                pp.report().unexpectedTokenError(pp.getIteratorStack().next());
                BufferUtils.tillLineTerminator(pp.getIteratorStack(), true).forEachRemaining(lexSymbol -> {});
                return;
            }else{
                path = s.getValue().toString();
            }
            s = pp.getIteratorStack().next();
            if(s.getSym() != AssemblerSym.GT){
                pp.report().unexpectedTokenError(pp.getIteratorStack().next());
                BufferUtils.tillLineTerminator(pp.getIteratorStack(), true).forEachRemaining(lexSymbol -> {});
                return;
            }

        }else{
            pp.report().unexpectedTokenError(pp.getIteratorStack().next());
            BufferUtils.tillLineTerminator(pp.getIteratorStack(), true).forEachRemaining(lexSymbol -> {});
            return;
        }


        File f = root.getFile();
        f = new File(f.getParent(), path);
        if(!f.exists()){
            f = new File(path);
        }
        if(!f.exists()){
            pp.report().reportError("File: '" + f.getPath() + "' does not exist", root);
            return;
        }

        Iterator<LexSymbol> as;
        try {
            as = new AssemblerScanner(new FileReader(f), f);
        } catch (FileNotFoundException e) {
            pp.report().reportError("Failed to load file cause: " + e.getMessage(), root);
            return;
        }
        as = new AssemblerScannerPreProcessor(as);
        pp.getIteratorStack().push_iterator_stack(as);
    }
}
