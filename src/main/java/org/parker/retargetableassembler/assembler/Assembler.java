package org.parker.retargetableassembler.assembler;

import org.parker.retargetableassembler.Report;
import org.parker.retargetableassembler.preprocessor.PreProcessor;

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
