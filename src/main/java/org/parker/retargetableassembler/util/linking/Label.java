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
package org.parker.retargetableassembler.util.linking;

import org.parker.retargetableassembler.exception.linker.LabelNotDeclaredError;
import org.parker.retargetableassembler.exception.linker.LinkingException;
import org.parker.retargetableassembler.base.preprocessor.util.Line;

public abstract class Label {

    public final String mnemonic;
    public final Line line;

    public Label(String mnemonic, Line line){
        this.mnemonic = mnemonic;
        this.line = line;
    }

    /**
     *
     * @return returns a long representing the location in memory this label points to
     * @throws LabelNotDeclaredError this is thrown when this label is not declared. ex, a global label is referenced but never declared
     */
    public abstract long getAddress() throws LinkingException;

    @Override
    public String toString() {
        return "Label: " + mnemonic + " on line: " + line.getHumanLineNumber() + " from: " + line.getFile().getAbsolutePath();
    }
}
