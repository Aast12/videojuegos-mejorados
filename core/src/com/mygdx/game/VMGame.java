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

    Music music; // musica de menu

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
        // camera = new OrthographicCamera();
        // camera.setToOrtho(false, 800, 600);
        // mymap = new MapHandler("mapa.tmx", camera);
        // map = mymap.map;
      	gameOver = new GameOver(this);
      	winScreen = new Texture("win_screen.png");


        settings = new Settings(this);
        settings.getSettings().setVisible(false);

        levelContinue = new LevelContinue(this);
        levelContinue.getLevelContinue().setVisible(false);

        font = new BitmapFont();
        //this.setScreen(new Menu(this, "THE GAME", mainMenuOptions, background));

        music = Gdx.audio.newMusic(Gdx.files.internal("Manu.ogg"));
        music.setVolume((float) 0.05);


        camera = new OrthographicCamera(800, 600);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();



        music.setLooping(true);
        music.play();
	
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
/*
        if (mainMenu.getMenu().isVisible()) {
            batch.begin();
            mainMenu.render(Gdx.graphics.getDeltaTime());
            batch.end();
        }
        else if (settings.getSettings().isVisible()) {
            batch.begin();
            settings.render(Gdx.graphics.getDeltaTime());
            batch.end();
	}
	else if (gameOver.isVisible()) {
		batch.begin();
		gameOver.render(Gdx.graphics.getDeltaTime());
		batch.end();
	}
        else if (!mainMenu.getMenu().isVisible() && !level1.getLost() && !level1.getWin()) { // pantalla de juego
		
            if (music.isPlaying()) {
                music.stop();
                levelMusic.play();
            }

            camera.position.x = player.x + player.getHitbox().width / 2f;
            camera.position.y = player.y + player.getHitbox().height / 2f;
            camera.update();

            mymap.render(batch);
            batch.begin();
            
		// Sprites Render y tick
		for (Iterator<Enemy> it = enemies.iterator(); it.hasNext();) {
			Enemy e = it.next();
			e.tick();
		}
	        level1.tick();
	        player.tick();

            player.render(batch);
		for (Iterator<Enemy> it = enemies.iterator(); it.hasNext();) {
			Enemy e = it.next();
			e.render(batch);
		}
	        level1.render(batch);
            batch.draw(end, 128, 596);

            //batch.draw;
            batch.end();

            // HUD Render
            hud.stage.act(Gdx.graphics.getDeltaTime());
            hud.stage.draw();

            hud.setHealth(player.getHealth());
            hud.setDash(player.getDashes());
            hud.setTime(level1.getLevelSeconds());
		
        } else if (level1.getWin()) { // pantalla de game won
            batch.begin();
            batch.draw(winScreen, camera.position.x - 400, camera.position.y - 300);
            batch.end();
        } 
	else if (level1.getLost())
	{
		gameOver.setVisible(true);
		camera.setToOrtho(false, 800, 600);
		camera.update();
	}
	*/
    }

    /**
     * se deja de hacer render de estos objetos de los que nos despojamos
     */
    @Override
	    public void dispose() {
		//batch.dispose();
		// map.dispose();
		//mymap.dispose();
		font.dispose();
        //hud.stage.dispose();
    }
}
