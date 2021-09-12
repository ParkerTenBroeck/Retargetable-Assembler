package org.parker.retargetableassembler.pipe;

import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.LexSymbol;

import java.util.ArrayList;

public class MultiReporter implements Report {

    ArrayList<Report> reports = new ArrayList<>();

    public void clear(){
        reports.clear();
    }

    public void addReport(Report report){
        reports.add(report);
    }

    public void removeReport(Report report){
        reports.remove(report);
    }

    @Override
    public void report(String message, Exception e, LexSymbol parent, ReportLevel level) {
        reports.forEach(report -> report.report(message, e, parent, level));
    }
}
