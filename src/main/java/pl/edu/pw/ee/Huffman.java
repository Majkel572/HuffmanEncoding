package pl.edu.pw.ee;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Huffman {
    private File directory;
    private ArrayList<NodeSignCode> dictionary;

    public int huffman(String pathToRootDir, boolean compress) throws IOException {
        if (pathToRootDir != null) {
            directory = new File(pathToRootDir);
        } else {
            directory = new File("src\\testFiles\\niemanie\\");
        }
        String[] children = directory.list();

        if (children == null)
            throw new IllegalArgumentException("Either directory does not exist or is not a directory.");
        else {
            for (int i = 0; i < children.length; i++) {
                String filename = pathToRootDir + children[i];
                if (filename.equals(pathToRootDir + "input.txt") && compress == true) {
                    return compress(pathToRootDir);
                }
                if (filename.equals(pathToRootDir + "inputCompressed.txt") && compress == false) {
                    return decompress(pathToRootDir);
                }
            }
        }

        return -1;
    }

    private int compress(String pathToRootDir) {
        HuffmanNode root = makeHuffmanTree(pathToRootDir + "input.txt");
        try {
            createDictionaryFromTree(root);
            HuffmanIO.writeByteToFile(makeBinaryString(pathToRootDir + "input.txt", root), root,
                    pathToRootDir + "inputCompressed.txt");
            HuffmanIO.saveTreeToFile(root, pathToRootDir + "inputDictionary.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return root.getHowManyBytes() * 8;
    }

    private int decompress(String pathToRootDir) {
        HuffmanNode root = HuffmanIO.readTreeFromFile(pathToRootDir + "inputDictionary.txt");
        int unusedBits = createDictionaryFromTree(root);
        String binaryString = HuffmanIO.readBinFiles(pathToRootDir + "inputCompressed.txt", root);
        String text = convertBinToText(binaryString, unusedBits);
        return HuffmanIO.writeTextToFile(pathToRootDir + "inputDecompressed.txt", text);
    }

    private HuffmanNode makeHuffmanTree(String filename) {
        int[] signFrequencies = HuffmanIO.countCharFreqsFromFile(filename);
        MinHeap<HuffmanNode> minHeap = new MinHeap<>(signFrequencies.length);
        for (int i = 0; i < signFrequencies.length; i++) {
            if (signFrequencies[i] != 0) {
                minHeap.put(new HuffmanNode(Character.valueOf((char) i), signFrequencies[i]));
            }
        }

        HuffmanNode root = new HuffmanNode();
        if(minHeap.size() == 1){
            root.setLeftChild(minHeap.pop());
            root.setRightChild(new HuffmanNode());
            return root;
        }
        while (minHeap.size() > 1) {
            HuffmanNode x = minHeap.pop();
            HuffmanNode y = minHeap.pop();

            HuffmanNode freq = new HuffmanNode();
            freq.setFrequency(x.getFrequency() + y.getFrequency());

            freq.setLeftChild(x);
            freq.setRightChild(y);

            root = freq;

            minHeap.put(freq);
        }
        return root;
    }

    private String makeBinaryString(String filename, HuffmanNode root) {
        File file = new File(filename);
        String binaryString = "";
        int i = 0;
        try (FileReader fr = new FileReader(file)) {
            int content;
            while ((content = fr.read()) != -1) {
                char c = (char) content;
                while (dictionary.get(i).getSign() != c) {
                    i++;
                }
                binaryString = binaryString.concat(dictionary.get(i).getCodedSign());
                i = 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int howManyBytes = binaryString.length() / 8;
        if (binaryString.length() % 8 != 0) {
            howManyBytes++;
        }
        root.setHowManyBytes(howManyBytes);
        return binaryString;
    }

    private void encodeChars(HuffmanNode root, String s) throws IOException {
        if (root.getLeftChild() == null && root.getRightChild() == null) {
            NodeSignCode nsc = new NodeSignCode(root.getSign(), s);
            dictionary.add(nsc);
            return;
        }
        encodeChars(root.getLeftChild(), s + "0");
        encodeChars(root.getRightChild(), s + "1");
    }

    private int createDictionaryFromTree(HuffmanNode root) {
        dictionary = new ArrayList<>();
        try {
            encodeChars(root, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root.getUnusedBits();
    }

    private String convertBinToText(String binaryString, int unusedBits) {
        String temp = new String();
        String text = new String();
        int j;
        boolean matchesCodedSign = true;
        for (int i = 0; i < binaryString.length(); i++) {
            matchesCodedSign = true;
            j = 0;
            temp = temp + binaryString.charAt(i);
            while (dictionary.get(j).getCodedSign().equals(temp) == false) {
                j++;
                if (j == dictionary.size()) {
                    matchesCodedSign = false;
                    break;
                }
            }

            if (matchesCodedSign == true) {
                Character c = dictionary.get(j).getSign();
                text = text + c;
                temp = "";
            }

        }
        return text;
    }
}
