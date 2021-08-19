package org.parker.retargetableassembler.pipe.preprocessor;

import org.junit.Test;
import org.parker.retargetableassembler.pipe.lex.jflex.AssemblerScanner;
import org.parker.retargetableassembler.pipe.lex.jflex.AssemblerScannerPreProcessor;
import org.parker.retargetableassembler.pipe.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.util.iterators.IteratorStack;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Iterator;

public class PreProcessorTest {

    @Test
    public void test() throws Exception {
        File file = new File("src/test/resources/MIPS/PreProcessor/test2.asm");
        Iterator<LexSymbol> as = new AssemblerScanner(new FileReader(file), file);
        as = new AssemblerScannerPreProcessor(as);

        PreProcessor pp = new PreProcessor();
        pp.start(as);

        Field privateStringField = PreProcessor.class.
                getDeclaredField("iteratorStack");
        privateStringField.setAccessible(true);

        while(!pp.hasNext()){
            LexSymbol s = pp.next();
            System.out.println(s.toString());
            System.out.println(((IteratorStack<LexSymbol>)privateStringField.get(pp)).getCurrentStackSize());
        }
    }

}