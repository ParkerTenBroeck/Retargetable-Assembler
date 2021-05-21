package org.parker.retargetableassembler.operand;

@SuppressWarnings("unused")
public class OpIntegerRegister extends OpRegister {

    public final int registerValue;

    public OpIntegerRegister(int registerValue){
        this.registerValue = registerValue;
    }

    @Override
    public Integer getValue() {
        return registerValue;
    }
}
