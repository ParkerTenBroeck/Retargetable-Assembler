package org.parker.mips.assembler.instructions.formatter.jump;

import org.parker.mips.assembler.MipsLinkType;
import org.parker.mips.assembler.instructions.formatter.MipsFormatter;
import org.parker.retargetableassembler.base.assembler.BaseAssembler;
import org.parker.retargetableassembler.instruction.LinkableInstructionFormatter;
import org.parker.retargetableassembler.instruction.StandardFormattedInstruction;
import org.parker.retargetableassembler.operand.OpLong;
import org.parker.retargetableassembler.base.linker.LinkType;

public enum MipsJumpFormatter implements MipsFormatter, LinkableInstructionFormatter {

    j(0b000010),
    jal(0b000011);

    private final int opCode;

    MipsJumpFormatter(int opCode){
        this.opCode = opCode;
    }

    @Override
    public void encode(byte[] data, StandardFormattedInstruction instruction, BaseAssembler assembler){
        int im = 0;

        if(instruction.argsLength() == 1){
                if(!(instruction.getArg(0) instanceof OpLong)){
                    instruction.throwParameterTypeError(0,OpLong.class);
                }
            im = ((Number) instruction.getArg(0).getValue()).intValue();
        }else{
            instruction.throwParameterCountError(1);
        }
        //Operand Order o,i
        MipsFormatter.encodeJump(data, new int[]{opCode, im}, assembler);
    }

    @Override
    public LinkType[] getLinkTypes() {
        return new LinkType[]{MipsLinkType.RELATIVE_WORD};
    }


}
