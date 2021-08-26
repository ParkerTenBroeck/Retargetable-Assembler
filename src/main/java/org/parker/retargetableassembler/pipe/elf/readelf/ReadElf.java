package org.parker.retargetableassembler.pipe.elf.readelf;

import org.parker.retargetableassembler.pipe.elf.Elf;

public class ReadElf {

    public static String readElf(Elf elf) {
        StringBuilder sb = new StringBuilder();

        sb.append(readElfHeader(elf));
        sb.append(readElfSectionHeaders(elf));
        sb.append(readElfProgramHeaders(elf));
        sb.append(sectionToSegmentMapping(elf));

        sb.append("Hex dump of section '" + elf.get_e_shstrndx_string(elf.sectionHeaders[elf.fileHeader.e_shstrndx].sh_name) + "'\n");
        sb.append(hexDump(elf.section_data[elf.fileHeader.e_shstrndx], 0, elf.section_data[elf.fileHeader.e_shstrndx].length));

        sb.append("String dump of section '" + elf.get_e_shstrndx_string(elf.sectionHeaders[elf.fileHeader.e_shstrndx].sh_name) + "'\n");
        sb.append(stringDump(elf.section_data[elf.fileHeader.e_shstrndx], 0, elf.section_data[elf.fileHeader.e_shstrndx].length));

        return sb.toString();
        //return super.toString();
    }

    public static String readElfHeader(Elf elf) {
        Elf.FileHeader fileHeader = elf.fileHeader;
        StringBuilder sb = new StringBuilder();

        sb.append("FileHeader:\n" );

        sb.append("  Magic:");
        for(int i = 0; i < 16; i ++){
            sb.append(" "+ String.format("%2s", Integer.toHexString(fileHeader.e_ident[i])).replace(' ', '0') + " ");
        }
        sb.append("\n  Class:                             " + (fileHeader.e_ident[fileHeader.EI_CLASS] == 1 ? "ELF32":"ELF64"));
        sb.append("\n  Data:                              " + (fileHeader.e_ident[fileHeader.EI_DATA] == 1 ? "2's complement, little endian": "2's complement, big endian"));
        sb.append("\n  Version:                           ");
        if(fileHeader.e_ident[fileHeader.EI_VERSION] == 1) {
            sb.append("1 (current)");
        }
        sb.append("\n  OS/ABI:                            ");
        switch (fileHeader.e_ident[fileHeader.EI_OSABI]){
            case 0x00:
                sb.append("UNIX - System V");
                break;
            case 0x01:
                sb.append("HP-UX");
                break;
            case 0x02:
                sb.append("NetBSD");
                break;
            case 0x03:
                sb.append("Linux");
                break;
            case 0x04:
                sb.append("GNU Hurd");
                break;
            case 0x06:
                sb.append("Solaris");
                break;
            case 0x07:
                sb.append("AIX");
                break;
            case 0x08:
                sb.append("IRIX");
                break;
            case 0x09:
                sb.append("FreeBSD");
                break;
            case 0x0A:
                sb.append("True64");
                break;
            case 0x0B:
                sb.append("Novell Modesto");
                break;
            case 0x0C:
                sb.append("OpenBSD");
                break;
            case 0x0D:
                sb.append("OpenVMS");
                break;
            case 0x0E:
                sb.append("NonStop Kernel");
                break;
            case 0x0F:
                sb.append("AROS");
                break;
            case 0x10:
                sb.append("Fenix OS");
                break;
            case 0x11:
                sb.append("CloudABI");
                break;
            case 0x12:
                sb.append("Stratus Technologies OpenVOS");
                break;
        }
        sb.append("\n  ABI Version:                       ");
        sb.append(fileHeader.e_ident[fileHeader.EI_ABIVERSION]);

        sb.append("\n  Type:                              ");
        switch (fileHeader.e_type){
            case 0x00:
                sb.append("ET_NONE");
                break;
            case 0x01:
                sb.append("ET_REL");
                break;
            case 0x02:
                sb.append("ET_EXEC");
                break;
            case 0x03:
                sb.append("ET_DYN");
                break;
            case 0x04:
                sb.append("ET_CORE");
                break;
            case (short)0xFE00:
                sb.append("ET_LOOS");
                break;
            case (short)0xFEFF:
                sb.append("ET_HIOS");
                break;
            case (short)0xFF00:
                sb.append("ET_LOPROC");
                break;
            case (short)0xFFFF:
                sb.append("ET_HIPROC");
                break;
            default:
                sb.append("0x" + Long.toHexString(fileHeader.e_type));
                break;
        }

        sb.append("\n  Machine:                           ");

        sb.append("\n  Version:                           ");
        sb.append("0x" + Long.toHexString(fileHeader.e_version));

        sb.append("\n  Entry point address:               ");
        sb.append("0x" + Long.toHexString(fileHeader.e_entry));

        sb.append("\n  Start of program headers:          ");
        sb.append(fileHeader.e_phoff + " (bytes into file)");

        sb.append("\n  Start of section headers:          ");
        sb.append(fileHeader.e_shoff + " (bytes into file)");

        sb.append("\n  Flags:                             ");
        sb.append("0x" + Long.toHexString(fileHeader.e_flags));

        sb.append("\n  Size of this header:               ");
        sb.append(fileHeader.e_ehsize + " (bytes)");

        sb.append("\n  Size of program headers:           ");
        sb.append(fileHeader.e_phentsize + " (bytes)");

        sb.append("\n  Number of program headers:         ");
        sb.append(fileHeader.e_phnum);

        sb.append("\n  Size of section headers:           ");
        sb.append(fileHeader.e_shentsize + " (bytes)");

        sb.append("\n  number of section headers:         ");
        sb.append(fileHeader.e_shnum);

        sb.append("\n  Section header string table index: ");
        sb.append(fileHeader.e_shstrndx);

        return sb.toString();
    }

