/*
 *    Copyright 2021 ParkerTenBroeck
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.parker.retargetableassembler.base.assembler;

import org.parker.retargetableassembler.base.Data;
import org.parker.retargetableassembler.base.StatementAssociatedData;
import org.parker.retargetableassembler.base.Linkable;
import org.parker.retargetableassembler.base.preprocessor.*;
import org.parker.retargetableassembler.base.preprocessor.statements.PreProcessedAssemblyDirective;
import org.parker.retargetableassembler.base.preprocessor.statements.PreProcessedAssemblyStatement;
import org.parker.retargetableassembler.base.preprocessor.statements.PreProcessedLabel;
import org.parker.retargetableassembler.base.preprocessor.statements.PreProcessedStatement;
import org.parker.retargetableassembler.debugger.Debugger;
import org.parker.retargetableassembler.debugger.FinalizedLabel;
import org.parker.retargetableassembler.directives.assembler.AssemblerDirectives;
import org.parker.retargetableassembler.exception.assembler.AssemblerError;
import org.parker.retargetableassembler.base.preprocessor.expressions.CompiledExpression;
import org.parker.retargetableassembler.base.linker.AssemblyUnit;
import org.parker.retargetableassembler.base.linker.Label;
import org.parker.retargetableassembler.base.linker.LocalLabel;
import org.parker.retargetableassembler.util.AssemblerLogLevel;
import org.parker.retargetableassembler.util.Memory;
import org.parker.retargetableassembler.util.PagedMemory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public abstract class BaseAssembler<P extends PreProcessor> implements Assembler{


    private static final Logger ASSEMBLER_LOGGER = Logger.getLogger(BaseAssembler.class.getName() + "\\.Assembler");
    private static final Logger LINKER_LOGGER = Logger.getLogger(BaseAssembler.class.getName() + "\\.Linker");

    //global values
    protected P preProcessor;
    protected ArrayList<AssemblyUnit> assemblyUnits = new ArrayList<>();
    protected HashMap<String, Label> globalLabelMap = new HashMap<>();

    //debugging
    protected HashMap<Data, PreProcessedStatement> dataToPreProcessedStatement;

    //values specific to current assemble(File)
    protected File currentFile;
    protected PreProcessedStatement currentStatement;
    protected HashMap<String, Label> fileLabelMap;
    protected ArrayList<Data> fileDataList;
    protected AssemblyUnit currentAssemblyUnit;
    protected long currentAssemblyUnitAddress = 0;
    protected long currentAssemblyUnitSize = 0;

    protected boolean isBigEndian;

    protected void assemble(File input){

        if(input == null) {
            ASSEMBLER_LOGGER.log(AssemblerLogLevel.ASSEMBLER_ERROR, "Provided file is null skipping");
            return;
        }
        ASSEMBLER_LOGGER.log(AssemblerLogLevel.ASSEMBLER_MESSAGE, "Started assembly of: " + input.getAbsolutePath());

        currentFile = input;
        initLocalFileRelatedContent();
        List<PreProcessedStatement> lines = preProcessFile(input);

        for(int i = 0; i < lines.size(); i ++){
            currentStatement = lines.get(i);
            evaluatePreProcessedStatement(currentStatement);
        }
        currentAssemblyUnit.setSize(currentAssemblyUnitSize);
        assemblyUnits.add(currentAssemblyUnit);

        clearLocalFileRelatedContent();
    }

    protected void evaluatePreProcessedStatement(PreProcessedStatement statement){
        if (currentStatement instanceof PreProcessedAssemblyStatement) {

            try {
                String mnemonic = ((PreProcessedAssemblyStatement) currentStatement).identifier;
                CompiledExpression[] args = ((PreProcessedAssemblyStatement) currentStatement).args;

                StatementAssociatedData instruction = getInstruction(mnemonic);
                instruction.setArgExpressions(args, currentStatement.getLine());
                //data = instruction;
                addDataToCurrent(instruction);
            }catch (Exception e){
                ASSEMBLER_LOGGER.log(AssemblerLogLevel.ASSEMBLER_ERROR, "", new AssemblerError("Failed to evaluate Assembly Statement", currentStatement.getLine(), e));
                return;
            }

        } else if (currentStatement instanceof PreProcessedAssemblyDirective) {

            try {
                String directive = ((PreProcessedAssemblyDirective) currentStatement).identifier;
                CompiledExpression[] args = ((PreProcessedAssemblyDirective) currentStatement).args;

                AssemblerDirectives.getHandler(directive).parse(
                        ((PreProcessedAssemblyDirective) currentStatement).parentLine, args, this);
            }catch (Exception e){
                ASSEMBLER_LOGGER.log(AssemblerLogLevel.ASSEMBLER_ERROR, "", new AssemblerError("Failed to evaluate AssemblyDirective", currentStatement.getLine(), -1, e));
                return;
            }

        } else if (currentStatement instanceof PreProcessedLabel) {
            try {
                Label label = new LocalLabel(currentAssemblyUnitAddress, this.currentAssemblyUnit, ((PreProcessedLabel) currentStatement).label, currentStatement.getLine());
                currentAssemblyUnit.addLabel(label);

            }catch (Exception e){
                ASSEMBLER_LOGGER.log(AssemblerLogLevel.ASSEMBLER_ERROR, "", new AssemblerError("Failed to evaluate Label",  currentStatement.getLine(), -1, e));
                return;
            }
        }else{
            ASSEMBLER_LOGGER.log(AssemblerLogLevel.ASSEMBLER_ERROR, "", new AssemblerError("Failed To Evaluate PreProcessor Statement: " + currentStatement.getClass().getSimpleName(), currentStatement.getLine()));
        }
    }

    protected List<PreProcessedStatement> preProcessFile(File file){
        return preProcessor.preprocess(file);
    }

    protected void clearLocalFileRelatedContent(){
        currentFile = null;
        currentStatement = null;
        fileLabelMap = null;
        fileDataList = null;
        currentAssemblyUnit = null;
        currentAssemblyUnitAddress = 0;
        currentAssemblyUnitSize = 0;
    }

    protected void initLocalFileRelatedContent(){
        fileLabelMap = new HashMap<>();
        fileDataList = new ArrayList<>();
        currentAssemblyUnit = new AssemblyUnit(fileDataList, fileLabelMap);
        currentAssemblyUnitAddress = 0;
        currentAssemblyUnitSize = 0;
    }

    public void addDataToCurrent(Data data){
        currentAssemblyUnitAddress += data.getSize();
        currentAssemblyUnitSize += data.getSize();
        fileDataList.add(data);

        //debug
        dataToPreProcessedStatement.put(data, currentStatement);
    }

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
        for(int i = 1; i < assemblyUnits.size(); i ++){
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

    @Override
    public Memory assemble(File[] files) {

        isBigEndian = false;
        this.globalLabelMap = new HashMap<>();
        this.assemblyUnits = new ArrayList<>();
        this.preProcessor = createPreProcessor();

        this.dataToPreProcessedStatement = new HashMap<>();

        for(File f: files){
            assemble(f);
        }

        Memory mem = linkGlobal();

        this.globalLabelMap = null;
        this.assemblyUnits = null;
        this.preProcessor = null;

        this.dataToPreProcessedStatement = null;

        return mem;
    }

    public Label getLabel(String token) {
        if(currentAssemblyUnit.getAsuLabelMap().containsKey(token)) {
            return currentAssemblyUnit.getAsuLabelMap().get(token);
        }else{
            throw new IllegalArgumentException("token: " + token + " is not declared in the current scope");
        }
    }

    public void setCurrentAssemblyUnitAlignment(int i){
        currentAssemblyUnit.setAlignment(i);
    }

    public long getCurrentAssemblyUnitAddress(){
        return currentAssemblyUnitAddress;
    }

    public AssemblyUnit getCurrentAssemblyUnit() {
        return currentAssemblyUnit;
    }

    public Map<String, Label> getGlobalLabelMap() {
        return globalLabelMap;
    }

    public void addGlobalLabel(Label label) {
        this.globalLabelMap.put(label.symbolMnemonic, label);
    }

    public boolean isBigEndian(){
        return isBigEndian;
    }

    protected abstract P createPreProcessor();
    protected abstract StatementAssociatedData getInstruction(String mnemonic);
}
