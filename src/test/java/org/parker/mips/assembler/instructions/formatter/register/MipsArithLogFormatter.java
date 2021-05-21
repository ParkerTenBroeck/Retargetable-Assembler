package org.parker.mips.assembler.instructions.formatter.register;

import org.parker.mips.assembler.instructions.formatter.MipsFormatter;
import org.parker.retargetableassembler.instruction.StandardFormattedInstruction;
import org.parker.retargetableassembler.operand.OpRegister;
import org.parker.retargetableassembler.base.assembler.BaseAssembler;

public enum MipsArithLogFormatter implements MipsFormatter {

    add(0b100000),
    addu(0b100001),
    and(0b100100),
    nor(0b100111),
    or(0b100101),
    sub(0b100010),
    subu(0b100011),
    xor(0b100110),
    slt(0b101010),
    sltu(0b101001);

    private final int opCode;

    MipsArithLogFormatter(int opCode){
        this.opCode = opCode;
    }

    @Override
    public void encode(byte[] data, StandardFormattedInstruction instruction, BaseAssembler assembler){
        int regd = 0;
        int regs = 0;
        int regt = 0;
        if(instruction.argsLength() == 3){
            for(int i = 0; i < instruction.argsLength(); i ++){
                if(!(instruction.getArg(i) instanceof OpRegister)){
                    instruction.throwParameterTypeError(i, OpRegister.class);
                }
            }
            regd = (Integer) instruction.getArg(0).getValue();
            regs = (Integer) instruction.getArg(1).getValue();
            regt = (Integer) instruction.getArg(2).getValue();
        }else{
            instruction.throwParameterCountError(3);
        }
        //Operand Order o,s,t,d,a,f
        MipsFormatter.encodeRegister(data, new int[]{0,regs, regt, regd, 0, opCode}, assembler);
    }
}
