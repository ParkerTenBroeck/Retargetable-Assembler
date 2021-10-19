package org.parker.util;

public class BitManipulation {

    public static long getAlignmentOffset(long address, long alignment){
        if(bitCount(alignment) != 1){

        }else{
            long mask = alignment - 1;
            long offset = ((~(mask & address)) + 1) & mask;
            return offset;
        }
        return 0;
    }

    public static int bitCount(long number){
        int count = 0;
        for(int i = 0; i < 64; i ++){
            count += ((number >> i) & 0b1) > 0?1:0;
        }
        return count;
    }

    public static long align(long address, long alignment){
        return address + BitManipulation.getAlignmentOffset(address, alignment);
    }

}
