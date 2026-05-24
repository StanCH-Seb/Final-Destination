import java.io.FileWriter;
import java.io.File;
import java.util.Scanner;

public class GameLogger {

    private static final String LOG_FILE = "game_log.txt";

    public static void logSession(String playerName, int deathCount, String outcome) {
        String entry = "[" + System.currentTimeMillis() + "]"
                     + "  Player: " + playerName
                     + "  |  Deaths: " + deathCount
                     + "  |  Outcome: " + outcome;

        try {
            FileWriter writer = new FileWriter(LOG_FILE, true);
            writer.write(entry + "\n");
            writer.close();
        } catch (Exception e) {
            System.out.println("Warning: could not save session — " + e.getMessage());
        }
    }

    public static void displayLog() {
        System.out.println("\n════ PAST SESSIONS ════");

        try {
            File file = new File(LOG_FILE);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                System.out.println(reader.nextLine());
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("No session log found. Play a round first!");
        }

        System.out.println("═══════════════════════\n");
    }
}