package org.parker.retargetableassembler.util.buffers;

import java.util.Arrays;

@SuppressWarnings({"unchecked", "unused"})
public class CircularBuffer<T> {

    private Object[] buffer;
    private int head;

    public CircularBuffer(){
        this(16);
    }

    public CircularBuffer(int size){
        this(size, null);
    }

    public CircularBuffer(T fill){
        this(16, fill);
    }

    public CircularBuffer(int size, T fill){
        this.buffer = new Object[size];
        this.head = 0;
        this.fill(fill);
    }

    @Override
    public CircularBuffer<T> clone() {
        CircularBuffer<T> cbClone = new CircularBuffer<>(buffer.length);
        cbClone.head = this.head;
        System.arraycopy(this.buffer, 0, cbClone.buffer, 0, buffer.length);
        return cbClone;
    }

    public void add(T item){
        this.head ++;
        if(head >= buffer.length){
            head = 0;
        }
        buffer[head] = item;
    }

    public void clear(){
        Arrays.fill(buffer, null);
        head = 0;
    }

    public void fill(T item){
        Arrays.fill(buffer, item);
    }

    public T get(){
        return (T) buffer[head];
    }

    public T get(int offset){
        if(offset >= buffer.length){
            throw new IndexOutOfBoundsException();
        }
        int index = head - offset;
        if(index < 0){
            index += buffer.length;
        }
        return (T) buffer[index];
    }


    public void resize(int size){
        resize(size, null);
    }

    public void resize(int size, T fill){
        if(size <= 0){
            throw new IllegalArgumentException("size cannot be less than zero");
        }
        Object[] newBuffer = new Object[size];
        for(int i = 0; i < size; i ++){
            if(i < buffer.length){
                newBuffer[i] = fill;
            }else{
                newBuffer[i] = this.get(i);
            }
        }
        this.buffer = newBuffer;
        this.head = 0;
    }

    public int getSize() {
        return buffer.length;
    }
}
