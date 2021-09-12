package org.parker.retargetableassembler.pipe.preprocessor;

import org.parker.retargetableassembler.pipe.Report;
import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.LexSymbol;

import java.util.Collection;

public class PreProcessorReportWrapper {
    private final Report report;

    public PreProcessorReportWrapper(Report report){
        this.report = report;
    }

    public void reportCodeError(String message){
        report.reportCodeError(message);
    }


    public void reportCodeError(Exception e){
        report.reportCodeError(e);
    }


    public void reportCodeError(String message, Exception e){
        report.reportCodeError(message, e);
    }

    public void unexpectedTokenError(LexSymbol unexpectedToken){
        report.reportError("Error: Unexpected token '" +
                LexSymbol.terminalNames[unexpectedToken.sym] + "'", unexpectedToken);
    }

    public void unexpectedTokenError(LexSymbol unexpectedToken, int expected){
        report.reportError("Unexpected token: " + "'" + LexSymbol.terminalNames[unexpectedToken.sym] + "'" +
                "Expected: '" + LexSymbol.terminalNames[expected] + "'", unexpectedToken);
    }

    public void reportError(String message, LexSymbol cause){
        report.reportError(message, cause);
    }

    public void reportError(String message, Collection<LexSymbol> causeCollection){
        LexSymbol cause = LexSymbol.combine(LexSymbol.IDENTIFIER, null, causeCollection);
        report.reportError(message, cause);
    }

    public void reportWarning(String message, LexSymbol cause) {
        report.reportWarning(message, cause);
    }

    public void reportMessage(String message) {
        report.reportMessage(message);
    }

    public Report getReport() {
        return report;
    }
}
