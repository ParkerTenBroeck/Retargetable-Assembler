package org.parker.retargetableassembler.tokenizer.calc.java;/*
  This example comes from a short article series in the Linux
  Gazette by Richard A. Sevenich and Christopher Lopes, titled
  "Compiler Construction Tools". The article series starts at

  http://www.linuxgazette.com/issue39/sevenich.html

  Small changes and updates to newest JFlex+Cup versions
  by Gerwin Klein
*/

/*
  Commented By: Christopher Lopes

  <p>To Run:
  <pre>
  java Main test.txt
  </pre>
  where {@code test.txt} is an test input file for the calculator.
*/


import java.io.*;

import java_cup.runtime.Symbol;
import org.parker.retargetableassembler.tokenizer.calc.cup.parser;
import org.parker.retargetableassembler.tokenizer.calc.jflex.Lexer;

public class Main {
  public static void main(String[] argv) {
    /* Start the parser */
    if(argv.length == 0){
      argv = new String[]{"C:\\Users\\parke\\Downloads\\jflex-1.8.2\\jflex-1.8.2\\examples\\cup-lcalc\\src\\test\\data\\test.txt"};
    }

    try {
      parser p = new parser(new Lexer(new FileReader(argv[0])));
      Symbol result = p.parse();
      System.out.println(result);
    } catch (Exception e) {
      /* do cleanup here -- possibly rethrow e */
      e.printStackTrace();
    }
  }
}
