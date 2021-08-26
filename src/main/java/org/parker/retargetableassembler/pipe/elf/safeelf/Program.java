package org.parker.retargetableassembler.pipe.elf.safeelf;

import org.parker.retargetableassembler.pipe.elf.Elf;

public class Program {

    public Section[] segment;

    Elf.ProgramHeader toProgramHeader(){
        return new Elf.ProgramHeader();
    }
}
