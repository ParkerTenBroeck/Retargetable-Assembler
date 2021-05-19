package org.parker.mips;

import com.google.common.io.Files;
import org.junit.Assert;

import org.junit.Test;
import org.parker.mips.assembler.MipsAssembler;
import org.parker.mips.assembler.MipsPreProcessor;
import org.parker.retargetableassembler.base.preprocessor.statements.PreProcessedStatement;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MipsPreProcessorTest {


    private static class FilesToCompare {
        public File a;
        public File s;
    }

    @Test
    public void mipsPreProcessor(){
        MipsPreProcessor pp =  new MipsPreProcessor(new MipsAssembler());

        File[] allFiles = new File("src/test/resources/MIPS/PreProcessor/Comparison").listFiles();

        HashMap<String, FilesToCompare> testFileNameToFilesToCompare = new HashMap<>();

        for(File f: allFiles){
            FilesToCompare te = testFileNameToFilesToCompare.getOrDefault(Files.getNameWithoutExtension(f.getName()), new FilesToCompare());

            if(Files.getFileExtension(f.getName()).equals("asm")){
                te.a = f;
            }else if(Files.getFileExtension(f.getName()).equals("s")){
                te.s = f;
            }
            testFileNameToFilesToCompare.put(Files.getNameWithoutExtension(f.getName()), te);
        }

        for(Map.Entry<String, FilesToCompare> c:testFileNameToFilesToCompare.entrySet()){
            List<PreProcessedStatement> a = pp.preprocess(c.getValue().a);
            List<PreProcessedStatement> s = pp.preprocess(c.getValue().s);
            Assert.assertEquals("Not same size: ", a.size(), s.size());
            for(int i =0; i < a.size(); i ++){
                Assert.assertEquals("Line on a: " +
                        a.get(i).getLine().getLineNumber() +
                        " and line on s: " +
                        s.get(i).getLine().getLineNumber()
                        +" does not match", a.get(i).toString(), s.get(i).toString());
            }
        }
    }

}