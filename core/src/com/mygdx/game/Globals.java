package com.mygdx.game;

import java.util.HashMap;

public class Globals {
    public static String[] difficulties = {"easy", "normal", "hard"};
    public static int index = 1; // default: normal
    //public int difficulty = index + 1; //TODO: QUE VENGA DE LA PANTALLA DE SETTINGS --este rollo no se actualiza
    public static int musicVolume = 50;
    public static int fxVolume;
    public static int totalScore = 0;
    
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