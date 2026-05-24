package game.logic;


import game.ui.Display;

public class GameEngine{

    private Scene[] scenes;
    private String playerName;
    private int deathCount;

    public GameEngine(String playerName){
        this.playerName = playerName;
        this.scenes     = StoryBuilder.buildScenes(playerName);
    }

    public void run() {
        while (true) {
            Display.clearScreen();
            Display.showMenu();

            String input = Display.ask("Choice: ").toUpperCase();

            if (input.equals("A")) {
                startGame();
            } else if (input.equals("B")) {
                System.out.println("\nThanks for playing!");
                break;
            } else if (input.equals("C")) {
                GameLogger.displayLog();

                Display.ask("Press ENTER to go back...");
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

            Display.clearScreen();
            System.out.println("────────────────────────────────────────");
            System.out.println("[" + scene.getSceneType() + "]");
            Display.printSlowly(scene.getNarrative());

            if (scene.isGameOver()) {
                gameOver();
                return;
            }

            if (scene.isEnding()) {
                GameLogger.logSession(playerName, deathCount, "SURVIVED");
                Display.ask("\nPress ENTER to continue...");
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
                    String input = Display.ask("\nChoice: ").toUpperCase();

                    if (input.equals("H") && scene.getHint() != null) {
                        Display.printSlowly("\nHint: " + scene.getHint(), 10);
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

    private void gameOver() {
        deathCount++;
        Display.showGameOver();
        GameLogger.logSession(playerName, deathCount, "DIED");
        Display.ask("Press ENTER to continue...");
    }
}