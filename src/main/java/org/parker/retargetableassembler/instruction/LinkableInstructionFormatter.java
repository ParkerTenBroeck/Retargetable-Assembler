package org.parker.retargetableassembler.instruction;

import org.parker.retargetableassembler.base.assembler.linking.LinkType;

public interface LinkableInstructionFormatter extends InstructionFormatter{

    /**
     * Used to return the link types used by each operand
     * @return returns an array that represents the linktype of each operand in the corresponding index
     *
     */
    default LinkType[] getLinkTypes(){
        return new LinkType[0];
    }
}
