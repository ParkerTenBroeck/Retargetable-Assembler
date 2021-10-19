package org.parker.elfutils.dynamic;

import org.parker.elfutils.Elf;

import java.io.DataInput;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.parker.elfutils.Elf.FileHeader.*;

@SuppressWarnings("unused")
public class ElfDynamic {
    /* Dynamic section tags.  */;
    public static final int DT_NULL = 0;
    public static final int DT_NEEDED = 1;
    public static final int DT_PLTRELSZ = 2;
    public static final int DT_PLTGOT = 3;
    public static final int DT_HASH = 4;
    public static final int DT_STRTAB = 5;
    public static final int DT_SYMTAB = 6;
    public static final int DT_RELA = 7;
    public static final int DT_RELASZ = 8;
    public static final int DT_RELAENT = 9;
    public static final int DT_STRSZ = 10;
    public static final int DT_SYMENT = 11;
    public static final int DT_INIT = 12;
    public static final int DT_FINI = 13;
    public static final int DT_SONAME = 14;
    public static final int DT_RPATH = 15;
    public static final int DT_SYMBOLIC = 16;
    public static final int DT_REL = 17;
    public static final int DT_RELSZ = 18;
    public static final int DT_RELENT = 19;
    public static final int DT_PLTREL = 20;
    public static final int DT_DEBUG = 21;
    public static final int DT_TEXTREL = 22;
    public static final int DT_JMPREL = 23;
    public static final int DT_BIND_NOW = 24;
    public static final int DT_INIT_ARRAY = 25;
    public static final int DT_FINI_ARRAY = 26;
    public static final int DT_INIT_ARRAYSZ = 27;
    public static final int DT_FINI_ARRAYSZ = 28;
    public static final int DT_RUNPATH = 29;
    public static final int DT_FLAGS = 30;
    public static final int DT_ENCODING = 32;
    public static final int DT_PREINIT_ARRAY = 32;
    public static final int DT_PREINIT_ARRAYSZ = 33;
    public static final int DT_SYMTAB_SHNDX = 34;

    public enum DT{
        NULL(0),
        NEEDED(1),
        PLTRELSZ(2),
        PLTGOT(3),
        HASH(4),
        STRTAB(5),
        SYMTAB(6),
        RELA(7),
        RELASZ(8),
        RELAENT(9),
        STRSZ(10),
        SYMENT(11),
        INIT(12),
        FINI(13),
        SONAME(14),
        RPATH(15),
        SYMBOLIC(16),
        REL(17),
        RELSZ(18),
        RELENT(19),
        PLTREL(20),
        DEBUG(21),
        TEXTREL(22),
        JMPREL(23),
        BIND_NOW(24),
        INIT_ARRAY(25),
        FINI_ARRAY(26),
        INIT_ARRAYSZ(27),
        FINI_ARRAYSZ(28),
        RUNPATH(29),
        FLAGS(30),
        ENCODING(32),
        PREINIT_ARRAY(32),
        PREINIT_ARRAYSZ(33),
        SYMTAB_SHNDX(34);

        public final int ID;
        DT(int id){this.ID = id;}
        public static DT fromID(int id){
            for(DT dt:DT.values())if(dt.ID == id) return dt;
            return NULL;
        }
    }

