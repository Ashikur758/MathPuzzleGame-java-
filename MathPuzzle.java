import java.io.*;
import java.util.*;

public class MathPuzzle {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = sc.nextLine().trim();

        System.out.print("Select difficulty level (1- Easy, 2- Medium, 3- Hard): ");
        int level = 1;
        String levelInput = sc.nextLine().trim();
        try {
            level = Integer.parseInt(levelInput);
            if (level < 1 || level > 3) level = 1;
        } catch (Exception e) {
            level = 1;
        }

        List<String> question = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("question.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replace("\r", "").trim(); // Remove Windows \r and spaces
                if (!line.isEmpty()) {
                    question.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading question file: " + e.getMessage());
            sc.close();
            return;
        }

        int score = 0;

        for (String q : question) {
            System.out.println(q);
            System.out.print("Ans:");  // No space after colon
            String answerStr = sc.nextLine().trim();

            if (answerStr.isEmpty()) {
                System.out.println("Invalid input. Skipping question.");
                continue;
            }

            try {
                int userAnswer = Integer.parseInt(answerStr);
                int correctAnswer = evaluateExpression(q);

                if (userAnswer == correctAnswer) {
                    System.out.println("Correct!");
                    score += 10 * level;
                } else {
                    System.out.println("Wrong! Correct answer is: " + correctAnswer);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Skipping question.");
            } catch (Exception e) {
                System.out.println("Error evaluating question: " + e.getMessage());
            }
        }

        System.out.println("Your score: " + score);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("scores.txt", true))) {
            bw.write(name + " | Difficulty: " + level + " | Score: " + score);
            bw.newLine();
            System.out.println("Score saved to scores.txt");
        } catch (IOException e) {
            System.out.println("Error saving score: " + e.getMessage());
        }

        sc.close();
    }

    private static int evaluateExpression(String expr) throws Exception {
        expr = expr.replace("=", "").replaceAll("\\s", "").replace("\r", "").trim();

        if (expr.contains("+")) {
            String[] parts = expr.split("\\+");
            return Integer.parseInt(parts[0]) + Integer.parseInt(parts[1]);
        } else if (expr.contains("-")) {
            String[] parts = expr.split("\\-");
            return Integer.parseInt(parts[0]) - Integer.parseInt(parts[1]);
        } else if (expr.contains("*")) {
            String[] parts = expr.split("\\*");
            return Integer.parseInt(parts[0]) * Integer.parseInt(parts[1]);
        } else if (expr.contains("/")) {
            String[] parts = expr.split("\\/");
            return Integer.parseInt(parts[0]) / Integer.parseInt(parts[1]);
        } else {
            throw new Exception("Unknown operation");
        }
    }
}
