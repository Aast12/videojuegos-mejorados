package com.mygdx.game;

import java.util.HashMap;

public class Globals {
    public int difficulty = 1; //TODO: QUE VENGA DE LA PANTALLA DE SETTINGS

    public static HashMap<String, String> itemTypes = new HashMap<String, String>() {
        {
            put("carrot", "carrot.png");
            put("magenta", "item.png");
        }
    };

}