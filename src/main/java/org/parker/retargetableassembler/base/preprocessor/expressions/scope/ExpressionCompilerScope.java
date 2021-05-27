package org.parker.retargetableassembler.base.preprocessor.expressions.scope;

public interface ExpressionCompilerScope {

    default String preProcessVariableMnemonic(String token){
        return token;
    }
}
