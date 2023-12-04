import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class December4A {

    public static void main(String[] args) {
        InputStream inputStream = December4A.class.getResourceAsStream("/input.txt");
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
                gameCount += parseLine(scanner.nextLine());
            }
            return gameCount;
        }
    }

    private static long parseLine(String line) {
        String[] part2 = line.split(": ");
        String[] numbers = part2[1].split(" \\| ");
        String winning = numbers[0];
        String losing = numbers[1];
        String[] winningNumbers = winning.split("\\s+");
        String[] losingNumbers = losing.split("\\s+");

        List<String> losingList = Arrays.asList(losingNumbers);

        int counter = 0;
        for (String winningNumber : winningNumbers) {
            if (losingList.contains(winningNumber)) {
                if (counter == 0) {
                    counter = 1;
                } else {
                    counter *= 2;
                }
            }
        }
        return counter;
    }

}
