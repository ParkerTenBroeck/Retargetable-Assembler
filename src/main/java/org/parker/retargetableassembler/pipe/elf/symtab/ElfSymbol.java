package org.parker.retargetableassembler.pipe.elf.symtab;

import org.parker.retargetableassembler.pipe.elf.Elf;

import java.io.*;

@SuppressWarnings("unused")
public class ElfSymbol {
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

    public enum STB{
        LOCAL(0),
        GLOBAL(1),
        WEAK(2),
        LOPROC(13),
        HIPROC(15);

        public final int ID;
        STB(int id){
            this.ID = id;
        }
        public static STB fromID(int id){
            for(STB stb: STB.values()){
                if(stb.ID == id) return stb;
            }
            return LOCAL;
        }
    }

    public enum STT{
        NOTYPE(0),
        OBJECT(1),
        FUNC(2),
        SECTION(3),
        FILE(4),
        COMMON(5),
        TLS(6),
        LOOS(10),
        LOPROC(13),
        HIPROC(15);

        public final int ID;
        STT(int id){
            this.ID = id;
        }
        public static STT fromID(int id){
            for(STT stt: STT.values()){
                if(stt.ID == id) return stt;
            }
            return NOTYPE;
        }
    }

    public enum STV{
        DEFAULT(0),
        INTERNAL(1),
        HIDDEN(2),
        PROTECTED(3);

        public final int ID;
        STV(int id){
            this.ID = id;
        }
        public static STV fromID(int id){
            for(STV stv: STV.values()){
                if(stv.ID == id) return stv;
            }
            return DEFAULT;
        }
    }

    int st_name;
    transient String name;
    transient SymbolType type;
    byte st_info;
    byte st_other;
    short st_shndx;
    long st_value;
    long st_size;

    public int getNDX() {
        return st_shndx;
    }
    public long getValue(){return st_value;}
    public long getSize(){return st_size;}
    public STB getBind(){
        return STB.fromID((st_info >> 4) & 0xF);
    }
    public int getBindI(){
        return (st_info >> 4) & 0xF;
    }
    public void setBind(int type){
        st_info = (byte)((st_info & (~0xF0)) | ((type & 0xF) << 4));
    }
    public STT getType(){
        return STT.fromID(st_info & 0xF);
    }
    public int getTypeI(){
        return st_info & 0xF;
    }
    public void setType(int type){
        st_info = (byte)((st_info & (~0xF)) | (type & 0xF));
    }
    public STV getVisibility(){
        return STV.fromID(st_other&0x3);
    }
    public int getVisibilityI(){
        return st_other&0x3;
    }
    public void setVisibility(int vis){
        st_other = (byte)((st_other & (~0x3)) | (vis&0x3));
    }

    public final int getSymbolSize(){
        return type.typeSize;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("0x" + String.format("%16X", st_value).replace(' ', '0') + " ");
        sb.append(String.format("%5d", st_size) + " ");
        switch (getTypeI()){
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

        switch (getBindI()){
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

        switch (getVisibilityI()){
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
            sb.append(String.format("3%d", st_shndx) + " ");
        }

        sb.append(name);

        return sb.toString();
    }

    private ElfSymbol(Elf elf, Elf.SectionHeader section, int index) throws IOException {

        DataInputStream in = new DataInputStream(
                new ByteArrayInputStream(
                        section.contents,
                        (int)section.sh_entsize * index, (int) (section.sh_size)));
        if(elf.is64Bit()){
            this.type = SymbolType.Elf64_Sym;
            this.st_name = elf.readInt(in);
            this.st_info = elf.readByte(in);
            this.st_other = elf.readByte(in);
            this.st_shndx = elf.readShort(in);
            this.st_value = elf.readLong(in);
            this.st_size = elf.readLong(in);
        }else{
            this.type = SymbolType.Elf32_Sym;
            this.st_name = elf.readInt(in);
            this.st_value = elf.readInt(in);
            this.st_size = elf.readInt(in);
            this.st_info = elf.readByte(in);
            this.st_other = elf.readByte(in);
            this.st_shndx = elf.readShort(in);
        }
        this.name = elf.get_string_from_section(section.sh_link, this.st_name);
    }

    public static ElfSymbol fromElf(Elf elf, Elf.SectionHeader section, int index) throws IOException {
        return new ElfSymbol(elf, section, index);
    }

    public String getName() {
        return this.name;
    }

    enum SymbolType{
        Elf64_Sym(24),
        Elf32_Sym(16);

        final int typeSize;

        SymbolType(int typeSize){
            this.typeSize = typeSize;
        }
    }

}
