package org.parker.retargetableassembler.operand;

@SuppressWarnings("unused")
public class OpStringRegister extends OpRegister{

    public final String registerValue;

    public OpStringRegister(String registerValue){
        this.registerValue = registerValue;
    }

    @Override
    public String getValue() {
        return registerValue;
    }
}
