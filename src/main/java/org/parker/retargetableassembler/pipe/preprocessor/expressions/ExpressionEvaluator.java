package org.parker.retargetableassembler.pipe.preprocessor.expressions;

import org.parker.retargetableassembler.pipe.preprocessor.PreProcessorReportWrapper;
import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.util.iterators.PeekEverywhereIterator;

import java.math.BigDecimal;
import java.util.*;

public class ExpressionEvaluator {

    public static $CompiledExpression[] evaluateCommaSeparatedExpressions(PeekEverywhereIterator<LexSymbol> iterator, PreProcessorReportWrapper report){
        ArrayList<$CompiledExpression> expressions = new ArrayList<>();
        while(iterator.peek_ahead().sym != LexSymbol.LINE_TERMINATOR && iterator.peek_ahead().sym != LexSymbol.EOF){
            expressions.add(evaluateExpression(iterator, report));
            if(iterator.peek_ahead().sym != LexSymbol.COMMA){
                break;
            }else{
                iterator.next();
            }
        }
        return expressions.toArray(new $CompiledExpression[0]);
    }

    public static $CompiledExpression evaluateExpression(PeekEverywhereIterator<LexSymbol> iterator, PreProcessorReportWrapper report){
        try {
            return new $CompiledExpression(iterator, report);
        }catch (Exception ignored){
            return null;
        }
    }

    protected static class $CompiledExpression extends CompiledExpression{

        AST compiledExpression;

        public $CompiledExpression(PeekEverywhereIterator<LexSymbol> iterator, PreProcessorReportWrapper report){
            this.setReport(report);
            this.compiledExpression = parseLevel15(iterator);
        }

        @Override
        public Object evaluateExpression() {
            try {
                return compiledExpression.evaluate();
            }catch (Exception ignored){
                return null;
            }
        }

        @Override
        public List<LexSymbol> toSymbols() {
            return compiledExpression.toSymbols();
        }

        @Override
        public String toString() {
            if(compiledExpression == null) return "";
            return compiledExpression.toString();
        }

        public Node<LexSymbol> getAsTree(){
            return compiledExpression;
        }

        public enum TreeType{
            ArrayAccess(1),
            ArrayDeclaration(2),
            AssignmentOperator(3),
            BinaryOperator(4),
            CommaSeparatedList(5),
            Constant(6),
            FunctionCall(7),
            Parenthesis(8),
            TernaryOperator(9),
            TypeCast(10),
            UnaryOperator(11),
            Variable(12);

            public final int ID;
            TreeType(int ID){
                this.ID = ID;
            }
        }

        @SuppressWarnings("unused")
        public interface Node<T>{
            default boolean isLeaf(){
                return false;
            }
            default T getValue(){
                return null;
            }
            List<Node<T>> getChildren();
            int getNumChildren();
            Node<T> getChild(int child);
            TreeType getType();
        }

        public static class SymbolLeaf implements Node<LexSymbol>{

            private final LexSymbol id;

            public SymbolLeaf(LexSymbol id){
                this.id = id;
            }

            @Override
            public List<Node<LexSymbol>> getChildren() {
                return null;
            }

            @Override
            public int getNumChildren() {
                return 0;
            }

            @Override
            public Node<LexSymbol> getChild(int child) {
                throw new IndexOutOfBoundsException();
            }

            @Override
            public LexSymbol getValue() {
                return id;
            }

            @Override
            public TreeType getType() {
                return null;
            }

            @Override
            public boolean isLeaf() {
                return true;
            }
        }

        /* expression parts */
        public static abstract class AST implements Node<LexSymbol>{
            public abstract Object evaluate();
            public abstract List<LexSymbol> toSymbols();
        }

        public static class Parenthesis extends AST{
            LexSymbol b1;
            AST inside;
            LexSymbol b2;

            @Override
            public String toString() {
                return  "(" + inside.toString() + ")";
            }

            @Override
            public Object evaluate() {
                return inside.evaluate();
            }

            @Override
            public List<LexSymbol> toSymbols() {
                List<LexSymbol> symbols = new ArrayList<>();
                symbols.add(b1);
                symbols.addAll(inside.toSymbols());
                symbols.add(b2);
                return symbols;
            }

            @Override
            public List<Node<LexSymbol>> getChildren() {
                return Arrays.asList(new SymbolLeaf(b1), inside, new SymbolLeaf(b2));
            }

            @Override
            public int getNumChildren() {
                return 3;
            }

            @Override
            public Node<LexSymbol> getChild(int child) {
                return getChildren().get(child);
            }

            @Override
            public TreeType getType() {
                return TreeType.Parenthesis;
            }
        }

        public class Variable extends AST{
            LexSymbol variableIdent;

            public Variable(LexSymbol sym) {
                variableIdent = sym;
            }

            @Override
            public Object evaluate() {
                if(getContext() == null){
                    getReport().reportError("Given Context is null aborting evaluation", this.toSymbols());
                    throw new RuntimeException();
                }
                if(getContext().hasVariable(variableIdent.value.toString())){
                    return getContext().getVariable(variableIdent.value.toString());
                }else{
                    getReport().reportError("'" + variableIdent.value + "' is not defined in the current scope", variableIdent);
                    return null;
                }
            }

            @Override
            public String toString() {
                return variableIdent.value.toString();
            }

            @Override
            public List<LexSymbol> toSymbols() {
                return Collections.singletonList(variableIdent);
            }

            @Override
            public List<Node<LexSymbol>> getChildren() {
                return null;
            }

            @Override
            public int getNumChildren() {
                return 0;
            }

            @Override
            public Node<LexSymbol> getChild(int child) {
                throw new IndexOutOfBoundsException();
            }

            @Override
            public boolean isLeaf() {
                return true;
            }

            @Override
            public LexSymbol getValue() {
                return variableIdent;
            }

            @Override
            public TreeType getType() {
                return TreeType.Variable;
            }
        }

        public static class Constant extends AST{
            LexSymbol constant;

            public Constant(LexSymbol sym) {
                constant = sym;
            }

            @Override
            public Object evaluate() {
                return constant.value;
            }

            @Override
            public String toString() {
                if(constant.sym == LexSymbol.STRING_LITERAL){
                    return '"' + constant.value.toString() + '"';
                }else{
                    return constant.value.toString();
                }
            }

            @Override
            public List<LexSymbol> toSymbols() {
                return Collections.singletonList(constant);
            }

            @Override
            public List<Node<LexSymbol>> getChildren() {
                return null;
            }

            @Override
            public int getNumChildren() {
                return 0;
            }

            @Override
            public Node<LexSymbol> getChild(int child) {
                throw new IndexOutOfBoundsException();
            }

            @Override
            public boolean isLeaf() {
                return true;
            }

            @Override
            public LexSymbol getValue() {
                return constant;
            }

            @Override
            public TreeType getType() {
                return TreeType.Constant;
            }
        }

        public class TypeCast extends AST{
            LexSymbol b1;
            LexSymbol type;
            LexSymbol b2;
            AST o1;

            @Override
            public String toString() {
                return "(" + type.value.toString() + ")" + o1.toString();
            }

            @Override
            public Object evaluate() {
                if(getContext() == null){
                    getReport().reportError("Given Context is null aborting evaluation", this.toSymbols());
                    throw new RuntimeException();
                }
                if(getContext().hasTypeCast(type.value.toString())){
                    return getContext().evaluateTypeCast(type.value.toString(), o1.evaluate());
                }else{
                    getReport().reportError("Current context does not have type cast", type);
                    return null;
                }
            }

