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
    Texture img;
    Texture end; // THIS WILL LATER BE AN ATTRIBUTE OF LEVEL CLASS
    Enemy man1;
    Item item1 = null;
    double lastHit;
    int health;
    int pointsInLevel; //This will be used for checking win condition

    int levelSeconds;
    float timeSeconds = 0f;
    private float period = 1f;

    double acceleration;
    long timeUnit = 1000000000;
    boolean isDashing;
    long dashTime;

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

        badlogic = new Rectangle();
        badlogic.x = 800 / 2 - 64 / 2;
        badlogic.y = 136;
        badlogic.width = 64;
        badlogic.height = 64;
        batch = new SpriteBatch();
        img = new Texture("player.png");
        end = new Texture("end.png");
        man1 = new Enemy(656, 300);
        item1 = new Item(200, 300);
        lastHit = System.nanoTime();
        gameOver = new Texture("game_over.png");
        winScreen = new Texture("win_screen.png");
        health = 100;
        acceleration = 0;

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
        if (isDashing && System.nanoTime() - dashTime > 0.2 * timeUnit) {
            isDashing = false;
            acceleration = 0;
        }

        Gdx.gl.glClearColor(26 / 256f, 28 / 256f, 44 / 256f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();

        if (mainMenu.getMenu().isVisible()) {
            batch.begin();
            mainMenu.render(Gdx.graphics.getDeltaTime());
            batch.end();
        } else if (!mainMenu.getMenu().isVisible() && !lost && !win) {
            double dx = 0, dy = 0;
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                dx = -(200f + acceleration) * Gdx.graphics.getDeltaTime();
                // badlogic.x -= (200 + acceleration) * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                dx = (200 + acceleration) * Gdx.graphics.getDeltaTime();
                // badlogic.x += (200 + acceleration) * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                dy = (200 + acceleration) * Gdx.graphics.getDeltaTime();
                // badlogic.y += (200 + acceleration) * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                dy = -(200 + acceleration) * Gdx.graphics.getDeltaTime();
                // badlogic.y -= (200 + acceleration) * Gdx.graphics.getDeltaTime();
            }

            Rectangle test = new Rectangle(badlogic);
            test.x += dx;
            test.y += dy;
            boolean collision = mymap.collidesOnLayer("Walls", test);

            if (!collision) {
                badlogic.x += dx;
                badlogic.y += dy;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.F)) {// GELES

            }
            if (Gdx.input.isKeyPressed(Input.Keys.E)) {// RECOGER
                if (item1 != null) { //Later implementation will be with each of the elements of the array of items
                    if (badlogic.overlaps(item1.hitbox)) {
                        double now = System.nanoTime();
                        if (now - lastHit > timeUnit) {
                            item1 = null;
                            lastHit = now;
                            pointsInLevel = pointsInLevel + 1;
                        }
                    }
                }
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {// DASH
                dashTime = System.nanoTime();
                isDashing = true;
                acceleration = 200;
            }
            if (badlogic.overlaps(man1.hitbox)) {
                double now = System.nanoTime();
                if (now - lastHit > timeUnit) {
                    //health--;
                    health -= 25; //quick death for testing
                    lastHit = now;
                    if (health <= 0) {
                        lost = true;
                    }
                }
            }
            if (pointsInLevel == 1) { //The 1 will later be an attribute for needed points to win in that level
                if ((badlogic.x + badlogic.width) > 160 && badlogic.x < 160
                        && (badlogic.y + badlogic.height) > 628 && badlogic.y < 628) {
                    win = true;
                }
            }

            timeSeconds += Gdx.graphics.getRawDeltaTime();
            if (timeSeconds > period) {
                timeSeconds -= period;
                levelSeconds--;
                if (levelSeconds <= 0) {
                    lost = true;
                }
            }

            camera.position.x = badlogic.x + badlogic.width / 2f;
            camera.position.y = badlogic.y + badlogic.height / 2f;
            camera.update();

            // Map Render
            // renderer.render();
            // renderer.setView(camera);
            // batch.setProjectionMatrix(camera.combined);
            mymap.render(batch);
            batch.begin();

            // Sprites Render
            man1.tick();
            batch.draw(img, badlogic.x, badlogic.y); //TODO:replace with render()
            batch.draw(man1.img, man1.x, man1.y);
            if (item1 != null) {
                batch.draw(item1.img, item1.x, item1.y);
            }
            batch.draw(end, 128, 596);

            //batch.draw;
            batch.end();

            // HUD Render
            hud.stage.act(Gdx.graphics.getDeltaTime());
            hud.stage.draw();

            hud.setHealth(health);
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
        img.dispose();
        // map.dispose();
        mymap.dispose();
        font.dispose();
        hud.stage.dispose();
    }
}
