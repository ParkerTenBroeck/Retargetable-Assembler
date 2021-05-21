package org.parker.mips.assembler.instructions.formatter.register;

import org.parker.mips.assembler.instructions.formatter.MipsFormatter;
import org.parker.retargetableassembler.base.assembler.BaseAssembler;
import org.parker.retargetableassembler.instruction.StandardFormattedInstruction;
import org.parker.retargetableassembler.operand.OpRegister;

public enum MipsMoveToFormatter implements MipsFormatter {

    mthi(0b010001),
    mtlo(0b010011);

    private final int opCode;

    MipsMoveToFormatter(int opCode){
        this.opCode = opCode;
    }

    @Override
    public void encode(byte[] data, StandardFormattedInstruction instruction, BaseAssembler assembler){
        int regs = 0;
        if(instruction.argsLength() == 3){
                if(!(instruction.getArg(0) instanceof OpRegister)){
                    instruction.throwParameterTypeError(0, OpRegister.class);
                }
            regs = ((Number) instruction.getArg(0).getValue()).intValue();
        }else{
            instruction.throwParameterCountError(3);
        }
        //Operand Order o,s,t,d,a,f
        MipsFormatter.encodeRegister(data, new int[]{0,regs, 0, 0, 0, opCode}, assembler);
    }
}
