package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;

import java.util.ArrayList;
import java.util.Vector;

public class VMGame extends Game {


    BitmapFont font; // el font que utilizaremos para los botones
    Level level; // variable en la que se almacena el nivel

    OrthographicCamera camera; // es la camara que seguira al jugador
    // Los siguientes dos manejaran el mapa, la imagen y los tiles


    Vector<RectangleMapObject> walls; // Los tiles de las paredes


    GameOver gameOver; // imagen de game over
    Texture winScreen; // imagen de game won


    //Son las pantallas disponibles
    MainMenu mainMenu;
    Settings settings;
    LevelContinue levelContinue;
    ScreenHandler screenHandler;

    /**
     * aqui se crean los assets necesarios para jugar al juego
     */
    @Override
    public void create() {
      	gameOver = new GameOver(this);
      	winScreen = new Texture("win_screen.png");


        settings = new Settings(this);
        settings.getSettings().setVisible(false);

        levelContinue = new LevelContinue(this);
        levelContinue.getLevelContinue().setVisible(false);

        font = new BitmapFont();



        camera = new OrthographicCamera(800, 600);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();



	
	ScreenHandler.getInstance().init(this);
	ScreenHandler.getInstance().showScreen(ScreenEnum.MAIN_MENU, this);
	
    }

    /**
     * marcamos el render de cada una de las screens y los assets que vamos a utilizar
     */
    @Override
    public void render() {
        Gdx.gl.glClearColor(26 / 256f, 28 / 256f, 44 / 256f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();
    }

    /**
     * se deja de hacer render de estos objetos de los que nos despojamos
     */
    @Override
	    public void dispose() {
		font.dispose();
    }
}
