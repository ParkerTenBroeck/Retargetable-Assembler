package org.parker.retargetableassembler.base.elf.safeelf;

public abstract class SectionDataInterpreter {

    Section parentSection;

    public abstract byte[] flushData();
    public abstract long getDataSize();
    public abstract void loadData();

}
