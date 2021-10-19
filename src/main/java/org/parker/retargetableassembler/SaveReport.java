package org.parker.retargetableassembler;


import org.parker.retargetableassembler.preprocessor.lex.jflex.LexSymbol;

import java.util.ArrayList;

public class SaveReport implements Report {

    private ArrayList<SavedReport> savedReports = new ArrayList<>();

    public synchronized void flushToReport(Report report){
        for(int i = 0; i < savedReports.size(); i ++){
            SavedReport r = savedReports.get(i);
            report.report(r.message, r.e, r.parent, r.level);
        }
        savedReports.clear();
    }

    public synchronized void clear(){
        savedReports.clear();
    }

    @Override
    public void report(String message, Exception e, LexSymbol parent, ReportLevel level) {
        savedReports.add(new SavedReport(message, e, parent, level));
    }

    private static class SavedReport{
        private String message;
        private Exception e;
        private LexSymbol parent;
        private ReportLevel level;

        public SavedReport(String message, Exception e, LexSymbol parent, ReportLevel level) {
            this.message = message;
            this.e = e;
            this.parent = parent;
            this.level = level;
        }
    }
}
