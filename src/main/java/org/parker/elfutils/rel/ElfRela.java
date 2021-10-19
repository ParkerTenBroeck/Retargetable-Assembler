package org.parker.elfutils.rel;

import org.parker.elfutils.Elf;
import org.parker.elfutils.symtab.Relocations.*;

import java.io.DataInput;
import java.io.IOException;

import static org.parker.elfutils.Elf.FileHeader.*;

public class ElfRela {
    long r_offset;
    long r_info;
    long r_addend;

    TYPE type;

    public enum TYPE{
        REL32(false),
        REL64(true),
        RELA32(false),
        RELA64(true);

        public final boolean is64Bit;
        TYPE(boolean is64Bit){
            this.is64Bit = is64Bit;
        }
    }

    public static ElfRela relFromElf(Elf elf, Elf.SectionHeader section, int index) throws IOException {
        DataInput in = elf.generateDataInputFromSectionArray(section, index);
        ElfRela rela = new ElfRela();
        if(elf.is64Bit()){
            rela.r_offset = elf.readLong(in);
            rela.r_info = elf.readLong(in);
            rela.type = TYPE.REL64;
        }else{
            rela.r_offset = elf.readInt(in);
            rela.r_info = elf.readInt(in);
            rela.type = TYPE.REL32;
        }
        return rela;
    }

    public static ElfRela relaFromElf(Elf elf, Elf.SectionHeader section, int index) throws IOException {
        DataInput in = elf.generateDataInputFromSectionArray(section, index);
        ElfRela rela = new ElfRela();
        if(elf.is64Bit()){
            rela.r_offset = elf.readLong(in);
            rela.r_info = elf.readLong(in);
            rela.r_addend = elf.readLong(in);
            rela.type = TYPE.RELA64;
        }else{
            rela.r_offset = elf.readInt(in);
            rela.r_info = elf.readInt(in);
            rela.r_addend = elf.readInt(in);
            rela.type = TYPE.RELA32;
        }
        return rela;
    }
    public long getSym(){
        if(type.is64Bit){
            return r_info >> 32;
        }else{
            return r_info >> 8;
        }
    }

    public long getType(){
        if(type.is64Bit){
            return r_info & 0xffffffff;
        }else{
            return r_info & 0xff;
        }
    }

    /*
    public long getInfo(){
        if(type.is64Bit){
            return r_info & 0xffffffff;
        }else{
            return r_info & 0xff;
        }
    }
     */

    public long getOffset() {
        return this.r_offset;
    }

    public long getInfo(){
        return r_info;
    }

    public long getAddend(){
        return r_addend;
    }

