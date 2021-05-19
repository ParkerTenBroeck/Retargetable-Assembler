package org.parker.retargetableassembler.exception.preprocessor.expression;

public class ParseVariableMnemonicError extends RuntimeException{

    public ParseVariableMnemonicError(){
        super();
    }

    public ParseVariableMnemonicError(String message){
        super(message);
    }

    public ParseVariableMnemonicError(String message, Exception e){
        super(message, e);
    }
}
