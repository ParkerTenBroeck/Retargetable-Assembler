package org.parker.retargetableassembler.preprocessor.lex.jflex;

import org.junit.Test;

import java.io.*;
import java.util.Iterator;


public class AssemblerScannerTest {

    @Test
    public void test() throws IOException {
        File file = new File("src/test/resources/MIPS/PreProcessor/test2.asm");
        Iterator<LexSymbol> as = new AssemblerScanner(new FileReader(file), file, true);


        while(as.hasNext()){
            System.out.println(as.next().toString());
        }
    }

}