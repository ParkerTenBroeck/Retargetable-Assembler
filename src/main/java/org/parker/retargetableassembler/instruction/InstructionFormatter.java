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
package org.parker.retargetableassembler.instruction;

import org.parker.retargetableassembler.base.assembler.BaseAssembler;
import org.parker.retargetableassembler.base.linker.Symbol;
import org.parker.retargetableassembler.operand.*;
import org.parker.retargetableassembler.util.IntegerRegister;
import org.parker.retargetableassembler.util.Register;
import org.parker.retargetableassembler.util.StringRegister;

public interface InstructionFormatter {

    int getInstructionSize(StandardFormattedInstruction instruction, BaseAssembler assembler);

    void encode(byte[] data, StandardFormattedInstruction instruction, BaseAssembler assembler);

    default Operand evaluateOperand(int index, Object result, StandardFormattedInstruction instruction, BaseAssembler assembler){
        //registers
        if(result instanceof Register){
            if(result instanceof IntegerRegister) {
                return new OpIntegerRegister(((IntegerRegister) result).getRegisterValue());
            }else if(result instanceof StringRegister){
                return new OpStringRegister(((StringRegister) result).getRegisterValue());
            }else{
                instruction.throwUnexpectedParameterTypeException(index, result.getClass());
            }
            //integers
        }else if(result instanceof Integer || result instanceof Long || result instanceof Byte || result instanceof Short){
            return new OpImmediate(((Number) result).longValue());
            //labels
        }else if(result instanceof Symbol){
            return new OpSymbol(((Symbol) result));
        }else{
            instruction.throwUnexpectedParameterTypeException(index, result.getClass());
        }
        //this should never be reached
        return null;
    }
}
