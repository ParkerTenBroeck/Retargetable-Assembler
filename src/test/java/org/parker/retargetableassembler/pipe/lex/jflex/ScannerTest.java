package org.parker.retargetableassembler.pipe.lex.jflex;

import org.junit.Test;

import java.io.*;
import java.util.Iterator;


public class ScannerTest {

    @Test
    public void test() throws IOException {
        if(true)return;
        File file = new File("src/test/resources/MIPS/PreProcessor/test2.asm");
        Iterator<LexSymbol> as = new AssemblerScanner(new FileReader(file), file);


        while(as.hasNext()){
            System.out.println(as.next().toString());
        }
    }

}