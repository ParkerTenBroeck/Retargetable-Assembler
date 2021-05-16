/*
 *    Copyright 2021 ParkerTenBroeck
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.parker.retargetableassembler.util;

public interface Memory {

    long getSize();
    void setByte(long address, byte value);
    byte getByte(long address);
    void clear();

    default long getLong(long address, boolean bigEndian){

        byte mem8;
        byte mem7;
        byte mem6;
        byte mem5;
        byte mem4;
        byte mem3;
        byte mem2;
        byte mem1;

        if(bigEndian) {
            mem8 = getByte(address);
            mem7 = getByte(address + 1);
            mem6 = getByte(address + 2);
            mem5 = getByte(address + 3);
            mem4 = getByte(address + 4);
            mem3 = getByte(address + 5);
            mem2 = getByte(address + 6);
            mem1 = getByte(address + 7);
        }else{
            mem8 = getByte(address + 7);
            mem7 = getByte(address + 6);
            mem6 = getByte(address + 5);
            mem5 = getByte(address + 4);
            mem4 = getByte(address + 3);
            mem3 = getByte(address + 2);
            mem2 = getByte(address + 1);
            mem1 = getByte(address );
        }

        return mem1 & 0xFF | mem2 & 0xFF << 8 | mem3 & 0xFF << 16 | mem4 & 0xFF << 24 | mem5 & 0xFF << 32 | mem6 & 0xFF << 40 | mem7 & 0xFF << 48 | mem8 & 0xFF << 56;
    }
    default int getWord(long address, boolean bigEndian){

        byte mem4;
        byte mem3;
        byte mem2;
        byte mem1;

        if(bigEndian) {
            mem4 = getByte(address);
            mem3 = getByte(address + 1);
            mem2 = getByte(address + 2);
            mem1 = getByte(address + 3);
        }else{
            mem4 = getByte(address + 3);
            mem3 = getByte(address + 2);
            mem2 = getByte(address + 1);
            mem1 = getByte(address);
        }

        return mem1 & 0xFF | mem2 & 0xFF << 8 | mem3 & 0xFF << 16 | mem4 & 0xFF << 24;
    }
    default short getShort(long address, boolean bigEndian){

        byte mem2;
        byte mem1;

        if(bigEndian) {
            mem2 = getByte(address);
            mem1 = getByte(address + 1);
        }else{
            mem2 = getByte(address + 1);
            mem1 = getByte(address );
        }

        return (short) (mem1 & 0xFF | mem2 & 0xFF << 8);
    }

    default void setLong(long address, long value, boolean bigEndian){

        if(bigEndian) {
            setByte(address, (byte) (value >> 56 & 0xFF));
            setByte(address + 1, (byte) (value >> 48 & 0xFF));
            setByte(address + 2, (byte) (value >> 40 & 0xFF));
            setByte(address + 3, (byte) (value >> 32 & 0xFF));
            setByte(address + 4, (byte) (value >> 24 & 0xFF));
            setByte(address + 5, (byte) (value >> 16 & 0xFF));
            setByte(address + 6, (byte) (value >> 8 & 0xFF));
            setByte(address + 7, (byte) (value & 0xFF));
        }else{
            setByte(address + 7, (byte) (value >> 56 & 0xFF));
            setByte(address + 6, (byte) (value >> 48 & 0xFF));
            setByte(address + 5, (byte) (value >> 40 & 0xFF));
            setByte(address + 4, (byte) (value >> 32 & 0xFF));
            setByte(address + 3, (byte) (value >> 24 & 0xFF));
            setByte(address + 2, (byte) (value >> 16 & 0xFF));
            setByte(address + 1, (byte) (value >> 8 & 0xFF));
            setByte(address, (byte) (value & 0xFF));
        }
    }

    default void setWord(long address, int value, boolean bigEndian){
        if(bigEndian) {
            setByte(address, (byte) (value >> 24 & 0xFF));
            setByte(address + 1, (byte) (value >> 16 & 0xFF));
            setByte(address + 2, (byte) (value >> 8 & 0xFF));
            setByte(address + 3, (byte) (value & 0xFF));
        }else{
            setByte(address + 3, (byte) (value >> 24 & 0xFF));
            setByte(address + 2, (byte) (value >> 16 & 0xFF));
            setByte(address + 1, (byte) (value >> 8 & 0xFF));
            setByte(address, (byte) (value & 0xFF));
        }
    }

    default void setShort(long address, short value, boolean bigEndian){
        if(bigEndian) {
            setByte(address, (byte) (value >> 8 & 0xFF));
            setByte(address + 1, (byte) (value & 0xFF));
        }else{
            setByte(address + 1, (byte) (value >> 8 & 0xFF));
            setByte(address, (byte) (value & 0xFF));
        }
    }


    default void add(long address, byte[] bytes){
       this.add(address, 0, bytes.length, bytes);
    }

    default void add(long address, long start, long end, byte[] bytes){
        add(address, start, end, new ByteMemory(bytes));
    }

    default void add(long address, Memory memory){
        this.add(address, 0, memory.getSize(), memory);
    }

    default void add(long address, long start, long end, Memory memory){
        for(long i = start; i < end; i ++){
            this.setByte(address, memory.getByte(i));
            address ++;
        }
    }

}
