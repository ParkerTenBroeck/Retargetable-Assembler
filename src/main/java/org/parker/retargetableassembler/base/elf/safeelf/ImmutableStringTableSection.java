package org.parker.retargetableassembler.base.elf.safeelf;

public final class ImmutableStringTableSection extends SectionDataInterpreter{

    private byte[] data = new byte[0];


    @Override
    public byte[] flushData() {
        return new byte[0];
    }

    public String getString(int index){
        int size = 0;
        while((index + size) < data.length && data[index] != 0){
            size ++;
        }
        return new String(data, index, size);
    }

    @Override
    public void loadData() {
        data = parentSection.getData();
    }

    @Override
    public long getDataSize() {
        return 0;
    }
}
