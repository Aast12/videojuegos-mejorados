/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author kaetahr
 */
public class Enemy extends Entity {
	//Disease is the damage dealing circlu
	//hitbox is just used for collision detection
	Rectangle disease; //TODO: implement disease into main logic


	public Enemy(int x, int y)
	{
		super(x, y);
		this.img = new Texture("man.png");
		this.hitbox = new Rectangle();
		hitbox.width = 115;
		hitbox.height = 115;
		hitbox.x = x + 40;
		hitbox.y = y + 33;
	}

	public void tick()
	{
		hitbox.x = x + 40;
		hitbox.y = y + 33;
	}

	@Override
	public void render(SpriteBatch batch) {
		batch.draw(img, x, y);
}


}
