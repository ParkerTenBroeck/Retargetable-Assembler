package org.parker.retargetableassembler.base.preprocessor;

import org.parker.retargetableassembler.base.preprocessor.statements.PreProcessedStatement;

import java.io.File;
import java.util.List;

public interface PreProcessor {

    List<PreProcessedStatement> preprocess(File file);

}
