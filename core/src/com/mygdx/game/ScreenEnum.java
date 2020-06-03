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
        public Screen getScreen(VMGame game, int lvl, Object... params) {
            return new MainMenu(game);
        }
        
    },
    OPTIONS_MENU {
        @Override
        public Screen getScreen(VMGame game, int lvl, Object... params) {
            return new Settings(game);
        }
        
    },
    LEVEL_OVERLAY {
        @Override
        public Screen getScreen(VMGame game, int lvl, Object... params) {
            return new LevelContinue(game, lvl);
        }
        
    },
    LEVEL {
        @Override
        public Screen getScreen(VMGame game, int lvl, Object... params) {
            int time = 0;
            String map = "";
            switch(lvl){
                case 1: time = 80; map = "maps/level0.tmx";
                break;
                case 2: time = 80; map = "maps/alam_level1.tmx";
                break;
                case 3: time = 80; map = "maps/alam_level2.tmx";
                break;
                case 4: time = 80; map = "maps/E1map.tmx";
                break;
                case 5: time = 80; map = "KMap.tmx";
                break;
                case 6: time = 80; map = "maps/E2map.tmx";
                break;
                case 7: time = 80; map = "maps/DMap1.tmx";
                break;
                case 8: time = 80; map = "maps/DMap2.tmx";
                break;
                case 9: time = 80; map = "maps/DMap3.tmx";
                break;
                case 10: time = 80; map = "mapv3.tmx";
            }
            return new Level(time, game, map, lvl);
        }
        
    },
    GAME_WON {
        @Override
        public Screen getScreen(VMGame game, int lvl, Object... params) {
            return new GameWon(game);
        }
    },
    GAME_OVER {
        @Override
        public Screen getScreen(VMGame game, int lvl, Object... params) {
            return new GameOver(game);
        }
    };
    
    /**
     * Permite acceder a una screen por nombre
     * @param game
     * @param params
     * @return
     */
    public abstract Screen getScreen(VMGame game, int lvl, Object... params);
    
}
