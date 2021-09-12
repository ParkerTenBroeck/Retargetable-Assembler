package org.parker.retargetableassembler.pipe.preprocessor;

import org.parker.retargetableassembler.pipe.Report;
import org.parker.retargetableassembler.pipe.preprocessor.expressions.CompiledExpression;
import org.parker.retargetableassembler.pipe.preprocessor.expressions.ExpressionEvaluator;
import org.parker.retargetableassembler.pipe.preprocessor.lex.cup.AssemblerSym;
import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.preprocessor.directives.Directives;
import org.parker.retargetableassembler.pipe.preprocessor.directives.other.MACRO;
import org.parker.retargetableassembler.pipe.preprocessor.util.BufferUtils;
import org.parker.retargetableassembler.pipe.preprocessor.util.PreProcessorOutputFilter;
import org.parker.retargetableassembler.pipe.util.iterators.IteratorStack;
import org.parker.retargetableassembler.pipe.util.iterators.PeekEverywhereIterator;

import java.util.*;

public class PreProcessor implements Iterator<LexSymbol>{

    private PreProcessorReportWrapper report;

    private LexSymbol lastNonLocalLabel;

    private boolean atBOL = true;
    private boolean atEOF = false;
    private Object expressionEvaluator;
    private Directives directives = new Directives();
    private Object defineStack;
    private Object constants;
    private MacroHolder definedMacros = new MacroHolder();
    private ArrayList<PreProcessorOutputFilter> filterStack = new ArrayList<>();
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

    public void setReport(Report report){
        this.report = new PreProcessorReportWrapper(report);
    }

    public Report getReport(){
        return this.report.getReport();
    }

    public PreProcessorReportWrapper report(){
        return this.report;
    }

    public IteratorStack<LexSymbol> getIteratorStack(){
        return this.iteratorStack;
    }

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
                directives.handleDirective((String) s.value, s, this);
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
                    directives.handleStranglerDirective((String) s.value, s, this);
                    s = iteratorStack.next();
                }

                exitIf:
                if(s.sym == LexSymbol.INSTRUCTION){
                    Iterator<LexSymbol> endOfLineIterator = BufferUtils.tillLineTerminator(iteratorStack.peek_iterator_stack(), true);
                    List<LexSymbol> endOfLineData = BufferUtils.iteratorToArrayList(endOfLineIterator);
                    PeekEverywhereIterator<LexSymbol> reusableIterator = BufferUtils.peekEverywhereIteratorFromList(endOfLineData);

                    List<List<LexSymbol>> macroArguments = MACRO.splitMacroArguments(reusableIterator);

                    if(reusableIterator.peek_ahead().sym != LexSymbol.LINE_TERMINATOR && reusableIterator.peek_ahead().sym != LexSymbol.EOF){
                        this.report().reportError("Unexpected token found at the end of arguments", iteratorStack.peek_iterator_stack().peek_ahead());
                        s = null;
                        break exitIf;
                    }
                    if(definedMacros.hasAvailableMacro(s.value.toString(), macroArguments.size())){
                        MACRO.MacroDefinition md = definedMacros.getAvailableMacro(s, macroArguments.size());
                        iteratorStack.push_iterator_stack(new MACRO.MacroIterator(md, macroArguments));
                        s = null;
                    }else{
                        InstructionDefinition id = new InstructionDefinition();
                        id.instruction = s;
                        reusableIterator = BufferUtils.peekEverywhereIteratorFromList(endOfLineData);
                        id.arguments =
                                ExpressionEvaluator.evaluateCommaSeparatedExpressions(reusableIterator, report());
                        for(CompiledExpression e: id.arguments) {
                            if(!e.isValid()){
                                this.report().reportError("Invalid arguments. Skipping over instruction", s);
                                s = null;
                                break exitIf;
                            }
                        }
                        LexSymbol last = this.iteratorStack.peek_iterator_stack().peek_behind();
                        s = new LexSymbol(s.getFile(), s.sym, s.getLine(), s.getColumn(), s.getCharPos(),
                                (int) ((last.getCharPos() + last.getSize()) - s.getCharPos()),
                                s.left, s.right, id);
                        return s;
                    }
                }
            }
        }
        atBOL = s.sym == LexSymbol.LINE_TERMINATOR;

        if (s.sym == AssemblerSym.EOF) {
            atEOF = true;
        }

        if(s.sym == LexSymbol.LABEL){
            if(s.value.toString().startsWith(".")){
                if(lastNonLocalLabel != null){
                    s.value = lastNonLocalLabel.value.toString() + s.value.toString();
                }else{
                    report().reportError("Found local label without nonlocal label", s);
                    return s;
                }
            }else{
                lastNonLocalLabel = s;
            }
        }

        return s;
    }

    public boolean hasNext(){
        return atEOF;
    }

    public void clear(){
        iteratorStack.clear_peek_behind();
        definedMacros.clear();
        filterStack.clear();
        iteratorStack.clear();
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


    public class MacroHolder{

        Map<String, pp> macros = new HashMap<>();

        public boolean hasAvailableMacro(String id, int args){
            if(macros.containsKey(id)){
                return macros.get(id).hasAvailableMacro(args);
            }
            return false;
        }

        public MACRO.MacroDefinition getAvailableMacro(LexSymbol id, int args){
            if(macros.containsKey(id.value)){
                MACRO.MacroDefinition md = macros.get(id.value).getAvailableMacro(args);
                if(md != null){
                    md = md.clone();
                    md.updateID(id);
                    return md;
                }
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
            if(macros.containsKey(mID.getIDName())){
                macros.get(mID.getIDName()).removeMacro(mID);
            }
            //does not contain
        }

        public void clear() {
            macros.clear();
        }

        private class pp{
            private final ArrayList<MACRO.MacroDefinition> macros = new ArrayList<>();

            public void clear(){
                macros.clear();
            }

            public void addMacro(MACRO.MacroDefinition m){

                if(hasMacro(m)){
                    removeMacro(m);
                }
                for (MACRO.MacroDefinition macro : macros) {
                    if (macro.getID().overlaps(m.getID())) {
                        report().reportWarning("Macro Definition '" + m.getID().toString() + "' overlaps with existing definitions", m.getID().getID());
                    }
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
                for (MACRO.MacroDefinition macro : macros) {
                    if (macro.getID().equals(m)) {
                        return true;
                    }
                }
                return false;
            }


            public MACRO.MacroDefinition getAvailableMacro(int args){
                for(int i = macros.size() - 1; i >= 0; i --){
                    MACRO.MacroDefinition m = macros.get(i);
                    if(m.getID().withinArguments(args)){
                        return m;
                    }
                }
                return null;
            }

            public boolean hasAvailableMacro(int args){
                for(int i = macros.size() - 1; i >= 0; i --){
                    MACRO.MacroDefinition m = macros.get(i);
                    if(m.getID().withinArguments(args)){
                        return true;
                    }
                }
                return false;
            }

        }
    }

    public static class InstructionDefinition{
        LexSymbol instruction;
        CompiledExpression[] arguments;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append(instruction.value + " ");

            for(int i = 0; i < arguments.length; i ++){
                if(i != 0) sb.append(", ");
                sb.append(arguments[i].toString());
            }
            return sb.toString();
        }
    }
}
