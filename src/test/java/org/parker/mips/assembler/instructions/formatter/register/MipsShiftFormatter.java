package org.parker.mips.assembler.instructions.formatter.register;

import org.parker.mips.assembler.MipsRegister;
import org.parker.mips.assembler.instructions.formatter.MipsFormatter;
import org.parker.retargetableassembler.base.assembler.BaseAssembler;
import org.parker.retargetableassembler.instruction.StandardFormattedInstruction;
import org.parker.retargetableassembler.operand.OpImmediate;
import org.parker.retargetableassembler.operand.OpRegister;

public enum MipsShiftFormatter implements MipsFormatter {

    sll(0b000000),
    sra(0b000011),
    srl(0b000010);

    private final int opCode;

    MipsShiftFormatter(int opCode){
        this.opCode = opCode;
    }

    @Override
    public void encode(byte[] data, StandardFormattedInstruction instruction, BaseAssembler assembler){
        int regd = 0;
        int regt = 0;
        int a = 0;
        if(instruction.argsLength() == 3){

                if(!(instruction.getArg(0) instanceof OpRegister)){
                    instruction.throwParameterTypeError(0, OpRegister.class);
                }else if(!(instruction.getArg(1) instanceof OpRegister)){
                    instruction.throwParameterTypeError(1, OpRegister.class);
                }else if(!(instruction.getArg(2) instanceof OpImmediate)){
                    instruction.throwParameterTypeError(2, OpImmediate.class);
                }
            regd = ((Number) instruction.getArg(0).getValue()).intValue();
            regt = ((Number) instruction.getArg(1).getValue()).intValue();
            a = ((Number) instruction.getArg(2).getValue()).intValue();
        }else{
            instruction.throwParameterCountError(3);
        }
        //Operand Order o,s,t,d,a,f
        MipsFormatter.encodeRegister(data, new int[]{0,0, regt, regd, a, opCode}, assembler);
    }
}
