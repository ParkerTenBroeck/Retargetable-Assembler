package org.parker.retargetableassembler.pipe.preprocessor.directives.other;

import org.parker.retargetableassembler.pipe.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.preprocessor.directives.PreProcessorDirective;
import org.parker.retargetableassembler.pipe.preprocessor.util.BufferUtils;
import org.parker.retargetableassembler.pipe.util.iterators.IteratorStack;

import java.util.ArrayList;
import java.util.Iterator;

public class MACRO {


    public static class DEFINITION implements PreProcessorDirective {
        @Override
        public void init(IteratorStack<LexSymbol> iterator, PreProcessor pp) {

            Iterator<LexSymbol> curr = iterator.peek_iterator_stack();
            Iterator<LexSymbol> line = BufferUtils.tillLineTerminator(curr, false);

            MacroDefinition m = new MacroDefinition();
            m.mID = generateMacroIDFromIterator(line);
            if(line.hasNext()){
                throw new RuntimeException("Unexpected token expected:LINE_TERMINATOR got:" + LexSymbol.terminalNames[line.next().sym]);
            }

            LexSymbol temp;
            int r = 0;
            loop:
            do{

                temp = curr.next();

                if(temp.sym == LexSymbol.DIRECTIVE){
                    if(temp.value.equals("endmacro")) {
                        if (r <= 0) {
                            break loop;
                        } else {
                            m.tokenList.add(temp);
                            r--;
                            continue;
                        }
                    }else if(temp.value.equals("macro")){
                        m.tokenList.add(temp);
                        r ++;
                        continue;
                    }
                }
                if(temp.sym == LexSymbol.EOF){
                    throw new RuntimeException("reached EOF without macro termination");
                }
                m.tokenList.add(temp);

            }while(curr.hasNext());

            curr.next();//new line;

            pp.addMacroDefinition(m);
        }
    }

    public static class UNDEFINITION implements PreProcessorDirective {

        @Override
        public void init(IteratorStack<LexSymbol> iterator, PreProcessor pp) {
            Iterator<LexSymbol> curr = iterator.peek_iterator_stack();
            Iterator<LexSymbol> line = BufferUtils.tillLineTerminator(curr, false);

            MacroID mID = generateMacroIDFromIterator(line);
            if(line.hasNext()){
                throw new RuntimeException("Unexpected token expected:LINE_TERMINATOR got:" + LexSymbol.terminalNames[line.next().sym]);
            }

            pp.removeMacroFromID(mID);
        }
    }
    
    public static MacroID generateMacroIDFromIterator(Iterator<LexSymbol> iterator){
        
        MacroID mID = new MacroID();
        LexSymbol temp;
        
        if(iterator.hasNext()){
            temp = iterator.next();
            if(temp.sym == LexSymbol.IDENTIFIER) {
                mID.id = temp;
            }else{
                throw new RuntimeException("Invalid Token Type expected:IDENTIFIER got:" + LexSymbol.terminalNames[temp.sym]);
            }
        }
        if(iterator.hasNext()){
            temp = iterator.next();
            if(temp.sym == LexSymbol.INTEGER_LITERAL){
                mID.lowerOp =  ((Number)temp.value).intValue();
                if(mID.lowerOp < 0){
                    throw new RuntimeException("Cannot have negative operands");
                }
            }else{
                throw new RuntimeException("Invalid Token Type expected:INTEGER_LITERAL got:" + LexSymbol.terminalNames[temp.sym]);
            }
        }

        if(iterator.hasNext()) {
            temp = iterator.next();
            if (temp.sym == LexSymbol.PLUS) {
                mID.greedy = true;
            } else if(temp.sym == LexSymbol.MINUS){
                iterator.hasNext();
                if(iterator.hasNext()){
                    temp = iterator.next();
                    if(temp.sym == LexSymbol.INTEGER_LITERAL){
                        mID.higherOp = ((Number)temp.value).intValue();
                        if(mID.higherOp < 0){
                            throw new RuntimeException("Cannot have negative operands");
                        }else if(mID.higherOp <= mID.lowerOp){
                            throw new RuntimeException("Upper operand limit cannot be lower or equal to lower operand limit");
                        }
                    }
                    if(iterator.hasNext()){
                        temp = iterator.next();
                        if (temp.sym == LexSymbol.PLUS) {
                            mID.greedy = true;
                        }else{
                            temp = iterator.next();
                            throw new RuntimeException("Invalid Token Type got:" + LexSymbol.terminalNames[temp.sym]);
                        }
                    }
                }
            }else{
                temp = iterator.next();
                throw new RuntimeException("Invalid Token Type got:" + LexSymbol.terminalNames[temp.sym]);
            }
        }
        return mID;
    }
    
    public static class MacroID{
        private LexSymbol id;
        private int lowerOp = 0;
        private int higherOp = -1;
        private boolean greedy = false;

        public String getID(){
            return (String) id.value;
        }

        public int getLowerOP(){
            return lowerOp;
        }

        public int getHigherOp(){
            return higherOp;
        }

        public boolean isGreedy(){
            return greedy;
        }
    }

    public static class MacroDefinition {

        MacroID mID = new MacroID();
        ArrayList<LexSymbol> tokenList = new ArrayList<>();

        @Override
        public boolean equals(Object obj) {

            MacroID mID2;

            if(obj instanceof MacroDefinition){
                mID2 = ((MacroDefinition) obj).mID;
            }else if(obj instanceof  MacroID){
                mID2 = (MacroID) obj;
            }else{
                return false;
            }
            if(!mID.id.value.equals(mID2.id.value)){
                return false;
            }
            if(mID.lowerOp != mID2.lowerOp){
                return false;
            }
            if(mID.higherOp != mID2.higherOp){
                return false;
            }
            if(mID.greedy != mID2.greedy){
                return false;
            }

            return true;
        }

        public boolean withinArguments(int args) {
            if(args == mID.lowerOp){
                return true;
            }
            if(args >= mID.lowerOp && mID.greedy){
                return true;
            }
            if(mID.higherOp > 0){
                if(args >= mID.lowerOp && args <= mID.higherOp){
                    return true;
                }
            }

            return false;
        }

        public String getNameID() {
            return (String) mID.id.value;
        }

        public MacroID getID(){
            return this.mID;
        }
    }

}
