package org.parker.retargetableassembler.preprocessor.expressions;

import org.parker.retargetableassembler.preprocessor.lex.jflex.LexSymbol;

public interface Context {

    void setVariable(String id, Object value);
    Object getVariable(String id);
    boolean hasVariable(String id);

    Object evaluateFunction(LexSymbol caller, int num, Object[] evaluate);
    boolean hasFunction(String id, int num);

    Object evaluateTypeCast(LexSymbol caller, Object evaluate);
    boolean hasTypeCast(String id);
}