    public static String readElfSectionHeaders(Elf elf) {

        StringBuilder sb = new StringBuilder();

        if(elf.sectionHeaders != null && elf.sectionHeaders.length > 0) {
            sb.append("\nSectionHeaders:\n");
            for (int i = 0; i < elf.sectionHeaders.length; i++) {

                Elf.SectionHeader sectionHeader = elf.sectionHeaders[i];

                sb.append("  index " + i + ":\n");

                sb.append("    Name:                         " + elf.get_e_shstrndx_string(sectionHeader.sh_name));

                sb.append("\n    Type:                         ");

                switch (sectionHeader.sh_type) {
                    case 0x0:
                        sb.append("SHT_NULL");
                        break;
                    case 0x1:
                        sb.append("SHT_PROGBITS(Program data)");
                        break;
                    case 0x2:
                        sb.append("SHT_SYMTAB(Symbol table)");
                        break;
                    case 0x3:
                        sb.append("SHT_STRTAB(String table)");
                        break;
                    case 0x4:
                        sb.append("SHT_RELA(Relocation entries with addends)");
                        break;
                    case 0x5:
                        sb.append("SHT_HASH(Symbol hash table)");
                        break;
                    case 0x6:
                        sb.append("SHT_DYNAMIC(Dynamic linking information)");
                        break;
                    case 0x7:
                        sb.append("SHT_NOTE(Notes)");
                        break;
                    case 0x8:
                        sb.append("SHT_NOBITS(Program space with no data(bss))");
                        break;
                    case 0x9:
                        sb.append("SHT_REL(Relocation entries, no addends)");
                        break;
                    case 0xA:
                        sb.append("SHT_SHLIB(Reserved)");
                        break;
                    case 0xB:
                        sb.append("SHT_DYNSYM(Dynamic linker symbol table)");
                        break;
                    case 0xE:
                        sb.append("SHT_INIT_ARRAY(Array of constructors)");
                        break;
                    case 0xF:
                        sb.append("SHT_FINI_ARRAY(Array of destructors)");
                        break;
                    case 0x10:
                        sb.append("SHT_PREINIT_ARRAY(Array of pre-constructors)");
                        break;
                    case 0x11:
                        sb.append("SHT_GROUP(Section group)");
                        break;
                    case 0x12:
                        sb.append("SHT_SYMTAB_SHNDX(Extended section indices)");
                        break;
                    case 0x13:
                        sb.append("SHT_NUM(Number of defied types)");
                        break;
                    case 0x60000000:
                        sb.append("SHT_LOOS(Start OS-specific)");
                        break;
                    }


                    sb.append("\n    Flags:                        ");

                    if (sectionHeader.sh_flags == 0x0) {
                        sb.append("0x0");
                    }
                    if ((0x1 & sectionHeader.sh_flags) > 0) {
                        sb.append("SHF_WRITE(Writable) ");
                    }
                    if ((0x2 & sectionHeader.sh_flags) > 0) {
                        sb.append("SHF_ALLOC(Occupies memory during execution) ");
                    }
                    if ((0x4 & sectionHeader.sh_flags) > 0) {
                        sb.append("SHF_EXECINSTR(Executable) ");
                    }
                    if ((0x10 & sectionHeader.sh_flags) > 0) {
                        sb.append("SHF_MERGE(Might be merged) ");
                    }
                    if ((0x20 & sectionHeader.sh_flags) > 0) {
                        sb.append("SHF_STRINGS(Contains null-terminated strings) ");
                    }
                    if ((0x40 & sectionHeader.sh_flags) > 0) {
                        sb.append("SHF_INFO_LINK(Contains SHT index) ");
                    }
                    if ((0x80 & sectionHeader.sh_flags) > 0) {
                        sb.append("SHF_LINK_ORDER(Preserve order after combining) ");
                    }
                    if ((0x100 & sectionHeader.sh_flags) > 0) {
                        sb.append("SHF_OS_NONCONFORMING(Non-standard OS specific handling required) ");
                    }
                    if ((0x200 & sectionHeader.sh_flags) > 0) {
                        sb.append("SHF_GROUP(Section is member of a group) ");
                    }
                    if ((0x400 & sectionHeader.sh_flags) > 0) {
                        sb.append("SHF_TLS(Section hold thread-local data) ");
                    }
                    if ((0x0FF00000 & sectionHeader.sh_flags) > 0) {
                        sb.append("SHF_MASKOS(OS-specific) ");
                    }
                    if ((0xF0000000 & sectionHeader.sh_flags) > 0) {
                        sb.append("SHF_MASKPROC(Processor-specific) ");
                    }
                    if ((0x4000000 & sectionHeader.sh_flags) > 0) {
                        sb.append("SHF_ORDERED(Special ordering requirement) ");
                    }
                    if ((0x8000000 & sectionHeader.sh_flags) > 0) {
                        sb.append("SHF_EXCLUDE(Section is excluded unless referenced or allocated) ");
                    }

                    sb.append("\n    Virtual Address in memory:    0x" + Long.toHexString(sectionHeader.sh_addr));
                    sb.append("\n    Align:                        " + sectionHeader.sh_addralign);
                    sb.append("\n    Offset into file:             0x" + Long.toHexString(sectionHeader.sh_offset));
                    sb.append("\n    Size:                         " + sectionHeader.sh_size + " (bytes)");
                    sb.append("\n    Link:                         " + sectionHeader.sh_link);
                    sb.append("\n    Info:                         " + Long.toHexString(sectionHeader.sh_info));
                    sb.append("\n    Entry Size:                   " + sectionHeader.sh_entsize);
                    sb.append("\n");
                }
            }else{
                sb.append("\nThere are no section headers in this file\n");
            }

            return sb.toString();
        }

