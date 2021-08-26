package org.parker.retargetableassembler.pipe.preprocessor.expressions;

public abstract class Context {
    public abstract Object getVariable(String value);
    public abstract boolean hasVariable(String value);

    public abstract Object evaluateFunction(String toString, int num, Object[] evaluate);
    public abstract boolean hasFunction(String toString, int num);

    public abstract Object evaluateTypeCast(String toString, Object evaluate);
    public abstract boolean hasTypeCast(String toString);
}
