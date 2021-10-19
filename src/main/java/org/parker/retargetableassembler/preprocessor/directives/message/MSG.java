package org.parker.retargetableassembler.preprocessor.directives.message;

import org.parker.retargetableassembler.Report;
import org.parker.retargetableassembler.preprocessor.PreProcessor;
import org.parker.retargetableassembler.preprocessor.directives.PreProcessorDirective;
import org.parker.retargetableassembler.preprocessor.lex.jflex.LexSymbol;
import org.parker.retargetableassembler.preprocessor.util.BufferUtils;

public final class MSG {

    public static final class MMSG implements PreProcessorDirective {
        @Override
        public void init(LexSymbol root, PreProcessor pp) {
            LOG(root, pp, Report.ReportLevel.message);
        }
    }

    public static final class WMSG implements PreProcessorDirective {
        @Override
        public void init(LexSymbol root, PreProcessor pp) {
            LOG(root, pp, Report.ReportLevel.warning);
        }
    }

    public static final class EMSG implements PreProcessorDirective {

        @Override
        public void init(LexSymbol root, PreProcessor pp) {
            LOG(root, pp, Report.ReportLevel.error);
        }
    }

    public static final class MMSGE implements PreProcessorDirective {
        @Override
        public void init(LexSymbol root, PreProcessor pp) {
            LOGE(root, pp, Report.ReportLevel.message);
        }
    }

    public static final class WMSGE implements PreProcessorDirective {
        @Override
        public void init(LexSymbol root, PreProcessor pp) {
            LOGE(root, pp, Report.ReportLevel.warning);
        }
    }

    public static final class EMSGE implements PreProcessorDirective {

        @Override
        public void init(LexSymbol root, PreProcessor pp) {
            LOGE(root, pp, Report.ReportLevel.error);
        }
    }

    public static void LOG(LexSymbol root, PreProcessor pp, Report.ReportLevel level){
        BufferUtils.LineTerminatorIterator line = BufferUtils.tillLineTerminator(pp.getIteratorStack(), false);

        String msg = "";
        while(line.hasNext()){
            LexSymbol s = line.next();
                        msg += ("".equals(msg) ? "" : ", ") + s.getValue().toString();
            if(line.hasNext()){
                s = line.next();
                if(s.getSym() != LexSymbol.COMMA){
                    line.toLineTerminator();
                    pp.report().unexpectedTokenError(s, LexSymbol.COMMA);
                    break;
                }
            }else{
                break;
            }
        }
        pp.report().report(msg, null, root, level);
    }

    public static void LOGE(LexSymbol root, PreProcessor pp, Report.ReportLevel level){
        //BufferUtils.LineTerminatorIterator line = BufferUtils.tillLineTerminator(pp.getIteratorStack(), false);
        PreProcessor.EvaluatedExpression[] eps = pp.evaluateExpressions();
        String msg = "";
        for(int i = 0; i < eps.length; i ++){
            if(eps[i].val != null) {
                msg += eps[i].val;
                msg += i != eps.length - 1 ? ", " : "";
            }else{
                pp.report().reportError("Error evaluating expression", eps[i].expression.toSymbols());
            }
        }
        pp.report().report(msg, null, root, level);
    }
}
