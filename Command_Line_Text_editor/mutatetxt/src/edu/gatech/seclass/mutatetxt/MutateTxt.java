package edu.gatech.seclass.mutatetxt;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.*;

public class MutateTxt implements MutateTxtInterface {

    // here are the various different flagged options that we are going to be
    // keeping a track of
    String filepath;
    boolean caseInsensitive = false;
    String excludeSubstring = null;
    String keepSubstring = null;
    Style formatStyle = null;
    String formatSubstring = null;
    int duplicationFactor = 0;
    int NumPadding = -1;

    @Override
    public void reset() {
        filepath = null;
        caseInsensitive = false;
        excludeSubstring = null;
        keepSubstring = null;
        formatStyle = null;
        formatSubstring = null;
        duplicationFactor = 0;
        NumPadding = -1;
    }

    @Override
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public void setCaseInsensitive(boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
    }

    @Override
    public void setExcludeString(String excludeString) {
        this.excludeSubstring = excludeString;
    }

    @Override
    public void setKeepLines(String keepLines) {
        this.keepSubstring = keepLines;
    }

    @Override
    public void setFormatText(Style style, String strToFormat) {
        this.formatStyle = style;
        this.formatSubstring = strToFormat;
    }

    @Override
    public void setDuplicateFactor(Integer duplicateFactor) {
        this.duplicationFactor = duplicateFactor;
    }

    @Override
    public void setAddPaddedLineNumber(Integer padding) {
        this.NumPadding = padding;
    }

    public void mutatetxt() throws MutateTxtException {

        if (filepath == null) {
            throwUsage();
        }

        if (caseInsensitive && excludeSubstring == null && keepSubstring == null) {
            throwUsage();
        }
        if (excludeSubstring != null && keepSubstring != null) {
            throwUsage();
        }
        if (duplicationFactor != 0 & (duplicationFactor < 0 || duplicationFactor > 9)) {
            throwUsage();
        }
        if (NumPadding != -1 && (NumPadding < 1 || NumPadding > 9)) {
            throwUsage();
        }

        if (formatStyle != null && (formatSubstring == null || formatSubstring.isEmpty())) {
            throwUsage();
        }


        File file = new File(filepath);
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            throwUsage();
        }
        if (file.length() == 0) {
            throwUsage();
        }

        List<String> fileLines = readFile(file);
        if (fileLines == null || !EndsWithNewline(file)) {
            throwUsage();
        }

        if (keepSubstring != null) {
            Iterator<String> iterator = fileLines.iterator();
            while (iterator.hasNext()) {
                String line = iterator.next();
                if (!containSubstring(line, keepSubstring, caseInsensitive)) {
                    iterator.remove();
                }
            }
        } else if (excludeSubstring != null) {
            Iterator<String> iterator = fileLines.iterator();
            while (iterator.hasNext()) {
                String line = iterator.next();
                if (containSubstring(line, excludeSubstring, caseInsensitive)) {
                    iterator.remove();
                }
            }
        }

        // here is where we will be applying the formatting to the lines
        if (formatStyle != null) {
            String wrapper = "";
            if (formatStyle.equals(Style.bold)) {
                wrapper = "**";
            } else if (formatStyle.equals(Style.italic)) {
                wrapper = "*";
            } else if (formatStyle.equals(Style.code)){
                wrapper = "`";
            }
            for (int i = 0; i < fileLines.size(); i++) {
                String line = fileLines.get(i);

                if (containSubstring(line, formatSubstring, caseInsensitive)) {
                    String newLine = line.replaceFirst(Pattern.quote(formatSubstring),
                            Matcher.quoteReplacement(wrapper + formatSubstring + wrapper));
                    fileLines.set(i, newLine);
                }
            }

        }

        List<String> dupeList = new ArrayList<>();
        for (String line : fileLines) {
            for (int i = 0; i <= duplicationFactor; i++) {
                dupeList.add(line);
            }
        }

        // here we will be adding the line number padding to the lines that we have
        if (NumPadding > 0) {
            for (int i = 0; i < dupeList.size(); i++) {
                String line = dupeList.get(i);
                String num = String.format("%0" + NumPadding + "d", i + 1);
                String newLine = num + " " + line;
                dupeList.set(i, newLine);
            }
        }

        // Output shown
        for (String line : dupeList) {
            System.out.println(line);
        }


    }

    private void throwUsage() throws MutateTxtException {
        throw new MutateTxtException(
                "Usage: mutateTxt [ -k substring | -e substring ] [ -i ] " +
                        "[ -b substring | -t substring | -c substring ] " +
                        "[ -d duplicateFactor ] [ -p padding ] FILE"
        );
    }

    private boolean containSubstring(String str, String substring, boolean caseInsensitive) {
        if (caseInsensitive) {
            return str.toLowerCase().contains(substring.toLowerCase());
        } else {
            return str.contains(substring);
        }

    }

    private List<String> readFile(File file) {
        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            return null;

        }
    }

    private boolean EndsWithNewline(File file) {
        try {
            String content = Files.readString(file.toPath());
            return content.endsWith(System.lineSeparator());
        } catch (IOException e) {
            return false;
        }

    }
}
