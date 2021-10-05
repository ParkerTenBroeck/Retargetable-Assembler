package org.parker.retargetableassembler.pipe.elf;

import org.parker.retargetableassembler.pipe.elf.readelf.ReadElf;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

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
        return data;
    }

    public void seek(long address) throws IOException {
        this.raf.seek(address);
    }

    public byte readByte() throws IOException {
        return raf.readByte();
    }

    public short readShort() throws IOException {
        if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 1){//little endien
            return Short.reverseBytes(raf.readShort());
        }else if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 2){//big endien
            return raf.readShort();
        }else{
            throw new IOException();
        }
    }

    public int readInt() throws IOException{
        if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 1){//little endien
            return Integer.reverseBytes(raf.readInt());
        }else if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 2){//big endien
            return raf.readInt();
        }else{
            throw new IOException();
        }
    }

    public long readLong() throws IOException{
        if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 1){//little endien
            return Long.reverseBytes(raf.readLong());
        }else if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 2){//big endien
            return raf.readLong();
        }else{
            throw new IOException();
        }
    }

    public void writeByte(byte b) throws IOException {
        raf.writeByte(b);
    }

    public void writeShort(short s) throws IOException {
        if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 1){//little endien
             raf.writeShort(Short.reverseBytes(s));
        }else if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 2){//big endien
            raf.writeShort(s);
        }else{
            throw new IOException();
        }
    }

    public void writeInt(int i) throws IOException{
        if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 1){//little endien
            raf.writeInt(Integer.reverseBytes(i));
        }else if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 2){//big endien
            raf.writeInt(i);
        }else{
            throw new IOException();
        }
    }

    public void writeLong(long l) throws IOException{
        if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 1){//little endien
            raf.writeLong(Long.reverseBytes(l));
        }else if(this.fileHeader.e_ident[FileHeader.EI_DATA] == 2){//big endien
            raf.writeLong(l);
        }else{
            throw new IOException();
        }
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
        public static int SHT_NULL = 0x0;
        public static int SHT_PROGBITS = 0x1;
        public static int SHT_SYMTAB = 0x2;
        public static int SHT_STRTAB = 0x3;
        public static int SHT_RELA = 0x4;
        public static int SHT_HASH = 0x5;
        public static int SHT_DYNAMIC = 0x6;
        public static int SHT_NOTE = 0x7;
        public static int SHT_NOBITS = 0x8; /* Section occupies no space in file */
        public static int SHT_REL = 0x9;    /* Relocation entries, no addends */
        public static int SHT_SHLIB = 0xA;  /* Reserved, unspecified semantics */
        public static int SHT_DYNSYM = 0xB; /* Dynamic linking symbol table */
        public static int SHT_INIT_ARRAY = 0xE;
        public static int SHT_FINI_ARRAY = 0xF;
        public static int SHT_PREINIT_ARRAY = 0x10;
        public static int SHT_GROUP = 0x11;
        public static int SHT_SYMTAB_SHNDX = 0x12;
        public static int SHT_NUM = 0x13;
        public static int SHT_LOOS = 0x60000000;
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

        protected SectionHeader read(Elf elf, int index) throws IOException {
            if(elf.fileHeader.e_shoff == 0){
                return null;
            }
            elf.seek(elf.fileHeader.e_shoff + elf.fileHeader.e_shentsize * index);

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
