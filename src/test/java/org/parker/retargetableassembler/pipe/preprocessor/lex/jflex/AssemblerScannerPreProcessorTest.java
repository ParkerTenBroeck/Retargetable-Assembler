package org.parker.retargetableassembler.pipe.preprocessor.lex.jflex;


import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class AssemblerScannerPreProcessorTest {

    @Test
    public void test() throws IOException {
        File file = new File("src/test/resources/MIPS/PreProcessor/test2.asm");
        Iterator<LexSymbol> as = new AssemblerScanner(new FileReader(file), file);
        as = new AssemblerScannerPreProcessor(as);

        while(as.hasNext()){
            System.out.println(as.next().toString());
        }
    }


}