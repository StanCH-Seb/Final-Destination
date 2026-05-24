import java.util.ArrayList;

public abstract class Scene{

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
}