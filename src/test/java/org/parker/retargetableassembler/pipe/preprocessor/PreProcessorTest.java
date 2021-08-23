package org.parker.retargetableassembler.pipe.preprocessor;

import org.junit.Test;
import org.parker.retargetableassembler.pipe.LoggerReport;
import org.parker.retargetableassembler.pipe.SaveReport;
import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.AssemblerScanner;
import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.AssemblerScannerPreProcessor;
import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.LexSymbol;

import java.io.File;
import java.io.FileReader;
import java.util.Iterator;

public class PreProcessorTest {

    @Test
    public void test() throws Exception {
        SaveReport report = new SaveReport();

        File file = new File("src/test/resources/MIPS/PreProcessor/test2.asm");
        Iterator<LexSymbol> as = new AssemblerScanner(new FileReader(file), file);
        as = new AssemblerScannerPreProcessor(as);

        PreProcessor pp = new PreProcessor();
        pp.setReport(report);
        pp.start(as);

        while(!pp.hasNext()){
            LexSymbol s = pp.next();
            System.out.println(s.toString());
        }

        report.flushToReport(new LoggerReport());

    }

}