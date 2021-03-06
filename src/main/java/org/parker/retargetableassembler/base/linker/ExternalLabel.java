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
package org.parker.retargetableassembler.base.linker;

import org.parker.retargetableassembler.exception.linker.IllegalLabelReference;
import org.parker.retargetableassembler.exception.linker.LinkingException;
import org.parker.retargetableassembler.base.preprocessor.util.Line;
import org.parker.retargetableassembler.base.assembler.BaseAssembler;
import org.parker.retargetableassembler.exception.linker.LabelNotDeclaredError;

import java.util.Map;

public class ExternalLabel extends Label{

    private final Map<String, Label> globalLabelMap;

    public ExternalLabel(Map<String, Label> globalLabelMap, String mnemonic, Line line) {
        super(mnemonic, line);
        this.globalLabelMap = globalLabelMap;
    }

    public ExternalLabel(BaseAssembler assembler, String mnemonic, Line line) {
        this(assembler.getGlobalLabelMap(), mnemonic, line);
    }

    @Override
    public long getAddress() throws LinkingException {
        if(this.globalLabelMap.containsKey(this.symbolMnemonic)) {
            if(globalLabelMap.get(this.symbolMnemonic) instanceof ExternalLabel){
                throw new IllegalLabelReference(this, globalLabelMap.get(this.symbolMnemonic));
            }
            return globalLabelMap.get(this.symbolMnemonic).getAddress();
        }else{
            throw new LabelNotDeclaredError(this);
        }
    }
}
