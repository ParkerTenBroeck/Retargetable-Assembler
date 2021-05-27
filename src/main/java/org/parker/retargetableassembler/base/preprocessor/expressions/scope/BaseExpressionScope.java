package org.parker.retargetableassembler.base.preprocessor.expressions.scope;

import org.parker.retargetableassembler.exception.preprocessor.expression.ParseFunctionError;

import java.io.Serializable;
import java.util.HashMap;

public class BaseExpressionScope implements ExpressionScope{

    private static final HashMap<String, FunctionParser> FUNCTION_MAP = new HashMap<>();

    static {
        FUNCTION_MAP.put("sqrt", new FunctionParser() {
            @Override
            public Object parse(Object parms) {
                if (parms instanceof Number) {
                    return Math.sqrt(((Number) parms).doubleValue());
                } else {
                    throw new IllegalArgumentException("Sqrt function cannot take: " + parms.getClass().getSimpleName());
                }
            }
        });

        FUNCTION_MAP.put("sin", new FunctionParser() {
            @Override
            public Object parse(Object parms) {
                if (parms instanceof Number) {
                    return Math.sin(((Number) parms).doubleValue());
                } else {
                    throw new IllegalArgumentException("sin function cannot take: " + parms.getClass().getSimpleName());
                }
            }
        });

        FUNCTION_MAP.put("cos", new FunctionParser() {
            @Override
            public Object parse(Object parms) {
                if (parms instanceof Number) {
                    return Math.cos(((Number) parms).doubleValue());
                } else {
                    throw new IllegalArgumentException("cos function cannot take: " + parms.getClass().getSimpleName());
                }
            }
        });

        FUNCTION_MAP.put("tan", new FunctionParser() {
            @Override
            public Object parse(Object parms) {
                if (parms instanceof Number) {
                    return Math.tan(((Number) parms).doubleValue());
                } else {
                    throw new IllegalArgumentException("tan function cannot take: " + parms.getClass().getSimpleName());
                }
            }
        });

        FUNCTION_MAP.put("toRadians", new FunctionParser() {
            @Override
            public Object parse(Object parms) {
                if (parms instanceof Number) {
                    return Math.toRadians(((Number) parms).doubleValue());
                } else {
                    throw new IllegalArgumentException("toRadians function cannot take: " + parms.getClass().getSimpleName());
                }
            }
        });

        FUNCTION_MAP.put("toDegrees", new FunctionParser() {
            @Override
            public Object parse(Object parms) {
                if (parms instanceof Number) {
                    return Math.toDegrees(((Number) parms).doubleValue());
                } else {
                    throw new IllegalArgumentException("toDegrees function cannot take: " + parms.getClass().getSimpleName());
                }
            }
        });

        FUNCTION_MAP.put("pow", new FunctionParser() {
            @Override
            public Object parse(Object parms) {
                if (parms instanceof Object[]) {
                    Object[] newParms = (Object[]) parms;
                    if (newParms.length == 2) {
                        if (newParms[0] instanceof Number || newParms[1] instanceof Number) {
                            return Math.pow(((Number) newParms[0]).doubleValue(), ((Number) newParms[1]).doubleValue());
                        } else {
                            throw new IllegalArgumentException("Invalid parameters for function Pow found: " + newParms.getClass().getSimpleName() + " and " + newParms.getClass().getSimpleName() + " but not Number and Number");
                        }
                    } else {
                        throw new IllegalArgumentException("Invalid number of parameters for function Pow requires 2 found: " + newParms.length);
                    }
                } else {
                    throw new IllegalArgumentException("Pow function cannot take: " + parms.getClass().getSimpleName());
                }
            }
        });
    }



    /**
     * Too add more functions yes
     */
    @Override
    public Object parseFunction(String token, Object parms) {
        if (FUNCTION_MAP.containsKey(token)) {
            return FUNCTION_MAP.get(token).parse(parms);
        } else {
            throw new ParseFunctionError("Function: " + token + " is not defined");
        }
    }

    private static abstract class FunctionParser implements Serializable {
        abstract public Object parse(Object parms);
    }
}
