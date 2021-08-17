package org.parker.retargetableassembler.base.linker;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Arrays;

public class Symbol implements Serializable{

    //these are transient only to allow for custom serialization
    public transient final String symbolMnemonic;
    public transient long sectionAddress;
    public transient String section;
    public transient SymbolType type;
    public transient SymbolBinding binding;
    //cant see this being used
    public transient SymbolVisibility visibility;

    public Symbol(String symbolMnemonic){
        this.symbolMnemonic = symbolMnemonic;
    }

    public static void main(String... args) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(out);

        Symbol s = new Symbol("test");
        s.type = SymbolType.SECTION;
        s.binding = SymbolBinding.WEAK;

        oout.writeObject(s);
        byte[] data = out.toByteArray();
        System.out.println(Arrays.toString(data));
        System.out.println(new String(data));

        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream oin = new ObjectInputStream(in);
        Object o = oin.readObject();
        if(o instanceof Symbol){
            SymbolBinding local = (SymbolBinding) o;
            System.out.println(local.name);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException{
        byte[] temp = symbolMnemonic.getBytes();
        out.writeShort(temp.length);
        out.write(temp);

        temp = type.name.getBytes();
        out.writeShort(temp.length);
        out.write(temp);

        temp = binding.name.getBytes();
        out.writeShort(temp.length);
        out.write(temp);
    }

    /**
     * Custom deserialization. We need to re-initialize a logger instance as loggers can't be serialized.
     */
    @SuppressWarnings("unused")
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {

        byte[] temp = new byte[in.readShort()];
        in.read(temp);
        String mnemonic = new String(temp);

        temp = new byte[in.readShort()];
        in.read(temp);
        type = new SymbolType(new String(temp));

        temp = new byte[in.readShort()];
        in.read(temp);
        binding = new SymbolBinding(new String(temp));

        //setting the final fields
        try {
            Class myType = Symbol.class;

            // use getDeclaredField as the field is non public
            Field symbolMnemonic = myType.getDeclaredField("symbolMnemonic");

            // make the field non final
            symbolMnemonic.setAccessible(true);
            symbolMnemonic.set(this, mnemonic);

            // make the field final again
            symbolMnemonic.setAccessible(false);

        } catch (Exception ignore) {
        }
    }
}
