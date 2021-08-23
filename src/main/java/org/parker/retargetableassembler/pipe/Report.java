package org.parker.retargetableassembler.pipe;

import org.parker.retargetableassembler.util.AssemblerLogLevel;

import java.util.logging.Level;
import java.util.logging.Logger;

public interface Report {

    void reportCodeError(String message);
    void reportCodeError(Exception e);
    void reportCodeError(String message, Exception e);

    void reportError(String message);
    void reportWarning(String message);
    void reportMessage(String message);
}
