package org.parker.retargetableassembler.base.elf.safeelf;

import org.parker.retargetableassembler.base.elf.Elf;

import java.util.HashMap;

public class SafeElf {

    boolean e64;
    boolean littleEndian;


    Section[] sections;
    Program[] programs;

    Elf parentElf;

    public SafeElf(Elf elf, boolean loadAllAsSafe){

    }

    public SafeElf(){

    }

    Elf toElf(){
        Elf elf = new Elf();
        return elf;
    }
}
