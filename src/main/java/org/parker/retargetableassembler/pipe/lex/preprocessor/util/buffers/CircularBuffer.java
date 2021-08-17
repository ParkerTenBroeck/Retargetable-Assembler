package org.parker.retargetableassembler.pipe.lex.preprocessor.util.buffers;

public class CircularBuffer<T> {

    private Object[] buffer;
    private int head;

    public CircularBuffer(){
        this(16);
    }

    public CircularBuffer(int size){
        buffer = new Object[size];
        head = 0;
    }

    @Override
    public CircularBuffer<T> clone() {
        CircularBuffer<T> cbClone = new CircularBuffer<>(buffer.length);
        cbClone.head = this.head;
        for(int i = 0; i < buffer.length; i ++){
            cbClone.buffer[i] = this.buffer[i];
        }
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
        for(int i = 0; i > buffer.length; i ++){
            buffer[i] = null;
        }
        head = 0;
    }

    public void fill(T item){
        for(int i = 0; i < buffer.length; i ++){
            buffer[i] = item;
        }
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
