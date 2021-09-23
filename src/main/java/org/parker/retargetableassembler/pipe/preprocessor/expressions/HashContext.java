package org.parker.retargetableassembler.pipe.preprocessor.expressions;

import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.LexSymbol;

import java.util.HashMap;

public class HashContext implements Context{

    private HashMap<String, Object> variables = new HashMap<>();
    private HashMap<String, Func> functions = new HashMap<>();
    private HashMap<String, Cast> casts = new HashMap<>();

    @Override
    public void setVariable(String id, Object value) {
        variables.put(id, value);
    }

    @Override
    public Object getVariable(String id) {
        return variables.get(id);
    }

    @Override
    public boolean hasVariable(String id) {
        return variables.containsKey(id);
    }

    @Override
    public Object evaluateFunction(LexSymbol caller, int num, Object[] evaluate) {
        return functions.get(caller.getValue() + "\0\0SPECIAL\0\0" + num).evaluate(caller, evaluate);
    }

    @Override
    public boolean hasFunction(String toString, int num) {
        return functions.containsKey(toString + "\0\0SPECIAL\0\0" + num);
    }

    public void addFunction(String toString, int numArgs, Func func){
        functions.put(toString + "\0\0SPECIAL\0\0" + numArgs, func);
    }

    @Override
    public Object evaluateTypeCast(LexSymbol caller, Object evaluate) {
        return casts.get(caller.getValue()).evaluate(caller, evaluate);
    }

    public void addTypeCast(String toString, Cast cast){
        casts.put(toString, cast);
    }

    @Override
    public boolean hasTypeCast(String toString) {
        return casts.containsKey(toString);
    }

    public static interface Func {
        Object evaluate(LexSymbol caller, Object[] vals);
    }

    public static interface Cast{
        Object evaluate(LexSymbol caller, Object val);
    }
}
