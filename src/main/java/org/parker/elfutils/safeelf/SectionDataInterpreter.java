package org.parker.elfutils.safeelf;

public abstract class SectionDataInterpreter {

    Section parentSection;

    public abstract byte[] flushData();
    public abstract long getDataSize();
    public abstract void loadData();

}
