package org.parker.elfutils.readelf;

import org.parker.elfutils.Elf;
import org.parker.elfutils.dynamic.ElfDynamic;
import org.parker.elfutils.rel.ElfRela;
import org.parker.elfutils.symtab.ElfSymbol;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadElfTree {

    public static DefaultMutableTreeNode readElf(Elf elf) throws IOException {
        DefaultMutableTreeNode elfNode = new DefaultMutableTreeNode("Elf", true);

        elfNode.add(readElfHeader(elf));
        elfNode.add(readElfSectionHeaders(elf));
        elfNode.add(readElfProgramHeaders(elf));
        elfNode.add(sectionToSegmentMapping(elf));

        return elfNode;
    }

    public static DefaultMutableTreeNode readElfHeader(Elf elf) {
        Elf.FileHeader fileHeader = elf.fileHeader;
        //StringBuilder sb = new StringBuilder();
        DefaultMutableTreeNode node = new DefaultMutableTreeNode("File Header");

        String s = "";
        for (int i = 0; i < 16; i++) {
            s += " " + String.format("%2s", Integer.toHexString(fileHeader.e_ident[i])).replace(' ', '0') + " ";
        }
        node.add(node("Magic", s));

        node.add(node("Class", (fileHeader.e_ident[Elf.FileHeader.EI_CLASS] == 1 ? "ELF32" : "ELF64")));
        node.add(node("Data", (fileHeader.e_ident[Elf.FileHeader.EI_DATA] == 1 ? "2's complement, little endian" : "2's complement, big endian")));
        node.add(node("Version", fileHeader.e_ident[Elf.FileHeader.EI_VERSION] == 1 ? "1 (current)" : "Other"));

        {
            StringBuilder sb = new StringBuilder();
            switch (fileHeader.e_ident[Elf.FileHeader.EI_OSABI]) {
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
            node.add(node("OS/ABI", node("ID", fileHeader.e_ident[Elf.FileHeader.EI_OSABI]), node("Name", sb.toString())));
        }

        node.add(node("ABI Version", fileHeader.e_ident[Elf.FileHeader.EI_ABIVERSION]));

        {
            StringBuilder sb = new StringBuilder();
            switch (fileHeader.e_type) {
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
                case (short) 0xFE00:
                    sb.append("ET_LOOS");
                    break;
                case (short) 0xFEFF:
                    sb.append("ET_HIOS");
                    break;
                case (short) 0xFF00:
                    sb.append("ET_LOPROC");
                    break;
                case (short) 0xFFFF:
                    sb.append("ET_HIPROC");
                    break;
                default:
                    sb.append("0x" + Long.toHexString(fileHeader.e_type));
                    break;
            }
            node.add(node("Type", sb.toString()));
        }

        node.add(node("Machine/ISA", Elf.FileHeader.ISA.fromValue(fileHeader.e_machine).name));

        node.add(node("Version", Long.toHexString(fileHeader.e_version)));

        node.add(node("Entry point address", Long.toHexString(fileHeader.e_entry)));

        node.add(node("Start of program headers(bytes into file)", fileHeader.e_phoff));

        node.add(node("Start of section headers(bytes into file)", fileHeader.e_shoff));

        node.add(node("Flags", Long.toHexString(fileHeader.e_flags)));

        node.add(node("Size of this header(bytes)", fileHeader.e_ehsize));

        node.add(node("Size of program headers(bytes)", fileHeader.e_phentsize));

        node.add(node("Number of program headers", fileHeader.e_phnum));

        node.add(node("Size of section headers(bytes)", fileHeader.e_shentsize));

        node.add(node("Number of seciton headers", fileHeader.e_shnum));

        node.add(node("Section header string table index", fileHeader.e_shstrndx));

        return node;
    }

    public static DefaultMutableTreeNode node(String name, Object value){
        DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(name);
        tmp.add(new DefaultMutableTreeNode(value));
        return tmp;
    }

    public static DefaultMutableTreeNode node(String name, List<DefaultMutableTreeNode> nodes){
        DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(name);
        if(nodes == null) return tmp;
        for(DefaultMutableTreeNode node: nodes) tmp.add(node);
        return tmp;
    }

    public static DefaultMutableTreeNode nodeSplit(String name, String value){
        DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(name);
        for(String sub: value.split("\n"))
        tmp.add(new DefaultMutableTreeNode(sub));
        return tmp;
    }
    public static List<DefaultMutableTreeNode> nodeSplit(String value){
        ArrayList<DefaultMutableTreeNode> nodes = new ArrayList<>();
        for(String sub: value.split("\n"))
            nodes.add(new DefaultMutableTreeNode(sub));
        return nodes;
    }

    public static List<DefaultMutableTreeNode> getChildren(DefaultMutableTreeNode parent){
        ArrayList<DefaultMutableTreeNode> nodes = new ArrayList<>();
        for(int i = 0; i < parent.getChildCount(); i ++)
            nodes.add((DefaultMutableTreeNode) parent.getChildAt(i));
        return nodes;
    }

    public static DefaultMutableTreeNode node(String name){
        DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(name);
        return tmp;
    }
    public static DefaultMutableTreeNode node(String name, DefaultMutableTreeNode... children){
        DefaultMutableTreeNode tmp = new DefaultMutableTreeNode(name);
        for(MutableTreeNode child: children)
        tmp.add(child);
        return tmp;
    }

    public static DefaultMutableTreeNode readElfSectionHeaders(Elf elf) throws IOException {

        DefaultMutableTreeNode node = new DefaultMutableTreeNode("Sections");

        if(elf.sectionHeaders != null && elf.sectionHeaders.length > 0) {
            //sb.append("\nSectionHeaders:\n");
            for (int i = 0; i < elf.sectionHeaders.length; i++) {

                Elf.SectionHeader sectionHeader = elf.sectionHeaders[i];

                DefaultMutableTreeNode iNode = new DefaultMutableTreeNode(i + ": " + elf.get_e_shstrndx_string(sectionHeader.sh_name));
                node.add(iNode);

                iNode.add(node("Name", elf.get_e_shstrndx_string(sectionHeader.sh_name)));

                {
                    StringBuilder sb = new StringBuilder();
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
                    iNode.add(node("Type", sb.toString()));
                }


                {
                    StringBuilder sb = new StringBuilder();
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
                    iNode.add(node("Flags", node("Name", sb.toString()), node("Hex", Long.toHexString(sectionHeader.sh_flags))));
                }
                iNode.add(node("Virtual Address in memory", "0x" + Long.toHexString(sectionHeader.sh_addr)));
                iNode.add(node("Align", sectionHeader.sh_addralign));
                iNode.add(node("Offset into file", "0x" + Long.toHexString(sectionHeader.sh_offset)));
                iNode.add(node("Size(bytes)", sectionHeader.sh_size));
                iNode.add(node("Link", sectionHeader.sh_link));
                iNode.add(node("Info", Long.toHexString(sectionHeader.sh_info)));
                iNode.add(node("Entry Size(bytes)", sectionHeader.sh_entsize));
                iNode.add(nodeSplit("Hex Dump", hexDump(elf.readSectionData(i), 0, elf.readSectionData(i).length)));
                iNode.add(node("Representation", readSection(elf, sectionHeader, elf.readSectionData(i))));
            }
        }else{
            node = node("No section Headers");
        }


        return node;
    }

    public static List<DefaultMutableTreeNode> readSection(Elf elf, Elf.SectionHeader section, byte[] data) throws IOException {
        switch (section.sh_type){
            case Elf.SectionHeader.SHT_STRTAB:
                return nodeSplit(stringDump(data, 0, data.length));
            case Elf.SectionHeader.SHT_SYMTAB:
            case Elf.SectionHeader.SHT_DYNSYM:
                return decodeSymbolSection(elf, section);
            case Elf.SectionHeader.SHT_REL:
            case Elf.SectionHeader.SHT_RELA:
                return decodeRelocationSection(elf, section);
            case Elf.SectionHeader.SHT_DYNAMIC:
                return decodeDynamicSection(elf, section);
            default:
                return null;
        }
    }

    public static MutableTreeNode readElfProgramHeaders(Elf elf) {

        DefaultMutableTreeNode node = new DefaultMutableTreeNode("Program Headers");

        if(elf.programHeaders != null && elf.programHeaders.length > 0) {
            //sb.append("\nProgramHeaders:\n");
            for (int i = 0; i < elf.programHeaders.length; i++) {

                Elf.ProgramHeader programHeader = elf.programHeaders[i];

                DefaultMutableTreeNode iNode = new DefaultMutableTreeNode(
                        i + ": " + Elf.ProgramHeader.TYPE.fromType(programHeader.p_type).name());
                node.add(iNode);

                {
                    StringBuilder sb = new StringBuilder();
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
                            sb.append(Elf.ProgramHeader.TYPE.fromType(programHeader.p_type).name());
                            break;
                    }
                    iNode.add(node("Type", node("Name", sb.toString()), node("ID", Long.toHexString(programHeader.p_type))));
                }

                iNode.add(node("Flags", Long.toHexString(programHeader.p_flags)));

                iNode.add(node("Offset(bytes)", "0x" + Long.toHexString(programHeader.p_offset)));

                iNode.add(node("Virtual Address(bytes)", "0x" + Long.toHexString(programHeader.p_vaddr)));

                iNode.add(node("Physical Address(bytes)", "0x" + Long.toHexString(programHeader.p_paddr)));

                iNode.add(node("Memory Size(bytes)", programHeader.p_memsz));

                iNode.add(node("File Size(bytes)", programHeader.p_filesz));

                iNode.add(node("Align Size(bytes)", programHeader.p_align));
            }
        }else{
            node = node("No Program Headers");
        }

        return node;
    }

    public static List<DefaultMutableTreeNode> decodeSymbolSection(Elf elf, Elf.SectionHeader section) throws IOException {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode("Dynamic Symbols");
        if((section.sh_type != Elf.SectionHeader.SHT_SYMTAB
                && section.sh_type != Elf.SectionHeader.SHT_DYNSYM))
            return null;

        if (section.sh_entsize == 0)
        {
            node.add(node("Section table for: " + elf.getSectionName(section) + " is null"));
            return getChildren(node);
        }

        int num_sysm = (int) (section.sh_size / section.sh_entsize);

        for(int si = 0; si < num_sysm; si ++){
            node.add(pDynamicSymbol(elf, section, si));
        }

        return getChildren(node);
    }

    public static List<DefaultMutableTreeNode> decodeRelocationSection(Elf elf, Elf.SectionHeader section) throws IOException {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode("Relocation Entries");
        if((section.sh_type != Elf.SectionHeader.SHT_REL
                && section.sh_type != Elf.SectionHeader.SHT_RELA))
            return null;

        if (section.sh_entsize == 0)
        {
            node.add(node("Relocation table for: " + elf.getSectionName(section) + " is null"));
            return getChildren(node);
        }

        int num_sysm = (int) (section.sh_size / section.sh_entsize);

        for(int si = 0; si < num_sysm; si ++){
            node.add(pRelocationEntry(elf, section, si));
        }

        return getChildren(node);
    }

    public static List<DefaultMutableTreeNode> decodeDynamicSection(Elf elf, Elf.SectionHeader section) throws IOException {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode("Dynamic Entries");
        if(section.sh_type != Elf.SectionHeader.SHT_DYNAMIC)
            return null;

        if (section.sh_entsize == 0)
        {
            node.add(node("Dynamic table for: " + elf.getSectionName(section) + " is null"));
            return getChildren(node);
        }

        int num_sysm = (int) (section.sh_size / section.sh_entsize);

        for(int si = 0; si < num_sysm; si ++){
            node.add(pDynamicEntry(elf, section, si));
        }

        return getChildren(node);
    }

    public static DefaultMutableTreeNode pDynamicEntry(Elf elf, Elf.SectionHeader section, int index) throws IOException {

        ElfDynamic dynamic = ElfDynamic.fromElf(elf, section, index);

        DefaultMutableTreeNode me = new DefaultMutableTreeNode(index + ": " + dynamic.getName(elf, section));
        //me.add(node("Tag", index));
        me.add(node("Tag", "0x" + Long.toHexString(dynamic.getTag())));
        me.add(node("Type", "("  + ElfDynamic.DT.fromID((int) dynamic.getTag()).name()+ ")"));
        me.add(node("Name/Value",dynamic.getName(elf, section)));
        return me;
    }

    public static DefaultMutableTreeNode pDynamicSymbol(Elf elf, Elf.SectionHeader section, int index) throws IOException {

        ElfSymbol symbol = ElfSymbol.fromElf(elf, section, index);

        DefaultMutableTreeNode me = new DefaultMutableTreeNode(index + ": " + symbol.getName());
        me.add(node("Index", index));
        me.add(node("Value", "0x" + Long.toHexString(symbol.getValue())));
        me.add(node("Size", symbol.getSize()));
        me.add(node("Type", symbol.getType().name()));
        me.add(node("Visibility", symbol.getVisibility().name()));
        me.add(node("Ndx", symbol.getNDX() == 0 ? "UND" : symbol.getNDX()));
        me.add(node("Name",symbol.getName()));
        return me;
    }

    public static DefaultMutableTreeNode pRelocationEntry(Elf elf, Elf.SectionHeader section, int index) throws IOException {

        ElfRela reloc;

        switch (section.sh_type){
            case Elf.SectionHeader.SHT_REL:
                reloc = ElfRela.relFromElf(elf, section, index);
                break;
            case Elf.SectionHeader.SHT_RELA:
                reloc = ElfRela.relaFromElf(elf, section, index);
                break;
            default:
                return null;
        }
        ElfSymbol symbol = ElfSymbol.fromElf(elf, elf.getSection(section.sh_link), (int) reloc.getSym());

        DefaultMutableTreeNode me = new DefaultMutableTreeNode(index);
        me.add(node("Offset", "0x" + (elf.is64Bit() ? Long.toHexString(reloc.getOffset()) : Integer.toHexString((int) reloc.getOffset()))));
        me.add(node("Info", "0x" + (elf.is64Bit() ? Long.toHexString(reloc.getInfo()) : Integer.toHexString((int) reloc.getInfo()))));
        me.add(node("Type", reloc.getRelocType(elf)));
        me.add(node("Sym. Value", "0x" + (elf.is64Bit() ? Long.toHexString(symbol.getValue()) : Integer.toHexString((int)symbol.getValue()))));

        me.add(node("Sym. Name + Addend", symbol.getName() + (symbol.getName().trim().equals("") ? "" : " + ") + reloc.getAddend()));
        return me;
    }

    public static DefaultMutableTreeNode sectionToSegmentMapping(Elf elf){
        DefaultMutableTreeNode node = new DefaultMutableTreeNode("Section to Segment mapping");
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("Segment Sections");
        node.add(node1);

        String format = "%" + (int)Math.ceil(Math.log10(elf.programHeaders.length)) + "d";

        for(int i = 0; i < elf.programHeaders.length; i ++){
            StringBuilder sb = new StringBuilder();
            Elf.ProgramHeader programHeader = elf.programHeaders[i];

            for(int s = 0; s < elf.sectionHeaders.length; s ++){
                Elf.SectionHeader sectionHeader = elf.sectionHeaders[s];

                if(!Elf.ELF_TBSS_SPECIAL(sectionHeader, programHeader)
                        && Elf.ELF_SECTION_IN_SEGMENT_STRICT(sectionHeader, programHeader)) {
                    sb.append(elf.get_e_shstrndx_string(sectionHeader.sh_name) + " ");
                }
            }
            node1.add(node(String.format(format, i).replace(' ', '0') + " "
                    + Elf.ProgramHeader.TYPE.fromType(programHeader.p_type).name(), sb.toString()));
        }


        return node;
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

