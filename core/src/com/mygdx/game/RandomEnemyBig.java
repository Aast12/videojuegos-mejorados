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
public class RandomEnemyBig extends RandomEnemy {
	
	public RandomEnemyBig(int x, int y, MapHandler map) {
		super(x, y, map);
		covidOffset = 64;
		this.covidBox = new SafeDistance(this.x-covidOffset, this.y-covidOffset, 192, 192);
		speed = 2;
		maxDisplacement = 800;
		dialogue.add("Me da miedo toser");
	}

	
}