    public static String readElfProgramHeaders(Elf elf) {

        StringBuilder sb = new StringBuilder();

        if(elf.programHeaders != null && elf.programHeaders.length > 0) {
            sb.append("\nProgramHeaders:\n");
            for (int i = 0; i < elf.programHeaders.length; i++) {

                Elf.ProgramHeader programHeader = elf.programHeaders[i];

                sb.append("  index " + i + ":\n");

                sb.append("    Type:                ");

                switch (programHeader.p_type) {
                    case 0x0:
                        sb.append("PT_NULL");
                        break;
                    case 0x1:
                        sb.append("PT_LOAD(Loadable segment)");
                        break;
                    case 0x2:
                        sb.append("TP_DYNAMIC(Dynamic linking information)");
                        break;
                    case 0x3:
                        sb.append("PT_INTERP(Interpreter information)");
                        break;
                    case 0x4:
                        sb.append("PT_NOTE(Auxiliary information)");
                        break;
                    case 0x5:
                        sb.append("PT_SHLIB(reserved)");
                        break;
                    case 0x6:
                        sb.append("PT_PHDR(segment containing program header table itself)");
                        break;
                    case 0x7:
                        sb.append("PT_TLS(Thread-Local Storage template)");
                        break;
                    case 0x60000000:
                        sb.append("PT_LOOS");
                        break;
                    case 0x6FFFFFFF:
                        sb.append("PT_HIOS");
                        break;
                    case 0x70000000:
                        sb.append("PT_LOPROC");
                        break;
                    case 0x7FFFFFFF:
                        sb.append("PT_HIPROC");
                        break;
                    default:
                        sb.append("0x" + Long.toHexString(programHeader.p_type));
                        break;
                }

                sb.append("\n    Flags:               ");
                sb.append("0x" + Long.toHexString(programHeader.p_flags));

                sb.append("\n    Offset:              ");
                sb.append("0x" + Long.toHexString(programHeader.p_offset) + " (bytes)");

                sb.append("\n    Virtual Address:     ");
                sb.append("0x" + Long.toHexString(programHeader.p_vaddr) + " (bytes)");

                sb.append("\n    Physical Address:    ");
                sb.append("0x" + Long.toHexString(programHeader.p_paddr) + " (bytes)");

                sb.append("\n    Memory Size:         ");
                sb.append(programHeader.p_memsz + " (bytes)");

                sb.append("\n    File Size:           ");
                sb.append(programHeader.p_filesz + " (bytes)");

                sb.append("\n    Align Size:          ");
                sb.append(programHeader.p_align);
                sb.append("\n");
            }
        }else{
            sb.append("\nThere are no program headers in this file\n");
        }

        return sb.toString();
    }

