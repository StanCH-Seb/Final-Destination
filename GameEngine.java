import java.util.Scanner;

public class GameEngine {

    private Scene[] scenes;
    private Scanner scanner = new Scanner(System.in);
    
    public GameEngine(String playerName){
        scenes = StoryBuilder.buildScenes(playerName);
    }

    public void run(){

        while (true){
            clearScreen();
            showMenu();

            String input = ask("Choice: ");
            input = input.toUpperCase();

            if(input.equals("A")){
                startGame();
            }else if(input.equals("B")){
                System.out.println("\nThanks for playing!");
                break;
            }else{
                System.out.println("Invalid choice.");
            }
        }
    }

    private void startGame(){

        String current = "PREAMBLE";

        while (true) {

            Scene scene = getScene(current);

            if (scene == null) {
                System.out.println("Scene not found.");
                return;
            }

            clearScreen();

            System.out.println("────────────────────────────────────────");
            printSlowly(scene.getNarrative());

            if (scene.isGameOver()) {
                gameOver();
                return;
            }

            if (scene.isEnding()) {
                ending();
                return;
            }

            System.out.println();

            Choice[] choices = scene.getChoices().toArray(new Choice[0]);

            for(int i = 0; i < choices.length; i++){
                System.out.println(
                    choices[i].getLabel() + ". " + choices[i].getText()
                );
            }

            if(scene.getHint() != null){
                System.out.println("H. Hint");
            }

            String nextScene = null;

            while(nextScene == null){

                String input = ask("\nChoice: ");
                input = input.toUpperCase();

                if (input.equals("H") && scene.getHint() != null) {
                    System.out.println("\nHint: " + scene.getHint());
                    continue;
                }

                for(int i = 0; i < choices.length; i++){

                    if(input.equals(choices[i].getLabel())){
                        nextScene = choices[i].getNextSceneId();
                    }
                }

                if(nextScene == null){
                    System.out.println("Invalid choice.");
                }
            }

            if(nextScene.equals("MENU")){
                return;
            }

            current = nextScene;
        }
    }

    private Scene getScene(String id){

        for(int i = 0; i < scenes.length; i++){
            if(scenes[i].getId().equals(id)){
                return scenes[i];
            }
        }

        return null;
    }

    private void showMenu(){

        System.out.println("════════════════════════════════════════");
        System.out.println("           FINAL DESTINATION");
        System.out.println("════════════════════════════════════════");
        System.out.println();

        System.out.println("A. Start");
        System.out.println("B. Quit");
        System.out.println("C.Options");
        System.out.println();
    }
    
    private void gameOver(){

        System.out.println("\nGAME OVER");
        System.out.println("Press ENTER to continue...");
        scanner.nextLine();
    }

    private void ending(){

        System.out.println("\nPress ENTER to continue...");
        scanner.nextLine();
    }

    private String ask(String text){

        System.out.print(text);
        return scanner.nextLine().trim();
    }

    private void clearScreen(){

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void printSlowly(String text){
        for(int i = 0; i < text.length(); i++){
            char c = text.charAt(i);
            System.out.print(c);

            try{
                Thread.sleep(20);
                }
                catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
        }

        System.out.println();
    }
}