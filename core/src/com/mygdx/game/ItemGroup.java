package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class ItemGroup {
    private Texture texture;
    private int counter;
    private int indexList; // guardara el indice original en la lista de objetos
    private String key;

    /**
     * constructor del array de itemgroup de los niveles
     * @param t la imagen del item
     * @param c cuantos de esos objetos hay
     * @param i indice en la lista original
     */
    public ItemGroup (Texture t, int c, int i) {
        this.texture = t;
        this.counter = c;
        this.indexList = i;
    }

    /**
     * constructor del array de itemgroup de los niveles
     * @param t la imagen del item
     * @param c cuantos de esos objetos hay
     * @param i indice en la lista original
     */
    public ItemGroup (Texture t, int c, String k) {
        this.texture = t;
        this.counter = c;
        this.key = k;
    }

    /**
     * getter del counter
     * @return la cantidad de objetos
     */
    public int getCounter() {return counter;}

    /**
     * getter de la textura
     * @return la imagen del item, puede estar o no coloreado por propositos de diseno interactivo
     */
    public Texture getTexture() {return texture;}

    /**
     * getter del indice
     * @return numero del indice del item en la lista del nivel
     */
    public int getIndexList() {return indexList;}

    /**
     * getter de la clave
     * @return clave del HashMap del nivel
     */
    public String getKey() {return key;}

    /**
     * setter del contador
     * @param c un entero con la cantidad de objetos no recogidos
     */
    public void setCounter(int c) {counter = c;}

    /**
     * setter de la textura
     * @param t una imagen en formato de textura
     */
    public void setTexture(Texture t) {texture = t;}

    /**
     * setter del numero de indice
     * @param i numero indicador del indice
     */
    public void setIndexList(int i) {indexList = i;}
}
