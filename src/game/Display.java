import java.util.Scanner;

public class Display {

    private static Scanner scanner = new Scanner(System.in);

    public static void showMenu(){
        System.out.println("════════════════════════════════════════");
        System.out.println("           FINAL DESTINATION");
        System.out.println("════════════════════════════════════════");
        System.out.println();
        System.out.println("A. Start");
        System.out.println("B. Quit");
        System.out.println("C. View Past Sessions");
        System.out.println();
    }

    public static void showGameOver(){
        System.out.println("\nGAME OVER");
    }

    public static void clearScreen(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static String ask(String prompt){
        try {
            System.out.print(prompt);
            return scanner.nextLine().trim();
        } catch (Exception e) {
            System.out.println("Input error: " + e.getMessage());
            return "";
        }
    }

    public static void printSlowly(String text){
        printSlowly(text, 2);
    }

    public static void printSlowly(String text, int delayMs){
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println();
    }
}