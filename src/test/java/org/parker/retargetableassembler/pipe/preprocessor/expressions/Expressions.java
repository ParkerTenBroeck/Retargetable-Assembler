package org.parker.retargetableassembler.pipe.preprocessor.expressions;

import org.parker.retargetableassembler.base.preprocessor.expressions.TestExpressionScope;

import java.util.HashMap;

public class Expressions {

    public static final HashMap<String, Object> resultExpressionMap = new HashMap<>();

    static{
        resultExpressionMap.put("2-1", 0B1);

        resultExpressionMap.put("(Double)1", 1.0);

        resultExpressionMap.put("true || false && false?\"This \" + \"is \" + \"true\":\"This \" + \"is \" + \"false\"", "This is true");
        resultExpressionMap.put("false?1:2", false?1:2);

        resultExpressionMap.put("+5", 5);
        resultExpressionMap.put("-5", -5);
        resultExpressionMap.put("+2.5", 2.5);
        resultExpressionMap.put("-2.5", -2.5);
        resultExpressionMap.put("+0xF", 15);
        resultExpressionMap.put("-0xF", -15);
        resultExpressionMap.put("!true", false);
        resultExpressionMap.put("!false", true);
        resultExpressionMap.put("!!false", false);
        resultExpressionMap.put("~0b0", -1);

        resultExpressionMap.put("2+-3", -1);
        resultExpressionMap.put("2+(-3)", -1);

        resultExpressionMap.put("(5+4+(4*(5/2.5)))", 17.0);
        resultExpressionMap.put("12%5", 2);

        resultExpressionMap.put("8 << 1", 16);
        resultExpressionMap.put("8 >> 1", 4);
        resultExpressionMap.put("16 >>> 4", 16 >>> 4);
        resultExpressionMap.put("-16 >>> 4", -16 >>> 4);

        resultExpressionMap.put("8 > 2", true);
        resultExpressionMap.put("2 > 8", false);

        resultExpressionMap.put("8 < 2", false);
        resultExpressionMap.put("2 < 8", true);

        resultExpressionMap.put("sin(toRadians(90))", 1.0);
        resultExpressionMap.put("sin(toRadians(90))", 1.0);

        resultExpressionMap.put("sin(toRadians(90))", 1.0);
        resultExpressionMap.put("sin(toRadians(90))", 1.0);

        resultExpressionMap.put("8 == 8", true);
        resultExpressionMap.put("2 == 8", false);

        resultExpressionMap.put("8 != 8", false);
        resultExpressionMap.put("2 != 8", true);

        resultExpressionMap.put("sin(PI/6)", Math.sin(Math.PI/6.0));
        resultExpressionMap.put("cos(PI*2/6)", Math.cos(Math.PI*2.0/6.0));
        resultExpressionMap.put("tan(PI/4)", Math.tan(Math.PI/4.0));

        resultExpressionMap.put("pow(2,5)", 32.0);
        resultExpressionMap.put("sqrt(25)", 5.0);
        resultExpressionMap.put("sin(toRadians(90))", 1.0);
        resultExpressionMap.put("sin(toRadians(toDegrees(PI/2)))", 1.0);


        resultExpressionMap.put("{sin(toRadians(toDegrees(PI/2))), !!false ? 12 : 10, 4}[2]", 4);
        resultExpressionMap.put("{sin(toRadians(toDegrees(PI/2))), !!false ? 12 : 10, 4}[1]", !!false ? 12 : 10);
        resultExpressionMap.put("{sin(toRadians(toDegrees(PI/2))), !!false ? 12 : 10, 4}[0]", Math.sin(Math.toRadians(Math.toDegrees(Math.PI/2))));


        resultExpressionMap.put("TestDummyObject.someMember", TestExpressionScope.TestDummyObject.someMember);

        resultExpressionMap.put("a = 4", 4);
        resultExpressionMap.put("a = 2, a", 2);
        resultExpressionMap.put("a = b = c = 2, a", 2);
        resultExpressionMap.put("a = b = c = 2, b", 2);
        resultExpressionMap.put("a = b = c = 2, c", 2);

        resultExpressionMap.put("a = b = c = 2, c", 2);
        resultExpressionMap.put("a = 2, b = 15, a + b", 17);
        resultExpressionMap.put("a = c = 2, b = 15, (a + b) * c", 34);
    }
}
