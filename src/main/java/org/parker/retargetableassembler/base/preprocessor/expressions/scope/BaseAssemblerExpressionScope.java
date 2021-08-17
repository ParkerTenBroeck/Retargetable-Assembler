package org.parker.retargetableassembler.base.preprocessor.expressions.scope;

import org.parker.retargetableassembler.base.assembler.BaseAssembler;
import org.parker.retargetableassembler.exception.preprocessor.expression.ParseMemberAccessError;

public class BaseAssemblerExpressionScope<A extends BaseAssembler> extends BaseExpressionScope {

    protected A assembler;
    public void setAssembler(A assembler){
        this.assembler = assembler;
    }

    @Override
    public Object parseVariable(String token) {
        if(true){
            return null;//assembler.getLabel(token);
        }else {
            return super.parseVariable(token);
        }
    }

    @Override
    public Object parseMemberAccess(Object variable, String memberAccess) {
        //if(variable instanceof Label){
        //    if(memberAccess.equals("byteAddress")){
        //        try {
        //            return ((Label) variable).getAddress();
        //        }catch (Exception e){
        //            throw new ParseMemberAccessError("Cannot parse byteAddress for label", e);
        //        }
        //    }
        //}
        return super.parseMemberAccess(variable, memberAccess);
    }


}