    public String getRelocType(Elf elf){
        String rtype;
        int reloc = (int) getType();
        int type = elf.fileHeader.e_machine;
        switch (type)
        {
            case EM_AARCH64:
                rtype = elf_aarch64_reloc_type.fromID(reloc);
                break;

            case EM_M32R:
            case EM_CYGNUS_M32R:
                rtype = elf_m32r_reloc_type.fromID (reloc);
                break;

            case EM_386:
            case EM_IAMCU:
                rtype = elf_i386_reloc_type.fromID (reloc);
                break;

            case EM_68HC11:
            case EM_68HC12:
                rtype = elf_m68hc11_reloc_type.fromID (reloc);
                break;

            case EM_S12Z:
                rtype = elf_s12z_reloc_type.fromID (reloc);
                break;

            case EM_68K:
                rtype = elf_m68k_reloc_type.fromID (reloc);
                break;

            case EM_960:
                rtype = elf_i960_reloc_type.fromID (reloc);
                break;

            case EM_AVR:
            case EM_AVR_OLD:
                rtype = elf_avr_reloc_type.fromID (reloc);
                break;

            case EM_OLD_SPARCV9:
            case EM_SPARC32PLUS:
            case EM_SPARCV9:
            case EM_SPARC:
                rtype = elf_sparc_reloc_type.fromID (reloc);
                break;

            case EM_SPU:
                rtype = elf_spu_reloc_type.fromID (reloc);
                break;

            case EM_V800:
                rtype = v800_reloc_type.fromID (reloc);
                break;
            case EM_V850:
            case EM_CYGNUS_V850:
                rtype = v850_reloc_type.fromID (reloc);
                break;

            case EM_D10V:
            case EM_CYGNUS_D10V:
                rtype = elf_d10v_reloc_type.fromID (reloc);
                break;

            case EM_D30V:
            case EM_CYGNUS_D30V:
                rtype = elf_d30v_reloc_type.fromID (reloc);
                break;

            case EM_DLX:
                rtype = elf_dlx_reloc_type.fromID (reloc);
                break;

            case EM_SH:
                rtype = elf_sh_reloc_type.fromID (reloc);
                break;

            case EM_MN10300:
            case EM_CYGNUS_MN10300:
                rtype = elf_mn10300_reloc_type.fromID (reloc);
                break;

            case EM_MN10200:
            case EM_CYGNUS_MN10200:
                rtype = elf_mn10200_reloc_type.fromID (reloc);
                break;

            case EM_FR30:
            case EM_CYGNUS_FR30:
                rtype = elf_fr30_reloc_type.fromID(reloc);
                break;

            case EM_CYGNUS_FRV:
                rtype = elf_frv_reloc_type.fromID(reloc);
                break;

            case EM_CSKY:
                rtype = elf_csky_reloc_type.fromID(reloc);
                break;

            case EM_FT32:
                rtype = elf_ft32_reloc_type.fromID(reloc);
                break;

            case EM_MCORE:
                rtype = elf_mcore_reloc_type.fromID(reloc);
                break;

            case EM_MMIX:
                rtype = elf_mmix_reloc_type.fromID(reloc);
                break;

            case EM_MOXIE:
                rtype = elf_moxie_reloc_type.fromID(reloc);
                break;

            case EM_MSP430:
                //if (uses_msp430x_relocs (filedata))
                //{
                    rtype = elf_msp430x_reloc_type.fromID(reloc);
                    break;
                //}
                /* Fall through.  */
            case EM_MSP430_OLD:
                rtype = elf_msp430_reloc_type.fromID(reloc);
                break;

            case EM_NDS32:
                rtype = elf_nds32_reloc_type.fromID(reloc);
                break;

            case EM_PPC:
                rtype = elf_ppc_reloc_type.fromID(reloc);
                break;

            case EM_PPC64:
                rtype = elf_ppc64_reloc_type.fromID(reloc);
                break;

            case EM_MIPS:
            case EM_MIPS_RS3_LE:
                rtype = elf_mips_reloc_type.fromID(reloc);
                break;

            case EM_RISCV:
                rtype = elf_riscv_reloc_type.fromID(reloc);
                break;

            case EM_ALPHA:
                rtype = elf_alpha_reloc_type.fromID(reloc);
                break;

            case EM_ARM:
                rtype = elf_arm_reloc_type.fromID(reloc);
                break;

            case EM_ARC:
            case EM_ARC_COMPACT:
            case EM_ARC_COMPACT2:
                rtype = elf_arc_reloc_type.fromID(reloc);
                break;

            case EM_PARISC:
                rtype = elf_hppa_reloc_type.fromID(reloc);
                break;

            case EM_H8_300:
            case EM_H8_300H:
            case EM_H8S:
                rtype = elf_h8_reloc_type.fromID(reloc);
                break;

            case EM_OR1K:
                rtype = elf_or1k_reloc_type.fromID(reloc);
                break;

            case EM_PJ:
            case EM_PJ_OLD:
                rtype = elf_pj_reloc_type.fromID(reloc);
                break;
            case EM_IA_64:
                rtype = elf_ia64_reloc_type.fromID(reloc);
                break;

            case EM_CRIS:
                rtype = elf_cris_reloc_type.fromID(reloc);
                break;

            case EM_860:
                rtype = elf_i860_reloc_type.fromID(reloc);
                break;

            case EM_X86_64:
            case EM_L1OM:
            case EM_K1OM:
                rtype = elf_x86_64_reloc_type.fromID(reloc);
                break;

            case EM_S370:
                rtype = i370_reloc_type.fromID(reloc);
                break;

            case EM_S390_OLD:
            case EM_S390:
                rtype = elf_s390_reloc_type.fromID(reloc);
                break;

            case EM_SCORE:
                rtype = elf_score_reloc_type.fromID(reloc);
                break;

            case EM_XSTORMY16:
                rtype = elf_xstormy16_reloc_type.fromID(reloc);
                break;

            case EM_CRX:
                rtype = elf_crx_reloc_type.fromID(reloc);
                break;

            case EM_VAX:
                rtype = elf_vax_reloc_type.fromID(reloc);
                break;

            case EM_VISIUM:
                rtype = elf_visium_reloc_type.fromID(reloc);
                break;

            case EM_BPF:
                rtype = elf_bpf_reloc_type.fromID(reloc);
                break;

            case EM_ADAPTEVA_EPIPHANY:
                rtype = elf_epiphany_reloc_type.fromID(reloc);
                break;

            case EM_IP2K:
            case EM_IP2K_OLD:
                rtype = elf_ip2k_reloc_type.fromID(reloc);
                break;

            case EM_IQ2000:
                rtype = elf_iq2000_reloc_type.fromID(reloc);
                break;

            case EM_XTENSA_OLD:
            case EM_XTENSA:
                rtype = elf_xtensa_reloc_type.fromID(reloc);
                break;

            case EM_LATTICEMICO32:
                rtype = elf_lm32_reloc_type.fromID(reloc);
                break;

            case EM_M32C_OLD:
            case EM_M32C:
                rtype = elf_m32c_reloc_type.fromID(reloc);
                break;

            case EM_MT:
                rtype = elf_mt_reloc_type.fromID(reloc);
                break;

            case EM_BLACKFIN:
                rtype = elf_bfin_reloc_type.fromID(reloc);
                break;

            case EM_CYGNUS_MEP:
                rtype = elf_mep_reloc_type.fromID(reloc);
                break;

            case EM_CR16:
                rtype = elf_cr16_reloc_type.fromID(reloc);
                break;

            case EM_MICROBLAZE:
            case EM_MICROBLAZE_OLD:
                rtype = elf_microblaze_reloc_type.fromID(reloc);
                break;

            case EM_RL78:
                rtype = elf_rl78_reloc_type.fromID(reloc);
                break;

            case EM_RX:
                rtype = elf_rx_reloc_type.fromID(reloc);
                break;

            case EM_METAG:
                rtype = elf_metag_reloc_type.fromID(reloc);
                break;

            case EM_XC16X:
            case EM_C166:
                rtype = elf_xc16x_reloc_type.fromID(reloc);
                break;

            case EM_TI_C6000:
                rtype = elf_tic6x_reloc_type.fromID(reloc);
                break;

            case EM_TILEGX:
                rtype = elf_tilegx_reloc_type.fromID(reloc);
                break;

            case EM_TILEPRO:
                rtype = elf_tilepro_reloc_type.fromID(reloc);
                break;

            case EM_WEBASSEMBLY:
                rtype = elf_wasm32_reloc_type.fromID(reloc);
                break;

            case EM_XGATE:
                rtype = elf_xgate_reloc_type.fromID(reloc);
                break;

            case EM_ALTERA_NIOS2:
                rtype = elf_nios2_reloc_type.fromID(reloc);
                break;

            case EM_TI_PRU:
                rtype = elf_pru_reloc_type.fromID(reloc);
                break;

            case EM_NFP:
                //if (EF_NFP_MACH (elf.fileHeader.e_flags) == E_NFP_MACH_3200)
                //    rtype = elf_nfp3200_reloc_type.fromID(reloc);
                //else
                //    rtype = elf_nfp_reloc_type.fromID(reloc);
                rtype = "NOT ADDED YET PLEASE DONT HURT ME!!!";
                break;

            case EM_Z80:
                rtype = elf_z80_reloc_type.fromID(reloc);
                break;
            default:
                rtype = "";
                break;
        }
        return rtype;
    }
}
