import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class December4B {

    public static void main(String[] args) {
        InputStream inputStream = December4B.class.getResourceAsStream("/input.txt");
        if (inputStream != null) {
            long gameCount = processInput(inputStream);
            System.out.println("Result: " + gameCount);
        } else {
            System.out.println("File not found");
        }
    }

    private static long processInput(InputStream inputStream) {
        try (Scanner scanner = new Scanner(inputStream)) {
            Map<Integer, Integer> frequency = new HashMap<>();
            int lastCardNumber = 0;
            while (scanner.hasNextLine()) {
                String[] splitResult = scanner.nextLine().split(": ");
                String cardNumber = splitResult[0].split("\\s+")[1];
                lastCardNumber = Integer.parseInt(cardNumber);
                frequency.put(lastCardNumber, frequency.getOrDefault(lastCardNumber, 0) + 1);
                long counter = getNumberOfWinningCards(splitResult[1]);
                int i = 0;
                while (i < frequency.getOrDefault(lastCardNumber, 0)) {
                    i++;
                    for (int j = lastCardNumber + 1; j <= lastCardNumber + counter; j++) {
                        frequency.put(j, frequency.getOrDefault(j, 0) + 1);
                    }
                }
            }
            return calculateSum(frequency, lastCardNumber);
        }
    }


    private static int calculateSum(Map<Integer, Integer> frequencyMap, int lastNumber) {
        int sum = 0;
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getKey() <= lastNumber) {
                sum += entry.getValue();
            }
        }
        return sum;
    }

    private static long getNumberOfWinningCards(String line) {
        String[] numbers = line.split(" \\| ");
        String winning = numbers[0];
        String losing = numbers[1];
        String[] winningNumbers = winning.split("\\s+");
        String[] losingNumbers = losing.split("\\s+");

        List<String> losingList = Arrays.asList(losingNumbers);
        int counter = 0;
        for (String winningNumber : winningNumbers) {
            if (losingList.contains(winningNumber)) {
                counter++;
            }
        }
        return counter;
    }

}
