public class HintChoice{ 

    public static String getHint(String sceneId){ 



        switch(sceneId){ 

            case "SCENE_1": 
                return "But the same path feels so eerie..."; 

            case "SCENE_2": 

                return "The dogs seem unusually aggressive..."; 

            case "SCENE_3": 

                return "Not every stranger can be trusted."; 

            case "SCENE_4": 

                return "Fear can cloud your judgment."; 

            case "SCENE_5": 

                return "Rushing home might get you into a disaster."; 

            case "SCENE_6": 

                return "The choice you make now will determine your fate."; 

            default: 

                return ""; 

        } 

    } 



    public static Choice[] getChoices(String sceneId){ 



        switch(sceneId){ 



            case "PREAMBLE": 

                return new Choice[]{ 

                    new Choice("A", "Continue...", "SCENE_1") 

                }; 



            case "SCENE_1": 

                return new Choice[]{ 

                    new Choice("A", "Go straight", "DEATH_AC_FALL"), 

                    new Choice("B", "Trust instinct", "SCENE_2") 

                }; 



            case "SCENE_2": 

                return new Choice[]{ 

                    new Choice("A", "Feed dogs", "DEATH_DOG_BITE"), 

                    new Choice("B", "Defend yourself", "SCENE_3") 

                }; 



            case "SCENE_3": 

                return new Choice[]{ 

                    new Choice("A", "Trust man", "SCENE_4"), 

                    new Choice("B", "Alley", "DEATH_ALLEY_KNIFE"), 

                    new Choice("C", "Wait", "DEATH_DOGS_RETURN"), 

                    new Choice("D", "Sing", "DEATH_SINGING") 

                }; 



            case "SCENE_4": 

                return new Choice[]{ 

                    new Choice("A", "Run", "DEATH_SLIP"), 

                    new Choice("B", "Shout", "DEATH_SHOUT"), 

                    new Choice("C", "Climb", "DEATH_GATE"), 

                    new Choice("D", "Confront", "SCENE_5") 

                }; 



            case "SCENE_5": 

                return new Choice[]{ 

                    new Choice("A", "Run home", "DEATH_SPEEDING_CAR"), 

                    new Choice("B", "Walk carefully", "SCENE_6") 

                }; 



            case "SCENE_6": 

                return new Choice[]{ 

                    new Choice("A", "Choose A", "MENU"), 

                    new Choice("B", "Choose B", "ENDING_EXPLOSION") 

                }; 



            default: 

                return new Choice[0]; 

        } 

    } 

} 