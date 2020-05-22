/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Screen;

/**
 *
 * @author kaetahr
 */
public enum ScreenEnum {
	MAIN_MENU {
		@Override
		public Screen getScreen(VMGame game, Object... params) {
			return new MainMenu(game);
		}

	},
	LEVEL {
		@Override
		public Screen getScreen(VMGame game, Object... params) {
			return new Level(40, game);
		}
		
	};
	/**
	 * Permite acceder a una screen por nombre
	 * @param game
	 * @param params
	 * @return 
	 */
	public abstract Screen getScreen(VMGame game, Object... params);
	
}
