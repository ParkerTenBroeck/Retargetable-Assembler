package org.parker.retargetableassembler.pipe.elf.symtab;

import org.parker.retargetableassembler.pipe.elf.safeelf.SectionDataInterpreter;

public class SymbolTableSection extends SectionDataInterpreter {

    String stringTableName;


    @Override
    public void loadData() {

    }

    @Override
    public byte[] flushData() {
        return null;
    }

    @Override
    public long getDataSize() {
        return 0;
    }
}
