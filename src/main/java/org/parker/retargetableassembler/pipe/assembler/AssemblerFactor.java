package org.parker.retargetableassembler.pipe.assembler;

import org.parker.retargetableassembler.pipe.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.AssemblerScanner;
import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.AssemblerScannerPreProcessor;
import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.LexSymbol;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Iterator;

public class AssemblerFactor {

    public static Assembler createAssembler(File file) throws FileNotFoundException {

        Reader reader = new FileReader(file);
        Iterator<LexSymbol> input = new AssemblerScanner(reader, file);
        input = new AssemblerScannerPreProcessor(input);
        PreProcessor pp = new PreProcessor(input);
        Assembler as = new Assembler(pp);
        return as;
    }
}
