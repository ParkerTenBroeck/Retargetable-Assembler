package org.parker.retargetableassembler;

import org.parker.retargetableassembler.preprocessor.lex.jflex.LexSymbol;

public interface Report {

    enum ReportLevel{
        codeError,
        error,
        warning,
        message
    }

    default void reportCodeError(String message){
        reportCodeError(message, null, null);
    }
    default void reportCodeError(Exception e){
        reportCodeError("", e, null);
    }
    default void reportCodeError(String message, Exception e){
        reportCodeError(message, e, null);
    }

    default void reportCodeError(String message, LexSymbol parent){
        reportCodeError(message, null, parent);
    }
    default void reportCodeError(Exception e, LexSymbol parent){
        reportCodeError("", e, parent);
    }
    default void reportCodeError(String message, Exception e, LexSymbol parent){
        report(message, e, parent, ReportLevel.codeError);
    }

    default void reportError(String message, LexSymbol parent){
        report(message, null, parent, ReportLevel.error);
    }
    default void reportWarning(String message, LexSymbol parent){
        report(message, null, parent, ReportLevel.warning);
    }
    default void reportMessage(String message, LexSymbol parent){
        report(message, null, parent, ReportLevel.message);
    }

    default void reportError(String message){
        reportError(message, null);
    }

    default void reportWarning(String message){
        reportWarning(message, null);
    }
    default void reportMessage(String message){
        reportMessage(message, null);
    }


    void report(String message, Exception e, LexSymbol parent, ReportLevel level);
}
