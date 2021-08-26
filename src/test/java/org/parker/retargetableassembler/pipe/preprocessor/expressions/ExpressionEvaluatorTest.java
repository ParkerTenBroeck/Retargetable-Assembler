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
import java.util.Map;

public class ExpressionEvaluatorTest {

    @Test
    public void Test(){

        TestContext tc = new TestContext();

        for(Map.Entry<String, Object> entry:Expressions.resultExpressionMap.entrySet()){

            System.out.println(entry.getKey());
            if(entry.getKey().startsWith("{sin(toRadians(toDegrees(PI/2))), !!false ? 12 : 10, 4}")){
                System.out.println(entry.getKey());
            }
            AssemblerScanner as = new AssemblerScanner(new StringReader(entry.getKey()), new File(""), false);

            PeekEverywhereIterator<LexSymbol> iterator = new PeekEverywhereIteratorWrapper<>(as);

            CompiledExpression e = ExpressionEvaluator.evaluateExpression(iterator, new PreProcessorReportWrapper(new LoggerReport()));

            e.setContext(tc);
            Object got = e.evaluateExpression();
            Assert.assertEquals(entry.getValue(), got);
            System.out.println(entry.getValue() + " ## " + got);
            System.out.println(entry.getKey() + " ## " + e.toString());
            System.out.println(e.toSymbols());
        }

    }

    private static class TestContext extends Context{

        public static class TestDummyObject{
            public final static int someMember = 22;
        }

        @Override
        public Object getVariable(String value) {
            if(value.equals("pi") || value.equals("PI")){
                return Math.PI;
            }else if(value.equals("TestDummyObject.someMember")){
                return TestDummyObject.someMember;
            }else{
                return null;
            }
        }

        @Override
        public boolean hasVariable(String value) {
            return value.equals("pi") || value.equals("PI") || value.equals("TestDummyObject.someMember");
        }

        @Override
        public Object evaluateFunction(String toString, int num, Object[] evaluate) {
            if(num == 1){
                switch (toString){
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
        public Object evaluateTypeCast(String toString, Object evaluate) {
            if(toString.equals("Double")){
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