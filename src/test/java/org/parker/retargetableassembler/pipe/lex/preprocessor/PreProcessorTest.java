package org.parker.retargetableassembler.pipe.lex.preprocessor;

import org.junit.Test;
import org.parker.retargetableassembler.pipe.lex.jflex.AssemblerScanner;

import java.io.File;
import java.io.FileReader;

public class PreProcessorTest {

    @Test
    public void test() throws Exception {
        File file = new File("src/test/resources/MIPS/PreProcessor/test2.asm");
        AssemblerScanner as = new AssemblerScanner(new FileReader(file), file);

        PreProcessor pp = new PreProcessor();
        pp.start(as);

        while(!pp.hasNext()){
            System.out.println(pp.next().toString());
        }
    }

}