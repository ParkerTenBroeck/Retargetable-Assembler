package org.parker.retargetableassembler.util;

public class IntegerRegister implements Register{

    public final int registerNumber;

    public IntegerRegister(int registerNumber){
        this.registerNumber = registerNumber;
    }

    @Override
    public Integer getRegisterValue() {
        return registerNumber;
    }
}
