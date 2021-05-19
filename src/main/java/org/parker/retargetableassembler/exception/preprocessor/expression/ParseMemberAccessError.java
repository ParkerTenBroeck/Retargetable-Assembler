package org.parker.retargetableassembler.exception.preprocessor.expression;

public class ParseMemberAccessError extends RuntimeException{

    public ParseMemberAccessError(String message, Exception e){
        super(message, e);
    }
}
