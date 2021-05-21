package org.parker.mips.assembler.instructions.formatter.register;

import org.parker.mips.assembler.instructions.formatter.MipsFormatter;
import org.parker.retargetableassembler.base.assembler.BaseAssembler;
import org.parker.retargetableassembler.instruction.StandardFormattedInstruction;
import org.parker.retargetableassembler.operand.OpRegister;

public enum MipsJumpRFormatter implements MipsFormatter {

    jalr(0b001001),
    jr(0b001000);

    private final int opCode;

    MipsJumpRFormatter(int opCode){
        this.opCode = opCode;
    }

    @Override
    public void encode(byte[] data, StandardFormattedInstruction instruction, BaseAssembler assembler){
        int regs = 0;
        if(instruction.argsLength() == 1){
                if(!(instruction.getArg(0) instanceof OpRegister)){
                    instruction.throwParameterTypeError(0, OpRegister.class);
                }
            regs = ((Number) instruction.getArg(0).getValue()).intValue();
        }else{
            instruction.throwParameterCountError(1);
        }
        //Operand Order o,s,t,d,a,f
        MipsFormatter.encodeRegister(data, new int[]{0,regs, 0, 0, 0, opCode}, assembler);
    }
}
