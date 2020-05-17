package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;
import java.util.ArrayList;

public class Level {
    private boolean win, lost;
    private int levelSeconds;
    float timeSeconds = 0f;
    float period = 1f;
    private MapHandler map;
    private ArrayList<Item> items = new ArrayList<Item>();

    public Level (int seconds, MapHandler map, ArrayList<Item> items) {
        this.win = false;
        this.lost = false;
        this.map = map;
        this.levelSeconds = seconds;
        this.items.addAll(items);
        this.items= new ArrayList<Item>(items);
    }

    public int getLevelSeconds() {return levelSeconds;}

    public boolean getWin() {return win;}

    public boolean getLost() {return lost;}

    public MapHandler getMap() {return map;}

    public ArrayList<Item> getItems() {return items;}

    public void setWin(boolean win) { this.win = win;}

    public void setLost(boolean lost) { this.lost = lost;}

    public boolean checkLost() {
        /**
         * Se hace el contrarreloj y permite perder por tiempo
         */
        timeSeconds += Gdx.graphics.getRawDeltaTime();
        if(timeSeconds > period){
            timeSeconds-=period;
            levelSeconds--;
            if (levelSeconds <= 0) {
                return true;
            }
        }
        return false;
    }


    public void tick() {
        lost = checkLost();
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i <= items.size(); i++ ) {
            items.get(i).render(batch);
        }
    }
}