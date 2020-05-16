package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import java.util.LinkedList;

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

    double acceleration;
    boolean isDashing;
    long dashTime;

    OrthographicCamera camera;
    OrthogonalTiledMapRenderer renderer;
    TiledMap map;

    HUD hud;

    Texture gameOver;
    Texture winScreen;
    boolean lost;
    boolean win;

    private int day; // para la pantalla previa a nivel (puede servir de nivel)

    Music music;

    MainMenu mainMenu;
    LevelContinue levelContinue;
    Settings settings;

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

        mainMenu = new MainMenu(this);
        mainMenu.getMenu().setVisible(true);

        levelContinue = new LevelContinue(this);
        levelContinue.getLevelContinue().setVisible(true);

        settings = new Settings(this);
        settings.getSettings().setVisible(true);

        font = new BitmapFont();
        //this.setScreen(new Menu(this, "THE GAME", mainMenuOptions, background));

        lost = false;
        win = false;

        music = Gdx.audio.newMusic(Gdx.files.internal("Manu.ogg"));
        music.setVolume((float) 0.05);

        hud = new HUD();
        hud.setTime(123);
        hud.setHealth(98);
        hud.setDash(2);
        hud.setGel(0);

        map = new TmxMapLoader().load("mapa.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1);

        camera = new OrthographicCamera(800, 600);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();

        music.setLooping(true);
        music.play();

    }

    @Override
    public void render() {
        // System.out.println(music.getPosition());

        // if (music.isPlaying() == false) {
        //     System.out.println("PLAY");
        // }
        if (isDashing && System.nanoTime() - dashTime > 0.2 * 1000000000) {
            isDashing = false;
            acceleration = 0;
        }

        Gdx.gl.glClearColor(26 / 256f, 28 / 256f, 44 / 256f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();

        if (mainMenu.getMenu().isVisible()) {
            batch.begin();
            mainMenu.render(Gdx.graphics.getDeltaTime());
            //mainMenu.render(Gdx.graphics.getDeltaTime());
            batch.end();
        } else if (!mainMenu.getMenu().isVisible() && !lost && !win) {

            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                badlogic.x -= (200 + acceleration) * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                badlogic.x += (200 + acceleration) * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                badlogic.y += (200 + acceleration) * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                badlogic.y -= (200 + acceleration) * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.F)) {// GELES

            }
            if (Gdx.input.isKeyPressed(Input.Keys.E)) {// RECOGER
                if (item1 != null) { //Later implementation will be with each of the elements of the array of items
                    if (badlogic.overlaps(item1.hitbox)) {
                        double now = System.nanoTime();
                        if (now - lastHit > 1000000000) {
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
                acceleration = 250;
            }
            if (badlogic.overlaps(man1.hitbox)) {
                double now = System.nanoTime();
                if (now - lastHit > 1000000000) {
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

            camera.position.x = badlogic.x + badlogic.width / 2f;
            camera.position.y = badlogic.y + badlogic.height / 2f;
            camera.update();

            // Map Render
            renderer.render();
            renderer.setView(camera);
            batch.begin();
            batch.setProjectionMatrix(camera.combined);

            // Sprites Render
            man1.tick();
            batch.draw(img, badlogic.x, badlogic.y);
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
        map.dispose();
        renderer.dispose();
        font.dispose();
        hud.stage.dispose();
    }
}
