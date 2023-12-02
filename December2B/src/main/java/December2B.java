import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class December2B {

    private static final Pattern BLUE_PATTERN = Pattern.compile("(\\d+)\\s+blue");
    private static final Pattern RED_PATTERN = Pattern.compile("(\\d+)\\s+red");
    private static final Pattern GREEN_PATTERN = Pattern.compile("(\\d+)\\s+green");

    public static void main(String[] args) {
        InputStream inputStream = December2B.class.getResourceAsStream("/input.txt");
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
                int redMax = findMaxNumber(line, RED_PATTERN);
                int blueMax = findMaxNumber(line, BLUE_PATTERN);
                int greenMax = findMaxNumber(line, GREEN_PATTERN);
                gameCount += (long) redMax * blueMax * greenMax;

            }
            return gameCount;
        }
    }

    private static int findMaxNumber(String line, Pattern pattern) {
        Matcher matcher = pattern.matcher(line);
        int max = 0;
        while (matcher.find()) {
            int count = Integer.parseInt(matcher.group(1));
            max = Math.max(max, count);
        }
        return max;
    }
}
