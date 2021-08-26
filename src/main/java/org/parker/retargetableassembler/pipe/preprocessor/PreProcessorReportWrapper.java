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
        report.reportError(unexpectedToken.getFile().getPath() + " " +
                "line:" + (unexpectedToken.getLine() + 1) + " " +
                "column:" + (unexpectedToken.getColumn() + 1) + " " +
                "Error: Unexpected token '" + LexSymbol.terminalNames[unexpectedToken.sym] + "'");
    }

    public void unexpectedTokenError(LexSymbol unexpectedToken, int expected){
        report.reportError(unexpectedToken.getFile().getPath() + " " +
                "line:" + (unexpectedToken.getLine() + 1) + " " +
                "column:" + (unexpectedToken.getColumn() + 1) + " " +
                "Error: Unexpected token '" + LexSymbol.terminalNames[unexpectedToken.sym] + "' " +
                "Expected: '" + LexSymbol.terminalNames[expected] + "'");
    }

    public void reportError(LexSymbol cause){
        report.reportError(cause.getFile().getPath() + " " +
                "line:" + (cause.getLine() + 1) + " " +
                "column:" + (cause.getColumn() + 1) + " " +
                "Error:");
    }

    public void reportError(String message, LexSymbol cause){
        report.reportError(cause.getFile().getPath() + " " +
                "line:" + (cause.getLine() + 1) + " " +
                "column:" + (cause.getColumn() + 1) + " " +
                "Error: " + message);
    }

    public void reportError(String message, Collection<LexSymbol> causeCollection){
        LexSymbol cause = LexSymbol.combine(LexSymbol.IDENTIFIER, null, causeCollection);
        report.reportError(cause.getFile().getPath() + " " +
                "line:" + (cause.getLine() + 1) + " " +
                "column:" + (cause.getColumn() + 1) + " " +
                "Error: " + message);
    }

    public void reportWarning(String message, LexSymbol cause) {
        report.reportWarning(cause.getFile().getPath() + " " +
            "line:" + (cause.getLine() + 1) + " " +
            "column:" + (cause.getColumn() + 1) + " " +
            "Warning: " + message);
    }

    public void reportMessage(String message) {
        report.reportMessage("Message: " + message);
    }

    public Report getReport() {
        return report;
    }
}
