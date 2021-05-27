package org.parker.retargetableassembler.base.linker;

import java.io.Serializable;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class SymbolType implements Serializable {

     public final static SymbolType NOTYPE = new SymbolType("notype");
     public final static SymbolType OBJECT = new SymbolType("object");
     public final static SymbolType FUNC = new SymbolType("func");
     public final static SymbolType SECTION = new SymbolType("section");

     public final String name;

     public  SymbolType (String name){
          this.name = name;
     }

     @Override
     public boolean equals(Object obj) {
          if(obj instanceof SymbolType){
               return this.name.equals(((SymbolType) obj).name);
          }else{
               return false;
          }
     }
}
