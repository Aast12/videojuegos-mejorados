package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class ItemGroup {
    private Texture texture;
    private int counter;
    private int indexList; // guardara el indice original en la lista de objetos

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
