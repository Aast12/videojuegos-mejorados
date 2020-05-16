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
	SafeDistance covidBox;
	int speed;
	int direction; //1D movement
	MapHandler map;


	public Enemy(int x, int y, MapHandler map)
	{
		super(x, y);
		this.img = new Texture("man.png");
		this.hitbox = new Rectangle();
		hitbox.width = 64;
		hitbox.height = 64;
		hitbox.x = x;
		hitbox.y = y;
		covidBox = new SafeDistance(x-32, y-32);
		speed =1;
		direction = -1;
		this.map = map;

	}

	private void moveUpDown()
	{
		boolean collision;
		Rectangle test = new Rectangle(hitbox);
		test.y += speed * direction;
		collision = map.collidesOnLayer("Walls", test);
		if (collision)
		{
			direction *= -1;
			System.out.println("bump");
		}
		y += speed * direction;
	}

	private void moveLeftRight()
	{
		boolean collision;
		Rectangle test = new Rectangle(hitbox);
		test.x += speed * direction;
		collision = map.collidesOnLayer("Walls", test);
		if (collision)
		{
			direction *= -1;
			System.out.println("bump");
		}
		x += speed * direction;
	}

	public void tick()
	{
		moveLeftRight();
		hitbox.x = x;
		hitbox.y = y;
		covidBox.tick(x-32, y-32);
	}

	@Override
	public void render(SpriteBatch batch) {
		covidBox.render(batch);
		batch.draw(img, x, y);
}


}
