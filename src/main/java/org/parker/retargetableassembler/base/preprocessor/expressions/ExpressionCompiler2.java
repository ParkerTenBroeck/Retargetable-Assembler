/*
 *    Copyright 2021 ParkerTenBroeck
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.parker.retargetableassembler.base.preprocessor.expressions;

import org.parker.retargetableassembler.base.preprocessor.expressions.scope.ExpressionCompilerScope;
import org.parker.retargetableassembler.base.preprocessor.util.Line;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionCompiler2 {

    private static final Logger LOGGER = Logger.getLogger(ExpressionCompiler2.class.getName());

    private static final Pattern namePattern = Pattern.compile("\\s*([a-zA-Z_$][a-zA-Z_$0-9]*)\\s*");
    private static final Pattern castPattern = Pattern.compile("\\s*([(][\\s]*([a-zA-Z_$][a-zA-Z_$0-9]*)[\\s]*[)])\\s*[a-zA-Z_$0-9]+");
    private static final HashMap<String, Class> castMap = new HashMap<>();
    private static final HashMap<String, Class> primitiveCastMap = new HashMap<>();
    private ExpressionCompilerScope expressionCompilerScope;

    static{
        primitiveCastMap.put("Double", Double.class);
        primitiveCastMap.put("double", Double.class);
        primitiveCastMap.put("Float", Float.class);
        primitiveCastMap.put("float", Float.class);

        primitiveCastMap.put("Long", Long.class);
        primitiveCastMap.put("long", Long.class);
        primitiveCastMap.put("Integer", Integer.class);
        primitiveCastMap.put("int", Integer.class);
        primitiveCastMap.put("Short", Short.class);
        primitiveCastMap.put("short", Short.class);
        primitiveCastMap.put("Byte", Byte.class);
        primitiveCastMap.put("byte", Byte.class);

        primitiveCastMap.put("Character", Character.class);
        primitiveCastMap.put("char", Character.class);
    }

    public CompiledExpression compileExpression(String str, Line line, int offset) {
        PrivateExpressionCompiler ec = new PrivateExpressionCompiler(str, line, offset);
        ec.expressionCompilerScope = this.expressionCompilerScope;
        CompiledExpression e = ec.compileExpression();
        ec.expressionCompilerScope = null;
        return e;
    }

    public final synchronized CompiledExpression[] compileExpressionsAsArray(String str, Line line, int offset){
        PrivateExpressionCompiler ec = new PrivateExpressionCompiler(str, line, offset);
        ec.expressionCompilerScope = this.expressionCompilerScope;
        CompiledExpression[] e = ec.compileTopLevelExpressions();
        ec.expressionCompilerScope = null;
        return e;
    }

    public void setExpressionCompilerScope(ExpressionCompilerScope ecs){
        this.expressionCompilerScope = ecs;
    }

    private interface Expression extends Serializable{
        Object evaluate();
        //List<Variable> getUsedVariables();
    }

    /**
     *  this is really only for something thats for sure
     */
    private static class PrivateExpressionCompiler implements Serializable{

        class CompilationError extends org.parker.retargetableassembler.exception.preprocessor.expression.ExpressionError {

            public CompilationError(String message, final int s, final int e){
                super(message, expressionLine, s, e);
            }

            public CompilationError(String message, final int s){
                super(message, expressionLine, s);
            }
        }


        private class ExpressionError extends org.parker.retargetableassembler.exception.preprocessor.expression.ExpressionError {

            public ExpressionError(String message, final int s, final int e){
                super(message,expressionLine, s + expressionLineIndexOffset, e + expressionLineIndexOffset);
            }

            public ExpressionError(String message, final int s, final int e, final Exception ex){
                super(message,expressionLine, s + expressionLineIndexOffset, e + expressionLineIndexOffset, ex);
            }
        }

        public PrivateExpressionCompiler(String str, Line line, int offset){
            this.str = str;
            this.expressionLine = line;
            this.expressionLineIndexOffset = offset;
        }

        private transient ExpressionCompilerScope expressionCompilerScope;
        private CompiledExpression compiledExpression;
        private final Line expressionLine;
        private final int expressionLineIndexOffset;
        private final String str;
        private transient int pos = -1, ch;
        private transient boolean isInPreProcess = false;

        private void nextChar() {
            ch = (++pos < str.length()) ? str.charAt(pos) : -1;
        }

        private boolean eat(int charToEat) {
            while (ch == ' ') nextChar();
            if (ch == charToEat) {
                nextChar();
                return true;
            }
            return false;
        }

        private boolean eat(String stringToEat) {
            removeWhiteSpace();
            if (str.startsWith(stringToEat, pos)) {
                for (int i = 0; i < stringToEat.length(); i++) {
                    nextChar();
                }
                return true;
            }
            return false;
        }

        private int removeWhiteSpace(){
            while(ch == ' ')nextChar();
            return pos;
        }

        private boolean next(String next) {
            removeWhiteSpace();
            return str.startsWith(next, pos);
        }

        public CompiledExpression compileExpression() {
            if(str.trim().isEmpty())return null;

            pos = -1;
            ch = 0;
            nextChar();
            try {
                Expression e = parseLevel15();
                compiledExpression = new CompiledExpression(expressionLine,  expressionLineIndexOffset, pos + expressionLineIndexOffset) {
                    @Override
                    public Object evaluate() {
                        return e.evaluate();
                    }
                    @Override
                    public String toString() {
                        return e.toString();
                    }
                };
            } catch (Exception e) {
                throw new RuntimeException("Error At index: " + pos + ": " + e.getMessage(), e);
            }

            if (pos < str.length()) throw new CompilationError("Unexpected: " + (char) ch, pos + expressionLineIndexOffset);
            return compiledExpression;
        }

        public CompiledExpression[] compileTopLevelExpressions() {
            if(str.trim().isEmpty())return new CompiledExpression[0];

            pos = -1;
            ch = 0;
            nextChar();
            CompiledExpression[] x;
            x = parseTopLevel15();

            if (pos < str.length()) throw new CompilationError("Unexpected: " + (char) ch, pos + expressionLineIndexOffset);
            return x;
        }

        private synchronized CompiledExpression[] parseTopLevel15() {

            final List<CompiledExpression> list = new ArrayList<>();
            PrivateExpressionCompiler pec;

            final PrivateExpressionCompiler pecf1 = new PrivateExpressionCompiler(this.str, this.expressionLine, this.expressionLineIndexOffset);
            pec = pecf1;
            pecf1.pos = this.pos;
            pecf1.ch = this.ch;
            pecf1.expressionCompilerScope = this.expressionCompilerScope;

            int start = pecf1.removeWhiteSpace();
            Expression xEM = pecf1.parseLevel14();
            int end = pos;

            CompiledExpression why = new CompiledExpression(expressionLine, start + expressionLineIndexOffset, end + expressionLineIndexOffset) {
                {
                    pecf1.compiledExpression = this;
                }

                @Override
                public Object evaluate() {
                    return xEM.evaluate();
                }

                @Override
                public String toString() {
                    return xEM.toString();
                }
            };
            list.add(why);


            for (; ; ) {
                this.pos = pec.pos;
                this.ch = pec.ch;
                //start = removeWhiteSpace();
                if (eat(",")) {
                    final PrivateExpressionCompiler pecf = new PrivateExpressionCompiler(this.str, this.expressionLine, this.expressionLineIndexOffset);
                    pecf.expressionCompilerScope = this.expressionCompilerScope;
                    pec = pecf;
                    pec.pos = this.pos;
                    pec.ch = this.ch;

                    Expression xEMT = pec.parseLevel14();
                    end = pos;
                    why = new CompiledExpression(expressionLine, start + expressionLineIndexOffset + 1, end + expressionLineIndexOffset) {
                        {
                            pecf.compiledExpression = this;
                        }
                        @Override
                        public Object evaluate() {
                            return xEMT.evaluate();
                        }

                        @Override
                        public String toString() {
                            return xEMT.toString();
                        }
                    };
                    list.add(why);
                } else {
                    return list.toArray(new CompiledExpression[0]);
                }
                start = end;
            }
        }

        private synchronized Expression parseLevel15() {
           return parseLevel15(false);
        }


        /**
         *
         * @param explicitList if this is false no array will be wrapped around the object if the array size would have been
         *                     1. if false an array will always be wrapped around the generated objects no matter the size
         * @return
         */
        private synchronized Expression parseLevel15(boolean explicitList) {
            Expression xEM = parseLevel14();

            final List<Expression> list = new ArrayList<>();
            list.add(xEM);
            for (; ; ) {
                if (eat(",")) {
                    if (list.size()  == 0) {
                        list.add(xEM);
                    }
                    list.add(parseLevel14());

                } else {
                    if (list.size() == 1 && !explicitList) {
                        return xEM;
                    } else {
                        return new Expression() {
                            @Override
                            public Object evaluate() {
                                Object[] x = new Object[list.size()];
                                for (int i = 0; i < list.size(); i++) {
                                    x[i] = list.get(i).evaluate();
                                }
                                return x;
                            }

                            @Override
                            public String toString() {
                                List<String> a = new ArrayList<>();
                                for(Expression e: list){
                                    a.add(e.toString());
                                }
                                String temp = "";

                                for(int i = 0; i < a.size(); i ++){
                                    temp += a.get(i);
                                    if(i < a.size() - 1){
                                        temp += ", ";
                                    }
                                }

                                return temp;
                            }
                        };
                    }
                }
            }
        }

        private Expression parseLevel14() {
            return parseLevel13();
        }

        private Expression parseLevel13() {
            final int s = removeWhiteSpace();
            Expression xEM = parseLevel12();

            if(eat("?")){
                Expression EM1 = parseLevel15();
                if(eat(":")){
                    Expression EM2 = parseLevel15();
                    final int e = pos;

                    return new Expression() {
                        @Override
                        public Object evaluate() {
                            Object d = xEM.evaluate();
                            Object o1 = EM1.evaluate();
                            Object o2 = EM2.evaluate();
                            if (d instanceof Boolean) {
                                return (Boolean) d ? o1 : o2;
                            } else {
                                throw new ExpressionError("Expected Boolean before ? got: " + d.getClass().getSimpleName(), s, e);
                            }
                        }

                        @Override
                        public String toString() {
                            return xEM.toString() + " ? " + EM1.toString() + " : " + EM2.toString();
                        }
                    };
                }else{
                    throw new IllegalArgumentException("idl");
                }
            }else{
                return xEM;
            }
        }

        private Expression parseLevel12() {
            int si = removeWhiteSpace();
            Expression xEM = parseLevel11();

            for (; ; ) {
                final int s = si;
                if (eat("||")) {
                    si = removeWhiteSpace();
                    Expression xEP = xEM, yEP = parseLevel11();
                    final int e = pos;

                    xEM = new Expression() {
                        @Override
                        public Object evaluate() {
                            Object x = xEP.evaluate();
                            Object y = yEP.evaluate();
                            if (x instanceof Boolean && y instanceof Boolean) {
                                return (Boolean) x || (Boolean) y;
                            } else {
                                throw new ExpressionError("Cannot preform Logical OR between: " + x.getClass().getSimpleName() + " and " + y.getClass().getSimpleName(), s, e);
                            }
                        }

                        @Override
                        public String toString() {
                            return xEP.toString() + " || " + yEP.toString();
                        }
                    };
                } else {
                    return xEM;
                }
            }
        }

        private Expression parseLevel11() {
            int si = removeWhiteSpace();
            Expression xEM = parseLevel10();

            for (;;) {
                final int s = si;
                if (eat("&&")) {
                    si = removeWhiteSpace();
                    Expression xEP = xEM, yEP = parseLevel10();
                    final int e = pos;

                    xEM = new Expression() {
                        @Override
                        public Object evaluate() {
                            Object x = xEP.evaluate();
                            Object y = yEP.evaluate();
                            if (x instanceof Boolean && y instanceof Boolean) {
                                return (Boolean) x && (Boolean) y;
                            } else {
                                throw new ExpressionError("Cannot preform Logical AND between: " + x.getClass().getSimpleName() + " and " + y.getClass().getSimpleName(), s, e);
                            }
                        }

                        @Override
                        public String toString() {
                            return xEP.toString() + " && " + yEP.toString();
                        }
                    };
                } else {
                    return xEM;
                }
            }
        }

        private Expression parseLevel10() {
            int si = removeWhiteSpace();
            Expression xEM = parseLevel9();

            for (; ; ) {
                final int s = si;
                if (!next("||") && eat("|")) {
                    si = removeWhiteSpace();
                    Expression xEP = xEM, yEP = parseLevel9();
                    final int e = pos;

                    xEM = new Expression() {
                        @Override
                        public Object evaluate() {
                            Object x = xEP.evaluate();
                            Object y = yEP.evaluate();
                            if (x instanceof Number && y instanceof Number) {
                                return bitwiseOr((Number) x, (Number) y);
                            } else {
                                throw new ExpressionError("Cannot preform bitwise OR between: " + x.getClass().getSimpleName() + " and " + y.getClass().getSimpleName(), s, e);
                            }
                        }

                        @Override
                        public String toString() {
                            return xEP.toString() + " | " + yEP.toString();
                        }
                    };
                } else {
                    return xEM;
                }
            }
        }

        private Expression parseLevel9() {
            int si = removeWhiteSpace();
            Expression xEM = parseLevel8();

            for (; ; ) {
                final int s = si;
                if (eat("^")) {
                    si = removeWhiteSpace();
                    Expression xEP = xEM, yEP = parseLevel8();
                    final int e = pos;

                    xEM = new Expression() {
                        @Override
                        public Object evaluate() {
                            Object x = xEP.evaluate();
                            Object y = yEP.evaluate();
                            if (x instanceof Number && y instanceof Number) {
                                return bitwiseXor((Number) x, (Number) y);
                            } else {
                                throw new ExpressionError("Cannot preform bitwise XOR between: " + x.getClass().getSimpleName() + " and " + y.getClass().getSimpleName(), s, e);
                            }
                        }

                        @Override
                        public String toString() {
                            return xEP.toString() + " ^ " + yEP.toString();
                        }
                    };
                } else {
                    return xEM;
                }
            }
        }

        private Expression parseLevel8() {
            int si = removeWhiteSpace();
            Expression xEM = parseLevel7();

            for (; ; ) {
                final int s = si;
                if (!next("&&") && eat("&")) {
                    si = removeWhiteSpace();
                    Expression xEP = xEM, yEP = parseLevel7();
                    final int e = pos;

                    xEM = new Expression() {
                        @Override
                        public Object evaluate() {
                            Object x = xEP.evaluate();
                            Object y = yEP.evaluate();
                            if (x instanceof Number && y instanceof Number) {
                                return bitwiseAnd((Number) x, (Number) y);
                            } else {
                                throw new ExpressionError("Cannot preform bitwise AND between: " + x.getClass().getSimpleName() + " and " + y.getClass().getSimpleName(), s, e);
                            }
                        }

                        @Override
                        public String toString() {
                            return xEP.toString() + " & " + yEP.toString();
                        }
                    };
                } else {
                    return xEM;
                }
            }
        }

        private Expression parseLevel7() {
            final int s = removeWhiteSpace();
            Expression xEM = parseLevel6();

            if (eat("==")) {
                Expression xEP = xEM, yEP = parseLevel6();
                final int e = pos;
                return new Expression() {
                    @Override
                    public Object evaluate() {
                        Object x = xEP.evaluate();
                        Object y = yEP.evaluate();
                        if (x instanceof Number && y instanceof Number) {
                            return NUMBER_COMPARATOR.compare(x, y) == 0;
                        } else {
                            throw new ExpressionError("Cannot use '==' on: " + x.getClass().getSimpleName() + " and " + y.getClass().getSimpleName(), s, e);
                        }
                    }

                    @Override
                    public String toString() {
                        return xEP.toString() + " == " + yEP.toString();
                    }
                };
            } else if (eat("!=")) {
                Expression xEP = xEM, yEP = parseLevel6();
                final int e = pos;

                return new Expression() {
                    @Override
                    public Object evaluate() {
                        Object x = xEP.evaluate();
                        Object y = yEP.evaluate();
                        if (x instanceof Number && y instanceof Number) {
                            return NUMBER_COMPARATOR.compare(x, y) != 0;
                        } else {
                            throw new ExpressionError("Cannot use '!=' on: " + x.getClass().getSimpleName() + " and " + y.getClass().getSimpleName(), s, e);
                        }
                    }

                    @Override
                    public String toString() {
                        return xEP.toString() + " != " + yEP.toString();
                    }
                };
            } else {
                return xEM;
            }

        }

        private Expression parseLevel6() {
            final int s = removeWhiteSpace();
            Expression xEM = parseLevel5();

            if (eat("<")) {
                Expression xEP = xEM, yEP = parseLevel5();
                final int e = pos;

                return new Expression() {
                    @Override
                    public Object evaluate() {
                        Object x = xEP.evaluate();
                        Object y = yEP.evaluate();
                        if (x instanceof Number && y instanceof Number) {
                            return NUMBER_COMPARATOR.compare(x, y) < 0;
                        } else {
                            throw new ExpressionError("Cannot use '<' on: " + x.getClass().getSimpleName() + " and " + y.getClass().getSimpleName(), s, e);
                        }
                    }

                    @Override
                    public String toString() {
                        return xEP.toString() + " < " + yEP.toString();
                    }
                };
            } else if (eat("<=")) {
                Expression xEP = xEM, yEP = parseLevel5();
                final int e = pos;

                return new Expression() {
                    @Override
                    public Object evaluate() {
                        Object x = xEP.evaluate();
                        Object y = yEP.evaluate();
                        if (x instanceof Number && y instanceof Number) {
                            return NUMBER_COMPARATOR.compare(x, y) <= 0;
                        } else {
                            throw new ExpressionError("Cannot use '<=' on: " + x.getClass().getSimpleName() + " and " + y.getClass().getSimpleName(), s, e);
                        }
                    }

                    @Override
                    public String toString() {
                        return xEP.toString() + " <= " + yEP.toString();
                    }
                };
            } else if (eat(">")) {
                Expression xEP = xEM, yEP = parseLevel5();
                final int e = pos;

                return new Expression() {
                    @Override
                    public Object evaluate() {
                        Object x = xEP.evaluate();
                        Object y = yEP.evaluate();
                        if (x instanceof Number && y instanceof Number) {
                            return NUMBER_COMPARATOR.compare(x, y) > 0;
                        } else {
                            throw new ExpressionError("Cannot use '>' on: " + x.getClass().getSimpleName() + " and " + y.getClass().getSimpleName(), s, e);
                        }
                    }

                    @Override
                    public String toString() {
                        return xEP.toString() + " > " + yEP.toString();
                    }
                };
            } else if (eat(">=")) {
                Expression xEP = xEM, yEP = parseLevel5();
                final int e = pos;

                return new Expression() {
                    @Override
                    public Object evaluate() {
                        Object x = xEP.evaluate();
                        Object y = yEP.evaluate();
                        if (x instanceof Number && y instanceof Number) {
                            return NUMBER_COMPARATOR.compare(x, y) >= 0;
                        } else {
                            throw new ExpressionError("Cannot use '>=' on: " + x.getClass().getSimpleName() + " and " + y.getClass().getSimpleName(), s, e);
                        }
                    }

                    @Override
                    public String toString() {
                        return xEP.toString() + " >= " + yEP.toString();
                    }
                };
            } else {
                return xEM;
            }
        }

        private Expression parseLevel5() {
            int si = removeWhiteSpace();
            Expression xEM = parseLevel4();

            for (; ; ) {
                final int s = si;
                if (eat("<<")) {
                    si = removeWhiteSpace();
                    Expression xEP = xEM, yEP = parseLevel4();
                    final int e = pos;

                    xEM = new Expression() {
                        @Override
                        public Object evaluate() {
                            Object x = xEP.evaluate();
                            Object y = yEP.evaluate();
                            if (x instanceof Number && y instanceof Number) {
                                return shiftLeft((Number) x, (Number) y);
                            } else {
                                throw new ExpressionError("Cannot use '<<' on: " + x.getClass().getSimpleName() + " and " + y.getClass().getSimpleName(), s, e);
                            }
                        }

                        @Override
                        public String toString() {
                            return xEP.toString() + " << " + yEP.toString();
                        }
                    };
                } else if (eat(">>")) {
                    si = removeWhiteSpace();
                    Expression xEP = xEM, yEP = parseLevel4();
                    final int e = pos;

                    xEM = new Expression() {
                        @Override
                        public Object evaluate() {
                            Object x = xEP.evaluate();
                            Object y = yEP.evaluate();
                            if (x instanceof Number && y instanceof Number) {
                                return shiftRight((Number) x, (Number) y);
                            } else {
                                throw new ExpressionError("Cannot use '>>' on: " + x.getClass().getSimpleName() + " and " + y.getClass().getSimpleName(), s, e);
                            }
                        }

                        @Override
                        public String toString() {
                            return xEP.toString() + " >> " + yEP.toString();
                        }
                    };
                } else {
                    return xEM;
                }
            }
        }

        /**
         * Level 4 used for + / - operators
         * (addition / subtraction)
         *
         */
        private Expression parseLevel4() {
            int si = removeWhiteSpace();
            Expression xEM = parseLevel3();
            for (; ; ) {
                final int s = si;
                if (eat("+")) {
                    si = removeWhiteSpace();
                    Expression xEP = xEM, yEP = parseLevel3();
                    final int e = pos;

                    xEM = new Expression() {
                        @Override
                        public Object evaluate() {
                            Object x = xEP.evaluate();
                            Object y = yEP.evaluate();
                            if (x instanceof String || y instanceof String) {
                                return x.toString() + y.toString();
                            } else if (x instanceof Number || y instanceof Number) {
                                return add((Number) x, (Number) y);
                            } else {
                                throw new ExpressionError("Cannot use '+' token between a " + x.getClass().getSimpleName() + " and " + y.getClass().getSimpleName(), s, e);
                            }
                        }

                        @Override
                        public String toString() {
                            return xEP.toString() + " + " + yEP.toString();
                        }
                    };
                } else if (eat("-")) {
                    si = removeWhiteSpace();
                    Expression xEP = xEM, yEP = parseLevel3();
                    final int e = pos;

                    xEM = new Expression() {
                        @Override
                        public Object evaluate() {
                            Object x = xEP.evaluate();
                            Object y = yEP.evaluate();
                            if (x instanceof Number || y instanceof Number) {
                                return subtract((Number) x, (Number) y);
                            } else {
                                throw new ExpressionError("Cannot use '-' token between a " + x.getClass().getSimpleName() + " and " + y.getClass().getSimpleName(), s, e);
                            }
                        }

                        @Override
                        public String toString() {
                            return xEP.toString() + " - " + yEP.toString();
                        }
                    };
                } else {
                    return xEM;
                }
            }
        }

        /**
         * level 3 is responsible for multiplication division and mods
         *
         */
        private Expression parseLevel3() {
            int si = removeWhiteSpace();
            Expression xEM = parseLevel2();
            for (; ; ) {
                final int s = si;
                if (eat("*")) {
                    si = removeWhiteSpace();
                    Expression xEP = xEM, yEP = parseLevel2();
                    final int e = pos;

                    xEM = new Expression() {
                        @Override
                        public Object evaluate() {
                            Object x = xEP.evaluate();
                            Object y = yEP.evaluate();
                            if (x instanceof Number || y instanceof Number) {
                                return multiply((Number) x, (Number) y);
                            } else {
                                throw new ExpressionError("Cannot use '*' token between a " + x.getClass().getSimpleName() + " and " + y.getClass().getSimpleName(), s, e);
                            }
                        }

                        @Override
                        public String toString() {
                            return xEP.toString() + " * " + yEP.toString();
                        }
                    };
                } else if (eat("/")) {
                    si = removeWhiteSpace();
                    Expression xEP = xEM, yEP = parseLevel2();
                    final int e = pos;

                    xEM = new Expression() {
                        @Override
                        public Object evaluate() {
                            Object x = xEP.evaluate();
                            Object y = yEP.evaluate();
                            if (x instanceof Number || y instanceof Number) {
                                return divide((Number) x, (Number) y);
                            } else {
                                throw new ExpressionError("Cannot use '/' token between a " + x.getClass().getSimpleName() + " and " + y.getClass().getSimpleName(), s, e);
                            }
                        }

                        @Override
                        public String toString() {
                            return xEP.toString() + " / " + yEP.toString();
                        }
                    };
                } else if (eat("%")) {
                    si = removeWhiteSpace();
                    Expression xEP = xEM, yEP = parseLevel2();
                    final int e = pos;
                    xEM = new Expression() {
                        @Override
                        public Object evaluate() {
                            Object x = xEP.evaluate();
                            Object y = yEP.evaluate();
                            if (x instanceof Number || y instanceof Number) {
                                return mod((Number) x, (Number) y);
                            } else {
                                throw new ExpressionError("Cannot use '%' token between a " + x.getClass().getSimpleName() + " and " + y.getClass().getSimpleName(), s, e);
                            }
                        }

                        @Override
                        public String toString() {
                            return xEP.toString() + " % " + yEP.toString();
                        }
                    };
                } else {
                    return xEM;
                }
            }
        }

        /**
         * Level 2 used for + / - (right to left)
         *
         */
        private Expression parseLevel2() {
            int si = removeWhiteSpace();
            for (; ; ) {
                final int s = si;
                if (eat("+")) {
                    si = removeWhiteSpace();
                    Expression xEM = parseLevel1();
                    final int e = pos;
                    return new Expression() {
                        @Override
                        public Object evaluate() {
                            Object x = xEM.evaluate();
                            if (x instanceof Number) {
                                return x;
                            } else {
                                throw new ExpressionError("cannot use '+' modifier on: " + x.getClass().getSimpleName(), s, e);
                            }
                        }

                        @Override
                        public String toString() {
                            return " +" + xEM.toString();
                        }
                    };
                } else if (eat("-")) {
                    {
                        si = removeWhiteSpace();
                        final Expression xEM = parseLevel1();
                        final int e = pos;
                        return new Expression() {
                            @Override
                            public Object evaluate() {
                                Object x = xEM.evaluate();
                                if (x instanceof Number) {
                                    return subtract(0, (Number) x);
                                } else {
                                    throw new ExpressionError("Cannot use '-' modifier on: " + x.getClass().getSimpleName(), s, e);
                                }
                            }

                            @Override
                            public String toString() {
                                return " -" + xEM.toString();
                            }
                        };
                    }
                } else if (eat("~")) {
                    si = removeWhiteSpace();
                    final Expression xEM = parseLevel1();
                    final int e = pos;
                    return () -> new Expression() {
                            @Override
                            public Object evaluate() {
                                Object x = xEM.evaluate();
                                if (x instanceof Number) {
                                    return invert((Number) x);
                                } else {
                                    throw new ExpressionError("Cannot use '~' modifier on: " + x.getClass().getSimpleName(), s, e);
                                }
                            }
                            @Override
                            public String toString() {
                                return " ~" + xEM.toString();
                            }
                        };
                } else if (eat("!")) {
                    si = removeWhiteSpace();
                    final Expression xEM = parseLevel1();
                    final int e = pos;
                    return new Expression() {
                        @Override
                        public Object evaluate() {
                            Object x = xEM.evaluate();
                            if (x instanceof Boolean) {
                                return !(Boolean) x;
                            } else {
                                throw new ExpressionError("Cannot use '!' modifier on: " + x.getClass().getSimpleName(), s, e);
                            }
                        }

                        @Override
                        public String toString() {
                            return " !" + xEM.toString();
                        }
                    };
                } else {
                    si = removeWhiteSpace();
                    Matcher m = castPattern.matcher(str.substring(pos));
                    if(m.find()){
                        final String cast = m.group(1);
                        final String cName = m.group(2);
                        eat(cast);

                        final Expression xEM = parseLevel1();
                        final int e = pos;
                        return new Expression() {
                            @Override
                            public Object evaluate() {
                                Object x = xEM.evaluate();
                                if (primitiveCastMap.containsKey(cName)) {
                                        Class c = primitiveCastMap.get(cName);
                                        if (x instanceof Number) {
                                            if (c == Long.class) {
                                                x = new Long(((Number) x).longValue());
                                            } else if (c == Integer.class) {
                                                x = new Integer(((Number) x).intValue());
                                            } else if (c == Short.class) {
                                                x = new Short(((Number) x).shortValue());
                                            } else if (c == Byte.class) {
                                                x = new Byte(((Number) x).byteValue());
                                            } else if (c == Double.class) {
                                                x = new Double(((Number) x).doubleValue());
                                            } else if (c == Float.class) {
                                                x = new Float(((Number) x).floatValue());
                                            } else if (c == Character.class){
                                                if(x instanceof Long || x instanceof Integer || x instanceof Short || x instanceof Byte){
                                                    x = new Character((char)((Number)x).longValue());
                                                }else{
                                                    throw new ClassCastException("Cannot cast: " + x.getClass().getSimpleName() + " cannot be cast to char || Character");
                                                }
                                        } else {
                                            throw new ExpressionError(x.getClass().getSimpleName() + " cannot be cast to a: " + cName, s, e);
                                        }
                                    }
                                } else if (castMap.containsKey(cName)) {
                                    x = castMap.get(cName).cast(x);
                                } else {
                                    throw new ExpressionError("Cannot find class: " + cName, s, e);
                                }
                                return x;
                            }

                            @Override
                            public String toString() {
                                return '(' + cName + ')' + xEM.toString();
                            }
                        };
                    }

                    return parseLevel1();
                }
            }
        }


        /**
         * level 1 is responsible for variable, constant, and function access
         *
         */
        private Expression parseLevel1() {

            final int s = removeWhiteSpace();

            final Expression xEM;
            final int startPos = this.pos;
            first:
            if (eat('(')) { // parentheses
                Expression internal = parseLevel15();
                xEM = new Expression() {
                    @Override
                    public Object evaluate() {
                        return internal.evaluate();
                    }

                    @Override
                    public String toString() {
                        return '(' + internal.toString() + ')';
                    }
                };
                eat(')');
            } else if ((ch >= '0' && ch <= '9')) { // numbers

                while ((ch >= '0' && ch <= '9') || ch == '.' || (ch >= 'a' && ch <= 'z')|| (ch >= 'A' && ch <= 'Z')) nextChar();
                if (str.substring(startPos, this.pos).contains(".")) {
                    final int end = this.pos;
                    final String string = str;
                    final int e = pos;
                    xEM = new Expression() {
                        @Override
                        public Object evaluate() {
                            try {
                                return Double.parseDouble(string.substring(startPos, end));
                            } catch (Exception ex) {
                                throw new ExpressionError("Failed to parse double: " + string.substring(startPos, end), s, e, ex);
                            }
                        }

                        @Override
                        public String toString() {
                            return string.substring(startPos, end);
                        }
                    };
                } else {
                    final int end = this.pos;
                    final String string = str;
                    final int e = pos;
                    xEM = new Expression() {
                        @Override
                        public Object evaluate() {
                            try {
                                return parseNumber(string.substring(startPos, end));
                            } catch (Exception ex) {
                                throw new ExpressionError("Failed to parse int: " + string.substring(startPos, end), s, e, ex);
                            }
                        }

                        @Override
                        public String toString() {
                            return string.substring(startPos, end);
                        }
                    };
                }
            } else if (Character.isAlphabetic(ch) || ch == '_' || ch == '$') { // functions
                while (Character.isAlphabetic(ch) || Character.isDigit(ch) || ch == '_'|| ch == '$') nextChar();
                final String token = str.substring(startPos, this.pos);
                final int e = pos;

                switch (token) {
                    case "true":
                    case "TRUE":
                        xEM = new Expression() {
                            @Override
                            public Object evaluate() {
                                return true;
                            }

                            @Override
                            public String toString() {
                                return "true";
                            }
                        };
                        break first;
                    case "false":
                    case "FALSE":
                        xEM = new Expression() {
                            @Override
                            public Object evaluate() {
                                return false;
                            }

                            @Override
                            public String toString() {
                                return "false";
                            }
                        };
                        break first;
                    case "pi":
                    case "PI":
                        xEM = new Expression() {
                            @Override
                            public Object evaluate() {
                                return Math.PI;
                            }

                            @Override
                            public String toString() {
                                return "pi";
                            }
                        };
                        break first;
                    default:
                }

                if (next("(")) {
                    final Expression args = parseLevel15(true);
                    final int endFunc = pos;

                    xEM = new Expression() {
                        @Override
                        public Object evaluate() {
                            try {
                                if(compiledExpression.getExpressionScope() == null){
                                    throw new ExpressionError("Failed to parse function no valid scope given",s,e);
                                }
                                return compiledExpression.getExpressionScope().parseFunction(token, args.evaluate());
                            } catch (Exception ex) {
                                throw new ExpressionError("Failed to parse function: " + token, s, endFunc, ex);
                            }
                        }

                        @Override
                        public String toString() {
                            return token + args.toString();
                        }
                    };
                } else {

                    final String preProcessedToken;
                    if(expressionCompilerScope != null){
                        preProcessedToken = expressionCompilerScope.preProcessVariableMnemonic(token);
                    }else{
                        preProcessedToken = token;
                    }
                    if(!isInPreProcess && expressionCompilerScope != null && !token.equals(preProcessedToken)) {

                        isInPreProcess = true;
                        xEM = new Expression() {
                            private CompiledExpression pp;
                            {
                                @Deprecated //this needs to fetch the line it was generated on
                                ExpressionCompiler2.PrivateExpressionCompiler pec = new ExpressionCompiler2.PrivateExpressionCompiler(preProcessedToken, null, 0);
                                pec.isInPreProcess = true;
                                pp = pec.compileExpression();
                                pec.compiledExpression = compiledExpression;

                            }
                            @Override
                            public Object evaluate() {
                                try {
                                    return pp.evaluate();
                                } catch (Exception error) {
                                    throw new ExpressionError("Failed to parse: " + pp.toString() + " from value: " + token, s, e, error);
                                }
                            }

                            @Override
                            public String toString() {
                                return pp.toString();
                            }
                        };
                        isInPreProcess = false;
                    }else{
                        xEM = new Expression() {
                            @Override
                            public Object evaluate() {
                                if (compiledExpression.getExpressionScope() == null) {
                                    throw new ExpressionError("Failed to parse symbol no valid scope given", s, e);
                                }
                                try {
                                    return compiledExpression.getExpressionScope().parseVariable(token);
                                } catch (Exception ex) {
                                    throw new ExpressionError("Failed to parse symbol", s, e, ex);
                                }
                            }

                            @Override
                            public String toString() {
                                return token;
                            }
                        };
                    }
                }

            } else if (ch == '"') {
                nextChar();
                while (ch != '"') {
                    if(ch == '\\'){
                        nextChar();
                        if(ch == '"'){
                            nextChar();
                        }
                    }
                    nextChar();
                }
                nextChar();

                final String string = str;
                final int start = startPos + 1;
                final int end = pos - 1;
                xEM = new Expression() {
                    @Override
                    public Object evaluate() {
                        return string.substring(start, end);
                    }

                    @Override
                    public String toString() {
                        return '"' + string.substring(start, end) + '"';
                    }
                };
            }else{
                throw new CompilationError("Unexpected: " + (char) ch, s, s);
            }

            //access stuff
            Expression access = xEM;
            for(;;) {
                final int start = removeWhiteSpace();
                final Expression accessf = access;
                if (eat(".")) {

                    final int memberAccessStart = pos;
                    if (Character.isAlphabetic(ch) || ch == '_' || ch == '$') { // functions
                        while (Character.isAlphabetic(ch) || Character.isDigit(ch) || ch == '_' || ch == '$')
                            nextChar();
                    }
                    final int memberAccessEnd = pos;
                    final String memberAccess = str.substring(memberAccessStart, memberAccessEnd);

                    access = new Expression() {
                        @Override
                        public Object evaluate() {
                            try {
                                return compiledExpression.getExpressionScope().parseMemberAccess(
                                        accessf.evaluate(), memberAccess);
                            } catch (Exception ex) {
                                throw new ExpressionError("Failed evaluate member access", start, memberAccessEnd, ex);
                            }
                        }

                        @Override
                        public String toString() {
                            return accessf.toString() + "." + memberAccess;
                        }
                    };
                } else {
                    return access;
                }
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
        } else {
            return a.intValue() + b.intValue();
        }
    }

    public static Number subtract(Number a, Number b) {
        if (a instanceof Double || b instanceof Double) {
            return a.doubleValue() - b.doubleValue();
        } else if (a instanceof Float || b instanceof Float) {
            return a.floatValue() - b.floatValue();
        } else if (a instanceof Long || b instanceof Long) {
            return a.longValue() - b.longValue();
        } else {
            return a.intValue() - b.intValue();
        }
    }

    public static Number multiply(Number a, Number b) {
        if (a instanceof Double || b instanceof Double) {
            return a.doubleValue() * b.doubleValue();
        } else if (a instanceof Float || b instanceof Float) {
            return a.floatValue() * b.floatValue();
        } else if (a instanceof Long || b instanceof Long) {
            return a.longValue() * b.longValue();
        } else {
            return a.intValue() * b.intValue();
        }
    }

    public static Number divide(Number a, Number b) {
        if (a instanceof Double || b instanceof Double) {
            return a.doubleValue() / b.doubleValue();
        } else if (a instanceof Float || b instanceof Float) {
            return a.floatValue() / b.floatValue();
        } else if (a instanceof Long || b instanceof Long) {
            return a.longValue() / b.longValue();
        } else {
            return a.intValue() / b.intValue();
        }
    }

    public static Number mod(Number a, Number b) {
        if (a instanceof Double || b instanceof Double) {
            return a.doubleValue() % b.doubleValue();
        } else if (a instanceof Float || b instanceof Float) {
            return a.floatValue() % b.floatValue();
        } else if (a instanceof Long || b instanceof Long) {
            return a.longValue() % b.longValue();
        } else {
            return a.intValue() % b.intValue();
        }
    }

    public static Number shiftLeft(Number a, Number b) {
        if (a instanceof Integer) {
            return a.intValue() << b.intValue();
        } else if (a instanceof Long) {
            return a.longValue() << b.intValue();
        } else if (a instanceof Short) {
            return a.shortValue() << b.intValue();
        } else if (a instanceof Byte) {
            return a.byteValue() << b.intValue();
        } else {
            return a.intValue() << b.intValue();
        }
    }

    public static Number shiftRight(Number a, Number b) {
        if (a instanceof Integer) {
            return a.intValue() >> b.intValue();
        } else if (a instanceof Long) {
            return a.longValue() >> b.intValue();
        } else if (a instanceof Short) {
            return a.shortValue() >> b.intValue();
        } else if (a instanceof Byte) {
            return a.byteValue() >> b.intValue();
        } else {
            return a.intValue() >> b.intValue();
        }
    }

    public static Number bitwiseAnd(Number a, Number b) {
        if (a instanceof Integer) {
            return a.intValue() & b.intValue();
        } else if (a instanceof Long) {
            return a.longValue() & b.intValue();
        } else if (a instanceof Short) {
            return a.shortValue() & b.intValue();
        } else if (a instanceof Byte) {
            return a.byteValue() & b.intValue();
        } else {
            return a.intValue() & b.intValue();
        }
    }

    public static Number bitwiseOr(Number a, Number b) {
        if (a instanceof Integer) {
            return a.intValue() | b.intValue();
        } else if (a instanceof Long) {
            return a.longValue() | b.intValue();
        } else if (a instanceof Short) {
            return a.shortValue() | b.intValue();
        } else if (a instanceof Byte) {
            return a.byteValue() | b.intValue();
        } else {
            return a.intValue() | b.intValue();
        }
    }

    public static Number bitwiseXor(Number a, Number b) {
        if (a instanceof Integer) {
            return a.intValue() ^ b.intValue();
        } else if (a instanceof Long) {
            return a.longValue() ^ b.intValue();
        } else if (a instanceof Short) {
            return a.shortValue() ^ b.intValue();
        } else if (a instanceof Byte) {
            return a.byteValue() ^ b.intValue();
        } else {
            return a.intValue() ^ b.intValue();
        }
    }

    public static Number invert(Number a) {
        if (a instanceof Integer) {
            return ~a.intValue();
        } else if (a instanceof Long) {
            return ~a.longValue();
        } else if (a instanceof Short) {
            return ~a.shortValue();
        } else if (a instanceof Byte) {
            return ~a.byteValue();
        } else {
            return ~a.intValue();
        }
    }

    private static final ExpressionCompiler2.NumberComparator NUMBER_COMPARATOR = new ExpressionCompiler2.NumberComparator();

    private static class NumberComparator implements Comparator {
        @SuppressWarnings("unchecked")
        @Override
        public int compare(Object number1, Object number2) {
            if (number2.getClass().equals(number1.getClass())) {
                // both numbers are instances of the same type!
                if (number1 instanceof Comparable) {
                    // and they implement the Comparable interface
                    return ((Comparable) number1).compareTo(number2);
                }
            }
            // for all different Number types, let's check there double values
            if (number1 instanceof Double || number1 instanceof Float || number1 instanceof BigDecimal || number2 instanceof Double || number2 instanceof Float || number2 instanceof BigDecimal) {
                if (((Number) number1).doubleValue() < ((Number) number2).doubleValue())
                    return -1;
                if (((Number) number1).doubleValue() > ((Number) number2).doubleValue())
                    return 1;
                return 0;
            } else {
                if (((Number) number1).longValue() < ((Number) number2).longValue())
                    return -1;
                if (((Number) number1).longValue() > ((Number) number2).longValue())
                    return 1;
                return 0;
            }
        }
    }

    public static Number parseNumber(String string) {

        String number = string.toLowerCase();


        if (number.contains(".")) {

            if(number.endsWith("f")){
                number = number.substring(0, number.length() - 1);
                return Float.parseFloat(number);
            }else{
                return Double.parseDouble(number);
            }

        } else {
            int radix;

            if (number.startsWith("0b")) {
                number = number.substring(2);
                radix = 2;
            } else if (number.startsWith("0x")) {
                number = number.substring(2);
                radix = 16;
            } else if (number.startsWith("0")) {
                number = number.substring(1);
                radix = 8;
            } else {
                radix = 10;
            }

            if (number.endsWith("l")) {
                number = number.substring(0, number.length() - 1);
                return Long.parseLong(number, radix);
            } else {
                return Integer.parseInt(number, radix);
            }
        }
    }
}