    public static String sectionToSegmentMapping(Elf elf){
        StringBuilder sb = new StringBuilder();

        sb.append("Section to Segment mapping:\n");
        sb.append("  Segment Sections...\n");

        String format = "%" + (int)Math.ceil(Math.log10(elf.programHeaders.length)) + "d";



        for(int i = 0; i < elf.programHeaders.length; i ++){
            sb.append("    " + String.format(format, i).replace(' ', '0') + "    ");

            Elf.ProgramHeader programHeader = elf.programHeaders[i];
            long off = programHeader.p_offset;

            for(int s = 0; s < elf.sectionHeaders.length; s ++){

                Elf.SectionHeader sectionHeader = elf.sectionHeaders[s];

                if(sectionHeader.sh_offset == off){

                    long size = 0;
                    while(size < programHeader.p_filesz){
                        if(sectionHeader.sh_type != 0) {
                            sb.append(elf.get_e_shstrndx_string(sectionHeader.sh_name) + " ");
                        }
                        size += sectionHeader.sh_size;

                        s++;
                        if( s >= elf.sectionHeaders.length ){
                            break;
                        }
                        sectionHeader = elf.sectionHeaders[s];
                    }
                }
            }

            sb.append("\n");
        }


        return sb.toString();
    }

    public static String hexDump(byte[] data, int offset, int size){
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < size; i += 16) {
            sb.append("  0x" + String.format("%8X", offset + i).replace(' ', '0') + " ");

            int rowSize = 16;
            if(size - i < 16){
                rowSize = size - i;
            }

            for (int j = 0; j < rowSize; j++) {
                sb.append(String.format("%02X", data[offset + j + i]) + " ");
            }
            if(rowSize < 16){
                for(int r = 0; r < (16 - rowSize); r++) {
                    sb.append("   ");
                }
            }

            for (int j = 0; j < rowSize; j++) {
                if (data[offset + j] == 0 || data[offset + j + i] == '\n') {
                    sb.append('.');
                } else {
                    sb.append((char) data[offset + j + i]);
                }
            }
            sb.append('\n');
        }

        return sb.toString();
    }

    public static String stringDump(byte[] data, int offset, int size) {
        StringBuilder sb = new StringBuilder();

        int strOffset = 0;
        while (strOffset < size) {

            int strSize = 0;
            while ((strOffset + strSize) < size && data[offset + strSize + strOffset] != 0) {
                strSize++;
            }
            if(strSize > 0) {
                sb.append("  0x" + String.format("%8X", offset + strOffset).replace(' ', '0') + " ");
                sb.append(" " + new String(data, offset + strOffset, strSize) + "\n");
            }
            strOffset = strOffset + strSize + 1;
        }

        return sb.toString();
    }

    }

