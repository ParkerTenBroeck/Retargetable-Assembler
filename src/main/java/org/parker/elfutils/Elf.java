package org.parker.elfutils;

import org.parker.elfutils.readelf.ReadElf;

import java.io.*;

/*
           ElfN_Addr       Unsigned program address, uintN_t
           ElfN_Off        Unsigned file offset, uintN_t
           ElfN_Section    Unsigned section index, uint16_t
           ElfN_Versym     Unsigned version symbol information, uint16_t
           Elf_Byte        unsigned char
           ElfN_Half       uint16_t
           ElfN_Sword      int32_t
           ElfN_Word       uint32_t
           ElfN_Sxword     int64_t
           ElfN_Xword      uint64_t
 */

@SuppressWarnings({"unused"})
public class Elf {

    public FileHeader fileHeader;
    public ProgramHeader programHeaders[];
    public SectionHeader sectionHeaders[];

    transient public File file;
    transient public RandomAccessFile raf;

    transient public byte[][] section_data;


    public Elf(File file) throws IOException {
        this(file, false);
    }

    public Elf(File file, boolean readSectionData) throws IOException {
        this.file = file;
        this.raf = new RandomAccessFile(file, "r");
        this.read(readSectionData);

    }

    public Elf(){

    }

    public String get_e_shstrndx_string(int index){
        int size = 0;
        while(section_data[fileHeader.e_shstrndx][index + size] != 0){
            size ++;
        }
        return new String(section_data[fileHeader.e_shstrndx], index, size);
    }

    public String get_string_from_section(int section, int index) throws IOException {
        this.readSectionData(section);
        return get_string_from_section(sectionHeaders[section], index);
    }

    public static String get_string_from_section(Elf.SectionHeader section, int index){
        int size = 0;
        while(section.contents[index + size] != 0){
            size ++;
        }
        return new String(section.contents, index, size);
    }



    public static void main(String... args) throws IOException {
        File f = new File("U:\\home\\parker\\testCode\\gdbTest\\a.out");
        //File f = new File("U:\\home\\parker\\assembly\\ldTest\\hw.o");
        //File f = new File("U:\\home\\parker\\assembly\\ldTest\\hw.out");
        Elf elf = new Elf(f, true);
        System.out.println(ReadElf.readElf(elf));
    }

    protected void read(boolean readSectionData) throws IOException {
        fileHeader = new FileHeader().read(this);

        programHeaders = new ProgramHeader[fileHeader.e_phnum];
        for(int i = 0; i < programHeaders.length; i ++){
            programHeaders[i] = new ProgramHeader().read(this, i);
        }

        sectionHeaders = new SectionHeader[fileHeader.e_shnum];
        for(int i = 0; i < sectionHeaders.length; i ++){
            sectionHeaders[i] = new SectionHeader().read(this, i);
        }

        section_data = new byte[sectionHeaders.length][];

        if(readSectionData){
            for(int i = 0; i < sectionHeaders.length; i ++){
                readSectionData(i);
            }
        }else{
            readSectionData(fileHeader.e_shstrndx);
        }
    }

    private byte[] readSectionData(SectionHeader section) throws IOException {
        for(int i = 0; i < sectionHeaders.length; i ++) if(section.equals(sectionHeaders[i]))return this.readSectionData(i);
        return null;
    }

    public byte[] readSectionData(int section) throws IOException {

        if(section_data[section] != null){
            return section_data[section];
        }
        SectionHeader sectionHeader = sectionHeaders[section];
        byte[] data;
        if(sectionHeader.sh_type != 0 && sectionHeader.sh_type != 0x8){
            data = new byte[(int) sectionHeader.sh_size];
            raf.seek(sectionHeader.sh_offset);
            raf.read(data);
        }else{
            data = new byte[0];
        }
        section_data[section] = data;
        sectionHeaders[section].contents = data;
        return data;
    }

    public void seekRAF(long address) throws IOException {
        this.raf.seek(address);
    }

    public byte readByte() throws IOException {
        return readByte(raf);
    }

    public short readShort() throws IOException {
        return readShort(raf);
    }

    public int readInt() throws IOException{
        return readInt(raf);
    }

    public long readLong() throws IOException{
        return readLong(raf);
    }

    public void writeByte(byte b) throws IOException {
        writeByte(b, raf);
    }

    public void writeShort(short s) throws IOException {
        writeShort(s, raf);
    }

    public void writeInt(int i) throws IOException{
        writeInt(i, raf);
    }

    public void writeLong(long l) throws IOException{
        writeLong(l, raf);
    }

    public byte readByte(DataInput in) throws IOException {
        return in.readByte();
    }

