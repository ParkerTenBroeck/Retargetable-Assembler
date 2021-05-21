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
package org.parker.mips.assembler;

import org.parker.retargetableassembler.base.preprocessor.expressions.BaseExpressionCompiler;

import java.util.HashMap;

public class MipsExpressionCompiler extends BaseExpressionCompiler<MipsAssembler, MipsPreProcessor> {

    private static final HashMap<String, MipsRegister> regMap = new HashMap<>();

    static{
        regMap.put("$0", new MipsRegister(0));
        regMap.put("$1", new MipsRegister(1));
        regMap.put("$2", new MipsRegister(2));
        regMap.put("$3", new MipsRegister(3));
        regMap.put("$4", new MipsRegister(4));
        regMap.put("$5", new MipsRegister(5));
        regMap.put("$6", new MipsRegister(6));
        regMap.put("$7", new MipsRegister(7));
        regMap.put("$8", new MipsRegister(8));
        regMap.put("$9", new MipsRegister(9));
        regMap.put("$10", new MipsRegister(10));
        regMap.put("$11", new MipsRegister(11));
        regMap.put("$12", new MipsRegister(12));
        regMap.put("$13", new MipsRegister(13));
        regMap.put("$14", new MipsRegister(14));
        regMap.put("$15", new MipsRegister(15));
        regMap.put("$16", new MipsRegister(16));
        regMap.put("$17", new MipsRegister(17));
        regMap.put("$18", new MipsRegister(18));
        regMap.put("$19", new MipsRegister(19));
        regMap.put("$20", new MipsRegister(20));
        regMap.put("$21", new MipsRegister(21));
        regMap.put("$22", new MipsRegister(22));
        regMap.put("$23", new MipsRegister(23));
        regMap.put("$24", new MipsRegister(24));
        regMap.put("$25", new MipsRegister(25));
        regMap.put("$26", new MipsRegister(26));
        regMap.put("$27", new MipsRegister(27));
        regMap.put("$28", new MipsRegister(28));
        regMap.put("$29", new MipsRegister(29));
        regMap.put("$30", new MipsRegister(30));
        regMap.put("$31", new MipsRegister(31));
    }

    @Override
    protected Object parseVariable(String token) {
        if(regMap.containsKey(token)){
            return regMap.get(token);
        }else{
            return super.parseVariable(token);
        }
    }
}
