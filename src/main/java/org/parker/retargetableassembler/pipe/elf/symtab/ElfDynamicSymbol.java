package org.parker.retargetableassembler.pipe.elf.symtab;

@SuppressWarnings("unused")
public class ElfDynamicSymbol {

    /* The next 2 dynamic tag ranges, integer value range (DT_VALRNGLO to;
   DT_VALRNGHI) and virtual address range (DT_ADDRRNGLO to DT_ADDRRNGHI),;
   are used on Solaris.  We support them everywhere.  Note these values;
   lie outside of the (new) range for OS specific values.  This is a;
   deliberate special case and we maintain it for backwards compatability.;
    */;
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

    /* Flag values used in the DT_POSFLAG_1 .dynamic entry.	 */
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
    public static final int	DF_1_DISPRELDNE = 0x00008000;
    public static final int	DF_1_DISPRELPND = 0x00010000;
    public static final int	DF_1_NODIRECT = 0x00020000;
    public static final int	DF_1_IGNMULDEF = 0x00040000;
    public static final int	DF_1_NOKSYMS = 0x00080000;
    public static final int	DF_1_NOHDR = 0x00100000;
    public static final int	DF_1_EDITED = 0x00200000;
    public static final int	DF_1_NORELOC = 0x00400000;
    public static final int	DF_1_SYMINTPOSE = 0x00800000;
    public static final int	DF_1_GLOBAUDIT = 0x01000000;
    public static final int	DF_1_SINGLETON = 0x02000000;
    public static final int	DF_1_STUB = 0x04000000;
    public static final int	DF_1_PIE = 0x08000000;
    public static final int	DF_1_KMOD = 0x10000000;
    public static final int	DF_1_WEAKFILTER = 0x20000000;
    public static final int	DF_1_NOCOMMON = 0x40000000;

    /* Flag values for the DT_FLAGS entry.	*/
    public static final int DF_ORIGIN = (1 << 0);
    public static final int DF_SYMBOLIC = (1 << 1);
    public static final int DF_TEXTREL = (1 << 2);
    public static final int DF_BIND_NOW = (1 << 3);
    public static final int DF_STATIC_TLS = (1 << 4);


}
