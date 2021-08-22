package org.parker.retargetableassembler.pipe.preprocessor;

import org.parker.retargetableassembler.pipe.lex.cup.AssemblerSym;
import org.parker.retargetableassembler.pipe.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.preprocessor.directives.Directives;
import org.parker.retargetableassembler.pipe.preprocessor.directives.other.MACRO;
import org.parker.retargetableassembler.pipe.preprocessor.util.PreProcessorOutputFilter;
import org.parker.retargetableassembler.pipe.util.iterators.IteratorStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PreProcessor implements Iterator<LexSymbol>{

    private Directives directives = new Directives();
    private ArrayList<PreProcessorOutputFilter> filterStack = new ArrayList<>();
    private boolean atBOL = true;
    private MacroHolder definedMacros = new MacroHolder();

    private IteratorStack<LexSymbol> iteratorStack = new IteratorStack<LexSymbol>(){

        {
            this.setMaxScannerStackSize(200);
        }

        @Override
        public void push_iterator_stack(Iterator<LexSymbol> iterator) {
            super.push_iterator_stack(iterator);
            if(iterator instanceof  PreProcessorOutputFilter){
                filterStack.add((PreProcessorOutputFilter) iterator);
            }
        }

        @Override
        protected void onPop(Iterator<LexSymbol> popped) {
            if(filterStack.size() <=0 ){
                return;
            }
            if(filterStack.get(filterStack.size() - 1).equals(popped)){
                filterStack.remove(popped);
            }
        }

        @Override
        protected LexSymbol next_peekless() {
            LexSymbol t = super.next_peekless();

                while (t == null || t.sym == LexSymbol.EOF) {
                    LexSymbol peek = super.peek_ahead();
                    if (peek == null) {
                        super.next_peekless();
                        return t;
                    } else {
                        t = super.next_peekless();
                    }
                }

            return t;
        }
    };

    private boolean atEOF = false;




    @Override
    public LexSymbol next() {

        LexSymbol s = null;

        while(s == null || (s.sym == LexSymbol.LINE_TERMINATOR && this.atBOL)) {
            s = iteratorStack.next();
            if (s.sym == AssemblerSym.EOF) {
                atEOF = true;
                return s;
            }
            while (s.sym == AssemblerSym.DIRECTIVE && directives.hasDirective((String) s.value)) {
                directives.handleDirective((String) s.value, iteratorStack, this);
                s = iteratorStack.next();
            }

            for (int i = filterStack.size() - 1; i >= 0; i--) {
                s = filterStack.get(i).filterOutput(s);
                if (s == null) {
                    break;
                }
            }

            if(s != null) {
                while (s.sym == AssemblerSym.DIRECTIVE && directives.hasStranglerDirective((String) s.value)) {
                    directives.handleStranglerDirective((String) s.value, iteratorStack, this);
                    s = iteratorStack.next();
                }
            }
        }
        atBOL = s.sym == LexSymbol.LINE_TERMINATOR;

        return s;
    }

    public boolean hasNext(){
        return atEOF;
    }

    public void clear(){
        iteratorStack.clear_peek_behind();
        atEOF = false;
    }

    public void start(Iterator<LexSymbol> s){
        clear();
        iteratorStack.push_iterator_stack(s);
    }

    public void addMacroDefinition(MACRO.MacroDefinition m) {
        definedMacros.addMacro(m);
    }

    public void removeMacroFromID(MACRO.MacroID mID) {
        definedMacros.removeMacroFromID(mID);
    }


    public static class MacroHolder{

        Map<String, pp> macros = new HashMap<>();

        public boolean hasAvailableMacro(String id, int args){
            if(macros.containsKey(id)){
                return macros.get(id).hasAvailableMacro(args);
            }
            return false;
        }

        public MACRO.MacroDefinition getAvailableMacro(String id, int args){
            if(macros.containsKey(id)){
                return macros.get(id).getAvailableMacro(args);
            }
            return null;
        }

        public synchronized void addMacro(MACRO.MacroDefinition m){
            if(macros.containsKey(m.getNameID())){
                macros.get(m.getNameID()).addMacro(m);
            }else{
                pp p = new pp();
                p.addMacro(m);
                macros.put(m.getNameID(), p);
            }
        }

        public synchronized void removeMacro(MACRO.MacroDefinition m){
            removeMacroFromID(m.getID());
        }

        public synchronized void removeMacroFromID(MACRO.MacroID mID){
            if(macros.containsKey(mID.getID())){
                macros.get(mID.getID()).removeMacro(mID);
            }
            //does not contain
        }

        private static class pp{
            private ArrayList<MACRO.MacroDefinition> macros = new ArrayList<>();

            public void clear(){
                macros.clear();
            }

            public void addMacro(MACRO.MacroDefinition m){
                //TODO add override checks to give warnings when newly defined macros overlap

                if(hasMacro(m)){
                    removeMacro(m);
                }
                macros.add(m);
            }

            public void removeMacro(MACRO.MacroDefinition m){
                removeMacro(m.getID());
            }

            public void removeMacro(MACRO.MacroID m){
                macros.remove(m);
            }

            public boolean hasMacro(MACRO.MacroDefinition m){
                return hasMacro(m.getID());
            }

            public boolean hasMacro(MACRO.MacroID m){
                for (int i = 0; i < macros.size(); i++) {
                    if(macros.get(i).equals(m)){
                        return true;
                    }
                }
                return false;
            }


            public MACRO.MacroDefinition getAvailableMacro(int args){
                for(int i = macros.size() - 1; i >= 0; i --){
                    MACRO.MacroDefinition m = macros.get(i);
                    if(m.withinArguments(args)){
                        return m;
                    }
                }
                return null;
            }

            public boolean hasAvailableMacro(int args){
                for(int i = macros.size() - 1; i >= 0; i --){
                    MACRO.MacroDefinition m = macros.get(i);
                    if(m.withinArguments(args)){
                        return true;
                    }
                }
                return false;
            }

        }
    }
}
