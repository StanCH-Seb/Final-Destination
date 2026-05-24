public class GameLogger {

    private static final String LOG_FILE = "game_log.txt";

    public static void logSession(String playerName, int deathCount, String outcome) {

        // No LocalDateTime / DateTimeFormatter needed
        long now = System.currentTimeMillis();
        String entry = "[" + now + "]"
                     + "  Player: " + playerName
                     + "  |  Deaths: " + deathCount
                     + "  |  Outcome: " + outcome
                     + System.lineSeparator();

        try {
            Files.write(
                Paths.get(LOG_FILE),
                entry.getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
        } catch (Exception e) {
            System.out.println("Warning: could not save session — " + e.getMessage());
        }
    }

    public static void displayLog() {
        System.out.println("\n════ PAST SESSIONS ════");

        try {
            List<String> lines = Files.readAllLines(Paths.get(LOG_FILE));
            if (lines.isEmpty()) {
                System.out.println("No sessions recorded yet.");
            } else {
                for (String line : lines) {
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            System.out.println("No session log found. Play a round first!");
        }

        System.out.println("═══════════════════════\n");
    }
}