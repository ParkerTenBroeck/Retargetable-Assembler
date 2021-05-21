package org.parker.mips.assembler.instructions.formatter.immediate;

import org.parker.mips.assembler.instructions.formatter.MipsFormatter;
import org.parker.retargetableassembler.base.assembler.BaseAssembler;
import org.parker.retargetableassembler.instruction.StandardFormattedInstruction;
import org.parker.retargetableassembler.operand.OpImmediate;
import org.parker.retargetableassembler.operand.OpRegister;

public enum MipsLoadIFormatter implements MipsFormatter {

    lhi(0b011001),
    llo(0b011000);

    private final int opCode;

    MipsLoadIFormatter(int opCode){
        this.opCode = opCode;
    }

    @Override
    public void encode(byte[] data, StandardFormattedInstruction instruction, BaseAssembler assembler){
        int regt = 0;
        int im = 0;

        if(instruction.argsLength() == 2){
            if(!(instruction.getArg(0) instanceof OpRegister)){
                instruction.throwParameterTypeError(1, OpRegister.class);
            }
            if(!(instruction.getArg(1) instanceof OpImmediate)){
                instruction.throwParameterTypeError(1, OpImmediate.class);
            }
            regt = ((Number) instruction.getArg(0).getValue()).intValue();
            im = ((Number) instruction.getArg(1).getValue()).intValue();
        }else{
            instruction.throwParameterCountError(2);
        }
        //Operand Order o,s,t,i
        MipsFormatter.encodeImmediate(data, new int[]{opCode, 0, regt, im}, assembler);
    }
}
