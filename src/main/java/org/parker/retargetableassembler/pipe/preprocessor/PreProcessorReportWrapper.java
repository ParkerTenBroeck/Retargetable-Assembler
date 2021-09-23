package org.parker.retargetableassembler.pipe.preprocessor;

import org.parker.retargetableassembler.pipe.Report;
import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.LexSymbol;

import java.util.Collection;

public class PreProcessorReportWrapper implements Report {
    private final Report report;

    public PreProcessorReportWrapper(Report report){
        this.report = report;
    }

    public void unexpectedTokenError(LexSymbol unexpectedToken){
        this.reportError("Unexpected token '" +
                LexSymbol.terminalNames[unexpectedToken.getSym()] + "'", unexpectedToken);
    }

    public void unexpectedTokenError(String message, LexSymbol unexpectedToken){
        this.reportError(message + " Unexpected token '" +
                LexSymbol.terminalNames[unexpectedToken.getSym()] + "'", unexpectedToken);
    }

    public void unexpectedTokenError(LexSymbol unexpectedToken, int expected){
        this.reportError("Unexpected token: " + "'" + LexSymbol.terminalNames[unexpectedToken.getSym()] + "'" +
                "Expected: '" + LexSymbol.terminalNames[expected] + "'", unexpectedToken);
    }

    public void reportError(String message, Collection<LexSymbol> causeCollection){
        LexSymbol cause = LexSymbol.combine(LexSymbol.IDENTIFIER, null, causeCollection);
        this.reportError(message, cause);
    }

    @Override
    public void report(String message, Exception e, LexSymbol parent, ReportLevel level) {
        report.report(message, e, parent, level);
    }

    public Report getReport() {
        return report;
    }
}
