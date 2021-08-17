package org.parker.retargetableassembler.tokenizer.jj.java;

import java.io.*;
import java_cup.runtime.Symbol;
import org.parker.retargetableassembler.tokenizer.jj.cup.sym;
import org.parker.retargetableassembler.tokenizer.jj.jflex.Scanner;
import org.parker.retargetableassembler.tokenizer.jj.jflex.UnicodeEscapes;

/**
 * Simple test driver for the java lexer. Just runs it on some input files and produces debug
 * output. Needs symbol class from parser.
 */
public class TestLexer {

  /** some numerals to for lexer testing */
  int intDec = 37;

  long longDec = 37l;
  int intHex = 0x0001;
  long longHex = 0xFFFFl;
  int intOct = 0377;
  long longOc = 007l;
  int smallest = -2147483648;

  public static void main(String[] argv) {

    if(argv.length == 0){
      argv = new String[]{"C:\\GitHub\\Retargetable-Assembler\\src\\main\\java\\org\\parker\\retargetableassembler\\tokenizer\\java\\TestLexer.java"};
    }

    for (int i = 0; i < argv.length; i++) {
      try {
        System.out.println("Lexing [" + argv[i] + "]");
        Scanner scanner = new Scanner(new UnicodeEscapes(new FileReader(argv[i])));

        Symbol s;
        do {
          s = scanner.debug_next_token();
          System.out.println("token: " + s);
        } while (s.sym != sym.EOF);

        System.out.println("No errors.");
      } catch (Exception e) {
        e.printStackTrace(System.out);
        System.exit(1);
      }
    }
  }
}
