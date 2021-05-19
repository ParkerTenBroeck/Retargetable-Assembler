package org.parker.retargetableassembler.base.preprocessor.expressions;


import org.junit.Assert;
import org.junit.Test;
import org.parker.retargetableassembler.base.preprocessor.util.Line;

import java.io.*;

public class ExpressionCompilerTest {

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

}