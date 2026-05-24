package game;

public class Choice {
    private String label;
    private String text;
    private String nextSceneId;
    
    public Choice(String label, String text, String nextSceneId) {
        this.label = label;
        this.text = text;
        this.nextSceneId = nextSceneId;
    }
    
    public String getLabel() {return label;}
    public String getText() {return text;}
    public String getNextSceneId() {return nextSceneId;}
}