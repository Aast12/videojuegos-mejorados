package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author davidg
 */
public class Item {
    public int x;
    public int y;
    Rectangle hitbox;
    Texture img;

    Item(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.img = new Texture("item.png");
        this.hitbox = new Rectangle();
        hitbox.width = 26;
        hitbox.height = 26;
        hitbox.x = x + 3;
        hitbox.y = y + 3;
    }
}
