package org.parker.retargetableassembler.base.preprocessor.expressions.scope;

import org.parker.retargetableassembler.base.preprocessor.BasePreProcessor;

public class BasePreProcessorExpressionCompilerScope<P extends BasePreProcessor> implements ExpressionCompilerScope{

    protected P preprocessor;
    public void setPreProcessor(P preprocessor){
        this.preprocessor = preprocessor;
    }

    @Override
    public String preProcessVariableMnemonic(String token) {
        if(preprocessor.isDefinedValue(token)){
            Object d = preprocessor.getDefinedValue(token);
            if(d instanceof String){
                return (String) d;
            }else{
                throw new IllegalArgumentException("Cannot insert: " + d.getClass().getSimpleName());
            }
        }else{
            return ExpressionCompilerScope.super.preProcessVariableMnemonic(token);
        }
    }
}
