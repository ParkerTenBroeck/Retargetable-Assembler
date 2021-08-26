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

    byte[] readSectionData(int section) throws IOException {

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

    public class FileHeader{
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

            raf.seek(0);
            raf.read(this.e_ident);
            fileHeader = this;//this is so things work bla bla bla
            if(e_ident[EI_CLASS] == 1) {
                read32(elf);
            }else if(e_ident[EI_CLASS] == 2){
                read64(elf);
            }else{
                throw new IOException();
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

}
