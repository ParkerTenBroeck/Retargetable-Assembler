package org.parker.retargetableassembler.base.linker;

import org.parker.retargetableassembler.exception.linker.LinkingException;
import org.parker.retargetableassembler.exception.linker.SymbolNotDeclaredExcpetion;
import org.parker.retargetableassembler.util.Memory;

import java.io.File;

public class Linker {


    public Linker(){

    }

    public Memory link(File[] files){

        return null;
    }

    public long resolveSymbolAddress(Symbol symbol) throws LinkingException {
        throw new SymbolNotDeclaredExcpetion(symbol);
    }

    /*
    protected PagedMemory linkGlobal(){

        LINKER_LOGGER.log(AssemblerLogLevel.ASSEMBLER_MESSAGE, "Started linking global");

        PagedMemory pMemory = new PagedMemory();

        assemblyUnits.sort((o1, o2) -> {
            if(o1.getStartingAddress() < o2.getStartingAddress()){
                return -1;
            }else if(o1.getStartingAddress() > o2.getStartingAddress()){
                return 1;
            }else{
                return 0;
            }
        });

        if(assemblyUnits.get(0).getStartingAddress() < 0){
            assemblyUnits.get(0).setStartingAddress(0);
        }
        for(int i = 1; i < assemblyUnits.getSize(); i ++){
            if(assemblyUnits.get(i).getStartingAddress() < 0){
                assemblyUnits.get(i).setStartingAddress(assemblyUnits.get(i - 1).getEndingAddress());
            }else{
                if(assemblyUnits.get(i - 1).getEndingAddress() > assemblyUnits.get(i).getStartingAddress()){
                    LINKER_LOGGER.log(AssemblerLogLevel.ASSEMBLER_ERROR, "Size miss mach");
                }
            }
        }

        Debugger debugger = new Debugger();

        for(AssemblyUnit au: assemblyUnits){

            currentAssemblyUnit = au;

            long address = au.getStartingAddress();
            long size = 0;

            for(Data d:au.data){
                if(d instanceof Linkable){
                    try {
                        ((Linkable) d).link(this, address);
                    }catch (Exception e){
                        LINKER_LOGGER.log(AssemblerLogLevel.ASSEMBLER_ERROR, ": Failed to link data", e);
                        try {
                            address += d.getSize();
                            size += d.getSize();
                        }catch (Exception ex){
                            LINKER_LOGGER.log(AssemblerLogLevel.ASSEMBLER_ERROR, "", ex);
                        }
                        continue;
                    }
                }
                try {
                    byte[] bytes = d.toBinary();
                    pMemory.add((int) address, bytes);
                    address += d.getSize();
                    size += d.getSize();

                    debugger.addDataRange(address - d.getSize(), address, dataToPreProcessedStatement.get(d).getLine());
                }catch(Exception e){
                    try{
                        address += d.getSize();
                        size += d.getSize();
                    }catch (Exception ex){
                        ASSEMBLER_LOGGER.log(AssemblerLogLevel.ASSEMBLER_ERROR, "",  ex);
                    }
                    ASSEMBLER_LOGGER.log(AssemblerLogLevel.ASSEMBLER_ERROR, "",  e);
                }
            }
            if(size != au.getSize()){
                LINKER_LOGGER.log(AssemblerLogLevel.ASSEMBLER_ERROR, ": Size missmatch size of Assembler Unit does not match the linked size? expected: " + au.getSize() + " got: " + size);
            }
        }

        for(AssemblyUnit au: assemblyUnits){
            for(Map.Entry<String, Label> s:au.asuLabelMap.entrySet()){
                try {
                    debugger.addLabel(new FinalizedLabel(s.getValue().symbolMnemonic, s.getValue().line, s.getValue().getAddress()));
                }catch (Exception ignored){

                }
            }
        }

        return pMemory;
    }
     */
}
