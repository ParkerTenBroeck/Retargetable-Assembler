package org.parker.retargetableassembler.base.linker;

import org.parker.retargetableassembler.base.Data;

import java.io.*;
import java.util.List;

public class Section implements Serializable {

    public String name;
    public File file;
    public long address;
    public int alignment;
    public long size;
    //public List<Symbol> sectionSymbols;
    public List<Data> sectionData;

}
