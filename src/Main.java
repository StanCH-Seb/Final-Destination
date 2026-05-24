import java.util.Scanner;
import ui.text.GameEngine;

public class Main {

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);

        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("                     FINAL DESTINATION");
        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println();

        System.out.print("Enter your name, Player: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            name = "Player";
        }

        new GameEngine(name).run();

        scanner.close();
    }
}