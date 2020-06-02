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
public class SafeDistance extends Entity {

	/**
	 * Inicializa un area de sana distancea para colocar alrededor de 
	 * enemigos
	 * @param x
	 * @param y 
	 */
	public SafeDistance(int x, int y, int w, int h) {
		super(x, y);
		hitbox = new Rectangle();
		hitbox.width = w;
		hitbox.height = h;
		if (w == 192)
			img = new Texture("big_covid_radius.png");
		else
			img = new Texture("covid_radius.png");
	}

	/**
	 * Corrige la posicion del circulo 
	 * @param x 
	 * @param y 
	 */
	public void tick(int x, int y)
	{
		this.x = x;
		this.y = y;

		hitbox.x = x;
		hitbox.y = y;
		
	}


	@Override
	public void render(SpriteBatch batch) {
		batch.draw(img, x, y);
	}
	
}
