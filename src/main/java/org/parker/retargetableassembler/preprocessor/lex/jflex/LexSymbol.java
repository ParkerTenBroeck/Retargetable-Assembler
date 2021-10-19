package org.parker.retargetableassembler.preprocessor.lex.jflex;

import java_cup.runtime.Symbol;
import org.parker.retargetableassembler.preprocessor.lex.cup.AssemblerSym;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("unused")
public class LexSymbol implements AssemblerSym {
    private final java_cup.runtime.Symbol rSym;
    private final int line;
    private final int column;
    private final int size;
    private final long charPos;
    private final File file;
    private final LexSymbol parent;

    public LexSymbol(int type, int line, int column, long charPos, int size) {
        this(null, type, line, column, charPos, size, -1, -1, null);
    }
    public LexSymbol(File file, int type, int line, int column, long charPos, int size) {
        this(file, type, line, column, charPos, size, -1, -1, null);
    }

    public LexSymbol(int type, int line, int column, long charPos, int size, Object value) {
        this(null, type, line, column, charPos, size, -1, -1, value);
    }

    public LexSymbol(File file, int type, int line, int column, long charPos, int size, Object value) {
        this(file, type, line, column, charPos, size, -1, -1, value);
    }

    public LexSymbol invertParentRelationship(){
        return invertParentRelationshipTruncate(0);
    }

    public LexSymbol invertParentRelationshipTruncate(int trunkate) {
        if(trunkate < -1) throw new IndexOutOfBoundsException("Trunkate cannot be negative");
        List<LexSymbol> tmp = new ArrayList<>();
        LexSymbol sym = this;
        for(int i = 0; i < 100; i ++){ //max iterations
            if(sym == null) break;
            tmp.add(sym.clone());
            sym = sym.parent;
        }

        for(int i = tmp.size() - 1; i > trunkate; i --){
            tmp.get(i).setParent(tmp.get(i -1));
        }
        return tmp.get(tmp.size() - 1);
    }

    @Override
    protected LexSymbol clone() {
        return new LexSymbol(file, rSym.sym, line, column, charPos, size, rSym.left, rSym.right, rSym.value, parent);
    }

    protected LexSymbol cloneWithNewParent(LexSymbol parent) {
        return new LexSymbol(file, rSym.sym, line, column, charPos, size, rSym.left, rSym.right, rSym.value, parent);
    }

    protected LexSymbol cloneWithNewSym(int sym) {
        return new LexSymbol(file, sym, line, column, charPos, size, rSym.left, rSym.right, rSym.value, parent);
    }

    protected LexSymbol cloneWithNewValue(Object value) {
        return new LexSymbol(file, rSym.sym, line, column, charPos, size, rSym.left, rSym.right, value, parent);
    }

    protected LexSymbol cloneWithNewSymAndValue(int sym, Object value) {
        return new LexSymbol(file, sym, line, column, charPos, size, rSym.left, rSym.right, value, parent);
    }

    /** Default is always NULL EOF
     *
     */
    public LexSymbol(){
        this(EOF);
    }

    public LexSymbol(int sym_num) {
        this(null, sym_num, -1, -1, -1, -1, -1, -1, null, null);
    }

    public LexSymbol(File file, int type, int line, int column, long charPos, int size, int left, int right, Object value, LexSymbol parent) {
        rSym = new Symbol(type, left, right, value);
        //super(type, left, right, value);
        this.file = file;
        this.line = line;
        this.column = column;
        this.charPos = charPos;
        this.size = size;
        this.parent = parent;
    }

    public LexSymbol(File file, int type, int line, int column, long charPos, int size, int left, int right, Object value) {
        this(file, type, line, column, charPos, size, left, right, value, null);
    }

    public LexSymbol getSuperParent(){
        return this.parent == null ? this : this.getParent();
    }

    public static LexSymbol combine(int identifier, Object o, Collection<LexSymbol> causeCollection) {
        LexSymbol[] temp = causeCollection.toArray(new LexSymbol[0]);
        if(temp.length > 0) {
            LexSymbol first = temp[0];
            LexSymbol last = temp[temp.length - 1];
            return combine(identifier, o, first, last);
        }else{
            return null;
        }
    }

    public static LexSymbol combine(int newType, Object newVal, LexSymbol sym1, LexSymbol sym2){
        return new LexSymbol(sym1.file, newType, sym1.line, sym1.column, sym1.charPos,
                (int) ((sym2.size + sym2.charPos) - sym1.charPos), sym1.rSym.left, sym2.rSym.right, newVal);
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
                + AssemblerSym.terminalNames[this.rSym.sym]
                + "("
                + this.rSym
                + ") "
                + "\nfile: "
                + file.getAbsolutePath()
                + (this.rSym.value == null ? "" : (" , value: '" + this.rSym.value + "'"))
                + "\n";
    }

    public int getSym() { return this.rSym.sym; }

    public Object getValue() { return this.rSym.value; }

    public LexSymbol setSym(int sym) {
        return this.cloneWithNewSym(sym);
    }

    public LexSymbol setValue(Object value) {
        return this.cloneWithNewValue(value);
    }

    public LexSymbol setSymValue(int sym, Object value) {
        return this.cloneWithNewSymAndValue(sym, value);
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

    public LexSymbol getParent(){
        return parent;
    }

    public LexSymbol setParent(LexSymbol parent){
        return this.cloneWithNewParent(parent);
    }
}
