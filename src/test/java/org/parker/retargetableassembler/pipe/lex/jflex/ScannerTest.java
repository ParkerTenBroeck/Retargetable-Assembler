package org.parker.retargetableassembler.pipe.lex.jflex;

import org.junit.Test;

import java.io.*;


public class ScannerTest {

    @Test
    public void test() throws IOException {
        File file = new File("src/test/resources/MIPS/PreProcessor/test2.asm");
        AssemblerScanner as = new AssemblerScanner(new FileReader(file), file);

        while(!as.yyatEOF()){
            as.next_token();
            //System.out.println(as.next_token().toString());
        }
    }

}