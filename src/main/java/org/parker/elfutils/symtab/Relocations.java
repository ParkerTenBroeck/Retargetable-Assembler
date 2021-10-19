package org.parker.elfutils.symtab;

public class Relocations {


    public enum elf_aarch64_reloc_type {

        R_AARCH64_NONE(0),


        R_AARCH64_P32_ABS32(1),


        R_AARCH64_P32_ABS16(2),


        R_AARCH64_P32_PREL32(3),


        R_AARCH64_P32_PREL16(4),


        R_AARCH64_P32_MOVW_UABS_G0(5),


        R_AARCH64_P32_MOVW_UABS_G0_NC(6),


        R_AARCH64_P32_MOVW_UABS_G1(7),


        R_AARCH64_P32_MOVW_SABS_G0(8),


        R_AARCH64_P32_LD_PREL_LO19(9),


        R_AARCH64_P32_ADR_PREL_LO21(10),


        R_AARCH64_P32_ADR_PREL_PG_HI21(11),


        R_AARCH64_P32_ADD_ABS_LO12_NC(12),


        R_AARCH64_P32_LDST8_ABS_LO12_NC(13),


        R_AARCH64_P32_LDST16_ABS_LO12_NC(14),


        R_AARCH64_P32_LDST32_ABS_LO12_NC(15),


        R_AARCH64_P32_LDST64_ABS_LO12_NC(16),


        R_AARCH64_P32_LDST128_ABS_LO12_NC(17),


        R_AARCH64_P32_TSTBR14(18),


        R_AARCH64_P32_CONDBR19(19),


        R_AARCH64_P32_JUMP26(20),


        R_AARCH64_P32_CALL26(21),


        R_AARCH64_P32_MOVW_PREL_G0(22),
        R_AARCH64_P32_MOVW_PREL_G0_NC(23),
        R_AARCH64_P32_MOVW_PREL_G1(24),

        R_AARCH64_P32_GOT_LD_PREL19(25),
        R_AARCH64_P32_ADR_GOT_PAGE(26),
        R_AARCH64_P32_LD32_GOT_LO12_NC(27),
        R_AARCH64_P32_LD32_GOTPAGE_LO14(28),

        R_AARCH64_P32_TLSGD_ADR_PREL21(80),
        R_AARCH64_P32_TLSGD_ADR_PAGE21(81),
        R_AARCH64_P32_TLSGD_ADD_LO12_NC(82),
        R_AARCH64_P32_TLSLD_ADR_PREL21(83),
        R_AARCH64_P32_TLSLD_ADR_PAGE21(84),
        R_AARCH64_P32_TLSLD_ADD_LO12_NC(85),
        R_AARCH64_P32_TLSLD_MOVW_DTPREL_G1(87),
        R_AARCH64_P32_TLSLD_MOVW_DTPREL_G0(88),
        R_AARCH64_P32_TLSLD_MOVW_DTPREL_G0_NC(89),
        R_AARCH64_P32_TLSLD_ADD_DTPREL_HI12(90),
        R_AARCH64_P32_TLSLD_ADD_DTPREL_LO12(91),
        R_AARCH64_P32_TLSLD_ADD_DTPREL_LO12_NC(92),
        R_AARCH64_P32_TLSIE_ADR_GOTTPREL_PAGE21(103),
        R_AARCH64_P32_TLSIE_LD32_GOTTPREL_LO12_NC(104),
        R_AARCH64_P32_TLSIE_LD_GOTTPREL_PREL19(105),
        R_AARCH64_P32_TLSLE_MOVW_TPREL_G1(106),
        R_AARCH64_P32_TLSLE_MOVW_TPREL_G0(107),
        R_AARCH64_P32_TLSLE_MOVW_TPREL_G0_NC(108),
        R_AARCH64_P32_TLSLE_ADD_TPREL_HI12(109),
        R_AARCH64_P32_TLSLE_ADD_TPREL_LO12(110),
        R_AARCH64_P32_TLSLE_ADD_TPREL_LO12_NC(111),
        R_AARCH64_P32_TLSLE_LDST8_TPREL_LO12(112),
        R_AARCH64_P32_TLSLE_LDST8_TPREL_LO12_NC(113),
        R_AARCH64_P32_TLSLE_LDST16_TPREL_LO12(114),
        R_AARCH64_P32_TLSLE_LDST16_TPREL_LO12_NC(115),
        R_AARCH64_P32_TLSLE_LDST32_TPREL_LO12(116),
        R_AARCH64_P32_TLSLE_LDST32_TPREL_LO12_NC(117),
        R_AARCH64_P32_TLSLE_LDST64_TPREL_LO12(118),
        R_AARCH64_P32_TLSLE_LDST64_TPREL_LO12_NC(119),

        R_AARCH64_P32_TLSDESC_LD_PREL19(122),
        R_AARCH64_P32_TLSDESC_ADR_PREL21(123),
        R_AARCH64_P32_TLSDESC_ADR_PAGE21(124),
        R_AARCH64_P32_TLSDESC_LD32_LO12_NC(125),
        R_AARCH64_P32_TLSDESC_ADD_LO12_NC(126),
        R_AARCH64_P32_TLSDESC_CALL(127),


        R_AARCH64_P32_COPY(180),


        R_AARCH64_P32_GLOB_DAT(181),


        R_AARCH64_P32_JUMP_SLOT(182),


        R_AARCH64_P32_RELATIVE(183),
        R_AARCH64_P32_TLS_DTPMOD(184),
        R_AARCH64_P32_TLS_DTPREL(185),
        R_AARCH64_P32_TLS_TPREL(186),
        R_AARCH64_P32_TLSDESC(187),
        R_AARCH64_P32_IRELATIVE(188),

        R_AARCH64_NULL(256),


        R_AARCH64_ABS64(257),


        R_AARCH64_ABS32(258),


        R_AARCH64_ABS16(259),


        R_AARCH64_PREL64(260),


        R_AARCH64_PREL32(261),


        R_AARCH64_PREL16(262),


        R_AARCH64_MOVW_UABS_G0(263),


        R_AARCH64_MOVW_UABS_G0_NC(264),


        R_AARCH64_MOVW_UABS_G1(265),


        R_AARCH64_MOVW_UABS_G1_NC(266),


        R_AARCH64_MOVW_UABS_G2(267),


        R_AARCH64_MOVW_UABS_G2_NC(268),


        R_AARCH64_MOVW_UABS_G3(269),


        R_AARCH64_MOVW_SABS_G0(270),


        R_AARCH64_MOVW_SABS_G1(271),


        R_AARCH64_MOVW_SABS_G2(272),


        R_AARCH64_LD_PREL_LO19(273),


        R_AARCH64_ADR_PREL_LO21(274),


        R_AARCH64_ADR_PREL_PG_HI21(275),


        R_AARCH64_ADR_PREL_PG_HI21_NC(276),


        R_AARCH64_ADD_ABS_LO12_NC(277),


        R_AARCH64_LDST8_ABS_LO12_NC(278),


        R_AARCH64_TSTBR14(279),


        R_AARCH64_CONDBR19(280),


        R_AARCH64_JUMP26(282),


        R_AARCH64_CALL26(283),


        R_AARCH64_LDST16_ABS_LO12_NC(284),


        R_AARCH64_LDST32_ABS_LO12_NC(285),


        R_AARCH64_LDST64_ABS_LO12_NC(286),


        R_AARCH64_MOVW_PREL_G0(287),
        R_AARCH64_MOVW_PREL_G0_NC(288),
        R_AARCH64_MOVW_PREL_G1(289),
        R_AARCH64_MOVW_PREL_G1_NC(290),
        R_AARCH64_MOVW_PREL_G2(291),
        R_AARCH64_MOVW_PREL_G2_NC(292),
        R_AARCH64_MOVW_PREL_G3(293),


        R_AARCH64_LDST128_ABS_LO12_NC(299),


        R_AARCH64_MOVW_GOTOFF_G0(300),
        R_AARCH64_MOVW_GOTOFF_G0_NC(301),
        R_AARCH64_MOVW_GOTOFF_G1(302),
        R_AARCH64_MOVW_GOTOFF_G1_NC(303),
        R_AARCH64_MOVW_GOTOFF_G2(304),
        R_AARCH64_MOVW_GOTOFF_G2_NC(305),
        R_AARCH64_MOVW_GOTOFF_G3(306),


        R_AARCH64_GOTREL64(307),
        R_AARCH64_GOTREL32(308),


        R_AARCH64_GOT_LD_PREL19(309),
        R_AARCH64_LD64_GOTOFF_LO15(310),
        R_AARCH64_ADR_GOT_PAGE(311),
        R_AARCH64_LD64_GOT_LO12_NC(312),
        R_AARCH64_LD64_GOTPAGE_LO15(313),


        R_AARCH64_TLSGD_ADR_PREL21(512),
        R_AARCH64_TLSGD_ADR_PAGE21(513),
        R_AARCH64_TLSGD_ADD_LO12_NC(514),
        R_AARCH64_TLSGD_MOVW_G1(515),
        R_AARCH64_TLSGD_MOVW_G0_NC(516),


        R_AARCH64_TLSLD_ADR_PREL21(517),
        R_AARCH64_TLSLD_ADR_PAGE21(518),
        R_AARCH64_TLSLD_ADD_LO12_NC(519),
        R_AARCH64_TLSLD_MOVW_G1(520),
        R_AARCH64_TLSLD_MOVW_G0_NC(521),
        R_AARCH64_TLSLD_LD_PREL19(522),
        R_AARCH64_TLSLD_MOVW_DTPREL_G2(523),
        R_AARCH64_TLSLD_MOVW_DTPREL_G1(524),
        R_AARCH64_TLSLD_MOVW_DTPREL_G1_NC(525),
        R_AARCH64_TLSLD_MOVW_DTPREL_G0(526),
        R_AARCH64_TLSLD_MOVW_DTPREL_G0_NC(527),
        R_AARCH64_TLSLD_ADD_DTPREL_HI12(528),
        R_AARCH64_TLSLD_ADD_DTPREL_LO12(529),
        R_AARCH64_TLSLD_ADD_DTPREL_LO12_NC(530),
        R_AARCH64_TLSLD_LDST8_DTPREL_LO12(531),
        R_AARCH64_TLSLD_LDST8_DTPREL_LO12_NC(532),
        R_AARCH64_TLSLD_LDST16_DTPREL_LO12(533),
        R_AARCH64_TLSLD_LDST16_DTPREL_LO12_NC(534),
        R_AARCH64_TLSLD_LDST32_DTPREL_LO12(535),
        R_AARCH64_TLSLD_LDST32_DTPREL_LO12_NC(536),
        R_AARCH64_TLSLD_LDST64_DTPREL_LO12(537),
        R_AARCH64_TLSLD_LDST64_DTPREL_LO12_NC(538),


        R_AARCH64_TLSIE_MOVW_GOTTPREL_G1(539),
        R_AARCH64_TLSIE_MOVW_GOTTPREL_G0_NC(540),
        R_AARCH64_TLSIE_ADR_GOTTPREL_PAGE21(541),
        R_AARCH64_TLSIE_LD64_GOTTPREL_LO12_NC(542),
        R_AARCH64_TLSIE_LD_GOTTPREL_PREL19(543),


        R_AARCH64_TLSLE_MOVW_TPREL_G2(544),
        R_AARCH64_TLSLE_MOVW_TPREL_G1(545),
        R_AARCH64_TLSLE_MOVW_TPREL_G1_NC(546),
        R_AARCH64_TLSLE_MOVW_TPREL_G0(547),
        R_AARCH64_TLSLE_MOVW_TPREL_G0_NC(548),
        R_AARCH64_TLSLE_ADD_TPREL_HI12(549),
        R_AARCH64_TLSLE_ADD_TPREL_LO12(550),
        R_AARCH64_TLSLE_ADD_TPREL_LO12_NC(551),
        R_AARCH64_TLSLE_LDST8_TPREL_LO12(552),
        R_AARCH64_TLSLE_LDST8_TPREL_LO12_NC(553),
        R_AARCH64_TLSLE_LDST16_TPREL_LO12(554),
        R_AARCH64_TLSLE_LDST16_TPREL_LO12_NC(555),
        R_AARCH64_TLSLE_LDST32_TPREL_LO12(556),
        R_AARCH64_TLSLE_LDST32_TPREL_LO12_NC(557),
        R_AARCH64_TLSLE_LDST64_TPREL_LO12(558),
        R_AARCH64_TLSLE_LDST64_TPREL_LO12_NC(559),


        R_AARCH64_TLSDESC_LD_PREL19(560),
        R_AARCH64_TLSDESC_ADR_PREL21(561),
        R_AARCH64_TLSDESC_ADR_PAGE21(562),
        R_AARCH64_TLSDESC_LD64_LO12(563),
        R_AARCH64_TLSDESC_ADD_LO12(564),
        R_AARCH64_TLSDESC_OFF_G1(565),
        R_AARCH64_TLSDESC_OFF_G0_NC(566),
        R_AARCH64_TLSDESC_LDR(567),
        R_AARCH64_TLSDESC_ADD(568),
        R_AARCH64_TLSDESC_CALL(569),

        R_AARCH64_TLSLE_LDST128_TPREL_LO12(570),
        R_AARCH64_TLSLE_LDST128_TPREL_LO12_NC(571),
        R_AARCH64_TLSLD_LDST128_DTPREL_LO12(572),
        R_AARCH64_TLSLD_LDST128_DTPREL_LO12_NC(573),


        R_AARCH64_COPY(1024),


        R_AARCH64_GLOB_DAT(1025),


        R_AARCH64_JUMP_SLOT(1026),


        R_AARCH64_RELATIVE(1027),
        R_AARCH64_TLS_DTPMOD64(1028),
        R_AARCH64_TLS_DTPREL64(1029),
        R_AARCH64_TLS_TPREL64(1030),


        R_AARCH64_TLS_DTPMOD(1028),
        R_AARCH64_TLS_DTPREL(1029),
        R_AARCH64_TLS_TPREL(1030),

        R_AARCH64_TLSDESC(1031),
        R_AARCH64_IRELATIVE(1032),

        R_AARCH64_end(0);
        public final int ID;

        elf_aarch64_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_aarch64_reloc_type e : elf_aarch64_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }

    public enum elf_alpha_reloc_type {
        R_ALPHA_NONE(0),
        R_ALPHA_REFLONG(1),
        R_ALPHA_REFQUAD(2),
        R_ALPHA_GPREL32(3),
        R_ALPHA_LITERAL(4),
        R_ALPHA_LITUSE(5),
        R_ALPHA_GPDISP(6),
        R_ALPHA_BRADDR(7),
        R_ALPHA_HINT(8),
        R_ALPHA_SREL16(9),
        R_ALPHA_SREL32(10),
        R_ALPHA_SREL64(11),


        R_ALPHA_GPRELHIGH(17),
        R_ALPHA_GPRELLOW(18),
        R_ALPHA_GPREL16(19),


        R_ALPHA_COPY(24),
        R_ALPHA_GLOB_DAT(25),
        R_ALPHA_JMP_SLOT(26),
        R_ALPHA_RELATIVE(27),


        R_ALPHA_BRSGP(28),


        R_ALPHA_TLSGD(29),
        R_ALPHA_TLSLDM(30),
        R_ALPHA_DTPMOD64(31),
        R_ALPHA_GOTDTPREL(32),
        R_ALPHA_DTPREL64(33),
        R_ALPHA_DTPRELHI(34),
        R_ALPHA_DTPRELLO(35),
        R_ALPHA_DTPREL16(36),
        R_ALPHA_GOTTPREL(37),
        R_ALPHA_TPREL64(38),
        R_ALPHA_TPRELHI(39),
        R_ALPHA_TPRELLO(40),
        R_ALPHA_TPREL16(41),

        R_ALPHA_max(0);
        public final int ID;

        elf_alpha_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_alpha_reloc_type e : elf_alpha_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_arc_reloc_type {

        R_ARC_NONE(0),


        R_ARC_8(1),


        R_ARC_16(2),


        R_ARC_24(3),


        R_ARC_32(4),


        R_ARC_N8(8),


        R_ARC_N16(9),


        R_ARC_N24(10),


        R_ARC_N32(11),


        R_ARC_SDA(12),


        R_ARC_SECTOFF(13),


        R_ARC_S21H_PCREL(14),


        R_ARC_S21W_PCREL(15),


        R_ARC_S25H_PCREL(16),


        R_ARC_S25W_PCREL(17),


        R_ARC_SDA32(18),


        R_ARC_SDA_LDST(19),


        R_ARC_SDA_LDST1(20),


        R_ARC_SDA_LDST2(21),


        R_ARC_SDA16_LD(22),


        R_ARC_SDA16_LD1(23),


        R_ARC_SDA16_LD2(24),


        R_ARC_S13_PCREL(25),


        R_ARC_W(26),


        R_ARC_32_ME(27),


        R_ARC_32_ME_S(105),


        R_ARC_N32_ME(28),


        R_ARC_SECTOFF_ME(29),


        R_ARC_SDA32_ME(30),


        R_ARC_W_ME(31),


        R_AC_SECTOFF_U8(35),


        R_AC_SECTOFF_U8_1(36),


        R_AC_SECTOFF_U8_2(37),


        R_AC_SECTOFF_S9(38),


        R_AC_SECTOFF_S9_1(39),


        R_AC_SECTOFF_S9_2(40),


        R_ARC_SECTOFF_ME_1(41),


        R_ARC_SECTOFF_ME_2(42),


        R_ARC_SECTOFF_1(43),


        R_ARC_SECTOFF_2(44),


        R_ARC_SDA_12(45),


        R_ARC_SDA16_ST2(48),


        R_ARC_32_PCREL(49),


        R_ARC_PC32(50),


        R_ARC_GOT32(59),


        R_ARC_GOTPC32(51),


        R_ARC_PLT32(52),


        R_ARC_COPY(53),


        R_ARC_GLOB_DAT(54),


        R_ARC_JMP_SLOT(55),


        R_ARC_RELATIVE(56),


        R_ARC_GOTOFF(57),


        R_ARC_GOTPC(58),


        R_ARC_S21W_PCREL_PLT(60),


        R_ARC_S25H_PCREL_PLT(61),


        R_ARC_JLI_SECTOFF(63),


        R_ARC_TLS_DTPMOD(66),


        R_ARC_TLS_TPOFF(68),


        R_ARC_TLS_GD_GOT(69),


        R_ARC_TLS_GD_LD(70),


        R_ARC_TLS_GD_CALL(71),


        R_ARC_TLS_IE_GOT(72),


        R_ARC_TLS_DTPOFF(67),


        R_ARC_TLS_DTPOFF_S9(73),


        R_ARC_TLS_LE_S9(74),


        R_ARC_TLS_LE_32(75),


        R_ARC_S25W_PCREL_PLT(76),


        R_ARC_S21H_PCREL_PLT(77),


        R_ARC_NPS_CMEM16(78),

        R_ARC_max(0);
        public final int ID;

        elf_arc_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_arc_reloc_type e : elf_arc_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }

    public enum elf_arm_reloc_type {
        R_ARM_NONE(0),
        R_ARM_PC24(1),
        R_ARM_ABS32(2),
        R_ARM_REL32(3),
        R_ARM_LDR_PC_G0(4),
        R_ARM_ABS16(5),
        R_ARM_ABS12(6),
        R_ARM_THM_ABS5(7),
        R_ARM_ABS8(8),
        R_ARM_SBREL32(9),
        R_ARM_THM_CALL(10),
        R_ARM_THM_PC8(11),
        R_ARM_BREL_ADJ(12),
        R_ARM_TLS_DESC(13),
        R_ARM_THM_SWI8(14),
        R_ARM_XPC25(15),
        R_ARM_THM_XPC22(16),
        R_ARM_TLS_DTPMOD32(17),
        R_ARM_TLS_DTPOFF32(18),
        R_ARM_TLS_TPOFF32(19),
        R_ARM_COPY(20),
        R_ARM_GLOB_DAT(21),
        R_ARM_JUMP_SLOT(22),
        R_ARM_RELATIVE(23),
        R_ARM_GOTOFF32(24),
        R_ARM_BASE_PREL(25),
        R_ARM_GOT_BREL(26),
        R_ARM_PLT32(27),
        R_ARM_CALL(28),
        R_ARM_JUMP24(29),
        R_ARM_THM_JUMP24(30),
        R_ARM_BASE_ABS(31),
        R_ARM_ALU_PCREL7_0(32),
        R_ARM_ALU_PCREL15_8(33),
        R_ARM_ALU_PCREL23_15(34),
        R_ARM_LDR_SBREL_11_0(35),
        R_ARM_ALU_SBREL_19_12(36),
        R_ARM_ALU_SBREL_27_20(37),
        R_ARM_TARGET1(38),
        R_ARM_SBREL31(39),
        R_ARM_V4BX(40),
        R_ARM_TARGET2(41),
        R_ARM_PREL31(42),
        R_ARM_MOVW_ABS_NC(43),
        R_ARM_MOVT_ABS(44),
        R_ARM_MOVW_PREL_NC(45),
        R_ARM_MOVT_PREL(46),
        R_ARM_THM_MOVW_ABS_NC(47),
        R_ARM_THM_MOVT_ABS(48),
        R_ARM_THM_MOVW_PREL_NC(49),
        R_ARM_THM_MOVT_PREL(50),
        R_ARM_THM_JUMP19(51),
        R_ARM_THM_JUMP6(52),
        R_ARM_THM_ALU_PREL_11_0(53),
        R_ARM_THM_PC12(54),
        R_ARM_ABS32_NOI(55),
        R_ARM_REL32_NOI(56),
        R_ARM_ALU_PC_G0_NC(57),
        R_ARM_ALU_PC_G0(58),
        R_ARM_ALU_PC_G1_NC(59),
        R_ARM_ALU_PC_G1(60),
        R_ARM_ALU_PC_G2(61),
        R_ARM_LDR_PC_G1(62),
        R_ARM_LDR_PC_G2(63),
        R_ARM_LDRS_PC_G0(64),
        R_ARM_LDRS_PC_G1(65),
        R_ARM_LDRS_PC_G2(66),
        R_ARM_LDC_PC_G0(67),
        R_ARM_LDC_PC_G1(68),
        R_ARM_LDC_PC_G2(69),
        R_ARM_ALU_SB_G0_NC(70),
        R_ARM_ALU_SB_G0(71),
        R_ARM_ALU_SB_G1_NC(72),
        R_ARM_ALU_SB_G1(73),
        R_ARM_ALU_SB_G2(74),
        R_ARM_LDR_SB_G0(75),
        R_ARM_LDR_SB_G1(76),
        R_ARM_LDR_SB_G2(77),
        R_ARM_LDRS_SB_G0(78),
        R_ARM_LDRS_SB_G1(79),
        R_ARM_LDRS_SB_G2(80),
        R_ARM_LDC_SB_G0(81),
        R_ARM_LDC_SB_G1(82),
        R_ARM_LDC_SB_G2(83),
        R_ARM_MOVW_BREL_NC(84),
        R_ARM_MOVT_BREL(85),
        R_ARM_MOVW_BREL(86),
        R_ARM_THM_MOVW_BREL_NC(87),
        R_ARM_THM_MOVT_BREL(88),
        R_ARM_THM_MOVW_BREL(89),
        R_ARM_TLS_GOTDESC(90),
        R_ARM_TLS_CALL(91),
        R_ARM_TLS_DESCSEQ(92),
        R_ARM_THM_TLS_CALL(93),
        R_ARM_PLT32_ABS(94),
        R_ARM_GOT_ABS(95),
        R_ARM_GOT_PREL(96),
        R_ARM_GOT_BREL12(97),
        R_ARM_GOTOFF12(98),
        R_ARM_GOTRELAX(99),
        R_ARM_GNU_VTENTRY(100),
        R_ARM_GNU_VTINHERIT(101),
        R_ARM_THM_JUMP11(102),
        R_ARM_THM_JUMP8(103),
        R_ARM_TLS_GD32(104),
        R_ARM_TLS_LDM32(105),
        R_ARM_TLS_LDO32(106),
        R_ARM_TLS_IE32(107),
        R_ARM_TLS_LE32(108),
        R_ARM_TLS_LDO12(109),
        R_ARM_TLS_LE12(110),
        R_ARM_TLS_IE12GP(111),