    /*  Note, the Oct 4, 1999 draft of the ELF ABI changed the values;
    for DT_LOOS and DT_HIOS.  Some implementations however, use;
    values outside of the new range (see below). =  */
    public static final int OLD_DT_LOOS = 0x60000000;
    public static final int DT_LOOS = 0x6000000d;
    public static final int DT_HIOS = 0x6ffff000;
    public static final int OLD_DT_HIOS = 0x6fffffff;
    /* The next 2 dynamic tag ranges, integer value range (DT_VALRNGLO to;
       DT_VALRNGHI) and virtual address range (DT_ADDRRNGLO to DT_ADDRRNGHI),;
       are used on Solaris.  We support them everywhere.  Note these values;
       lie outside of the (new) range for OS specific values.  This is a;
       deliberate special case and we maintain it for backwards compatability.;
     */
    public static final int DT_VALRNGLO = 0x6ffffd00;
    public static final int DT_GNU_FLAGS_1 = 0x6ffffdf4;
    public static final int DT_GNU_PRELINKED = 0x6ffffdf5;
    public static final int DT_GNU_CONFLICTSZ = 0x6ffffdf6;
    public static final int DT_GNU_LIBLISTSZ = 0x6ffffdf7;
    public static final int DT_CHECKSUM = 0x6ffffdf8;
    public static final int DT_PLTPADSZ = 0x6ffffdf9;
    public static final int DT_MOVEENT = 0x6ffffdfa;
    public static final int DT_MOVESZ = 0x6ffffdfb;
    public static final int DT_FEATURE = 0x6ffffdfc;
    public static final int DT_POSFLAG_1 = 0x6ffffdfd;
    public static final int DT_SYMINSZ = 0x6ffffdfe;
    public static final int DT_SYMINENT = 0x6ffffdff;
    public static final int DT_VALRNGHI = 0x6ffffdff;
    public static final int DT_ADDRRNGLO = 0x6ffffe00;
    public static final int DT_GNU_HASH = 0x6ffffef5;
    public static final int DT_TLSDESC_PLT = 0x6ffffef6;
    public static final int DT_TLSDESC_GOT = 0x6ffffef7;
    public static final int DT_GNU_CONFLICT = 0x6ffffef8;
    public static final int DT_GNU_LIBLIST = 0x6ffffef9;
    public static final int DT_CONFIG = 0x6ffffefa;
    public static final int DT_DEPAUDIT = 0x6ffffefb;
    public static final int DT_AUDIT = 0x6ffffefc;
    public static final int DT_PLTPAD = 0x6ffffefd;
    public static final int DT_MOVETAB = 0x6ffffefe;
    public static final int DT_SYMINFO = 0x6ffffeff;
    public static final int DT_ADDRRNGHI = 0x6ffffeff;
    public static final int DT_RELACOUNT = 0x6ffffff9;
    public static final int DT_RELCOUNT = 0x6ffffffa;
    public static final int DT_FLAGS_1 = 0x6ffffffb;
    public static final int DT_VERDEF = 0x6ffffffc;
    public static final int DT_VERDEFNUM = 0x6ffffffd;
    public static final int DT_VERNEED = 0x6ffffffe;
    public static final int DT_VERNEEDNUM = 0x6fffffff;
    /* This tag is a GNU extension to the Solaris version scheme.  */
    public static final int DT_VERSYM = 0x6ffffff0;
    public static final int DT_LOPROC = 0x70000000;
    public static final int DT_HIPROC = 0x7fffffff;
/* These section tags are used on Solaris.  We support them;
   everywhere, and hope they do not conflict.  */
    public static final int DT_AUXILIARY = 0x7ffffffd;
    public static final int DT_USED = 0x7ffffffe;
    public static final int DT_FILTER = 0x7fffffff;
    /* Values used in DT_FEATURE .dynamic entry.  */
    public static final int DTF_1_PARINIT = 0x00000001;
/* From;
   http://docs.sun.com:80/ab2/coll.45.13/LLM/@Ab2PageView/21165?Ab2Lang=C&Ab2Enc=iso-8859-1;
   DTF_1_CONFEXP is the same as DTF_1_PARINIT. It is a typo. The value;
   defined here is the same as the one in <sys/link.h> on Solaris 8.  */
    public static final int DTF_1_CONFEXP = 0x00000002;
    /* Flag values used in the DT_POSFLAG_1 .dynamic entry. =  */
    public static final int DF_P1_LAZYLOAD = 0x00000001;
    public static final int DF_P1_GROUPPERM = 0x00000002;
    /* Flag value in the DT_GNU_FLAGS_1 /dynamic entry.  */
    public static final int DF_GNU_1_UNIQUE = 0x00000001;
    /* Flag value in in the DT_FLAGS_1 .dynamic entry.  */
    public static final int DF_1_NOW = 0x00000001;
    public static final int DF_1_GLOBAL = 0x00000002;
    public static final int DF_1_GROUP = 0x00000004;
    public static final int DF_1_NODELETE = 0x00000008;
    public static final int DF_1_LOADFLTR = 0x00000010;
    public static final int DF_1_INITFIRST = 0x00000020;
    public static final int DF_1_NOOPEN = 0x00000040;
    public static final int DF_1_ORIGIN = 0x00000080;
    public static final int DF_1_DIRECT = 0x00000100;
    public static final int DF_1_TRANS = 0x00000200;
    public static final int DF_1_INTERPOSE = 0x00000400;
    public static final int DF_1_NODEFLIB = 0x00000800;
    public static final int DF_1_NODUMP = 0x00001000;
    public static final int DF_1_CONFALT = 0x00002000;
    public static final int DF_1_ENDFILTEE = 0x00004000;
    public static final int DF_1_DISPRELDNE = 0x00008000;
    public static final int DF_1_DISPRELPND = 0x00010000;
    public static final int DF_1_NODIRECT = 0x00020000;
    public static final int DF_1_IGNMULDEF = 0x00040000;
    public static final int DF_1_NOKSYMS = 0x00080000;
    public static final int DF_1_NOHDR = 0x00100000;
    public static final int DF_1_EDITED = 0x00200000;
    public static final int DF_1_NORELOC = 0x00400000;
    public static final int DF_1_SYMINTPOSE = 0x00800000;
    public static final int DF_1_GLOBAUDIT = 0x01000000;
    public static final int DF_1_SINGLETON = 0x02000000;
    public static final int DF_1_STUB = 0x04000000;
    public static final int DF_1_PIE = 0x08000000;
    public static final int DF_1_KMOD = 0x10000000;
    public static final int DF_1_WEAKFILTER = 0x20000000;
    public static final int DF_1_NOCOMMON = 0x40000000;
    /* Flag values for the DT_FLAGS entry. = */
    public static final int DF_ORIGIN = (1 << 0);
    public static final int DF_SYMBOLIC = (1 << 1);
    public static final int DF_TEXTREL = (1 << 2);
    public static final int DF_BIND_NOW = (1 << 3);
    public static final int DF_STATIC_TLS = (1 << 4);

