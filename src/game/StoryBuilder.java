<<<<<<< HEAD:StoryBuilder.java
public class StoryBuilder { 
        public static Scene[] buildScenes(String playerName) { 
        Scene[] scenes = new Scene[25]; 
        int i = 0; 
=======
package game;
public class StoryBuilder {
>>>>>>> f207274c292e5637415cae881ca2a2c34a9dda9d:src/game/StoryBuilder.java

        StoryScene preamble=new StoryScene("PREAMBLE", StoryLines.PREAMBLE(playerName)); 
        preamble.addChoice(new Choice("A", "Continue...", "SCENE_1")); 
        scenes[i++] =preamble; 


        StoryScene s1=new StoryScene("SCENE_1", StoryLines.SCENE_1(playerName)); 
        scenes[i++]=s1; 
        scenes[i++]=new DeathScene("DEATH_AC_FALL", StoryLines.DEATH_AC_FALL(playerName)); 


        StoryScene s2=new StoryScene("SCENE_2", StoryLines.SCENE_2(playerName)); 
        scenes[i++]=s2; 
        scenes[i++]=new DeathScene("DEATH_DOG_BITE", StoryLines.DEATH_DOG_BITE(playerName)); 


        StoryScene s3=new StoryScene("SCENE_3", StoryLines.SCENE_3(playerName)); 
        scenes[i++]=s3;
        scenes[i++]=new DeathScene("DEATH_ALLEY_KNIFE", StoryLines.DEATH_ALLEY_KNIFE(playerName)); 
        scenes[i++]=new DeathScene("DEATH_DOGS_RETURN", StoryLines.DEATH_DOGS_RETURN(playerName)); 
        scenes[i++]=new DeathScene("DEATH_SINGING", StoryLines.DEATH_SINGING(playerName)); 


        StoryScene s4=new StoryScene("SCENE_4", StoryLines.SCENE_4(playerName)); 
        scenes[i++]=s4;
        scenes[i++]=new DeathScene("DEATH_SLIP", StoryLines.DEATH_SLIP(playerName)); 
        scenes[i++]=new DeathScene("DEATH_SHOUT", StoryLines.DEATH_SHOUT(playerName)); 
        scenes[i++]=new DeathScene("DEATH_GATE", StoryLines.DEATH_GATE(playerName)); 

        StoryScene s5=new StoryScene("SCENE_5", StoryLines.SCENE_5(playerName)); 
        scenes[i++]=s5; 
        scenes[i++]=new DeathScene("DEATH_SPEEDING_CAR", StoryLines.DEATH_SPEEDING_CAR(playerName)); 

        StoryScene s6=new StoryScene("SCENE_6", StoryLines.SCENE_6(playerName)); 
        scenes[i++]=s6; 
        scenes[i++]=new EndingScene("ENDING_EXPLOSION", StoryLines.ENDING_EXPLOSION()); 

        return scenes; 

    } 

} 