package org.parker.retargetableassembler.pipe.assembler;

import org.parker.retargetableassembler.pipe.Report;
import org.parker.retargetableassembler.pipe.preprocessor.PreProcessor;

public class Assembler {

    private PreProcessor pp;

    public Assembler(PreProcessor pp){
        this.pp = pp;
    }

    public void assemble(){

    }

    public void setReport(Report report){
        pp.setReport(report);
    }

    public void setPreProcessor(PreProcessor pp){
        this.pp = pp;
    }
}
