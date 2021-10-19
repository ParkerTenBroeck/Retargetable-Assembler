package org.parker.retargetableassembler.preprocessor;

import org.junit.Test;
import org.parker.retargetableassembler.LoggerReport;
import org.parker.retargetableassembler.SaveReport;
import org.parker.retargetableassembler.preprocessor.lex.jflex.AssemblerScanner;
import org.parker.retargetableassembler.preprocessor.lex.jflex.AssemblerScannerPreProcessor;
import org.parker.retargetableassembler.preprocessor.lex.jflex.LexSymbol;

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
        try {
            while (!pp.hasNext()) {
                LexSymbol s = pp.next();
                System.out.println(s.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        report.flushToReport(new LoggerReport());

    }

}