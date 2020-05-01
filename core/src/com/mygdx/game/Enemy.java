/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author kaetahr
 */
public class Enemy {
	int x;
	int y;	
	Texture img;

	Enemy(int x, int y)
	{
		this.x = 400;
		this.y = 300;
		this.img = new Texture("man.png");
	}


}
