package org.parker.mips.assembler;

import org.parker.retargetableassembler.base.assembler.linking.LinkType;

public interface MipsLinkType extends LinkType {

    LinkType RELATIVE_WORD = (s, d) -> (d >> 2) - ((s >> 2) + 1);
}