        R_ARM_ME_TOO(128),
        R_ARM_THM_TLS_DESCSEQ(129),

        R_ARM_THM_ALU_ABS_G0_NC(132),
        R_ARM_THM_ALU_ABS_G1_NC(133),
        R_ARM_THM_ALU_ABS_G2_NC(134),
        R_ARM_THM_ALU_ABS_G3_NC(135),
        R_ARM_THM_BF16(136),
        R_ARM_THM_BF12(137),
        R_ARM_THM_BF18(138),

        R_ARM_IRELATIVE(160),
        R_ARM_GOTFUNCDESC(161),
        R_ARM_GOTOFFFUNCDESC(162),
        R_ARM_FUNCDESC(163),
        R_ARM_FUNCDESC_VALUE(164),
        R_ARM_TLS_GD32_FDPIC(165),
        R_ARM_TLS_LDM32_FDPIC(166),
        R_ARM_TLS_IE32_FDPIC(167),


        R_ARM_RXPC25(249),
        R_ARM_RSBREL32(250),
        R_ARM_THM_RPC22(251),
        R_ARM_RREL32(252),
        R_ARM_RABS32(253),
        R_ARM_RPC24(254),
        R_ARM_RBASE(255),


        R_ARM_GOTOFF(R_ARM_GOTOFF32.ID),
        R_ARM_THM_PC22(R_ARM_THM_CALL.ID),
        R_ARM_THM_PC11(R_ARM_THM_JUMP11.ID),
        R_ARM_THM_PC9(R_ARM_THM_JUMP8.ID),


        R_ARM_GOTPC(R_ARM_BASE_PREL.ID),
        R_ARM_GOT32(R_ARM_GOT_BREL.ID),
        R_ARM_ROSEGREL32(R_ARM_SBREL31.ID),
        R_ARM_AMP_VCALL9(R_ARM_BREL_ADJ.ID),

        R_ARM_max(256);
        public final int ID;

        elf_arm_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_arm_reloc_type e : elf_arm_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }

    enum arm_st_branch_type {
        ST_BRANCH_TO_ARM,
        ST_BRANCH_TO_THUMB,
        ST_BRANCH_LONG,
        ST_BRANCH_UNKNOWN,
        ST_BRANCH_ENUM_SIZE
    }

    ;


    public enum elf_avr_reloc_type {
        R_AVR_NONE(0),
        R_AVR_32(1),
        R_AVR_7_PCREL(2),
        R_AVR_13_PCREL(3),
        R_AVR_16(4),
        R_AVR_16_PM(5),
        R_AVR_LO8_LDI(6),
        R_AVR_HI8_LDI(7),
        R_AVR_HH8_LDI(8),
        R_AVR_LO8_LDI_NEG(9),
        R_AVR_HI8_LDI_NEG(10),
        R_AVR_HH8_LDI_NEG(11),
        R_AVR_LO8_LDI_PM(12),
        R_AVR_HI8_LDI_PM(13),
        R_AVR_HH8_LDI_PM(14),
        R_AVR_LO8_LDI_PM_NEG(15),
        R_AVR_HI8_LDI_PM_NEG(16),
        R_AVR_HH8_LDI_PM_NEG(17),
        R_AVR_CALL(18),
        R_AVR_LDI(19),
        R_AVR_6(20),
        R_AVR_6_ADIW(21),
        R_AVR_MS8_LDI(22),
        R_AVR_MS8_LDI_NEG(23),
        R_AVR_LO8_LDI_GS(24),
        R_AVR_HI8_LDI_GS(25),
        R_AVR_8(26),
        R_AVR_8_LO8(27),
        R_AVR_8_HI8(28),
        R_AVR_8_HLO8(29),
        R_AVR_DIFF8(30),
        R_AVR_DIFF16(31),
        R_AVR_DIFF32(32),
        R_AVR_LDS_STS_16(33),
        R_AVR_PORT6(34),
        R_AVR_PORT5(35),
        R_AVR_32_PCREL(36),
        R_AVR_max(0);
        public final int ID;

