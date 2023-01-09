package pl.edu.pw.ee;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class HuffmanIOTest {
    String path;

    @Before
    public void init() {
        path = "src\\test\\java\\pl\\edu\\pw\\ee\\HuffmanIOTestFiles\\";
    }

    @Test
    public void should_countCharFreqsFromFile() {
        int[] tab = HuffmanIO.countCharFreqsFromFile(path + "abcd.txt");
        assertEquals(2, tab['a']);
        assertEquals(1, tab['b']);
        assertEquals(1, tab['c']);
        assertEquals(1, tab['d']);
    }

    @Test
    public void should_writeByteToFile_andReadByteFromFile() throws IOException {
        HuffmanNode hn = new HuffmanNode();
        String encoded = "001001001";
        HuffmanIO.writeByteToFile(encoded, hn, path + "should_writeByteToFile_andReadByteFromFile.txt");
        String decoded = HuffmanIO.readBinFiles(path + "should_writeByteToFile_andReadByteFromFile.txt", hn);
        assertEquals(encoded, decoded);
        assertEquals(7, hn.getUnusedBits());
    }

    @Test
    public void should_saveTreeToFile_andReadTreeFromFile() {
        HuffmanNode hn = new HuffmanNode('\u0000', new HuffmanNode('a', 2), new HuffmanNode(), 6);
        HuffmanIO.saveTreeToFile(hn, path + "should_saveTreeToFile_andReadTreeFromFile.txt");
        HuffmanNode readHn = HuffmanIO.readTreeFromFile(path + "should_saveTreeToFile_andReadTreeFromFile.txt");
        assertEquals('a', readHn.getLeftChild().getSign());
    }
}
