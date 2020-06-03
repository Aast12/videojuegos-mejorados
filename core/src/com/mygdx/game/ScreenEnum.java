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
        OPTIONS_MENU {
		@Override
		public Screen getScreen(VMGame game, Object... params) {
			return new Settings(game);
		}

	},
        LEVEL_OVERLAY {
		@Override
		public Screen getScreen(VMGame game, Object... params) {
			return new LevelContinue(game);
		}

	},
	LEVEL1 {
		@Override
		public Screen getScreen(VMGame game, Object... params) {
			return new Level(40, game, "mapTest.tmx", 1);
		}
		
	},
        LEVEL2 {
		@Override
		public Screen getScreen(VMGame game, Object... params) {
			return new Level(50, game, "mapTest.tmx", 2);
		}
		
	},
        LEVEL3 {
		@Override
		public Screen getScreen(VMGame game, Object... params) {
			return new Level(50, game, "mapTest.tmx", 3);
		}
		
	},
        LEVEL4 {
		@Override
		public Screen getScreen(VMGame game, Object... params) {
			return new Level(50, game, "mapTest.tmx", 4);
		}
		
	},
        LEVEL5 {
		@Override
		public Screen getScreen(VMGame game, Object... params) {
			return new Level(50, game, "mapTest.tmx", 5);
		}
		
	},
        LEVEL6 {
		@Override
		public Screen getScreen(VMGame game, Object... params) {
			return new Level(50, game, "mapTest.tmx", 6);
		}
		
	},
        LEVEL7 {
		@Override
		public Screen getScreen(VMGame game, Object... params) {
			return new Level(50, game, "mapTest.tmx", 7);
		}
		
	},
        LEVEL8 {
		@Override
		public Screen getScreen(VMGame game, Object... params) {
			return new Level(50, game, "mapTest.tmx", 8);
		}
		
	},
        LEVEL9 {
		@Override
		public Screen getScreen(VMGame game, Object... params) {
			return new Level(50, game, "mapTest.tmx", 9);
		}
		
	},
        LEVEL10 {
		@Override
		public Screen getScreen(VMGame game, Object... params) {
			return new Level(200, game, "maps/example/mapv3.tmx", 10);
		}
		
	},
	GAME_WON {
		@Override
		public Screen getScreen(VMGame game, Object... params) {
			return new GameWon(game);
		}
	},
	GAME_OVER {
		@Override
		public Screen getScreen(VMGame game, Object... params) {
			return new GameOver(game);
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
