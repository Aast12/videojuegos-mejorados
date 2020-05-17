/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

/**
 *
 * @author kaetahr
 */
public class RandomEnemy extends Enemy {
	double lastMove; //used for random movement
	boolean isMoving; //used for random movement
	int targetX;
	int targetY;
	
	/**
	 * Inicializa un enemigo con movimiento al azar
	 * @param x
	 * @param y
	 * @param map
	 */
	public RandomEnemy(int x, int y, MapHandler map) {
		super(x, y, map);

		lastMove = System.nanoTime();
		isMoving = false;
	}

	/**
	 * AI para mover enemigos al azar
	 * Se mueven cada dos segundos.
	 * Prefieren moverse en direcciones cardinales que las diagonales
	 */
	private void moveRandom()
	{
		if (!isMoving)
		{
			double now = System.nanoTime();
			if (now - lastMove > 2000000000) 
			{
				System.out.println("new random");
				boolean willMove;

				targetX = targetY = 0;
				willMove = Math.random() >.5;
				if (willMove)
					targetX = (int)(Math.random() * ((1600 - 10) + 1 ) + 10) - 500;
				targetX += x;
				willMove = Math.random() >.5;
				if (willMove)
					targetY = (int)(Math.random() * ((1000 - 10) + 1 ) + 10) - 500;
				targetY += y;
				isMoving = true;
			}
		}
		else
		{
			int tmpX, tmpY;
			if (targetX > x)
			{
				tmpX = speed;
			}
			else if (targetX == x)
			{
				tmpX = 0;
			}
			else
			{
				tmpX = 0 - speed;
			}

			if (targetY > y)
			{
				tmpY = speed;
			}
			else if (targetY == y)
			{
				tmpY = 0;
			}
			else
			{
				tmpY  =  0 - speed;
			}


			boolean collision = checkCollision(tmpX, tmpY);
			if (tmpX == 0 && tmpY == 0)
			{
				isMoving = false;
				lastMove = System.nanoTime();
			}
			if (collision)
			{
				isMoving = false;
			}
			else
			{
				this.x += tmpX;
				this.y += tmpY;
			}
		}
	}

	/**
	 * Se corre cada frame
	 */
	public void tick()
	{
		moveRandom();	
		hitbox.x = x;
		hitbox.y = y;
		covidBox.tick(x-32, y-32);
	}
	
}
