package org.parker.retargetableassembler.pipe.preprocessor.directives.other;

import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.preprocessor.directives.PreProcessorDirective;
import org.parker.retargetableassembler.pipe.preprocessor.util.BufferUtils;
import org.parker.retargetableassembler.pipe.util.iterators.PeekEverywhereIteratorAbstract;

import java.util.ArrayList;
import java.util.Iterator;

public final class MACRO {


    public static class DEFINITION implements PreProcessorDirective {
        @Override
        public void init(LexSymbol root, PreProcessor pp) {

            Iterator<LexSymbol> curr = pp.getIteratorStack().peek_iterator_stack();
            Iterator<LexSymbol> line = BufferUtils.tillLineTerminator(curr, false);

            MacroDefinition m = new MacroDefinition();
            m.mID = generateMacroIDFromIterator(line, pp);
            if(line.hasNext()){
                pp.report().unexpectedTokenError(line.next(), LexSymbol.LINE_TERMINATOR);
                return;
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
                    pp.report().reportError("macro definition without termination", root);
                    return;
                }
                m.tokenList.add(temp);

            }while(curr.hasNext());

            curr.next();//new line;

            pp.addMacroDefinition(m);
        }
    }

    public static class UNDEFINITION implements PreProcessorDirective {

        @Override
        public void init(LexSymbol root, PreProcessor pp) {
            Iterator<LexSymbol> curr = pp.getIteratorStack().peek_iterator_stack();
            Iterator<LexSymbol> line = BufferUtils.tillLineTerminator(curr, false);

            MacroID mID = generateMacroIDFromIterator(line, pp);
            if(line.hasNext()){
                pp.report().unexpectedTokenError(line.next(), LexSymbol.LINE_TERMINATOR);
                return;
            }

            pp.removeMacroFromID(mID);
        }
    }
    
    public static MacroID generateMacroIDFromIterator(Iterator<LexSymbol> iterator, PreProcessor pp){
        
        MacroID mID = new MacroID();
        LexSymbol temp;
        
        if(iterator.hasNext()){
            temp = iterator.next();
            if(temp.sym == LexSymbol.IDENTIFIER) {
                mID.id = temp;
            }else{
                pp.report().unexpectedTokenError(temp, LexSymbol.IDENTIFIER);
                return null;
            }
        }
        if(iterator.hasNext()){
            temp = iterator.next();
            if(temp.sym == LexSymbol.INTEGER_LITERAL){
                mID.lowerOp =  ((Number)temp.value).intValue();
                mID.higherOp = mID.lowerOp;
                if(mID.lowerOp < 0){
                    pp.report().reportError("Cannot have negative operands", temp);
                    return null;
                }
            }else{
                pp.report().unexpectedTokenError(temp, LexSymbol.INTEGER_LITERAL);
                return null;
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
                            pp.report().reportError("Cannot have negative operands", temp);
                            return null;
                        }else if(mID.higherOp <= mID.lowerOp){
                            pp.report().reportError("Upper operand limit cannot be lower or equal to lower operand limit", temp);
                            return null;
                        }
                    }
                    if(iterator.hasNext()){
                        temp = iterator.next();
                        if (temp.sym == LexSymbol.PLUS) {
                            mID.greedy = true;
                        }else{
                            temp = iterator.next();
                            pp.report().unexpectedTokenError(temp);
                            return null;
                        }
                    }
                }
            }else{
                temp = iterator.next();
                pp.report().unexpectedTokenError(temp);
                return null;
            }
        }
        return mID;
    }

    public static class MacroIterator extends PeekEverywhereIteratorAbstract<LexSymbol>{

        private int index = 0;
        private int use;
        private MacroDefinition md;

        public MacroIterator(MacroDefinition md){
            this.md = md;
            this.use = md.uses;
            md.uses ++;
        }

        @Override
        public boolean hasNext() {
            return index < md.tokenList.size();
        }

        @Override
        protected LexSymbol next_peekless() {
            if(index < md.tokenList.size()){
                LexSymbol sym = md.tokenList.get(index);

                if(sym.sym == LexSymbol.LABEL || sym.sym == LexSymbol.IDENTIFIER){
                    if(sym.value.toString().startsWith("..$") && sym.value.toString().length() > 3){
                        sym = new LexSymbol(sym.getFile(), sym.sym, sym.getLine(), sym.getColumn(), sym.getCharPos(),
                                sym.getSize(), sym.left, sym.right,
                                "..$" + use + "." + sym.value.toString().substring(3));
                    }
                }

                index ++;
                return sym;
            }else{
                return new LexSymbol();
            }
        }
    }
    
    public static class MacroID{
        private LexSymbol id;
        private int lowerOp = 0;
        private int higherOp = -1;
        private boolean greedy = false;

        public String getIDName(){
            return (String) id.value;
        }
        public LexSymbol getID(){
            return id;
        }

        @Override
        public String toString() {
            String temp = getIDName();
            if(lowerOp == higherOp){
                temp += " " + lowerOp;
            }else{
                temp += " " + lowerOp + "-" + higherOp;
            }
            if(greedy)
                temp += "+";

            return temp;
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

        @Override
        public boolean equals(Object obj) {

            MacroID mID2;

            if(obj instanceof  MacroID){
                mID2 = (MacroID) obj;
            }else{
                return false;
            }
            if(!id.value.equals(mID2.id.value)){
                return false;
            }
            if(lowerOp != mID2.lowerOp){
                return false;
            }
            if(higherOp != mID2.higherOp){
                return false;
            }
            if(greedy != mID2.greedy){
                return false;
            }

            return true;
        }

        public boolean withinArguments(int args) {
            if(args == lowerOp){
                return true;
            }
            if(args >= lowerOp && greedy){
                return true;
            }
            if(higherOp > 0){
                if(args >= lowerOp && args <= higherOp){
                    return true;
                }
            }

            return false;
        }

        public boolean overlaps(MacroID mID){
            if(this.greedy && mID.greedy){
                return true;
            }else if(this.greedy){
                if(mID.higherOp >= this.lowerOp){
                    return true;
                }
            }else if(mID.greedy){
                if(this.higherOp >= mID.lowerOp){
                    return true;
                }
            }else{
                return this.lowerOp <= mID.higherOp && mID.lowerOp <= this.higherOp;
            }
            return false;
        }
    }

    public static class MacroDefinition {

        MacroID mID = new MacroID();
        ArrayList<LexSymbol> tokenList = new ArrayList<>();
        int uses = 0;

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
            return mID.equals(mID2);
        }

        public String getNameID() {
            return (String) mID.id.value;
        }

        public MacroID getID(){
            return this.mID;
        }
    }

}
