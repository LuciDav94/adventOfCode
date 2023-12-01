import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class December1B {
    private static Map<String, Integer> map = Map.ofEntries(
            Map.entry("one", 1),
            Map.entry("two", 2),
            Map.entry("three", 3),
            Map.entry("four", 4),
            Map.entry("five", 5),
            Map.entry("six", 6),
            Map.entry("seven", 7),
            Map.entry("eight", 8),
            Map.entry("nine", 9)
    );

    public static void main(String[] args) {
        InputStream inputStream = December1B.class.getResourceAsStream("/input.txt");

        if (inputStream != null) {
            Scanner scanner = new Scanner(inputStream);
            try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
                long number = 0L;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    if (!line.isEmpty()) {
                        Future<Integer> firstDigitFuture = executorService.submit(() -> findFirstDigit(
                                replaceNumbers(line)
                        ));
                        Future<Integer> lastDigitFuture = executorService.submit(() -> findLastDigit(
                                replaceNumbers(line)
                        ));

                        try {
                            int firstDigit = firstDigitFuture.get();
                            int lastDigit = lastDigitFuture.get();


                            number += lastDigit + firstDigit * 10L;
                            System.out.println(lastDigit + firstDigit * 10L);
                        } catch (InterruptedException | ExecutionException e) {
                            System.out.println("Something went wrong!");
                        }
                    }
                }
                System.out.println("Result is: " + number);

                executorService.shutdown();
            }
        } else {
            System.out.println("File not found");
        }
    }

    private static int findFirstDigit(String line) {
        for (int i = 0; i < line.length(); i++) {
            char currentChar = line.charAt(i);
            if (Character.isDigit(currentChar)) {
                return Character.getNumericValue(currentChar);
            }
        }
        return 0;
    }

    private static String replaceNumbers(String line) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            line = line.replaceAll(key, key + value + key);
        }
        System.out.println(line);
        return line;
    }

    private static int findLastDigit(String line) {
        for (int i = line.length() - 1; i >= 0; i--) {
            char currentChar = line.charAt(i);
            if (Character.isDigit(currentChar)) {
                return Character.getNumericValue(currentChar);
            }
        }
        return 0;
    }
}
