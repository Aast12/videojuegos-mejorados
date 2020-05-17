package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2D;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

public class VMGame extends Game {

    //OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFont font;
    Texture end; // THIS WILL LATER BE AN ATTRIBUTE OF LEVEL CLASS
    Enemy man1;
    Level level1;
    Player player;
    Item item1;
    ArrayList<Item> items;

    OrthographicCamera camera;
    // OrthogonalTiledMapRenderer renderer;
    TiledMap map;
    MapHandler mymap;

    Vector<RectangleMapObject> walls;

    HUD hud;

    Texture gameOver;
    Texture winScreen;

    Music music;

    MainMenu mainMenu;
    Settings settings;
    LevelContinue levelContinue;

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

        level1 = new Level(10, mymap, items);
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

        hud = new HUD();
        hud.setTime(level1.getLevelSeconds());
        hud.setHealth(98);
        hud.setDash(2);
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
        } else if (!mainMenu.getMenu().isVisible() && !level1.getLost() && !level1.getWin()) {

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
            hud.setTime(level1.getLevelSeconds());
        } else if (level1.getWin()) {
            batch.begin();
            batch.draw(winScreen, camera.position.x - 400, camera.position.y - 300);
            batch.end();
        } else {
            batch.begin();
            batch.draw(gameOver, camera.position.x - 400, camera.position.y - 300);
            batch.end();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        // map.dispose();
        mymap.dispose();
        font.dispose();
        hud.stage.dispose();
    }
}
