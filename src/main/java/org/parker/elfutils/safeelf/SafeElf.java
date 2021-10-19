package org.parker.elfutils.safeelf;

import org.parker.elfutils.Elf;

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
