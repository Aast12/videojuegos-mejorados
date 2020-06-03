package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {
    public int x;
    public int y;
    Rectangle hitbox;
    Texture img;
    
    /**
     * Inicializa una entidad
     * @param x posicion inicial
     * @param y  posicion inicial
     */
    Entity(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Getter de pos X
     * @return
     */
    public int getX() {
        return x;
    }
    
    /**
     * Getter de posicion Y
     * @return
     */
    public int getY() {
        return y;
    }
    
    /**
     * Getter de imagen
     * @return
     */
    public Texture getImg() {
        return img;
    }
    
    /**
     * getter de rectangulo de hitbox
     * @return
     */
    public Rectangle getHitbox() {
        return hitbox;
    }
    
    /**
     * Setter de X
     * @param posX
     */
    public void setX(int posX) {
        this.x = posX;
    }
    
    /**
     * Setter de Y
     * @param posY
     */
    public void setY(int posY) {
        this.y = posY;
    }
    
    /**
     * Setter de hitbox
     * @param hitbox
     */
    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }
    
    /**
     * Setter de imagen
     * @param img
     */
    public void setImg(Texture img) {
        this.img = img;
    }
    
    /**
     * Dibuja la imagen a un batch
     * @param batch
     */
    public abstract void render(SpriteBatch batch);
    
}
