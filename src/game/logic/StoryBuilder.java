package game.logic;

public class StoryBuilder {

    public static Scene[] buildScenes(String playerName) {

        Scene[] scenes = new Scene[25];
        int i = 0;

        StoryScene preamble = new StoryScene("PREAMBLE", StoryLines.PREAMBLE(playerName));
        preamble.addChoice(new Choice("A", "Continue...", "SCENE_1"));
        scenes[i++] = preamble;

        StoryScene s1 = new StoryScene("SCENE_1", StoryLines.SCENE_1(playerName));
        s1.setHint(HintChoice.getHint("SCENE_1"));
        s1.addChoice(new Choice("A", "Go straight",    "DEATH_AC_FALL"));
        s1.addChoice(new Choice("B", "Trust instinct", "SCENE_2"));
        scenes[i++] = s1;

        scenes[i++] = new DeathScene("DEATH_AC_FALL", StoryLines.DEATH_AC_FALL(playerName));

      
        StoryScene s2 = new StoryScene("SCENE_2", StoryLines.SCENE_2(playerName));
        s2.setHint(HintChoice.getHint("SCENE_2"));
        s2.addChoice(new Choice("A", "Feed dogs",       "DEATH_DOG_BITE"));
        s2.addChoice(new Choice("B", "Defend yourself", "SCENE_3"));
        scenes[i++] = s2;

        scenes[i++] = new DeathScene("DEATH_DOG_BITE", StoryLines.DEATH_DOG_BITE(playerName));

   
        StoryScene s3 = new StoryScene("SCENE_3", StoryLines.SCENE_3(playerName));
        s3.setHint(HintChoice.getHint("SCENE_3"));
        s3.addChoice(new Choice("A", "Trust man", "SCENE_4"));
        s3.addChoice(new Choice("B", "Alley",     "DEATH_ALLEY_KNIFE"));
        s3.addChoice(new Choice("C", "Wait",      "DEATH_DOGS_RETURN"));
        s3.addChoice(new Choice("D", "Sing",      "DEATH_SINGING"));
        scenes[i++] = s3;

        scenes[i++] = new DeathScene("DEATH_ALLEY_KNIFE", StoryLines.DEATH_ALLEY_KNIFE(playerName));
        scenes[i++] = new DeathScene("DEATH_DOGS_RETURN", StoryLines.DEATH_DOGS_RETURN(playerName));
        scenes[i++] = new DeathScene("DEATH_SINGING",     StoryLines.DEATH_SINGING(playerName));

        
        StoryScene s4 = new StoryScene("SCENE_4", StoryLines.SCENE_4(playerName));
        s4.setHint(HintChoice.getHint("SCENE_4"));
        s4.addChoice(new Choice("A", "Run",      "DEATH_SLIP"));
        s4.addChoice(new Choice("B", "Shout",    "DEATH_SHOUT"));
        s4.addChoice(new Choice("C", "Climb",    "DEATH_GATE"));
        s4.addChoice(new Choice("D", "Confront", "SCENE_5"));
        scenes[i++] = s4;

        scenes[i++] = new DeathScene("DEATH_SLIP",  StoryLines.DEATH_SLIP(playerName));
        scenes[i++] = new DeathScene("DEATH_SHOUT", StoryLines.DEATH_SHOUT(playerName));
        scenes[i++] = new DeathScene("DEATH_GATE",  StoryLines.DEATH_GATE(playerName));

        StoryScene s5 = new StoryScene("SCENE_5", StoryLines.SCENE_5(playerName));
        s5.setHint(HintChoice.getHint("SCENE_5"));
        s5.addChoice(new Choice("A", "Run home",       "DEATH_SPEEDING_CAR"));
        s5.addChoice(new Choice("B", "Walk carefully", "SCENE_6"));
        scenes[i++] = s5;

        scenes[i++] = new DeathScene("DEATH_SPEEDING_CAR", StoryLines.DEATH_SPEEDING_CAR(playerName));

       
        StoryScene s6 = new StoryScene("SCENE_6", StoryLines.SCENE_6(playerName));
        s6.setHint(HintChoice.getHint("SCENE_6"));
        s6.addChoice(new Choice("A", "Choose A", "MENU"));
        s6.addChoice(new Choice("B", "Choose B", "ENDING_EXPLOSION"));
        scenes[i++] = s6;

        scenes[i++] = new EndingScene("ENDING_EXPLOSION", StoryLines.ENDING_EXPLOSION());

        return scenes;
    }
}