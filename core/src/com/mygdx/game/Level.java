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
    private ArrayList<ArrayList<Item>> items = new ArrayList<ArrayList<Item>>();
    private ArrayList<ItemGroup> group = new ArrayList<ItemGroup>();

    /**
     * Constructor de nivel
     * @param seconds segundos para que se acabe el contrarreloj
     * @param map mapa que pertenece al nivel
     * @param items items que pertenecen el nivel
     */
    public Level (int seconds, MapHandler map, ArrayList<ArrayList<Item>> items) {
        this.win = false;
        this.lost = false;
        this.map = map;
        this.levelSeconds = seconds;
        // this.items.addAll(items);
        this.items= new ArrayList<ArrayList<Item>>(items);
        constructGroup(this.items);
    }

    public void constructGroup(ArrayList<ArrayList<Item>> list) {

        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).get(0).getPickable() == 1) {
                group.add(new ItemGroup(list.get(i).get(0).getImg(), 1, i));
                for (int j = 1; j < list.get(i).size(); j++) {
                    group.get(i).setCounter(j+1);
                }
            }
        }
    }

    public ArrayList<ItemGroup> getGroup() {return group;}

    /**
     * getter de los segundos
     * @return segundos
     */
    public int getLevelSeconds() {return levelSeconds;}

    /**
     * getter de si gano
     * @return booleano si gano
     */
    public boolean getWin() {return win;}

    /**
     * getter de si perdio
     * @return booleano de si perdio
     */
    public boolean getLost() {return lost;}

    /**
     * getter del mapa
     * @return regresa el mapa
     */
    public MapHandler getMap() {return map;}

    /**
     * getter la lista de objetos
     * @return la lista de objetos
     */
    public ArrayList<ArrayList<Item>> getItems() {return items;}

    /**
     * setter de win
     * @param win cambia si gano o no
     */
    public void setWin(boolean win) { this.win = win;}

    /**
     * setter de lose
     * @param lost cambia si perdio o no
     */
    public void setLost(boolean lost) { this.lost = lost;}

    /**
     * checa si el usuario ha perdido si se acabo el tiempo
     * @return booleano de si perdio o no
     */
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

    /**
     * hace el cheqeo de si perdio
     */
    public void tick() {
        lost = checkLost();
    }

    /**
     * renderiza los objetos
     * @param batch renderizador de dibujo
     */
    public void render(SpriteBatch batch) {
        for (int i = 0; i < items.size(); i++ ) {
            for (int j = 0; j < items.get(i).size(); j++) {
                System.out.println(items.size());
                System.out.println((items.get(i).size()));
                if (items.get(i).get(j).getPickable() != 0) {
                    items.get(i).get(j).render(batch);
                }
            }
        }
    }
}