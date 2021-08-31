package org.parker.retargetableassembler.pipe.preprocessor.expressions;

public abstract class Context {

    public abstract void setVariable(String id, Object value);
    public abstract Object getVariable(String id);
    public abstract boolean hasVariable(String id);

    public abstract Object evaluateFunction(String toString, int num, Object[] evaluate);
    public abstract boolean hasFunction(String toString, int num);

    public abstract Object evaluateTypeCast(String toString, Object evaluate);
    public abstract boolean hasTypeCast(String toString);
}
