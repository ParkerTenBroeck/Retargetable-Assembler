package org.parker;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
    public static void main(String... args) throws IOException {
        InputStream is  = new  FileInputStream("src/test/resources/MIPS/Test.java");
        OutputStream os  = new  FileOutputStream("src/test/resources/MIPS/Test2.java");

        Pattern pattern = Pattern.compile("\\s*public\\s+enum\\s+([a-zA-Z_][a-zA-Z_0-9]*)\\s*\\{");
        String replacement = "";

        StringBuilder builder = new StringBuilder();
        while(is.available() > 0){
            Character c = (char) is.read();
            builder.append(c);
            if(c == '\n'){
                String string = builder.toString();
                Matcher matcher = pattern.matcher(string);
                if(matcher.find()){
                    replacement = matcher.group(1);
                }else{
                    string = string.replace("elf_aarch64_reloc_type", replacement);
                }
                os.write(string.getBytes(StandardCharsets.UTF_8));
                builder.setLength(0);
            }
        }
    }
}
