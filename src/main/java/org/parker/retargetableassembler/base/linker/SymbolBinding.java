package org.parker.retargetableassembler.base.linker;

import java.io.*;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
public class SymbolBinding implements Serializable {

     public final static SymbolBinding LOCAL = new SymbolBinding("local");
     public final static SymbolBinding GLOBAL = new SymbolBinding("global");
     public final static SymbolBinding WEAK = new SymbolBinding("weak");

     public final String name;

     public  SymbolBinding (String name){
          this.name = name;
     }

     @Override
     public boolean equals(Object obj) {
          if(obj instanceof SymbolBinding){
               return this.name.equals(((SymbolBinding) obj).name);
          }else{
               return false;
          }
     }

}