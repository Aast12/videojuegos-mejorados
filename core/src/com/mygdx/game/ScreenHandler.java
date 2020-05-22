/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 *
 * @author kaetahr
 * Screen handler como singleton
 */
public class ScreenHandler {
	private static ScreenHandler instance; //única instancia de la clase
	private Game game;
	
	/**
	 * Constructor de Singleton
	 */
	private ScreenHandler()
	{
		super();
	}

	public static ScreenHandler getInstance()
	{
		if (instance == null)
		{
			instance = new ScreenHandler();
		}
		return instance;
	}

	public void init(Game g)
	{
		this.game = g;
	}

	public void showScreen(ScreenEnum screen,VMGame game, Object... params)
	{
		Screen curr = game.getScreen();
		Screen newScreen;
		newScreen = screen.getScreen(game, params);
		game.setScreen(newScreen);

		if (curr != null)
		{
			curr.dispose();
		}
	}


	
}
