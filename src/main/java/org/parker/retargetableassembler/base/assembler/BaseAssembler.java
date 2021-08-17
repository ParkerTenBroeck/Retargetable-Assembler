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
import org.parker.retargetableassembler.base.linker.*;
import org.parker.retargetableassembler.base.preprocessor.*;
import org.parker.retargetableassembler.base.preprocessor.statements.PreProcessedAssemblyDirective;
import org.parker.retargetableassembler.base.preprocessor.statements.PreProcessedAssemblyStatement;
import org.parker.retargetableassembler.base.preprocessor.statements.PreProcessedLabel;
import org.parker.retargetableassembler.base.preprocessor.statements.PreProcessedStatement;
import org.parker.retargetableassembler.directives.assembler.AssemblerDirectives;
import org.parker.retargetableassembler.exception.assembler.AssemblerError;
import org.parker.retargetableassembler.base.preprocessor.expressions.CompiledExpression;
import org.parker.retargetableassembler.exception.assembler.SymbolRedeclarationException;
import org.parker.retargetableassembler.util.AssemblerLogLevel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public abstract class BaseAssembler<P extends PreProcessor> implements Assembler{


    private static final Logger ASSEMBLER_LOGGER = Logger.getLogger(BaseAssembler.class.getName() + "\\.Assembler");

    protected P preProcessor;

    //debugging
    protected HashMap<Data, PreProcessedStatement> dataToPreProcessedStatement;

    protected PreProcessedStatement currentStatement;
    protected long currentSectionProgramCounter = 0;
    protected AssemblyUnit currentAssemblyUnit = null;
    protected Section currentSection = null;


    protected Section getDefaultSection(){
        Section s = new Section();
        return s;
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
                Symbol symbol = this.getSymbol(((PreProcessedLabel) currentStatement).label);
                if(symbol.sectionAddress != -1){
                    symbol.sectionAddress = currentSectionProgramCounter;
                }else{
                    throw new SymbolRedeclarationException(symbol, currentStatement.getLine());
                }

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

    public Symbol getSymbol(String name){
        if(currentAssemblyUnit.symbolTable.containsKey(name)){
            return currentAssemblyUnit.symbolTable.get(name);
        }else{
            Symbol symbol = new Symbol(((PreProcessedLabel) currentStatement).label);
            symbol.binding = SymbolBinding.LOCAL;
            symbol.type = SymbolType.NOTYPE;
            symbol.sectionAddress = -1;
            currentAssemblyUnit.symbolTable.put(name, symbol);
            return symbol;
        }
    }

    public void addDataToCurrent(Data data){
        currentSectionProgramCounter += data.getSize();
        currentSection.size += data.getSize();
        currentSection.sectionData.add(data);

        //debug
        //dataToPreProcessedStatement.put(data, currentStatement);
    }

    @Override
    public File assemble(File file) {

        currentAssemblyUnit = new AssemblyUnit();
        currentAssemblyUnit.symbolTable = new HashMap<>();
        currentAssemblyUnit.architecture = "test";
        currentAssemblyUnit.sections = new ArrayList<>();

        Section s = new Section();
        s.bigEndian = false;

        currentAssemblyUnit.sections.add(s);
        currentSection = s;

        preProcessor = createPreProcessor();

        if(file == null) {
            ASSEMBLER_LOGGER.log(AssemblerLogLevel.ASSEMBLER_ERROR, "Provided file is null skipping");
            return null;
        }
        ASSEMBLER_LOGGER.log(AssemblerLogLevel.ASSEMBLER_MESSAGE, "Started assembly of: " + file.getAbsolutePath());


        List<PreProcessedStatement> lines = preProcessFile(file);

        for(int i = 0; i < lines.size(); i ++){
            currentStatement = lines.get(i);
            evaluatePreProcessedStatement(currentStatement);
        }

        return null;
    }

    public void setCurrentSectionAlignment(int i){
        currentSection.alignment = i;
    }

    public long getCurrentSectionProgramCounter(){
        return currentSectionProgramCounter;
    }

    public boolean isBigEndian(){
        return currentSection.bigEndian;
    }

    protected abstract P createPreProcessor();
    protected abstract StatementAssociatedData getInstruction(String mnemonic);
}
