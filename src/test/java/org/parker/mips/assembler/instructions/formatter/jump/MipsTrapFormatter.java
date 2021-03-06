package org.parker.mips.assembler.instructions.formatter.jump;

import org.parker.mips.assembler.instructions.formatter.MipsFormatter;
import org.parker.retargetableassembler.base.assembler.BaseAssembler;
import org.parker.retargetableassembler.instruction.StandardFormattedInstruction;
import org.parker.retargetableassembler.operand.OpImmediate;

public enum MipsTrapFormatter implements MipsFormatter {

    trap(0b011010);

    private final int opCode;

    MipsTrapFormatter(int opCode){
        this.opCode = opCode;
    }

    @Override
    public void encode(byte[] data, StandardFormattedInstruction instruction, BaseAssembler assembler){
        int im = 0;

        if(instruction.argsLength() == 1){
                if(!(instruction.getArg(0) instanceof OpImmediate)){
                    instruction.throwParameterTypeError(0, OpImmediate.class);
                }
            im = ((Number) instruction.getArg(0).getValue()).intValue();
        }else{
            instruction.throwParameterCountError(1);
        }
        //Operand Order o,i
        MipsFormatter.encodeJump(data, new int[]{opCode, im}, assembler);
    }
}

