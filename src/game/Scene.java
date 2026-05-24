package game;

import java.util.ArrayList;

<<<<<<< HEAD:Scene.java
public abstract class Scene{
=======
// ABSTRACTION: Scene is abstract — callers work with Scene without knowing
// whether it's a story beat, a death, or an ending.
// INHERITANCE: StoryScene, DeathScene, EndingScene all extend this class.
public abstract class Scene extends GameObject {
>>>>>>> f207274c292e5637415cae881ca2a2c34a9dda9d:src/game/Scene.java

    private String id;
    private String narrative;
    private String hint;
    private ArrayList<Choice> choices;

    public Scene(String id, String narrative){
        this.id = id;
        this.narrative = narrative;
        this.choices = new ArrayList<>();
        this.hint = null;
    }

   
    public abstract String  getSceneType();
    public abstract boolean isGameOver();
    public abstract boolean isEnding();

   
<<<<<<< HEAD:Scene.java
    public void addChoice(Choice choice){
        choices.add(choice); 
    }
    public void setHint(String hint){ 
        this.hint = hint; 
    }

    public String getId(){
         return id; 
        }
    public String getNarrative(){ 
        return narrative; 
    }
    public String getHint(){ 
        return hint; 
    }
    public ArrayList<Choice> getChoices(){ 
        return choices; 
    }
=======
    public void addChoice(Choice choice){choices.add(choice); }
    public void setHint(String hint){ this.hint = hint; }

    public String getId(){return id;}
    public String getNarrative(){ return narrative;}
    public String getHint(){ return hint;}
    public ArrayList<Choice> getChoices(){ return choices;}
>>>>>>> f207274c292e5637415cae881ca2a2c34a9dda9d:src/game/Scene.java
}