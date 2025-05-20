package edu.gatech.seclass.mutatetxt;

import java.beans.Transient;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.text.Position;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.io.TempDir;

import java.util.concurrent.TimeUnit;

@Timeout(value = 1, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
public class MyMainTest {
    // Place all of your tests in this class, optionally using MainTest.java as an
    // example
    private final String usageStr = "Usage: mutatetxt [ -i | -e substring | -k substring | -f style substring | -d num | -n padding ] FILE"
            + System.lineSeparator();

    @TempDir
    Path tempDirectory;

    @RegisterExtension
    OutputCapture capture = new OutputCapture();

    /* ----------------------------- Test Utilities ----------------------------- */

    /**
     * Returns path of a new "input.txt" file with specified contents written into
     * it. The file will
     * be created using {@link TempDir TempDir}, so it is automatically deleted
     * after test
     * execution.
     *
     * @param contents the text to include in the file
     * @return a Path to the newly written file, or null if there was an issue
     *         creating the file
     */
    private Path createFile(String contents) {
        return createFile(contents, "input.txt");
    }

    /**
     * Returns path to newly created file with specified contents written into it.
     * The file will be
     * created using {@link TempDir TempDir}, so it is automatically deleted after
     * test execution.
     *
     * @param contents the text to include in the file
     * @param fileName the desired name for the file to be created
     * @return a Path to the newly written file, or null if there was an issue
     *         creating the file
     */
    private Path createFile(String contents, String fileName) {
        Path file = tempDirectory.resolve(fileName);
        try {
            Files.writeString(file, contents);
        } catch (IOException e) {
            return null;
        }

        return file;
    }

    /**
     * Takes the path to some file and returns the contents within.
     *
     * @param file the path to some file
     * @return the contents of the file as a String, or null if there was an issue
     *         reading the file
     */
    private String getFileContent(Path file) {
        try {
            return Files.readString(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* ------------------------------- Test Cases ------------------------------- */

    // Frame #: 1
    // <error> Size of file : None
    @Test
    public void mutatetxtTest1() {
        String[] args = {};
        Main.main(args);
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertTrue(capture.stdout().isEmpty());
    }

    // Frame #: 2
    // <single> Size of file : Not Empty
    @Test
    public void mutatetxtTest2() {
        String input = "World is Mine file not empty" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(input, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 3
    // <single> (follows [if])
    // Final Line Termination : NewLine Present in File
    @Test
    public void mutatetxtTest3() {
        String input = "This new line is present " + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(input, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 4
    // <error> (follows [if])
    // Final Line Termination : NewLine Missing in File
    @Test
    public void mutatetxtTest4() {
        String input = "World is Mine";
        Path inputFile = createFile(input);
        String[] args = { inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertTrue(capture.stdout().isEmpty());
    }

    // Frame #: 5
    // <single>
    // Presence of d option : None
    @Test
    public void mutatetxtTest5() {
        String input = "World is Mine" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(input, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 6
    // <single>
    // Presence of d option : Many
    @Test
    public void mutatetxtTest6() {
        String input = "World is Mine" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-d", "2", "-d", "1", inputFile.toString() };
        Main.main(args);

        String expectedOut = "World is Mine" + System.lineSeparator() + "World is Mine" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 7
    // <error> (follows [if])
    // Parameter value of d option : <0
    @Test
    public void mutatetxtTest7() {
        String input = "World is Mine" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-d", "-39", inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertTrue(capture.stdout().isEmpty());
    }

    // Frame #: 8
    // <single> (follows [if])
    // Parameter value of d option : 0
    @Test
    public void mutatetxtTest8() {
        String input = "World is Mine" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-d", "0", inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(input, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 9
    // <single> (follows [if])
    // Parameter value of d option : 1-9
    @Test
    public void mutatetxtTest9() {
        String input = "World is Mine" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-d", "2", inputFile.toString() };
        Main.main(args);

        String expectedOut = "World is Mine" + System.lineSeparator() +
                "World is Mine" + System.lineSeparator() + "World is Mine" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 10
    // <error> (follows [if])
    // Parameter value of d option : >9
    @Test
    public void mutatetxtTest10() {
        String input = "World is Mine" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-d", "39", inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertTrue(capture.stdout().isEmpty());
    }

    // Frame #: 11
    // <error> (follows [if])
    // Parameter value of d option : Non-Integer-Value
    @Test
    public void mutatetxtTest11() {
        String input = "World is Mine" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-d", "Miku", inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertTrue(capture.stdout().isEmpty());
    }

    // Frame #: 12
    // <single>
    // Presence of f option : None
    @Test
    public void mutatetxtTest12() {
        String input = "World is Mine no f" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-d", "1", inputFile.toString() };
        Main.main(args);

        String expectedOut = "World is Mine no f" + System.lineSeparator() +
                "World is Mine no f" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 13
    // <single>
    // Presence of f option : Many
    @Test
    public void mutatetxtTest13() {
        String input = "World is Mine!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-f", "bold", "Mine", "-f", "bold", "World", inputFile.toString() };
        Main.main(args);

        String expectedOut = "**World** is Mine!" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 14
    // <single> (follows [if])
    // Parameter value of f option : Bold
    @Test
    public void mutatetxtTest14() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-f", "bold", "World", inputFile.toString() };
        Main.main(args);

        String expectedOut = "**World** is Mine!" + System.lineSeparator() +
                "Mine is this **World**!" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 15
    // <single> (follows [if])
    // Parameter value of f option : Italic
    @Test
    public void mutatetxtTest15() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-f", "italic", "World", inputFile.toString() };
        Main.main(args);

        String expectedOut = "*World* is Mine!" + System.lineSeparator() +
                "Mine is this *World*!" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 16
    // <single> (follows [if])
    // Parameter value of f option : Code
    @Test
    public void mutatetxtTest16() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-f", "code", "World", inputFile.toString() };
        Main.main(args);

        String expectedOut = "`World` is Mine!" + System.lineSeparator() +
                "Mine is this `World`!" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 17
    // <error> (follows [if])
    // Parameter value of f option : empty/invalid (ie style missing)
    @Test
    public void mutatetxtTest17() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-f", "World", inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertTrue(capture.stdout().isEmpty());
    }

    // Frame #: 18
    // <error> (follows [if])
    // Paramter value of f substring : Empty
    @Test
    public void mutatetxtTest18() {
        String input = "World is Mine!" + System.lineSeparator() + "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-f", "bold", inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertTrue(capture.stdout().isEmpty());
    }

    // Frame #: 19
    // <single> (follows [if])
    // Paramter value of f substring : Not-empty
    @Test
    public void mutatetxtTest19() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-f", "bold", "this", inputFile.toString() };
        Main.main(args);

        String expectedOut = "World is Mine!" + System.lineSeparator() +
                "Mine is **this** World!" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 20
    // <single>
    // Presence of i options : None
    @Test
    public void mutatetxtTest20() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-d", "1", "-f", "bold", "World", inputFile.toString() };
        Main.main(args);

        String expectedOut = "**World** is Mine!" + System.lineSeparator() +
                "**World** is Mine!" + System.lineSeparator() +
                "Mine is this **World**!" + System.lineSeparator() +
                "Mine is this **World**!" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 21
    // <single>
    // Presence of i options : Many
    @Test
    public void mutatetxtTest21() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-i", "-k", "world", "-i", "-k", "World", inputFile.toString() };
        Main.main(args);

        String expectedOut = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 22
    // <single>D
    // Presence of k options : None
    @Test
    public void mutatetxtTest22() {
        String input = "World is Mine! nok" + System.lineSeparator() +
                "Mine is this World! nok" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-d", "1", "-f", "bold", "World", inputFile.toString() };
        Main.main(args);

        String expectedOut = "**World** is Mine! nok" + System.lineSeparator() +
                "**World** is Mine! nok" + System.lineSeparator() +
                "Mine is this **World**! nok" + System.lineSeparator() +
                "Mine is this **World**! nok" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 23
    // <single>
    // Presence of k options : Many
    @Test
    public void mutatetxtTest23() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-k", "World", "-k", "this", inputFile.toString() };
        Main.main(args);

        String expectedOut = "Mine is this World!" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 24
    // <error> (follows [if])
    // Presence of k string : Empty
    @Test
    public void mutatetxtTest24() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-k", inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertTrue(capture.stdout().isEmpty());
    }

    // Frame #: 25
    // <single> (follows [if])
    // Presence of k string : Not-Empty
    @Test
    public void mutatetxtTest25() {
        String input = "World is Mine! kstring" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-k", "this", inputFile.toString() };
        Main.main(args);

        String expectedOut = "Mine is this World!" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 26
    // <single>
    // Presence of e options : None
    @Test
    public void mutatetxtTest26() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-d", "1", "-f", "bold", "World", "-i", "-k", "World", inputFile.toString() };
        Main.main(args);

        String expectedOut = "**World** is Mine!" + System.lineSeparator() +
                "**World** is Mine!" + System.lineSeparator() +
                "Mine is this **World**!" + System.lineSeparator() +
                "Mine is this **World**!" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 27
    // <single>
    // Presence of e options : Many
    @Test
    public void mutatetxtTest27() {
        String input = "World is Mine!" + System.lineSeparator() + "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-e", "World", "-e", "this", inputFile.toString() };
        Main.main(args);

        String expectedOut = "World is Mine!" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 28
    // <error> (follows [if])
    // Presence of e string : Empty
    @Test
    public void mutatetxtTest28() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-e", inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertTrue(capture.stdout().isEmpty());
    }

    // Frame #: 29
    // <single> (follows [if])
    // Presence of e string : Not-Empty
    @Test
    public void mutatetxtTest29() {
        String input = "World is Mine!" + System.lineSeparator() + "Mine is this World! enotempty"
                + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-e", "this", inputFile.toString() };
        Main.main(args);

        String expectedOut = "World is Mine!" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 30
    // <single>
    // Presence of n option : None
    @Test
    public void mutatetxtTest30() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-d", "1", "-f", "bold", "World", "-i", "-e", "this", inputFile.toString() };
        Main.main(args);

        String expectedOut = "**World** is Mine!" + System.lineSeparator() +
                "**World** is Mine!" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 31
    // <single>
    // Presence of n option : Many
    @Test
    public void mutatetxtTest31() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-n", "3", "-n", "2", inputFile.toString() };
        Main.main(args);

        String expectedOut = "01 World is Mine!" + System.lineSeparator() +
                "02 Mine is this World!" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 32
    // <error> (follows [if])
    // Parameter value of n option : <1
    @Test
    public void mutatetxtTest32() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-n", "0", inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertTrue(capture.stdout().isEmpty());
    }

    // Frame #: 33
    // <single> (follows [if])
    // Parameter value of n option : 1-9
    @Test
    public void mutatetxtTest33() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-n", "3", inputFile.toString() };
        Main.main(args);

        String expectedOut = "001 World is Mine!" + System.lineSeparator() +
                "002 Mine is this World!" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 34
    // <error> (follows [if])
    // Parameter value of n option : >9
    @Test
    public void mutatetxtTest34() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-n", "10", inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertTrue(capture.stdout().isEmpty());
    }

    // Frame #: 35
    // <error> (follows [if])
    // Parameter value of n option : Non-Integer-Value
    @Test
    public void mutatetxtTest35() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-n", "Miku", inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertTrue(capture.stdout().isEmpty());
    }

    // Frame #: 36
    // <single> Option-e and-k presence: Only-e Present
    @Test
    public void mutatetxtTest36() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-e", "this", inputFile.toString() };
        Main.main(args);

        String expectedOut = "World is Mine!" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 37
    // <single> Option-e and-k presence: Only-k Present
    @Test
    public void mutatetxtTest37() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-k", "this", inputFile.toString() };
        Main.main(args);

        String expectedOut = "Mine is this World!" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 38
    // <single> Option-e and-k presence: None Present
    @Test
    public void mutatetxtTest38() {
        String input = "World is Mine! noek" + System.lineSeparator() +
                "Mine is this World! noek" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-d", "1", "-f", "bold", "World", inputFile.toString() };
        Main.main(args);

        String expectedOut = "**World** is Mine! noek" + System.lineSeparator() +
                "**World** is Mine! noek" + System.lineSeparator() +
                "Mine is this **World**! noek" + System.lineSeparator() +
                "Mine is this **World**! noek" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 39
    // <error> Option-e and-k presence: Both e and k present
    @Test
    public void mutatetxtTest39() {
        String input = "World is Mine!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-e", "World", "-k", "Mine", inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertTrue(capture.stdout().isEmpty());
    }

    // Frame #: 40
    // <single> (follows [if])
    // Case sensitivity option for k and e : Case sensitive -e

    @Test
    public void mutatetxtTest40() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-e", "world", inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(input, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 41
    // <single> (follows [if])
    // Case sensitivity option for k and e : Case insensitive -e
    @Test
    public void mutatetxtTest41() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is tHiS World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-i", "-e", "this", inputFile.toString() };
        Main.main(args);
        String expectedOut = "World is Mine!" + System.lineSeparator();

        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 42
    // <single> (follows [if])
    // Case sensitivity option for k and e : Case sensitive -k
    @Test
    public void mutatetxtTest42() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-k", "world", inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals("", capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 43
    // <single> (follows [if])
    // Case sensitivity option for k and e : Case insensitive -k
    @Test
    public void mutatetxtTest43() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-i", "-k", "world", inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(input, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 44
    // <single>
    // Presence of Unrecognized options: No Unrecognized options present
    @Test
    public void mutatetxtTest44() {
        String input = "World is Mine! mainop" + System.lineSeparator() +
                "Mine is this World! mainop" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-d", "1", "-f", "bold", "World", inputFile.toString() };
        Main.main(args);

        String expectedOut = "**World** is Mine! mainop" + System.lineSeparator() +
                "**World** is Mine! mainop" + System.lineSeparator() +
                "Mine is this **World**! mainop" + System.lineSeparator() +
                "Mine is this **World**! mainop" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 45
    // <single>
    // Presence of Unrecognized options: One Unrecognized option present
    @Test
    public void mutatetxtTest45() {
        String input = "World is Mine!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-m", inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertTrue(capture.stdout().isEmpty());
    }

    // Frame #: 46
    // <single>
    // Presence of Unrecognized options: Many Unrecognized options present
    @Test
    public void mutatetxtTest46() {
        String input = "World is Mine!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-m", "-i", "-u", inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertTrue(capture.stdout().isEmpty());
    }

    // Frame #: 47
    // <single> (follows [if])
    // Position of substring in line : Beginning
    @Test
    public void mutatetxtTest47() {
        String input = "World is Mine!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-f", "bold", "World", inputFile.toString() };
        Main.main(args);

        String expectedOut = "**World** is Mine!" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 48
    // <single> (follows [if])
    // Position of substring in line : Middle
    @Test
    public void mutatetxtTest48() {
        String input = "World is Mine!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-f", "bold", "is", inputFile.toString() };
        Main.main(args);

        String expectedOut = "World **is** Mine!" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 49
    // <single> (follows [if])
    // Position of substring in line : End
    @Test
    public void mutatetxtTest49() {
        String input = "World is Mine!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-f", "bold", "Mine!", inputFile.toString() };
        Main.main(args);

        String expectedOut = "World is **Mine!**" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 50
    // <error> (follows [if])
    // Test Case 50 (Key = 2.0.2.0.2.0.0.2.2.0.2.0.2.0.0.0.0.0.)
    // Size of file : Empty
    // Final Line Termination : <n/a>
    // Presence of d option : One
    // Parameter value of d option : <n/a>
    // Presence of f option : One
    // Parameter value of f option : <n/a>
    // Paramter value of f substring : <n/a>
    // Presence of i options : One
    // Presence of k options : One
    // Presence of k string : <n/a>
    // Presence of e options : One
    // Presence of e string : <n/a>
    // Presence of n option : One
    // Parameter value of n option : <n/a>
    // Option -e and -k presence : <n/a>
    // Case sensitivity option for k and e : <n/a>
    // Presence of Unrecognized options : <n/a>
    // Position of substring in line : <n/a>
    @Test
    public void mutatetxtTest50() {
        String input = "";
        Path inputFile = createFile(input);
        String[] args = { "-i", "-e", "World", "-k", "Mine", "-n", "3", "-d", "2", "-f", "bold", "text",
                inputFile.toString() };
        Main.main(args);
        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertTrue(capture.stdout().isEmpty());

    }

    // Frame #: 51
    // New line character only
    @Test
    public void mutatetextTest51() {
        String input = System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(input, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }
    // Frame #: 52
    // -f option present but substring parameter missing

    @Test
    public void mutatetextTest52() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-f", "code", inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertTrue(capture.stdout().isEmpty());
    }

    // Frame #: 53
    // -i option present but no k or e option present
    @Test
    public void mutatetextTest53() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-i", inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertTrue(capture.stdout().isEmpty());
    }

    // Frame #: 54
    // -f option with substring not in any line
    @Test
    public void mutatetextTest54() {
        String input = "World is Mine" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-f", "code", "Miku", inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(input, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 55
    // -f option with substring multiple times in each line
    @Test
    public void mutatetextTest55() {
        String input = "World is Mine! World" + System.lineSeparator() +
                "Mine is this World! World" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-f", "bold", "World", inputFile.toString() };
        Main.main(args);

        String expectedOut = "**World** is Mine! World" + System.lineSeparator() +
                "Mine is this **World**! World" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 56
    // test -f but with a flag included as the substring
    @Test
    public void mutatetextTest56() {
        String input = "Miku +d" + System.lineSeparator()
                + "+d/-d?" + System.lineSeparator()
                + "(Providing execution permissions will make you a wizard)" + System.lineSeparator();
        Path inputFile = createFile(input);

        String[] args = { "-f", "code", "-d", inputFile.toString() };
        Main.main(args);

        String expectedOut = "Miku +d" + System.lineSeparator()
                + "+d/`-d`?" + System.lineSeparator()
                + "(Providing execution permissions will make you a wizard)" + System.lineSeparator();
        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

    // Frame #: 57
    // -n option present but nothing afterwards
    @Test
    public void mutatetextTest57() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-n", inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertTrue(capture.stdout().isEmpty());
    }

    // Frame #: 58
    // -d option present but no substring present
    @Test
    public void mutatetextTest58() {
        String input = "World is Mine!" + System.lineSeparator() +
                "Mine is this World!" + System.lineSeparator();
        Path inputFile = createFile(input);
        String[] args = { "-d", inputFile.toString() };
        Main.main(args);

        Assertions.assertEquals(usageStr, capture.stderr());
        Assertions.assertTrue(capture.stdout().isEmpty());
    }

    // Frame #: 59
    // -k -d and -n optiions present
    @Test
    public void mutatetextTest59() {
        String input = "I’m busy later" + System.lineSeparator()
                + "My daughter is reading Charlotte's web" + System.lineSeparator()
                + "I never knew a spider could make me feel so much" + System.lineSeparator();
        Path inputFile = createFile(input);

        String[] args = { "-k", "I", "-d", "1", "-n", "5", inputFile.toString() };
        Main.main(args);

        String expectedOut = "00001 I’m busy later" + System.lineSeparator()
                + "00002 I’m busy later" + System.lineSeparator()
                + "00003 I never knew a spider could make me feel so much" + System.lineSeparator()
                + "00004 I never knew a spider could make me feel so much" + System.lineSeparator();

        Assertions.assertEquals(expectedOut, capture.stdout());
        Assertions.assertEquals("", capture.stderr());
    }

}