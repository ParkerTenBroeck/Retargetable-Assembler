package org.parker.util;

import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class CompressionTest {

    boolean printTree = false;
    boolean printText = false;

    @Test
    public void Compress() throws IOException {
        int[] tally = new int[256];
        Node[] nodeV = new Node[256];

        //File file = new File("src/test/resources/MIPS/PreProcessor/test2.asm");
        //File file = new File("C:\\Users\\parke\\Desktop\\noita backup\\5 orbs\\save00\\world\\world_10240_-512.png_petri");

        byte[] fileC;
        fileC = "hello my name is someone who is unnamed this is a test hehehehhe".getBytes(StandardCharsets.UTF_8);
        //fileC = Files.readAllBytes(file.toPath());

        System.out.println("size(bits): " + fileC.length * 8 + " (bytes)" + fileC.length);

        if(printText) System.out.println(new String(fileC));

        for(int i = 0; i < fileC.length; i ++){
            tally[((int)fileC[i]) & 0xFF]++;
        }
        PriorityQueue<Node> nodes = new PriorityQueue<>(Comparator.comparingInt(o -> o.frequ));

        for(int i = 0; i < tally.length; i ++){
            if(tally[i] == 0) continue;
            nodes.add(nodeV[i] = new Node((byte)i, tally[i]));
        }
        while(nodes.size() > 1){
            Node right = nodes.poll();
            Node left = nodes.poll();
            nodes.add(new Node(left, right));
        }
        Node tree = nodes.poll();
        tree.calcRep(0, 0);
        System.out.println("First Tree: Height: " + tree.getHeight() + " Total Frequ: " + tree.frequ +
                " Size(bits): " + tree.getSize() + " (bytes)" + tree.getSize() / 8.0 + " Compression Ratio: " +  (tree.getSize() /  8.0) / tree.frequ);

        if(printTree){
            StringBuilder buffer = new StringBuilder();
            print(tree, buffer, "", "");
            System.out.println(buffer);
        }

        File out = new File("src/test/resources/MIPS/PreProcessor/test2c.asm");
        ByteArrayOutputStream babs = new ByteArrayOutputStream((int)Math.round(Math.ceil(tree.frequ * 8.0 / (tree.getHeight() + 1))) + /*tree size*/ 0);
        BitOutputStream bout = new BitOutputStream(babs);

        tree.writeToBiteStream(bout);
        System.out.println("Tree Size(bits): " + bout.getSize() + " (bytes): " + bout.getSize() / 8.0 + " Compression Ratio With Tree: " + (bout.getSize() / 8.0 + tree.getSize() /  8.0) / tree.frequ);

        for(int i = 0; i < fileC.length; i ++){
            bout.write(nodeV[((int)fileC[i]) & 0xFF].rep, nodeV[((int)fileC[i]) & 0xFF].level);
        }
        bout.close();

        byte[] temp = babs.toByteArray();
        BitInputStream bis = new BitInputStream(new ByteArrayInputStream(temp));

        tree = Node.fromBitInputStream(bis);
        ByteArrayOutputStream textRebuild = new ByteArrayOutputStream();

        Node tmp = tree;
        while(bis.available() > 0){
            if(bis.read()){
                tmp = tmp.left;
            }else{
                tmp = tmp.right;
            }
            if(tmp.isLeaf()){
                textRebuild.write(tmp.value);
                tmp = tree;
            }
        }
        byte[] reconstruct = textRebuild.toByteArray();
        boolean correct = true;
        for(int i = 0; i < fileC.length; i ++){
            if(reconstruct[i] != fileC[i]){
                System.out.println("It DOESNT MATCH");
                correct = false;
                break;
            }
        }
        if(printText) System.out.println(new String(textRebuild.toByteArray()));

        if(printTree) {
            StringBuilder buffer = new StringBuilder();
            print(tree, buffer, "", "");
            System.out.println("size: " + temp.length);
        }

        System.out.println("\nStarting Size: " + fileC.length + "(bytes) Ending Size(With Tree): " + temp.length + "(bytes) Total Size Reduction: " + (fileC.length - temp.length) + "(bytes)");
    }

    private void print(Node tree, StringBuilder buffer, String prefix, String childrenPrefix) {

        buffer.append(prefix);
        if(tree.isLeaf()){
            buffer.append("level: " + tree.level + " frequ: " + tree.frequ + " rep: " + Integer.toBinaryString(tree.rep) + " value: '" + (char)tree.value + "'" + "(" + Integer.toHexString(tree.value) + ")");
            buffer.append('\n');
            return;
        }else{
            buffer.append("level: " + tree.level + " frequ: " + tree.frequ);
            buffer.append('\n');
        }
            print(tree.left, buffer, childrenPrefix + "|-- ", childrenPrefix + "|   ");
            print(tree.right, buffer, childrenPrefix + "\\-- ", childrenPrefix + "    ");
    }

    private static class Node{
        byte value;
        Node left;
        Node right;
        transient Node parent;
        transient int frequ;
        transient int rep;
        transient int level;

        public Node(){

        }

        public Node(byte value, int frequ){
            this.value = value;
            this.frequ = frequ;
        }

        public void writeToBiteStream(BitOutputStream out) throws IOException {
            out.write(isLeaf());
            if(isLeaf()){
                out.write(value, 8);
            }else{
                left.writeToBiteStream(out);
                right.writeToBiteStream(out);
            }
        }

        public int getSize(){
            if(isLeaf()){
                return frequ * level;
            }else{
                return left.getSize() + right.getSize();
            }
        }

        public static Node fromBitInputStream(BitInputStream in) throws IOException {
            return fromBitInputStream(in, 0);
        }

        public static Node fromBitInputStream(BitInputStream in, int level) throws IOException {
            boolean isLeaf = in.read();
            Node n = new Node();
            if(isLeaf){
                n.value = (byte)in.read(8);
            }else{
                n.left = fromBitInputStream(in, level + 1);
                n.right = fromBitInputStream(in, level + 1);
            }
            n.level = level;
            return n;
        }

        public int getHeight(){
            return getHeight(0);
        }

        private int getHeight(int level){
            if(isLeaf()) return level;
            return Integer.max(left.getHeight(level + 1), right.getHeight(level + 1));
        }

        public Node(Node left, Node right){
            this.left = left;
            this.right = right;
            this.frequ = left.frequ + right.frequ;
            left.parent = this;
            right.parent = this;
        }

        public boolean isLeaf(){
            return left == null && right == null;
        }

        public void calcRep(int i, int level) {
            if(isLeaf()) {
                rep = i;
                this.level = level;
                return;
            }
            this.level = level;
            left.calcRep(i << 1 | 1, level + 1);
            right.calcRep(i << 1, level + 1);
        }
    }

    public static class BitInputStream {

        private InputStream in;
        private int num = 0;
        private int count = 7;

        public BitInputStream(InputStream in) {
            this.in = in;
        }

        public int available() throws IOException {
            return in.available() * 8 + 7 - count;
        }


        public int read(int num) throws IOException{
            int val = 0;
            for(int i = 0; i < num; i ++){
                val |= (read() ? 1 : 0) << (num - i - 1);
            }
            return val;
        }

        public boolean read() throws IOException {
            this.count++;
            if(count == 8){
                count = 0;
            }

            if(count == 0){
                num = in.read();
            }
            boolean ret = ((num >> (7 - count)) & 1) > 0;

            return ret;
        }

        public void close() throws IOException {
            this.in.close();
        }

    }

    public static class BitOutputStream {

        private OutputStream out;
        private byte buffer = 0;
        private int count = 0;
        private int size;

        public BitOutputStream(OutputStream out) {
            this.out = out;
        }

        public int getSize(){
            return size;
        }

        public void write(boolean x) throws IOException {
            size ++;
            buffer |= (x ? 1 : 0) << (7 - count);
            this.count++;
            if(count == 8){
                try {
                    out.write(buffer);
                }catch (Error e){
                    System.out.println("asd");
                }
                count = 0;
                buffer = 0;
            }
        }

        public void write(int x, int num) throws IOException {
            for(int i = 0 ; i < num; i ++){
                write(((x >> (num - i - 1)) & 1) > 0);
            }
        }

        public void close() throws IOException {
            if(count != 0) this.out.write(buffer);
            this.out.close();
        }

    }
}
