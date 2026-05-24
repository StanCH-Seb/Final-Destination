package game;

public class Choice {
    private final String label;
    private final String nextSceneId;
    private final String text;
    
    public Choice(String label, String text, String nextSceneId) {
        this.label = label;
        this.text = text;
        this.nextSceneId = nextSceneId;
    }
    
    public String getLabel() {return label;}
    public String getText() {return text;}
    public String getNextSceneId() {return nextSceneId;}
}