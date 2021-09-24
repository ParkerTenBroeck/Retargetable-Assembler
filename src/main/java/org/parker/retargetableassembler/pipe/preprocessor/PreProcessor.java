package org.parker.retargetableassembler.pipe.preprocessor;

import org.parker.retargetableassembler.pipe.Report;
import org.parker.retargetableassembler.pipe.preprocessor.expressions.*;
import org.parker.retargetableassembler.pipe.preprocessor.lex.cup.AssemblerSym;
import org.parker.retargetableassembler.pipe.preprocessor.directives.Directives;
import org.parker.retargetableassembler.pipe.preprocessor.directives.other.MACRO;
import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.LexSymbol;
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
    private HashContext constantContext = new HashContext();
    private MacroHolder definedMacros = new MacroHolder();
    private ArrayList<PreProcessorOutputFilter> filterStack = new ArrayList<>();
    private IteratorStack<LexSymbol> iteratorStack = new IteratorStack<LexSymbol>(){

        {
            this.setMaxScannerStackSize(200);
            constantContext.addFunction("sin", 1,  (caller, vals)  -> {
                if(vals[0] instanceof Number){
                    return Math.sin(((Number) vals[0]).doubleValue());
                }else{
                    report.reportError("Invalid type");
                    return VoidType.vd;
                }
            });
            constantContext.addFunction("cos", 1,  (caller, vals)  -> {
                if(vals[0] instanceof Number){
                    return Math.cos(((Number) vals[0]).doubleValue());
                }else{
                    report.reportError("Invalid type");
                    return VoidType.vd;
                }
            });
            constantContext.addFunction("tan", 1,  (caller, vals)  -> {
                if(vals[0] instanceof Number){
                    return Math.tan(((Number) vals[0]).doubleValue());
                }else{
                    report.reportError("Invalid type");
                    return VoidType.vd;
                }
            });
            constantContext.addFunction("sqrt", 1, (caller, vals) -> {
                if(vals[0] instanceof Number){
                    return Math.sqrt(((Number) vals[0]).doubleValue());
                }else{
                    report.reportError("Invalid type");
                    return VoidType.vd;
                }
            });
            constantContext.addFunction("defined", 1, (caller, vals) -> {
                if(vals[0] instanceof Number){
                    return Math.sqrt(((Number) vals[0]).doubleValue());
                }else{
                    report.reportError("Invalid type", caller);
                    return VoidType.vd;
                }
            });
            constantContext.addFunction("toString", 1, (caller, vals) -> vals[0].toString());
            constantContext.addFunction("subString", 2, (caller, vals) -> {
                if(vals[1] instanceof Integer || vals[1] instanceof Long || vals[1] instanceof Short || vals[1] instanceof Byte){
                    return vals[0].toString().substring(((Number) vals[1]).intValue());
                }else{
                    report.reportError("Invalid type", caller);
                    return VoidType.vd;
                }
            });
            constantContext.addFunction("subString", 3, (caller, vals) -> {
                if(vals[1] instanceof Integer || vals[1] instanceof Long || vals[1] instanceof Short || vals[1] instanceof Byte &&
                vals[2] instanceof Integer || vals[2] instanceof Long || vals[2] instanceof Short || vals[2] instanceof Byte){
                    return vals[0].toString().substring(((Number) vals[1]).intValue(), ((Number) vals[1]).intValue());
                }else{
                    report.reportError("Invalid type given for argument 2/3", caller);
                    return VoidType.vd;
                }
            });
            constantContext.addFunction("srtLength", 1, (caller, vals) -> {
                if(vals[1] instanceof String){
                    return vals[0].toString().length();
                }else{
                    report.reportError("Invalid type given for argument 1", caller);
                    return VoidType.vd;
                }
            });
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
            LexSymbol s = super.next_peekless();

                while (s == null || s.getSym() == LexSymbol.EOF) {
                    LexSymbol peek = super.peek_ahead();
                    if (peek == null) {
                        super.next_peekless();
                        return s;
                    } else {
                        s = super.next_peekless();
                    }
                }

            if(s.getSym() == LexSymbol.LABEL || s.getSym() == LexSymbol.IDENTIFIER){
                if(s.getValue().toString().startsWith(".")){
                    if(lastNonLocalLabel != null){
                        s = s.setValue(lastNonLocalLabel.getValue().toString() + s.getValue().toString());
                    }else{
                        report().reportError("Found local label without nonlocal label", s);
                        return s;
                    }
                }else{
                    if(s.getSym() == LexSymbol.LABEL)lastNonLocalLabel = s;
                }
            }

            return s;
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

    public class EvaluatedExpression{
        public final Object val;
        public final CompiledExpression expression;

        public EvaluatedExpression(Object val, CompiledExpression expression){
            this.val = val;
            this.expression = expression;
        }
    }

    public EvaluatedExpression evaluateExpression(){
        CompiledExpression cp = ExpressionEvaluator.evaluateExpression(iteratorStack, report());
        cp.setContext(this.getConstantContext());
        return new EvaluatedExpression(cp.evaluateExpression(), cp);
    }

    private Context getConstantContext() {
        return constantContext;
    }

    public EvaluatedExpression[] evaluateExpressions(){
        CompiledExpression[] expressions = ExpressionEvaluator.evaluateCommaSeparatedExpressions(iteratorStack, report());
        EvaluatedExpression[] results = new EvaluatedExpression[expressions.length];
        for(int i = 0; i < expressions.length; i ++){
            expressions[i].setContext(getConstantContext());
            results[i] = new EvaluatedExpression(expressions[i].evaluateExpression(), expressions[i]);
        }
        return results;
    }

    @Override
    public LexSymbol next() {

        LexSymbol s = null;

        while(s == null || (s.getSym() == LexSymbol.LINE_TERMINATOR && this.atBOL)) {
            s = iteratorStack.next();
            if (s.getSym() == AssemblerSym.EOF) {
                atEOF = true;
                return s;
            }
            while (s.getSym() == AssemblerSym.DIRECTIVE && directives.hasDirective((String) s.getValue())) {
                directives.handleDirective((String) s.getValue(), s, this);
                s = iteratorStack.next();
            }

            for (int i = filterStack.size() - 1; i >= 0; i--) {
                s = filterStack.get(i).filterOutput(s);
                if (s == null) {
                    break;
                }
            }

            if(s != null) {
                while (s.getSym() == AssemblerSym.DIRECTIVE && directives.hasStranglerDirective((String) s.getValue())) {
                    directives.handleStranglerDirective((String) s.getValue(), s, this);
                    s = iteratorStack.next();
                }

                exitIf:
                if(s.getSym() == LexSymbol.INSTRUCTION){
                    Iterator<LexSymbol> endOfLineIterator = BufferUtils.tillLineTerminator(iteratorStack, true);
                    List<LexSymbol> endOfLineData = BufferUtils.iteratorToArrayList(endOfLineIterator);
                    final LexSymbol fs = s;
                    endOfLineData.forEach(lexSymbol -> lexSymbol.setParent(fs));
                    PeekEverywhereIterator<LexSymbol> reusableIterator = BufferUtils.peekEverywhereIteratorFromList(endOfLineData);

                    List<List<LexSymbol>> macroArguments = MACRO.splitMacroArguments(reusableIterator);

                    if(!reusableIterator.hasNext() && reusableIterator.peek_ahead().getSym() != LexSymbol.LINE_TERMINATOR && reusableIterator.peek_ahead().getSym() != LexSymbol.EOF){
                        this.report().reportError("Unexpected token found at the end of arguments", reusableIterator.peek_ahead());
                        s = null;
                        break exitIf;
                    }
                    if(definedMacros.hasAvailableMacro(s.getValue().toString(), macroArguments.size())){
                        MACRO.MacroDefinition md = definedMacros.getAvailableMacro(s, macroArguments.size());
                        for(int i = md.getID().getHigherOp(), iter = macroArguments.size() - 1; i < iter; i ++){ //combine and remove extra arguments for greedy macros
                            macroArguments.get(md.getID().getHigherOp()).addAll(macroArguments.get(md.getID().getHigherOp() + 1 ));
                            macroArguments.remove(md.getID().getHigherOp() + 1);
                        }
                        for(int i = 0; i < macroArguments.size() - 1; i ++){ //remove commas from arguments
                            macroArguments.get(i).remove(macroArguments.get(i).size() - 1);
                        }
                        iteratorStack.push_iterator_stack(new MACRO.MacroIterator(s, md, macroArguments));
                        s = null;
                    }else{
                        InstructionDefinition id = new InstructionDefinition();
                        id.instruction = s;
                        reusableIterator = BufferUtils.peekEverywhereIteratorFromList(endOfLineData);
                        id.arguments =
                                ExpressionEvaluator.evaluateCommaSeparatedExpressions(reusableIterator, report());
                        for(CompiledExpression e: id.arguments) {
                            if(e == null || !e.isValid()){
                                this.report().reportError("Invalid arguments. Skipping over instruction", s);
                                s = null;
                                break exitIf;
                            }
                        }
                        if(!reusableIterator.hasNext() && reusableIterator.peek_ahead().getSym() != LexSymbol.LINE_TERMINATOR && reusableIterator.peek_ahead().getSym() != LexSymbol.EOF){
                            this.report().reportError("Unexpected token found at the end of arguments", reusableIterator.peek_ahead());
                            s = null;
                            break exitIf;
                        }

                        LexSymbol last = this.iteratorStack.peek_iterator_stack().peek_behind();
                        s = LexSymbol.combine(s.getSym(), id, s, last);
                        //s = new LexSymbol(s.getFile(), s.getSym(), s.getLine(), s.getColumn(), s.getCharPos(),
                        //        (int) ((last.getCharPos() + last.getSize()) - s.getCharPos()),
                        //        s.left, s.right, id);
                        return s;
                    }
                }
            }
            atBOL = s!= null && s.getSym() == LexSymbol.LINE_TERMINATOR;
        }


        if (s.getSym() == AssemblerSym.EOF) {
            atEOF = true;
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
            if(macros.containsKey(id.getValue())){
                MACRO.MacroDefinition md = macros.get(id.getValue()).getAvailableMacro(args);
                if(md != null){
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

            sb.append(instruction.getValue() + " ");

            for(int i = 0; i < arguments.length; i ++){
                if(i != 0) sb.append(", ");
                sb.append(arguments[i].toString());
            }
            return sb.toString();
        }
    }
}
