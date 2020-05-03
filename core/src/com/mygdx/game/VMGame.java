package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    Enemy man1;
    Item item1 = null;
    double lastHit;
    int health;

    double acceleration;
    boolean isDashing;
    long dashTime;

    OrthographicCamera camera;
    OrthogonalTiledMapRenderer renderer;
    TiledMap map;
    
    HUD hud;
    

    Texture gameOver;
    boolean lost;

    // for main menu
    private Menu mainMenu;
    private LinkedList<Button> mainMenuOptions;

    @Override

    public void create() {
        //camera = new OrthographicCamera();
        //camera.setToOrtho(false, 800, 600);

        badlogic = new Rectangle();
        badlogic.x = 800 / 2 - 64 / 2;
        badlogic.y = 20;
        badlogic.width = 64;
        badlogic.height = 64;
        batch = new SpriteBatch();
        img = new Texture("badlogic.png");

        man1 = new Enemy(400, 300);
        item1 = new Item(200, 300);
        lastHit = System.nanoTime();
      	gameOver = new Texture("game_over.png");
        health = 100;
        acceleration = 0;

        // for main menu
        mainMenuOptions = new LinkedList<Button>();
        Texture mainMenuMaterial = new Texture("material1.png");
        Button start = new Button(60, 450, 128, 32, "START", mainMenuMaterial);
        Button load = new Button(60, 485, 128, 32, "LOAD", mainMenuMaterial);
        Button options = new Button(60, 520, 128, 32, "OPTIONS", mainMenuMaterial);
        Button exit = new Button(60, 555, 128, 32, "EXIT", mainMenuMaterial);
        mainMenuOptions.add(start);
        mainMenuOptions.add(load);
        mainMenuOptions.add(options);
        mainMenuOptions.add(exit);
        Texture background = new Texture("main_menu_background.png");
        mainMenu = new Menu(this, "THE GAME", mainMenuOptions, background);
        mainMenu.setVisible(true);

        font = new BitmapFont();
        //this.setScreen(new Menu(this, "THE GAME", mainMenuOptions, background));
      
	      lost = false;


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
    }

    @Override
    public void render() {
        if (Gdx.input.isTouched() && mainMenu.getOptions().get(0).getBox().contains(Gdx.input.getX(), Gdx.input.getY())) {
            mainMenu.setVisible(false);
        }
        if (isDashing && dashTime - System.nanoTime() < 3 * 1000000000) {
            isDashing = false;
            acceleration = 0;
        }

        Gdx.gl.glClearColor(26 / 256f, 28 / 256f, 44 / 256f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

      
        super.render();

        man1.tick();
        if (mainMenu.isVisible()) {
            batch.begin();
            mainMenu.render(Gdx.graphics.getDeltaTime());
            batch.end();
        }
	    else if (!lost)
	    {

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
            if (item1 != null) {
                if (badlogic.overlaps(item1.hitbox)) {
                    double now = System.nanoTime();
                    if (now - lastHit > 1000000000) {
                        item1 = null;
                        lastHit = now;
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
            if (now - lastHit > 1000000000) {
                //health--;
                health -= 25; //quick death for testing
                        lastHit = now;

                if (health <= 0)
                {
                    lost = true;
                }
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
	    if(item1 != null) {
            batch.draw(item1.img, item1.x, item1.y);
        }
        batch.end();

        // HUD Render
        hud.stage.act(Gdx.graphics.getDeltaTime());
	    hud.stage.draw();

        hud.setHealth(health);
	    }
	    else
	    {
            batch.begin();
		    batch.draw(gameOver, 0, 0);
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
