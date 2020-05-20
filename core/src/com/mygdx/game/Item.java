package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author davidg
 */
public class Item extends Entity{
    private int pickable; // This will be used as item counter and render, if 0, dont render, if 1 and 2, render
    //states
    // 0: already picked
    // 1: is able to be picked
    // 2: only available to render

    public Item(int x, int y, int width, int height, int pickable)
    {
        super(x, y);
        this.img = new Texture("item.png");
        this.pickable = pickable;
        this.hitbox = new Rectangle();
        hitbox.width = width;
        hitbox.height = height;
        hitbox.x = x + 3;
        hitbox.y = y + 3;
    }

    public int getPickable() {return pickable;}

    public void setPickable(int p) {pickable = p;}

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(img, x, y);
    }

    public void pick() {
        // todo: make generic pick
    }
}