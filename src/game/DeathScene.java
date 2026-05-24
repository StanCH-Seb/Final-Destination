package game;

public class DeathScene extends Scene {

    public DeathScene(String id, String narrative) {
        super(id, narrative);
    }

    @Override
    public String getSceneType(){
         return "DEATH";
    }

    @Override
    public boolean isGameOver()  { return true; }   // ← different behaviour

    @Override
    public boolean isEnding()    { return false; }
}