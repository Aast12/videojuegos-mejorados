package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class ItemGroup {
    private Texture texture;
    private int counter;
    private int indexList;

    public ItemGroup (Texture t, int c, int i) {
        this.texture = t;
        this.counter = c;
        this.indexList = i;
    }

    public int getCounter() {return counter;}

    public Texture getTexture() {return texture;}

    public int getIndexList() {return indexList;}

    public void setCounter(int c) {counter = c;}

    public void setTexture(Texture t) {texture = t;}

    public void setIndexList(int i) {indexList = i;}
}