            @Override
            public List<LexSymbol> toSymbols() {
                List<LexSymbol> symbols = new ArrayList<>();
                symbols.add(b1);
                symbols.add(type);
                symbols.add(b2);
                symbols.addAll(o1.toSymbols());
                return symbols;
            }

            @Override
            public List<Node<LexSymbol>> getChildren() {
                return Arrays.asList(new SymbolLeaf(b1), new SymbolLeaf(type), new SymbolLeaf(b2), o1);
            }

            @Override
            public int getNumChildren() {
                return 4;
            }

            @Override
            public Node<LexSymbol> getChild(int child) {
                return getChildren().get(child);
            }

            @Override
            public TreeType getType() {
                return TreeType.TypeCast;
            }
        }

        public abstract static class UnaryOperator extends AST{
            LexSymbol s;
            String sRep;
            AST o1;

            @Override
            public String toString() {
                return (sRep == null ? LexSymbol.terminalNames[s.sym] : sRep) + " " + o1.toString();
            }

            @Override
            public List<LexSymbol> toSymbols() {
                List<LexSymbol> symbols = new ArrayList<>();
                symbols.add(s);
                symbols.addAll(o1.toSymbols());
                return symbols;
            }

            @Override
            public List<Node<LexSymbol>> getChildren() {
                return Arrays.asList(new SymbolLeaf(s), o1);
            }

            @Override
            public int getNumChildren() {
                return 2;
            }

            @Override
            public Node<LexSymbol> getChild(int child) {
                return getChildren().get(child);
            }

            @Override
            public TreeType getType() {
                return TreeType.UnaryOperator;
            }
        }

        public abstract static class BinaryOperator extends AST{
            AST o1;
            LexSymbol s;
            String sRep;
            AST o2;

            @Override
            public String toString() {
                return o1.toString() + " " + (sRep == null ? LexSymbol.terminalNames[s.sym] : sRep) + " " + o2.toString();
            }

            @Override
            public List<LexSymbol> toSymbols() {
                List<LexSymbol> symbols = new ArrayList<>(o1.toSymbols());
                symbols.add(s);
                symbols.addAll(o2.toSymbols());
                return symbols;
            }

            @Override
            public List<Node<LexSymbol>> getChildren() {
                return Arrays.asList(o1, new SymbolLeaf(s), o2);
            }

            @Override
            public int getNumChildren() {
                return 3;
            }

            @Override
            public Node<LexSymbol> getChild(int child) {
                return getChildren().get(child);
            }

            @Override
            public TreeType getType() {
                return TreeType.BinaryOperator;
            }
        }

        public abstract static class AssignmentOperator extends AST{
            LexSymbol id;
            LexSymbol s;
            String sRep;
            AST o1;

            @Override
            public String toString() {
                return id.value.toString() + " " + (sRep == null ? LexSymbol.terminalNames[s.sym] : sRep) + " " + o1.toString();
            }

            @Override
            public List<LexSymbol> toSymbols() {
                List<LexSymbol> symbols = new ArrayList<>();
                symbols.add(id);
                symbols.add(s);
                symbols.addAll(o1.toSymbols());
                return symbols;
            }

            @Override
            public List<Node<LexSymbol>> getChildren() {
                return Arrays.asList(new SymbolLeaf(id), new SymbolLeaf(s), o1);
            }

            @Override
            public int getNumChildren() {
                return 3;
            }

            @Override
            public Node<LexSymbol> getChild(int child) {
                return getChildren().get(child);
            }

            @Override
            public TreeType getType() {
                return TreeType.AssignmentOperator;
            }
        }

        public abstract static class TernaryOperator extends AST{
            AST o1;
            LexSymbol s1;
            String s1Rep;
            AST o2;
            LexSymbol s2;
            String s2Rep;
            AST o3;

            @Override
            public String toString() {
                return o1.toString() + " " + (s1Rep == null ? LexSymbol.terminalNames[s1.sym] : s1Rep) + " " + o2.toString() + " " + (s2Rep == null ? LexSymbol.terminalNames[s2.sym] : s2Rep) + " " + o3.toString();
            }

            @Override
            public List<LexSymbol> toSymbols() {
                List<LexSymbol> symbols = new ArrayList<>(o1.toSymbols());
                symbols.add(s1);
                symbols.addAll(o2.toSymbols());
                symbols.add(s2);
                symbols.addAll(o3.toSymbols());
                return symbols;
            }

            @Override
            public List<Node<LexSymbol>> getChildren() {
                return Arrays.asList(o1, new SymbolLeaf(s1), o2, new SymbolLeaf(s2), o3);
            }

            @Override
            public int getNumChildren() {
                return 5;
            }

            @Override
            public Node<LexSymbol> getChild(int child) {
                return getChildren().get(child);
            }

            @Override
            public TreeType getType() {
                return TreeType.TernaryOperator;
            }
        }

        public static class CommaSeparatedList extends AST {

            private static class item{
                AST exp;
                LexSymbol comma;

                public item(AST exp, LexSymbol comma){
                    this.exp = exp;
                    this.comma = comma;
                }
            }
            private final ArrayList<item> list = new ArrayList<>();

            public void addItem(AST exp){
                addItem(exp, null);
            }
            public void addItem(AST exp, LexSymbol comma){
                list.add(new item(exp, comma));
            }

            @Override
            public Object[] evaluate() {
                Object[] result = new Object[list.size()];
                for(int i = 0; i < list.size(); i ++){
                    result[i] = list.get(i).exp.evaluate();
                }
                return result;
            }

            @Override
            public List<LexSymbol> toSymbols() {
                List<LexSymbol> symbols = new ArrayList<>();
                for (CommaSeparatedList.item item : list) {
                    symbols.addAll(item.exp.toSymbols());
                    if (item.comma != null) {
                        symbols.add(item.comma);
                    }
                }
                return symbols;
            }

            @Override
            public String toString() {
                StringBuilder s = new StringBuilder();
                for (CommaSeparatedList.item item : list) {
                    s.append(item.exp.toString());
                    if (item.comma != null) {
                        s.append(", ");
                    }
                }
                return s.toString();
            }

            public int getNum(){
                return list.size();
            }


            @Override
            public List<Node<LexSymbol>> getChildren() {
                List<Node<LexSymbol>> items = new ArrayList<>();
                for (CommaSeparatedList.item item : list) {
                    items.add(item.exp);
                    if (item.comma != null) {
                        items.add(new SymbolLeaf(item.comma));
                    }
                }
                return items;
            }

            @Override
            public int getNumChildren() {
                return getChildren().size();
            }

            @Override
            public Node<LexSymbol> getChild(int child) {
                return getChildren().get(child);
            }

            @Override
            public TreeType getType() {
                return TreeType.CommaSeparatedList;
            }
        }

        public class ArrayAccess extends AST{

            AST array;
            LexSymbol b1;
            AST argument;
            LexSymbol b2;

            @Override
            public Object evaluate() {
                Object arr = array.evaluate();
                Object index = argument.evaluate();
                if(arr instanceof Object[]){
                    if(index instanceof Long || index instanceof Integer || index instanceof Short || index instanceof Byte){
                        int i = ((Number) index).intValue();
                        Object[] ar = (Object[]) arr;
                        if(i >= ar.length || i < 0){
                            getReport().reportError("Index out of bounds: " + i, this.toSymbols());
                            return null;
                        }else{
                            return ar[i];
                        }
                    }else{
                        getReport().reportError("Cannot use " + index.getClass().getSimpleName() + " as an index", this.toSymbols());
                        return null;
                    }
                }else{
                    getReport().reportError("Cannot access member as array", this.toSymbols());
                    return null;
                }
            }

