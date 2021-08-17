package org.parker.retargetableassembler.tokenizer.jj.java;/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Copyright (C) 1998-2018  Gerwin Klein <lsf@jflex.de>                    *
 * All rights reserved.                                                    *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import java_cup.runtime.Symbol;
import org.parker.retargetableassembler.tokenizer.jj.cup.parser;
import org.parker.retargetableassembler.tokenizer.jj.jflex.Scanner;
import org.parker.retargetableassembler.tokenizer.jj.jflex.UnicodeEscapes;

import java.io.*;

/**
 * Simple test driver for the java parser. Just runs it on some input files, gives no useful output.
 */
public class JavaParser {

  public static void main(String[] argv) {

    if(argv.length == 0){
      argv = new String[]{"C:\\GitHub\\Retargetable-Assembler\\src\\main\\java\\org\\parker\\retargetableassembler\\tokenizer\\java\\TestLexer.java"};
    }

    for (int i = 0; i < argv.length; i++) {
      try {
        System.out.println("Parsing [" + argv[i] + "]");
        Scanner s = new Scanner(new UnicodeEscapes(new FileReader(argv[i])));
        parser p = new parser(s);
        Symbol sym = p .parse();
        System.out.println(sym);

        System.out.println("No errors.");
      } catch (Exception e) {
        e.printStackTrace(System.err);
        System.exit(1);
      }
    }
  }
}
