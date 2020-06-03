package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;


public class DataManager {
    
    private Preferences prefs = Gdx.app.getPreferences("savefile");
    
    private int totalScore;
    private int currLevel;
    
    public DataManager(){
        loadValues();
    }
    
    public void loadValues(){
        totalScore = prefs.getInteger("totalScore");
        currLevel = prefs.getInteger("currLevel");
    }
    
    public void updateValues(){
        prefs.putInteger("totalScore", totalScore);
        prefs.putInteger("currLevel", currLevel);
        prefs.flush();
    }
    
    public void setValues(int score, int level){
        totalScore = score;
        currLevel = level;
        updateValues();
        loadValues();
    }
    
    public int getTotalScore(){
        return totalScore;
    }
    
    public int getCurrLevel () {
        return currLevel;
    }
}
