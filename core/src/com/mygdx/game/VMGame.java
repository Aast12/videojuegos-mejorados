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

    SpriteBatch batch; // servira para hacer render de los objetos
    BitmapFont font; // el font que utilizaremos para los botones
    Texture end; // luego sera un atributo de la clase Level
    Enemy man1; // aqui estara el enemigo
    Level level1; // variable en la que se almacena el nivel
    Player player; // entidad que controlara el usuario
    Item item1; // aqui se guarda un item
    ArrayList<Item> items; // aqui se guarda la lista de los items

    OrthographicCamera camera; // es la camara que seguira al jugador
    // Los siguientes dos manejaran el mapa, la imagen y los tiles
    TiledMap map;
    MapHandler mymap;

    Vector<RectangleMapObject> walls; // Los tiles de las paredes

    HUD hud; // aqui se hace el rendering del layout dle HUD

    Texture gameOver; // imagen de game over
    Texture winScreen; // imagen de game won

    Music music; // musica de menu
    Music levelMusic; // musica del nivel

    //Son las pantallas disponibles
    MainMenu mainMenu;
    Settings settings;
    LevelContinue levelContinue;

    /**
     * aqui se crean los assets necesarios para jugar al juego
     */
    @Override
    public void create() {
        // camera = new OrthographicCamera();
        // camera.setToOrtho(false, 800, 600);
        // mymap = new MapHandler("mapa.tmx", camera);
        // map = mymap.map;

        batch = new SpriteBatch();
        end = new Texture("end.png");

        item1 = new Item(200, 300, 26, 26);
        items = new ArrayList<Item>();
        items.add(item1);

        level1 = new Level(40, mymap, items);
        player = new Player(800 / 2 - 64 / 2, 136, level1, this);
      
      	gameOver = new Texture("game_over.png");
      	winScreen = new Texture("win_screen.png");

        mainMenu = new MainMenu(this);
        mainMenu.getMenu().setVisible(true);

        settings = new Settings(this);
        settings.getSettings().setVisible(false);

        levelContinue = new LevelContinue(this);
        levelContinue.getLevelContinue().setVisible(false);

        font = new BitmapFont();
        //this.setScreen(new Menu(this, "THE GAME", mainMenuOptions, background));

        music = Gdx.audio.newMusic(Gdx.files.internal("Manu.ogg"));
        music.setVolume((float) 0.05);
        levelMusic = Gdx.audio.newMusic(Gdx.files.internal("TheJ.mp3"));
        levelMusic.setVolume((float) 0.5);

        hud = new HUD();
        hud.setTime(level1.getLevelSeconds());
        hud.setHealth(98);
        hud.setDash(player.getDashes());
        hud.setGel(0);

        camera = new OrthographicCamera(800, 600);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        mymap = new MapHandler("mapa.tmx", camera);
        map = mymap.map;
        man1 = new RandomEnemy(700, 600, mymap);

        music.setLooping(true);
        music.play();
    }

    /**
     * marcamos el render de cada una de las screens y los assets que vamos a utilizar
     */
    @Override
    public void render() {
        // System.out.println(music.getPosition());

        // if (music.isPlaying() == false) {
        //     System.out.println("PLAY");
        // }
        Gdx.gl.glClearColor(26 / 256f, 28 / 256f, 44 / 256f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();

        if (mainMenu.getMenu().isVisible()) {
            batch.begin();
            mainMenu.render(Gdx.graphics.getDeltaTime());
            batch.end();
        }
        else if (settings.getSettings().isVisible()) {
            batch.begin();
            settings.render(Gdx.graphics.getDeltaTime());
            batch.end();
        } else if (!mainMenu.getMenu().isVisible() && !level1.getLost() && !level1.getWin()) { // pantalla de juego
            if (music.isPlaying()) {
                music.stop();
                levelMusic.play();
            }

            camera.position.x = player.x + player.getHitbox().width / 2f;
            camera.position.y = player.y + player.getHitbox().height / 2f;
            camera.update();

            // Map Render
            // renderer.render();
            // renderer.setView(camera);
            // batch.setProjectionMatrix(camera.combined);
            mymap.render(batch);
            batch.begin();
            
            // Sprites Render y tick
	        man1.tick();
	        level1.tick();
	        player.tick();

            player.render(batch);
	        man1.render(batch);
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
        } else { // pantalla de game over
            batch.begin();
            batch.draw(gameOver, camera.position.x - 400, camera.position.y - 300);
            batch.end();
        }
    }

    /**
     * se deja de hacer render de estos objetos de los que nos despojamos
     */
    @Override
    public void dispose() {
        batch.dispose();
        // map.dispose();
        mymap.dispose();
        font.dispose();
        hud.stage.dispose();
    }
}
