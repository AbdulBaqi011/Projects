package edu.gatech.seclass.mutatetxt;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class Main {
    // Empty Main class for compiling Individual Project
    // During Deliverable 1 and Deliverable 2, DO NOT ALTER THIS CLASS or implement
    //
    // it

    public static void main(String[] args) {
        // Empty Skeleton Method
        if (args.length == 0) {
            usage();
            return;
        }

        // here are the various different flagged options that we are going to be
        // keeping a track of
        boolean caseInsensitive = false;
        String excludeSubstring = null;
        String keepSubstring = null;
        String formatStyle = null;
        String formatSubstring = null;
        int duplicationFactor = 0;
        int NumPadding = -1;

        List<String> argsList = new ArrayList<>(Arrays.asList(args));
        String fileName = argsList.remove(argsList.size() - 1); // Last argument is the file name

        for (int i = 0; i < argsList.size(); i++) {
            String option = argsList.get(i);
            if (option.equals("-i")) {

                caseInsensitive = true;
            }
            // This is an error because we are not allowed to have keep substring and there
            // needs to be another
            // argument after e flag because that will be the substring that we will be
            // using to see what to exculde
            else if (option.equals("-e")) {
                // exclude substring
                if (keepSubstring != null || i + 1 >= argsList.size()) {
                    usage();
                    return;
                }
                excludeSubstring = argsList.get(++i);
            } else if (option.equals("-k")) {
                // keep substring
                if (excludeSubstring != null || i + 1 >= argsList.size()) {
                    usage();
                    return;
                }
                keepSubstring = argsList.get(++i);
            }

            else if (option.equals("-f")) {
                if (i + 2 >= argsList.size()) {
                    usage();
                    return;
                }
                formatStyle = argsList.get(++i);
                formatSubstring = argsList.get(++i);
                if (!formatStyle.equals("bold") && !formatStyle.equals("italic") && !formatStyle.equals("code")) {
                    usage();
                    return;
                }

                if (formatSubstring.isEmpty()) {
                    usage();
                    return;
                }

            } else if (option.equals("-d")) {
                // duplication factor
                if (i + 1 >= argsList.size()) {
                    usage();
                    return;
                }
                try {
                    int tmpduplicateNum = Integer.parseInt(argsList.get(++i));
                    if (tmpduplicateNum > 9 || tmpduplicateNum < 0) {
                        usage();
                        return;
                    }
                    duplicationFactor = tmpduplicateNum;
                } catch (NumberFormatException e) {
                    usage();
                    return;
                }

            } else if (option.equals("-n")) {
                // line number padding
                if (i + 1 >= argsList.size()) {
                    usage();
                    return;
                }
                try {
                    int tmpnumpad = Integer.parseInt(argsList.get(++i));
                    if (tmpnumpad > 9 || tmpnumpad < 1) {
                        usage();
                        return;
                    }
                    NumPadding = tmpnumpad;
                } catch (NumberFormatException e) {
                    usage();
                    return;
                }
            } else {
                usage();
                return;
            }
        }

        // validation check for -i without -e/-k:
        if (caseInsensitive && excludeSubstring == null && keepSubstring == null) {
            usage();
            return;
        }

        // here we check if the file exists or not and if it is a file or not
        File file = new File(fileName);
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            usage();
            return;
        }

        if (file.length() == 0) {
            usage();
            return;
        }

        // here we check if the file ends the line with a new line or not
        List<String> fileLines = readFile(file);

        if (fileLines == null || !EndsWithNewline(file)) {
            usage();
            return;
        }

        // how this works is that we will use the method we created contain substring.
        // this method checks to see
        // based on case insensitive or not if the string contains the substring or not.
        // if it does and keep substring is not null it will only keep those lines that
        // contain that substring
        // if it does and exclude substring is not null it will remove those lines that
        // contain that substring

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
            String wrapper;
            if (formatStyle.equals("bold")) {
                wrapper = "**";
            } else if (formatStyle.equals("italic")) {
                wrapper = "*";
            } else {
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

        // here we will be duplicating the lines based on the duplication factor that
        // the user has given us
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
        // Add validation for -i without -e/-k:

        // Output shown
        for (String line : dupeList) {
            System.out.println(line);
        }

    }

    private static boolean EndsWithNewline(File file) {
        try {
            String content = java.nio.file.Files.readString(file.toPath());
            return content.endsWith(System.lineSeparator());
        } catch (IOException e) {
            return false;
        }

    }

    // this method will check if the string contains the substring or not and will
    // return a boolean accordingly
    private static boolean containSubstring(String str, String substring, boolean caseInsensitive) {
        if (caseInsensitive) {
            return str.toLowerCase().contains(substring.toLowerCase());
        } else {
            return str.contains(substring);
        }
    }

    private static List<String> readFile(File file) {
        try {
            return java.nio.file.Files.readAllLines(file.toPath());
        } catch (IOException e) {
            return null;

        }
    }

    private static void usage() {
        System.err.println(
                "Usage: mutatetxt [ -i | -e substring | -k substring | -f style substring | -d num | -n padding ] FILE");
    }

}
