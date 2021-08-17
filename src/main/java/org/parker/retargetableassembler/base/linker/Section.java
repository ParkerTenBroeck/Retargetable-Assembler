package org.parker.retargetableassembler.base.linker;

import org.parker.retargetableassembler.base.Data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Section implements Serializable {

    //always defaults to text if not defined
    public String name = "text";
    public File file;
    //relocatable by default
    public long address = -1;
    //defaults to 1 byte alignment
    public int alignment = 1;
    //no data
    public long size = 0;
    public boolean bigEndian;
    //public List<Symbol> sectionSymbols;
    public List<Data> sectionData = new ArrayList<>();

    public long getSize(){
        return size;
    }

}
