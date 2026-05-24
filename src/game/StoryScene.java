package game;
public class StoryScene extends Scene {

    public StoryScene(String id, String narrative) {
        super(id, narrative);          // calls Scene constructor
    }

    @Override
    public String getSceneType(){
         return "STORY"; 
        }

    @Override
    public boolean isGameOver(){
         return false; 
    
    }

    @Override
    public boolean isEnding(){
         return false;
    }
}