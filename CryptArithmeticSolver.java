import java.util.*;

public class CryptArithmeticSolver {

    static String word1, word2, result;
    static Set<Character> uniqueChars = new LinkedHashSet<>();
    static Map<Character, Integer> charToDigit = new HashMap<>();
    static boolean[] usedDigits = new boolean[10];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input words
        System.out.println("Enter first word:");
        word1 = sc.next().toUpperCase();

        System.out.println("Enter second word:");
        word2 = sc.next().toUpperCase();

        System.out.println("Enter result word:");
        result = sc.next().toUpperCase();

        // Collect unique characters
        for (char c : (word1 + word2 + result).toCharArray()) {
            uniqueChars.add(c);
        }

        if (uniqueChars.size() > 10) {
            System.out.println("Too many unique letters (>10), cannot solve.");
            return;
        }

        // Try to solve
        if (!solve(new ArrayList<>(uniqueChars), 0)) {
            System.out.println("No solution found.");
        }
    }

    // Recursive backtracking solver
    public static boolean solve(List<Character> chars, int idx) {
        if (idx == chars.size()) {
            if (isValidSolution()) {
                printMapping();
                return true;
            }
            return false;
        }

        for (int digit = 0; digit <= 9; digit++) {
            if (!usedDigits[digit]) {
                char currentChar = chars.get(idx);
                charToDigit.put(currentChar, digit);
                usedDigits[digit] = true;

                if (solve(chars, idx + 1)) {
                    return true;
                }

                // Backtrack
                usedDigits[digit] = false;
                charToDigit.remove(currentChar);
            }
        }
        return false;
    }

    // Validate the current mapping
    public static boolean isValidSolution() {
        // Leading zeros not allowed
        if (charToDigit.get(word1.charAt(0)) == 0 || 
            charToDigit.get(word2.charAt(0)) == 0 || 
            charToDigit.get(result.charAt(0)) == 0) {
            return false;
        }

        int num1 = getValue(word1);
        int num2 = getValue(word2);
        int res = getValue(result);

        return num1 + num2 == res;
    }

    public static int getValue(String word) {
        int value = 0;
        for (char c : word.toCharArray()) {
            value = value * 10 + charToDigit.get(c);
        }
        return value;
    }

    public static void printMapping() {
        System.out.println("\nSolution Found!");
        for (Map.Entry<Character, Integer> entry : charToDigit.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }

        System.out.printf("\n%s + %s = %s\n", 
            getValue(word1), getValue(word2), getValue(result));
    }
}

