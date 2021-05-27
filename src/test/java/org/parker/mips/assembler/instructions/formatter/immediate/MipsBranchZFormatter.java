package org.parker.mips.assembler.instructions.formatter.immediate;

import org.parker.mips.assembler.MipsLinkType;
import org.parker.mips.assembler.instructions.formatter.MipsFormatter;
import org.parker.retargetableassembler.base.assembler.BaseAssembler;
import org.parker.retargetableassembler.instruction.LinkableInstructionFormatter;
import org.parker.retargetableassembler.instruction.StandardFormattedInstruction;
import org.parker.retargetableassembler.operand.OpLong;
import org.parker.retargetableassembler.operand.OpRegister;
import org.parker.retargetableassembler.base.linker.LinkType;

public enum MipsBranchZFormatter implements MipsFormatter, LinkableInstructionFormatter {

    bgtz(0b000111),
    blez(0b000110);

    private final int opCode;

    MipsBranchZFormatter(int opCode) {
        this.opCode = opCode;
    }

    @Override
    public void encode(byte[] data, StandardFormattedInstruction instruction, BaseAssembler assembler) {
        int regs = 0;
        int im = 0;

        if (instruction.argsLength() == 2) {
            if (!(instruction.getArg(0) instanceof OpRegister)) {
                instruction.throwParameterTypeError(0, OpRegister.class);
            }
            if (!(instruction.getArg(1) instanceof OpLong)) {
                instruction.throwParameterTypeError(1, OpLong.class);
            }
            regs = ((Number) instruction.getArg(0).getValue()).intValue();
            im = ((Number) instruction.getArg(1).getValue()).intValue();
        } else {
            instruction.throwParameterCountError(2);
        }
        //Operand Order o,s,t,i
        MipsFormatter.encodeImmediate(data, new int[]{opCode, regs, 0, im}, assembler);
    }

    @Override
    public LinkType[] getLinkTypes() {
        return new LinkType[]{null, MipsLinkType.RELATIVE_WORD};
    }
}
