package org.parker.retargetableassembler.pipe.preprocessor.expressions;

import org.junit.Assert;
import org.junit.Test;
import org.parker.retargetableassembler.pipe.LoggerReport;
import org.parker.retargetableassembler.pipe.preprocessor.PreProcessorReportWrapper;
import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.AssemblerScanner;
import org.parker.retargetableassembler.pipe.preprocessor.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.pipe.util.iterators.PeekEverywhereIterator;
import org.parker.retargetableassembler.pipe.util.iterators.PeekEverywhereIteratorWrapper;

import java.io.File;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExpressionEvaluatorTest {

    @Test
    public void Test(){

        for(Map.Entry<String, Object> entry:Expressions.resultExpressionMap.entrySet()){

            System.out.println(entry.getKey());

            AssemblerScanner as = new AssemblerScanner(new StringReader(entry.getKey()), new File(""), false);

            PeekEverywhereIterator<LexSymbol> iterator = new PeekEverywhereIteratorWrapper<>(as);

            ExpressionEvaluator.$CompiledExpression e = ExpressionEvaluator.evaluateExpression(iterator, new PreProcessorReportWrapper(new LoggerReport()));

            TestContext tc = new TestContext();
            e.setContext(tc);
            Object got = e.evaluateExpression();
            System.out.println(entry.getValue() + " ## " + got);
            System.out.println(entry.getKey() + " ## " + e.toString());

            StringBuilder buffer = new StringBuilder(50);
            print(e.getAsTree(), buffer, "", "");
            System.out.println(buffer);


            Assert.assertEquals(entry.getValue(), got);
        }
    }

    private void print(ExpressionEvaluator.$CompiledExpression.Node<LexSymbol> tree, StringBuilder buffer, String prefix, String childrenPrefix) {

        buffer.append(prefix);
        if(tree.isLeaf()){
            buffer.append(LexSymbol.terminalNames[tree.getValue().sym]);
            if(tree.getValue().value != null){
                buffer.append(" '" + tree.getValue().value + "'");
            }
            buffer.append('\n');
            return;
        }else{
            buffer.append(tree.getType().name());
            buffer.append('\n');
        }

        for (Iterator<ExpressionEvaluator.$CompiledExpression.Node<LexSymbol>> it = tree.getChildren().iterator(); it.hasNext();) {
            ExpressionEvaluator.$CompiledExpression.Node<LexSymbol> next = it.next();
            if (it.hasNext()) {
                print(next, buffer, childrenPrefix + "|-- ", childrenPrefix + "|   ");
            } else {
                print(next, buffer, childrenPrefix + "\\-- ", childrenPrefix + "    ");
            }
        }
    }

    private static class TestContext implements Context{

        private HashMap<String, Object> variables = new HashMap<>();

        public static class TestDummyObject{
            public final static int someMember = 22;
        }

        public TestContext(){
            variables.put("pi", Math.PI);
            variables.put("PI", Math.PI);
            variables.put("TestDummyObject.someMember", TestDummyObject.someMember);
        }

        @Override
        public void setVariable(String id, Object value) {
            variables.put(id, value);
        }

        @Override
        public Object getVariable(String value) {
            return variables.get(value);
        }

        @Override
        public boolean hasVariable(String value) {
            return variables.containsKey(value);
        }

        @Override
        public Object evaluateFunction(LexSymbol toString, int num, Object[] evaluate) {
            if(num == 1){
                switch (toString.value.toString()){
                    case "sin":
                        return Math.sin(((Number) evaluate[0]).doubleValue());
                    case "cos":
                        return Math.cos(((Number) evaluate[0]).doubleValue());
                    case "tan":
                        return Math.tan(((Number) evaluate[0]).doubleValue());
                    case "sqrt":
                        return Math.sqrt(((Number) evaluate[0]).doubleValue());
                    case "toRadians":
                        return Math.toRadians(((Number) evaluate[0]).doubleValue());
                    case "toDegrees":
                        return Math.toDegrees(((Number) evaluate[0]).doubleValue());
                    default:
                        return  null;
                }
            }else if(num == 2){
                if(toString.equals("pow")){
                    return Math.pow(((Number) evaluate[0]).doubleValue(), ((Number) evaluate[1]).doubleValue());
                }
            }
            return null;
        }

        @Override
        public boolean hasFunction(String toString, int num) {
            System.out.println("func: " + toString + " " + num);
            if(num == 1){
                switch (toString){
                    case "sin":
                    case "cos":
                    case "tan":
                    case "sqrt":
                    case "toRadians":
                    case "toDegrees":
                        return true;
                    default:
                        return false;
                }
            }else if(num == 2){
                return toString.equals("pow");
            }
            return false;
        }

        @Override
        public Object evaluateTypeCast(LexSymbol toString, Object evaluate) {
            if(toString.value.equals("Double")){
                return ((Number)evaluate).doubleValue();
            }
            return null;
        }

        @Override
        public boolean hasTypeCast(String toString) {

            return toString.equals("Double");
        }
    }

}