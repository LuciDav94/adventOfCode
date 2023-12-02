import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class December2A {

    private static final int BLUE_LIMIT = 14;
    private static final int GREEN_LIMIT = 13;
    private static final int RED_LIMIT = 12;

    public static void main(String[] args) {
        InputStream inputStream = December2A.class.getResourceAsStream("/input.txt");

        if (inputStream != null) {
            long gameCount = processInput(inputStream);
            System.out.println("Result: " + gameCount);
        } else {
            System.out.println("File not found");
        }
    }

    private static long processInput(InputStream inputStream) {
        try (Scanner scanner = new Scanner(inputStream)) {
            long gameCount = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int game = extractGameNumber(line);

                if (isValidLine(line)) {
                    gameCount += game;
                }
            }
            return gameCount;
        }
    }

    private static int extractGameNumber(String line) {
        return Integer.parseInt(line.split(":")[0].split(" ")[1]);
    }

    private static boolean isValidLine(String line) {
        return isPatternValid(line, Pattern.compile("(\\d+)\\s+blue"), BLUE_LIMIT)
                && isPatternValid(line, Pattern.compile("(\\d+)\\s+red"), RED_LIMIT)
                && isPatternValid(line, Pattern.compile("(\\d+)\\s+green"), GREEN_LIMIT);
    }

    private static boolean isPatternValid(String line, Pattern pattern, int limit) {
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            int count = Integer.parseInt(matcher.group(1));
            if (count > limit) {
                return false;
            }
        }
        return true;
    }
}