            @Override
            public String toString() {
                return  array.toString() + "[" + argument.toString() + "]";
            }

            @Override
            public List<LexSymbol> toSymbols() {
                List<LexSymbol> symbols = new ArrayList<>(array.toSymbols());
                symbols.add(b1);
                symbols.addAll(argument.toSymbols());
                symbols.add(b2);
                return symbols;
            }

            @Override
            public List<Node<LexSymbol>> getChildren() {
                return Arrays.asList(array, new SymbolLeaf(b1), argument, new SymbolLeaf(b2));
            }

            @Override
            public int getNumChildren() {
                return 4;
            }

            @Override
            public Node<LexSymbol> getChild(int child) {
                return getChildren().get(child);
            }

            @Override
            public TreeType getType() {
                return TreeType.ArrayAccess;
            }
        }

        public static class ArrayDeclaration extends AST{

            LexSymbol b1;
            CommaSeparatedList arguments;
            LexSymbol b2;

            @Override
            public String toString() {
                return  "{" + arguments.toString() + "}";
            }

            @Override
            public Object evaluate() {
                return arguments.evaluate();
            }

            @Override
            public List<LexSymbol> toSymbols() {
                List<LexSymbol> symbols = new ArrayList<>();
                symbols.add(b1);
                symbols.addAll(arguments.toSymbols());
                symbols.add(b2);
                return symbols;
            }

            @Override
            public List<Node<LexSymbol>> getChildren() {
                return Arrays.asList(new SymbolLeaf(b1), arguments, new SymbolLeaf(b2));
            }

            @Override
            public int getNumChildren() {
                return 3;
            }

            @Override
            public Node<LexSymbol> getChild(int child) {
                return getChildren().get(child);
            }

            @Override
            public TreeType getType() {
                return TreeType.ArrayDeclaration;
            }
        }

        public class FunctionCall extends AST{
            LexSymbol funcIdent;
            LexSymbol b1;
            CommaSeparatedList arguments;
            LexSymbol b2;

            @Override
            public Object evaluate() {
                if(getContext() == null){
                    getReport().reportError("Given Context is null aborting evaluation", this.toSymbols());
                    throw new RuntimeException();
                }
                if(getContext().hasFunction(funcIdent.value.toString(), arguments == null ? 0 : arguments.getNum())){
                    return getContext().evaluateFunction(funcIdent.value.toString(), arguments.getNum(), arguments.evaluate());
                }else{
                    getReport().reportError(funcIdent);
                    return null;
                }
            }

            @Override
            public String toString() {
                return funcIdent.value.toString() + "(" + arguments.toString() + ")" ;
            }

            @Override
            public List<LexSymbol> toSymbols() {
                List<LexSymbol> symbols = new ArrayList<>();
                symbols.add(funcIdent);
                symbols.add(b1);
                symbols.addAll(arguments.toSymbols());
                symbols.add(b2);
                return symbols;
            }

            @Override
            public List<Node<LexSymbol>> getChildren() {
                return Arrays.asList(new SymbolLeaf(funcIdent), new SymbolLeaf(b1), arguments, new SymbolLeaf(b2));
            }

            @Override
            public int getNumChildren() {
                return 4;
            }

            @Override
            public Node<LexSymbol> getChild(int child) {
                return getChildren().get(child);
            }

            @Override
            public TreeType getType() {
                return TreeType.FunctionCall;
            }
        }
        /* end of expression parts */
        /* start of expression compiler */

        //comma separated list
        public AST parseLevel16(PeekEverywhereIterator<LexSymbol> iterator){
            CommaSeparatedList csl = new CommaSeparatedList();
            while(true){
                AST item = parseLevel14(iterator);
                if(iterator.peek_ahead().sym == LexSymbol.COMMA){
                    csl.addItem(item, iterator.next());
                }else{
                    csl.addItem(item);
                    break;
                }
            }
            return csl;
        }

        public AST parseLevel15(PeekEverywhereIterator<LexSymbol> iterator){
            AST ast = parseLevel14(iterator);

            for(;;) {
                if (iterator.peek_ahead().sym == LexSymbol.COMMA) {
                    BinaryOperator bo = new BinaryOperator() {
                        @Override
                        public Object evaluate() {
                            o1.evaluate();
                            return o2.evaluate();
                        }
                    };
                    bo.o1 = ast;
                    bo.s = iterator.next();
                    bo.sRep = ",";
                    bo.o2 = parseLevel14(iterator);
                    ast = bo;
                }else{
                    return ast;
                }
            }
        }

        void t(int i ){
            System.out.println(i);
        }


