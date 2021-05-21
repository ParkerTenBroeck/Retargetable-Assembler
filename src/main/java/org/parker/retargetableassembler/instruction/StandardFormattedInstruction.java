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

import org.parker.retargetableassembler.base.StatementAssociatedData;
import org.parker.retargetableassembler.operand.*;
import org.parker.retargetableassembler.base.assembler.BaseAssembler;
import org.parker.retargetableassembler.exception.assembler.AssemblerError;

public class StandardFormattedInstruction extends StatementAssociatedData<Operand> {

    final InstructionFormatter isf;
    final BaseAssembler assembler;
    private byte[] data;

    public StandardFormattedInstruction(InstructionFormatter isf, BaseAssembler assembler) {
        this.isf = isf;
        this.assembler = assembler;
    }

    @Override
    protected Operand evaluateArgument(int index, Object result) {
        return isf.evaluateOperand(index, result, this, assembler);
    }

    @Override
    public byte[] toBinary() {
        if(data == null){
            data = new byte[(int) getSize()];
            try {
                isf.encode(data, this, assembler);
            }catch (Exception e){
                throw new AssemblerError("Failed to convert instruction to bytes", getLine(), e);
            }
        }
        return data;
    }

    @Override
    public long getSize() {
        return isf.getInstructionSize(this, assembler);
    }
}