        elf_avr_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_avr_reloc_type e : elf_avr_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_bfin_reloc_type {
        R_BFIN_UNUSED0(0x00),
        R_BFIN_PCREL5M2(0x01),
        R_BFIN_UNUSED1(0x02),
        R_BFIN_PCREL10(0x03),
        R_BFIN_PCREL12_JUMP(0x04),
        R_BFIN_RIMM16(0x05),
        R_BFIN_LUIMM16(0x06),
        R_BFIN_HUIMM16(0x07),
        R_BFIN_PCREL12_JUMP_S(0x08),
        R_BFIN_PCREL24_JUMP_X(0x09),
        R_BFIN_PCREL24(0x0a),
        R_BFIN_UNUSEDB(0x0b),
        R_BFIN_UNUSEDC(0x0c),
        R_BFIN_PCREL24_JUMP_L(0x0d),
        R_BFIN_PCREL24_CALL_X(0x0e),
        R_BFIN_VAR_EQ_SYMB(0x0f),
        R_BFIN_BYTE_DATA(0x10),
        R_BFIN_BYTE2_DATA(0x11),
        R_BFIN_BYTE4_DATA(0x12),
        R_BFIN_PCREL11(0x13),
        R_BFIN_GOT17M4(0x14),
        R_BFIN_GOTHI(0x15),
        R_BFIN_GOTLO(0x16),
        R_BFIN_FUNCDESC(0x17),
        R_BFIN_FUNCDESC_GOT17M4(0x18),
        R_BFIN_FUNCDESC_GOTHI(0x19),
        R_BFIN_FUNCDESC_GOTLO(0x1a),
        R_BFIN_FUNCDESC_VALUE(0x1b),
        R_BFIN_FUNCDESC_GOTOFF17M4(0x1c),
        R_BFIN_FUNCDESC_GOTOFFHI(0x1d),
        R_BFIN_FUNCDESC_GOTOFFLO(0x1e),
        R_BFIN_GOTOFF17M4(0x1f),
        R_BFIN_GOTOFFHI(0x20),
        R_BFIN_GOTOFFLO(0x21),

        R_BFIN_PUSH(0xE0),
        R_BFIN_CONST(0xE1),
        R_BFIN_ADD(0xE2),
        R_BFIN_SUB(0xE3),
        R_BFIN_MULT(0xE4),
        R_BFIN_DIV(0xE5),
        R_BFIN_MOD(0xE6),
        R_BFIN_LSHIFT(0xE7),
        R_BFIN_RSHIFT(0xE8),
        R_BFIN_AND(0xE9),
        R_BFIN_OR(0xEA),
        R_BFIN_XOR(0xEB),
        R_BFIN_LAND(0xEC),
        R_BFIN_LOR(0xED),
        R_BFIN_LEN(0xEE),
        R_BFIN_NEG(0xEF),
        R_BFIN_COMP(0xF0),
        R_BFIN_PAGE(0xF1),
        R_BFIN_HWPAGE(0xF2),
        R_BFIN_ADDR(0xF3),
        R_BFIN_PLTPC(0x40),
        R_BFIN_GOT(0x41),
        R_BFIN_GNU_VTINHERIT(0x42),
        R_BFIN_GNU_VTENTRY(0x43),
        R_BFIN_max(0);
        public final int ID;

        elf_bfin_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_bfin_reloc_type e : elf_bfin_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_cr16_reloc_type {
        R_CR16_NONE(0),
        R_CR16_NUM8(1),
        R_CR16_NUM16(2),
        R_CR16_NUM32(3),
        R_CR16_NUM32a(4),
        R_CR16_REGREL4(5),
        R_CR16_REGREL4a(6),
        R_CR16_REGREL14(7),
        R_CR16_REGREL14a(8),
        R_CR16_REGREL16(9),
        R_CR16_REGREL20(10),
        R_CR16_REGREL20a(11),
        R_CR16_ABS20(12),
        R_CR16_ABS24(13),
        R_CR16_IMM4(14),
        R_CR16_IMM8(15),
        R_CR16_IMM16(16),
        R_CR16_IMM20(17),
        R_CR16_IMM24(18),
        R_CR16_IMM32(19),
        R_CR16_IMM32a(20),
        R_CR16_DISP4(21),
        R_CR16_DISP8(22),
        R_CR16_DISP16(23),
        R_CR16_DISP24(24),
        R_CR16_DISP24a(25),
        R_CR16_SWITCH8(26),
        R_CR16_SWITCH16(27),
        R_CR16_SWITCH32(28),
        R_CR16_GOT_REGREL20(29),
        R_CR16_GOTC_REGREL20(30),
        R_CR16_GLOB_DAT(31),
        R_CR16_MAX(0);
        public final int ID;

        elf_cr16_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_cr16_reloc_type e : elf_cr16_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_cris_reloc_type {
        R_CRIS_NONE(0),
        R_CRIS_8(1),
        R_CRIS_16(2),
        R_CRIS_32(3),


        R_CRIS_8_PCREL(4),
        R_CRIS_16_PCREL(5),
        R_CRIS_32_PCREL(6),

        R_CRIS_GNU_VTINHERIT(7),
        R_CRIS_GNU_VTENTRY(8),


        R_CRIS_COPY(9),


        R_CRIS_GLOB_DAT(10),


        R_CRIS_JUMP_SLOT(11),


        R_CRIS_RELATIVE(12),


        R_CRIS_16_GOT(13),


        R_CRIS_32_GOT(14),


        R_CRIS_16_GOTPLT(15),


        R_CRIS_32_GOTPLT(16),


        R_CRIS_32_GOTREL(17),


        R_CRIS_32_PLT_GOTREL(18),


        R_CRIS_32_PLT_PCREL(19),


        R_CRIS_32_GOT_GD(20),


        R_CRIS_16_GOT_GD(21),


        R_CRIS_32_GD(22),


        R_CRIS_DTP(23),

        R_CRIS_32_DTPREL(24),


        R_CRIS_16_DTPREL(25),


        R_CRIS_32_GOT_TPREL(26),


        R_CRIS_16_GOT_TPREL(27),


        R_CRIS_32_TPREL(28),

        R_CRIS_16_TPREL(29),


        R_CRIS_DTPMOD(30),


        R_CRIS_32_IE(31),


        R_CRIS_max(0);
        public final int ID;

        elf_cris_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_cris_reloc_type e : elf_cris_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_crx_reloc_type {
        R_CRX_NONE(0),
        R_CRX_REL4(1),
        R_CRX_REL8(2),
        R_CRX_REL8_CMP(3),
        R_CRX_REL16(4),
        R_CRX_REL24(5),
        R_CRX_REL32(6),
        R_CRX_REGREL12(7),
        R_CRX_REGREL22(8),
        R_CRX_REGREL28(9),
        R_CRX_REGREL32(10),
        R_CRX_ABS16(11),
        R_CRX_ABS32(12),
        R_CRX_NUM8(13),
        R_CRX_NUM16(14),
        R_CRX_NUM32(15),
        R_CRX_IMM16(16),
        R_CRX_IMM32(17),
        R_CRX_SWITCH8(18),
        R_CRX_SWITCH16(19),
        R_CRX_SWITCH32(20),
        R_CRX_MAX(0);
        public final int ID;

        elf_crx_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_crx_reloc_type e : elf_crx_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_csky_reloc_type {
        R_CKCORE_NONE(0),
        R_CKCORE_ADDR32(1),
        R_CKCORE_PCREL_IMM8BY4(2),
        R_CKCORE_PCREL_IMM11BY2(3),
        R_CKCORE_PCREL_IMM4BY2(4),
        R_CKCORE_PCREL32(5),
        R_CKCORE_PCREL_JSR_IMM11BY2(6),
        R_CKCORE_GNU_VTINHERIT(7),
        R_CKCORE_GNU_VTENTRY(8),
        R_CKCORE_RELATIVE(9),
        R_CKCORE_COPY(10),
        R_CKCORE_GLOB_DAT(11),
        R_CKCORE_JUMP_SLOT(12),
        R_CKCORE_GOTOFF(13),
        R_CKCORE_GOTPC(14),
        R_CKCORE_GOT32(15),
        R_CKCORE_PLT32(16),
        R_CKCORE_ADDRGOT(17),
        R_CKCORE_ADDRPLT(18),
        R_CKCORE_PCREL_IMM26BY2(19),
        R_CKCORE_PCREL_IMM16BY2(20),
        R_CKCORE_PCREL_IMM16BY4(21),
        R_CKCORE_PCREL_IMM10BY2(22),
        R_CKCORE_PCREL_IMM10BY4(23),
        R_CKCORE_ADDR_HI16(24),
        R_CKCORE_ADDR_LO16(25),
        R_CKCORE_GOTPC_HI16(26),
        R_CKCORE_GOTPC_LO16(27),
        R_CKCORE_GOTOFF_HI16(28),
        R_CKCORE_GOTOFF_LO16(29),
        R_CKCORE_GOT12(30),
        R_CKCORE_GOT_HI16(31),
        R_CKCORE_GOT_LO16(32),
        R_CKCORE_PLT12(33),
        R_CKCORE_PLT_HI16(34),
        R_CKCORE_PLT_LO16(35),
        R_CKCORE_ADDRGOT_HI16(36),
        R_CKCORE_ADDRGOT_LO16(37),
        R_CKCORE_ADDRPLT_HI16(38),
        R_CKCORE_ADDRPLT_LO16(39),
        R_CKCORE_PCREL_JSR_IMM26BY2(40),
        R_CKCORE_TOFFSET_LO16(41),
        R_CKCORE_DOFFSET_LO16(42),
        R_CKCORE_PCREL_IMM18BY2(43),
        R_CKCORE_DOFFSET_IMM18(44),
        R_CKCORE_DOFFSET_IMM18BY2(45),
        R_CKCORE_DOFFSET_IMM18BY4(46),
        R_CKCORE_GOTOFF_IMM18(47),
        R_CKCORE_GOT_IMM18BY4(48),
        R_CKCORE_PLT_IMM18BY4(49),
        R_CKCORE_PCREL_IMM7BY4(50),
        R_CKCORE_TLS_LE32(51),
        R_CKCORE_TLS_IE32(52),
        R_CKCORE_TLS_GD32(53),
        R_CKCORE_TLS_LDM32(54),
        R_CKCORE_TLS_LDO32(55),
        R_CKCORE_TLS_DTPMOD32(56),
        R_CKCORE_TLS_DTPOFF32(57),
        R_CKCORE_TLS_TPOFF32(58),
        R_CKCORE_PCREL_FLRW_IMM8BY4(59),
        R_CKCORE_NOJSRI(60),
        R_CKCORE_CALLGRAPH(61),
        R_CKCORE_IRELATIVE(62),
        R_CKCORE_PCREL_BLOOP_IMM4BY4(63),
        R_CKCORE_PCREL_BLOOP_IMM12BY4(64),
        R_CKCORE_MAX(0);
        public final int ID;

        elf_csky_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_csky_reloc_type e : elf_csky_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }

    public enum elf_d10v_reloc_type {
        R_D10V_NONE(0),
        R_D10V_10_PCREL_R(1),
        R_D10V_10_PCREL_L(2),
        R_D10V_16(3),
        R_D10V_18(4),
        R_D10V_18_PCREL(5),
        R_D10V_32(6),
        R_D10V_GNU_VTINHERIT(7),
        R_D10V_GNU_VTENTRY(8),
        R_D10V_max(0);
        public final int ID;

        elf_d10v_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_d10v_reloc_type e : elf_d10v_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_d30v_reloc_type {
        R_D30V_NONE(0),
        R_D30V_6(1),
        R_D30V_9_PCREL(2),
        R_D30V_9_PCREL_R(3),
        R_D30V_15(4),
        R_D30V_15_PCREL(5),
        R_D30V_15_PCREL_R(6),
        R_D30V_21(7),
        R_D30V_21_PCREL(8),
        R_D30V_21_PCREL_R(9),
        R_D30V_32(10),
        R_D30V_32_PCREL(11),
        R_D30V_32_NORMAL(12),
        R_D30V_max(0);
        public final int ID;

        elf_d30v_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_d30v_reloc_type e : elf_d30v_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_dlx_reloc_type {
        R_DLX_NONE(0),
        R_DLX_RELOC_8(1),
        R_DLX_RELOC_16(2),
        R_DLX_RELOC_32(3),
        R_DLX_GNU_VTINHERIT(4),
        R_DLX_GNU_VTENTRY(5),
        R_DLX_RELOC_16_HI(6),
        R_DLX_RELOC_16_LO(7),
        R_DLX_RELOC_16_PCREL(8),
        R_DLX_RELOC_26_PCREL(9),
        R_DLX_max(0);
        public final int ID;

        elf_dlx_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_dlx_reloc_type e : elf_dlx_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_bpf_reloc_type {
        R_BPF_NONE(0),
        R_BPF_INSN_64(1),
        R_BPF_INSN_32(2),
        R_BPF_INSN_16(3),
        R_BPF_INSN_DISP16(4),
        R_BPF_DATA_8_PCREL(5),
        R_BPF_DATA_16_PCREL(6),
        R_BPF_DATA_32_PCREL(7),
        R_BPF_DATA_8(8),
        R_BPF_DATA_16(9),
        R_BPF_INSN_DISP32(10),
        R_BPF_DATA_32(11),
        R_BPF_DATA_64(12),
        R_BPF_DATA_64_PCREL(13),
        R_BPF_max(0);
        public final int ID;

        elf_bpf_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_bpf_reloc_type e : elf_bpf_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_epiphany_reloc_type {
        R_EPIPHANY_NONE(0),


        R_EPIPHANY_8(1),
        R_EPIPHANY_16(2),
        R_EPIPHANY_32(3),


        R_EPIPHANY_8_PCREL(4),
        R_EPIPHANY_16_PCREL(5),
        R_EPIPHANY_32_PCREL(6),


        R_EPIPHANY_SIMM8(7),
        R_EPIPHANY_SIMM24(8),


        R_EPIPHANY_HIGH(9),
        R_EPIPHANY_LOW(10),


        R_EPIPHANY_SIMM11(11),

        R_EPIPHANY_IMM11(12),


        R_EPIPHANY_IMM8(13),

        R_EPIPHANY_max(0);
        public final int ID;

        elf_epiphany_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_epiphany_reloc_type e : elf_epiphany_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_fr30_reloc_type {
        R_FR30_NONE(0),
        R_FR30_8(1),
        R_FR30_20(2),
        R_FR30_32(3),
        R_FR30_48(4),
        R_FR30_6_IN_4(5),
        R_FR30_8_IN_8(6),
        R_FR30_9_IN_8(7),
        R_FR30_10_IN_8(8),
        R_FR30_9_PCREL(9),
        R_FR30_12_PCREL(10),
        R_FR30_GNU_VTINHERIT(11),
        R_FR30_GNU_VTENTRY(12),
        R_FR30_max(0);
        public final int ID;

        elf_fr30_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_fr30_reloc_type e : elf_fr30_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_frv_reloc_type {
        R_FRV_NONE(0),
        R_FRV_32(1),
        R_FRV_LABEL16(2),
        R_FRV_LABEL24(3),
        R_FRV_LO16(4),
        R_FRV_HI16(5),
        R_FRV_GPREL12(6),
        R_FRV_GPRELU12(7),
        R_FRV_GPREL32(8),
        R_FRV_GPRELHI(9),
        R_FRV_GPRELLO(10),
        R_FRV_GOT12(11),
        R_FRV_GOTHI(12),
        R_FRV_GOTLO(13),
        R_FRV_FUNCDESC(14),
        R_FRV_FUNCDESC_GOT12(15),
        R_FRV_FUNCDESC_GOTHI(16),
        R_FRV_FUNCDESC_GOTLO(17),
        R_FRV_FUNCDESC_VALUE(18),
        R_FRV_FUNCDESC_GOTOFF12(19),
        R_FRV_FUNCDESC_GOTOFFHI(20),
        R_FRV_FUNCDESC_GOTOFFLO(21),
        R_FRV_GOTOFF12(22),
        R_FRV_GOTOFFHI(23),
        R_FRV_GOTOFFLO(24),
        R_FRV_GETTLSOFF(25),
        R_FRV_TLSDESC_VALUE(26),
        R_FRV_GOTTLSDESC12(27),
        R_FRV_GOTTLSDESCHI(28),
        R_FRV_GOTTLSDESCLO(29),
        R_FRV_TLSMOFF12(30),
        R_FRV_TLSMOFFHI(31),
        R_FRV_TLSMOFFLO(32),
        R_FRV_GOTTLSOFF12(33),
        R_FRV_GOTTLSOFFHI(34),
        R_FRV_GOTTLSOFFLO(35),
        R_FRV_TLSOFF(36),
        R_FRV_TLSDESC_RELAX(37),
        R_FRV_GETTLSOFF_RELAX(38),
        R_FRV_TLSOFF_RELAX(39),
        R_FRV_TLSMOFF(40),
        R_FRV_GNU_VTINHERIT(200),
        R_FRV_GNU_VTENTRY(201),
        R_FRV_max(0);
        public final int ID;

        elf_frv_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_frv_reloc_type e : elf_frv_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_ft32_reloc_type {
        R_FT32_NONE(0),
        R_FT32_32(1),
        R_FT32_16(2),
        R_FT32_8(3),
        R_FT32_10(4),
        R_FT32_20(5),
        R_FT32_17(6),
        R_FT32_18(7),
        R_FT32_RELAX(8),
        R_FT32_SC0(9),
        R_FT32_SC1(10),
        R_FT32_15(11),
        R_FT32_DIFF32(12),
        R_FT32_max(0);
        public final int ID;

        elf_ft32_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_ft32_reloc_type e : elf_ft32_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_h8_reloc_type {
        R_H8_NONE(0),
        R_H8_DIR32(1),
        R_H8_DIR32_28(2),
        R_H8_DIR32_24(3),
        R_H8_DIR32_16(4),
        R_H8_DIR32U(6),
        R_H8_DIR32U_28(7),
        R_H8_DIR32U_24(8),
        R_H8_DIR32U_20(9),
        R_H8_DIR32U_16(10),
        R_H8_DIR24(11),
        R_H8_DIR24_20(12),
        R_H8_DIR24_16(13),
        R_H8_DIR24U(14),
        R_H8_DIR24U_20(15),
        R_H8_DIR24U_16(16),
        R_H8_DIR16(17),
        R_H8_DIR16U(18),
        R_H8_DIR16S_32(19),
        R_H8_DIR16S_28(20),
        R_H8_DIR16S_24(21),
        R_H8_DIR16S_20(22),
        R_H8_DIR16S(23),
        R_H8_DIR8(24),
        R_H8_DIR8U(25),
        R_H8_DIR8Z_32(26),
        R_H8_DIR8Z_28(27),
        R_H8_DIR8Z_24(28),
        R_H8_DIR8Z_20(29),
        R_H8_DIR8Z_16(30),
        R_H8_PCREL16(31),
        R_H8_PCREL8(32),
        R_H8_BPOS(33),
        R_H8_FIRST_INVALID_DIR_RELOC(34),
        R_H8_LAST_INVALID_DIR_RELOC(58),
        R_H8_DIR16A8(59),
        R_H8_DIR16R8(60),
        R_H8_DIR24A8(61),
        R_H8_DIR24R8(62),
        R_H8_DIR32A16(63),
        R_H8_DISP32A16(64),
        R_H8_ABS32(65),
        R_H8_ABS32A16(127),
        R_H8_SYM(128),
        R_H8_OPneg(129),
        R_H8_OPadd(130),
        R_H8_OPsub(131),
        R_H8_OPmul(132),
        R_H8_OPdiv(133),
        R_H8_OPshla(134),
        R_H8_OPshra(135),
        R_H8_OPsctsize(136),
        R_H8_OPhword(137),
        R_H8_OPlword(138),
        R_H8_OPhigh(139),
        R_H8_OPlow(140),
        R_H8_OPscttop(141),
        R_H8_max(0);
        public final int ID;

        elf_h8_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_h8_reloc_type e : elf_h8_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_hppa_reloc_type {
        R_PARISC_NONE(0),


        R_PARISC_DIR32(1),


        R_PARISC_DIR21L(2),


        R_PARISC_DIR17R(3),


        R_PARISC_DIR17F(4),


        R_PARISC_DIR14R(6),


        R_PARISC_DIR14F(7),

        R_PARISC_PCREL12F(8),


        R_PARISC_PCREL32(9),


        R_PARISC_PCREL21L(10),


        R_PARISC_PCREL17R(11),


        R_PARISC_PCREL17F(12),


        R_PARISC_PCREL17C(13),


        R_PARISC_PCREL14R(14),


        R_PARISC_PCREL14F(15),


        R_PARISC_DPREL21L(18),


        R_PARISC_DPREL14WR(19),


        R_PARISC_DPREL14DR(20),


        R_PARISC_DPREL14R(22),


        R_PARISC_DPREL14F(23),

        R_PARISC_DLTREL21L(26),


        R_PARISC_DLTREL14R(30),


        R_PARISC_DLTREL14F(31),


        R_PARISC_DLTIND21L(34),


        R_PARISC_DLTIND14R(38),


        R_PARISC_DLTIND14F(39),


        R_PARISC_SETBASE(40),


        R_PARISC_SECREL32(41),


        R_PARISC_BASEREL21L(42),


        R_PARISC_BASEREL17R(43),


        R_PARISC_BASEREL17F(44),


        R_PARISC_BASEREL14R(46),


        R_PARISC_BASEREL14F(47),


        R_PARISC_SEGBASE(48),


        R_PARISC_SEGREL32(49),


        R_PARISC_PLTOFF21L(50),


        R_PARISC_PLTOFF14R(54),


        R_PARISC_PLTOFF14F(55),


        R_PARISC_LTOFF_FPTR32(57),


        R_PARISC_LTOFF_FPTR21L(58),


        R_PARISC_LTOFF_FPTR14R(62),


        R_PARISC_FPTR64(64),


        R_PARISC_PLABEL32(65),


        R_PARISC_PLABEL21L(66),


        R_PARISC_PLABEL14R(70),


        R_PARISC_PCREL64(72),


        R_PARISC_PCREL22C(73),


        R_PARISC_PCREL22F(74),


        R_PARISC_PCREL14WR(75),


        R_PARISC_PCREL14DR(76),


        R_PARISC_PCREL16F(77),


        R_PARISC_PCREL16WF(78),


        R_PARISC_PCREL16DF(79),


        R_PARISC_DIR64(80),


        R_PARISC_DIR14WR(83),


        R_PARISC_DIR14DR(84),


        R_PARISC_DIR16F(85),


        R_PARISC_DIR16WF(86),


        R_PARISC_DIR16DF(87),


        R_PARISC_GPREL64(88),


        R_PARISC_DLTREL14WR(91),


        R_PARISC_DLTREL14DR(92),


        R_PARISC_GPREL16F(93),


        R_PARISC_GPREL16WF(94),


        R_PARISC_GPREL16DF(95),


        R_PARISC_LTOFF64(96),


        R_PARISC_DLTIND14WR(99),


        R_PARISC_DLTIND14DR(100),


        R_PARISC_LTOFF16F(101),


        R_PARISC_LTOFF16WF(102),


        R_PARISC_LTOFF16DF(103),


        R_PARISC_SECREL64(104),


        R_PARISC_BASEREL14WR(107),


        R_PARISC_BASEREL14DR(108),


        R_PARISC_SEGREL64(112),


        R_PARISC_PLTOFF14WR(115),


        R_PARISC_PLTOFF14DR(116),


        R_PARISC_PLTOFF16F(117),


        R_PARISC_PLTOFF16WF(118),


        R_PARISC_PLTOFF16DF(119),


        R_PARISC_LTOFF_FPTR64(120),


        R_PARISC_LTOFF_FPTR14WR(123),


        R_PARISC_LTOFF_FPTR14DR(124),


        R_PARISC_LTOFF_FPTR16F(125),


        R_PARISC_LTOFF_FPTR16WF(126),


        R_PARISC_LTOFF_FPTR16DF(127),


        R_PARISC_COPY(128),


        R_PARISC_IPLT(129),


        R_PARISC_EPLT(130),


        R_PARISC_TPREL32(153),


        R_PARISC_TPREL21L(154),


        R_PARISC_TPREL14R(158),


        R_PARISC_LTOFF_TP21L(162),


        R_PARISC_LTOFF_TP14R(166),


        R_PARISC_LTOFF_TP14F(167),


        R_PARISC_TPREL64(216),


        R_PARISC_TPREL14WR(219),


        R_PARISC_TPREL14DR(220),


        R_PARISC_TPREL16F(221),


        R_PARISC_TPREL16WF(222),


        R_PARISC_TPREL16DF(223),


        R_PARISC_LTOFF_TP64(224),


        R_PARISC_LTOFF_TP14WR(227),


        R_PARISC_LTOFF_TP14DR(228),


        R_PARISC_LTOFF_TP16F(229),


        R_PARISC_LTOFF_TP16WF(230),


        R_PARISC_LTOFF_TP16DF(231),


        R_PARISC_GNU_VTENTRY(232),
        R_PARISC_GNU_VTINHERIT(233),

        R_PARISC_TLS_GD21L(234),
        R_PARISC_TLS_GD14R(235),
        R_PARISC_TLS_GDCALL(236),
        R_PARISC_TLS_LDM21L(237),
        R_PARISC_TLS_LDM14R(238),
        R_PARISC_TLS_LDMCALL(239),
        R_PARISC_TLS_LDO21L(240),
        R_PARISC_TLS_LDO14R(241),
        R_PARISC_TLS_DTPMOD32(242),
        R_PARISC_TLS_DTPMOD64(243),
        R_PARISC_TLS_DTPOFF32(244),
        R_PARISC_TLS_DTPOFF64(245),

        R_PARISC_UNIMPLEMENTED(0);
        public final int ID;

        elf_hppa_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_hppa_reloc_type e : elf_hppa_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }

    public enum elf_i386_reloc_type {
        R_386_NONE(0),
        R_386_32(1),
        R_386_PC32(2),
        R_386_GOT32(3),
        R_386_PLT32(4),
        R_386_COPY(5),
        R_386_GLOB_DAT(6),
        R_386_JUMP_SLOT(7),
        R_386_RELATIVE(8),
        R_386_GOTOFF(9),
        R_386_GOTPC(10),
        R_386_32PLT(11),
        FIRST_INVALID_RELOC(12),
        LAST_INVALID_RELOC(13),
        R_386_TLS_TPOFF(14),
        R_386_TLS_IE(15),
        R_386_TLS_GOTIE(16),
        R_386_TLS_LE(17),
        R_386_TLS_GD(18),
        R_386_TLS_LDM(19),
        R_386_16(20),
        R_386_PC16(21),
        R_386_8(22),
        R_386_PC8(23),
        R_386_TLS_GD_32(24),
        R_386_TLS_GD_PUSH(25),
        R_386_TLS_GD_CALL(26),
        R_386_TLS_GD_POP(27),
        R_386_TLS_LDM_32(28),
        R_386_TLS_LDM_PUSH(29),
        R_386_TLS_LDM_CALL(30),
        R_386_TLS_LDM_POP(31),
        R_386_TLS_LDO_32(32),
        R_386_TLS_IE_32(33),
        R_386_TLS_LE_32(34),
        R_386_TLS_DTPMOD32(35),
        R_386_TLS_DTPOFF32(36),
        R_386_TLS_TPOFF32(37),
        R_386_SIZE32(38),
        R_386_TLS_GOTDESC(39),
        R_386_TLS_DESC_CALL(40),
        R_386_TLS_DESC(41),
        R_386_IRELATIVE(42),

        R_386_GOT32X(43),


        R_386_USED_BY_INTEL_200(200),


        R_386_GNU_VTINHERIT(250),
        R_386_GNU_VTENTRY(251),
        R_386_max(0);
        public final int ID;

        elf_i386_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_i386_reloc_type e : elf_i386_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum i370_reloc_type {
        R_I370_NONE(0),
        R_I370_ADDR31(1),
        R_I370_ADDR32(2),
        R_I370_ADDR16(3),
        R_I370_REL31(4),
        R_I370_REL32(5),
        R_I370_ADDR12(6),
        R_I370_REL12(7),
        R_I370_ADDR8(8),
        R_I370_REL8(9),
        R_I370_COPY(10),
        R_I370_RELATIVE(11),
        R_I370_max(0);
        public final int ID;

        i370_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (i370_reloc_type e : i370_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_i860_reloc_type {
        R_860_NONE(0x00),
        R_860_32(0x01),
        R_860_COPY(0x02),
        R_860_GLOB_DAT(0x03),
        R_860_JUMP_SLOT(0x04),
        R_860_RELATIVE(0x05),
        R_860_PC26(0x30),
        R_860_PLT26(0x31),
        R_860_PC16(0x32),
        R_860_LOW0(0x40),
        R_860_SPLIT0(0x42),
        R_860_LOW1(0x44),
        R_860_SPLIT1(0x46),
        R_860_LOW2(0x48),
        R_860_SPLIT2(0x4A),
        R_860_LOW3(0x4C),
        R_860_LOGOT0(0x50),
        R_860_SPGOT0(0x52),
        R_860_LOGOT1(0x54),
        R_860_SPGOT1(0x56),
        R_860_LOGOTOFF0(0x60),
        R_860_SPGOTOFF0(0x62),
        R_860_LOGOTOFF1(0x64),
        R_860_SPGOTOFF1(0x66),
        R_860_LOGOTOFF2(0x68),
        R_860_LOGOTOFF3(0x6C),
        R_860_LOPC(0x70),
        R_860_HIGHADJ(0x80),
        R_860_HAGOT(0x90),
        R_860_HAGOTOFF(0xA0),
        R_860_HAPC(0xB0),
        R_860_HIGH(0xC0),
        R_860_HIGOT(0xD0),
        R_860_HIGOTOFF(0xE0),
        R_860_max(0);
        public final int ID;

        elf_i860_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_i860_reloc_type e : elf_i860_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_i960_reloc_type {
        R_960_NONE(0),
        R_960_12(1),
        R_960_32(2),
        R_960_IP24(3),
        R_960_SUB(4),
        R_960_OPTCALL(5),
        R_960_OPTCALLX(6),
        R_960_OPTCALLXA(7),
        R_960_max(0);
        public final int ID;

        elf_i960_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_i960_reloc_type e : elf_i960_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }

    public enum elf_ia64_reloc_type {
        R_IA64_NONE(0x00),

        R_IA64_IMM14(0x21),
        R_IA64_IMM22(0x22),
        R_IA64_IMM64(0x23),
        R_IA64_DIR32MSB(0x24),
        R_IA64_DIR32LSB(0x25),
        R_IA64_DIR64MSB(0x26),
        R_IA64_DIR64LSB(0x27),

        R_IA64_GPREL22(0x2a),
        R_IA64_GPREL64I(0x2b),
        R_IA64_GPREL32MSB(0x2c),
        R_IA64_GPREL32LSB(0x2d),
        R_IA64_GPREL64MSB(0x2e),
        R_IA64_GPREL64LSB(0x2f),

        R_IA64_LTOFF22(0x32),
        R_IA64_LTOFF64I(0x33),

        R_IA64_PLTOFF22(0x3a),
        R_IA64_PLTOFF64I(0x3b),
        R_IA64_PLTOFF64MSB(0x3e),
        R_IA64_PLTOFF64LSB(0x3f),

        R_IA64_FPTR64I(0x43),
        R_IA64_FPTR32MSB(0x44),
        R_IA64_FPTR32LSB(0x45),
        R_IA64_FPTR64MSB(0x46),
        R_IA64_FPTR64LSB(0x47),

        R_IA64_PCREL60B(0x48),
        R_IA64_PCREL21B(0x49),
        R_IA64_PCREL21M(0x4a),
        R_IA64_PCREL21F(0x4b),
        R_IA64_PCREL32MSB(0x4c),
        R_IA64_PCREL32LSB(0x4d),
        R_IA64_PCREL64MSB(0x4e),
        R_IA64_PCREL64LSB(0x4f),

        R_IA64_LTOFF_FPTR22(0x52),
        R_IA64_LTOFF_FPTR64I(0x53),
        R_IA64_LTOFF_FPTR32MSB(0x54),
        R_IA64_LTOFF_FPTR32LSB(0x55),
        R_IA64_LTOFF_FPTR64MSB(0x56),
        R_IA64_LTOFF_FPTR64LSB(0x57),

        R_IA64_SEGREL32MSB(0x5c),
        R_IA64_SEGREL32LSB(0x5d),
        R_IA64_SEGREL64MSB(0x5e),
        R_IA64_SEGREL64LSB(0x5f),

        R_IA64_SECREL32MSB(0x64),
        R_IA64_SECREL32LSB(0x65),
        R_IA64_SECREL64MSB(0x66),
        R_IA64_SECREL64LSB(0x67),

        R_IA64_REL32MSB(0x6c),
        R_IA64_REL32LSB(0x6d),
        R_IA64_REL64MSB(0x6e),
        R_IA64_REL64LSB(0x6f),

        R_IA64_LTV32MSB(0x74),
        R_IA64_LTV32LSB(0x75),
        R_IA64_LTV64MSB(0x76),
        R_IA64_LTV64LSB(0x77),

        R_IA64_PCREL21BI(0x79),
        R_IA64_PCREL22(0x7a),
        R_IA64_PCREL64I(0x7b),

        R_IA64_IPLTMSB(0x80),
        R_IA64_IPLTLSB(0x81),
        R_IA64_COPY(0x84),
        R_IA64_LTOFF22X(0x86),
        R_IA64_LDXMOV(0x87),

        R_IA64_TPREL14(0x91),
        R_IA64_TPREL22(0x92),
        R_IA64_TPREL64I(0x93),
        R_IA64_TPREL64MSB(0x96),
        R_IA64_TPREL64LSB(0x97),

        R_IA64_LTOFF_TPREL22(0x9a),

        R_IA64_DTPMOD64MSB(0xa6),
        R_IA64_DTPMOD64LSB(0xa7),
        R_IA64_LTOFF_DTPMOD22(0xaa),

        R_IA64_DTPREL14(0xb1),
        R_IA64_DTPREL22(0xb2),
        R_IA64_DTPREL64I(0xb3),
        R_IA64_DTPREL32MSB(0xb4),
        R_IA64_DTPREL32LSB(0xb5),
        R_IA64_DTPREL64MSB(0xb6),
        R_IA64_DTPREL64LSB(0xb7),

        R_IA64_LTOFF_DTPREL22(0xba),

        R_IA64_MAX_RELOC_CODE(0xba),


        R_IA64_VMS_DIR8(0x70000000),
        R_IA64_VMS_DIR16LSB(0x70000001),
        R_IA64_VMS_CALL_SIGNATURE(0x70000002),
        R_IA64_VMS_EXECLET_FUNC(0x70000003),
        R_IA64_VMS_EXECLET_DATA(0x70000004),
        R_IA64_VMS_FIX8(0x70000005),
        R_IA64_VMS_FIX16(0x70000006),
        R_IA64_VMS_FIX32(0x70000007),
        R_IA64_VMS_FIX64(0x70000008),
        R_IA64_VMS_FIXFD(0x70000009),
        R_IA64_VMS_ACC_LOAD(0x7000000a),
        R_IA64_VMS_ACC_ADD(0x7000000b),
        R_IA64_VMS_ACC_SUB(0x7000000c),
        R_IA64_VMS_ACC_MUL(0x7000000d),
        R_IA64_VMS_ACC_DIV(0x7000000e),
        R_IA64_VMS_ACC_AND(0x7000000f),
        R_IA64_VMS_ACC_IOR(0x70000010),
        R_IA64_VMS_ACC_EOR(0x70000011),
        R_IA64_VMS_ACC_ASH(0x70000012),
        R_IA64_VMS_ACC_STO8(0x70000014),
        R_IA64_VMS_ACC_STO16LSH(0x70000015),
        R_IA64_VMS_ACC_STO32LSH(0x70000016),
        R_IA64_VMS_ACC_STO64LSH(0x70000017),
        R_IA64_max(0);
        public final int ID;

        elf_ia64_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_ia64_reloc_type e : elf_ia64_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_ip2k_reloc_type {
        R_IP2K_NONE(0),
        R_IP2K_16(1),
        R_IP2K_32(2),
        R_IP2K_FR9(3),
        R_IP2K_BANK(4),
        R_IP2K_ADDR16CJP(5),
        R_IP2K_PAGE3(6),
        R_IP2K_LO8DATA(7),
        R_IP2K_HI8DATA(8),
        R_IP2K_LO8INSN(9),
        R_IP2K_HI8INSN(10),
        R_IP2K_PC_SKIP(11),
        R_IP2K_TEXT(12),
        R_IP2K_FR_OFFSET(13),
        R_IP2K_EX8DATA(14),
        R_IP2K_max(0);
        public final int ID;

        elf_ip2k_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_ip2k_reloc_type e : elf_ip2k_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_lm32_reloc_type {
        R_LM32_NONE(0),
        R_LM32_8(1),
        R_LM32_16(2),
        R_LM32_32(3),
        R_LM32_HI16(4),
        R_LM32_LO16(5),
        R_LM32_GPREL16(6),
        R_LM32_CALL(7),
        R_LM32_BRANCH(8),
        R_LM32_GNU_VTINHERIT(9),
        R_LM32_GNU_VTENTRY(10),
        R_LM32_16_GOT(11),
        R_LM32_GOTOFF_HI16(12),
        R_LM32_GOTOFF_LO16(13),
        R_LM32_COPY(14),
        R_LM32_GLOB_DAT(15),
        R_LM32_JMP_SLOT(16),
        R_LM32_RELATIVE(17),
        R_LM32_max(0);
        public final int ID;

        elf_lm32_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_lm32_reloc_type e : elf_lm32_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_iq2000_reloc_type {
        R_IQ2000_NONE(0),
        R_IQ2000_16(1),
        R_IQ2000_32(2),
        R_IQ2000_26(3),
        R_IQ2000_PC16(4),
        R_IQ2000_HI16(5),
        R_IQ2000_LO16(6),
        R_IQ2000_OFFSET_16(7),
        R_IQ2000_OFFSET_21(8),
        R_IQ2000_UHI16(9),
        R_IQ2000_32_DEBUG(10),
        R_IQ2000_GNU_VTINHERIT(200),
        R_IQ2000_GNU_VTENTRY(201),
        R_IQ2000_max(0);
        public final int ID;

        elf_iq2000_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_iq2000_reloc_type e : elf_iq2000_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_m32c_reloc_type {
        R_M32C_NONE(0),
        R_M32C_16(1),
        R_M32C_24(2),
        R_M32C_32(3),
        R_M32C_8_PCREL(4),
        R_M32C_16_PCREL(5),


        R_M32C_8(6),

        R_M32C_LO16(7),

        R_M32C_HI8(8),

        R_M32C_HI16(9),


        R_M32C_RL_JUMP(10),

        R_M32C_RL_1ADDR(11),

        R_M32C_RL_2ADDR(12),

        R_M32C_max(0);
        public final int ID;

        elf_m32c_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_m32c_reloc_type e : elf_m32c_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_m32r_reloc_type {
        R_M32R_NONE(0),

        R_M32R_16(1),
        R_M32R_32(2),
        R_M32R_24(3),
        R_M32R_10_PCREL(4),
        R_M32R_18_PCREL(5),
        R_M32R_26_PCREL(6),
        R_M32R_HI16_ULO(7),
        R_M32R_HI16_SLO(8),
        R_M32R_LO16(9),
        R_M32R_SDA16(10),
        R_M32R_GNU_VTINHERIT(11),
        R_M32R_GNU_VTENTRY(12),


        R_M32R_16_RELA(33),
        R_M32R_32_RELA(34),
        R_M32R_24_RELA(35),
        R_M32R_10_PCREL_RELA(36),
        R_M32R_18_PCREL_RELA(37),
        R_M32R_26_PCREL_RELA(38),
        R_M32R_HI16_ULO_RELA(39),
        R_M32R_HI16_SLO_RELA(40),
        R_M32R_LO16_RELA(41),
        R_M32R_SDA16_RELA(42),
        R_M32R_RELA_GNU_VTINHERIT(43),
        R_M32R_RELA_GNU_VTENTRY(44),

        R_M32R_REL32(45),

        R_M32R_GOT24(48),
        R_M32R_26_PLTREL(49),
        R_M32R_COPY(50),
        R_M32R_GLOB_DAT(51),
        R_M32R_JMP_SLOT(52),
        R_M32R_RELATIVE(53),
        R_M32R_GOTOFF(54),
        R_M32R_GOTPC24(55),
        R_M32R_GOT16_HI_ULO(56),
        R_M32R_GOT16_HI_SLO(57),
        R_M32R_GOT16_LO(58),
        R_M32R_GOTPC_HI_ULO(59),
        R_M32R_GOTPC_HI_SLO(60),
        R_M32R_GOTPC_LO(61),
        R_M32R_GOTOFF_HI_ULO(62),
        R_M32R_GOTOFF_HI_SLO(63),
        R_M32R_GOTOFF_LO(64),

        R_M32R_max(0);
        public final int ID;

        elf_m32r_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_m32r_reloc_type e : elf_m32r_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_m68k_reloc_type {
        R_68K_NONE(0),
        R_68K_32(1),
        R_68K_16(2),
        R_68K_8(3),
        R_68K_PC32(4),
        R_68K_PC16(5),
        R_68K_PC8(6),
        R_68K_GOT32(7),
        R_68K_GOT16(8),
        R_68K_GOT8(9),
        R_68K_GOT32O(10),
        R_68K_GOT16O(11),
        R_68K_GOT8O(12),
        R_68K_PLT32(13),
        R_68K_PLT16(14),
        R_68K_PLT8(15),
        R_68K_PLT32O(16),
        R_68K_PLT16O(17),
        R_68K_PLT8O(18),
        R_68K_COPY(19),
        R_68K_GLOB_DAT(20),
        R_68K_JMP_SLOT(21),
        R_68K_RELATIVE(22),

        R_68K_GNU_VTINHERIT(23),
        R_68K_GNU_VTENTRY(24),

        R_68K_TLS_GD32(25),
        R_68K_TLS_GD16(26),
        R_68K_TLS_GD8(27),
        R_68K_TLS_LDM32(28),
        R_68K_TLS_LDM16(29),
        R_68K_TLS_LDM8(30),
        R_68K_TLS_LDO32(31),
        R_68K_TLS_LDO16(32),
        R_68K_TLS_LDO8(33),
        R_68K_TLS_IE32(34),
        R_68K_TLS_IE16(35),
        R_68K_TLS_IE8(36),
        R_68K_TLS_LE32(37),
        R_68K_TLS_LE16(38),
        R_68K_TLS_LE8(39),
        R_68K_TLS_DTPMOD32(40),
        R_68K_TLS_DTPREL32(41),
        R_68K_TLS_TPREL32(42),
        R_68K_max(0);
        public final int ID;

        elf_m68k_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_m68k_reloc_type e : elf_m68k_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_m68hc11_reloc_type {
        R_M68HC11_NONE(0),
        R_M68HC11_8(1),
        R_M68HC11_HI8(2),
        R_M68HC11_LO8(3),
        R_M68HC11_PCREL_8(4),
        R_M68HC11_16(5),
        R_M68HC11_32(6),
        R_M68HC11_3B(7),
        R_M68HC11_PCREL_16(8),


        R_M68HC11_GNU_VTINHERIT(9),
        R_M68HC11_GNU_VTENTRY(10),

        R_M68HC11_24(11),
        R_M68HC11_LO16(12),
        R_M68HC11_PAGE(13),

        R_M68HC12_16B(15),
        R_M68HC12_PCREL_9(16),
        R_M68HC12_PCREL_10(17),
        R_M68HC12_HI8XG(18),
        R_M68HC12_LO8XG(19),


        R_M68HC11_RL_JUMP(20),


        R_M68HC11_RL_GROUP(21),
        R_M68HC11_max(0);
        public final int ID;

        elf_m68hc11_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_m68hc11_reloc_type e : elf_m68hc11_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_s12z_reloc_type {
        R_S12Z_NONE(0),
        R_S12Z_OPR(1),
        R_S12Z_UKNWN_2(2),
        R_S12Z_PCREL_7_15(3),
        R_S12Z_EXT24(4),
        R_S12Z_EXT18(5),
        R_S12Z_CW32(6),
        R_S12Z_EXT32(7),
        R_S12Z_max(0);
        public final int ID;

        elf_s12z_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_s12z_reloc_type e : elf_s12z_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_mcore_reloc_type {
        R_MCORE_NONE(0),
        R_MCORE_ADDR32(1),
        R_MCORE_PCRELIMM8BY4(2),
        R_MCORE_PCRELIMM11BY2(3),
        R_MCORE_PCRELIMM4BY2(4),
        R_MCORE_PCREL32(5),
        R_MCORE_PCRELJSR_IMM11BY2(6),
        R_MCORE_GNU_VTINHERIT(7),
        R_MCORE_GNU_VTENTRY(8),
        R_MCORE_RELATIVE(9),
        R_MCORE_COPY(10),
        R_MCORE_GLOB_DAT(11),
        R_MCORE_JUMP_SLOT(12),
        R_MCORE_max(0);
        public final int ID;

        elf_mcore_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_mcore_reloc_type e : elf_mcore_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_mep_reloc_type {

        R_MEP_NONE(0),
        R_RELC(1),

        R_MEP_8(2),
        R_MEP_16(3),
        R_MEP_32(4),

        R_MEP_PCREL8A2(5),
        R_MEP_PCREL12A2(6),
        R_MEP_PCREL17A2(7),
        R_MEP_PCREL24A2(8),
        R_MEP_PCABS24A2(9),

        R_MEP_LOW16(10),
        R_MEP_HI16U(11),
        R_MEP_HI16S(12),
        R_MEP_GPREL(13),
        R_MEP_TPREL(14),

        R_MEP_TPREL7(15),
        R_MEP_TPREL7A2(16),
        R_MEP_TPREL7A4(17),

        R_MEP_UIMM24(18),
        R_MEP_ADDR24A4(19),

        R_MEP_GNU_VTINHERIT(20),
        R_MEP_GNU_VTENTRY(21),

        R_MEP_max(0);
        public final int ID;

        elf_mep_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_mep_reloc_type e : elf_mep_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_metag_reloc_type {
        R_METAG_HIADDR16(0),
        R_METAG_LOADDR16(1),
        R_METAG_ADDR32(2),
        R_METAG_NONE(3),
        R_METAG_RELBRANCH(4),
        R_METAG_GETSETOFF(5),


        R_METAG_REG32OP1(6),
        R_METAG_REG32OP2(7),
        R_METAG_REG32OP3(8),
        R_METAG_REG16OP1(9),
        R_METAG_REG16OP2(10),
        R_METAG_REG16OP3(11),
        R_METAG_REG32OP4(12),

        R_METAG_HIOG(13),
        R_METAG_LOOG(14),

        R_METAG_REL8(15),
        R_METAG_REL16(16),


        R_METAG_GNU_VTINHERIT(30),
        R_METAG_GNU_VTENTRY(31),


        R_METAG_HI16_GOTOFF(32),
        R_METAG_LO16_GOTOFF(33),
        R_METAG_GETSET_GOTOFF(34),
        R_METAG_GETSET_GOT(35),
        R_METAG_HI16_GOTPC(36),
        R_METAG_LO16_GOTPC(37),
        R_METAG_HI16_PLT(38),
        R_METAG_LO16_PLT(39),
        R_METAG_RELBRANCH_PLT(40),
        R_METAG_GOTOFF(41),
        R_METAG_PLT(42),
        R_METAG_COPY(43),
        R_METAG_JMP_SLOT(44),
        R_METAG_RELATIVE(45),
        R_METAG_GLOB_DAT(46),


        R_METAG_TLS_GD(47),
        R_METAG_TLS_LDM(48),
        R_METAG_TLS_LDO_HI16(49),
        R_METAG_TLS_LDO_LO16(50),
        R_METAG_TLS_LDO(51),
        R_METAG_TLS_IE(52),
        R_METAG_TLS_IENONPIC(53),
        R_METAG_TLS_IENONPIC_HI16(54),
        R_METAG_TLS_IENONPIC_LO16(55),
        R_METAG_TLS_TPOFF(56),
        R_METAG_TLS_DTPMOD(57),
        R_METAG_TLS_DTPOFF(58),
        R_METAG_TLS_LE(59),
        R_METAG_TLS_LE_HI16(60),
        R_METAG_TLS_LE_LO16(61),

        R_METAG_MAX(0);
        public final int ID;

        elf_metag_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_metag_reloc_type e : elf_metag_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_microblaze_reloc_type {
        R_MICROBLAZE_NONE(0),
        R_MICROBLAZE_32(1),
        R_MICROBLAZE_32_PCREL(2),
        R_MICROBLAZE_64_PCREL(3),
        R_MICROBLAZE_32_PCREL_LO(4),
        R_MICROBLAZE_64(5),
        R_MICROBLAZE_32_LO(6),
        R_MICROBLAZE_SRO32(7),
        R_MICROBLAZE_SRW32(8),
        R_MICROBLAZE_64_NONE(9),
        R_MICROBLAZE_32_SYM_OP_SYM(10),
        R_MICROBLAZE_GNU_VTINHERIT(11),
        R_MICROBLAZE_GNU_VTENTRY(12),
        R_MICROBLAZE_GOTPC_64(13),
        R_MICROBLAZE_GOT_64(14),
        R_MICROBLAZE_PLT_64(15),
        R_MICROBLAZE_REL(16),
        R_MICROBLAZE_JUMP_SLOT(17),
        R_MICROBLAZE_GLOB_DAT(18),
        R_MICROBLAZE_GOTOFF_64(19),
        R_MICROBLAZE_GOTOFF_32(20),
        R_MICROBLAZE_COPY(21),
        R_MICROBLAZE_TLS(22),
        R_MICROBLAZE_TLSGD(23),
        R_MICROBLAZE_TLSLD(24),
        R_MICROBLAZE_TLSDTPMOD32(25),
        R_MICROBLAZE_TLSDTPREL32(26),
        R_MICROBLAZE_TLSDTPREL64(27),
        R_MICROBLAZE_TLSGOTTPREL32(28),
        R_MICROBLAZE_TLSTPREL32(29),
        R_MICROBLAZE_TEXTPCREL_64(30),
        R_MICROBLAZE_TEXTREL_64(31),
        R_MICROBLAZE_TEXTREL_32_LO(32),
        R_MICROBLAZE_max(0);
        public final int ID;

        elf_microblaze_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_microblaze_reloc_type e : elf_microblaze_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_mips_reloc_type {
        R_MIPS_NONE(0),
        R_MIPS_16(1),
        R_MIPS_32(2),
        R_MIPS_REL32(3),
        R_MIPS_26(4),
        R_MIPS_HI16(5),
        R_MIPS_LO16(6),
        R_MIPS_GPREL16(7),
        R_MIPS_LITERAL(8),
        R_MIPS_GOT16(9),
        R_MIPS_PC16(10),
        R_MIPS_CALL16(11),
        R_MIPS_GPREL32(12),


        R_MIPS_UNUSED1(13),
        R_MIPS_UNUSED2(14),
        R_MIPS_UNUSED3(15),
        R_MIPS_SHIFT5(16),
        R_MIPS_SHIFT6(17),
        R_MIPS_64(18),
        R_MIPS_GOT_DISP(19),
        R_MIPS_GOT_PAGE(20),
        R_MIPS_GOT_OFST(21),
        R_MIPS_GOT_HI16(22),
        R_MIPS_GOT_LO16(23),
        R_MIPS_SUB(24),
        R_MIPS_INSERT_A(25),
        R_MIPS_INSERT_B(26),
        R_MIPS_DELETE(27),
        R_MIPS_HIGHER(28),
        R_MIPS_HIGHEST(29),
        R_MIPS_CALL_HI16(30),
        R_MIPS_CALL_LO16(31),
        R_MIPS_SCN_DISP(32),
        R_MIPS_REL16(33),
        R_MIPS_ADD_IMMEDIATE(34),
        R_MIPS_PJUMP(35),
        R_MIPS_RELGOT(36),
        R_MIPS_JALR(37),

        R_MIPS_TLS_DTPMOD32(38),
        R_MIPS_TLS_DTPREL32(39),
        R_MIPS_TLS_DTPMOD64(40),
        R_MIPS_TLS_DTPREL64(41),
        R_MIPS_TLS_GD(42),
        R_MIPS_TLS_LDM(43),
        R_MIPS_TLS_DTPREL_HI16(44),
        R_MIPS_TLS_DTPREL_LO16(45),
        R_MIPS_TLS_GOTTPREL(46),
        R_MIPS_TLS_TPREL32(47),
        R_MIPS_TLS_TPREL64(48),
        R_MIPS_TLS_TPREL_HI16(49),
        R_MIPS_TLS_TPREL_LO16(50),
        R_MIPS_GLOB_DAT(51),

        R_MIPS_PC21_S2(60),
        R_MIPS_PC26_S2(61),
        R_MIPS_PC18_S3(62),
        R_MIPS_PC19_S2(63),
        R_MIPS_PCHI16(64),
        R_MIPS_PCLO16(65),
        R_MIPS_max(66),

        R_MIPS16_min(100),
        R_MIPS16_26(100),
        R_MIPS16_GPREL(101),
        R_MIPS16_GOT16(102),
        R_MIPS16_CALL16(103),
        R_MIPS16_HI16(104),
        R_MIPS16_LO16(105),
        R_MIPS16_TLS_GD(106),
        R_MIPS16_TLS_LDM(107),
        R_MIPS16_TLS_DTPREL_HI16(108),
        R_MIPS16_TLS_DTPREL_LO16(109),
        R_MIPS16_TLS_GOTTPREL(110),
        R_MIPS16_TLS_TPREL_HI16(111),
        R_MIPS16_TLS_TPREL_LO16(112),
        R_MIPS16_PC16_S1(113),
        R_MIPS16_max(114),

        R_MIPS_COPY(126),
        R_MIPS_JUMP_SLOT(127),


        R_MICROMIPS_min(130),
        R_MICROMIPS_26_S1(133),
        R_MICROMIPS_HI16(134),
        R_MICROMIPS_LO16(135),
        R_MICROMIPS_GPREL16(136),

        R_MICROMIPS_LITERAL(137),
        R_MICROMIPS_GOT16(138),

        R_MICROMIPS_PC7_S1(139),
        R_MICROMIPS_PC10_S1(140),
        R_MICROMIPS_PC16_S1(141),
        R_MICROMIPS_CALL16(142),

        R_MICROMIPS_GOT_DISP(145),
        R_MICROMIPS_GOT_PAGE(146),
        R_MICROMIPS_GOT_OFST(147),
        R_MICROMIPS_GOT_HI16(148),
        R_MICROMIPS_GOT_LO16(149),
        R_MICROMIPS_SUB(150),
        R_MICROMIPS_HIGHER(151),
        R_MICROMIPS_HIGHEST(152),
        R_MICROMIPS_CALL_HI16(153),
        R_MICROMIPS_CALL_LO16(154),
        R_MICROMIPS_SCN_DISP(155),
        R_MICROMIPS_JALR(156),
        R_MICROMIPS_HI0_LO16(157),

        R_MICROMIPS_TLS_GD(162),
        R_MICROMIPS_TLS_LDM(163),
        R_MICROMIPS_TLS_DTPREL_HI16(164),
        R_MICROMIPS_TLS_DTPREL_LO16(165),
        R_MICROMIPS_TLS_GOTTPREL(166),
        R_MICROMIPS_TLS_TPREL_HI16(169),
        R_MICROMIPS_TLS_TPREL_LO16(170),

        R_MICROMIPS_GPREL7_S2(172),
        R_MICROMIPS_PC23_S2(173),
        R_MICROMIPS_max(174),


        R_MIPS_PC32(248),
        R_MIPS_EH(249),

        R_MIPS_GNU_REL16_S2(250),

        R_MIPS_GNU_VTINHERIT(253),
        R_MIPS_GNU_VTENTRY(254),
        R_MIPS_maxext(0);
        public final int ID;

        elf_mips_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_mips_reloc_type e : elf_mips_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_mmix_reloc_type {
        R_MMIX_NONE(0),


        R_MMIX_8(1),
        R_MMIX_16(2),
        R_MMIX_24(3),
        R_MMIX_32(4),
        R_MMIX_64(5),


        R_MMIX_PC_8(6),
        R_MMIX_PC_16(7),
        R_MMIX_PC_24(8),
        R_MMIX_PC_32(9),
        R_MMIX_PC_64(10),


        R_MMIX_GNU_VTINHERIT(11),
        R_MMIX_GNU_VTENTRY(12),


        R_MMIX_GETA(13),
        R_MMIX_GETA_1(14),
        R_MMIX_GETA_2(15),
        R_MMIX_GETA_3(16),


        R_MMIX_CBRANCH(17),
        R_MMIX_CBRANCH_J(18),
        R_MMIX_CBRANCH_1(19),
        R_MMIX_CBRANCH_2(20),
        R_MMIX_CBRANCH_3(21),


        R_MMIX_PUSHJ(22),
        R_MMIX_PUSHJ_1(23),
        R_MMIX_PUSHJ_2(24),
        R_MMIX_PUSHJ_3(25),


        R_MMIX_JMP(26),
        R_MMIX_JMP_1(27),
        R_MMIX_JMP_2(28),
        R_MMIX_JMP_3(29),


        R_MMIX_ADDR19(30),


        R_MMIX_ADDR27(31),


        R_MMIX_REG_OR_BYTE(32),


        R_MMIX_REG(33),


        R_MMIX_BASE_PLUS_OFFSET(34),


        R_MMIX_LOCAL(35),


        R_MMIX_PUSHJ_STUBBABLE(36),
        R_MMIX_max(0);
        public final int ID;

        elf_mmix_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_mmix_reloc_type e : elf_mmix_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_mn10200_reloc_type {
        R_MN10200_NONE(0),
        R_MN10200_32(1),
        R_MN10200_16(2),
        R_MN10200_8(3),
        R_MN10200_24(4),
        R_MN10200_PCREL8(5),
        R_MN10200_PCREL16(6),
        R_MN10200_PCREL24(7),
        R_MN10200_max(0);
        public final int ID;

        elf_mn10200_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_mn10200_reloc_type e : elf_mn10200_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_mn10300_reloc_type {
        R_MN10300_NONE(0),
        R_MN10300_32(1),
        R_MN10300_16(2),
        R_MN10300_8(3),
        R_MN10300_PCREL32(4),
        R_MN10300_PCREL16(5),
        R_MN10300_PCREL8(6),
        R_MN10300_GNU_VTINHERIT(7),
        R_MN10300_GNU_VTENTRY(8),
        R_MN10300_24(9),
        R_MN10300_GOTPC32(10),
        R_MN10300_GOTPC16(11),
        R_MN10300_GOTOFF32(12),
        R_MN10300_GOTOFF24(13),
        R_MN10300_GOTOFF16(14),
        R_MN10300_PLT32(15),
        R_MN10300_PLT16(16),
        R_MN10300_GOT32(17),
        R_MN10300_GOT24(18),
        R_MN10300_GOT16(19),
        R_MN10300_COPY(20),
        R_MN10300_GLOB_DAT(21),
        R_MN10300_JMP_SLOT(22),
        R_MN10300_RELATIVE(23),
        R_MN10300_TLS_GD(24),
        R_MN10300_TLS_LD(25),
        R_MN10300_TLS_LDO(26),
        R_MN10300_TLS_GOTIE(27),
        R_MN10300_TLS_IE(28),
        R_MN10300_TLS_LE(29),
        R_MN10300_TLS_DTPMOD(30),
        R_MN10300_TLS_DTPOFF(31),
        R_MN10300_TLS_TPOFF(32),
        R_MN10300_SYM_DIFF(33),
        R_MN10300_ALIGN(34),
        R_MN10300_MAX(0);
        public final int ID;

        elf_mn10300_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_mn10300_reloc_type e : elf_mn10300_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_moxie_reloc_type {
        R_MOXIE_NONE(0),
        R_MOXIE_32(1),
        R_MOXIE_PCREL10(2),
        R_MOXIE_max(0);
        public final int ID;

        elf_moxie_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_moxie_reloc_type e : elf_moxie_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_mt_reloc_type {
        R_MT_NONE(0),
        R_MT_16(1),
        R_MT_32(2),
        R_MT_32_PCREL(3),
        R_MT_PC16(4),
        R_MT_HI16(5),
        R_MT_LO16(6),
        R_MT_max(0);
        public final int ID;

        elf_mt_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_mt_reloc_type e : elf_mt_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_msp430_reloc_type {
        R_MSP430_NONE(0),
        R_MSP430_32(1),
        R_MSP430_10_PCREL(2),
        R_MSP430_16(3),
        R_MSP430_16_PCREL(4),
        R_MSP430_16_BYTE(5),
        R_MSP430_16_PCREL_BYTE(6),
        R_MSP430_2X_PCREL(7),
        R_MSP430_RL_PCREL(8),
        R_MSP430_8(9),
        R_MSP430_SYM_DIFF(10),
        R_MSP430_GNU_SET_ULEB128(11),
        R_MSP430_GNU_SUB_ULEB128(12),
        R_MSP430_max(0);
        public final int ID;

        elf_msp430_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_msp430_reloc_type e : elf_msp430_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }

    public enum elf_msp430x_reloc_type {
        R_MSP430_ABS32(1),
        R_MSP430_ABS16(2),
        R_MSP430_ABS8(3),
        R_MSP430_PCR16(4),
        R_MSP430X_PCR20_EXT_SRC(5),
        R_MSP430X_PCR20_EXT_DST(6),
        R_MSP430X_PCR20_EXT_ODST(7),
        R_MSP430X_ABS20_EXT_SRC(8),
        R_MSP430X_ABS20_EXT_DST(9),
        R_MSP430X_ABS20_EXT_ODST(10),
        R_MSP430X_ABS20_ADR_SRC(11),
        R_MSP430X_ABS20_ADR_DST(12),
        R_MSP430X_PCR16(13),
        R_MSP430X_PCR20_CALL(14),
        R_MSP430X_ABS16(15),
        R_MSP430_ABS_HI16(16),
        R_MSP430_PREL31(17),
        R_MSP430_EHTYPE(18),
        R_MSP430X_10_PCREL(19),
        R_MSP430X_2X_PCREL(20),
        R_MSP430X_SYM_DIFF(21),
        R_MSP430X_GNU_SET_ULEB128(22),
        R_MSP430X_GNU_SUB_ULEB128(23),
        R_MSP430x_max(0);
        public final int ID;

        elf_msp430x_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_msp430x_reloc_type e : elf_msp430x_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_nds32_reloc_type {
        R_NDS32_NONE(0),

        R_NDS32_16(1),
        R_NDS32_32(2),
        R_NDS32_20(3),
        R_NDS32_9_PCREL(4),
        R_NDS32_15_PCREL(5),
        R_NDS32_17_PCREL(6),
        R_NDS32_25_PCREL(7),
        R_NDS32_HI20(8),
        R_NDS32_LO12S3(9),
        R_NDS32_LO12S2(10),
        R_NDS32_LO12S1(11),
        R_NDS32_LO12S0(12),
        R_NDS32_SDA15S3(13),
        R_NDS32_SDA15S2(14),
        R_NDS32_SDA15S1(15),
        R_NDS32_SDA15S0(16),
        R_NDS32_GNU_VTINHERIT(17),
        R_NDS32_GNU_VTENTRY(18),

        R_NDS32_16_RELA(19),
        R_NDS32_32_RELA(20),
        R_NDS32_20_RELA(21),
        R_NDS32_9_PCREL_RELA(22),
        R_NDS32_15_PCREL_RELA(23),
        R_NDS32_17_PCREL_RELA(24),
        R_NDS32_25_PCREL_RELA(25),
        R_NDS32_HI20_RELA(26),
        R_NDS32_LO12S3_RELA(27),
        R_NDS32_LO12S2_RELA(28),
        R_NDS32_LO12S1_RELA(29),
        R_NDS32_LO12S0_RELA(30),
        R_NDS32_SDA15S3_RELA(31),
        R_NDS32_SDA15S2_RELA(32),
        R_NDS32_SDA15S1_RELA(33),
        R_NDS32_SDA15S0_RELA(34),
        R_NDS32_RELA_GNU_VTINHERIT(35),
        R_NDS32_RELA_GNU_VTENTRY(36),

        R_NDS32_GOT20(37),
        R_NDS32_25_PLTREL(38),
        R_NDS32_COPY(39),
        R_NDS32_GLOB_DAT(40),
        R_NDS32_JMP_SLOT(41),
        R_NDS32_RELATIVE(42),
        R_NDS32_GOTOFF(43),
        R_NDS32_GOTPC20(44),
        R_NDS32_GOT_HI20(45),
        R_NDS32_GOT_LO12(46),
        R_NDS32_GOTPC_HI20(47),
        R_NDS32_GOTPC_LO12(48),
        R_NDS32_GOTOFF_HI20(49),
        R_NDS32_GOTOFF_LO12(50),

        R_NDS32_INSN16(51),

        R_NDS32_LABEL(52),
        R_NDS32_LONGCALL1(53),
        R_NDS32_LONGCALL2(54),
        R_NDS32_LONGCALL3(55),
        R_NDS32_LONGJUMP1(56),
        R_NDS32_LONGJUMP2(57),
        R_NDS32_LONGJUMP3(58),
        R_NDS32_LOADSTORE(59),
        R_NDS32_9_FIXED_RELA(60),
        R_NDS32_15_FIXED_RELA(61),
        R_NDS32_17_FIXED_RELA(62),
        R_NDS32_25_FIXED_RELA(63),
        R_NDS32_PLTREL_HI20(64),
        R_NDS32_PLTREL_LO12(65),
        R_NDS32_PLT_GOTREL_HI20(66),
        R_NDS32_PLT_GOTREL_LO12(67),
        R_NDS32_SDA12S2_DP_RELA(68),
        R_NDS32_SDA12S2_SP_RELA(69),
        R_NDS32_LO12S2_DP_RELA(70),
        R_NDS32_LO12S2_SP_RELA(71),
        R_NDS32_LO12S0_ORI_RELA(72),
        R_NDS32_SDA16S3_RELA(73),
        R_NDS32_SDA17S2_RELA(74),
        R_NDS32_SDA18S1_RELA(75),
        R_NDS32_SDA19S0_RELA(76),
        R_NDS32_DWARF2_OP1_RELA(77),
        R_NDS32_DWARF2_OP2_RELA(78),
        R_NDS32_DWARF2_LEB_RELA(79),
        R_NDS32_UPDATE_TA_RELA(80),
        R_NDS32_9_PLTREL(81),
        R_NDS32_PLT_GOTREL_LO20(82),
        R_NDS32_PLT_GOTREL_LO15(83),
        R_NDS32_PLT_GOTREL_LO19(84),
        R_NDS32_GOT_LO15(85),
        R_NDS32_GOT_LO19(86),
        R_NDS32_GOTOFF_LO15(87),
        R_NDS32_GOTOFF_LO19(88),
        R_NDS32_GOT15S2_RELA(89),
        R_NDS32_GOT17S2_RELA(90),
        R_NDS32_5_RELA(91),
        R_NDS32_10_UPCREL_RELA(92),
        R_NDS32_SDA_FP7U2_RELA(93),
        R_NDS32_WORD_9_PCREL_RELA(94),
        R_NDS32_25_ABS_RELA(95),
        R_NDS32_17IFC_PCREL_RELA(96),
        R_NDS32_10IFCU_PCREL_RELA(97),

        R_NDS32_TLS_LE_HI20(98),
        R_NDS32_TLS_LE_LO12(99),
        R_NDS32_TLS_IE_HI20(100),
        R_NDS32_TLS_IE_LO12S2(101),
        R_NDS32_TLS_TPOFF(102),
        R_NDS32_TLS_LE_20(103),
        R_NDS32_TLS_LE_15S0(104),
        R_NDS32_TLS_LE_15S1(105),
        R_NDS32_TLS_LE_15S2(106),
        R_NDS32_LONGCALL4(107),
        R_NDS32_LONGCALL5(108),
        R_NDS32_LONGCALL6(109),
        R_NDS32_LONGJUMP4(110),
        R_NDS32_LONGJUMP5(111),
        R_NDS32_LONGJUMP6(112),
        R_NDS32_LONGJUMP7(113),


        R_NDS32_TLS_IE_LO12(115),
        R_NDS32_TLS_IEGP_HI20(116),
        R_NDS32_TLS_IEGP_LO12(117),
        R_NDS32_TLS_IEGP_LO12S2(118),
        R_NDS32_TLS_DESC(119),
        R_NDS32_TLS_DESC_HI20(120),
        R_NDS32_TLS_DESC_LO12(121),
        R_NDS32_TLS_DESC_20(122),
        R_NDS32_TLS_DESC_SDA17S2(123),


        R_NDS32_RELAX_ENTRY(192),
        R_NDS32_GOT_SUFF(193),
        R_NDS32_GOTOFF_SUFF(194),
        R_NDS32_PLT_GOT_SUFF(195),
        R_NDS32_MULCALL_SUFF(196),
        R_NDS32_PTR(197),
        R_NDS32_PTR_COUNT(198),
        R_NDS32_PTR_RESOLVED(199),
        R_NDS32_PLTBLOCK(200),
        R_NDS32_RELAX_REGION_BEGIN(201),
        R_NDS32_RELAX_REGION_END(202),
        R_NDS32_MINUEND(203),
        R_NDS32_SUBTRAHEND(204),
        R_NDS32_DIFF8(205),
        R_NDS32_DIFF16(206),
        R_NDS32_DIFF32(207),
        R_NDS32_DIFF_ULEB128(208),
        R_NDS32_DATA(209),
        R_NDS32_TRAN(210),

        R_NDS32_TLS_LE_ADD(211),
        R_NDS32_TLS_LE_LS(212),
        R_NDS32_EMPTY(213),
        R_NDS32_TLS_DESC_ADD(214),
        R_NDS32_TLS_DESC_FUNC(215),
        R_NDS32_TLS_DESC_CALL(216),
        R_NDS32_TLS_DESC_MEM(217),
        R_NDS32_RELAX_REMOVE(218),
        R_NDS32_RELAX_GROUP(219),
        R_NDS32_TLS_IEGP_LW(220),
        R_NDS32_LSI(221),


        R_NDS32_max(0);
        public final int ID;

        elf_nds32_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_nds32_reloc_type e : elf_nds32_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_nios2_reloc_type {

        R_NIOS2_NONE(0),
        R_NIOS2_S16(1),
        R_NIOS2_U16(2),
        R_NIOS2_PCREL16(3),
        R_NIOS2_CALL26(4),
        R_NIOS2_IMM5(5),
        R_NIOS2_CACHE_OPX(6),
        R_NIOS2_IMM6(7),
        R_NIOS2_IMM8(8),
        R_NIOS2_HI16(9),
        R_NIOS2_LO16(10),
        R_NIOS2_HIADJ16(11),
        R_NIOS2_BFD_RELOC_32(12),
        R_NIOS2_BFD_RELOC_16(13),
        R_NIOS2_BFD_RELOC_8(14),
        R_NIOS2_GPREL(15),
        R_NIOS2_GNU_VTINHERIT(16),
        R_NIOS2_GNU_VTENTRY(17),
        R_NIOS2_UJMP(18),
        R_NIOS2_CJMP(19),
        R_NIOS2_CALLR(20),
        R_NIOS2_ALIGN(21),
        R_NIOS2_GOT16(22),
        R_NIOS2_CALL16(23),
        R_NIOS2_GOTOFF_LO(24),
        R_NIOS2_GOTOFF_HA(25),
        R_NIOS2_PCREL_LO(26),
        R_NIOS2_PCREL_HA(27),
        R_NIOS2_TLS_GD16(28),
        R_NIOS2_TLS_LDM16(29),
        R_NIOS2_TLS_LDO16(30),
        R_NIOS2_TLS_IE16(31),
        R_NIOS2_TLS_LE16(32),
        R_NIOS2_TLS_DTPMOD(33),
        R_NIOS2_TLS_DTPREL(34),
        R_NIOS2_TLS_TPREL(35),
        R_NIOS2_COPY(36),
        R_NIOS2_GLOB_DAT(37),
        R_NIOS2_JUMP_SLOT(38),
        R_NIOS2_RELATIVE(39),
        R_NIOS2_GOTOFF(40),
        R_NIOS2_CALL26_NOAT(41),
        R_NIOS2_GOT_LO(42),
        R_NIOS2_GOT_HA(43),
        R_NIOS2_CALL_LO(44),
        R_NIOS2_CALL_HA(45),


        R_NIOS2_R2_S12(64),
        R_NIOS2_R2_I10_1_PCREL(65),
        R_NIOS2_R2_T1I7_1_PCREL(66),
        R_NIOS2_R2_T1I7_2(67),
        R_NIOS2_R2_T2I4(68),
        R_NIOS2_R2_T2I4_1(69),
        R_NIOS2_R2_T2I4_2(70),
        R_NIOS2_R2_X1I7_2(71),
        R_NIOS2_R2_X2L5(72),
        R_NIOS2_R2_F1I5_2(73),
        R_NIOS2_R2_L5I4X1(74),
        R_NIOS2_R2_T1X1I6(75),
        R_NIOS2_R2_T1X1I6_2(76),


        R_NIOS2_ILLEGAL(77),
        R_NIOS2_maxext(0);
        public final int ID;

        elf_nios2_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_nios2_reloc_type e : elf_nios2_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_or1k_reloc_type {
        R_OR1K_NONE(0),
        R_OR1K_32(1),
        R_OR1K_16(2),
        R_OR1K_8(3),
        R_OR1K_LO_16_IN_INSN(4),
        R_OR1K_HI_16_IN_INSN(5),
        R_OR1K_INSN_REL_26(6),
        R_OR1K_GNU_VTENTRY(7),
        R_OR1K_GNU_VTINHERIT(8),
        R_OR1K_32_PCREL(9),
        R_OR1K_16_PCREL(10),
        R_OR1K_8_PCREL(11),
        R_OR1K_GOTPC_HI16(12),
        R_OR1K_GOTPC_LO16(13),
        R_OR1K_GOT16(14),
        R_OR1K_PLT26(15),
        R_OR1K_GOTOFF_HI16(16),
        R_OR1K_GOTOFF_LO16(17),
        R_OR1K_COPY(18),
        R_OR1K_GLOB_DAT(19),
        R_OR1K_JMP_SLOT(20),
        R_OR1K_RELATIVE(21),
        R_OR1K_TLS_GD_HI16(22),
        R_OR1K_TLS_GD_LO16(23),
        R_OR1K_TLS_LDM_HI16(24),
        R_OR1K_TLS_LDM_LO16(25),
        R_OR1K_TLS_LDO_HI16(26),
        R_OR1K_TLS_LDO_LO16(27),
        R_OR1K_TLS_IE_HI16(28),
        R_OR1K_TLS_IE_LO16(29),
        R_OR1K_TLS_LE_HI16(30),
        R_OR1K_TLS_LE_LO16(31),
        R_OR1K_TLS_TPOFF(32),
        R_OR1K_TLS_DTPOFF(33),
        R_OR1K_TLS_DTPMOD(34),
        R_OR1K_AHI16(35),
        R_OR1K_GOTOFF_AHI16(36),
        R_OR1K_TLS_IE_AHI16(37),
        R_OR1K_TLS_LE_AHI16(38),
        R_OR1K_SLO16(39),
        R_OR1K_GOTOFF_SLO16(40),
        R_OR1K_TLS_LE_SLO16(41),
        R_OR1K_PCREL_PG21(42),
        R_OR1K_GOT_PG21(43),
        R_OR1K_TLS_GD_PG21(44),
        R_OR1K_TLS_LDM_PG21(45),
        R_OR1K_TLS_IE_PG21(46),
        R_OR1K_LO13(47),
        R_OR1K_GOT_LO13(48),
        R_OR1K_TLS_GD_LO13(49),
        R_OR1K_TLS_LDM_LO13(50),
        R_OR1K_TLS_IE_LO13(51),
        R_OR1K_SLO13(52),
        R_OR1K_PLTA26(53),
        R_OR1K_GOT_AHI16(54),
        R_OR1K_max(0);
        public final int ID;

        elf_or1k_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_or1k_reloc_type e : elf_or1k_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_pj_reloc_type {
        R_PJ_NONE(0),
        R_PJ_DATA_DIR32(1),
        R_PJ_CODE_REL32(2),
        R_PJ_CODE_REL16(3),
        R_PJ_CODE_DIR32(6),
        R_PJ_CODE_DIR16(7),
        R_PJ_CODE_LO16(13),
        R_PJ_CODE_HI16(14),
        R_PJ_GNU_VTINHERIT(15),
        R_PJ_GNU_VTENTRY(16),
        R_PJ_max(0);
        public final int ID;

        elf_pj_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_pj_reloc_type e : elf_pj_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_ppc_reloc_type {
        R_PPC_NONE(0),
        R_PPC_ADDR32(1),
        R_PPC_ADDR24(2),
        R_PPC_ADDR16(3),
        R_PPC_ADDR16_LO(4),
        R_PPC_ADDR16_HI(5),
        R_PPC_ADDR16_HA(6),
        R_PPC_ADDR14(7),
        R_PPC_ADDR14_BRTAKEN(8),
        R_PPC_ADDR14_BRNTAKEN(9),
        R_PPC_REL24(10),
        R_PPC_REL14(11),
        R_PPC_REL14_BRTAKEN(12),
        R_PPC_REL14_BRNTAKEN(13),
        R_PPC_GOT16(14),
        R_PPC_GOT16_LO(15),
        R_PPC_GOT16_HI(16),
        R_PPC_GOT16_HA(17),
        R_PPC_PLTREL24(18),
        R_PPC_COPY(19),
        R_PPC_GLOB_DAT(20),
        R_PPC_JMP_SLOT(21),
        R_PPC_RELATIVE(22),
        R_PPC_LOCAL24PC(23),
        R_PPC_UADDR32(24),
        R_PPC_UADDR16(25),
        R_PPC_REL32(26),
        R_PPC_PLT32(27),
        R_PPC_PLTREL32(28),
        R_PPC_PLT16_LO(29),
        R_PPC_PLT16_HI(30),
        R_PPC_PLT16_HA(31),
        R_PPC_SDAREL16(32),
        R_PPC_SECTOFF(33),
        R_PPC_SECTOFF_LO(34),
        R_PPC_SECTOFF_HI(35),
        R_PPC_SECTOFF_HA(36),
        R_PPC_ADDR30(37),


        R_PPC_RELAX(48),
        R_PPC_RELAX_PLT(49),
        R_PPC_RELAX_PLTREL24(50),

        R_PPC_16DX_HA(51),


        R_PPC_TLS(67),
        R_PPC_DTPMOD32(68),
        R_PPC_TPREL16(69),
        R_PPC_TPREL16_LO(70),
        R_PPC_TPREL16_HI(71),
        R_PPC_TPREL16_HA(72),
        R_PPC_TPREL32(73),
        R_PPC_DTPREL16(74),
        R_PPC_DTPREL16_LO(75),
        R_PPC_DTPREL16_HI(76),
        R_PPC_DTPREL16_HA(77),
        R_PPC_DTPREL32(78),
        R_PPC_GOT_TLSGD16(79),
        R_PPC_GOT_TLSGD16_LO(80),
        R_PPC_GOT_TLSGD16_HI(81),
        R_PPC_GOT_TLSGD16_HA(82),
        R_PPC_GOT_TLSLD16(83),
        R_PPC_GOT_TLSLD16_LO(84),
        R_PPC_GOT_TLSLD16_HI(85),
        R_PPC_GOT_TLSLD16_HA(86),
        R_PPC_GOT_TPREL16(87),
        R_PPC_GOT_TPREL16_LO(88),
        R_PPC_GOT_TPREL16_HI(89),
        R_PPC_GOT_TPREL16_HA(90),
        R_PPC_GOT_DTPREL16(91),
        R_PPC_GOT_DTPREL16_LO(92),
        R_PPC_GOT_DTPREL16_HI(93),
        R_PPC_GOT_DTPREL16_HA(94),
        R_PPC_TLSGD(95),
        R_PPC_TLSLD(96),


        R_PPC_EMB_NADDR32(101),
        R_PPC_EMB_NADDR16(102),
        R_PPC_EMB_NADDR16_LO(103),
        R_PPC_EMB_NADDR16_HI(104),
        R_PPC_EMB_NADDR16_HA(105),
        R_PPC_EMB_SDAI16(106),
        R_PPC_EMB_SDA2I16(107),
        R_PPC_EMB_SDA2REL(108),
        R_PPC_EMB_SDA21(109),
        R_PPC_EMB_MRKREF(110),
        R_PPC_EMB_RELSEC16(111),
        R_PPC_EMB_RELST_LO(112),
        R_PPC_EMB_RELST_HI(113),
        R_PPC_EMB_RELST_HA(114),
        R_PPC_EMB_BIT_FLD(115),
        R_PPC_EMB_RELSDA(116),


        R_PPC_PLTSEQ(119),
        R_PPC_PLTCALL(120),


        R_PPC_VLE_REL8(216),
        R_PPC_VLE_REL15(217),
        R_PPC_VLE_REL24(218),
        R_PPC_VLE_LO16A(219),
        R_PPC_VLE_LO16D(220),
        R_PPC_VLE_HI16A(221),
        R_PPC_VLE_HI16D(222),
        R_PPC_VLE_HA16A(223),
        R_PPC_VLE_HA16D(224),
        R_PPC_VLE_SDA21(225),
        R_PPC_VLE_SDA21_LO(226),
        R_PPC_VLE_SDAREL_LO16A(227),
        R_PPC_VLE_SDAREL_LO16D(228),
        R_PPC_VLE_SDAREL_HI16A(229),
        R_PPC_VLE_SDAREL_HI16D(230),
        R_PPC_VLE_SDAREL_HA16A(231),
        R_PPC_VLE_SDAREL_HA16D(232),
        R_PPC_VLE_ADDR20(233),


        R_PPC_REL16DX_HA(246),


        R_PPC_IRELATIVE(248),


        R_PPC_REL16(249),
        R_PPC_REL16_LO(250),
        R_PPC_REL16_HI(251),
        R_PPC_REL16_HA(252),


        R_PPC_GNU_VTINHERIT(253),
        R_PPC_GNU_VTENTRY(254),


        R_PPC_TOC16(255),

        R_PPC_max(0);
        public final int ID;

        elf_ppc_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_ppc_reloc_type e : elf_ppc_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_ppc64_reloc_type {
        R_PPC64_NONE(0),
        R_PPC64_ADDR32(1),
        R_PPC64_ADDR24(2),
        R_PPC64_ADDR16(3),
        R_PPC64_ADDR16_LO(4),
        R_PPC64_ADDR16_HI(5),
        R_PPC64_ADDR16_HA(6),
        R_PPC64_ADDR14(7),
        R_PPC64_ADDR14_BRTAKEN(8),
        R_PPC64_ADDR14_BRNTAKEN(9),
        R_PPC64_REL24(10),
        R_PPC64_REL14(11),
        R_PPC64_REL14_BRTAKEN(12),
        R_PPC64_REL14_BRNTAKEN(13),
        R_PPC64_GOT16(14),
        R_PPC64_GOT16_LO(15),
        R_PPC64_GOT16_HI(16),
        R_PPC64_GOT16_HA(17),

        R_PPC64_COPY(19),
        R_PPC64_GLOB_DAT(20),
        R_PPC64_JMP_SLOT(21),
        R_PPC64_RELATIVE(22),

        R_PPC64_UADDR32(24),
        R_PPC64_UADDR16(25),
        R_PPC64_REL32(26),
        R_PPC64_PLT32(27),
        R_PPC64_PLTREL32(28),
        R_PPC64_PLT16_LO(29),
        R_PPC64_PLT16_HI(30),
        R_PPC64_PLT16_HA(31),

        R_PPC64_SECTOFF(33),
        R_PPC64_SECTOFF_LO(34),
        R_PPC64_SECTOFF_HI(35),
        R_PPC64_SECTOFF_HA(36),
        R_PPC64_REL30(37),
        R_PPC64_ADDR64(38),
        R_PPC64_ADDR16_HIGHER(39),
        R_PPC64_ADDR16_HIGHERA(40),
        R_PPC64_ADDR16_HIGHEST(41),
        R_PPC64_ADDR16_HIGHESTA(42),
        R_PPC64_UADDR64(43),
        R_PPC64_REL64(44),
        R_PPC64_PLT64(45),
        R_PPC64_PLTREL64(46),
        R_PPC64_TOC16(47),
        R_PPC64_TOC16_LO(48),
        R_PPC64_TOC16_HI(49),
        R_PPC64_TOC16_HA(50),
        R_PPC64_TOC(51),
        R_PPC64_PLTGOT16(52),
        R_PPC64_PLTGOT16_LO(53),
        R_PPC64_PLTGOT16_HI(54),
        R_PPC64_PLTGOT16_HA(55),


        R_PPC64_ADDR16_DS(56),
        R_PPC64_ADDR16_LO_DS(57),
        R_PPC64_GOT16_DS(58),
        R_PPC64_GOT16_LO_DS(59),
        R_PPC64_PLT16_LO_DS(60),
        R_PPC64_SECTOFF_DS(61),
        R_PPC64_SECTOFF_LO_DS(62),
        R_PPC64_TOC16_DS(63),
        R_PPC64_TOC16_LO_DS(64),
        R_PPC64_PLTGOT16_DS(65),
        R_PPC64_PLTGOT16_LO_DS(66),


        R_PPC64_TLS(67),
        R_PPC64_DTPMOD64(68),
        R_PPC64_TPREL16(69),
        R_PPC64_TPREL16_LO(70),
        R_PPC64_TPREL16_HI(71),
        R_PPC64_TPREL16_HA(72),
        R_PPC64_TPREL64(73),
        R_PPC64_DTPREL16(74),
        R_PPC64_DTPREL16_LO(75),
        R_PPC64_DTPREL16_HI(76),
        R_PPC64_DTPREL16_HA(77),
        R_PPC64_DTPREL64(78),
        R_PPC64_GOT_TLSGD16(79),
        R_PPC64_GOT_TLSGD16_LO(80),
        R_PPC64_GOT_TLSGD16_HI(81),
        R_PPC64_GOT_TLSGD16_HA(82),
        R_PPC64_GOT_TLSLD16(83),
        R_PPC64_GOT_TLSLD16_LO(84),
        R_PPC64_GOT_TLSLD16_HI(85),
        R_PPC64_GOT_TLSLD16_HA(86),
        R_PPC64_GOT_TPREL16_DS(87),
        R_PPC64_GOT_TPREL16_LO_DS(88),
        R_PPC64_GOT_TPREL16_HI(89),
        R_PPC64_GOT_TPREL16_HA(90),
        R_PPC64_GOT_DTPREL16_DS(91),
        R_PPC64_GOT_DTPREL16_LO_DS(92),
        R_PPC64_GOT_DTPREL16_HI(93),
        R_PPC64_GOT_DTPREL16_HA(94),
        R_PPC64_TPREL16_DS(95),
        R_PPC64_TPREL16_LO_DS(96),
        R_PPC64_TPREL16_HIGHER(97),
        R_PPC64_TPREL16_HIGHERA(98),
        R_PPC64_TPREL16_HIGHEST(99),
        R_PPC64_TPREL16_HIGHESTA(100),
        R_PPC64_DTPREL16_DS(101),
        R_PPC64_DTPREL16_LO_DS(102),
        R_PPC64_DTPREL16_HIGHER(103),
        R_PPC64_DTPREL16_HIGHERA(104),
        R_PPC64_DTPREL16_HIGHEST(105),
        R_PPC64_DTPREL16_HIGHESTA(106),
        R_PPC64_TLSGD(107),
        R_PPC64_TLSLD(108),
        R_PPC64_TOCSAVE(109),


        R_PPC64_ADDR16_HIGH(110),
        R_PPC64_ADDR16_HIGHA(111),
        R_PPC64_TPREL16_HIGH(112),
        R_PPC64_TPREL16_HIGHA(113),
        R_PPC64_DTPREL16_HIGH(114),
        R_PPC64_DTPREL16_HIGHA(115),


        R_PPC64_REL24_NOTOC(116),
        R_PPC64_ADDR64_LOCAL(117),
        R_PPC64_ENTRY(118),


        R_PPC64_PLTSEQ(119),
        R_PPC64_PLTCALL(120),


        R_PPC64_PLTSEQ_NOTOC(121),
        R_PPC64_PLTCALL_NOTOC(122),
        R_PPC64_PCREL_OPT(123),

        R_PPC64_D34(128),
        R_PPC64_D34_LO(129),
        R_PPC64_D34_HI30(130),
        R_PPC64_D34_HA30(131),
        R_PPC64_PCREL34(132),
        R_PPC64_GOT_PCREL34(133),
        R_PPC64_PLT_PCREL34(134),
        R_PPC64_PLT_PCREL34_NOTOC(135),
        R_PPC64_ADDR16_HIGHER34(136),
        R_PPC64_ADDR16_HIGHERA34(137),
        R_PPC64_ADDR16_HIGHEST34(138),
        R_PPC64_ADDR16_HIGHESTA34(139),
        R_PPC64_REL16_HIGHER34(140),
        R_PPC64_REL16_HIGHERA34(141),
        R_PPC64_REL16_HIGHEST34(142),
        R_PPC64_REL16_HIGHESTA34(143),
        R_PPC64_D28(144),
        R_PPC64_PCREL28(145),
        R_PPC64_TPREL34(146),
        R_PPC64_DTPREL34(147),
        R_PPC64_GOT_TLSGD_PCREL34(148),
        R_PPC64_GOT_TLSLD_PCREL34(149),
        R_PPC64_GOT_TPREL_PCREL34(150),
        R_PPC64_GOT_DTPREL_PCREL34(151),


        R_PPC64_LO_DS_OPT(200),
        R_PPC64_16DX_HA(201),


        R_PPC64_REL16_HIGH(240),
        R_PPC64_REL16_HIGHA(241),
        R_PPC64_REL16_HIGHER(242),
        R_PPC64_REL16_HIGHERA(243),
        R_PPC64_REL16_HIGHEST(244),
        R_PPC64_REL16_HIGHESTA(245),


        R_PPC64_REL16DX_HA(246),


        R_PPC64_JMP_IREL(247),
        R_PPC64_IRELATIVE(248),


        R_PPC64_REL16(249),
        R_PPC64_REL16_LO(250),
        R_PPC64_REL16_HI(251),
        R_PPC64_REL16_HA(252),


        R_PPC64_GNU_VTINHERIT(253),
        R_PPC64_GNU_VTENTRY(254),

        R_PPC64_max(0);
        public final int ID;

        elf_ppc64_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_ppc64_reloc_type e : elf_ppc64_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }

    static int ppc64_decode_local_entry(int other) {
        return ((1 << other) >> 2) << 2;
    }


    static int ppc64_encode_local_entry(int val) {
        return (val >= 4 * 4
                ? (val >= 8 * 4
                ? (val >= 16 * 4 ? 6 : 5)
                : 4)
                : (val >= 2 * 4
                ? 3
                : (val >= 1 * 4 ? 2 : 0)));
    }


    public enum elf_pru_reloc_type {
        R_PRU_NONE(0),
        R_PRU_16_PMEM(5),
        R_PRU_U16_PMEMIMM(6),
        R_PRU_BFD_RELOC_16(8),
        R_PRU_U16(9),
        R_PRU_32_PMEM(10),
        R_PRU_BFD_RELOC_32(11),
        R_PRU_S10_PCREL(14),
        R_PRU_U8_PCREL(15),
        R_PRU_LDI32(18),


        R_PRU_GNU_BFD_RELOC_8(64),
        R_PRU_GNU_DIFF8(65),
        R_PRU_GNU_DIFF16(66),
        R_PRU_GNU_DIFF32(67),
        R_PRU_GNU_DIFF16_PMEM(68),
        R_PRU_GNU_DIFF32_PMEM(69),
        R_PRU_ILLEGAL(70),
        R_PRU_maxext(0);
        public final int ID;

        elf_pru_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_pru_reloc_type e : elf_pru_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_riscv_reloc_type {
        R_RISCV_NONE(0),
        R_RISCV_32(1),
        R_RISCV_64(2),
        R_RISCV_RELATIVE(3),
        R_RISCV_COPY(4),
        R_RISCV_JUMP_SLOT(5),
        R_RISCV_TLS_DTPMOD32(6),
        R_RISCV_TLS_DTPMOD64(7),
        R_RISCV_TLS_DTPREL32(8),
        R_RISCV_TLS_DTPREL64(9),
        R_RISCV_TLS_TPREL32(10),
        R_RISCV_TLS_TPREL64(11),


        R_RISCV_BRANCH(16),
        R_RISCV_JAL(17),
        R_RISCV_CALL(18),
        R_RISCV_CALL_PLT(19),
        R_RISCV_GOT_HI20(20),
        R_RISCV_TLS_GOT_HI20(21),
        R_RISCV_TLS_GD_HI20(22),
        R_RISCV_PCREL_HI20(23),
        R_RISCV_PCREL_LO12_I(24),
        R_RISCV_PCREL_LO12_S(25),
        R_RISCV_HI20(26),
        R_RISCV_LO12_I(27),
        R_RISCV_LO12_S(28),
        R_RISCV_TPREL_HI20(29),
        R_RISCV_TPREL_LO12_I(30),
        R_RISCV_TPREL_LO12_S(31),
        R_RISCV_TPREL_ADD(32),
        R_RISCV_ADD8(33),
        R_RISCV_ADD16(34),
        R_RISCV_ADD32(35),
        R_RISCV_ADD64(36),
        R_RISCV_SUB8(37),
        R_RISCV_SUB16(38),
        R_RISCV_SUB32(39),
        R_RISCV_SUB64(40),
        R_RISCV_GNU_VTINHERIT(41),
        R_RISCV_GNU_VTENTRY(42),
        R_RISCV_ALIGN(43),
        R_RISCV_RVC_BRANCH(44),
        R_RISCV_RVC_JUMP(45),
        R_RISCV_RVC_LUI(46),
        R_RISCV_GPREL_I(47),
        R_RISCV_GPREL_S(48),
        R_RISCV_TPREL_I(49),
        R_RISCV_TPREL_S(50),
        R_RISCV_RELAX(51),
        R_RISCV_SUB6(52),
        R_RISCV_SET6(53),
        R_RISCV_SET8(54),
        R_RISCV_SET16(55),
        R_RISCV_SET32(56),
        R_RISCV_32_PCREL(57),
        R_RISCV_IRELATIVE(58),
        R_RISCV_max(0);
        public final int ID;

        elf_riscv_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_riscv_reloc_type e : elf_riscv_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }

    public enum elf_rl78_reloc_type {
        R_RL78_NONE(0x00),

        R_RL78_DIR32(0x01),
        R_RL78_DIR24S(0x02),
        R_RL78_DIR16(0x03),
        R_RL78_DIR16U(0x04),
        R_RL78_DIR16S(0x05),
        R_RL78_DIR8(0x06),
        R_RL78_DIR8U(0x07),
        R_RL78_DIR8S(0x08),


        R_RL78_DIR24S_PCREL(0x09),
        R_RL78_DIR16S_PCREL(0x0a),
        R_RL78_DIR8S_PCREL(0x0b),


        R_RL78_DIR16UL(0x0c),
        R_RL78_DIR16UW(0x0d),
        R_RL78_DIR8UL(0x0e),
        R_RL78_DIR8UW(0x0f),
        R_RL78_DIR32_REV(0x10),
        R_RL78_DIR16_REV(0x11),
        R_RL78_DIR3U_PCREL(0x12),


        R_RL78_RH_RELAX(0x2d),
        R_RL78_RH_SFR(0x2e),
        R_RL78_RH_SADDR(0x2f),


        R_RL78_ABS32(0x41),
        R_RL78_ABS24S(0x42),
        R_RL78_ABS16(0x43),
        R_RL78_ABS16U(0x44),
        R_RL78_ABS16S(0x45),
        R_RL78_ABS8(0x46),
        R_RL78_ABS8U(0x47),
        R_RL78_ABS8S(0x48),
        R_RL78_ABS24S_PCREL(0x49),
        R_RL78_ABS16S_PCREL(0x4a),
        R_RL78_ABS8S_PCREL(0x4b),
        R_RL78_ABS16UL(0x4c),
        R_RL78_ABS16UW(0x4d),
        R_RL78_ABS8UL(0x4e),
        R_RL78_ABS8UW(0x4f),
        R_RL78_ABS32_REV(0x50),
        R_RL78_ABS16_REV(0x51),

        R_RL78_SYM(0x80),
        R_RL78_OPneg(0x81),
        R_RL78_OPadd(0x82),
        R_RL78_OPsub(0x83),
        R_RL78_OPmul(0x84),
        R_RL78_OPdiv(0x85),
        R_RL78_OPshla(0x86),
        R_RL78_OPshra(0x87),
        R_RL78_OPsctsize(0x88),
        R_RL78_OPscttop(0x8d),
        R_RL78_OPand(0x90),
        R_RL78_OPor(0x91),
        R_RL78_OPxor(0x92),
        R_RL78_OPnot(0x93),
        R_RL78_OPmod(0x94),
        R_RL78_OPromtop(0x95),
        R_RL78_OPramtop(0x96),

        R_RL78_max(0);
        public final int ID;

        elf_rl78_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_rl78_reloc_type e : elf_rl78_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_rx_reloc_type {
        R_RX_NONE(0x00),

        R_RX_DIR32(0x01),
        R_RX_DIR24S(0x02),
        R_RX_DIR16(0x03),
        R_RX_DIR16U(0x04),
        R_RX_DIR16S(0x05),
        R_RX_DIR8(0x06),
        R_RX_DIR8U(0x07),
        R_RX_DIR8S(0x08),


        R_RX_DIR24S_PCREL(0x09),
        R_RX_DIR16S_PCREL(0x0a),
        R_RX_DIR8S_PCREL(0x0b),


        R_RX_DIR16UL(0x0c),
        R_RX_DIR16UW(0x0d),
        R_RX_DIR8UL(0x0e),
        R_RX_DIR8UW(0x0f),
        R_RX_DIR32_REV(0x10),
        R_RX_DIR16_REV(0x11),
        R_RX_DIR3U_PCREL(0x12),


        R_RX_RH_3_PCREL(0x20),
        R_RX_RH_16_OP(0x21),
        R_RX_RH_24_OP(0x22),
        R_RX_RH_32_OP(0x23),
        R_RX_RH_24_UNS(0x24),
        R_RX_RH_8_NEG(0x25),
        R_RX_RH_16_NEG(0x26),
        R_RX_RH_24_NEG(0x27),
        R_RX_RH_32_NEG(0x28),
        R_RX_RH_DIFF(0x29),
        R_RX_RH_GPRELB(0x2a),
        R_RX_RH_GPRELW(0x2b),
        R_RX_RH_GPRELL(0x2c),
        R_RX_RH_RELAX(0x2d),


        R_RX_ABS32(0x41),
        R_RX_ABS24S(0x42),
        R_RX_ABS16(0x43),
        R_RX_ABS16U(0x44),
        R_RX_ABS16S(0x45),
        R_RX_ABS8(0x46),
        R_RX_ABS8U(0x47),
        R_RX_ABS8S(0x48),
        R_RX_ABS24S_PCREL(0x49),
        R_RX_ABS16S_PCREL(0x4a),
        R_RX_ABS8S_PCREL(0x4b),
        R_RX_ABS16UL(0x4c),
        R_RX_ABS16UW(0x4d),
        R_RX_ABS8UL(0x4e),
        R_RX_ABS8UW(0x4f),
        R_RX_ABS32_REV(0x50),
        R_RX_ABS16_REV(0x51),

        R_RX_SYM(0x80),
        R_RX_OPneg(0x81),
        R_RX_OPadd(0x82),
        R_RX_OPsub(0x83),
        R_RX_OPmul(0x84),
        R_RX_OPdiv(0x85),
        R_RX_OPshla(0x86),
        R_RX_OPshra(0x87),
        R_RX_OPsctsize(0x88),
        R_RX_OPscttop(0x8d),
        R_RX_OPand(0x90),
        R_RX_OPor(0x91),
        R_RX_OPxor(0x92),
        R_RX_OPnot(0x93),
        R_RX_OPmod(0x94),
        R_RX_OPromtop(0x95),
        R_RX_OPramtop(0x96),

        R_RX_max(0);
        public final int ID;

        elf_rx_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_rx_reloc_type e : elf_rx_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_s390_reloc_type {
        R_390_NONE(0),
        R_390_8(1),
        R_390_12(2),
        R_390_16(3),
        R_390_32(4),
        R_390_PC32(5),
        R_390_GOT12(6),
        R_390_GOT32(7),
        R_390_PLT32(8),
        R_390_COPY(9),
        R_390_GLOB_DAT(10),
        R_390_JMP_SLOT(11),
        R_390_RELATIVE(12),
        R_390_GOTOFF32(13),
        R_390_GOTPC(14),
        R_390_GOT16(15),
        R_390_PC16(16),
        R_390_PC12DBL(62),
        R_390_PLT12DBL(63),
        R_390_PC16DBL(17),
        R_390_PLT16DBL(18),
        R_390_PC24DBL(64),
        R_390_PLT24DBL(65),
        R_390_PC32DBL(19),
        R_390_PLT32DBL(20),
        R_390_GOTPCDBL(21),
        R_390_64(22),
        R_390_PC64(23),
        R_390_GOT64(24),
        R_390_PLT64(25),
        R_390_GOTENT(26),
        R_390_GOTOFF16(27),
        R_390_GOTOFF64(28),
        R_390_GOTPLT12(29),
        R_390_GOTPLT16(30),
        R_390_GOTPLT32(31),
        R_390_GOTPLT64(32),
        R_390_GOTPLTENT(33),
        R_390_PLTOFF16(34),
        R_390_PLTOFF32(35),
        R_390_PLTOFF64(36),
        R_390_TLS_LOAD(37),
        R_390_TLS_GDCALL(38),

        R_390_TLS_LDCALL(39),

        R_390_TLS_GD32(40),

        R_390_TLS_GD64(41),

        R_390_TLS_GOTIE12(42),

        R_390_TLS_GOTIE32(43),

        R_390_TLS_GOTIE64(44),

        R_390_TLS_LDM32(45),

        R_390_TLS_LDM64(46),

        R_390_TLS_IE32(47),

        R_390_TLS_IE64(48),

        R_390_TLS_IEENT(49),

        R_390_TLS_LE32(50),

        R_390_TLS_LE64(51),

        R_390_TLS_LDO32(52),

        R_390_TLS_LDO64(53),

        R_390_TLS_DTPMOD(54),
        R_390_TLS_DTPOFF(55),
        R_390_TLS_TPOFF(56),

        R_390_20(57),
        R_390_GOT20(58),
        R_390_GOTPLT20(59),
        R_390_TLS_GOTIE20(60),

        R_390_IRELATIVE(61),

        R_390_GNU_VTINHERIT(250),
        R_390_GNU_VTENTRY(251),
        R_390_max(0);
        public final int ID;

        elf_s390_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_s390_reloc_type e : elf_s390_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }

    public enum elf_score_reloc_type {
        R_SCORE_NONE(0),
        R_SCORE_HI16(1),
        R_SCORE_LO16(2),
        R_SCORE_BCMP(3),
        R_SCORE_24(4),
        R_SCORE_PC19(5),
        R_SCORE16_11(6),
        R_SCORE16_PC8(7),
        R_SCORE_ABS32(8),
        R_SCORE_ABS16(9),
        R_SCORE_DUMMY2(10),
        R_SCORE_GP15(11),
        R_SCORE_GNU_VTINHERIT(12),
        R_SCORE_GNU_VTENTRY(13),
        R_SCORE_GOT15(14),
        R_SCORE_GOT_LO16(15),
        R_SCORE_CALL15(16),
        R_SCORE_GPREL32(17),
        R_SCORE_REL32(18),
        R_SCORE_DUMMY_HI16(19),
        R_SCORE_IMM30(20),
        R_SCORE_IMM32(21),
        R_SCORE_max(0);
        public final int ID;

        elf_score_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_score_reloc_type e : elf_score_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }

    public enum elf_sh_reloc_type {
        R_SH_NONE(0),
        R_SH_DIR32(1),
        R_SH_REL32(2),
        R_SH_DIR8WPN(3),
        R_SH_IND12W(4),
        R_SH_DIR8WPL(5),
        R_SH_DIR8WPZ(6),
        R_SH_DIR8BP(7),
        R_SH_DIR8W(8),
        R_SH_DIR8L(9),

        R_SH_LOOP_START(10),
        R_SH_LOOP_END(11),

        R_SH_FIRST_INVALID_RELOC(12),
        R_SH_LAST_INVALID_RELOC(21),

        R_SH_GNU_VTINHERIT(22),
        R_SH_GNU_VTENTRY(23),
        R_SH_SWITCH8(24),
        R_SH_SWITCH16(25),
        R_SH_SWITCH32(26),
        R_SH_USES(27),
        R_SH_COUNT(28),
        R_SH_ALIGN(29),
        R_SH_CODE(30),
        R_SH_DATA(31),
        R_SH_LABEL(32),

        R_SH_DIR16(33),
        R_SH_DIR8(34),
        R_SH_DIR8UL(35),
        R_SH_DIR8UW(36),
        R_SH_DIR8U(37),
        R_SH_DIR8SW(38),
        R_SH_DIR8S(39),
        R_SH_DIR4UL(40),
        R_SH_DIR4UW(41),
        R_SH_DIR4U(42),
        R_SH_PSHA(43),
        R_SH_PSHL(44),
        R_SH_DIR5U(45),
        R_SH_DIR6U(46),
        R_SH_DIR6S(47),
        R_SH_DIR10S(48),
        R_SH_DIR10SW(49),
        R_SH_DIR10SL(50),
        R_SH_DIR10SQ(51),
        R_SH_FIRST_INVALID_RELOC_2(52),
        R_SH_LAST_INVALID_RELOC_2(52),
        R_SH_DIR16S(53),
        R_SH_FIRST_INVALID_RELOC_3(54),
        R_SH_LAST_INVALID_RELOC_3(143),
        R_SH_TLS_GD_32(144),
        R_SH_TLS_LD_32(145),
        R_SH_TLS_LDO_32(146),
        R_SH_TLS_IE_32(147),
        R_SH_TLS_LE_32(148),
        R_SH_TLS_DTPMOD32(149),
        R_SH_TLS_DTPOFF32(150),
        R_SH_TLS_TPOFF32(151),
        R_SH_FIRST_INVALID_RELOC_4(152),
        R_SH_LAST_INVALID_RELOC_4(159),
        R_SH_GOT32(160),
        R_SH_PLT32(161),
        R_SH_COPY(162),
        R_SH_GLOB_DAT(163),
        R_SH_JMP_SLOT(164),
        R_SH_RELATIVE(165),
        R_SH_GOTOFF(166),
        R_SH_GOTPC(167),
        R_SH_GOTPLT32(168),
        R_SH_GOT_LOW16(169),
        R_SH_GOT_MEDLOW16(170),
        R_SH_GOT_MEDHI16(171),
        R_SH_GOT_HI16(172),
        R_SH_GOTPLT_LOW16(173),
        R_SH_GOTPLT_MEDLOW16(174),
        R_SH_GOTPLT_MEDHI16(175),
        R_SH_GOTPLT_HI16(176),
        R_SH_PLT_LOW16(177),
        R_SH_PLT_MEDLOW16(178),
        R_SH_PLT_MEDHI16(179),
        R_SH_PLT_HI16(180),
        R_SH_GOTOFF_LOW16(181),
        R_SH_GOTOFF_MEDLOW16(182),
        R_SH_GOTOFF_MEDHI16(183),
        R_SH_GOTOFF_HI16(184),
        R_SH_GOTPC_LOW16(185),
        R_SH_GOTPC_MEDLOW16(186),
        R_SH_GOTPC_MEDHI16(187),
        R_SH_GOTPC_HI16(188),
        R_SH_GOT10BY4(189),
        R_SH_GOTPLT10BY4(190),
        R_SH_GOT10BY8(191),
        R_SH_GOTPLT10BY8(192),
        R_SH_COPY64(193),
        R_SH_GLOB_DAT64(194),
        R_SH_JMP_SLOT64(195),
        R_SH_RELATIVE64(196),
        R_SH_FIRST_INVALID_RELOC_5(197),
        R_SH_LAST_INVALID_RELOC_5(200),
        R_SH_GOT20(201),
        R_SH_GOTOFF20(202),
        R_SH_GOTFUNCDESC(203),
        R_SH_GOTFUNCDESC20(204),
        R_SH_GOTOFFFUNCDESC(205),
        R_SH_GOTOFFFUNCDESC20(206),
        R_SH_FUNCDESC(207),
        R_SH_FUNCDESC_VALUE(208),
        R_SH_FIRST_INVALID_RELOC_6(209),
        R_SH_LAST_INVALID_RELOC_6(241),
        R_SH_SHMEDIA_CODE(242),
        R_SH_PT_16(243),
        R_SH_IMMS16(244),
        R_SH_IMMU16(245),
        R_SH_IMM_LOW16(246),
        R_SH_IMM_LOW16_PCREL(247),
        R_SH_IMM_MEDLOW16(248),
        R_SH_IMM_MEDLOW16_PCREL(249),
        R_SH_IMM_MEDHI16(250),
        R_SH_IMM_MEDHI16_PCREL(251),
        R_SH_IMM_HI16(252),
        R_SH_IMM_HI16_PCREL(253),
        R_SH_64(254),
        R_SH_64_PCREL(255),
        R_SH_max(0);
        public final int ID;

        elf_sh_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_sh_reloc_type e : elf_sh_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_sparc_reloc_type {
        R_SPARC_NONE(0),
        R_SPARC_8(1),
        R_SPARC_16(2),
        R_SPARC_32(3),
        R_SPARC_DISP8(4),
        R_SPARC_DISP16(5),
        R_SPARC_DISP32(6),
        R_SPARC_WDISP30(7),
        R_SPARC_WDISP22(8),
        R_SPARC_HI22(9),
        R_SPARC_22(10),
        R_SPARC_13(11),
        R_SPARC_LO10(12),
        R_SPARC_GOT10(13),
        R_SPARC_GOT13(14),
        R_SPARC_GOT22(15),
        R_SPARC_PC10(16),
        R_SPARC_PC22(17),
        R_SPARC_WPLT30(18),
        R_SPARC_COPY(19),
        R_SPARC_GLOB_DAT(20),
        R_SPARC_JMP_SLOT(21),
        R_SPARC_RELATIVE(22),
        R_SPARC_UA32(23),


        R_SPARC_PLT32(24),
        R_SPARC_HIPLT22(25),
        R_SPARC_LOPLT10(26),
        R_SPARC_PCPLT32(27),
        R_SPARC_PCPLT22(28),
        R_SPARC_PCPLT10(29),


        R_SPARC_10(30),
        R_SPARC_11(31),
        R_SPARC_64(32),
        R_SPARC_OLO10(33),
        R_SPARC_HH22(34),
        R_SPARC_HM10(35),
        R_SPARC_LM22(36),
        R_SPARC_PC_HH22(37),
        R_SPARC_PC_HM10(38),
        R_SPARC_PC_LM22(39),
        R_SPARC_WDISP16(40),
        R_SPARC_WDISP19(41),
        R_SPARC_UNUSED_42(42),
        R_SPARC_7(43),
        R_SPARC_5(44),
        R_SPARC_6(45),
        R_SPARC_DISP64(46),
        R_SPARC_PLT64(47),
        R_SPARC_HIX22(48),
        R_SPARC_LOX10(49),
        R_SPARC_H44(50),
        R_SPARC_M44(51),
        R_SPARC_L44(52),
        R_SPARC_REGISTER(53),
        R_SPARC_UA64(54),
        R_SPARC_UA16(55),

        R_SPARC_TLS_GD_HI22(56),
        R_SPARC_TLS_GD_LO10(57),
        R_SPARC_TLS_GD_ADD(58),
        R_SPARC_TLS_GD_CALL(59),
        R_SPARC_TLS_LDM_HI22(60),
        R_SPARC_TLS_LDM_LO10(61),
        R_SPARC_TLS_LDM_ADD(62),
        R_SPARC_TLS_LDM_CALL(63),
        R_SPARC_TLS_LDO_HIX22(64),
        R_SPARC_TLS_LDO_LOX10(65),
        R_SPARC_TLS_LDO_ADD(66),
        R_SPARC_TLS_IE_HI22(67),
        R_SPARC_TLS_IE_LO10(68),
        R_SPARC_TLS_IE_LD(69),
        R_SPARC_TLS_IE_LDX(70),
        R_SPARC_TLS_IE_ADD(71),
        R_SPARC_TLS_LE_HIX22(72),
        R_SPARC_TLS_LE_LOX10(73),
        R_SPARC_TLS_DTPMOD32(74),
        R_SPARC_TLS_DTPMOD64(75),
        R_SPARC_TLS_DTPOFF32(76),
        R_SPARC_TLS_DTPOFF64(77),
        R_SPARC_TLS_TPOFF32(78),
        R_SPARC_TLS_TPOFF64(79),

        R_SPARC_GOTDATA_HIX22(80),
        R_SPARC_GOTDATA_LOX10(81),
        R_SPARC_GOTDATA_OP_HIX22(82),
        R_SPARC_GOTDATA_OP_LOX10(83),
        R_SPARC_GOTDATA_OP(84),

        R_SPARC_H34(85),
        R_SPARC_SIZE32(86),
        R_SPARC_SIZE64(87),
        R_SPARC_WDISP10(88),

        R_SPARC_max_std(0),

        R_SPARC_JMP_IREL(248),
        R_SPARC_IRELATIVE(249),
        R_SPARC_GNU_VTINHERIT(250),
        R_SPARC_GNU_VTENTRY(251),
        R_SPARC_REV32(252),

        R_SPARC_max(0);
        public final int ID;

        elf_sparc_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_sparc_reloc_type e : elf_sparc_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }

    public enum elf_spu_reloc_type {
        R_SPU_NONE(0),
        R_SPU_ADDR10(1),
        R_SPU_ADDR16(2),
        R_SPU_ADDR16_HI(3),
        R_SPU_ADDR16_LO(4),
        R_SPU_ADDR18(5),
        R_SPU_ADDR32(6),
        R_SPU_REL16(7),
        R_SPU_ADDR7(8),
        R_SPU_REL9(9),
        R_SPU_REL9I(10),
        R_SPU_ADDR10I(11),
        R_SPU_ADDR16I(12),
        R_SPU_REL32(13),
        R_SPU_ADDR16X(14),
        R_SPU_PPU32(15),
        R_SPU_PPU64(16),
        R_SPU_ADD_PIC(17),
        R_SPU_max(0);
        public final int ID;

        elf_spu_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_spu_reloc_type e : elf_spu_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_tic6x_reloc_type {
        R_C6000_NONE(0),
        R_C6000_ABS32(1),
        R_C6000_ABS16(2),
        R_C6000_ABS8(3),
        R_C6000_PCR_S21(4),
        R_C6000_PCR_S12(5),
        R_C6000_PCR_S10(6),
        R_C6000_PCR_S7(7),
        R_C6000_ABS_S16(8),
        R_C6000_ABS_L16(9),
        R_C6000_ABS_H16(10),
        R_C6000_SBR_U15_B(11),
        R_C6000_SBR_U15_H(12),
        R_C6000_SBR_U15_W(13),
        R_C6000_SBR_S16(14),
        R_C6000_SBR_L16_B(15),
        R_C6000_SBR_L16_H(16),
        R_C6000_SBR_L16_W(17),
        R_C6000_SBR_H16_B(18),
        R_C6000_SBR_H16_H(19),
        R_C6000_SBR_H16_W(20),
        R_C6000_SBR_GOT_U15_W(21),
        R_C6000_SBR_GOT_L16_W(22),
        R_C6000_SBR_GOT_H16_W(23),
        R_C6000_DSBT_INDEX(24),
        R_C6000_PREL31(25),
        R_C6000_COPY(26),
        R_C6000_JUMP_SLOT(27),
        R_C6000_EHTYPE(28),
        R_C6000_PCR_H16(29),
        R_C6000_PCR_L16(30),
        R_C6000_ALIGN(253),
        R_C6000_FPHEAD(254),
        R_C6000_NOCMP(255),
        R_TIC6X_max(0);
        public final int ID;

        elf_tic6x_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_tic6x_reloc_type e : elf_tic6x_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }

    public enum elf_tilegx_reloc_type {
        R_TILEGX_NONE(0),


        R_TILEGX_64(1),
        R_TILEGX_32(2),
        R_TILEGX_16(3),
        R_TILEGX_8(4),
        R_TILEGX_64_PCREL(5),
        R_TILEGX_32_PCREL(6),
        R_TILEGX_16_PCREL(7),
        R_TILEGX_8_PCREL(8),


        R_TILEGX_HW0(9),
        R_TILEGX_HW1(10),
        R_TILEGX_HW2(11),
        R_TILEGX_HW3(12),
        R_TILEGX_HW0_LAST(13),
        R_TILEGX_HW1_LAST(14),
        R_TILEGX_HW2_LAST(15),

        R_TILEGX_COPY(16),
        R_TILEGX_GLOB_DAT(17),
        R_TILEGX_JMP_SLOT(18),
        R_TILEGX_RELATIVE(19),


        R_TILEGX_BROFF_X1(20),
        R_TILEGX_JUMPOFF_X1(21),
        R_TILEGX_JUMPOFF_X1_PLT(22),


        R_TILEGX_IMM8_X0(23),
        R_TILEGX_IMM8_Y0(24),
        R_TILEGX_IMM8_X1(25),
        R_TILEGX_IMM8_Y1(26),
        R_TILEGX_DEST_IMM8_X1(27),
        R_TILEGX_MT_IMM14_X1(28),
        R_TILEGX_MF_IMM14_X1(29),
        R_TILEGX_MMSTART_X0(30),
        R_TILEGX_MMEND_X0(31),
        R_TILEGX_SHAMT_X0(32),
        R_TILEGX_SHAMT_X1(33),
        R_TILEGX_SHAMT_Y0(34),
        R_TILEGX_SHAMT_Y1(35),

        R_TILEGX_IMM16_X0_HW0(36),
        R_TILEGX_IMM16_X1_HW0(37),
        R_TILEGX_IMM16_X0_HW1(38),
        R_TILEGX_IMM16_X1_HW1(39),
        R_TILEGX_IMM16_X0_HW2(40),
        R_TILEGX_IMM16_X1_HW2(41),
        R_TILEGX_IMM16_X0_HW3(42),
        R_TILEGX_IMM16_X1_HW3(43),
        R_TILEGX_IMM16_X0_HW0_LAST(44),
        R_TILEGX_IMM16_X1_HW0_LAST(45),
        R_TILEGX_IMM16_X0_HW1_LAST(46),
        R_TILEGX_IMM16_X1_HW1_LAST(47),
        R_TILEGX_IMM16_X0_HW2_LAST(48),
        R_TILEGX_IMM16_X1_HW2_LAST(49),

        R_TILEGX_IMM16_X0_HW0_PCREL(50),
        R_TILEGX_IMM16_X1_HW0_PCREL(51),
        R_TILEGX_IMM16_X0_HW1_PCREL(52),
        R_TILEGX_IMM16_X1_HW1_PCREL(53),
        R_TILEGX_IMM16_X0_HW2_PCREL(54),
        R_TILEGX_IMM16_X1_HW2_PCREL(55),
        R_TILEGX_IMM16_X0_HW3_PCREL(56),
        R_TILEGX_IMM16_X1_HW3_PCREL(57),
        R_TILEGX_IMM16_X0_HW0_LAST_PCREL(58),
        R_TILEGX_IMM16_X1_HW0_LAST_PCREL(59),
        R_TILEGX_IMM16_X0_HW1_LAST_PCREL(60),
        R_TILEGX_IMM16_X1_HW1_LAST_PCREL(61),
        R_TILEGX_IMM16_X0_HW2_LAST_PCREL(62),
        R_TILEGX_IMM16_X1_HW2_LAST_PCREL(63),

        R_TILEGX_IMM16_X0_HW0_GOT(64),
        R_TILEGX_IMM16_X1_HW0_GOT(65),

        R_TILEGX_IMM16_X0_HW0_PLT_PCREL(66),
        R_TILEGX_IMM16_X1_HW0_PLT_PCREL(67),
        R_TILEGX_IMM16_X0_HW1_PLT_PCREL(68),
        R_TILEGX_IMM16_X1_HW1_PLT_PCREL(69),
        R_TILEGX_IMM16_X0_HW2_PLT_PCREL(70),
        R_TILEGX_IMM16_X1_HW2_PLT_PCREL(71),

        R_TILEGX_IMM16_X0_HW0_LAST_GOT(72),
        R_TILEGX_IMM16_X1_HW0_LAST_GOT(73),
        R_TILEGX_IMM16_X0_HW1_LAST_GOT(74),
        R_TILEGX_IMM16_X1_HW1_LAST_GOT(75),

        R_TILEGX_IMM16_X0_HW3_PLT_PCREL(76),
        R_TILEGX_IMM16_X1_HW3_PLT_PCREL(77),

        R_TILEGX_IMM16_X0_HW0_TLS_GD(78),
        R_TILEGX_IMM16_X1_HW0_TLS_GD(79),
        R_TILEGX_IMM16_X0_HW0_TLS_LE(80),
        R_TILEGX_IMM16_X1_HW0_TLS_LE(81),
        R_TILEGX_IMM16_X0_HW0_LAST_TLS_LE(82),
        R_TILEGX_IMM16_X1_HW0_LAST_TLS_LE(83),
        R_TILEGX_IMM16_X0_HW1_LAST_TLS_LE(84),
        R_TILEGX_IMM16_X1_HW1_LAST_TLS_LE(85),
        R_TILEGX_IMM16_X0_HW0_LAST_TLS_GD(86),
        R_TILEGX_IMM16_X1_HW0_LAST_TLS_GD(87),
        R_TILEGX_IMM16_X0_HW1_LAST_TLS_GD(88),
        R_TILEGX_IMM16_X1_HW1_LAST_TLS_GD(89),


        R_TILEGX_IMM16_X0_HW0_TLS_IE(92),
        R_TILEGX_IMM16_X1_HW0_TLS_IE(93),

        R_TILEGX_IMM16_X0_HW0_LAST_PLT_PCREL(94),
        R_TILEGX_IMM16_X1_HW0_LAST_PLT_PCREL(95),
        R_TILEGX_IMM16_X0_HW1_LAST_PLT_PCREL(96),
        R_TILEGX_IMM16_X1_HW1_LAST_PLT_PCREL(97),
        R_TILEGX_IMM16_X0_HW2_LAST_PLT_PCREL(98),
        R_TILEGX_IMM16_X1_HW2_LAST_PLT_PCREL(99),

        R_TILEGX_IMM16_X0_HW0_LAST_TLS_IE(100),
        R_TILEGX_IMM16_X1_HW0_LAST_TLS_IE(101),
        R_TILEGX_IMM16_X0_HW1_LAST_TLS_IE(102),
        R_TILEGX_IMM16_X1_HW1_LAST_TLS_IE(103),


        R_TILEGX_TLS_DTPMOD64(106),
        R_TILEGX_TLS_DTPOFF64(107),
        R_TILEGX_TLS_TPOFF64(108),
        R_TILEGX_TLS_DTPMOD32(109),
        R_TILEGX_TLS_DTPOFF32(110),
        R_TILEGX_TLS_TPOFF32(111),

        R_TILEGX_TLS_GD_CALL(112),
        R_TILEGX_IMM8_X0_TLS_GD_ADD(113),
        R_TILEGX_IMM8_X1_TLS_GD_ADD(114),
        R_TILEGX_IMM8_Y0_TLS_GD_ADD(115),
        R_TILEGX_IMM8_Y1_TLS_GD_ADD(116),
        R_TILEGX_TLS_IE_LOAD(117),
        R_TILEGX_IMM8_X0_TLS_ADD(118),
        R_TILEGX_IMM8_X1_TLS_ADD(119),
        R_TILEGX_IMM8_Y0_TLS_ADD(120),
        R_TILEGX_IMM8_Y1_TLS_ADD(121),


        R_TILEGX_GNU_VTINHERIT(128),
        R_TILEGX_GNU_VTENTRY(129),
        R_TILEGX_max(0);
        public final int ID;

        elf_tilegx_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_tilegx_reloc_type e : elf_tilegx_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_tilepro_reloc_type {
        R_TILEPRO_NONE(0),


        R_TILEPRO_32(1),
        R_TILEPRO_16(2),
        R_TILEPRO_8(3),
        R_TILEPRO_32_PCREL(4),
        R_TILEPRO_16_PCREL(5),
        R_TILEPRO_8_PCREL(6),

        R_TILEPRO_LO16(7),
        R_TILEPRO_HI16(8),
        R_TILEPRO_HA16(9),

        R_TILEPRO_COPY(10),
        R_TILEPRO_GLOB_DAT(11),
        R_TILEPRO_JMP_SLOT(12),
        R_TILEPRO_RELATIVE(13),


        R_TILEPRO_BROFF_X1(14),
        R_TILEPRO_JOFFLONG_X1(15),
        R_TILEPRO_JOFFLONG_X1_PLT(16),


        R_TILEPRO_IMM8_X0(17),
        R_TILEPRO_IMM8_Y0(18),
        R_TILEPRO_IMM8_X1(19),
        R_TILEPRO_IMM8_Y1(20),
        R_TILEPRO_MT_IMM15_X1(21),
        R_TILEPRO_MF_IMM15_X1(22),

        R_TILEPRO_IMM16_X0(23),
        R_TILEPRO_IMM16_X1(24),
        R_TILEPRO_IMM16_X0_LO(25),
        R_TILEPRO_IMM16_X1_LO(26),
        R_TILEPRO_IMM16_X0_HI(27),
        R_TILEPRO_IMM16_X1_HI(28),
        R_TILEPRO_IMM16_X0_HA(29),
        R_TILEPRO_IMM16_X1_HA(30),

        R_TILEPRO_IMM16_X0_PCREL(31),
        R_TILEPRO_IMM16_X1_PCREL(32),
        R_TILEPRO_IMM16_X0_LO_PCREL(33),
        R_TILEPRO_IMM16_X1_LO_PCREL(34),
        R_TILEPRO_IMM16_X0_HI_PCREL(35),
        R_TILEPRO_IMM16_X1_HI_PCREL(36),
        R_TILEPRO_IMM16_X0_HA_PCREL(37),
        R_TILEPRO_IMM16_X1_HA_PCREL(38),

        R_TILEPRO_IMM16_X0_GOT(39),
        R_TILEPRO_IMM16_X1_GOT(40),
        R_TILEPRO_IMM16_X0_GOT_LO(41),
        R_TILEPRO_IMM16_X1_GOT_LO(42),
        R_TILEPRO_IMM16_X0_GOT_HI(43),
        R_TILEPRO_IMM16_X1_GOT_HI(44),
        R_TILEPRO_IMM16_X0_GOT_HA(45),
        R_TILEPRO_IMM16_X1_GOT_HA(46),

        R_TILEPRO_MMSTART_X0(47),
        R_TILEPRO_MMEND_X0(48),
        R_TILEPRO_MMSTART_X1(49),
        R_TILEPRO_MMEND_X1(50),

        R_TILEPRO_SHAMT_X0(51),
        R_TILEPRO_SHAMT_X1(52),
        R_TILEPRO_SHAMT_Y0(53),
        R_TILEPRO_SHAMT_Y1(54),

        R_TILEPRO_DEST_IMM8_X1(55),


        R_TILEPRO_TLS_GD_CALL(60),
        R_TILEPRO_IMM8_X0_TLS_GD_ADD(61),
        R_TILEPRO_IMM8_X1_TLS_GD_ADD(62),
        R_TILEPRO_IMM8_Y0_TLS_GD_ADD(63),
        R_TILEPRO_IMM8_Y1_TLS_GD_ADD(64),
        R_TILEPRO_TLS_IE_LOAD(65),

        R_TILEPRO_IMM16_X0_TLS_GD(66),
        R_TILEPRO_IMM16_X1_TLS_GD(67),
        R_TILEPRO_IMM16_X0_TLS_GD_LO(68),
        R_TILEPRO_IMM16_X1_TLS_GD_LO(69),
        R_TILEPRO_IMM16_X0_TLS_GD_HI(70),
        R_TILEPRO_IMM16_X1_TLS_GD_HI(71),
        R_TILEPRO_IMM16_X0_TLS_GD_HA(72),
        R_TILEPRO_IMM16_X1_TLS_GD_HA(73),

        R_TILEPRO_IMM16_X0_TLS_IE(74),
        R_TILEPRO_IMM16_X1_TLS_IE(75),
        R_TILEPRO_IMM16_X0_TLS_IE_LO(76),
        R_TILEPRO_IMM16_X1_TLS_IE_LO(77),
        R_TILEPRO_IMM16_X0_TLS_IE_HI(78),
        R_TILEPRO_IMM16_X1_TLS_IE_HI(79),
        R_TILEPRO_IMM16_X0_TLS_IE_HA(80),
        R_TILEPRO_IMM16_X1_TLS_IE_HA(81),

        R_TILEPRO_TLS_DTPMOD32(82),
        R_TILEPRO_TLS_DTPOFF32(83),
        R_TILEPRO_TLS_TPOFF32(84),

        R_TILEPRO_IMM16_X0_TLS_LE(85),
        R_TILEPRO_IMM16_X1_TLS_LE(86),
        R_TILEPRO_IMM16_X0_TLS_LE_LO(87),
        R_TILEPRO_IMM16_X1_TLS_LE_LO(88),
        R_TILEPRO_IMM16_X0_TLS_LE_HI(89),
        R_TILEPRO_IMM16_X1_TLS_LE_HI(90),
        R_TILEPRO_IMM16_X0_TLS_LE_HA(91),
        R_TILEPRO_IMM16_X1_TLS_LE_HA(92),


        R_TILEPRO_GNU_VTINHERIT(128),
        R_TILEPRO_GNU_VTENTRY(129),
        R_TILEPRO_max(0);
        public final int ID;

        elf_tilepro_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_tilepro_reloc_type e : elf_tilepro_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum v850_reloc_type {
        R_V850_NONE(0),
        R_V850_9_PCREL(1),
        R_V850_22_PCREL(2),
        R_V850_HI16_S(3),
        R_V850_HI16(4),
        R_V850_LO16(5),
        R_V850_ABS32(6),
        R_V850_16(7),
        R_V850_8(8),
        R_V850_SDA_16_16_OFFSET(9),
        R_V850_SDA_15_16_OFFSET(10),
        R_V850_ZDA_16_16_OFFSET(11),
        R_V850_ZDA_15_16_OFFSET(12),
        R_V850_TDA_6_8_OFFSET(13),
        R_V850_TDA_7_8_OFFSET(14),
        R_V850_TDA_7_7_OFFSET(15),
        R_V850_TDA_16_16_OFFSET(16),
        R_V850_TDA_4_5_OFFSET(17),
        R_V850_TDA_4_4_OFFSET(18),
        R_V850_SDA_16_16_SPLIT_OFFSET(19),
        R_V850_ZDA_16_16_SPLIT_OFFSET(20),
        R_V850_CALLT_6_7_OFFSET(21),
        R_V850_CALLT_16_16_OFFSET(22),
        R_V850_GNU_VTINHERIT(23),
        R_V850_GNU_VTENTRY(24),
        R_V850_LONGCALL(25),
        R_V850_LONGJUMP(26),
        R_V850_ALIGN(27),
        R_V850_REL32(28),
        R_V850_LO16_SPLIT_OFFSET(29),
        R_V850_16_PCREL(30),
        R_V850_17_PCREL(31),
        R_V850_23(32),
        R_V850_32_PCREL(33),
        R_V850_32_ABS(34),
        R_V850_16_SPLIT_OFFSET(35),
        R_V850_16_S1(36),
        R_V850_LO16_S1(37),
        R_V850_CALLT_15_16_OFFSET(38),
        R_V850_32_GOTPCREL(39),
        R_V850_16_GOT(40),
        R_V850_32_GOT(41),
        R_V850_22_PLT(42),
        R_V850_32_PLT(43),
        R_V850_COPY(44),
        R_V850_GLOB_DAT(45),
        R_V850_JMP_SLOT(46),
        R_V850_RELATIVE(47),
        R_V850_16_GOTOFF(48),
        R_V850_32_GOTOFF(49),
        R_V850_CODE(50),
        R_V850_DATA(51),

        R_V850_max(0);
        public final int ID;

        v850_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (v850_reloc_type e : v850_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }

    public enum v800_reloc_type {
        R_V800_NONE(0x00),
        R_V810_NONE(0x30),
        R_V810_BYTE(0x31),
        R_V810_HWORD(0x32),
        R_V810_WORD(0x33),
        R_V810_WLO(0x34),
        R_V810_WHI(0x35),
        R_V810_WHI1(0x36),
        R_V810_GPBYTE(0x37),
        R_V810_GPHWORD(0x38),
        R_V810_GPWORD(0x39),
        R_V810_GPWLO(0x3a),
        R_V810_GPWHI(0x3b),
        R_V810_GPWHI1(0x3c),
        R_V850_HWLO(0x3d),
        R_V810_reserved1(0x3e),
        R_V850_EP7BIT(0x3f),
        R_V850_EPHBYTE(0x40),
        R_V850_EPWBYTE(0x41),
        R_V850_REGHWLO(0x42),
        R_V810_reserved2(0x43),
        R_V850_GPHWLO(0x44),
        R_V810_reserved3(0x45),
        R_V850_PCR22(0x46),
        R_V850_BLO(0x47),
        R_V850_EP4BIT(0x48),
        R_V850_EP5BIT(0x49),
        R_V850_REGBLO(0x4a),
        R_V850_GPBLO(0x4b),
        R_V810_WLO_1(0x4c),
        R_V810_GPWLO_1(0x4d),
        R_V850_BLO_1(0x4e),
        R_V850_HWLO_1(0x4f),
        R_V810_reserved4(0x50),
        R_V850_GPBLO_1(0x51),
        R_V850_GPHWLO_1(0x52),
        R_V810_reserved5(0x53),
        R_V850_EPBLO(0x54),
        R_V850_EPHWLO(0x55),
        R_V810_reserved6(0x56),
        R_V850_EPWLO_N(0x57),
        R_V850_PC32(0x58),
        R_V850_W23BIT(0x59),
        R_V850_GPW23BIT(0x5a),
        R_V850_EPW23BIT(0x5b),
        R_V850_B23BIT(0x5c),
        R_V850_GPB23BIT(0x5d),
        R_V850_EPB23BIT(0x5e),
        R_V850_PC16U(0x5f),
        R_V850_PC17(0x60),
        R_V850_DW8(0x61),
        R_V850_GPDW8(0x62),
        R_V850_EPDW8(0x63),
        R_V850_PC9(0x64),
        R_V810_REGBYTE(0x65),
        R_V810_REGHWORD(0x66),
        R_V810_REGWORD(0x67),
        R_V810_REGWLO(0x68),
        R_V810_REGWHI(0x69),
        R_V810_REGWHI1(0x6a),
        R_V850_REGW23BIT(0x6b),
        R_V850_REGB23BIT(0x6c),
        R_V850_REGDW8(0x6d),
        R_V810_EPBYTE(0x6e),
        R_V810_EPHWORD(0x6f),
        R_V810_EPWORD(0x70),
        R_V850_WLO23(0x71),
        R_V850_WORD_E(0x72),
        R_V850_REGWORD_E(0x73),
        R_V850_WORD(0x74),
        R_V850_GPWORD(0x75),
        R_V850_REGWORD(0x76),
        R_V850_EPWORD(0x77),
        R_V810_TPBYTE(0x78),
        R_V810_TPHWORD(0x79),
        R_V810_TPWORD(0x7a),
        R_V810_TPWLO(0x7b),
        R_V810_TPWHI(0x7c),
        R_V810_TPWHI1(0x7d),
        R_V850_TPHWLO(0x7e),
        R_V850_TPBLO(0x7f),
        R_V810_TPWLO_1(0x80),
        R_V850_TPBLO_1(0x81),
        R_V850_TPHWLO_1(0x82),
        R_V850_TP23BIT(0x83),
        R_V850_TPW23BIT(0x84),
        R_V850_TPDW8(0x85),


        R_V810_ABS32(0xa0),
        R_V850_SYM(0xe0),
        R_V850_OPadd(0xe1),
        R_V850_OPsub(0xe2),
        R_V850_OPsctsize(0xe3),
        R_V850_OPscttop(0xe4),

        R_V800_max(0);
        public final int ID;

        v800_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (v800_reloc_type e : v800_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_vax_reloc_type {
        R_VAX_NONE(0),
        R_VAX_32(1),
        R_VAX_16(2),
        R_VAX_8(3),
        R_VAX_PC32(4),
        R_VAX_PC16(5),
        R_VAX_PC8(6),
        R_VAX_GOT32(7),
        R_VAX_PLT32(13),
        R_VAX_COPY(19),
        R_VAX_GLOB_DAT(20),
        R_VAX_JMP_SLOT(21),
        R_VAX_RELATIVE(22),

        R_VAX_GNU_VTINHERIT(23),
        R_VAX_GNU_VTENTRY(24),
        R_VAX_max(0);
        public final int ID;

        elf_vax_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_vax_reloc_type e : elf_vax_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_visium_reloc_type {
        R_VISIUM_NONE(0),
        R_VISIUM_8(1),
        R_VISIUM_16(2),
        R_VISIUM_32(3),
        R_VISIUM_8_PCREL(4),
        R_VISIUM_16_PCREL(5),
        R_VISIUM_32_PCREL(6),
        R_VISIUM_PC16(7),
        R_VISIUM_HI16(8),
        R_VISIUM_LO16(9),
        R_VISIUM_IM16(10),
        R_VISIUM_HI16_PCREL(11),
        R_VISIUM_LO16_PCREL(12),
        R_VISIUM_IM16_PCREL(13),
        R_VISIUM_GNU_VTINHERIT(200),
        R_VISIUM_GNU_VTENTRY(201),
        R_VISIUM_max(0);
        public final int ID;

        elf_visium_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_visium_reloc_type e : elf_visium_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_wasm32_reloc_type {
        R_WASM32_NONE(0),
        R_WASM32_32(1),
        R_WASM32_max(1);
        public final int ID;

        elf_wasm32_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_wasm32_reloc_type e : elf_wasm32_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_x86_64_reloc_type {
        R_X86_64_NONE(0),
        R_X86_64_64(1),
        R_X86_64_PC32(2),
        R_X86_64_GOT32(3),
        R_X86_64_PLT32(4),
        R_X86_64_COPY(5),
        R_X86_64_GLOB_DAT(6),
        R_X86_64_JUMP_SLOT(7),
        R_X86_64_RELATIVE(8),
        R_X86_64_GOTPCREL(9),

        R_X86_64_32(10),
        R_X86_64_32S(11),
        R_X86_64_16(12),
        R_X86_64_PC16(13),
        R_X86_64_8(14),
        R_X86_64_PC8(15),
        R_X86_64_DTPMOD64(16),
        R_X86_64_DTPOFF64(17),
        R_X86_64_TPOFF64(18),
        R_X86_64_TLSGD(19),
        R_X86_64_TLSLD(20),
        R_X86_64_DTPOFF32(21),
        R_X86_64_GOTTPOFF(22),
        R_X86_64_TPOFF32(23),
        R_X86_64_PC64(24),
        R_X86_64_GOTOFF64(25),
        R_X86_64_GOTPC32(26),

        R_X86_64_GOT64(27),
        R_X86_64_GOTPCREL64(28),

        R_X86_64_GOTPC64(29),

        R_X86_64_GOTPLT64(30),
        R_X86_64_PLTOFF64(31),

        R_X86_64_SIZE32(32),
        R_X86_64_SIZE64(33),
        R_X86_64_GOTPC32_TLSDESC(34),


        R_X86_64_TLSDESC_CALL(35),

        R_X86_64_TLSDESC(36),
        R_X86_64_IRELATIVE(37),
        R_X86_64_RELATIVE64(38),
        R_X86_64_PC32_BND(39),

        R_X86_64_PLT32_BND(40),


        R_X86_64_GOTPCRELX(41),


        R_X86_64_REX_GOTPCRELX(42),
        R_X86_64_GNU_VTINHERIT(250),
        R_X86_64_GNU_VTENTRY(251),
        R_X86_64_max(0);
        public final int ID;

        elf_x86_64_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_x86_64_reloc_type e : elf_x86_64_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_xc16x_reloc_type {
        R_XC16X_NONE(0),
        R_XC16X_ABS_8(1),
        R_XC16X_ABS_16(2),
        R_XC16X_ABS_32(3),
        R_XC16X_8_PCREL(4),
        R_XC16X_PAG(5),
        R_XC16X_POF(6),
        R_XC16X_SEG(7),
        R_XC16X_SOF(8),

        R_XC16X_max(0);
        public final int ID;

        elf_xc16x_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_xc16x_reloc_type e : elf_xc16x_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_xgate_reloc_type {
        R_XGATE_NONE(0),
        R_XGATE_8(1),
        R_XGATE_PCREL_8(2),
        R_XGATE_16(3),
        R_XGATE_32(4),
        R_XGATE_PCREL_16(5),

        R_XGATE_GNU_VTINHERIT(6),
        R_XGATE_GNU_VTENTRY(7),

        R_XGATE_24(8),
        R_XGATE_LO16(9),
        R_XGATE_GPAGE(10),
        R_XGATE_PCREL_9(11),
        R_XGATE_PCREL_10(12),
        R_XGATE_IMM8_LO(13),
        R_XGATE_IMM8_HI(14),
        R_XGATE_IMM3(15),
        R_XGATE_IMM4(16),
        R_XGATE_IMM5(17),


        R_XGATE_RL_JUMP(18),


        R_XGATE_RL_GROUP(19),
        R_XGATE_max(0);
        public final int ID;

        elf_xgate_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_xgate_reloc_type e : elf_xgate_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_xstormy16_reloc_type {
        R_XSTORMY16_NONE(0),

        R_XSTORMY16_32(1),
        R_XSTORMY16_16(2),
        R_XSTORMY16_8(3),
        R_XSTORMY16_PC32(4),
        R_XSTORMY16_PC16(5),
        R_XSTORMY16_PC8(6),

        R_XSTORMY16_REL_12(7),
        R_XSTORMY16_24(8),
        R_XSTORMY16_FPTR16(9),

        R_XSTORMY16_LO16(10),
        R_XSTORMY16_HI16(11),
        R_XSTORMY16_12(12),

        R_XSTORMY16_GNU_VTINHERIT(128),
        R_XSTORMY16_GNU_VTENTRY(129),
        R_XSTORMY16_max(0);
        public final int ID;

        elf_xstormy16_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_xstormy16_reloc_type e : elf_xstormy16_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_xtensa_reloc_type {
        R_XTENSA_NONE(0),
        R_XTENSA_32(1),
        R_XTENSA_RTLD(2),
        R_XTENSA_GLOB_DAT(3),
        R_XTENSA_JMP_SLOT(4),
        R_XTENSA_RELATIVE(5),
        R_XTENSA_PLT(6),
        R_XTENSA_OP0(8),
        R_XTENSA_OP1(9),
        R_XTENSA_OP2(10),
        R_XTENSA_ASM_EXPAND(11),
        R_XTENSA_ASM_SIMPLIFY(12),
        R_XTENSA_32_PCREL(14),
        R_XTENSA_GNU_VTINHERIT(15),
        R_XTENSA_GNU_VTENTRY(16),
        R_XTENSA_DIFF8(17),
        R_XTENSA_DIFF16(18),
        R_XTENSA_DIFF32(19),
        R_XTENSA_SLOT0_OP(20),
        R_XTENSA_SLOT1_OP(21),
        R_XTENSA_SLOT2_OP(22),
        R_XTENSA_SLOT3_OP(23),
        R_XTENSA_SLOT4_OP(24),
        R_XTENSA_SLOT5_OP(25),
        R_XTENSA_SLOT6_OP(26),
        R_XTENSA_SLOT7_OP(27),
        R_XTENSA_SLOT8_OP(28),
        R_XTENSA_SLOT9_OP(29),
        R_XTENSA_SLOT10_OP(30),
        R_XTENSA_SLOT11_OP(31),
        R_XTENSA_SLOT12_OP(32),
        R_XTENSA_SLOT13_OP(33),
        R_XTENSA_SLOT14_OP(34),
        R_XTENSA_SLOT0_ALT(35),
        R_XTENSA_SLOT1_ALT(36),
        R_XTENSA_SLOT2_ALT(37),
        R_XTENSA_SLOT3_ALT(38),
        R_XTENSA_SLOT4_ALT(39),
        R_XTENSA_SLOT5_ALT(40),
        R_XTENSA_SLOT6_ALT(41),
        R_XTENSA_SLOT7_ALT(42),
        R_XTENSA_SLOT8_ALT(43),
        R_XTENSA_SLOT9_ALT(44),
        R_XTENSA_SLOT10_ALT(45),
        R_XTENSA_SLOT11_ALT(46),
        R_XTENSA_SLOT12_ALT(47),
        R_XTENSA_SLOT13_ALT(48),
        R_XTENSA_SLOT14_ALT(49),
        R_XTENSA_TLSDESC_FN(50),
        R_XTENSA_TLSDESC_ARG(51),
        R_XTENSA_TLS_DTPOFF(52),
        R_XTENSA_TLS_TPOFF(53),
        R_XTENSA_TLS_FUNC(54),
        R_XTENSA_TLS_ARG(55),
        R_XTENSA_TLS_CALL(56),
        R_XTENSA_PDIFF8(57),
        R_XTENSA_PDIFF16(58),
        R_XTENSA_PDIFF32(59),
        R_XTENSA_NDIFF8(60),
        R_XTENSA_NDIFF16(61),
        R_XTENSA_NDIFF32(62),
        R_XTENSA_max(0);
        public final int ID;

        elf_xtensa_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_xtensa_reloc_type e : elf_xtensa_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }


    public enum elf_z80_reloc_type {
        R_Z80_NONE(0),
        R_Z80_8(1),
        R_Z80_8_DIS(2),
        R_Z80_8_PCREL(3),
        R_Z80_16(4),
        R_Z80_24(5),
        R_Z80_32(6),
        R_Z80_BYTE0(7),
        R_Z80_BYTE1(8),
        R_Z80_BYTE2(9),
        R_Z80_BYTE3(10),
        R_Z80_WORD0(11),
        R_Z80_WORD1(12),
        R_Z80_16_BE(13),
        R_Z80_max(0);
        public final int ID;

        elf_z80_reloc_type(int id) {
            this.ID = id;
        }

        public static String fromID(int id) {
            for (elf_z80_reloc_type e : elf_z80_reloc_type.values()) {
                if (e.ID == id) return e.name();
            }
            return "";
        }
    }
}


