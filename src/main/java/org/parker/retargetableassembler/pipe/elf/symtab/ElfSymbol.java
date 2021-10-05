package org.parker.retargetableassembler.pipe.elf.symtab;

@SuppressWarnings("unused")
public abstract class ElfSymbol {

    final static int STB_LOCAL = 0;
    final static int STB_GLOBAL = 1;
    final static int STB_WEAK = 2;
    final static int STB_LOPROC = 13;
    final static int STB_HIPROC = 15;

    final static int STT_NOTYPE = 0;
    final static int STT_OBJECT = 1;
    final static int STT_FUNC = 2;
    final static int STT_SECTION = 3;
    final static int STT_FILE = 4;
    final static int STT_COMMON = 5;
    final static int STT_TLS = 6;
    final static int STT_LOOS = 10;
    final static int STT_LOPROC = 13;
    final static int STT_HIPROC = 15;

    final static int STV_DEFAULT = 0;
    final static int STV_INTERNAL = 1;
    final static int STV_HIDDEN = 2;
    final static int STV_PROTECTED = 3;

    int st_name;
    transient String name;
    transient SymbolType type;
    byte st_info;
    byte st_other;
    short st_shndx;
    long st_value;
    long st_size;

    public int getBind(){
        return (st_info >> 4) & 0xF;
    }
    public void setBind(int type){
        st_info = (byte)((st_info & (~0xF0)) | ((type & 0xF) << 4));
    }
    public int getType(){
        return st_info & 0xF;
    }
    public void setType(int type){
        st_info = (byte)((st_info & (~0xF)) | (type & 0xF));
    }
    public int getVisibility(){
        return st_other&0x3;
    }
    public void setVisibility(int vis){
        st_other = (byte)((st_other & (~0x3)) | (vis&0x3));
    }

    public final int getSize(){
        return 24;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("0x" + String.format("%16X", st_value).replace(' ', '0') + " ");
        sb.append(String.format("%5d", st_size) + " ");
        switch (getType()){
            case STT_NOTYPE:
                sb.append("NOTYPE  ");
                break;
            case STT_OBJECT:
                sb.append("OBJECT ");
                break;
            case STT_FUNC:
                sb.append("FUNC   ");
                break;
            case STT_SECTION:
                sb.append("SECTION ");
                break;
            case STT_FILE:
                sb.append("FILE    ");
                break;
            case STT_COMMON:
                sb.append("COMMON ");
                break;
            case STT_TLS:
                sb.append("TLS    ");
                break;
            case STT_LOOS:
                sb.append("LOOS   ");
                break;
            case STT_LOPROC:
                sb.append("LOPROC ");
                break;
            case STT_HIPROC:
                sb.append("HIPROC ");
                break;
        }

        switch (getBind()){
            case STB_LOCAL:
                sb.append("LOCAL  ");
                break;
            case STB_GLOBAL:
                sb.append("GLOBAL ");
                break;
            case STB_WEAK:
                sb.append("WEAK   ");
                break;
            case STB_LOPROC:
                sb.append("LOPROC ");
                break;
            case STB_HIPROC:
                sb.append("HIPROC ");
                break;
        }

        switch (getVisibility()){
            case STV_DEFAULT:
                sb.append("DEFAULT   ");
                break;
            case STV_INTERNAL:
                sb.append("INTERNAL  ");
                break;
            case STV_HIDDEN:
                sb.append("HIDDEN    ");
                break;
            case STV_PROTECTED:
                sb.append("PROTECTED ");
                break;
        }

        if(st_shndx == 0){
            sb.append("UND" );
        }else{
            sb.append(String.format("3d", st_shndx) + " ");
        }

        sb.append(name);

        return sb.toString();
    }

    //public void ReadElf32SymbolFromFile(ElfFile elfFile){

    //}

    enum SymbolType{
        Elf64_Sym(24),
        Elf32_Sym(16);

        final int typeSize;

        SymbolType(int typeSize){
            this.typeSize = typeSize;
        }
    }

}
