package org.parker.retargetableassembler.pipe.lex.jflex;

import org.parker.retargetableassembler.pipe.lex.cup.AssemblerSym;

import java.io.File;

@SuppressWarnings("unused")
public class LexSymbol extends java_cup.runtime.Symbol implements AssemblerSym {
    private final int line;
    private final int column;
    private final int size;
    private final long charPos;
    private final File file;

    public LexSymbol(int type, int line,  int column, long charPos, int size) {
        this(null, type, line, column, charPos, size, -1, -1, null);
    }
    public LexSymbol(File file, int type, int line,  int column, long charPos, int size) {
        this(file, type, line, column, charPos, size, -1, -1, null);
    }

    public LexSymbol(int type, int line, int column, long charPos, int size, Object value) {
        this(null, type, line, column, charPos, size, -1, -1, value);
    }

    public LexSymbol(File file, int type, int line, int column, long charPos, int size, Object value) {
        this(file, type, line, column, charPos, size, -1, -1, value);
    }

    /** Default is always NULL EOF
     *
     */
    public LexSymbol(){
        this(EOF);
    }

    public LexSymbol(int sym_num) {
        this(null, sym_num, -1, -1, -1, -1, -1, -1, null);
    }

    public LexSymbol(File file, int type, int line, int column, long charPos, int size, int left, int right, Object value) {
        super(type, left, right, value);
        this.file = file;
        this.line = line;
        this.column = column;
        this.charPos = charPos;
        this.size = size;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int getSize() {
        return size;
    }

    public long getCharPos() {
        return charPos;
    }

    public File getFile() {
        return file;
    }

    public String toString() {
        return "line: "
                + (line + 1)
                + ", column: "
                + (column + 1)
                + ", index: "
                + (charPos)
                + ", size: "
                + size
                + ", sym: "
                + org.parker.retargetableassembler.pipe.lex.cup.AssemblerSym.terminalNames[this.sym]
                + "("
                + this.sym
                + ") "
                + "\nfile: "
                + file.getAbsolutePath()
                + (value == null ? "" : (" , value: '" + value + "'"))
                + "\n";

    }

    public static LexSymbol combine(int newType, Object newVal, LexSymbol sym1, LexSymbol sym2){
        return new LexSymbol(sym1.file, newType, sym1.line, sym1.column, sym1.charPos,
                sym1.size + sym2.size, sym1.left, sym2.right, newVal);
    }

}