package org.parker.mips.assembler.instructions.formatter;

import org.parker.retargetableassembler.exception.FieldOverflow;
import org.parker.retargetableassembler.base.assembler.BaseAssembler;
import org.parker.retargetableassembler.instruction.InstructionFormatter;
import org.parker.retargetableassembler.instruction.StandardFormattedInstruction;

public interface MipsFormatter extends InstructionFormatter {

    @Override
    default int getInstructionSize(StandardFormattedInstruction instruction, BaseAssembler assembler) {
        return 4;
    }

    static void encode(byte[] data, int[] fields, int[] fieldSize, BaseAssembler assembler){

        assert data.length == 4;
        assert fields.length == fieldSize.length;

        int dataI = 0;

        for(int i = 0; i < fields.length; i ++){
            int mask = (1 << (fieldSize[i])) - 1;

            int sMax = (1 << (fieldSize[i] - 1)) - 1;
            int sMin = -((1 << (fieldSize[i] - 1)));
            int uMax = (1 << (fieldSize[i])) - 1;
            int  uMin = 0;
            if((sMax >= fields[i] && sMin <= fields[i]) || (uMax >= fields[i] && uMin <= fields[i])) {
                dataI = dataI << fieldSize[i];
                dataI |= fields[i] & mask;
            }else{
                throw new FieldOverflow(i,fields[i], uMax, sMin);
            }
        }

        //ByteBuffer bb = ByteBuffer.wrap(data);
        //bb.order(assembler.isBigEndian()? ByteOrder.BIG_ENDIAN:ByteOrder.LITTLE_ENDIAN);
        //bb.putInt(dataI);
        //no idea if this is big or small endian
        data[0] = (byte) ((dataI & 0xFF000000) >> 24);
        data[1] = (byte) ((dataI & 0x00FF0000) >> 16);
        data[2] = (byte) ((dataI & 0x0000FF00) >> 8);
        data[3] = (byte) ((dataI & 0x000000FF));
    }

    static void encodeImmediate(byte[] data, int[] fields, BaseAssembler assembler){
        encode(data, fields, new int[]{6,5,5,16}, assembler);
    }

    static void encodeJump(byte[] data, int[] fields, BaseAssembler assembler){
        encode(data, fields, new int[]{6,26}, assembler);
    }

    static void encodeRegister(byte[] data, int[] fields, BaseAssembler assembler){
        encode(data, fields, new int[]{6,5,5,5,5,6}, assembler);
    }
}
