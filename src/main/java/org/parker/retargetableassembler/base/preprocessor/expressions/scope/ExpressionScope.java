package org.parker.retargetableassembler.base.preprocessor.expressions.scope;

import org.parker.retargetableassembler.exception.preprocessor.expression.ParseVariableMnemonicError;

public interface ExpressionScope {

    default Object parseMemberAccess(Object parseVariable, String memberAccess) {
        throw new IllegalArgumentException("Cannot access member: " + memberAccess + " on: " + parseVariable.getClass().getSimpleName());
    }

    default Object parseVariable(String token){
        throw new ParseVariableMnemonicError("Variable: " + token + " not found");
    }

    default Object parseFunction(String token, Object parms){
        throw new ParseVariableMnemonicError("Function: " + token + " not found");
    }
}
