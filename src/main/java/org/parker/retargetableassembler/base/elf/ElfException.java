package org.parker.retargetableassembler.base.elf;

public class ElfException extends RuntimeException{
    public ElfException(String message){
        super(message);
    }
    public ElfException(String message, Exception cause){
        super(message, cause);
    }
    public ElfException(Exception cause){
        super(cause);
    }
}
