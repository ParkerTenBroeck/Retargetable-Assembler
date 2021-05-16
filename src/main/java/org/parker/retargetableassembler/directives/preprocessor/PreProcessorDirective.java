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
package org.parker.retargetableassembler.directives.preprocessor;

import org.parker.retargetableassembler.base.preprocessor.BasePreProcessor;
import org.parker.retargetableassembler.base.preprocessor.IntermediateDirective;
import org.parker.retargetableassembler.base.preprocessor.IntermediateStatement;
import org.parker.retargetableassembler.util.ExpressionCompiler;

import java.util.List;

public interface PreProcessorDirective {

    void parse(IntermediateDirective s, List<IntermediateStatement> is, int index, ExpressionCompiler ec, BasePreProcessor preProcessor);

}
