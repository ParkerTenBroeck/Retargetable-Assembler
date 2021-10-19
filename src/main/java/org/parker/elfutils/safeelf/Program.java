package org.parker.elfutils.safeelf;

import org.parker.elfutils.Elf;

public class Program {

    public Section[] segment;

    Elf.ProgramHeader toProgramHeader(){
        return new Elf.ProgramHeader();
    }
}
