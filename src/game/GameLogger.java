package game;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GameLogger {
    private static final String LOG_FILE = "game_log.txt";

    public static void logSession(String playerName, int deathCount, String outcome) {
        long now = System.currentTimeMillis();
        String entry = "[" + now + "] Player: " + playerName + " | Deaths: " + deathCount + " | Outcome: " + outcome;
        
        // Exception Handling scenario: Safe defensive logging write operations
        try (PrintWriter out = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            out.println(entry);
        } catch (IOException e) {
            System.err.println("Warning: Could not write session logs to storage disk. " + e.getMessage());
        }
    }

    public static String readAllLogs() throws IOException {
        if (!Files.exists(Paths.get(LOG_FILE))) {
            return "No previous logs found. Run a session first!";
        }
        List<String> lines = Files.readAllLines(Paths.get(LOG_FILE));
        return String.join("\n", lines);
    }
}