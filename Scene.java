import java.util.ArrayList;

public class Scene {
    private String id;
    private String narrative;
    private String hint;
    private ArrayList<Choice> choices;
    private boolean gameOver;
    private boolean ending;
    
    public Scene(String id, String narrative) {
        this.id = id;
        this.narrative = narrative;
        this.choices = new ArrayList<Choice>();
        this.gameOver = false;
        this.ending = false;
        this.hint = null;
    }
    
  
    public void addChoice(Choice choice) {
        choices.add(choice);
    }
    
    public void setHint(String hint) {this.hint = hint;}
    public void setGameOver(boolean value) {this.gameOver = value;}
    public void setEnding(boolean value) {this.ending = value;}
    public String getId() {return id;}
    public String getNarrative() {return narrative;}
    public String getHint() {return hint;}
    public ArrayList<Choice> getChoices() {return choices;}
    public boolean isGameOver() {return gameOver;}
    public boolean isEnding() {return ending;}
}