        public AST parseLevel14(PeekEverywhereIterator<LexSymbol> iterator){
            int x = 10;
            t(x + 1);

            LexSymbol peekID = iterator.peek_ahead();
            LexSymbol peekAss = iterator.peek_ahead(1);

            if (peekAss.sym == LexSymbol.EQ && peekID.sym == LexSymbol.IDENTIFIER) {
                AssignmentOperator ao = new AssignmentOperator() {
                    @Override
                    public Object evaluate() {
                        getContext().setVariable(id.value.toString(), o1.evaluate());
                        return getContext().getVariable(id.value.toString());
                    }
                };
                ao.id = iterator.next();
                ao.s = iterator.next();
                ao.sRep = "=";
                ao.o1 = parseLevel14(iterator);
                return ao;
            }else if (peekAss.sym == LexSymbol.PLUSEQ && peekID.sym == LexSymbol.IDENTIFIER) {
                AssignmentOperator ao = new AssignmentOperator() {
                    @Override
                    public Object evaluate() {
                        if(getContext().hasVariable(id.value.toString())) {
                            Object o1 = getContext().getVariable(id.value.toString());
                            Object o2 = this.o1.evaluate();
                            Object result;
                            if(o1 instanceof Number && o2 instanceof Number){
                                result = add((Number)o1, (Number)o2);
                            }else if(o1 instanceof String && o2 instanceof String){
                                result = o1.toString() +  o2.toString();
                            }else{
                                getReport().reportError("Cannot add " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                            getContext().setVariable(id.value.toString(), result);
                            return getContext().getVariable(id.value.toString());
                        }else{
                            getReport().reportError("variable is not declared", id);
                            return null;
                        }
                    }
                };
                ao.id = iterator.next();
                ao.s = iterator.next();
                ao.sRep = "+=";
                ao.o1 = parseLevel14(iterator);
                return ao;
            }else if (peekAss.sym == LexSymbol.MINUSEQ && peekID.sym == LexSymbol.IDENTIFIER) {
                AssignmentOperator ao = new AssignmentOperator() {
                    @Override
                    public Object evaluate() {
                        if(getContext().hasVariable(id.value.toString())) {
                            Object o1 = getContext().getVariable(id.value.toString());
                            Object o2 = this.o1.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number){
                                getContext().setVariable(id.value.toString(), subtract((Number)o1, (Number)o2));
                                return getContext().getVariable(id.value.toString());
                            }else{
                                getReport().reportError("Cannot subtract " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }else{
                            getReport().reportError("variable is not declared", id);
                            return null;
                        }
                    }
                };
                ao.id = iterator.next();
                ao.s = iterator.next();
                ao.sRep = "-=";
                ao.o1 = parseLevel14(iterator);
                return ao;
            }else if (peekAss.sym == LexSymbol.MULTEQ && peekID.sym == LexSymbol.IDENTIFIER) {
                AssignmentOperator ao = new AssignmentOperator() {
                    @Override
                    public Object evaluate() {
                        if(getContext().hasVariable(id.value.toString())) {
                            Object o1 = getContext().getVariable(id.value.toString());
                            Object o2 = this.o1.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number){
                                getContext().setVariable(id.value.toString(), multiply((Number)o1, (Number)o2));
                                return getContext().getVariable(id.value.toString());
                            }else{
                                getReport().reportError("Cannot multiply " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }else{
                            getReport().reportError("variable is not declared", id);
                            return null;
                        }
                    }
                };
                ao.id = iterator.next();
                ao.s = iterator.next();
                ao.sRep = "*=";
                ao.o1 = parseLevel14(iterator);
                return ao;
            }else if (peekAss.sym == LexSymbol.DIVEQ && peekID.sym == LexSymbol.IDENTIFIER) {
                AssignmentOperator ao = new AssignmentOperator() {
                    @Override
                    public Object evaluate() {
                        if(getContext().hasVariable(id.value.toString())) {
                            Object o1 = getContext().getVariable(id.value.toString());
                            Object o2 = this.o1.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number){
                                getContext().setVariable(id.value.toString(), divide((Number)o1, (Number)o2));
                                return getContext().getVariable(id.value.toString());
                            }else{
                                getReport().reportError("Cannot divide " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }else{
                            getReport().reportError("variable is not declared", id);
                            return null;
                        }
                    }
                };
                ao.id = iterator.next();
                ao.s = iterator.next();
                ao.sRep = "/=";
                ao.o1 = parseLevel14(iterator);
                return ao;
            }else if (peekAss.sym == LexSymbol.MODEQ && peekID.sym == LexSymbol.IDENTIFIER) {
                AssignmentOperator ao = new AssignmentOperator() {
                    @Override
                    public Object evaluate() {
                        if(getContext().hasVariable(id.value.toString())) {
                            Object o1 = getContext().getVariable(id.value.toString());
                            Object o2 = this.o1.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number){
                                getContext().setVariable(id.value.toString(), mod((Number)o1, (Number)o2));
                                return getContext().getVariable(id.value.toString());

                            }else{
                                getReport().reportError("Cannot mod " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }else{
                            getReport().reportError("variable is not declared", id);
                            return null;
                        }
                    }
                };
                ao.id = iterator.next();
                ao.s = iterator.next();
                ao.sRep = "%=";
                ao.o1 = parseLevel14(iterator);
                return ao;
            }else if (peekAss.sym == LexSymbol.ANDEQ && peekID.sym == LexSymbol.IDENTIFIER) {
                AssignmentOperator ao = new AssignmentOperator() {
                    @Override
                    public Object evaluate() {
                        if(getContext().hasVariable(id.value.toString())) {
                            Object o1 = getContext().getVariable(id.value.toString());
                            Object o2 = this.o1.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number && !(o1 instanceof Double || o2 instanceof Double || o1 instanceof Float || o2 instanceof Float)){
                                getContext().setVariable(id.value.toString(), bitwiseAnd((Number)o1, (Number)o2));
                                return getContext().getVariable(id.value.toString());
                            }else{
                                getReport().reportError("Cannot and " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }else{
                            getReport().reportError("variable is not declared", id);
                            return null;
                        }
                    }
                };
                ao.id = iterator.next();
                ao.s = iterator.next();
                ao.sRep = "&=";
                ao.o1 = parseLevel14(iterator);
                return ao;
            }else if (peekAss.sym == LexSymbol.OREQ && peekID.sym == LexSymbol.IDENTIFIER) {
                AssignmentOperator ao = new AssignmentOperator() {
                    @Override
                    public Object evaluate() {
                        if(getContext().hasVariable(id.value.toString())) {
                            Object o1 = getContext().getVariable(id.value.toString());
                            Object o2 = this.o1.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number && !(o1 instanceof Double || o2 instanceof Double || o1 instanceof Float || o2 instanceof Float)){
                                getContext().setVariable(id.value.toString(), bitwiseOr((Number)o1, (Number)o2));
                                return getContext().getVariable(id.value.toString());
                            }else{
                                getReport().reportError("Cannot and " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }else{
                            getReport().reportError("variable is not declared", id);
                            return null;
                        }
                    }
                };
                ao.id = iterator.next();
                ao.s = iterator.next();
                ao.sRep = "|=";
                ao.o1 = parseLevel14(iterator);
                return ao;
            }else if (peekAss.sym == LexSymbol.XOREQ && peekID.sym == LexSymbol.IDENTIFIER) {
                AssignmentOperator ao = new AssignmentOperator() {
                    @Override
                    public Object evaluate() {
                        if(getContext().hasVariable(id.value.toString())) {
                            Object o1 = getContext().getVariable(id.value.toString());
                            Object o2 = this.o1.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number && !(o1 instanceof Double || o2 instanceof Double || o1 instanceof Float || o2 instanceof Float)){
                                getContext().setVariable(id.value.toString(), bitwiseXor((Number)o1, (Number)o2));
                                return getContext().getVariable(id.value.toString());
                            }else{
                                getReport().reportError("Cannot xor " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }else{
                            getReport().reportError("variable is not declared", id);
                            return null;
                        }
                    }
                };
                ao.id = iterator.next();
                ao.s = iterator.next();
                ao.sRep = "^=";
                ao.o1 = parseLevel14(iterator);
                return ao;
            }else if (peekAss.sym == LexSymbol.LSHIFTEQ && peekID.sym == LexSymbol.IDENTIFIER) {
                AssignmentOperator ao = new AssignmentOperator() {
                    @Override
                    public Object evaluate() {
                        if(getContext().hasVariable(id.value.toString())) {
                            Object o1 = getContext().getVariable(id.value.toString());
                            Object o2 = this.o1.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number && !(o1 instanceof Double || o2 instanceof Double || o1 instanceof Float || o2 instanceof Float)){
                                getContext().setVariable(id.value.toString(), shiftLeft((Number)o1, (Number)o2));
                                return getContext().getVariable(id.value.toString());
                            }else{
                                getReport().reportError("Cannot shift left " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }else{
                            getReport().reportError("variable is not declared", id);
                            return null;
                        }
                    }
                };
                ao.id = iterator.next();
                ao.s = iterator.next();
                ao.sRep = "<<=";
                ao.o1 = parseLevel14(iterator);
                return ao;
            }else if (peekAss.sym == LexSymbol.URSHIFTEQ && peekID.sym == LexSymbol.IDENTIFIER) {
                AssignmentOperator ao = new AssignmentOperator() {
                    @Override
                    public Object evaluate() {
                        if(getContext().hasVariable(id.value.toString())) {
                            Object o1 = getContext().getVariable(id.value.toString());
                            Object o2 = this.o1.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number && !(o1 instanceof Double || o2 instanceof Double || o1 instanceof Float || o2 instanceof Float)){
                                getContext().setVariable(id.value.toString(), uShiftRight((Number)o1, (Number)o2));
                                return getContext().getVariable(id.value.toString());
                            }else{
                                getReport().reportError("Cannot uShiftRight " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }else{
                            getReport().reportError("variable is not declared", id);
                            return null;
                        }
                    }
                };
                ao.id = iterator.next();
                ao.s = iterator.next();
                ao.sRep = ">>>=";
                ao.o1 = parseLevel14(iterator);
                return ao;
            }else if (peekAss.sym == LexSymbol.RSHIFTEQ && peekID.sym == LexSymbol.IDENTIFIER) {
                AssignmentOperator ao = new AssignmentOperator() {
                    @Override
                    public Object evaluate() {
                        if(getContext().hasVariable(id.value.toString())) {
                            Object o1 = getContext().getVariable(id.value.toString());
                            Object o2 = this.o1.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number && !(o1 instanceof Double || o2 instanceof Double || o1 instanceof Float || o2 instanceof Float)){
                                getContext().setVariable(id.value.toString(), bitwiseOr((Number)o1, (Number)o2));
                                return getContext().getVariable(id.value.toString());
                            }else{
                                getReport().reportError("Cannot right shift " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }else{
                            getReport().reportError("variable is not declared", id);
                            return null;
                        }
                    }
                };
                ao.id = iterator.next();
                ao.s = iterator.next();
                ao.sRep = ">>=";
                ao.o1 = parseLevel14(iterator);
                return ao;
            }else{
                return parseLevel13(iterator);
            }
        }

        public AST parseLevel13(PeekEverywhereIterator<LexSymbol> iterator){
            AST ast = parseLevel12(iterator);

            if(iterator.peek_ahead().sym == LexSymbol.QUESTION){
                TernaryOperator to = new TernaryOperator() {
                    @Override
                    public Object evaluate() {
                        Object o1 = this.o1.evaluate();
                        Object o2 = this.o2.evaluate();
                        Object o3 = this.o3.evaluate();
                        if(o1 instanceof Boolean){
                            return ((Boolean)o1) ? o2 : o3;
                        }else{
                            getReport().reportError("Expected boolean got " + o1.getClass().getSimpleName(), this.o1.toSymbols());
                            return null;
                        }
                    }
                };
                to.o1 = ast;
                to.s1 = iterator.next();
                to.s1Rep = "?";
                to.o2 = parseLevel12(iterator);
                if(iterator.peek_ahead().sym == LexSymbol.COLON){
                    to.s2 = iterator.next();
                    to.s2Rep = ":";
                    to.o3 = parseLevel12(iterator);
                    return to;
                }else{
                    getReport().reportError(iterator.next());
                    return null;
                }
            }else{
                return ast;
            }
        }

        public AST parseLevel12(PeekEverywhereIterator<LexSymbol> iterator){
            AST ast = parseLevel11(iterator);

            for(;;){
                if(iterator.peek_ahead().sym == LexSymbol.OROR){
                    BinaryOperator bo = new BinaryOperator() {
                        @Override
                        public Object evaluate() {
                            Object o1 = this.o1.evaluate();
                            Object o2 = this.o2.evaluate();
                            if(o1 instanceof Boolean && o2 instanceof Boolean){
                                return (Boolean)o1 || (Boolean)o2;
                            }else{
                                getReport().reportError("Cannot oror " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }
                    };
                    bo.o1 = ast;
                    bo.s = iterator.next();
                    bo.sRep = "||";
                    bo.o2 = parseLevel11(iterator);
                    ast = bo;

                }else{
                    return ast;
                }
            }
        }

        public AST parseLevel11(PeekEverywhereIterator<LexSymbol> iterator){
            AST ast = parseLevel10(iterator);

            for(;;){
                if(iterator.peek_ahead().sym == LexSymbol.ANDAND){
                    BinaryOperator bo = new BinaryOperator() {
                        @Override
                        public Object evaluate() {
                            Object o1 = this.o1.evaluate();
                            Object o2 = this.o2.evaluate();
                            if(o1 instanceof Boolean && o2 instanceof Boolean){
                                return (Boolean)o1 && (Boolean)o2;
                            }else{
                                getReport().reportError("Cannot andand " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }
                    };
                    bo.o1 = ast;
                    bo.s = iterator.next();
                    bo.sRep = "&&";
                    bo.o2 = parseLevel10(iterator);
                    ast = bo;

                }else{
                    return ast;
                }
            }
        }

        public AST parseLevel10(PeekEverywhereIterator<LexSymbol> iterator){
            AST ast = parseLevel9(iterator);

            for(;;){
                if(iterator.peek_ahead().sym == LexSymbol.OR){
                    BinaryOperator bo = new BinaryOperator() {
                        @Override
                        public Object evaluate() {
                            Object o1 = this.o1.evaluate();
                            Object o2 = this.o2.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number && !(o1 instanceof Double || o2 instanceof Double || o1 instanceof Float || o2 instanceof Float)){
                                return bitwiseOr((Number)o1, (Number)o2);
                            }else{
                                getReport().reportError("Cannot or " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }
                    };
                    bo.o1 = ast;
                    bo.s = iterator.next();
                    bo.sRep = "|";
                    bo.o2 = parseLevel9(iterator);
                    ast = bo;

                }else{
                    return ast;
                }
            }
        }

        public AST parseLevel9(PeekEverywhereIterator<LexSymbol> iterator){
            AST ast = parseLevel8(iterator);

            for(;;){
                if(iterator.peek_ahead().sym == LexSymbol.XOR){
                    BinaryOperator bo = new BinaryOperator() {
                        @Override
                        public Object evaluate() {
                            Object o1 = this.o1.evaluate();
                            Object o2 = this.o2.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number && !(o1 instanceof Double || o2 instanceof Double || o1 instanceof Float || o2 instanceof Float)){
                                return bitwiseXor((Number)o1, (Number)o2);
                            }else{
                                getReport().reportError("Cannot xor " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }
                    };
                    bo.o1 = ast;
                    bo.s = iterator.next();
                    bo.sRep = "^";
                    bo.o2 = parseLevel8(iterator);
                    ast = bo;

                }else{
                    return ast;
                }
            }
        }

        public AST parseLevel8(PeekEverywhereIterator<LexSymbol> iterator){
            AST ast = parseLevel7(iterator);

            for(;;){
                if(iterator.peek_ahead().sym == LexSymbol.AND){
                    BinaryOperator bo = new BinaryOperator() {
                        @Override
                        public Object evaluate() {
                            Object o1 = this.o1.evaluate();
                            Object o2 = this.o2.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number && !(o1 instanceof Double || o2 instanceof Double || o1 instanceof Float || o2 instanceof Float)){
                                return bitwiseAnd((Number)o1, (Number)o2);
                            }else{
                                getReport().reportError("Cannot and " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }
                    };
                    bo.o1 = ast;
                    bo.s = iterator.next();
                    bo.sRep = "&";
                    bo.o2 = parseLevel7(iterator);
                    ast = bo;

                }else{
                    return ast;
                }
            }
        }

        public AST parseLevel7(PeekEverywhereIterator<LexSymbol> iterator){
            AST ast = parseLevel6(iterator);

            for(;;){
                if(iterator.peek_ahead().sym == LexSymbol.EQEQ){
                    BinaryOperator bo = new BinaryOperator() {
                        @Override
                        public Object evaluate() {
                            Object o1 = this.o1.evaluate();
                            Object o2 = this.o2.evaluate();
                            return o1.equals(o2);
                        }
                    };
                    bo.o1 = ast;
                    bo.s = iterator.next();
                    bo.sRep = "==";
                    bo.o2 = parseLevel6(iterator);
                    ast = bo;

                }else if(iterator.peek_ahead().sym == LexSymbol.NOTEQ){
                    BinaryOperator bo = new BinaryOperator() {
                        @Override
                        public Object evaluate() {
                            Object o1 = this.o1.evaluate();
                            Object o2 = this.o2.evaluate();
                            return !o1.equals(o2);
                        }
                    };
                    bo.o1 = ast;
                    bo.s = iterator.next();
                    bo.sRep = "!=";
                    bo.o2 = parseLevel6(iterator);
                    ast = bo;

                }else{
                    return ast;
                }
            }
        }

        public AST parseLevel6(PeekEverywhereIterator<LexSymbol> iterator){
            AST ast = parseLevel5(iterator);

            for(;;){
                if(iterator.peek_ahead().sym == LexSymbol.LT){
                    BinaryOperator bo = new BinaryOperator() {
                        @Override
                        public Object evaluate() {
                            Object o1 = this.o1.evaluate();
                            Object o2 = this.o2.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number){
                                return NUMBER_COMPARATOR.compare(o1, o2) < 0;
                            }else{
                                getReport().reportError("Cannot compare " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }
                    };
                    bo.o1 = ast;
                    bo.s = iterator.next();
                    bo.sRep = "<";
                    bo.o2 = parseLevel5(iterator);
                    ast = bo;

                }else if(iterator.peek_ahead().sym == LexSymbol.GT){
                    BinaryOperator bo = new BinaryOperator() {
                        @Override
                        public Object evaluate() {
                            Object o1 = this.o1.evaluate();
                            Object o2 = this.o2.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number){
                                return NUMBER_COMPARATOR.compare(o1, o2) > 0;
                            }else{
                                getReport().reportError("Cannot compare " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }
                    };
                    bo.o1 = ast;
                    bo.s = iterator.next();
                    bo.sRep = ">";
                    bo.o2 = parseLevel5(iterator);
                    ast = bo;

                }else if(iterator.peek_ahead().sym == LexSymbol.LTEQ){
                    BinaryOperator bo = new BinaryOperator() {
                        @Override
                        public Object evaluate() {
                            Object o1 = this.o1.evaluate();
                            Object o2 = this.o2.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number){
                                return NUMBER_COMPARATOR.compare(o1, o2) <= 0;
                            }else{
                                getReport().reportError("Cannot compare " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }
                    };
                    bo.o1 = ast;
                    bo.s = iterator.next();
                    bo.sRep = "<=";
                    bo.o2 = parseLevel5(iterator);
                    ast = bo;

                }else if(iterator.peek_ahead().sym == LexSymbol.GTEQ){
                    BinaryOperator bo = new BinaryOperator() {
                        @Override
                        public Object evaluate() {
                            Object o1 = this.o1.evaluate();
                            Object o2 = this.o2.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number){
                                return NUMBER_COMPARATOR.compare(o1, o2) >= 0;
                            }else{
                                getReport().reportError("Cannot compare " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }
                    };
                    bo.o1 = ast;
                    bo.s = iterator.next();
                    bo.sRep = ">=";
                    bo.o2 = parseLevel5(iterator);
                    ast = bo;

                }else{
                    return ast;
                }
            }
        }

        public AST parseLevel5(PeekEverywhereIterator<LexSymbol> iterator){
            AST ast = parseLevel4(iterator);
            for(;;){
                LexSymbol peek = iterator.peek_ahead();
                if(peek.sym == LexSymbol.LSHIFT){
                    BinaryOperator bo = new BinaryOperator() {
                        @Override
                        public Object evaluate() {
                            Object o1 = this.o1.evaluate();
                            Object o2 = this.o2.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number){
                                return shiftLeft((Number)o1, (Number)o2);
                            }else{
                                getReport().reportError("Cannot shiftLeft " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }
                    };
                    bo.o1 = ast;
                    bo.s = iterator.next();
                    bo.sRep = "<<";
                    bo.o2 = parseLevel4(iterator);
                    ast = bo;

                }else if(peek.sym == LexSymbol.URSHIFT){
                    BinaryOperator bo = new BinaryOperator() {
                        @Override
                        public Object evaluate() {
                            Object o1 = this.o1.evaluate();
                            Object o2 = this.o2.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number && !(o1 instanceof Double || o2 instanceof Double || o1 instanceof Float || o2 instanceof Float)){
                                return uShiftRight((Number)o1, (Number)o2);
                            }else{
                                getReport().reportError("Cannot uShiftRight " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }
                    };
                    bo.o1 = ast;
                    bo.s = iterator.next();
                    bo.sRep = ">>>";
                    bo.o2 = parseLevel4(iterator);
                    ast = bo;

                }else if(peek.sym == LexSymbol.RSHIFT){
                    BinaryOperator bo = new BinaryOperator() {
                        @Override
                        public Object evaluate() {
                            Object o1 = this.o1.evaluate();
                            Object o2 = this.o2.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number && !(o1 instanceof Double || o2 instanceof Double || o1 instanceof Float || o2 instanceof Float)){
                                return shiftRight((Number)o1, (Number)o2);
                            }else{
                                getReport().reportError("Cannot shiftRight " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }
                    };
                    bo.o1 = ast;
                    bo.s = iterator.next();
                    bo.sRep = ">>";
                    bo.o2 = parseLevel4(iterator);
                    ast = bo;

                }else{
                    return ast;
                }
            }
        }

        public AST parseLevel4(PeekEverywhereIterator<LexSymbol> iterator){
            AST ast = parseLevel3(iterator);
            for(;;){
                LexSymbol peek = iterator.peek_ahead();
                if(peek.sym == LexSymbol.PLUS){
                    BinaryOperator bo = new BinaryOperator() {
                        @Override
                        public Object evaluate() {
                            Object o1 = this.o1.evaluate();
                            Object o2 = this.o2.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number){
                                return add((Number)o1, (Number)o2);
                            }else if(o1 instanceof String && o2 instanceof String){
                                return o1.toString() +  o2.toString();
                            }else{
                                getReport().reportError("Cannot add " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }
                    };
                    bo.o1 = ast;
                    bo.s = iterator.next();
                    bo.sRep = "+";
                    bo.o2 = parseLevel3(iterator);
                    ast = bo;

                }else if(peek.sym == LexSymbol.MINUS){
                    BinaryOperator bo = new BinaryOperator() {
                        @Override
                        public Object evaluate() {
                            Object o1 = this.o1.evaluate();
                            Object o2 = this.o2.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number){
                                return subtract((Number)o1, (Number)o2);
                            }else{
                                getReport().reportError("Cannot subtract " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }
                    };
                    bo.o1 = ast;
                    bo.s = iterator.next();
                    bo.sRep = "-";
                    bo.o2 = parseLevel3(iterator);
                    ast = bo;

                }else{
                    return ast;
                }
            }
        }

        public AST parseLevel3(PeekEverywhereIterator<LexSymbol> iterator){
            AST ast = parseLevel2(iterator);
            for(;;){
                LexSymbol peek = iterator.peek_ahead();
                if(peek.sym == LexSymbol.MULT){
                    BinaryOperator bo = new BinaryOperator() {
                        @Override
                        public Object evaluate() {
                            Object o1 = this.o1.evaluate();
                            Object o2 = this.o2.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number){
                                return multiply((Number)o1, (Number)o2);
                            }else{
                                getReport().reportError("Cannot multiply " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }
                    };
                    bo.o1 = ast;
                    bo.s = iterator.next();
                    bo.sRep = "*";
                    bo.o2 = parseLevel2(iterator);
                    ast = bo;

                }else if(peek.sym == LexSymbol.DIV){
                    BinaryOperator bo = new BinaryOperator() {
                        @Override
                        public Object evaluate() {
                            Object o1 = this.o1.evaluate();
                            Object o2 = this.o2.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number){
                                return divide((Number)o1, (Number)o2);
                            }else{
                                getReport().reportError("Cannot divide " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }
                    };
                    bo.o1 = ast;
                    bo.s = iterator.next();
                    bo.sRep = "/";
                    bo.o2 = parseLevel2(iterator);
                    ast = bo;

                }else if(peek.sym == LexSymbol.MOD){
                    BinaryOperator bo = new BinaryOperator() {
                        @Override
                        public Object evaluate() {
                            Object o1 = this.o1.evaluate();
                            Object o2 = this.o2.evaluate();
                            if(o1 instanceof Number && o2 instanceof Number){
                                return mod((Number)o1, (Number)o2);
                            }else{
                                getReport().reportError("Cannot mod " + o1.getClass().getSimpleName() + " and " + o1.getClass().getSimpleName(), this.toSymbols());
                                return null;
                            }
                        }
                    };
                    bo.o1 = ast;
                    bo.s = iterator.next();
                    bo.sRep = "%";
                    bo.o2 = parseLevel2(iterator);
                    ast = bo;

                }else{
                    return ast;
                }
            }
        }

        public AST parseLevel2(PeekEverywhereIterator<LexSymbol> iterator){

            LexSymbol peekAhead = iterator.peek_ahead();

            if(peekAhead.sym == LexSymbol.PLUS){
                UnaryOperator uo = new UnaryOperator() {
                    @Override
                    public Object evaluate() {
                        Object eval = o1.evaluate();
                        if(eval instanceof Number){
                            return eval;
                        }else{
                            getReport().reportError("Cannot preform + operation on non number types", this.toSymbols());
                            return null;
                        }
                    }
                };
                uo.s = iterator.next();
                uo.sRep = "+";
                uo.o1 = parseLevel2(iterator);
                return uo;

            }else if(peekAhead.sym == LexSymbol.MINUS){
                UnaryOperator uo = new UnaryOperator() {
                    @Override
                    public Object evaluate() {
                        Object eval = o1.evaluate();
                        if(eval instanceof Number){
                            return invert((Number) eval);
                        }else{
                            getReport().reportError("Cannot preform - operation on non number types" , this.toSymbols());
                            return null;
                        }
                    }
                };
                uo.s = iterator.next();
                uo.sRep = "-";
                uo.o1 = parseLevel2(iterator);
                return uo;

            }else if(peekAhead.sym == LexSymbol.NOT){
                UnaryOperator uo = new UnaryOperator() {
                    @Override
                    public Object evaluate() {
                        Object eval = o1.evaluate();
                        if(eval instanceof Boolean){
                            return !((Boolean)eval);
                        }else{
                            getReport().reportError("Cannot preform ! operation on non boolean types", this.toSymbols());
                            return null;
                        }
                    }
                };
                uo.s = iterator.next();
                uo.sRep = "!";
                uo.o1 = parseLevel2(iterator);
                return uo;

            }else if(peekAhead.sym == LexSymbol.COMP){
                UnaryOperator uo = new UnaryOperator() {
                    @Override
                    public Object evaluate() {
                        Object eval = o1.evaluate();
                        if(eval instanceof Long || eval instanceof Integer || eval instanceof Short || eval instanceof Byte){
                            return bitwiseInvert((Number) eval);
                        }else{
                            getReport().reportError("Cannot preform ~ operation on non long, int, short, byte types", this.toSymbols());
                            return null;
                        }
                    }
                };
                uo.s = iterator.next();
                uo.sRep = "~";
                uo.o1 = parseLevel2(iterator);
                return uo;

            }else if(peekAhead.sym == LexSymbol.LPAREN &&
                    iterator.peek_ahead(1).sym == LexSymbol.IDENTIFIER &&
                    iterator.peek_ahead(2).sym == LexSymbol.RPAREN){
                TypeCast tc = new TypeCast();
                tc.b1 = iterator.next();
                tc.type = iterator.next();
                tc.b2 = iterator.next();
                tc.o1 = parseLevel2(iterator);
                return tc;

            }

            return parseLevel1(iterator);
        }

        public AST parseLevel1(PeekEverywhereIterator<LexSymbol> iterator){
            LexSymbol sym = iterator.next();
            AST ast;

            if(sym.sym == LexSymbol.IDENTIFIER){
                if(iterator.peek_ahead().sym == LexSymbol.LPAREN){ //functions
                    FunctionCall func = new FunctionCall();
                    func.funcIdent = sym;
                    func.b1 = iterator.next();
                    if(iterator.peek_ahead().sym != LexSymbol.RPAREN){
                        func.arguments = (CommaSeparatedList) parseLevel16(iterator);
                    }
                    if(iterator.peek_ahead().sym != LexSymbol.RPAREN){
                        getReport().reportError("Un terminated brackets / unexpected token", iterator.next());
                    }else{
                        func.b2 = iterator.next();
                    }
                    ast = func;
                }else{ //variables
                    ast = new Variable(sym);
                }
            }else if(sym.sym == LexSymbol.LPAREN){
                Parenthesis p = new Parenthesis();
                p.b1 = sym;
                p.inside = parseLevel15(iterator);
                if(iterator.next().sym == LexSymbol.RPAREN){
                    p.b2 = iterator.peek_behind();
                    ast = p;
                }else{
                    getReport().reportError("Brackets not terminated / unexpected symbol", iterator.peek_behind());
                    ast = null;
                }

            }else if(sym.sym == LexSymbol.LBRACE){
                ArrayDeclaration p = new ArrayDeclaration();
                p.b1 = sym;
                p.arguments = (CommaSeparatedList) parseLevel16(iterator);
                if(iterator.next().sym == LexSymbol.RBRACE){
                    p.b2 = iterator.peek_behind();
                    ast = p;
                }else{
                    getReport().reportError("Braces not terminated / unexpected symbol", iterator.peek_behind());
                    ast = null;
                }
            }else{ //constants
                switch(sym.sym){
                    case LexSymbol.BOOLEAN_LITERAL:
                    case LexSymbol.STRING_LITERAL:
                    case LexSymbol.INTEGER_LITERAL:
                    case LexSymbol.FLOATING_POINT_LITERAL:
                    case LexSymbol.CHARACTER_LITERAL:
                        ast = new Constant(sym);
                        break;
                    default:
                        getReport().reportError(sym);
                        ast = null;
                }
            }

            sym = iterator.peek_ahead();
            if(sym.sym == LexSymbol.LBRACK){
                ArrayAccess p = new ArrayAccess();
                p.array = ast;
                p.b1 = iterator.next();
                p.argument = parseLevel15(iterator);
                if(iterator.next().sym == LexSymbol.RBRACK){
                    p.b2 = iterator.peek_behind();
                    ast = p;
                }else{
                    getReport().reportError("Brackets not terminated / unexpected symbol", iterator.peek_behind());
                    ast = null;
                }
            }else if(sym.sym == LexSymbol.COLON && iterator.peek_ahead(1).sym == LexSymbol.COLON){
                return ast;
            }

            return ast;
        }



    }

    private static final NumberComparator NUMBER_COMPARATOR = new NumberComparator();

    private static class NumberComparator implements Comparator<Object> {
        @SuppressWarnings("unchecked")
        @Override
        public int compare(Object number1, Object number2) {
            if (number2.getClass().equals(number1.getClass())) {
                // both numbers are instances of the same type!
                if (number1 instanceof Comparable) {
                    // and they implement the Comparable interface
                    return ((Comparable<Object>) number1).compareTo(number2);
                }
            }
            // for all different Number types, let's check there double values
            if (number1 instanceof Double || number1 instanceof Float || number1 instanceof BigDecimal || number2 instanceof Double || number2 instanceof Float || number2 instanceof BigDecimal) {
                return Double.compare(((Number) number1).doubleValue(), ((Number) number2).doubleValue());
            } else {
                return Long.compare(((Number) number1).longValue(), ((Number) number2).longValue());
            }
        }
    }

    public static Number add(Number a, Number b) {
        if (a instanceof Double || b instanceof Double) {
            return a.doubleValue() + b.doubleValue();
        } else if (a instanceof Float || b instanceof Float) {
            return a.floatValue() + b.floatValue();
        } else if (a instanceof Long || b instanceof Long) {
            return a.longValue() + b.longValue();
        } else if (a instanceof Integer || b instanceof Integer) {
            return a.intValue() + b.intValue();
        } else if (a instanceof Short || b instanceof Short) {
            return a.shortValue() + b.shortValue();
        } else if (a instanceof Byte || b instanceof Byte) {
            return a.byteValue() + b.byteValue();
        }
        return null;
    }

    public static Number subtract(Number a, Number b) {
        if (a instanceof Double || b instanceof Double) {
            return a.doubleValue() - b.doubleValue();
        } else if (a instanceof Float || b instanceof Float) {
            return a.floatValue() - b.floatValue();
        } else if (a instanceof Long || b instanceof Long) {
            return a.longValue() - b.longValue();
        } else if (a instanceof Integer || b instanceof Integer) {
            return a.intValue() - b.intValue();
        } else if (a instanceof Short || b instanceof Short) {
            return a.shortValue() - b.shortValue();
        } else if (a instanceof Byte || b instanceof Byte) {
            return a.byteValue() - b.byteValue();
        }
        return null;
    }

    public static Number multiply(Number a, Number b) {
        if (a instanceof Double || b instanceof Double) {
            return a.doubleValue() * b.doubleValue();
        } else if (a instanceof Float || b instanceof Float) {
            return a.floatValue() * b.floatValue();
        } else if (a instanceof Long || b instanceof Long) {
            return a.longValue() * b.longValue();
        } else if (a instanceof Integer || b instanceof Integer) {
            return a.intValue() * b.intValue();
        } else if (a instanceof Short || b instanceof Short) {
            return a.shortValue() * b.shortValue();
        } else if (a instanceof Byte || b instanceof Byte) {
            return a.byteValue() * b.byteValue();
        }
        return null;
    }

    public static Number divide(Number a, Number b) {
        if (a instanceof Double || b instanceof Double) {
            return a.doubleValue() / b.doubleValue();
        } else if (a instanceof Float || b instanceof Float) {
            return a.floatValue() / b.floatValue();
        } else if (a instanceof Long || b instanceof Long) {
            return a.longValue() / b.longValue();
        } else if (a instanceof Integer || b instanceof Integer) {
            return a.intValue() / b.intValue();
        } else if (a instanceof Short || b instanceof Short) {
            return a.shortValue() / b.shortValue();
        } else if (a instanceof Byte || b instanceof Byte) {
            return a.byteValue() / b.byteValue();
        }
        return null;
    }

    public static Number mod(Number a, Number b) {
        if (a instanceof Double || b instanceof Double) {
            return a.doubleValue() % b.doubleValue();
        } else if (a instanceof Float || b instanceof Float) {
            return a.floatValue() % b.floatValue();
        } else if (a instanceof Long || b instanceof Long) {
            return a.longValue() % b.longValue();
        } else if (a instanceof Integer || b instanceof Integer) {
            return a.intValue() % b.intValue();
        } else if (a instanceof Short || b instanceof Short) {
            return a.shortValue() % b.shortValue();
        } else if (a instanceof Byte || b instanceof Byte) {
            return a.byteValue() % b.byteValue();
        }
        return null;
    }

    public static Number shiftLeft(Number a, Number b) {
        if (a instanceof Long || b instanceof Long) {
            return a.longValue() << b.longValue();
        } else if (a instanceof Integer || b instanceof Integer) {
            return a.intValue() << b.intValue();
        } else if (a instanceof Short || b instanceof Short) {
            return a.shortValue() << b.shortValue();
        } else if (a instanceof Byte || b instanceof Byte) {
            return a.byteValue() << b.byteValue();
        }
        return null;
    }

    public static Number shiftRight(Number a, Number b) {
        if (a instanceof Long || b instanceof Long) {
            return a.longValue() >> b.longValue();
        } else if (a instanceof Integer || b instanceof Integer) {
            return a.intValue() >> b.intValue();
        } else if (a instanceof Short || b instanceof Short) {
            return a.shortValue() >> b.shortValue();
        } else if (a instanceof Byte || b instanceof Byte) {
            return a.byteValue() >> b.byteValue();
        }
        return null;
    }

    public static Number uShiftRight(Number a, Number b) {
        if (a instanceof Long || b instanceof Long) {
            return a.longValue() >>> b.longValue();
        } else if (a instanceof Integer || b instanceof Integer) {
            return a.intValue() >>> b.intValue();
        } else if (a instanceof Short || b instanceof Short) {
            return a.shortValue() >>> b.shortValue();
        } else if (a instanceof Byte || b instanceof Byte) {
            return a.byteValue() >>> b.byteValue();
        }
        return null;
    }

    public static Number bitwiseAnd(Number a, Number b) {
        if (a instanceof Long || b instanceof Long) {
            return a.longValue() & b.longValue();
        } else if (a instanceof Integer || b instanceof Integer) {
            return a.intValue() & b.intValue();
        } else if (a instanceof Short || b instanceof Short) {
            return a.shortValue() & b.shortValue();
        } else if (a instanceof Byte || b instanceof Byte) {
            return a.byteValue() & b.byteValue();
        }
        return null;
    }

    public static Number bitwiseOr(Number a, Number b) {
        if (a instanceof Long || b instanceof Long) {
            return a.longValue() | b.longValue();
        } else if (a instanceof Integer || b instanceof Integer) {
            return a.intValue() | b.intValue();
        } else if (a instanceof Short || b instanceof Short) {
            return a.shortValue() | b.shortValue();
        } else if (a instanceof Byte || b instanceof Byte) {
            return a.byteValue() | b.byteValue();
        }
        return null;
    }

    public static Number bitwiseXor(Number a, Number b) {
        if (a instanceof Long || b instanceof Long) {
            return a.longValue() ^ b.longValue();
        } else if (a instanceof Integer || b instanceof Integer) {
            return a.intValue() ^ b.intValue();
        } else if (a instanceof Short || b instanceof Short) {
            return a.shortValue() ^ b.shortValue();
        } else if (a instanceof Byte || b instanceof Byte) {
            return a.byteValue() ^ b.byteValue();
        }
        return null;
    }


    public static Number bitwiseInvert(Number a) {
        if (a instanceof Long) {
            return ~a.longValue();
        } else if (a instanceof Integer) {
            return ~a.intValue();
        } else if (a instanceof Short) {
            return ~a.shortValue();
        } else if (a instanceof Byte) {
            return ~a.byteValue();
        }
        return null;
    }

    public static Number invert(Number a) {
        if (a instanceof Double) {
            return -a.doubleValue();
        } else if (a instanceof Float) {
            return -a.floatValue();
        } else if (a instanceof Long) {
            return -a.longValue();
        } else if (a instanceof Integer) {
            return -a.intValue();
        } else if (a instanceof Short) {
            return -a.shortValue();
        } else if (a instanceof Byte) {
            return -a.byteValue();
        }
        return null;
    }

}
