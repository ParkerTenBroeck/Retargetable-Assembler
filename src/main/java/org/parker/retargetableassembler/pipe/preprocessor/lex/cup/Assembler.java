
//----------------------------------------------------
// The following code was generated by CUP v0.11b 20160615 (GIT 4ac7450)
//----------------------------------------------------

package org.parker.retargetableassembler.pipe.preprocessor.lex.cup;

import java_cup.runtime.XMLElement;

/** CUP v0.11b 20160615 (GIT 4ac7450) generated parser.
  */
@SuppressWarnings({"rawtypes"})
public class Assembler extends java_cup.runtime.lr_parser {

 public final Class getSymbolContainer() {
    return AssemblerSym.class;
}

  /** Default constructor. */
  @Deprecated
  public Assembler() {super();}

  /** Constructor which sets the default scanner. */
  @Deprecated
  public Assembler(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public Assembler(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\002\000\002\002\003\000\002\002\004" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\004\000\004\011\005\001\002\000\004\002\006\001" +
    "\002\000\004\002\001\001\002\000\004\002\000\001\002" +
    "" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\004\000\004\002\003\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$Assembler$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$Assembler$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$Assembler$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 1;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}


/** Cup generated class to encapsulate user supplied action code.*/
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
class CUP$Assembler$actions {
  private final Assembler parser;

  /** Constructor */
  CUP$Assembler$actions(Assembler parser) {
    this.parser = parser;
  }

  /** Method 0 with the actual generated action code for actions 0 to 300. */
  public final java_cup.runtime.Symbol CUP$Assembler$do_action_part00000000(
    int                        CUP$Assembler$act_num,
    java_cup.runtime.lr_parser CUP$Assembler$parser,
    java.util.Stack            CUP$Assembler$stack,
    int                        CUP$Assembler$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$Assembler$result;

      /* select the action based on the action number */
      switch (CUP$Assembler$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // goal ::= LINE_TERMINATOR 
            {
              Object RESULT =null;

              CUP$Assembler$result = parser.getSymbolFactory().newSymbol("goal",0, ((java_cup.runtime.Symbol)CUP$Assembler$stack.peek()), ((java_cup.runtime.Symbol)CUP$Assembler$stack.peek()), RESULT);
            }
          return CUP$Assembler$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= goal EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$Assembler$stack.elementAt(CUP$Assembler$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$Assembler$stack.elementAt(CUP$Assembler$top-1)).right;
		Object start_val = (Object)((java_cup.runtime.Symbol) CUP$Assembler$stack.elementAt(CUP$Assembler$top-1)).value;
		RESULT = start_val;
              CUP$Assembler$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$Assembler$stack.elementAt(CUP$Assembler$top-1)), ((java_cup.runtime.Symbol)CUP$Assembler$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$Assembler$parser.done_parsing();
          return CUP$Assembler$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number "+CUP$Assembler$act_num+"found in internal parse table");

        }
    } /* end of method */

  /** Method splitting the generated action code into several parts. */
  public final java_cup.runtime.Symbol CUP$Assembler$do_action(
    int                        CUP$Assembler$act_num,
    java_cup.runtime.lr_parser CUP$Assembler$parser,
    java.util.Stack            CUP$Assembler$stack,
    int                        CUP$Assembler$top)
    throws java.lang.Exception
    {
              return CUP$Assembler$do_action_part00000000(
                               CUP$Assembler$act_num,
                               CUP$Assembler$parser,
                               CUP$Assembler$stack,
                               CUP$Assembler$top);
    }
}

}