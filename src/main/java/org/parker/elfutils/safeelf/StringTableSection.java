package org.parker.elfutils.safeelf;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class StringTableSection extends SectionDataInterpreter {

    private ArrayList<String> strings = new ArrayList<>();
    private int size;

    public StringTableSection(){

    }

    public String getString(int index){
        return strings.get(index);
    }

    public void addString(String string){
        strings.add(string);
        size += string.length() + 1;
    }
    public void removeString(int index){
        size += strings.get(index).length() + 1;
        strings.remove(index);
    }

    @Override
    public void loadData() {
        strings = new ArrayList<>();
        byte[] data = parentSection.getData();
        int index = 0;
        while((index + size) < data.length){
            int size = 0;
            while(index < data.length && data[index] != 0){
                size ++;
            }
            strings.add(new String(data, index, size));
            index += size;
        }
    }

    @Override
    public byte[] flushData() {
        byte[] data = new byte[size];
        int index = 0;
        for(int i = 0; i < strings.size(); i ++){
            byte[] string = strings.get(i).getBytes(StandardCharsets.UTF_8);
            for(int is = 0; is < string.length; is ++){
                data[index] = string[is];
                index ++;
            }
            data[index] = 0;
            index ++;
        }
        return data;
    }

    @Override
    public long getDataSize() {
        return size;
    }
}
