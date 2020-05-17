package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author davidg
 */
public class Item extends Entity{

    public Item(int x, int y, int width, int height)
    {
        super(x, y);
        this.img = new Texture("item.png");
        this.hitbox = new Rectangle();
        hitbox.width = width;
        hitbox.height = height;
        hitbox.x = x + 3;
        hitbox.y = y + 3;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(img, x, y);
    }

    public void pick() {
        // todo: make generic pick
    }
}