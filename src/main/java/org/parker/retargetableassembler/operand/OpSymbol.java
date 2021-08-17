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
package org.parker.retargetableassembler.operand;

import org.parker.retargetableassembler.base.linker.Linker;
import org.parker.retargetableassembler.base.linker.Symbol;
import org.parker.retargetableassembler.exception.linker.LinkingException;
import org.parker.retargetableassembler.base.linker.LinkType;

public class OpSymbol extends OpLong implements LinkableOperand{

    protected final Symbol symbol;
    private Boolean linked = false;

    public OpSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public Long getValue() {
        if(linked) {
            return super.getValue();
        }else{
            throw new RuntimeException("Symbol: not linked yet" + symbol.symbolMnemonic);
        }
    }

    @Override
    public void link(Linker linker, long sourceAddr, LinkType linkType) throws LinkingException {
        if(linkType == null){
            this.setValue(LinkType.ABSOLUTE_BYTE.link(sourceAddr, linker.resolveSymbolAddress(symbol)));
        }else{
            this.setValue(linkType.link(sourceAddr, linker.resolveSymbolAddress(symbol)));
        }
        this.linked = true;
    }
}
