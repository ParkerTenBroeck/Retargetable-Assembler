package org.parker.retargetableassembler.preprocessor.expressions;

import org.parker.retargetableassembler.preprocessor.PreProcessorReportWrapper;
import org.parker.retargetableassembler.preprocessor.lex.jflex.LexSymbol;

import java.util.List;

public abstract class CompiledExpression {

    private transient Context context;
    private transient PreProcessorReportWrapper report;

    public abstract Object evaluateExpression();

    public Context getContext(){
        return this.context;
    }
    public void setContext(Context context){
        this.context = context;
    }

    public PreProcessorReportWrapper getReport(){
        return this.report;
    }
    public void setReport(PreProcessorReportWrapper report){
        this.report = report;
    }

    public abstract boolean isValid();

    public abstract List<LexSymbol> toSymbols();
}
