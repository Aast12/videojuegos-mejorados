package com.mygdx.game;

import java.util.HashMap;

public class Globals {
    public int difficulty; //TODO: QUE VENGA DE LA PANTALLA DE SETTINGS
    public int musicVolume;
    public int fxVolume = 50;
    String diff;
    public void init(){
        switch (diff) {
            case "easy":  
                difficulty = 1;
                break;
            case "normal":  
                difficulty = 2;
                break;
            case "hard":  
                difficulty = 3;
                break;
            default:
                difficulty = 1;
        }
    }

    public static HashMap<String, String> itemTypes = new HashMap<String, String>() {
        {
            put("carrot", "carrot.png");
            put("magenta", "item.png");
        }
    };

    public static float map(float num, float in_min, float in_max, float out_min, float out_max) {
        return (num - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

}