package org.parker.retargetableassembler.pipe;

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
    public void reportCodeError(String message) {
        reports.forEach(report -> report.reportCodeError(message));
    }

    @Override
    public void reportCodeError(Exception e) {
        reports.forEach(report -> report.reportCodeError(e));
    }

    @Override
    public void reportCodeError(String message, Exception e) {
        reports.forEach(report -> report.reportCodeError(message, e));
    }

    @Override
    public void reportError(String message) {
        reports.forEach(report -> report.reportError(message));
    }

    @Override
    public void reportWarning(String message) {
        reports.forEach(report -> report.reportWarning(message));
    }

    @Override
    public void reportMessage(String message) {
        reports.forEach(report -> report.reportWarning(message));
    }
}
