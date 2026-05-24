package game;
public class EndingScene extends Scene {

    public EndingScene(String id, String narrative) {super(id, narrative);}

    @Override
    public String getSceneType() {return "ENDING";}

    @Override
    public boolean isGameOver()  { return false; }

    @Override
    public boolean isEnding()    { return true; }

    @Override
    public void displayScene() {
        // Implementation of abstract displayScene from GameObject
    }
}