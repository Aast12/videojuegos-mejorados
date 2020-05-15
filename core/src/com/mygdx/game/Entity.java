package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {
    public int x;
    public int y;
    Rectangle hitbox;
    Texture img;

    Entity(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Texture getImg() {
        return img;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setX(int posX) {
        this.x = posX;
    }

    public void setY(int posY) {
        this.y = posY;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

    public void setImg(Texture img) {
        this.img = img;
    }

    public abstract void render(SpriteBatch batch);

}
