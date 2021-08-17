package org.parker.retargetableassembler.base.linker;

import java.io.Serializable;

public class SymbolVisibility implements Serializable {

    public final static SymbolType DEFAULT = new SymbolType("default");
    public final static SymbolType INTERNAL = new SymbolType("internal");
    public final static SymbolType PROTECTED = new SymbolType("protected");
    public final static SymbolType EXPORTED = new SymbolType("exported");
    public final static SymbolType SINGLETON = new SymbolType("singleton");
    public final static SymbolType ELIMINATED = new SymbolType("eliminated");

    public final String name;

    public  SymbolVisibility (String name){
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof SymbolVisibility){
            return this.name.equals(((SymbolVisibility) obj).name);
        }else{
            return false;
        }
    }
}
