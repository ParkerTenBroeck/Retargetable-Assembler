package org.parker.retargetableassembler.pipe.preprocessor.expressions;

public interface Context {

    void setVariable(String id, Object value);
    Object getVariable(String id);
    boolean hasVariable(String id);

    Object evaluateFunction(String toString, int num, Object[] evaluate);
    boolean hasFunction(String toString, int num);

    Object evaluateTypeCast(String toString, Object evaluate);
    boolean hasTypeCast(String toString);
}
