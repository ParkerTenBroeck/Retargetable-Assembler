package org.parker.retargetableassembler.pipe;

import org.junit.Test;

public class AssemblerMainTest {

    @Test
    public void testArguments(){
        SaveReport report = new SaveReport();
        AssemblerMain.assemble("-i /test.asm \"/test space/wow.asm\"-o /test.o \"/test space/wow.o\" -ga --preinc preincfile.asm", report);
        report.flushToReport(new LoggerReport());
    }

}