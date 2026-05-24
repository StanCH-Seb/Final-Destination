import java.util.Scanner;

public class GameEngine {

    private Scene[] scenes;
    private Scanner scanner = new Scanner(System.in);
    private String  playerName;   
    private int     deathCount;

    public GameEngine(String playerName) {
        this.playerName = playerName;
        this.scenes     = StoryBuilder.buildScenes(playerName);
    }

    public void run() {
        while (true) {
            clearScreen();
            showMenu();

            String input = ask("Choice: ").toUpperCase();

            if (input.equals("A")) {
                startGame();
            } else if (input.equals("B")) {
                System.out.println("\nThanks for playing!");
                break;
            } else if (input.equals("C")) {
                
                GameLogger.displayLog();
                ask("Press ENTER to go back...");
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private void startGame() {
        String current = "PREAMBLE";
        deathCount = 0;

        while (true) {

         
            Scene scene;
            try {
                scene = getScene(current);
            } catch (IllegalArgumentException e) {
                System.out.println("Internal error: " + e.getMessage());
                return;
            }

            if (scene == null) {
                System.out.println("Scene not found: " + current);
                return;
            }

            clearScreen();
            System.out.println("────────────────────────────────────────");
            
            System.out.println("[" + scene.getSceneType() + "]");
            printSlowly(scene.getNarrative());       
            if (scene.isGameOver()) {
                deathCount++;
                gameOver();
                return;
            }

            if (scene.isEnding()) {
               
                GameLogger.logSession(playerName, deathCount, "SURVIVED");
                ending();
                return;
            }

            System.out.println();
            Choice[] choices = scene.getChoices().toArray(new Choice[0]);

            for (Choice choice : choices) {
                System.out.println(choice.getLabel() + ". " + choice.getText());
            }

            if (scene.getHint() != null) {
                System.out.println("H. Hint");
            }

            String nextScene = null;
            while (nextScene == null) {

               
                try {
                    String input = ask("\nChoice: ").toUpperCase();

                    if (input.equals("H") && scene.getHint() != null) {
                        
                        printSlowly("\nHint: " + scene.getHint(), 10);
                        continue;
                    }

                    for (Choice choice : choices) {
                        if (input.equals(choice.getLabel())) {
                            nextScene = choice.getNextSceneId();
                        }
                    }

                    if (nextScene == null) {
                        System.out.println("Invalid choice. Try again.");
                    }

                } catch (Exception e) {
                    System.out.println("Input error: " + e.getMessage());
                }
            }

            if (nextScene.equals("MENU")) {
                return;
            }

            current = nextScene;
        }
    }

   
    private Scene getScene(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Scene ID cannot be null or empty.");
        }
        for (Scene scene : scenes) {
            if (scene.getId().equals(id)) {
                return scene;
            }
        }
        return null;
    }

    private void showMenu() {
        System.out.println("════════════════════════════════════════");
        System.out.println("           FINAL DESTINATION");
        System.out.println("════════════════════════════════════════");
        System.out.println();
        System.out.println("A. Start");
        System.out.println("B. Quit");
        System.out.println("C. View Past Sessions");
        System.out.println();
    }

    private void gameOver() {
        System.out.println("\nGAME OVER");
       
        GameLogger.logSession(playerName, deathCount, "DIED");
        ask("Press ENTER to continue...");
    }

    private void ending() {
        ask("\nPress ENTER to continue...");
    }

    private String ask(String prompt) {
      
        try {
            System.out.print(prompt);
            return scanner.nextLine().trim();
        } catch (Exception e) {
            System.out.println("Input error: " + e.getMessage());
            return "";
        }
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

   

   
    private void printSlowly(String text) {
        printSlowly(text, 20);
    }

    
    private void printSlowly(String text, int delayMs) {
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