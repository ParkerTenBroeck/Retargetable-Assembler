package org.parker.retargetableassembler.util;

public class StringRegister implements Register{
    public final String registerNumber;

    public StringRegister(String registerNumber){
        this.registerNumber = registerNumber;
    }

    @Override
    public String getRegisterValue() {
        return registerNumber;
    }
}
