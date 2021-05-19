package org.parker.retargetableassembler.exception.preprocessor.expression;

public class ParseFunctionError extends RuntimeException{

    public ParseFunctionError(){
        super();
    }

    public ParseFunctionError(String message){
        super(message);
    }

    public ParseFunctionError(String message, Exception e){
        super(message, e);
    }

}
