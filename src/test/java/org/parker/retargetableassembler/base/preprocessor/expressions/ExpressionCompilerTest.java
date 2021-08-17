package org.parker.retargetableassembler.base.preprocessor.expressions;


import org.junit.Assert;
import org.junit.Test;
import org.parker.retargetableassembler.base.preprocessor.expressions.scope.ExpressionScope;
import org.parker.retargetableassembler.base.preprocessor.util.Line;

import java.io.*;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class ExpressionCompilerTest {

    @Test
    public void parse(){

        ExpressionScope es = new TestExpressionScope();

        HashMap<Object, String> resultToExpressionStringMap= Expressions.resultExpressionMap;
        HashMap<Object, CompiledExpression> resultToExpressionMap= new HashMap<>();

        ExpressionCompiler ec = new ExpressionCompiler();

        for(Map.Entry<Object, String> ex: resultToExpressionStringMap.entrySet()){
            CompiledExpression compiled = ec.compileExpression(ex.getValue(), null, 0);
            compiled.setExpressionScope(es);
            CompiledExpression toStringAndBack = ec.compileExpression(compiled.toString(), null, 0);
            toStringAndBack.setExpressionScope(es);
            Assert.assertEquals(compiled.evaluate(), toStringAndBack.evaluate());
            resultToExpressionMap.put(ex.getKey(), compiled);
        }

        for(Map.Entry<Object, CompiledExpression> ex: resultToExpressionMap.entrySet()){
            Object val = ex.getValue().evaluate();
            Assert.assertEquals(ex.getKey(), val);
        }


    }

    @Test
    public void ExpressionCompilerSerializationTest(){

        ExpressionCompiler ec = new ExpressionCompiler();
        Line line = new Line();
        line.setLine("(5+4+(4*(5/2.5)))");
        CompiledExpression e = ec.compileExpression("(5+4+(4*(5/2.5)))", line, 0);

        CompiledExpression en = null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(e);
            out.flush();
            byte[] yourBytes = bos.toByteArray();

            try{
                ByteArrayInputStream bin = new ByteArrayInputStream(yourBytes);
                ObjectInputStream in = new ObjectInputStream(bin);
                en = (CompiledExpression) in.readObject();
            }catch(Exception ee){
                ee.printStackTrace();
            }

        }catch(Exception eee){
            eee.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        Assert.assertEquals(en.evaluate(), e.evaluate());
    }

    @Test
    public void parseNumber() {
        //ExpressionCompiler.parseNumber();
        Assert.assertEquals(0xFFa, ExpressionCompiler.parseNumber("0xFFa"));
        Assert.assertEquals(12.34123, ExpressionCompiler.parseNumber("12.34123"));
        Assert.assertEquals(12.34123f, ExpressionCompiler.parseNumber("12.34123f"));
        Assert.assertEquals(33L, ExpressionCompiler.parseNumber("33L"));
        Assert.assertEquals(034, ExpressionCompiler.parseNumber("034"));
        //NumberFormat.getInstance().parse()
    }
}