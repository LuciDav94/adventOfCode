import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class December3B {

    public static void main(String[] args) {
        InputStream inputStream = December3B.class.getResourceAsStream("/input.txt");
        if (inputStream != null) {
            List<String> allLines = readFile(inputStream);
            System.out.println(returnResult(allLines));
        } else {
            System.out.println("File not found");
        }
    }

    private static List<String> readFile(InputStream inputStream) {
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        }
        return lines;
    }

    public static boolean isValidIndex(int i, int j, int rows) {
        return 0 <= i && i < rows && 0 <= j && j < rows;
    }

    public static boolean isSymbol(Character c) {
        return !(Character.isDigit(c) || c == '.');
    }

    private static int returnResult(List<String> inputLines) {
        int rows = inputLines.size();
        List<List<List<Integer>>> map = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            map.add(new ArrayList<>(rows));
            for (int j = 0; j < rows; j++) {
                map.get(i).add(new ArrayList<>());
            }
        }

        int ans = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < rows; col++) {
                if (Character.isDigit(inputLines.get(row).charAt(col))) {
                    int left = col;
                    int number = 0;
                    while (col < rows && Character.isDigit(inputLines.get(row).charAt(col))) {
                        number = number * 10 + (inputLines.get(row).charAt(col++) - '0');
                    }
                    int right = col - 1;
                    for (int i = left - 1; i < right + 2; i++) {
                        for (int j = row - 1; j < row + 2; j++) {
                            if (isValidIndex(j, i, rows) && isSymbol(inputLines.get(j).charAt(i))) {
                                map.get(j).get(i).add(number);
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                if (inputLines.get(i).charAt(j) == '*' && map.get(i).get(j).size() == 2) {
                    ans += map.get(i).get(j).get(0) * map.get(i).get(j).get(1);
                }
            }
        }

        return ans;
    }


}
