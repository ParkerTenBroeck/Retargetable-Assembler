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
package org.parker.retargetableassembler.base.preprocessor.expressions;

import org.parker.retargetableassembler.base.preprocessor.expressions.scope.ExpressionScope;
import org.parker.retargetableassembler.base.preprocessor.util.Line;

import java.io.Serializable;

public abstract class CompiledExpression implements Serializable {
    public final int startingAddress;
    public final int endingAddress;
    public final Line line;
    //no scope should be saved when serializing
    private transient ExpressionScope expressionScope;

    public CompiledExpression(Line line, int s, int e){
        this.line = line;
        this.startingAddress = s;
        this.endingAddress = e;
    }

    public abstract Object evaluate();

    public ExpressionScope getExpressionScope(){
        return this.expressionScope;
    }

    public void setExpressionScope(ExpressionScope es){
        this.expressionScope = es;
    }
}
