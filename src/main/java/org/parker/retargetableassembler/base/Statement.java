/*
 *    Copyright 2021 ParkerTenBroeck
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.parker.retargetableassembler.base;

import org.parker.retargetableassembler.base.preprocessor.expressions.scope.ExpressionScope;
import org.parker.retargetableassembler.exception.assembler.AssemblerError;
import org.parker.retargetableassembler.exception.assembler.ParameterCountError;
import org.parker.retargetableassembler.exception.assembler.ParameterTypeError;
import org.parker.retargetableassembler.base.preprocessor.expressions.CompiledExpression;
import org.parker.retargetableassembler.base.preprocessor.util.Line;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Any Line in assembly source that is not a comment
 */
@SuppressWarnings("unused")
public abstract class Statement<ArgType> implements Serializable {

    private CompiledExpression[] argExpressions = null;
    private ArgType[] args = null;
    private boolean[] evaluated = null;
    private Line line;

    public final int argsLength(){
        return argExpressions.length;
    }

    public final ArgType getArg(int index){
        if(!evaluated[index]) {
            try {
                args[index] = evaluateArgument(index, argExpressions[index].evaluate());
            }catch (Exception e){
                throw new AssemblerError("Failed to evaluate operand: " + (index + 1), argExpressions[index].line, argExpressions[index].startingAddress, argExpressions[index].endingAddress, e);
            }
            evaluated[index] = true;
            argExpressions[index] = null;
        }
        return args[index];
    }

    public final Line getLine(){
        return this.line;
    }

    @SuppressWarnings("unchecked")
    protected ArgType evaluateArgument(int index, Object result){
        return (ArgType) result;
    }

    @SuppressWarnings("unchecked")
    public final void setArgExpressions(CompiledExpression[] expressions, Line line){
        assert argExpressions == null;
        assert expressions != null;

        this.argExpressions = expressions;
        evaluated = new boolean[expressions.length];
        Arrays.fill(evaluated, false);
        args = (ArgType[]) new Object[expressions.length];
        this.line = line;
    }

    public final void setExpressionScope(ExpressionScope scope){
        for(int i = 0; i < argExpressions.length; i ++){
            if(argExpressions[i] != null){
                argExpressions[i].setExpressionScope(scope);
            }
        }
    }


    public final void throwParameterTypeError(int i, Class<?> expected) {
        throw new ParameterTypeError("Wrong type for parameter: " + (i + 1) + " expected: " + expected.getSimpleName() + " got: " + getArg(i).getClass().getSimpleName(), argExpressions[i].line, argExpressions[i].startingAddress, argExpressions[i].endingAddress);
    }

    public final void throwParameterCountError(int expected) {
        if(argExpressions.length == 0){
            throw new ParameterCountError("Unexpected number of arguments, expected: " + expected + " gotten: " + argExpressions.length, line, -1, -1);
        }else{
            throw new ParameterCountError("Unexpected number of arguments, expected: " + expected + " gotten: " + argExpressions.length, line, argExpressions[0].startingAddress, argExpressions[argExpressions.length - 1].endingAddress);
        }

    }

    public final void throwUnexpectedParameterTypeException(int index, Class<?> got) {
        throw new ParameterTypeError("Unexpected type for parameter: " + (index + 1) + " got: " + got.getSimpleName(), argExpressions[index].line, argExpressions[index].startingAddress, argExpressions[index].endingAddress);
    }

    protected void throwParameterLinkingException(int i, Exception e){
        throw new AssemblerError("Failed to link argument: " + i, argExpressions[i].line, argExpressions[i].startingAddress, argExpressions[i].endingAddress, e);
    }

    protected final void throwInstructionFormatterException(String message){
        throw new AssemblerError(message, this.line);
    }
}
