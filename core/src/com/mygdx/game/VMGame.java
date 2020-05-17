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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

public class VMGame extends Game {

    private Rectangle badlogic;
    //OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFont font;
    Texture end; // THIS WILL LATER BE AN ATTRIBUTE OF LEVEL CLASS
    Enemy man1;
    Player player;
    Item item1 = null;
    int pointsInLevel; //This will be used for checking win condition

    int levelSeconds;
    float timeSeconds = 0f;
    private float period = 1f;

    OrthographicCamera camera;
    // OrthogonalTiledMapRenderer renderer;
    TiledMap map;
    MapHandler mymap;

    Vector<RectangleMapObject> walls;

    HUD hud;

    Texture gameOver;
    Texture winScreen;
    boolean lost;
    boolean win;

    Music music;

    MainMenu mainMenu;

    @Override

    public void create() {
        //camera = new OrthographicCamera();
        //camera.setToOrtho(false, 800, 600);

        batch = new SpriteBatch();
        end = new Texture("end.png");
        player = new Player(800 / 2 - 64 / 2, 136, this);
        man1 = new Enemy(656, 300);
        item1 = new Item(200, 300);
      
      	gameOver = new Texture("game_over.png");
      	winScreen = new Texture("win_screen.png");

        pointsInLevel = 0;
        levelSeconds = 10;

        mainMenu = new MainMenu(this);
        mainMenu.getMenu().setVisible(true);

        font = new BitmapFont();
        //this.setScreen(new Menu(this, "THE GAME", mainMenuOptions, background));

        lost = false;
        win = false;

        music = Gdx.audio.newMusic(Gdx.files.internal("Manu.ogg"));
        music.setVolume((float) 0.05);

        hud = new HUD();
        hud.setTime(levelSeconds);
        hud.setHealth(98);
        hud.setDash(2);
        hud.setGel(0);

        camera = new OrthographicCamera(800, 600);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        music.setLooping(true);
        music.play();

        mymap = new MapHandler("mapa.tmx", camera);
        map = mymap.map;

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
	    else if (!mainMenu.getMenu().isVisible() && !lost && !win){

	        // Se gana teniendo el item y yendo al final
            if (pointsInLevel == 1) { //The 1 will later be an attribute for needed points to win in that level
                if ((player.x + player.getHitbox().width) > 160 && player.x < 160 &&
                            (player.y + player.getHitbox().height) > 628 && player.y < 628 ) {
                    win = true;
                }
            }

            // Se puede perder por baja vida, implementar en level
            if (player.getHealth() <= 0){
                lost = true;
            }

            /**
             * Se hace el contrarreloj y permite perder por tiempo
             */
            timeSeconds +=Gdx.graphics.getRawDeltaTime();
            if(timeSeconds > period){
                timeSeconds-=period;
                levelSeconds--;
                if (levelSeconds <= 0) {
                    lost = true;
                }
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
            player.tick();
	        man1.tick();
            player.render(batch);
	        batch.draw(man1.img, man1.x, man1.y);
	        if(item1 != null) {
                item1.render(batch);
            }
            batch.draw(end, 128, 596);

            //batch.draw;
            batch.end();

            // HUD Render
            hud.stage.act(Gdx.graphics.getDeltaTime());
            hud.stage.draw();

            hud.setHealth(player.getHealth());
            hud.setTime(levelSeconds);
        } else if (win) {
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
