/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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
	int covidOffset;
	BitmapFont font;
	ArrayList<String> dialogue;
	String diagLine;
	protected boolean willTalk;


	/**
	 * Inicializa un enemigo
	 * @param x Posicion inicial
	 * @param y Posicion inicial
	 * @param map Mapa donde esta el enemigo
	 */
	public Enemy(int x, int y, MapHandler map)
	{
		super(x, y);
		covidOffset = 32;
		this.img = new Texture("man.png");
		this.hitbox = new Rectangle();
		hitbox.width = 64;
		hitbox.height = 64;
		hitbox.x = x;
		hitbox.y = y;
		covidBox = new SafeDistance(x-covidOffset, y-covidOffset, 128, 128);
		speed = 1;
		direction = -1;
		this.map = map;
		font = new BitmapFont();
		font.setColor(Color.YELLOW);
		dialogue = new ArrayList<>();
		dialogue.add("hmm...");
		dialogue.add("No quiero regresar a casa...");
		dialogue.add("¡Ah, Se me olvidaba!");
		dialogue.add("He estado aquí todo el día y aún no termino");
		dialogue.add("Me duele la garganta");
		dialogue.add("Me duelen los pies");
		diagLine = "";
		willTalk = false;
		
	}

	/**
	 * Regresa area no segura
	 * @return 
	 */
	public Rectangle getCovidZone()
	{
		return covidBox.getHitbox();
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
		}
		x += speed * direction;
	}

	protected void talk()
	{
		int index = (int)(Math.random() * ((dialogue.size())));
		System.out.println(index);
		diagLine = dialogue.get(index);
	}


	/**
	 * Se corre cada frame. 
	 */
	public void tick()
	{
		moveLeftRight();
		hitbox.x = x;
		hitbox.y = y;
		covidBox.tick(x-covidOffset, y-covidOffset);
	}

	@Override
	public void render(SpriteBatch batch) {
		covidBox.render(batch);
		batch.draw(img, x, y);

		if (willTalk)
			font.draw(batch, diagLine , x,y+80 );
	}
}
