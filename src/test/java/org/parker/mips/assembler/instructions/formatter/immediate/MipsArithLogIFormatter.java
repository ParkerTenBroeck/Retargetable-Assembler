package org.parker.mips.assembler.instructions.formatter.immediate;

import org.parker.mips.assembler.instructions.formatter.MipsFormatter;
import org.parker.retargetableassembler.base.assembler.BaseAssembler;
import org.parker.retargetableassembler.instruction.StandardFormattedInstruction;
import org.parker.retargetableassembler.operand.OpImmediate;
import org.parker.retargetableassembler.operand.OpRegister;

public enum MipsArithLogIFormatter implements MipsFormatter {

    addi(0b001000),
    addiu(0b001001),
    andi(0b001100),
    ori(0b001101),
    xori(0b001110),
    slti(0b001010),
    sltiu(0b001001);

    private final int opCode;

    MipsArithLogIFormatter(int opCode) {
        this.opCode = opCode;
    }

    @Override
    public void encode(byte[] data, StandardFormattedInstruction instruction, BaseAssembler assembler) {
        int regt = 0;
        int regs = 0;
        int im = 0;

        if (instruction.argsLength() == 3) {
            if (!(instruction.getArg(0) instanceof OpRegister)) {
                instruction.throwParameterTypeError(0, OpRegister.class);
            }
            if (!(instruction.getArg(1) instanceof OpRegister)) {
                instruction.throwParameterTypeError(1, OpRegister.class);
            }
            if (!(instruction.getArg(2) instanceof OpImmediate)) {
                instruction.throwParameterTypeError(2, OpImmediate.class);
            }
            regt = ((Number) instruction.getArg(0).getValue()).intValue();
            regs = ((Number) instruction.getArg(1).getValue()).intValue();
            im = ((Number) instruction.getArg(2).getValue()).intValue();
        } else {
            instruction.throwParameterCountError(3);
        }
        //Operand Order o,s,t,i
        MipsFormatter.encodeImmediate(data, new int[]{opCode, regs, regt, im}, assembler);
    }
}
