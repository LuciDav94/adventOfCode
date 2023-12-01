import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.*;

public class December1A {

    public static void main(String[] args) {
        InputStream inputStream = December1A.class.getResourceAsStream("/input.txt");

        if (inputStream != null) {
            Scanner scanner = new Scanner(inputStream);
            try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
                long number = 0L;
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    if (!line.isEmpty()) {
                        Future<Integer> firstDigitFuture = executorService.submit(() -> findFirstDigit(line));
                        Future<Integer> lastDigitFuture = executorService.submit(() -> findLastDigit(line));

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