    public short readShort(DataInput in) throws IOException {
        if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 1){//little endien
            return Short.reverseBytes(in.readShort());
        }else if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 2){//big endien
            return in.readShort();
        }else{
            throw new IOException();
        }
    }

    public int readInt(DataInput in) throws IOException{
        if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 1){//little endien
            return Integer.reverseBytes(in.readInt());
        }else if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 2){//big endien
            return in.readInt();
        }else{
            throw new IOException();
        }
    }

    public long readLong(DataInput in) throws IOException{
        if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 1){//little endien
            return Long.reverseBytes(in.readLong());
        }else if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 2){//big endien
            return in.readLong();
        }else{
            throw new IOException();
        }
    }

    public void writeByte(byte b, DataOutput out) throws IOException {
        out.writeByte(b);
    }

    public void writeShort(short s, DataOutput out) throws IOException {
        if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 1){//little endien
            out.writeShort(Short.reverseBytes(s));
        }else if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 2){//big endien
            out.writeShort(s);
        }else{
            throw new IOException();
        }
    }

    public void writeInt(int i, DataOutput out) throws IOException{
        if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 1){//little endien
            out.writeInt(Integer.reverseBytes(i));
        }else if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 2){//big endien
            out.writeInt(i);
        }else{
            throw new IOException();
        }
    }

    public void writeLong(long l, DataOutput out) throws IOException{
        if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 1){//little endien
            out.writeLong(Long.reverseBytes(l));
        }else if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 2){//big endien
            out.writeLong(l);
        }else{
            throw new IOException();
        }
    }

    public DataInput generateDataInputFromSectionArray(SectionHeader section, int index){
        return new DataInputStream(
                new ByteArrayInputStream(
                        section.contents,
                        (int)section.sh_entsize * index, (int) (section.sh_entsize * section.sh_size)));
    }

    public DataInput generateDataInputFromSection(SectionHeader section, int startingIndex){
        return new DataInputStream(
                new ByteArrayInputStream(
                        section.contents,
                        startingIndex, (int) (section.sh_entsize * section.sh_size)));
    }

    public String getSectionName(SectionHeader section) {
        return get_e_shstrndx_string(section.sh_name);
    }

    public boolean is64Bit() {
        return this.fileHeader.e_ident[FileHeader.EI_CLASS] == 2;
    }

    public boolean is32Bit() {
        return this.fileHeader.e_ident[FileHeader.EI_CLASS] == 1;
    }

    public SectionHeader getSection(int section) throws IOException {
        this.ensureData(section);
        return this.sectionHeaders[section];
    }

    private void ensureData(int section) throws IOException {
        this.readSectionData(section);
    }

    public static class FileHeader{
        public transient final static int EI_MAG0 = 0;
        public transient final static int EI_MAG1 = 1;
        public transient final static int EI_MAG2 = 2;
        public transient final static int EI_MAG3 = 3;
        public transient final static int EI_CLASS = 4;
        public transient final static int EI_DATA = 5;
        public transient final static int EI_VERSION = 6;
        public transient final static int EI_OSABI = 7;
        public transient final static int EI_ABIVERSION = 8;
        public transient final static int EI_PAD = 9;//not used

        public static final int  ELFOSABI_NONE = 0; /* UNIX System V ABI */
        public static final int  ELFOSABI_HPUX = 1; /* HP-UX operating system */
        public static final int  ELFOSABI_NETBSD = 2; /* NetBSD */
        public static final int  ELFOSABI_GNU = 3; /* GNU */
        public static final int  ELFOSABI_LINUX = 3; /* Alias for ELFOSABI_GNU */
        public static final int  ELFOSABI_SOLARIS = 6; /* Solaris */
        public static final int  ELFOSABI_AIX = 7; /* AIX */
        public static final int  ELFOSABI_IRIX = 8; /* IRIX */
        public static final int  ELFOSABI_FREEBSD = 9; /* FreeBSD */
        public static final int  ELFOSABI_TRU64 = 10; /* TRU64 UNIX */
        public static final int  ELFOSABI_MODESTO = 11; /* Novell Modesto */
        public static final int  ELFOSABI_OPENBSD = 12; /* OpenBSD */
        public static final int  ELFOSABI_OPENVMS = 13; /* OpenVMS */
        public static final int  ELFOSABI_NSK = 14; /* Hewlett-Packard Non-Stop Kernel */
        public static final int  ELFOSABI_AROS = 15; /* AROS */
        public static final int  ELFOSABI_FENIXOS = 16; /* FenixOS */
        public static final int  ELFOSABI_CLOUDABI = 17; /* Nuxi CloudABI */
        public static final int  ELFOSABI_OPENVOS = 18; /* Stratus Technologies OpenVOS */

        public static final int  ELFOSABI_C6000_ELFABI = 64; /* Bare-metal TMS320C6000 */
        public static final int  ELFOSABI_C6000_LINUX = 65; /* Linux TMS320C6000 */
        public static final int  ELFOSABI_ARM_FDPIC = 65; /* ARM FDPIC */
        public static final int  ELFOSABI_ARM = 97; /* ARM */
        public static final int  ELFOSABI_STANDALONE = 255; /* Standalone (embedded) application */

        public transient final static int SYSTEM_V = 0x00;
        public transient final static int HP_US = 0x01;
        public transient final static int NET_BSD = 0x02;
        public transient final static int LINUX = 0x03;
        public transient final static int GNU_HURD = 0x04;
        public transient final static int SOLARIS = 0x06;
        public transient final static int AIX = 0x07;
        public transient final static int IRIX = 0x08;
        public transient final static int FREE_BSD = 0x09;
        public transient final static int TRU_64 = 0x0A;
        public transient final static int NOVELL_MONDESTO = 0x0B;
        public transient final static int OPEN_BSD = 0x0C;
        public transient final static int OPEN_VMS = 0x0D;
        public transient final static int NONSTOP_KERNEL = 0x0E;
        public transient final static int AROS = 0x0F;
        public transient final static int FENIX_OS = 0x10;
        public transient final static int CLOUD_ABI = 0x11;
        public transient final static int STRATUS_TECHNOLOGIES_OPEN_VOS = 0x12;

        public transient final static int ET_NONE = 0x00;
        public transient final static int ET_REL = 0x01;
        public transient final static int ET_EXEC = 0x02;
        public transient final static int ET_DYN = 0x03;
        public transient final static int ET_CORE = 0x04;
        public transient final static int ET_LOOS = 0xFE00;
        public transient final static int ET_LOPROC = 0xFF00;
        public transient final static int ET_HIPROC = 0xFFFF;

        public static final int EM_NONE =   0; /* No machine */
        public static final int EM_M32 =   1; /* AT&T WE 32100 */
        public static final int EM_SPARC =   2; /* SUN SPARC */
        public static final int EM_386 =   3; /* Intel 80386 */
        public static final int EM_68K =   4; /* Motorola m68k family */
        public static final int EM_88K =   5; /* Motorola m88k family */
        public static final int EM_IAMCU =   6; /* Intel MCU */
        public static final int EM_860 =   7; /* Intel 80860 */
        public static final int EM_MIPS =   8; /* MIPS R3000 (officially, big-endian only) */
        public static final int EM_S370 =   9; /* IBM System/370 */
        public static final int EM_MIPS_RS3_LE =  10; /* MIPS R3000 little-endian (Oct 4 1999 Draft).  Deprecated.  */
        public static final int EM_OLD_SPARCV9 =  11; /* Old version of Sparc v9, from before the ABI.  Deprecated.  */
        public static final int EM_res011 =  11; /* Reserved */
        public static final int EM_res012 =  12; /* Reserved */
        public static final int EM_res013 =  13; /* Reserved */
        public static final int EM_res014 =  14; /* Reserved */
        public static final int EM_PARISC =  15; /* HPPA */
        public static final int EM_res016 =  16; /* Reserved */
        public static final int EM_PPC_OLD =  17; /* Old version of PowerPC.  Deprecated.  */
        public static final int EM_VPP550 =  17; /* Fujitsu VPP500 */
        public static final int EM_SPARC32PLUS =  18; /* Sun's "v8plus" */
        public static final int EM_960 =  19; /* Intel 80960 */
        public static final int EM_PPC =  20; /* PowerPC */
        public static final int EM_PPC64 =  21; /* 64-bit PowerPC */
        public static final int EM_S390 =  22; /* IBM S/390 */
        public static final int EM_SPU =  23; /* Sony/Toshiba/IBM SPU */
        public static final int EM_res024 =  24; /* Reserved */
        public static final int EM_res025 =  25; /* Reserved */
        public static final int EM_res026 =  26; /* Reserved */
        public static final int EM_res027 =  27; /* Reserved */
        public static final int EM_res028 =  28; /* Reserved */
        public static final int EM_res029 =  29; /* Reserved */
        public static final int EM_res030 =  30; /* Reserved */
        public static final int EM_res031 =  31; /* Reserved */
        public static final int EM_res032 =  32; /* Reserved */
        public static final int EM_res033 =  33; /* Reserved */
        public static final int EM_res034 =  34; /* Reserved */
        public static final int EM_res035 =  35; /* Reserved */
        public static final int EM_V800 =  36; /* NEC V800 series */
        public static final int EM_FR20 =  37; /* Fujitsu FR20 */
        public static final int EM_RH32 =  38; /* TRW RH32 */
        public static final int EM_MCORE =  39; /* Motorola M*Core */; /* May also be taken by Fujitsu MMA */
        public static final int EM_RCE =  39; /* Old name for MCore */
        public static final int EM_ARM =  40; /* ARM */
        public static final int EM_OLD_ALPHA =  41; /* Digital Alpha */
        public static final int EM_SH =  42; /* Renesas (formerly Hitachi) / SuperH SH */
        public static final int EM_SPARCV9 =  43; /* SPARC v9 64-bit */
        public static final int EM_TRICORE =  44; /* Siemens Tricore embedded processor */
        public static final int EM_ARC =  45; /* ARC Cores */
        public static final int EM_H8_300 =  46; /* Renesas (formerly Hitachi) H8/300 */
        public static final int EM_H8_300H =  47; /* Renesas (formerly Hitachi) H8/300H */
        public static final int EM_H8S =  48; /* Renesas (formerly Hitachi) H8S */
        public static final int EM_H8_500 =  49; /* Renesas (formerly Hitachi) H8/500 */
        public static final int EM_IA_64 =  50; /* Intel IA-64 Processor */
        public static final int EM_MIPS_X =  51; /* Stanford MIPS-X */
        public static final int EM_COLDFIRE =  52; /* Motorola Coldfire */
        public static final int EM_68HC12 =  53; /* Motorola M68HC12 */
        public static final int EM_MMA =  54; /* Fujitsu Multimedia Accelerator */
        public static final int EM_PCP =  55; /* Siemens PCP */
        public static final int EM_NCPU =  56; /* Sony nCPU embedded RISC processor */
        public static final int EM_NDR1 =  57; /* Denso NDR1 microprocessor */
        public static final int EM_STARCORE =  58; /* Motorola Star*Core processor */
        public static final int EM_ME16 =  59; /* Toyota ME16 processor */
        public static final int EM_ST100 =  60; /* STMicroelectronics ST100 processor */
        public static final int EM_TINYJ =  61; /* Advanced Logic Corp. TinyJ embedded processor */
        public static final int EM_X86_64 =  62; /* Advanced Micro Devices X86-64 processor */
        public static final int EM_PDSP =  63; /* Sony DSP Processor */
        public static final int EM_PDP10 =  64; /* Digital Equipment Corp. PDP-10 */
        public static final int EM_PDP11 =  65; /* Digital Equipment Corp. PDP-11 */
        public static final int EM_FX66 =  66; /* Siemens FX66 microcontroller */
        public static final int EM_ST9PLUS =  67; /* STMicroelectronics ST9+ 8/16 bit microcontroller */
        public static final int EM_ST7 =  68; /* STMicroelectronics ST7 8-bit microcontroller */
        public static final int EM_68HC16 =  69; /* Motorola MC68HC16 Microcontroller */
        public static final int EM_68HC11 =  70; /* Motorola MC68HC11 Microcontroller */
        public static final int EM_68HC08 =  71; /* Motorola MC68HC08 Microcontroller */
        public static final int EM_68HC05 =  72; /* Motorola MC68HC05 Microcontroller */
        public static final int EM_SVX =  73; /* Silicon Graphics SVx */
        public static final int EM_ST19 =  74; /* STMicroelectronics ST19 8-bit cpu */
        public static final int EM_VAX =  75; /* Digital VAX */
        public static final int EM_CRIS =  76; /* Axis Communications 32-bit embedded processor */
        public static final int EM_JAVELIN =  77; /* Infineon Technologies 32-bit embedded cpu */
        public static final int EM_FIREPATH =  78; /* Element 14 64-bit DSP processor */
        public static final int EM_ZSP =  79; /* LSI Logic's 16-bit DSP processor */
        public static final int EM_MMIX =  80; /* Donald Knuth's educational 64-bit processor */
        public static final int EM_HUANY =  81; /* Harvard's machine-independent format */
        public static final int EM_PRISM =  82; /* SiTera Prism */
        public static final int EM_AVR =  83; /* Atmel AVR 8-bit microcontroller */
        public static final int EM_FR30 =  84; /* Fujitsu FR30 */
        public static final int EM_D10V =  85; /* Mitsubishi D10V */
        public static final int EM_D30V =  86; /* Mitsubishi D30V */
        public static final int EM_V850 =  87; /* Renesas V850 (formerly NEC V850) */
        public static final int EM_M32R =  88; /* Renesas M32R (formerly Mitsubishi M32R) */
        public static final int EM_MN10300 =  89; /* Matsushita MN10300 */
        public static final int EM_MN10200 =  90; /* Matsushita MN10200 */
        public static final int EM_PJ =  91; /* picoJava */
        public static final int EM_OR1K =  92; /* OpenRISC 1000 32-bit embedded processor */
        public static final int EM_ARC_COMPACT =  93; /* ARC International ARCompact processor */
        public static final int EM_XTENSA =  94; /* Tensilica Xtensa Architecture */
        public static final int EM_SCORE_OLD =  95; /* Old Sunplus S+core7 backend magic number. Written in the absence of an ABI.  */
        public static final int EM_VIDEOCORE =  95; /* Alphamosaic VideoCore processor */
        public static final int EM_TMM_GPP =  96; /* Thompson Multimedia General Purpose Processor */
        public static final int EM_NS32K =  97; /* National Semiconductor 32000 series */
        public static final int EM_TPC =  98; /* Tenor Network TPC processor */
        public static final int EM_PJ_OLD =  99; /* Old value for picoJava.  Deprecated.  */
        public static final int EM_SNP1K =  99; /* Trebia SNP 1000 processor */
        public static final int EM_ST200 = 100; /* STMicroelectronics ST200 microcontroller */
        public static final int EM_IP2K = 101; /* Ubicom IP2022 micro controller */
        public static final int EM_MAX = 102; /* MAX Processor */
        public static final int EM_CR = 103; /* National Semiconductor CompactRISC */
        public static final int EM_F2MC16 = 104; /* Fujitsu F2MC16 */
        public static final int EM_MSP430 = 105; /* TI msp430 micro controller */
        public static final int EM_BLACKFIN = 106; /* ADI Blackfin */
        public static final int EM_SE_C33 = 107; /* S1C33 Family of Seiko Epson processors */
        public static final int EM_SEP = 108; /* Sharp embedded microprocessor */
        public static final int EM_ARCA = 109; /* Arca RISC Microprocessor */
        public static final int EM_UNICORE = 110; /* Microprocessor series from PKU-Unity Ltd. and MPRC of Peking University */
        public static final int EM_EXCESS = 111; /* eXcess: 16/32/64-bit configurable embedded CPU */
        public static final int EM_DXP = 112; /* Icera Semiconductor Inc. Deep Execution Processor */
        public static final int EM_ALTERA_NIOS2 = 113; /* Altera Nios II soft-core processor */
        public static final int EM_CRX = 114; /* National Semiconductor CRX */
        public static final int EM_CR16_OLD = 115; /* Old, value for National Semiconductor CompactRISC.  Deprecated.  */
        public static final int EM_XGATE = 115; /* Motorola XGATE embedded processor */
        public static final int EM_C166 = 116; /* Infineon C16x/XC16x processor */
        public static final int EM_M16C = 117; /* Renesas M16C series microprocessors */
        public static final int EM_DSPIC30F = 118; /* Microchip Technology dsPIC30F Digital Signal Controller */
        public static final int EM_CE = 119; /* Freescale Communication Engine RISC core */
        public static final int EM_M32C = 120; /* Renesas M32C series microprocessors */
        public static final int EM_res121 = 121; /* Reserved */
        public static final int EM_res122 = 122; /* Reserved */
        public static final int EM_res123 = 123; /* Reserved */
        public static final int EM_res124 = 124; /* Reserved */
        public static final int EM_res125 = 125; /* Reserved */
        public static final int EM_res126 = 126; /* Reserved */
        public static final int EM_res127 = 127; /* Reserved */
        public static final int EM_res128 = 128; /* Reserved */
        public static final int EM_res129 = 129; /* Reserved */
        public static final int EM_res130 = 130; /* Reserved */
        public static final int EM_TSK3000 = 131; /* Altium TSK3000 core */
        public static final int EM_RS08 = 132; /* Freescale RS08 embedded processor */
        public static final int EM_res133 = 133; /* Reserved */
        public static final int EM_ECOG2 = 134; /* Cyan Technology eCOG2 microprocessor */
        public static final int EM_SCORE = 135; /* Sunplus Score */
        public static final int EM_SCORE7 = 135; /* Sunplus S+core7 RISC processor */
        public static final int EM_DSP24 = 136; /* New Japan Radio (NJR) 24-bit DSP Processor */
        public static final int EM_VIDEOCORE3 = 137; /* Broadcom VideoCore III processor */
        public static final int EM_LATTICEMICO32 = 138; /* RISC processor for Lattice FPGA architecture */
        public static final int EM_SE_C17 = 139; /* Seiko Epson C17 family */
        public static final int EM_TI_C6000 = 140; /* Texas Instruments TMS320C6000 DSP family */
        public static final int EM_TI_C2000 = 141; /* Texas Instruments TMS320C2000 DSP family */
        public static final int EM_TI_C5500 = 142; /* Texas Instruments TMS320C55x DSP family */
        public static final int EM_res143 = 143; /* Reserved */
        public static final int EM_TI_PRU = 144; /* Texas Instruments Programmable Realtime Unit */
        public static final int EM_res145 = 145; /* Reserved */
        public static final int EM_res146 = 146; /* Reserved */
        public static final int EM_res147 = 147; /* Reserved */
        public static final int EM_res148 = 148; /* Reserved */
        public static final int EM_res149 = 149; /* Reserved */
        public static final int EM_res150 = 150; /* Reserved */
        public static final int EM_res151 = 151; /* Reserved */
        public static final int EM_res152 = 152; /* Reserved */
        public static final int EM_res153 = 153; /* Reserved */
        public static final int EM_res154 = 154; /* Reserved */
        public static final int EM_res155 = 155; /* Reserved */
        public static final int EM_res156 = 156; /* Reserved */
        public static final int EM_res157 = 157; /* Reserved */
        public static final int EM_res158 = 158; /* Reserved */
        public static final int EM_res159 = 159; /* Reserved */
        public static final int EM_MMDSP_PLUS = 160; /* STMicroelectronics 64bit VLIW Data Signal Processor */
        public static final int EM_CYPRESS_M8C = 161; /* Cypress M8C microprocessor */
        public static final int EM_R32C = 162; /* Renesas R32C series microprocessors */
        public static final int EM_TRIMEDIA = 163; /* NXP Semiconductors TriMedia architecture family */
        public static final int EM_QDSP6 = 164; /* QUALCOMM DSP6 Processor */
        public static final int EM_8051 = 165; /* Intel 8051 and variants */
        public static final int EM_STXP7X = 166; /* STMicroelectronics STxP7x family */
        public static final int EM_NDS32 = 167; /* Andes Technology compact code size embedded RISC processor family */
        public static final int EM_ECOG1 = 168; /* Cyan Technology eCOG1X family */
        public static final int EM_ECOG1X = 168; /* Cyan Technology eCOG1X family */
        public static final int EM_MAXQ30 = 169; /* Dallas Semiconductor MAXQ30 Core Micro-controllers */
        public static final int EM_XIMO16 = 170; /* New Japan Radio (NJR) 16-bit DSP Processor */
        public static final int EM_MANIK = 171; /* M2000 Reconfigurable RISC Microprocessor */
        public static final int EM_CRAYNV2 = 172; /* Cray Inc. NV2 vector architecture */
        public static final int EM_RX = 173; /* Renesas RX family */
        public static final int EM_METAG = 174; /* Imagination Technologies Meta processor architecture */
        public static final int EM_MCST_ELBRUS = 175; /* MCST Elbrus general purpose hardware architecture */
        public static final int EM_ECOG16 = 176; /* Cyan Technology eCOG16 family */
        public static final int EM_CR16 = 177; /* National Semiconductor CompactRISC 16-bit processor */
        public static final int EM_ETPU = 178; /* Freescale Extended Time Processing Unit */
        public static final int EM_SLE9X = 179; /* Infineon Technologies SLE9X core */
        public static final int EM_L1OM = 180; /* Intel L1OM */
        public static final int EM_K1OM = 181; /* Intel K1OM */
        public static final int EM_INTEL182 = 182; /* Reserved by Intel */
        public static final int EM_AARCH64 = 183; /* ARM 64-bit architecture */
        public static final int EM_ARM184 = 184; /* Reserved by ARM */
        public static final int EM_AVR32 = 185; /* Atmel Corporation 32-bit microprocessor family */
        public static final int EM_STM8 = 186; /* STMicroeletronics STM8 8-bit microcontroller */
        public static final int EM_TILE64 = 187; /* Tilera TILE64 multicore architecture family */
        public static final int EM_TILEPRO = 188; /* Tilera TILEPro multicore architecture family */
        public static final int EM_MICROBLAZE = 189; /* Xilinx MicroBlaze 32-bit RISC soft processor core */
        public static final int EM_CUDA = 190; /* NVIDIA CUDA architecture */
        public static final int EM_TILEGX = 191; /* Tilera TILE-Gx multicore architecture family */
        public static final int EM_CLOUDSHIELD  = 192 ; /* CloudShield architecture family */
        public static final int EM_COREA_1ST  = 193 ; /* KIPO-KAIST Core-A 1st generation processor family */
        public static final int EM_COREA_2ND  = 194 ; /* KIPO-KAIST Core-A 2nd generation processor family */
        public static final int EM_ARC_COMPACT2 = 195; /* Synopsys ARCompact V2 */
        public static final int EM_OPEN8  = 196 ; /* Open8 8-bit RISC soft processor core */
        public static final int EM_RL78 = 197; /* Renesas RL78 family.  */
        public static final int EM_VIDEOCORE5  = 198 ; /* Broadcom VideoCore V processor */
        public static final int EM_78K0R = 199; /* Renesas 78K0R.  */
        public static final int EM_56800EX  = 200 ; /* Freescale 56800EX Digital Signal Controller (DSC) */
        public static final int EM_BA1  = 201 ; /* Beyond BA1 CPU architecture */
        public static final int EM_BA2  = 202 ; /* Beyond BA2 CPU architecture */
        public static final int EM_XCORE  = 203 ; /* XMOS xCORE processor family */
        public static final int EM_MCHP_PIC  = 204 ; /* Microchip 8-bit PIC(r) family */
        public static final int EM_INTELGT = 205; /* Intel Graphics Technology */
        public static final int EM_INTEL206 = 206; /* Reserved by Intel */
        public static final int EM_INTEL207 = 207; /* Reserved by Intel */
        public static final int EM_INTEL208 = 208; /* Reserved by Intel */
        public static final int EM_INTEL209 = 209; /* Reserved by Intel */
        public static final int EM_KM32  = 210 ; /* KM211 KM32 32-bit processor */
        public static final int EM_KMX32  = 211 ; /* KM211 KMX32 32-bit processor */
        public static final int EM_KMX16  = 212 ; /* KM211 KMX16 16-bit processor */
        public static final int EM_KMX8  = 213 ; /* KM211 KMX8 8-bit processor */
        public static final int EM_KVARC  = 214 ; /* KM211 KVARC processor */
        public static final int EM_CDP  = 215 ; /* Paneve CDP architecture family */
        public static final int EM_COGE  = 216 ; /* Cognitive Smart Memory Processor */
        public static final int EM_COOL  = 217 ; /* Bluechip Systems CoolEngine */
        public static final int EM_NORC  = 218 ; /* Nanoradio Optimized RISC */
        public static final int EM_CSR_KALIMBA  = 219 ; /* CSR Kalimba architecture family */
        public static final int EM_Z80  = 220 ; /* Zilog Z80 */
        public static final int EM_VISIUM = 221; /* Controls and Data Services VISIUMcore processor */
        public static final int EM_FT32 = 222    ; /* FTDI Chip FT32 high performance 32-bit RISC architecture */
        public static final int EM_MOXIE = 223    ; /* Moxie processor family */
        public static final int EM_AMDGPU  = 224 ; /* AMD GPU architecture */
        public static final int EM_RISCV  = 243 ; /* RISC-V */
        public static final int EM_LANAI = 244; /* Lanai 32-bit processor.  */
        public static final int EM_CEVA = 245; /* CEVA Processor Architecture Family */
        public static final int EM_CEVA_X2 = 246; /* CEVA X2 Processor Family */
        public static final int EM_BPF = 247; /* Linux BPF – in-kernel virtual machine.  */
        public static final int EM_GRAPHCORE_IPU = 248; /* Graphcore Intelligent Processing Unit */
        public static final int EM_IMG1 = 249; /* Imagination Technologies */
        public static final int EM_NFP = 250; /* Netronome Flow Processor.  */
        public static final int EM_VE = 251; /* NEC Vector Engine */
        public static final int EM_CSKY = 252; /* C-SKY processor family.  */
        public static final int EM_ARC_COMPACT3_64 = 253; /* Synopsys ARCv2.3 64-bit */
        public static final int EM_MCS6502 = 254; /* MOS Technology MCS 6502 processor */
        public static final int EM_ARC_COMPACT3 = 255; /* Synopsys ARCv2.3 32-bit */
        public static final int EM_KVX = 256; /* Kalray VLIW core of the MPPA processor family */
        public static final int EM_65816 = 257; /* WDC 65816/65C816 */
        public static final int EM_LOONGARCH = 258; /* LoongArch */
        public static final int EM_KF32 = 259; /* ChipON KungFu32 */

        public static String getMachine(int e_machine){
            switch (e_machine)
            {
                /* Please keep this switch table sorted by increasing EM_ value.  */
                /* 0 */
                case EM_NONE:		return "None";
                case EM_M32:		return "WE32100";
                case EM_SPARC:		return "Sparc";
                case EM_386:		return "Intel 80386";
                case EM_68K:		return "MC68000";
                case EM_88K:		return "MC88000";
                case EM_IAMCU:		return "Intel MCU";
                case EM_860:		return "Intel 80860";
                case EM_MIPS:		return "MIPS R3000";
                case EM_S370:		return "IBM System/370";
                /* 10 */
                case EM_MIPS_RS3_LE:	return "MIPS R4000 big-endian";
                case EM_OLD_SPARCV9:	return "Sparc v9 (old)";
                case EM_PARISC:		return "HPPA";
                case EM_VPP550:		return "Fujitsu VPP500";
                case EM_SPARC32PLUS:	return "Sparc v8+" ;
                case EM_960:		return "Intel 80960";
                case EM_PPC:		return "PowerPC";
                /* 20 */
                case EM_PPC64:		return "PowerPC64";
                case EM_S390_OLD:
                case EM_S390:		return "IBM S/390";
                case EM_SPU:		return "SPU";
                /* 30 */
                case EM_V800:		return "Renesas V850 (using RH850 ABI)";
                case EM_FR20:		return "Fujitsu FR20";
                case EM_RH32:		return "TRW RH32";
                case EM_MCORE:		return "MCORE";
                /* 40 */
                case EM_ARM:		return "ARM";
                case EM_OLD_ALPHA:		return "Digital Alpha (old)";
                case EM_SH:			return "Renesas / SuperH SH";
                case EM_SPARCV9:		return "Sparc v9";
                case EM_TRICORE:		return "Siemens Tricore";
                case EM_ARC:		return "ARC";
                case EM_H8_300:		return "Renesas H8/300";
                case EM_H8_300H:		return "Renesas H8/300H";
                case EM_H8S:		return "Renesas H8S";
                case EM_H8_500:		return "Renesas H8/500";
                /* 50 */
                case EM_IA_64:		return "Intel IA-64";
                case EM_MIPS_X:		return "Stanford MIPS-X";
                case EM_COLDFIRE:		return "Motorola Coldfire";
                case EM_68HC12:		return "Motorola MC68HC12 Microcontroller";
                case EM_MMA:		return "Fujitsu Multimedia Accelerator";
                case EM_PCP:		return "Siemens PCP";
                case EM_NCPU:		return "Sony nCPU embedded RISC processor";
                case EM_NDR1:		return "Denso NDR1 microprocesspr";
                case EM_STARCORE:		return "Motorola Star*Core processor";
                case EM_ME16:		return "Toyota ME16 processor";
                /* 60 */
                case EM_ST100:		return "STMicroelectronics ST100 processor";
                case EM_TINYJ:		return "Advanced Logic Corp. TinyJ embedded processor";
                case EM_X86_64:		return "Advanced Micro Devices X86-64";
                case EM_PDSP:		return "Sony DSP processor";
                case EM_PDP10:		return "Digital Equipment Corp. PDP-10";
                case EM_PDP11:		return "Digital Equipment Corp. PDP-11";
                case EM_FX66:		return "Siemens FX66 microcontroller";
                case EM_ST9PLUS:		return "STMicroelectronics ST9+ 8/16 bit microcontroller";
                case EM_ST7:		return "STMicroelectronics ST7 8-bit microcontroller";
                case EM_68HC16:		return "Motorola MC68HC16 Microcontroller";
                /* 70 */
                case EM_68HC11:		return "Motorola MC68HC11 Microcontroller";
                case EM_68HC08:		return "Motorola MC68HC08 Microcontroller";
                case EM_68HC05:		return "Motorola MC68HC05 Microcontroller";
                case EM_SVX:		return "Silicon Graphics SVx";
                case EM_ST19:		return "STMicroelectronics ST19 8-bit microcontroller";
                case EM_VAX:		return "Digital VAX";
                case EM_CRIS:		return "Axis Communications 32-bit embedded processor";
                case EM_JAVELIN:		return "Infineon Technologies 32-bit embedded cpu";
                case EM_FIREPATH:		return "Element 14 64-bit DSP processor";
                case EM_ZSP:		return "LSI Logic's 16-bit DSP processor";
                /* 80 */
                case EM_MMIX:		return "Donald Knuth's educational 64-bit processor";
                case EM_HUANY:		return "Harvard Universitys's machine-independent object format";
                case EM_PRISM:		return "Vitesse Prism";
                case EM_AVR_OLD:
                case EM_AVR:		return "Atmel AVR 8-bit microcontroller";
                case EM_CYGNUS_FR30:
                case EM_FR30:		return "Fujitsu FR30";
                case EM_CYGNUS_D10V:
                case EM_D10V:		return "d10v";
                case EM_CYGNUS_D30V:
                case EM_D30V:		return "d30v";
                case EM_CYGNUS_V850:
                case EM_V850:		return "Renesas V850";
                case EM_CYGNUS_M32R:
                case EM_M32R:		return "Renesas M32R (formerly Mitsubishi M32r)";
                case EM_CYGNUS_MN10300:
                case EM_MN10300:		return "mn10300";
                /* 90 */
                case EM_CYGNUS_MN10200:
                case EM_MN10200:		return "mn10200";
                case EM_PJ:			return "picoJava";
                case EM_OR1K:		return "OpenRISC 1000";
                case EM_ARC_COMPACT:	return "ARCompact";
                case EM_XTENSA_OLD:
                case EM_XTENSA:		return "Tensilica Xtensa Processor";
                case EM_VIDEOCORE:		return "Alphamosaic VideoCore processor";
                case EM_TMM_GPP:		return "Thompson Multimedia General Purpose Processor";
                case EM_NS32K:		return "National Semiconductor 32000 series";
                case EM_TPC:		return "Tenor Network TPC processor";
                case EM_SNP1K:	        return "Trebia SNP 1000 processor";
                /* 100 */
                case EM_ST200:		return "STMicroelectronics ST200 microcontroller";
                case EM_IP2K_OLD:
                case EM_IP2K:		return "Ubicom IP2xxx 8-bit microcontrollers";
                case EM_MAX:		return "MAX Processor";
                case EM_CR:			return "National Semiconductor CompactRISC";
                case EM_F2MC16:		return "Fujitsu F2MC16";
                case EM_MSP430:		return "Texas Instruments msp430 microcontroller";
                case EM_BLACKFIN:		return "Analog Devices Blackfin";
                case EM_SE_C33:		return "S1C33 Family of Seiko Epson processors";
                case EM_SEP:		return "Sharp embedded microprocessor";
                case EM_ARCA:		return "Arca RISC microprocessor";
                /* 110 */
                case EM_UNICORE:		return "Unicore";
                case EM_EXCESS:		return "eXcess 16/32/64-bit configurable embedded CPU";
                case EM_DXP:		return "Icera Semiconductor Inc. Deep Execution Processor";
                case EM_ALTERA_NIOS2:	return "Altera Nios II";
                case EM_CRX:		return "National Semiconductor CRX microprocessor";
                case EM_XGATE:		return "Motorola XGATE embedded processor";
                case EM_C166:
                case EM_XC16X:		return "Infineon Technologies xc16x";
                case EM_M16C:		return "Renesas M16C series microprocessors";
                case EM_DSPIC30F:		return "Microchip Technology dsPIC30F Digital Signal Controller";
                case EM_CE:			return "Freescale Communication Engine RISC core";
                /* 120 */
                case EM_M32C:	        return "Renesas M32c";
                /* 130 */
                case EM_TSK3000:		return "Altium TSK3000 core";
                case EM_RS08:		return "Freescale RS08 embedded processor";
                case EM_ECOG2:		return "Cyan Technology eCOG2 microprocessor";
                case EM_SCORE:		return "SUNPLUS S+Core";
                case EM_DSP24:		return "New Japan Radio (NJR) 24-bit DSP Processor";
                case EM_VIDEOCORE3:		return "Broadcom VideoCore III processor";
                case EM_LATTICEMICO32:	return "Lattice Mico32";
                case EM_SE_C17:		return "Seiko Epson C17 family";
                /* 140 */
                case EM_TI_C6000:		return "Texas Instruments TMS320C6000 DSP family";
                case EM_TI_C2000:		return "Texas Instruments TMS320C2000 DSP family";
                case EM_TI_C5500:		return "Texas Instruments TMS320C55x DSP family";
                case EM_TI_PRU:		return "TI PRU I/O processor";
                /* 160 */
                case EM_MMDSP_PLUS:		return "STMicroelectronics 64bit VLIW Data Signal Processor";
                case EM_CYPRESS_M8C:	return "Cypress M8C microprocessor";
                case EM_R32C:		return "Renesas R32C series microprocessors";
                case EM_TRIMEDIA:		return "NXP Semiconductors TriMedia architecture family";
                case EM_QDSP6:		return "QUALCOMM DSP6 Processor";
                case EM_8051:		return "Intel 8051 and variants";
                case EM_STXP7X:		return "STMicroelectronics STxP7x family";
                case EM_NDS32:		return "Andes Technology compact code size embedded RISC processor family";
                case EM_ECOG1X:		return "Cyan Technology eCOG1X family";
                case EM_MAXQ30:		return "Dallas Semiconductor MAXQ30 Core microcontrollers";
                /* 170 */
                case EM_XIMO16:		return "New Japan Radio (NJR) 16-bit DSP Processor";
                case EM_MANIK:		return "M2000 Reconfigurable RISC Microprocessor";
                case EM_CRAYNV2:		return "Cray Inc. NV2 vector architecture";
                case EM_RX:			return "Renesas RX";
                case EM_METAG:		return "Imagination Technologies Meta processor architecture";
                case EM_MCST_ELBRUS:	return "MCST Elbrus general purpose hardware architecture";
                case EM_ECOG16:		return "Cyan Technology eCOG16 family";
                case EM_CR16:
                case EM_MICROBLAZE:
                case EM_MICROBLAZE_OLD:	return "Xilinx MicroBlaze";
                case EM_ETPU:		return "Freescale Extended Time Processing Unit";
                case EM_SLE9X:		return "Infineon Technologies SLE9X core";
                /* 180 */
                case EM_L1OM:		return "Intel L1OM";
                case EM_K1OM:		return "Intel K1OM";
                case EM_INTEL182:		return "Intel (reserved)";
                case EM_AARCH64:		return "AArch64";
                case EM_ARM184:		return "ARM (reserved)";
                case EM_AVR32:		return "Atmel Corporation 32-bit microprocessor";
                case EM_STM8:		return "STMicroeletronics STM8 8-bit microcontroller";
                case EM_TILE64:		return "Tilera TILE64 multicore architecture family";
                case EM_TILEPRO:		return "Tilera TILEPro multicore architecture family";
                /* 190 */
                case EM_CUDA:		return "NVIDIA CUDA architecture";
                case EM_TILEGX:		return "Tilera TILE-Gx multicore architecture family";
                case EM_CLOUDSHIELD:	return "CloudShield architecture family";
                case EM_COREA_1ST:		return "KIPO-KAIST Core-A 1st generation processor family";
                case EM_COREA_2ND:		return "KIPO-KAIST Core-A 2nd generation processor family";
                case EM_ARC_COMPACT2:	return "ARCv2";
                case EM_OPEN8:		return "Open8 8-bit RISC soft processor core";
                case EM_RL78:		return "Renesas RL78";
                case EM_VIDEOCORE5:		return "Broadcom VideoCore V processor";
                case EM_78K0R:		return "Renesas 78K0R";
                /* 200 */
                case EM_56800EX:		return "Freescale 56800EX Digital Signal Controller (DSC)";
                case EM_BA1:		return "Beyond BA1 CPU architecture";
                case EM_BA2:		return "Beyond BA2 CPU architecture";
                case EM_XCORE:		return "XMOS xCORE processor family";
                case EM_MCHP_PIC:		return "Microchip 8-bit PIC(r) family";
                case EM_INTELGT:		return "Intel Graphics Technology";
                /* 210 */
                case EM_KM32:		return "KM211 KM32 32-bit processor";
                case EM_KMX32:		return "KM211 KMX32 32-bit processor";
                case EM_KMX16:		return "KM211 KMX16 16-bit processor";
                case EM_KMX8:		return "KM211 KMX8 8-bit processor";
                case EM_KVARC:		return "KM211 KVARC processor";
                case EM_CDP:		return "Paneve CDP architecture family";
                case EM_COGE:		return "Cognitive Smart Memory Processor";
                case EM_COOL:		return "Bluechip Systems CoolEngine";
                case EM_NORC:		return "Nanoradio Optimized RISC";
                case EM_CSR_KALIMBA:	return "CSR Kalimba architecture family";
                /* 220 */
                case EM_Z80:		return "Zilog Z80";
                case EM_VISIUM:		return "CDS VISIUMcore processor";
                case EM_FT32:               return "FTDI Chip FT32";
                case EM_MOXIE:              return "Moxie";
                case EM_AMDGPU: 	 	return "AMD GPU";
                /* 230 (all reserved) */
                /* 240 */
                case EM_RISCV: 	 	return "RISC-V";
                case EM_LANAI:		return "Lanai 32-bit processor";
                case EM_CEVA:		return "CEVA Processor Architecture Family";
                case EM_CEVA_X2:		return "CEVA X2 Processor Family";
                case EM_BPF:		return "Linux BPF";
                case EM_GRAPHCORE_IPU:	return "Graphcore Intelligent Processing Unit";
                case EM_IMG1:		return "Imagination Technologies";
                /* 250 */
                case EM_NFP:		return "Netronome Flow Processor";
                case EM_VE:			return "NEC Vector Engine";
                case EM_CSKY:		return "C-SKY";
                case EM_ARC_COMPACT3_64:	return "Synopsys ARCv2.3 64-bit";
                case EM_MCS6502:		return "MOS Technology MCS 6502 processor";
                case EM_ARC_COMPACT3:	return "Synopsys ARCv2.3 32-bit";
                case EM_KVX:		return "Kalray VLIW core of the MPPA processor family";
                case EM_65816:		return "WDC 65816/65C816";
                case EM_LOONGARCH:		return "LoongArch";
                case EM_KF32:		return "ChipON KungFu32";

                /* Large numbers...  */
                case EM_MT:                 return "Morpho Techologies MT processor";
                case EM_ALPHA:		return "Alpha";
                case EM_WEBASSEMBLY:	return "Web Assembly";
                case EM_DLX:		return "OpenDLX";
                case EM_XSTORMY16:		return "Sanyo XStormy16 CPU core";
                case EM_IQ2000:       	return "Vitesse IQ2000";
                case EM_M32C_OLD:
                case EM_NIOS32:		return "Altera Nios";
                case EM_CYGNUS_MEP:         return "Toshiba MeP Media Engine";
                case EM_ADAPTEVA_EPIPHANY:	return "Adapteva EPIPHANY";
                case EM_CYGNUS_FRV:		return "Fujitsu FR-V";
                case EM_S12Z:               return "Freescale S12Z";

                default:
                    return "<unknown>: 0x%x" + Integer.toHexString(e_machine);
            }
        }

        /* If it is necessary to assign new unofficial EM_* values, please pick large
   random numbers (0x8523, 0xa7f2, etc.) to minimize the chances of collision
   with official or non-GNU unofficial values.
   NOTE: Do not just increment the most recent number by one.
   Somebody else somewhere will do exactly the same thing, and you
   will have a collision.  Instead, pick a random number.
   Normally, each entity or maintainer responsible for a machine with an
   unofficial e_machine number should eventually ask registry@sco.com for
   an officially blessed number to be added to the list above.*/

        /* AVR magic number.  Written in the absense of an ABI.  */
        public static final int EM_AVR_OLD = 0x1057;

        /* MSP430 magic number.  Written in the absense of everything.  */
        public static final int EM_MSP430_OLD = 0x1059;

        /* Morpho MT.   Written in the absense of an ABI.  */
        public static final int EM_MT = 0x2530;

        /* FR30 magic number - no EABI available.  */
        public static final int EM_CYGNUS_FR30 = 0x3330;

        /* Unofficial value for Web Assembly binaries, as used by LLVM.  */
        public static final int EM_WEBASSEMBLY = 0x4157;

        /* Freescale S12Z.   The Freescale toolchain generates elf files with this value.  */
        public static final int EM_S12Z = 0x4DEF;

        /* DLX magic number.  Written in the absense of an ABI.  */
        public static final int EM_DLX = 0x5aa5;

        /* FRV magic number - no EABI available??.  */
        public static final int EM_CYGNUS_FRV = 0x5441;

        /* Infineon Technologies 16-bit microcontroller with C166-V2 core.  */
        public static final int EM_XC16X = 0x4688;

        /* D10V backend magic number.  Written in the absence of an ABI.  */
        public static final int EM_CYGNUS_D10V = 0x7650;

        /* D30V backend magic number.  Written in the absence of an ABI.  */
        public static final int EM_CYGNUS_D30V = 0x7676;

        /* Ubicom IP2xxx;   Written in the absense of an ABI.  */
        public static final int EM_IP2K_OLD = 0x8217;

        /* Cygnus PowerPC ELF backend.  Written in the absence of an ABI.  */
        public static final int EM_CYGNUS_POWERPC = 0x9025;

        /* Alpha backend magic number.  Written in the absence of an ABI.  */
        public static final int EM_ALPHA = 0x9026;

        /* Cygnus M32R ELF backend.  Written in the absence of an ABI.  */
        public static final int EM_CYGNUS_M32R = 0x9041;

        /* V850 backend magic number.  Written in the absense of an ABI.  */
        public static final int EM_CYGNUS_V850 = 0x9080;

        /* old S/390 backend magic number. Written in the absence of an ABI.  */
        public static final int EM_S390_OLD = 0xa390;

        /* Old, unofficial value for Xtensa.  */
        public static final int EM_XTENSA_OLD = 0xabc7;

        public static final int EM_XSTORMY16 = 0xad45;

        /* mn10200 and mn10300 backend magic numbers.
           Written in the absense of an ABI.  */
        public static final int EM_CYGNUS_MN10300 = 0xbeef;
        public static final int EM_CYGNUS_MN10200 = 0xdead;

        /* Renesas M32C and M16C.  */
        public static final int EM_M32C_OLD = 0xFEB0;

        /* Vitesse IQ2000.  */
        public static final int EM_IQ2000 = 0xFEBA;

        /* NIOS magic number - no EABI available.  */
        public static final int EM_NIOS32 = 0xFEBB;

        public static final int EM_CYGNUS_MEP = 0xF00D;  /* Toshiba MeP */

        /* Old, unofficial value for Moxie.  */
        public static final int EM_MOXIE_OLD = 0xFEED;

        public static final int EM_MICROBLAZE_OLD = 0xbaab; /* Old MicroBlaze */

        public static final int EM_ADAPTEVA_EPIPHANY = 0x1223;  /* Adapteva's Epiphany architecture.  */

        /* Old constant that might be in use by some software. */
        public static final int EM_OPENRISC = EM_OR1K;

        /* C-SKY historically used 39, the same value as MCORE, from which the
           architecture was derived.  */
        public static final int EM_CSKY_OLD = EM_MCORE;


        public  enum ISA{
            Invalid_instruction_set(-1, "Invalid instruction set"),
            No_specific_instruction_set(0x00, "No specific instruction set"),
            AT_AND_T_WE_32100(0x01, "AT&T WE 32100"),
            SPARC(0x02, "SPARC"),
            x86(0x03, "x86"),
            Motorola_68000_M68k(0x04, "Motorola 68000 (M68k)"),
            Motorola_88000_M88k(0x05, "Motorola 88000 (M88k)"),
            Intel_MCU(0x06, "Intel MCU"),
            Intel_80860(0x07, "Intel 80860"),
            MIPS(0x08, "MIPS"),
            IBM_System_370(0x09, "IBM_System/370"),
            MIPS_RS3000_Little_endian(0x0A, "MIPS RS3000 Little-endian"),
            Hewlett_Packard_PA_RISC(0x0E, "Hewlett-Packard PA-RISC"),
            Intel_80960(0x0F, "Reserved for future use"),
            PowerPC(0x13, "Intel 80960"),
            PowerPC_64_bit(0x14, "PowerPC"),
            S390_including_S390x(0x15, "PowerPC (64-bit)"),
            IBM_SPU_SPC(0x16, "S390, including S390x"),
            NEC_V800(0x17, "IBM SPU/SPC"),
            Fujitsu_FR20(0x24, "NEC V800"),
            TRW_RH_32(0x25, "Fujitsu FR20"),
            Motorola_RCE(0x26, "TRW RH-32"),
            ARM_up_to_ARMv7_Aarch32(0x27, "Motorola RCE"),
            Digital_Alpha(0x28, "ARM (up to ARMv7/Aarch32)"),
            SuperH(0x29, "Digital Alpha"),
            SPARC_Version_9(0x2A, "SuperH"),
            Siemens_TriCore_embedded_processor(0x2B, "SPARC Version 9"),
            Argonaut_RISC_Core(0x2C, "Siemens TriCore embedded processor"),
            Hitachi_H8_300(0x2D, "Argonaut RISC Core"),
            Hitachi_H8_300H(0x2E, "Hitachi H8/300"),
            Hitachi_H8S(0x2F, "Hitachi H8/300H"),
            Hitachi_H8_500(0x30, "Hitachi H8S"),
            IA_64(0x31, "Hitachi H8/500"),
            Stanford_MIPS_X(0x32, "IA-64"),
            Motorola_ColdFire(0x33, "Stanford MIPS-X"),
            Motorola_M68HC12(0x34, "Motorola ColdFire"),
            Fujitsu_MMA_Multimedia_Accelerator(0x35, "Motorola M68HC12"),
            Siemens_PCP(0x36, "Fujitsu MMA Multimedia Accelerator"),
            Sony_nCPU_embedded_RISC_processor(0x37, "Siemens PCP"),
            Denso_NDR1_microprocessor(0x38, "Sony nCPU embedded RISC processor"),
            Motorola_Star_Core_processor(0x39, "Denso NDR1 microprocessor"),
            Toyota_ME16_processor(0x3A, "Motorola Star*Core processor"),
            STMicroelectronics_ST100_processor(0x3B, "Toyota ME16 processor"),
            Advanced_Logic_Corp_TinyJ_embedded_processor_family(0x3C, "STMicroelectronics ST100 processor"),
            AMD_x86_64(0x3D, "Advanced Logic Corp. TinyJ embedded processor family"),
            TMS320C6000_Family(0x3E, "AMD x86-64"),
            MCST_Elbrus_e2k(0x8C, "TMS320C6000 Family"),
            ARM_64_bits_ARMv8_Aarch64(0xAF, "MCST Elbrus e2k"),
            RISC_V(0xB7, "ARM 64-bits (ARMv8/Aarch64)"),
            Berkeley_Packet_Filter(0xF3, "RISC-V"),
            WDC_65C816(0xF7, "Berkeley Packet Filter");

            public final int value;
            public final String name;

            public static ISA fromValue(int value){
                for(ISA isa: ISA.values()){
                    if(isa.value == value)
                        return isa;
                }
                return ISA.Invalid_instruction_set;
            }

            ISA(int value, String name){
                this.value = value;
                this.name = name;
            }
        }



        public final byte[] e_ident = new byte[16]; //name is zero terminated followed by zero padding
        /*
          this should equal 0x7f, 'E', 'L', 'F' and something else
         */

        public short e_type;
        /*
          ET_NONE
                 An unknown type.
          ET_REL A relocatable file.
          ET_EXEC
                 An executable file.
          ET_DYN A shared object.
          ET_CORE
                 A core file.
         */

        public short e_machine;
        /*
          This member specifies the required architecture for an
          individual file.  For example:

          EM_NONE
                 An unknown machine
          EM_M32 AT&T WE 32100
          EM_SPARC
                 Sun Microsystems SPARC
          EM_386 Intel 80386
          EM_68K Motorola 68000
          EM_88K Motorola 88000
          EM_860 Intel 80860
          EM_MIPS
                 MIPS RS3000 (big-endian only)
          EM_PARISC
                 HP/PA
          EM_SPARC32PLUS
                 SPARC with enhanced instruction set
          EM_PPC PowerPC
          EM_PPC64
                 PowerPC 64-bit
          EM_S390
                 IBM S/390
          EM_ARM Advanced RISC Machines
          EM_SH  Renesas SuperH
          EM_SPARCV9
                 SPARC v9 64-bit
          EM_IA_64
                 Intel Itanium
          EM_X86_64
                 AMD x86-64
          EM_VAX DEC Vax
        */

        public int e_version;
        /*
          This member identifies the file version:

          EV_NONE
                 Invalid version
          EV_CURRENT
                 Current version
        */

        public long e_entry;
        /*
          This member gives the virtual address to which the system
          first transfers control, thus starting the process.  If
          the file has no associated entry point, this member holds
          zero.
         */

        public long e_phoff;
        /*
          This member holds the program header table's file offset
          in bytes.  If the file has no program header table, this
          member holds zero.
         */

        public long e_shoff;
        /*
          This member holds the section header table's file offset
          in bytes.  If the file has no section header table, this
          member holds zero.
         */

        public int e_flags;
        /*
          This member holds processor-specific flags associated with
          the file.  Flag names take the form EF_`machine_flag'.
          Currently, no flags have been defined.
         */

        public short e_ehsize;
        /*
          This member holds the ELF header's size in bytes.
         */

        public short e_phentsize;
        /*
          This member holds the size in bytes of the one entry in the
          file's program header table; all entries are the same
          size
         */

        public short e_phnum;
        /*
          This member holds the number of entries in the program
          header table.  Thus the product of e_phentsize and e_phnum
          gives the table's size in bytes.  If a file has no program
          header, e_phnum holds the value zero.

          If the number of entries in the program header table is
          larger than or equal to PN_XNUM (0xffff), this member
          holds PN_XNUM (0xffff) and the real number of entries in
          the program header table is held in the sh_info member of
          the initial entry in section header table.  Otherwise, the
          sh_info member of the initial entry contains the value
          zero.

          PN_XNUM
                 This is defined as 0xffff, the largest number
                 e_phnum can have, specifying where the actual
                 number of program headers is assigned.
         */

        public short e_shentsize;
        /*
        This member holds a sections header's size in bytes. A
        section header is one entry in the section header table;
        all entries are the same size.
         */

        public short e_shnum;
        /*
          This member holds the number of entries in the section
          header table.  Thus the product of e_shentsize and e_shnum
          gives the section header table's size in bytes.  If a file
          has no section header table, e_shnum holds the value of
          zero.

          If the number of entries in the section header table is
          larger than or equal to SHN_LORESERVE (0xff00), e_shnum
          holds the value zero and the real number of entries in the
          section header table is held in the sh_size member of the
          initial entry in section header table.  Otherwise, the
          sh_size member of the initial entry in the section header
          table holds the value zero.
         */

        public short e_shstrndx;
        /*
          This member holds the section header table index of the
          entry associated with the section name string table.  If
          the file has no section name string table, this member
          holds the value SHN_UNDEF.

          If the index of section name string table section is
          larger than or equal to SHN_LORESERVE (0xff00), this
          member holds SHN_XINDEX (0xffff) and the real index of the
          section name string table section is held in the sh_link
          member of the initial entry in section header table.
          Otherwise, the sh_link member of the initial entry in
          section header table contains the value zero.
         */

        protected FileHeader read(Elf elf) throws IOException {

            elf.raf.seek(0);
            elf.raf.read(this.e_ident);
            elf.fileHeader = this;//this is so things work bla bla bla
            if(e_ident[EI_CLASS] == 1) {
                read32(elf);
            }else if(e_ident[EI_CLASS] == 2){
                read64(elf);
            }else{
                throw new ElfException("Unrecognized elf class either 1(32 bit) or 2(64 bit) expected got: " + e_ident[EI_CLASS]);
            }

            return this;
        }

        protected void read64(Elf elf) throws IOException {
            this.e_type = elf.readShort();
            this.e_machine = elf.readShort();
            this.e_version = elf.readInt();
            this.e_entry = elf.readLong();
            this.e_phoff = elf.readLong();
            this.e_shoff = elf.readLong();
            this.e_flags = elf.readInt();
            this.e_ehsize = elf.readShort();
            this.e_phentsize = elf.readShort();
            this.e_phnum = elf.readShort();
            this.e_shentsize = elf.readShort();
            this.e_shnum = elf.readShort();
            this.e_shstrndx = elf.readShort();
        }
        protected void read32(Elf elf) throws IOException{
            this.e_type = elf.readShort();
            this.e_machine = elf.readShort();
            this.e_version = elf.readInt();
            this.e_entry = elf.readInt();
            this.e_phoff = elf.readInt();
            this.e_shoff = elf.readInt();
            this.e_flags = elf.readInt();
            this.e_ehsize = elf.readShort();
            this.e_phentsize = elf.readShort();
            this.e_phnum = elf.readShort();
            this.e_shentsize = elf.readShort();
            this.e_shnum = elf.readShort();
            this.e_shstrndx = elf.readShort();
        }
    }

    public static class ProgramHeader{

        public enum TYPE{
            PT_NULL(0),
            PT_LOAD(1),
            PT_DYNAMIC(2),
            PT_INTERP(3),
            PT_NOTE(4),
            PT_SHLIB(5),
            PT_PHDR(6),
            PT_TLS(7),
            PT_LOOS(0x60000000),
            PT_HIOS(0x6fffffff),
            PT_LOPROC(0x70000000),
            PT_HIPROC(0x7fffffff),
            // The remaining values are not in the standard.
            // Frame unwind information.
            PT_GNU_EH_FRAME(0x6474e550),
            PT_SUNW_EH_FRAME(0x6474e550),
            // Stack flags.
            PT_GNU_STACK(0x6474e551),
            // Read only after relocation.
            PT_GNU_RELRO(0x6474e552),
            // Platform architecture compatibility information
            PT_ARM_ARCHEXT(0x70000000),
            // Exception unwind tables
            PT_ARM_EXIDX(0x70000001),
            // Register usage information.  Identifies one .reginfo section.
            PT_MIPS_REGINFO(0x70000000),
            // Runtime procedure table.
             PT_MIPS_RTPROC(0x70000001),
            // .MIPS.options section.
            PT_MIPS_OPTIONS(0x70000002),
            // .MIPS.abiflags section.
            PT_MIPS_ABIFLAGS(0x70000003),
            // Platform architecture compatibility information
            PT_AARCH64_ARCHEXT(0x70000000),
            // Exception unwind tables
            PT_AARCH64_UNWIND(0x70000001),
            // 4k page table size
            PT_S390_PGSTE(0x70000000);

            public final int TYPE;

            public static TYPE fromType(int type){
                for(ProgramHeader.TYPE typeO: ProgramHeader.TYPE.values()){
                    if(typeO.TYPE == type)
                        return typeO;
                }
                return PT_NULL;
            }

            TYPE(int type){
                this.TYPE = type;
            }
        }
        
        public static final int PT_NULL = 0;		/* Program header table entry unused */
        public static final int PT_LOAD = 1;		/* Loadable program segment */
        public static final int PT_DYNAMIC = 2;		/* Dynamic linking information */
        public static final int PT_INTERP = 3;		/* Program interpreter */
        public static final int PT_NOTE = 4;		/* Auxiliary information */
        public static final int PT_SHLIB = 5;		/* Reserved, unspecified semantics */
        public static final int PT_PHDR = 6;		/* Entry for header table itself */
        public static final int PT_TLS = 7;		/* Thread local storage segment */
        public static final int PT_LOOS = 0x60000000;	/* OS-specific */
        public static final int PT_HIOS = 0x6fffffff;	/* OS-specific */
        public static final int PT_LOPROC = 0x70000000;	/* Processor-specific */
        public static final int PT_HIPROC = 0x7FFFFFFF;	/* Processor-specific */

        public static final int PT_GNU_EH_FRAME  = (PT_LOOS + 0x474e550); /* Frame unwind information */
        public static final int PT_SUNW_EH_FRAME = PT_GNU_EH_FRAME;     /* Solaris uses the same value */
        public static final int PT_GNU_STACK = (PT_LOOS + 0x474e551); /* Stack flags */
        public static final int PT_GNU_RELRO = (PT_LOOS + 0x474e552); /* Read-only after relocation */
        public static final int PT_GNU_PROPERTY = (PT_LOOS + 0x474e553); /* GNU property */

        /* OpenBSD segment types.  */
        public static final int PT_OPENBSD_RANDOMIZE = (PT_LOOS + 0x5a3dbe6);  /* Fill with random data.  */
        public static final int PT_OPENBSD_WXNEEDED = (PT_LOOS + 0x5a3dbe7);  /* Program does W^X violations.  */
        public static final int PT_OPENBSD_BOOTDATA = (PT_LOOS + 0x5a41be6);  /* Section for boot arguments.  */

        /* Mbind segments */
        public static final int PT_GNU_MBIND_NUM = 4096;
        public static final int PT_GNU_MBIND_LO = (PT_LOOS + 0x474e555);
        public static final int PT_GNU_MBIND_HI = (PT_GNU_MBIND_LO + PT_GNU_MBIND_NUM - 1);

        /* Program segment permissions, in program header p_flags field.  */

        public static final int PF_X = (1 << 0);	/* Segment is executable */
        public static final int PF_W = (1 << 1);	/* Segment is writable */
        public static final int PF_R = (1 << 2);	/* Segment is readable */
        /* public static final int PF_MASKOS	0x0F000000    *//* OS-specific reserved bits */
        public static final int PF_MASKOS = 0x0FF00000;	/* New value, Oct 4, 1999 Draft */
        public static final int PF_MASKPROC = 0xF0000000;	/* Processor-specific reserved bits */


        public int p_type;
        public int p_flags;
        public long p_offset;
        public long p_vaddr;
        public long p_paddr;
        public long p_filesz;
        public long p_memsz;
        public long p_align;

        protected ProgramHeader read(Elf elf, int index) throws IOException {

            if(elf.fileHeader.e_phoff == 0){
                return null;
            }

            elf.raf.seek(elf.fileHeader.e_phoff + elf.fileHeader.e_phentsize * index);

            if(elf.fileHeader.e_ident[FileHeader.EI_CLASS] == 1) {
                read32(elf);
            }else if(elf.fileHeader.e_ident[FileHeader.EI_CLASS] == 2){
                read64(elf);
            }else{
                throw new IOException();
            }


            return this;
        }

        private void read32(Elf elf) throws IOException {
            p_type = elf.readInt();
            p_offset = elf.readInt();
            p_vaddr = elf.readInt();
            p_paddr = elf.readInt();
            p_filesz = elf.readInt();
            p_memsz = elf.readInt();
            p_flags = elf.readInt();
            p_align = elf.readInt();
        }

        private void read64(Elf elf) throws IOException {
            p_type = elf.readInt();
            p_flags = elf.readInt();
            p_offset = elf.readLong();
            p_vaddr = elf.readLong();
            p_paddr = elf.readLong();
            p_filesz = elf.readLong();
            p_memsz = elf.readLong();
            p_align = elf.readLong();
        }


    }

    public static class SectionHeader{

        //types
        public static final int SHT_NULL = 0x0;
        public static final int SHT_PROGBITS = 0x1;
        public static final int SHT_SYMTAB = 0x2;
        public static final int SHT_STRTAB = 0x3;
        public static final int SHT_RELA = 0x4;
        public static final int SHT_HASH = 0x5;
        public static final int SHT_DYNAMIC = 0x6;
        public static final int SHT_NOTE = 0x7;
        public static final int SHT_NOBITS = 0x8; /* Section occupies no space in file */
        public static final int SHT_REL = 0x9;    /* Relocation entries, no addends */
        public static final int SHT_SHLIB = 0xA;  /* Reserved, unspecified semantics */
        public static final int SHT_DYNSYM = 0xB; /* Dynamic linking symbol table */
        public static final int SHT_INIT_ARRAY = 0xE;
        public static final int SHT_FINI_ARRAY = 0xF;
        public static final int SHT_PREINIT_ARRAY = 0x10;
        public static final int SHT_GROUP = 0x11;
        public static final int SHT_SYMTAB_SHNDX = 0x12;
        public static final int SHT_NUM = 0x13;
        public static final int SHT_LOOS = 0x60000000;
        public static final int  SHT_HIOS = 0x6fffffff;	/* Last of OS specific semantics */

        public static final int  SHT_GNU_INCREMENTAL_INPUTS = 0x6fff4700;   /* incremental build data */
        public static final int  SHT_GNU_ATTRIBUTES = 0x6ffffff5;	/* Object attributes */
        public static final int  SHT_GNU_HASH = 0x6ffffff6;	/* GNU style symbol hash table */
        public static final int  SHT_GNU_LIBLIST = 0x6ffffff7;	/* List of prelink dependencies */

        /* The next three section types are defined by Solaris, and are named
           SHT_SUNW*.  We use them in GNU code, so we also define SHT_GNU*
           versions.  */
        public static final int  SHT_SUNW_verdef = 0x6ffffffd;	/* Versions defined by file */
        public static final int  SHT_SUNW_verneed = 0x6ffffffe;	/* Versions needed by file */
        public static final int  SHT_SUNW_versym = 0x6fffffff;	/* Symbol versions */

        public static final int  SHT_GNU_verdef = SHT_SUNW_verdef;
        public static final int  SHT_GNU_verneed = SHT_SUNW_verneed;
        public static final int  SHT_GNU_versym = SHT_SUNW_versym;

        public static final int  SHT_LOPROC = 0x70000000;	/* Processor-specific semantics, lo */
        public static final int  SHT_HIPROC = 0x7FFFFFFF;	/* Processor-specific semantics, hi */
        public static final int  SHT_LOUSER = 0x80000000;	/* Application-specific semantics */
        /* public static final int  SHT_HIUSER = 0x8FFFFFFF    *//* Application-specific semantics */
        public static final int  SHT_HIUSER = 0xFFFFFFFF;	/* New value, defined in Oct 4, 1999 Draft */

        //flags
        public static int SHF_WRITE = (1 << 0);
        public static int SHF_ALLOC = (1 << 1);
        public static int SHF_EXECINSTR = (1 << 2);
        public static int SHF_MERGE = (1 << 4);
        public static int SHF_STRINGS = (1 << 5);
        public static int SHF_INFO_LINK = (1 << 6);
        public static int SHF_LINK_ORDER = (1 << 7);
        public static int SHF_OS_NONCONFORMING = (1 << 8);
        public static int SHF_GROUP = (1 << 9);
        public static int SHF_TLS = (1 << 10);
        public static int SHF_COMPRESSED = (1 << 11);	/* Section with compressed data */

        public static int SHF_MASKOS = 0x0FF00000;
        public static int SHF_MASKPROC = 0xF0000000;
        public static int SHF_ORDERED = 0x4000000;

        public static int SHF_EXCLUDE = 0x8000000;
        public static int SHF_GNU_MBIND = 0x01000000;	/* Mbind section.  */



        public int sh_name;
        /*
          This member specifies the name of the section.  Its value
          is an index into the section header string table section,
          giving the location of a null-terminated string.
         */
        public int sh_type;
        /*
          This member categorizes the section's contents and
          semantics.

          SHT_NULL
                 This value marks the section header as inactive.
                 It does not have an associated section.  Other
                 members of the section header have undefined
                 values.

          SHT_PROGBITS
                 This section holds information defined by the
                 program, whose format and meaning are determined
                 solely by the program.

          SHT_SYMTAB
                 This section holds a symbol table.  Typically,
                 SHT_SYMTAB provides symbols for link editing,
                 though it may also be used for dynamic linking.  As
                 a complete symbol table, it may contain many
                 symbols unnecessary for dynamic linking.  An object
                 file can also contain a SHT_DYNSYM section.

          SHT_STRTAB
                 This section holds a string table.  An object file
                 may have multiple string table sections.

          SHT_RELA
                 This section holds relocation entries with explicit
                 addends, such as type Elf32_Rela for the 32-bit
                 class of object files.  An object may have multiple
                 relocation sections.

          SHT_HASH
                 This section holds a symbol hash table.  An object
                 participating in dynamic linking must contain a
                 symbol hash table.  An object file may have only
                 one hash table.

          SHT_DYNAMIC
                 This section holds information for dynamic linking.
                 An object file may have only one dynamic section.

          SHT_NOTE
                 This section holds notes (ElfN_Nhdr).

          SHT_NOBITS
                 A section of this type occupies no space in the
                 file but otherwise resembles SHT_PROGBITS.
                 Although this section contains no bytes, the
                 sh_offset member contains the conceptual file
                 offset.

          SHT_REL
                 This section holds relocation offsets without
                 explicit addends, such as type Elf32_Rel for the
                 32-bit class of object files.  An object file may
                 have multiple relocation sections.

          SHT_SHLIB
                 This section is reserved but has unspecified
                 semantics.

          SHT_DYNSYM
                 This section holds a minimal set of dynamic linking
                 symbols.  An object file can also contain a
                 SHT_SYMTAB section.

          SHT_LOPROC, SHT_HIPROC
                 Values in the inclusive range [SHT_LOPROC,
                 SHT_HIPROC] are reserved for processor-specific
                 semantics.

          SHT_LOUSER
                 This value specifies the lower bound of the range
                 of indices reserved for application programs.

          SHT_HIUSER
                 This value specifies the upper bound of the range
                 of indices reserved for application programs.
                 Section types between SHT_LOUSER and SHT_HIUSER may
                 be used by the application, without conflicting
                 with current or future system-defined section
                 types.
         */
        public long sh_flags;
        /*
          Sections support one-bit flags that describe miscellaneous
          attributes.  If a flag bit is set in sh_flags, the
          attribute is "on" for the section.  Otherwise, the
          attribute is "off" or does not apply.  Undefined
          attributes are set to zero.

          SHF_WRITE
                 This section contains data that should be writable
                 during process execution.

          SHF_ALLOC
                 This section occupies memory during process
                 execution.  Some control sections do not reside in
                 the memory image of an object file.  This attribute
                 is off for those sections.

          SHF_EXECINSTR
                 This section contains executable machine
                 instructions.

          SHF_MASKPROC
                 All bits included in this mask are reserved for
                 processor-specific semantics.
         */
        public long sh_addr;
        /*
          If this section appears in the memory image of a process,
          this member holds the address at which the section's first
          byte should reside.  Otherwise, the member contains zero.
         */
        public long sh_offset;
        /*
          This member's value holds the byte offset from the
          beginning of the file to the first byte in the section.
          One section type, SHT_NOBITS, occupies no space in the
          file, and its sh_offset member locates the conceptual
          placement in the file.
         */
        public long sh_size;
        /*
          This member holds the section's size in bytes.  Unless the
          section type is SHT_NOBITS, the section occupies sh_size
          bytes in the file.  A section of type SHT_NOBITS may have
          a nonzero size, but it occupies no space in the file.
         */
        public int sh_link;
        /*
          This member holds a section header table index link, whose
          interpretation depends on the section type.
         */
        public int sh_info;
        /*
          This member holds extra information, whose interpretation
          depends on the section type.
         */
        public long sh_addralign;
        /*
          Some sections have address alignment constraints.  If a
          section holds a doubleword, the system must ensure
          doubleword alignment for the entire section.  That is, the
          value of sh_addr must be congruent to zero, modulo the
          value of sh_addralign.  Only zero and positive integral
          powers of two are allowed.  The value 0 or 1 means that
          the section has no alignment constraints.
         */
        public long sh_entsize;
        /*
          Some sections hold a table of fixed-sized entries, such as
          a symbol table.  For such a section, this member gives the
          size in bytes for each entry.  This member contains zero
          if the section does not hold a table of fixed-size
          entries.
         */

        public transient byte[] contents;

        protected SectionHeader read(Elf elf, int index) throws IOException {
            if(elf.fileHeader.e_shoff == 0){
                return null;
            }
            elf.seekRAF(elf.fileHeader.e_shoff + elf.fileHeader.e_shentsize * index);

            if(elf.fileHeader.e_ident[FileHeader.EI_CLASS] == 1) {
                read32(elf);
            }else if(elf.fileHeader.e_ident[FileHeader.EI_CLASS] == 2){
                read64(elf);
            }else{
                throw new IOException();
            }
            return this;
        }

        private void read32(Elf elf) throws IOException {
            sh_name = elf.readInt();
            sh_type = elf.readInt();
            sh_flags = elf.readInt();
            sh_addr = elf.readInt();
            sh_offset = elf.readInt();
            sh_size = elf.readInt();
            sh_link = elf.readInt();
            sh_info = elf.readInt();
            sh_addralign = elf.readInt();
            sh_entsize = elf.readInt();
        }

        private void read64(Elf elf) throws IOException {
            sh_name = elf.readInt();
            sh_type = elf.readInt();
            sh_flags = elf.readLong();
            sh_addr = elf.readLong();
            sh_offset = elf.readLong();
            sh_size = elf.readLong();
            sh_link = elf.readInt();
            sh_info = elf.readInt();
            sh_addralign = elf.readLong();
            sh_entsize = elf.readLong();
        }
    }

    public static boolean ELF_TBSS_SPECIAL(Elf.SectionHeader sectionHeader, Elf.ProgramHeader segment){
        return (((sectionHeader.sh_flags & Elf.SectionHeader.SHF_TLS) != 0)
        && sectionHeader.sh_flags == SectionHeader.SHT_NOBITS
        && segment.p_type != ProgramHeader.TYPE.PT_TLS.TYPE);
    }

    public static boolean ELF_SECTION_IN_SEGMENT_STRICT(Elf.SectionHeader section, Elf.ProgramHeader segment){
        return ELF_SECTION_IN_SEGMENT(section, segment, true, true);
    }

    public static boolean ELF_SECTION_IN_SEGMENT(Elf.SectionHeader section, Elf.ProgramHeader segment){
        return ELF_SECTION_IN_SEGMENT(section, segment, true, false);
    }

    public static long ELF_SECTION_SIZE(Elf.SectionHeader section, Elf.ProgramHeader segment){
        return ELF_TBSS_SPECIAL(section, segment) ? 0 : section.sh_size;
    }

    public static boolean VALID_SYMBOL_NAME(Elf elf,SectionHeader section, long offset) throws IOException {
        return elf.readSectionData(section).length > offset;
    }

    public static boolean VALID_SYMBOL_NAME(Elf elf,int section, long offset) throws IOException {
        return elf.readSectionData(section).length > offset;
    }

    public static boolean ELF_SECTION_IN_SEGMENT(Elf.SectionHeader section, Elf.ProgramHeader segment, boolean check_vma, boolean strict){
        return ((
                /* Only PT_LOAD, PT_GNU_RELRO and PT_TLS segments can contain	\
                SHF_TLS sections.  */
                (((section.sh_flags & SectionHeader.SHF_TLS) != 0)
                && (segment.p_type == ProgramHeader.TYPE.PT_TLS.TYPE
                || segment.p_type == ProgramHeader.TYPE.PT_GNU_RELRO.TYPE
                || segment.p_type == ProgramHeader.TYPE.PT_GNU_RELRO.TYPE))
                /* PT_TLS segment contains only SHF_TLS sections, PT_PHDR no	\
                sections at all.  */
                || ((section.sh_flags & Elf.SectionHeader.SHF_TLS) == 0
                && segment.p_type != ProgramHeader.TYPE.PT_TLS.TYPE
                && segment.p_type != ProgramHeader.TYPE.PT_PHDR.TYPE))
                /* PT_LOAD and similar segments only have SHF_ALLOC sections.  */	
                && !((section.sh_flags & SectionHeader.SHF_ALLOC) == 0
                && (segment.p_type == ProgramHeader.TYPE.PT_LOAD.TYPE
                    || segment.p_type == ProgramHeader.TYPE.PT_DYNAMIC.TYPE
                    || segment.p_type == ProgramHeader.TYPE.PT_GNU_EH_FRAME.TYPE
                    || segment.p_type == ProgramHeader.TYPE.PT_GNU_STACK.TYPE
                    || segment.p_type == ProgramHeader.TYPE.PT_GNU_RELRO.TYPE
                    || (segment.p_type >= ProgramHeader.PT_GNU_MBIND_LO
                    && segment.p_type <= ProgramHeader.PT_GNU_MBIND_HI)))
               /* Any section besides one of type SHT_NOBITS must have file		\
                  offsets within the segment.  */					
               && (section.sh_type == SectionHeader.SHT_NOBITS
                   || ( section.sh_offset >= segment.p_offset
                   && (!(strict)						
                       || (section.sh_offset - segment.p_offset		
                       <= segment.p_filesz - 1))				
                   && ((section.sh_offset - segment.p_offset		
                    + ELF_SECTION_SIZE(section, segment))
                       <= segment.p_filesz)))				
                    /* SHF_ALLOC sections must have VMAs within the segment.  */		
               && (!(check_vma)							
                   || (section.sh_flags & SectionHeader.SHF_ALLOC) == 0
                   || (section.sh_addr >= segment.p_vaddr			
                   && (!(strict)						
                       || (section.sh_addr - segment.p_vaddr		
                       <= segment.p_memsz - 1))				
                   && ((section.sh_addr - segment.p_vaddr			
                    + ELF_SECTION_SIZE(section, segment))
                       <= segment.p_memsz)))					
               /* No zero size sections at start or end of PT_DYNAMIC nor		\
                  PT_NOTE.  */							
               && ((segment.p_type != ProgramHeader.TYPE.PT_DYNAMIC.TYPE
                && segment.p_type != ProgramHeader.TYPE.PT_NOTE.TYPE)
                   || section.sh_size != 0					
                   || segment.p_memsz == 0					
                   || ((section.sh_type == SectionHeader.SHT_NOBITS
                    || ( section.sh_offset > segment.p_offset
                        && (section.sh_offset - segment.p_offset		
                        < segment.p_filesz)))				
                   && ((section.sh_flags & SectionHeader.SHF_ALLOC) == 0
                       || (section.sh_addr > segment.p_vaddr		
                       && (section.sh_addr - segment.p_vaddr		
                           < segment.p_memsz))))));
    }

}
