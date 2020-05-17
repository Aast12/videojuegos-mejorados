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


	/**
	 * Inicializa un enemigo
	 * @param x Posicion inicial
	 * @param y Posicion inicial
	 * @param map Mapa donde esta el enemigo
	 */
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

	/**
	 * Revisa si un enemigo va a chocar con una pared.
	 * @param x Diferencia en X (0) es no se mueve en X
	 * @param y Diferencia en Y (0)es no se mueve en Y
	 * @return 
	 */
	public boolean checkCollision(int x, int y)
	{
		Rectangle test = new Rectangle(hitbox);
		test.y += y; 
		test.x += x;
		return  map.collidesOnLayer("Walls", test);
	}
	

	/**
	 * Define moviemiento de en Y hasta chochar con una pared
	 */
	private void moveUpDown()
	{
		boolean collision = checkCollision(0, speed *direction);
		if (collision)
		{
			direction *= -1;
			System.out.println("bump");
		}
		y += speed * direction;
	}

	/**
	 * Define movimiento en X hasta chocar con una pared
	 */
	private void moveLeftRight()
	{
		boolean collision = checkCollision(speed*direction, 0);
		if (collision)
		{
			direction *= -1;
			System.out.println("bump");
		}
		x += speed * direction;
	}


	/**
	 * Se corre cada frame. 
	 */
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
