package org.parker.mips.assembler.instructions.formatter.register;

import org.parker.mips.assembler.instructions.formatter.MipsFormatter;
import org.parker.retargetableassembler.base.assembler.BaseAssembler;
import org.parker.retargetableassembler.instruction.StandardFormattedInstruction;
import org.parker.retargetableassembler.operand.OpRegister;

public enum MipsMoveFromFormatter implements MipsFormatter {

    mfhi(0b010000),
    mflo(0b010010);

    private final int opCode;

    MipsMoveFromFormatter(int opCode){
        this.opCode = opCode;
    }

    @Override
    public void encode(byte[] data, StandardFormattedInstruction instruction, BaseAssembler assembler){
        int regd = 0;
        if(instruction.argsLength() == 1){
                if(!(instruction.getArg(0) instanceof OpRegister)){
                    instruction.throwParameterTypeError(0, OpRegister.class);
                }
            regd = ((Number) instruction.getArg(0).getValue()).intValue();
        }else{
            instruction.throwParameterCountError(1);
        }
        //Operand Order o,s,t,d,a,f
        MipsFormatter.encodeRegister(data, new int[]{0,0, 0, regd, 0, opCode}, assembler);
    }
}
