package org.parker.retargetableassembler.pipe;


import java.util.ArrayList;

public class SaveReport implements Report {

    private ArrayList<SavedReport> savedReports = new ArrayList<>();

    public enum ReportLevel{
        codeError,
        error,
        warning,
        message
    }

    public synchronized void flushToReport(Report report){
        for(int i = 0; i < savedReports.size(); i ++){
            SavedReport r = savedReports.get(i);
            switch (r.level){
                case codeError:
                    if(r.e != null){
                        if(r.message != null){
                            report.reportCodeError(r.message, r.e);
                        }else{
                            report.reportCodeError(r.e);
                        }
                    }else{
                        report.reportCodeError(r.message);
                    }
                    break;
                case error:
                    report.reportError(r.message);
                    break;
                case warning:
                    report.reportWarning(r.message);
                    break;
                case message:
                    report.reportMessage(r.message);
                    break;
            }
        }
        savedReports.clear();
    }

    public synchronized void clear(){
        savedReports.clear();
    }

    @Override
    public void reportCodeError(String message) {
        savedReports.add(new SavedReport(message, ReportLevel.codeError));
    }

    @Override
    public void reportCodeError(Exception e) {
        savedReports.add(new SavedReport(e, ReportLevel.codeError));
    }

    @Override
    public void reportCodeError(String message, Exception e) {
        savedReports.add(new SavedReport(message, ReportLevel.codeError));
    }

    @Override
    public void reportError(String message) {
        savedReports.add(new SavedReport(message, ReportLevel.warning));
    }

    @Override
    public void reportWarning(String message) {
        savedReports.add(new SavedReport(message, ReportLevel.warning));
    }

    @Override
    public void reportMessage(String message) {
        savedReports.add(new SavedReport(message, ReportLevel.message));
    }

    private static class SavedReport{
        private String message;
        private Exception e;
        private ReportLevel level;

        public SavedReport(String message, ReportLevel level) {
            this.message = message;
            this.level = level;
        }

        public SavedReport(Exception e, ReportLevel level) {
            this.e = e;
            this.level = level;
        }

        public SavedReport(String message, Exception e, ReportLevel level) {
            this.message = message;
            this.e = e;
            this.level = level;
        }
    }
}
