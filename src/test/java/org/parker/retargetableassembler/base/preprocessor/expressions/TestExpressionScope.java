package org.parker.retargetableassembler.base.preprocessor.expressions;

import org.parker.retargetableassembler.base.preprocessor.expressions.scope.BaseExpressionScope;
import org.parker.retargetableassembler.base.preprocessor.expressions.scope.ExpressionScope;

public class TestExpressionScope extends BaseExpressionScope {

    @Override
    public Object parseMemberAccess(Object parseVariable, String memberAccess) {
        if(parseVariable instanceof TestDummyObject){
            if(memberAccess.equals("someMember")){
                return ((TestDummyObject) parseVariable).someMember;
            }else{
                return super.parseMemberAccess(parseVariable, memberAccess);
            }
        }else{
            return super.parseMemberAccess(parseVariable, memberAccess);
        }
    }

    @Override
    public Object parseVariable(String token) {
        if(token.equals("TestDummyObject")){
            return new TestDummyObject();
        }else{
            return super.parseVariable(token);
        }
    }

    public static class TestDummyObject{
        public final static int someMember = 22;
    }
}
