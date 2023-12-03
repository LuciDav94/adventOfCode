import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class December3A {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");


    public static void main(String[] args) {
        InputStream inputStream = December3A.class.getResourceAsStream("/input.txt");
        if (inputStream != null) {
            long gameCount = processInput(inputStream);
            System.out.println("Result: " + gameCount);
        } else {
            System.out.println("File not found");
        }
    }

    private static long processInput(InputStream inputStream) {
        try (Scanner scanner = new Scanner(inputStream)) {
            String previousLine = null;
            String currentLine = scanner.nextLine();
            String nextLine = scanner.nextLine();
            long gameCount = getPartNumber(currentLine, previousLine, nextLine);
            while (scanner.hasNextLine()) {
                previousLine = currentLine;
                currentLine = nextLine;
                nextLine = scanner.nextLine();
                gameCount += getPartNumber(currentLine, previousLine, nextLine);
            }
            previousLine = currentLine;
            currentLine = nextLine;
            nextLine = null;
            gameCount += getPartNumber(currentLine, previousLine, nextLine);
            return gameCount;
        }
    }

    private static long getPartNumber(String currentLine, String previousLine, String nextLine) {
        long count = 0L;
        Matcher matcher = NUMBER_PATTERN.matcher(currentLine);
        while (matcher.find()) {
            String number = matcher.group();
            int startIndex = matcher.start();
            int endIndex = startIndex + (number.length() - 1);

            if (checkIsPartNumber(startIndex, currentLine, endIndex, previousLine, nextLine)) {
                count += Long.parseLong(number);
            }
        }
        return count;
    }

    private static boolean checkIsPartNumber(int numberIndex, String line,
                                             int maxNumberIndex,
                                             String previousLine,
                                             String nextLine) {
        if (numberIndex > 0) {
            char leftChar = line.charAt(numberIndex - 1);
            if (leftChar != '.') return true;
        }
        if (maxNumberIndex < line.length() - 1) {
            char rightChar = line.charAt(maxNumberIndex + 1);
            if (rightChar != '.') return true;
        }
        for (int i = numberIndex - 1; i <= maxNumberIndex + 1; i++) {
            if (i >= 0) {
                if (previousLine != null) {
                    if (i <= previousLine.length() - 1) {
                        char previousLineChar = previousLine.charAt(i);
                        if (!Character.isDigit(previousLineChar) && previousLineChar != '.') return true;
                    }
                }
                if (nextLine != null) {
                    if (i <= nextLine.length() - 1) {
                        char nextLineChar = nextLine.charAt(i);
                        if (!Character.isDigit(nextLineChar) && nextLineChar != '.') return true;
                    }
                }
            }
        }
        return false;
    }

}
