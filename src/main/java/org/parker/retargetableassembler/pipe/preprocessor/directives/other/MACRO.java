package org.parker.retargetableassembler.pipe.preprocessor.directives.other;

import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.preprocessor.PreProcessor;
import org.parker.retargetableassembler.pipe.preprocessor.directives.PreProcessorDirective;
import org.parker.retargetableassembler.pipe.preprocessor.util.BufferUtils;
import org.parker.retargetableassembler.pipe.util.iterators.PeekAheadIterator;
import org.parker.retargetableassembler.pipe.util.iterators.PeekEverywhereIteratorAbstract;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public static List<List<LexSymbol>> splitMacroArguments(PeekAheadIterator<LexSymbol> iterator){
        LexSymbol s;
        if(!iterator.hasNext()) return new ArrayList<>();
        List<List<LexSymbol>> tmpList = new ArrayList<>();
        List<LexSymbol> tmpDataList = new ArrayList<>();
        if(iterator.peek_ahead().sym == LexSymbol.LINE_TERMINATOR || iterator.peek_ahead().sym == LexSymbol.EOF) return new ArrayList<>();
        do{
            s = iterator.next();

            if(s.sym == LexSymbol.BACKSLASH && iterator.peek_ahead().sym == LexSymbol.COMMA){
                tmpDataList.add(iterator.next());
            }else if(s.sym == LexSymbol.COMMA || s.sym == LexSymbol.LINE_TERMINATOR || s.sym == LexSymbol.EOF){
                tmpList.add(tmpDataList);
                tmpDataList = new ArrayList<>();
            }else{
                tmpDataList.add(s);
            }
        }while(iterator.hasNext() && (s.sym != LexSymbol.LINE_TERMINATOR && s.sym != LexSymbol.EOF));
        return tmpList;
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
        mID.id.sym = LexSymbol.MACRO;
        return mID;
    }

    public static class MacroIterator extends PeekEverywhereIteratorAbstract<LexSymbol>{

        private int index = 0;
        private int use;
        private MacroDefinition md;
        List<List<LexSymbol>> macroArguments;

        private boolean inArg;
        private int argNum;
        private int argIndex;
        private LexSymbol argSym;

        final LexSymbol caller;

        public MacroIterator(LexSymbol caller, MacroDefinition md, List<List<LexSymbol>> macroArguments){
            this.md = md;
            this.macroArguments = macroArguments;
            this.use = md.uses;
            this.caller = caller;
            md.uses ++;
        }

        @Override
        public boolean hasNext() {
            return index < md.tokenList.size();
        }

        @Override
        protected LexSymbol next_peekless() {
            if(index < md.tokenList.size()){
                LexSymbol sym = md.tokenList.get(index).clone();

                if(sym.sym == LexSymbol.LABEL || sym.sym == LexSymbol.IDENTIFIER){
                    if(sym.value.toString().startsWith("..$") && sym.value.toString().length() > 3){
                        sym.value = "$.." + use + "." + sym.value.toString().substring(3);
                    }
                }
                if(sym.sym == LexSymbol.IDENTIFIER){
                    if(sym.value.toString().startsWith("$_") && !inArg){
                        if(sym.value.equals("$_0")){
                            sym.sym = LexSymbol.INTEGER_LITERAL;
                            sym.value = macroArguments.size();
                            //return sym;
                        }else {
                            int val = 0;
                            try {
                                val = Integer.parseInt(sym.value.toString().substring(2));
                            } catch (Exception ignore) {
                                //TODO(parker) report errors
                            }
                            //TODO(parker) report errors
                            inArg = true;
                            argNum = val - 1;
                            argIndex = 0;
                            sym.setParent(caller);
                            argSym = sym;
                        }

                    }
                }
                if(inArg) {
                    LexSymbol tmp =  macroArguments.get(argNum).get(argIndex).clone();
                    sym = argSym.clone();
                    sym.value = tmp.value;
                    sym.sym = tmp.sym;
                    sym.setParent(tmp);
                    argIndex++;
                    if (argIndex >= macroArguments.get(argNum).size()){
                        index++;
                        inArg = false;
                    }
                }else{
                    index ++;
                    sym.setParent(caller);
                }

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

        @Override
        protected MacroID clone() {
            MacroID mID = new MacroID();
            mID.id = id;
            mID.lowerOp = lowerOp;
            mID.higherOp = higherOp;
            mID.greedy = greedy;
            return mID;
        }

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

        private MacroID mID = new MacroID();
        private ArrayList<LexSymbol> tokenList = new ArrayList<>();
        private int uses = 0;

        @Override
        public MacroDefinition clone() {
            MacroDefinition md =  new MacroDefinition();
            md.mID = mID.clone();
            md.tokenList = tokenList;
            md.uses = uses;
            return md;
        }

        public void updateID(LexSymbol id){
            if(!mID.id.value.equals(id.value)){
                throw new IllegalArgumentException();
            }
            mID.id = id;
        }

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
