package org.parker.mips;


import org.junit.Test;
import org.parker.mips.assembler.MipsAssembler;
import org.parker.retargetableassembler.base.assembler.Assembler;

import java.io.File;

public class MipsAssemblerTest {


    @Test
    public void mipsAssembler() {

        Assembler a = new MipsAssembler();

        File testDirectory = new File("src/test/resources/MIPS/Assembly/Linking Across Files");

        File[] files = testDirectory.listFiles();

        for(int i = 0; i < files.length; i ++ ) {
            if(files[i].isFile()){
                a.assemble(new File[]{files[i]});
            }else if(files[i].isDirectory()){
                a.assemble(files[i].listFiles());
            }
        }
    }
}