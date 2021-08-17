package org.parker.retargetableassembler.base.elf.safeelf;

import org.parker.retargetableassembler.base.elf.Elf;

public class Program {

    public Section[] segment;

    Elf.ProgramHeader toProgramHeader(){
        return new Elf.ProgramHeader();
    }
}
