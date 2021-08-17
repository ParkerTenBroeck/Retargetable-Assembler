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
package org.parker.retargetableassembler.exception.assembler;


import org.parker.retargetableassembler.base.linker.Symbol;
import org.parker.retargetableassembler.base.preprocessor.util.Line;

public class SymbolRedeclarationException extends AssemblerError {

    private final Symbol symbol;

    public SymbolRedeclarationException(Symbol symbol, Line line) {
        super("Label: " + symbol.symbolMnemonic + " has already been declared in this assembly unit", line);
        this.symbol = symbol;
    }
}
