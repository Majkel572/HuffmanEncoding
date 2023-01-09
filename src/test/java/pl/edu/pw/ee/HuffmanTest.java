package pl.edu.pw.ee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

public class HuffmanTest {

    @Test
    public void should_workWithMrTadeush() throws IOException {
        Huffman huffman = new Huffman();
        huffman.huffman("src\\testFiles\\PanTadeusz\\", true);
        huffman.huffman("src\\testFiles\\PanTadeusz\\", false);
        Path original = Path.of("src", "testFiles", "PanTadeusz", "input.txt");
        Path decompressed = Path.of("src", "testFiles", "PanTadeusz", "inputDecompressed.txt");

        assertEquals(-1, filesCompareByLine(original, decompressed));
    }

    @Test
    public void should_workWithOnlyEnters() throws IOException {
        Huffman huffman = new Huffman();
        // huffman.huffman("src\\testFiles\\fullEnters\\", true);
        huffman.huffman("src\\testFiles\\fullEnters\\", false);
        Path original = Path.of("src", "testFiles", "fullEnters", "input.txt");
        Path decompressed = Path.of("src", "testFiles", "fullEnters", "inputDecompressed.txt");

        assertEquals(-1, filesCompareByLine(original, decompressed));
    }

    @Test
    public void should_workWithSpecialSigns() throws IOException {
        Huffman huffman = new Huffman();
        huffman.huffman("src\\testFiles\\specialSigns\\", true);
        huffman.huffman("src\\testFiles\\specialSigns\\", false);
        Path original = Path.of("src", "testFiles", "specialSigns", "input.txt");
        Path decompressed = Path.of("src", "testFiles", "specialSigns", "inputDecompressed.txt");

        assertEquals(-1, filesCompareByLine(original, decompressed));
    }

    @Test
    public void should_workWithCasualSigns() throws IOException {
        Huffman huffman = new Huffman();
        huffman.huffman("src\\testFiles\\casualSigns\\", true);
        huffman.huffman("src\\testFiles\\casualSigns\\", false);
        Path original = Path.of("src", "testFiles", "casualSigns", "input.txt");
        Path decompressed = Path.of("src", "testFiles", "casualSigns", "inputDecompressed.txt");

        assertEquals(-1, filesCompareByLine(original, decompressed));
    }

    @Test
    public void should_workWithOneLetter() throws IOException {
        Huffman huffman = new Huffman();
        huffman.huffman("src\\testFiles\\oneLetter\\", true);
        huffman.huffman("src\\testFiles\\oneLetter\\", false);
        Path original = Path.of("src", "testFiles", "oneLetter", "input.txt");
        Path decompressed = Path.of("src", "testFiles", "oneLetter", "inputDecompressed.txt");

        assertEquals(-1, filesCompareByLine(original, decompressed));
    }

    @Test
    public void should_workWithEmptyFile() throws IOException {
        Huffman huffman = new Huffman();
        huffman.huffman("src\\testFiles\\emptyFile\\", true);
        huffman.huffman("src\\testFiles\\emptyFile\\", false);
        Path original = Path.of("src", "testFiles", "emptyFile", "input.txt");
        Path decompressed = Path.of("src", "testFiles", "emptyFile", "inputDecompressed.txt");

        assertEquals(-1, filesCompareByLine(original, decompressed));
    }

    @Test
    public void shouldNot_workWith_badDictionary_orBadCompressedFile() throws IOException{
        Huffman huffman = new Huffman();
        huffman.huffman("src\\testFiles\\badDictionaryAndCompressed\\", false);
        Path original = Path.of("src", "testFiles", "badDictionaryAndCompressed", "input.txt");
        Path decompressed = Path.of("src", "testFiles", "badDictionaryAndCompressed", "inputDecompressed.txt");
        assertNotEquals(-1, filesCompareByLine(original, decompressed));
    }

    private int filesCompareByLine(Path path1, Path path2) {
        try (BufferedReader bf1 = Files.newBufferedReader(path1);
                BufferedReader bf2 = Files.newBufferedReader(path2)) {

            int lineNumber = 1;
            String line1 = "", line2 = "";
            while ((line1 = bf1.readLine()) != null) {
                line2 = bf2.readLine();
                if (line2 == null || !line1.equals(line2)) {
                    return lineNumber;
                }
                lineNumber++;
            }
            if (bf2.readLine() == null) {
                return -1;
            } else {
                return lineNumber;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -10;
    }
}