    long d_tag;
    long d_ptr;
    long d_val;

    public long getTag() {
        return d_tag;
    }

    public long getPtr() {
        return d_ptr;
    }

    public long getVal() {
        return d_val;
    }

    public String getName(Elf elf, Elf.SectionHeader sectionHeader) throws IOException {
        
        StringBuilder sb = new StringBuilder();
        
        
        switch ((int) d_tag)
        {
            case DT_FLAGS:
                sb.append (print_dynamic_flags (d_val));
                break;

            case DT_AUXILIARY:
            case DT_FILTER:
            case DT_CONFIG:
            case DT_DEPAUDIT:
            case DT_AUDIT:
                switch ((int) d_tag)
                {
                    case DT_AUXILIARY:
                        sb.append (("Auxiliary library"));
                        break;

                    case DT_FILTER:
                        sb.append (("Filter library"));
                        break;

                    case DT_CONFIG:
                        sb.append (("Configuration file"));
                        break;

                    case DT_DEPAUDIT:
                        sb.append (("Dependency audit library"));
                        break;

                    case DT_AUDIT:
                        sb.append (("Audit library"));
                        break;
                    default:
                        break;
                }
                if (elf.readSectionData(sectionHeader.sh_link).length > d_val)
                    sb.append (": [" + elf.get_string_from_section(sectionHeader.sh_link, (int) d_val) + "]");
                else
                {
                    sb.append (": ");
                    sb.append ("0x" + Long.toHexString(d_val));
                    sb.append ('\n');
                }
                break;

            case DT_FEATURE:
                sb.append (("Flags:"));

                if (d_val == 0)
                    sb.append ((" None\n"));
                else
                {
                    long val = d_val;

                    if ((val & DTF_1_PARINIT) > 0) {
                        sb.append (" PARINIT");
                        val ^= DTF_1_PARINIT;
                    }
                    if (0 < (val & DTF_1_CONFEXP))
                    {
                        sb.append (" CONFEXP");
                        val ^= DTF_1_CONFEXP;
                    }
                    if (val != 0)
                        sb.append (Long.toHexString(val));
                }
                break;

            case DT_POSFLAG_1:
                sb.append (("Flags:"));

                if (d_val == 0)
                    sb.append ((" None\n"));
                else
                {
                    long val =d_val;

                    if ((val & DF_P1_LAZYLOAD) > 0)
                    {
                        sb.append (" LAZYLOAD");
                        val ^= DF_P1_LAZYLOAD;
                    }
                    if ((val & DF_P1_GROUPPERM) > 0)
                    {
                        sb.append (" GROUPPERM");
                        val ^= DF_P1_GROUPPERM;
                    }
                    if (val != 0)
                        sb.append (Long.toHexString(val));
                }
                break;

            case DT_FLAGS_1:
                sb.append (("Flags:"));
                if (d_val == 0)
                    sb.append ((" None\n"));
                else
                {
                    long val = d_val;

                    if ((val & DF_1_NOW) > 0)
                    {
                        sb.append (" NOW");
                        val ^= DF_1_NOW;
                    }
                    if ((val & DF_1_GLOBAL)> 0)
                    {
                        sb.append (" GLOBAL");
                        val ^= DF_1_GLOBAL;
                    }
                    if ((val & DF_1_GROUP)> 0)
                    {
                        sb.append (" GROUP");
                        val ^= DF_1_GROUP;
                    }
                    if ((val & DF_1_NODELETE)> 0)
                    {
                        sb.append (" NODELETE");
                        val ^= DF_1_NODELETE;
                    }
                    if ((val & DF_1_LOADFLTR)> 0)
                    {
                        sb.append (" LOADFLTR");
                        val ^= DF_1_LOADFLTR;
                    }
                    if ((val & DF_1_INITFIRST)> 0)
                    {
                        sb.append (" INITFIRST");
                        val ^= DF_1_INITFIRST;
                    }
                    if ((val & DF_1_NOOPEN)> 0)
                    {
                        sb.append (" NOOPEN");
                        val ^= DF_1_NOOPEN;
                    }
                    if ((val & DF_1_ORIGIN)> 0)
                    {
                        sb.append (" ORIGIN");
                        val ^= DF_1_ORIGIN;
                    }
                    if ((val & DF_1_DIRECT)> 0)
                    {
                        sb.append (" DIRECT");
                        val ^= DF_1_DIRECT;
                    }
                    if ((val & DF_1_TRANS)> 0)
                    {
                        sb.append (" TRANS");
                        val ^= DF_1_TRANS;
                    }
                    if ((val & DF_1_INTERPOSE)> 0)
                    {
                        sb.append (" INTERPOSE");
                        val ^= DF_1_INTERPOSE;
                    }
                    if ((val & DF_1_NODEFLIB)> 0)
                    {
                        sb.append (" NODEFLIB");
                        val ^= DF_1_NODEFLIB;
                    }
                    if ((val & DF_1_NODUMP)> 0)
                    {
                        sb.append (" NODUMP");
                        val ^= DF_1_NODUMP;
                    }
                    if ((val & DF_1_CONFALT)> 0)
                    {
                        sb.append (" CONFALT");
                        val ^= DF_1_CONFALT;
                    }
                    if ((val & DF_1_ENDFILTEE)> 0)
                    {
                        sb.append (" ENDFILTEE");
                        val ^= DF_1_ENDFILTEE;
                    }
                    if ((val & DF_1_DISPRELDNE)> 0)
                    {
                        sb.append (" DISPRELDNE");
                        val ^= DF_1_DISPRELDNE;
                    }
                    if ((val & DF_1_DISPRELPND)> 0)
                    {
                        sb.append (" DISPRELPND");
                        val ^= DF_1_DISPRELPND;
                    }
                    if ((val & DF_1_NODIRECT)> 0)
                    {
                        sb.append (" NODIRECT");
                        val ^= DF_1_NODIRECT;
                    }
                    if ((val & DF_1_IGNMULDEF)> 0)
                    {
                        sb.append (" IGNMULDEF");
                        val ^= DF_1_IGNMULDEF;
                    }
                    if ((val & DF_1_NOKSYMS)> 0)
                    {
                        sb.append (" NOKSYMS");
                        val ^= DF_1_NOKSYMS;
                    }
                    if ((val & DF_1_NOHDR)> 0)
                    {
                        sb.append (" NOHDR");
                        val ^= DF_1_NOHDR;
                    }
                    if ((val & DF_1_EDITED)> 0)
                    {
                        sb.append (" EDITED");
                        val ^= DF_1_EDITED;
                    }
                    if ((val & DF_1_NORELOC)> 0)
                    {
                        sb.append (" NORELOC");
                        val ^= DF_1_NORELOC;
                    }
                    if ((val & DF_1_SYMINTPOSE)> 0)
                    {
                        sb.append (" SYMINTPOSE");
                        val ^= DF_1_SYMINTPOSE;
                    }
                    if ((val & DF_1_GLOBAUDIT)> 0)
                    {
                        sb.append (" GLOBAUDIT");
                        val ^= DF_1_GLOBAUDIT;
                    }
                    if ((val & DF_1_SINGLETON)> 0)
                    {
                        sb.append (" SINGLETON");
                        val ^= DF_1_SINGLETON;
                    }
                    if ((val & DF_1_STUB)> 0)
                    {
                        sb.append (" STUB");
                        val ^= DF_1_STUB;
                    }
                    if ((val & DF_1_PIE)> 0)
                    {
                        sb.append (" PIE");
                        val ^= DF_1_PIE;
                    }
                    if ((val & DF_1_KMOD)> 0)
                    {
                        sb.append (" KMOD");
                        val ^= DF_1_KMOD;
                    }
                    if ((val & DF_1_WEAKFILTER)> 0)
                    {
                        sb.append (" WEAKFILTER");
                        val ^= DF_1_WEAKFILTER;
                    }
                    if ((val & DF_1_NOCOMMON)> 0)
                    {
                        sb.append (" NOCOMMON");
                        val ^= DF_1_NOCOMMON;
                    }
                    if (val != 0)
                        sb.append ("0x"+Long.toHexString(val));
                }
                break;

            case DT_PLTREL:
                //TODO filedata->dynamic_info[d_tag] = d_val;
                sb.append(get_dynamic_type (elf, sectionHeader, d_val));
                break;

            case DT_NULL	:
            case DT_NEEDED	:
            case DT_PLTGOT	:
            case DT_HASH	:
            case DT_STRTAB	:
            case DT_SYMTAB	:
            case DT_RELA	:
            case DT_INIT	:
            case DT_FINI	:
            case DT_SONAME	:
            case DT_RPATH	:
            case DT_SYMBOLIC:
            case DT_REL	:
            case DT_DEBUG	:
            case DT_TEXTREL	:
            case DT_JMPREL	:
            case DT_RUNPATH	:
                //TODO filedata->dynamic_info[d_tag] = d_val;

                String name;

                if (Elf.VALID_SYMBOL_NAME (elf, sectionHeader.sh_link, d_val))
                    name = elf.get_string_from_section (sectionHeader.sh_link, (int)d_val);
                else
                    name = null;

                if (name != null)
                {
                    switch ((int) d_tag)
                    {
                        case DT_NEEDED:
                            sb.append (("Shared library: ["+name+"]"));

                            //TODO if (filedata->program_interpreter
                            //TODO        && streq (name, filedata->program_interpreter))
                            //TODO    sb.append ((" program interpreter"));
                            break;

                        case DT_SONAME:
                            sb.append (("Library soname: ["+name+"]"));
                            break;

                        case DT_RPATH:
                            sb.append (("Library rpath: ["+name+"]"));
                            break;

                        case DT_RUNPATH:
                            sb.append (("Library runpath: ["+name+"]"));
                            break;

                        default:
                            sb.append ("0x" + Long.toHexString(d_val));
                            break;
                    }
                }
                else
                    sb.append ("0x" + Long.toHexString(d_val));

                sb.append ('\n');
                break;

            case DT_PLTRELSZ:
            case DT_RELASZ	:
            case DT_STRSZ	:
            case DT_RELSZ	:
            case DT_RELAENT	:
            case DT_SYMENT	:
            case DT_RELENT	:
                //TODO filedata->dynamic_info[d_tag] = entry->d_un.d_val;
                /* Fall through.  */
            case DT_PLTPADSZ:
            case DT_MOVEENT	:
            case DT_MOVESZ	:
            case DT_INIT_ARRAYSZ:
            case DT_FINI_ARRAYSZ:
            case DT_GNU_CONFLICTSZ:
            case DT_GNU_LIBLISTSZ:
                sb.append (d_val + " (bytes)\n");
                break;

            case DT_VERDEFNUM:
            case DT_VERNEEDNUM:
            case DT_RELACOUNT:
            case DT_RELCOUNT:
                sb.append (d_val + "\n");
                break;

            case DT_SYMINSZ:
            case DT_SYMINENT:
            case DT_SYMINFO:
            case DT_USED:
            case DT_INIT_ARRAY:
            case DT_FINI_ARRAY:
                if (d_tag == DT_USED
                        && Elf.VALID_SYMBOL_NAME (elf, sectionHeader.sh_link, (int)d_val))
                {
                    name = elf.get_string_from_section (sectionHeader.sh_link, (int)d_val);
                        sb.append ("Not needed object: ["+name+"]\n");
                        break;
                }

                sb.append ("0x" + Long.toHexString(d_val) + "\n");
                break;

            case DT_BIND_NOW:
                /* The value of this entry is ignored.  */
                    sb.append ('\n');
                break;

            case DT_GNU_PRELINKED:
                Date date = new Date(d_val);
                sb.append(new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss").format(date));
                break;

            case DT_GNU_HASH:
                //TODO filedata->dynamic_info_DT_GNU_HASH = d_val;
                sb.append ("0x" + Long.toHexString(d_val) + "\n");
                sb.append ('\n');
                break;

            case DT_GNU_FLAGS_1:
                sb.append (("Flags:"));
                if (d_val == 0)
                    sb.append ((" None\n"));
                else
                {
                    long val = d_val;

                    if ((val & DF_GNU_1_UNIQUE) > 0)
                    {
                        sb.append (" UNIQUE");
                        val ^= DF_GNU_1_UNIQUE;
                    }
                    if (val != 0)
                        sb.append (val);
                }
                break;

            default:
                if ((d_tag >= DT_VERSYM) && (d_tag <= DT_VERNEEDNUM))
                    //TODO filedata->version_info[DT_VERSIONTAGIDX (d_tag)] = d_val;

                switch (elf.fileHeader.e_machine)
                {
                    case EM_AARCH64:
                        //TODO sb.append(dynamic_section_aarch64_val (entry));
                        break;
                    case EM_MIPS:
                    case EM_MIPS_RS3_LE:
                        //TODO sb.append(dynamic_section_mips_val (filedata, entry));
                        break;
                    case EM_PARISC:
                        //TODO sb.append(dynamic_section_parisc_val (entry));
                        break;
                    case EM_IA_64:
                        //TODO sb.append(dynamic_section_ia64_val (entry));
                        break;
                    default:
                        sb.append ("0x" + Long.toHexString(d_val));
                        sb.append ('\n');
                }
                break;
        }
        return sb.toString();
    }

    static String
    print_dynamic_flags (long flags)
    {
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        while (flags > 0)
        {
            long flag;

            flag = flags & - flags;
            flags &= ~ flag;

            if (first)
                first = false;
            else
                sb.append (' ');

            switch ((int) flag)
            {
                case DF_ORIGIN:		 sb.append ("ORIGIN"); break;
                case DF_SYMBOLIC:	 sb.append ("SYMBOLIC"); break;
                case DF_TEXTREL:	 sb.append ("TEXTREL"); break;
                case DF_BIND_NOW:	 sb.append ("BIND_NOW"); break;
                case DF_STATIC_TLS:	 sb.append ("STATIC_TLS"); break;
                default:		 sb.append ("unknown"); break;
            }
        }
        return sb.toString();
    }

    public static String get_dynamic_type (Elf elf, Elf.SectionHeader section, long type)
    {

        switch ((int) type)
        {
            case DT_NULL:	return "NULL";
            case DT_NEEDED:	return "NEEDED";
            case DT_PLTRELSZ:	return "PLTRELSZ";
            case DT_PLTGOT:	return "PLTGOT";
            case DT_HASH:	return "HASH";
            case DT_STRTAB:	return "STRTAB";
            case DT_SYMTAB:	return "SYMTAB";
            case DT_RELA:	return "RELA";
            case DT_RELASZ:	return "RELASZ";
            case DT_RELAENT:	return "RELAENT";
            case DT_STRSZ:	return "STRSZ";
            case DT_SYMENT:	return "SYMENT";
            case DT_INIT:	return "INIT";
            case DT_FINI:	return "FINI";
            case DT_SONAME:	return "SONAME";
            case DT_RPATH:	return "RPATH";
            case DT_SYMBOLIC:	return "SYMBOLIC";
            case DT_REL:	return "REL";
            case DT_RELSZ:	return "RELSZ";
            case DT_RELENT:	return "RELENT";
            case DT_PLTREL:	return "PLTREL";
            case DT_DEBUG:	return "DEBUG";
            case DT_TEXTREL:	return "TEXTREL";
            case DT_JMPREL:	return "JMPREL";
            case DT_BIND_NOW:   return "BIND_NOW";
            case DT_INIT_ARRAY: return "INIT_ARRAY";
            case DT_FINI_ARRAY: return "FINI_ARRAY";
            case DT_INIT_ARRAYSZ: return "INIT_ARRAYSZ";
            case DT_FINI_ARRAYSZ: return "FINI_ARRAYSZ";
            case DT_RUNPATH:    return "RUNPATH";
            case DT_FLAGS:      return "FLAGS";

            case DT_PREINIT_ARRAY: return "PREINIT_ARRAY";
            case DT_PREINIT_ARRAYSZ: return "PREINIT_ARRAYSZ";
            case DT_SYMTAB_SHNDX: return "SYMTAB_SHNDX";

            case DT_CHECKSUM:	return "CHECKSUM";
            case DT_PLTPADSZ:	return "PLTPADSZ";
            case DT_MOVEENT:	return "MOVEENT";
            case DT_MOVESZ:	return "MOVESZ";
            case DT_FEATURE:	return "FEATURE";
            case DT_POSFLAG_1:	return "POSFLAG_1";
            case DT_SYMINSZ:	return "SYMINSZ";
            case DT_SYMINENT:	return "SYMINENT"; /* aka VALRNGHI */

            case DT_ADDRRNGLO:  return "ADDRRNGLO";
            case DT_CONFIG:	return "CONFIG";
            case DT_DEPAUDIT:	return "DEPAUDIT";
            case DT_AUDIT:	return "AUDIT";
            case DT_PLTPAD:	return "PLTPAD";
            case DT_MOVETAB:	return "MOVETAB";
            case DT_SYMINFO:	return "SYMINFO"; /* aka ADDRRNGHI */

            case DT_VERSYM:	return "VERSYM";

            case DT_TLSDESC_GOT: return "TLSDESC_GOT";
            case DT_TLSDESC_PLT: return "TLSDESC_PLT";
            case DT_RELACOUNT:	return "RELACOUNT";
            case DT_RELCOUNT:	return "RELCOUNT";
            case DT_FLAGS_1:	return "FLAGS_1";
            case DT_VERDEF:	return "VERDEF";
            case DT_VERDEFNUM:	return "VERDEFNUM";
            case DT_VERNEED:	return "VERNEED";
            case DT_VERNEEDNUM:	return "VERNEEDNUM";

            case DT_AUXILIARY:	return "AUXILIARY";
            case DT_USED:	return "USED";
            case DT_FILTER:	return "FILTER";

            case DT_GNU_PRELINKED: return "GNU_PRELINKED";
            case DT_GNU_CONFLICT: return "GNU_CONFLICT";
            case DT_GNU_CONFLICTSZ: return "GNU_CONFLICTSZ";
            case DT_GNU_LIBLIST: return "GNU_LIBLIST";
            case DT_GNU_LIBLISTSZ: return "GNU_LIBLISTSZ";
            case DT_GNU_HASH:	return "GNU_HASH";
            case DT_GNU_FLAGS_1: return "GNU_FLAGS_1";

            default:
                if ((type >= DT_LOPROC) && (type <= DT_HIPROC))
                {
	            String result = null;

                    switch (elf.fileHeader.e_machine)
                    {
                        case EM_AARCH64:
                            //TODO result = get_aarch64_dynamic_type (type);
                            break;
                        case EM_MIPS:
                        case EM_MIPS_RS3_LE:
                            //TODO result = get_mips_dynamic_type (type);
                            break;
                        case EM_SPARCV9:
                            //TODO result = get_sparc64_dynamic_type (type);
                            break;
                        case EM_PPC:
                            //TODO result = get_ppc_dynamic_type (type);
                            break;
                        case EM_PPC64:
                            //TODO result = get_ppc64_dynamic_type (type);
                            break;
                        case EM_IA_64:
                            //TODO result = get_ia64_dynamic_type (type);
                            break;
                        case (short) EM_ALPHA:
                            //TODO result = get_alpha_dynamic_type (type);
                            break;
                        case EM_SCORE:
                            //TODO result = get_score_dynamic_type (type);
                            break;
                        case EM_TI_C6000:
                            //TODO result = get_tic6x_dynamic_type (type);
                            break;
                        case EM_ALTERA_NIOS2:
                            //TODO result = get_nios2_dynamic_type (type);
                            break;
                        default:
                            if (elf.fileHeader.e_ident[EI_OSABI] == ELFOSABI_SOLARIS) {
                                //TODO result = get_solaris_dynamic_type (type);
                            }else
                                result = null;
                            break;
                    }

                    if (result != null)
                        return result;

                    return "Processor Specific: " + type;
                }
                else if (((type >= DT_LOOS) && (type <= DT_HIOS))
                        || (elf.fileHeader.e_machine == EM_PARISC
                        && (type >= OLD_DT_LOOS) && (type <= OLD_DT_HIOS)))
                {
	                String result = null;

                    switch (elf.fileHeader.e_machine)
                    {
                        case EM_PARISC:
                            //TODO result = get_parisc_dynamic_type (type);
                            break;
                        case EM_IA_64:
                            //TODO result = get_ia64_dynamic_type (type);
                            break;
                        default:
                            if (elf.fileHeader.e_ident[EI_OSABI] == ELFOSABI_SOLARIS) {
                                //TODO result = get_solaris_dynamic_type (type);
                            }else
                                result = null;
                            break;
                    }

                    if (result != null)
                        return result;

                    return "Operating System specific: " + type;
                }
                else
                    return "<unknown>: " + type;

        }
    }

    public static ElfDynamic fromElf(Elf elf, Elf.SectionHeader section, int index) throws IOException {
        DataInput in = elf.generateDataInputFromSectionArray(section, index);
        ElfDynamic dynamic = new ElfDynamic();
        if (elf.is64Bit()) {
            dynamic.d_tag = elf.readLong(in);
            dynamic.d_ptr = dynamic.d_val = elf.readLong(in);
        } else {
            dynamic.d_tag = elf.readInt(in);
            dynamic.d_ptr = dynamic.d_val = elf.readInt(in);
            elf.readInt(in); //d_off
        }
        return dynamic;
    }
}
