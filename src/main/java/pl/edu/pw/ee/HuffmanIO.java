package pl.edu.pw.ee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HuffmanIO {
    public static int[] countCharFreqsFromFile(String filename) {
        File file = new File(filename);
        int[] signFrequencies = new int[256];

        try (FileReader fr = new FileReader(file, StandardCharsets.UTF_8)) {
            int content;
            while ((content = fr.read()) != -1) {
                char c = (char) content;
                while (c > signFrequencies.length - 1) {
                    signFrequencies = doubleResize(signFrequencies);
                }
                signFrequencies[c] = signFrequencies[c] + 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return signFrequencies;
    }

    private static int[] doubleResize(int[] signFrequencies) {
        int[] resizedSignFrequencies = new int[2 * signFrequencies.length];
        System.arraycopy(signFrequencies, 0, resizedSignFrequencies, 0, signFrequencies.length - 1);
        return resizedSignFrequencies;
    }

    public static void writeByteToFile(String binaryString, HuffmanNode root, String filename) throws IOException {
        int nullBits = 8 - (binaryString.length() % 8);
        root.setUnusedBits(nullBits);
        FileOutputStream fos = new FileOutputStream(filename);
        String adjustedBinaryString = "";
        int tmpBits = binaryString.length() - (binaryString.length() % 8);
        String tmp = binaryString.substring(tmpBits, binaryString.length());
        adjustedBinaryString = binaryString.substring(0, tmpBits);
        for (int i = 0; i < nullBits; i++) {
            adjustedBinaryString = adjustedBinaryString.concat("0");
        }
        adjustedBinaryString = adjustedBinaryString.concat(tmp);
        for (int i = 0; i < adjustedBinaryString.length(); i += 8) {
            String byteString = adjustedBinaryString.substring(i, i + 8);
            byte byteData = (byte) Integer.parseInt(byteString, 2);
            fos.write(byteData);
        }
        fos.close();
    }

    public static void saveTreeToFile(HuffmanNode root, String filepath) {

        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter(filepath))) {
            bWriter.write(Integer.toString(root.getUnusedBits()));
            helpRecursiveMethod(bWriter, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void helpRecursiveMethod(BufferedWriter bWriter, HuffmanNode node) throws IOException {
        if (node.isLeafNode()) {
            bWriter.write('1');
            bWriter.write(node.getSign());
        } else {
            bWriter.write('0');
            helpRecursiveMethod(bWriter, node.getLeftChild());
            helpRecursiveMethod(bWriter, node.getRightChild());
        }
    }

    public static HuffmanNode readTreeFromFile(String filename) {
        HuffmanNode root = new HuffmanNode();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            root = readTreeFromFileR(root, br, true, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    private static HuffmanNode readTreeFromFileR(HuffmanNode root, BufferedReader br, boolean isFirst, int uB)
            throws IOException {
        int unusedBits = uB;
        if (isFirst == true) {
            unusedBits = Character.getNumericValue(br.read());
            isFirst = false;
        }
        if (br.read() == '1') {
            return new HuffmanNode((char) br.read(), null, null, unusedBits);
        } else {
            HuffmanNode leftChild = readTreeFromFileR(root, br, false, unusedBits);
            HuffmanNode rightChild = readTreeFromFileR(root, br, false, unusedBits);
            return new HuffmanNode((char) 0, leftChild, rightChild, unusedBits);
        }
    }

    public static String readBinFiles(String filename, HuffmanNode root) {
        File file = new File(filename);
        FileInputStream fin;
        String binaryString = "";
        String oneIntToBinaryString;
        String unusedBitsZerosString = "";
        int content;
        int unusedBits = root.getUnusedBits();
        byte oneByte;
        int j = 0;
        for (int i = 0; i < unusedBits; i++) {
            unusedBitsZerosString += "0";
        }
        try {
            fin = new FileInputStream(file);
            long howManyBytes = Files.size(Paths.get(filename));
            while ((content = fin.read()) != -1) {
                oneByte = (byte) content;
                oneIntToBinaryString = String.format("%8s", Integer.toBinaryString(oneByte & 0xFF)).replace(' ', '0');
                int notImportantLength = oneIntToBinaryString.length() - (8 - unusedBits);
                if (j == howManyBytes - 1) {
                    if (oneIntToBinaryString.substring(0, notImportantLength).equals(unusedBitsZerosString)) {
                        oneIntToBinaryString = oneIntToBinaryString.substring(notImportantLength,
                                oneIntToBinaryString.length());
                    }
                }
                binaryString += oneIntToBinaryString;
                j++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("File not found, program won't work.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return binaryString;
    }

    public static int writeTextToFile(String filename, String text) {
        try {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(filename), StandardCharsets.UTF_8));
            writer.write(text);
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return text.length();
    }
}
