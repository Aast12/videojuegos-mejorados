/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author kaetahr
 */
public class Enemy {
	public int x;
	public int y;
	Rectangle hitbox;
	Texture img;
	

	Enemy(int x, int y)
	{
		this.x = 400;
		this.y = 300;
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


}
