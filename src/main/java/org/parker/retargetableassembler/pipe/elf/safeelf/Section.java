package org.parker.retargetableassembler.pipe.elf.safeelf;

import org.parker.retargetableassembler.pipe.elf.Elf;
import org.parker.retargetableassembler.pipe.elf.ElfException;

import java.io.IOException;
import java.util.ArrayList;

public class Section {

    String name;
    SH_TYPE type;
    SH_FLAG flags;
    long address;
    long addressAlign;
    long size;
    long entrySize;
    long info;
    Section link;

    private byte[] data;
    SectionDataInterpreter dataInterpreter;
    SafeElf parent;

    public byte[] getData() throws ElfException {
        if(data == null){
            if(entrySize == 0){
                data = new byte[0];
            }else{
                if(parent.parentElf != null){
                    Elf.SectionHeader sectionHeader = parent.parentElf.sectionHeaders[0];
                    try {
                        parent.parentElf.raf.seek(sectionHeader.sh_offset);
                        data = new byte[(int) sectionHeader.sh_entsize];
                        parent.parentElf.raf.read(data);
                    } catch (IOException e) {
                        throw new ElfException(e);
                    }
                }else{
                    data = new byte[0];
                }
            }
        }
        return data;
    }

    public void flushData(){
        if(dataInterpreter != null){
            data = dataInterpreter.flushData();
        }else{
            data = new byte[0];
        }

    }

    public void setData(byte[] data){
        this.data = data;
    }

    public void populate(SafeElf elf, Elf.SectionHeader self){
        this.parent = elf;
        link = elf.sections[self.sh_link];
    }

    public Elf.SectionHeader toSectionHeader(){
        Elf.SectionHeader sectionHeader = new Elf.SectionHeader();
        return sectionHeader;
    }


    public enum SH_TYPE{

        SHT_NULL(0),
        SHT_PROGBITS(0x1),
        SHT_SYMTAB(0x2),
        SHT_STRTAB(0x3),
        SHT_RELA(0x4),
        SHT_HASH(0x5),
        SHT_DYNAMIC(0x6),
        SHT_NOTE(0x7),
        SHT_NOBITS(0x8),
        SHT_REL(0x9),
        SHT_SHLIB(0xA),
        SHT_DYNSYM(0xB),
        SHT_INIT_ARRAY(0xE),
        SHT_FINI_ARRAY(0xF),
        SHT_PREINIT_ARRAY(0x10),
        SHT_GROUP(0x11),
        SHT_SYMTAB_SHNDX(0x12),
        SHT_NUM(0x13),
        SHT_LOOS(0x60000000);

        final int type;
        SH_TYPE(int type){
            this.type = type;
        }
        public static SH_TYPE toEnum(int type){
            SH_TYPE[] vals = SH_TYPE.values();
            for(int i = 0; i < vals.length; i ++){
                if(vals[i].type == type){
                    return vals[i];
                }
            }
            return null;
        }
    }

    public enum SH_FLAG{

        SHF_ZERO(0x0, false),
        SHF_WRITE(1, false),
        SHF_ALLOC(2, false),
        SHF_EXECINSTR(3, false),
        SHF_UNUSED_1(4, true),
        SHF_MERGE(5, false),
        SHF_STRIGNS(6, false),
        SHF_INFO_LINK(7, false),
        SHF_LINK_ORDER(8, false),
        SHF_OS_NONCONFORMING(9, false),
        SHF_GROUP(10, false),
        SHF_TLS(11, false),
        SHF_UNUSED_2(12, true),
        SHF_UNUSED_3(13, true),
        SHF_UNUSED_4(14, true),
        SHF_UNUSED_5(15, true),
        SHF_UNUSED_6(16, true),
        SHF_UNUSED_7(17, true),
        SHF_UNUSED_8(18, true),
        SHF_UNUSED_9(19, true),
        SHF_UNUSED_10(20, true),
        SHF_MASKOS_1(21, true),
        SHF_MASKOS_2(22, true),
        SHF_MASKOS_3(23, true),
        SHF_MASKOS_4(24, true),
        SHF_MASKOS_5(25, true),
        SHF_MASKOS_6(26, true),
        SHF_MASKOS_7(27, true),
        SHF_MASKOS_8(28, true),
        SHF_MASKPROC_1(29, true),
        SHF_MASKPROC_2(30, true),
        SHF_MASKPROC_3(31, true),
        SHF_MASKPROC_4(32, true),
        SHF_ORDERED(30, false),
        SHF_EXCLUDE(31, false);

        final static SH_FLAG[] flags = new SH_FLAG[64];
        final static SH_FLAG[] masks = new SH_FLAG[64];

        static{
            for(SH_FLAG flag:SH_FLAG.values()) {
                if(flag.mask){
                    masks[flag.pos] = flag;
                }else{
                    flags[flag.pos] = flag;
                }
            }
        }

        public static SH_FLAG[] getFlags(long flagNum){

            ArrayList<SH_FLAG> flagList = new ArrayList<>();

            if(flagNum == 0){
                flagList.add(SHF_ZERO);
            }else {
                for (int i = 0; i < 64; i++) {
                    if (((flagNum >> i) & 1) > 0) {
                        if(flags[i] != null){
                            flagList.add(flags[i]);
                        }
                        if(masks[i] != null){
                            flagList.add(masks[i]);
                        }
                    }
                }
            }
            return flagList.toArray(new SH_FLAG[0]);
        }

        public static long toFlags(SH_FLAG[] flags){
            long val = 0;
            for(SH_FLAG flag:flags){
                val |= 1 << (flag.pos - 1);
            }
            return val;
        }

        final int pos;
        final boolean mask;
        SH_FLAG(int pos, boolean mask){
            this.pos = pos;
            this.mask = mask;
        }
    }
}
