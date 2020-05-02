package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class VMGame extends ApplicationAdapter {

    private Rectangle badlogic;
    SpriteBatch batch;
    Texture img;
    Enemy man1;
    Item item1 = null;
    double lastHit;
    int health;
    double acceleration;
    boolean isDashing;
    long dashTime;

    OrthographicCamera camera;
    
    HUD hud;
    
    @Override
    public void create() {
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
        health = 100;
        acceleration = 0;

        hud = new HUD();
        hud.setTime(123);
        hud.setHealth(98);
        hud.setDash(2);
        hud.setGel(0);

        camera = new OrthographicCamera(800, 600);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        // camera.setToOrtho(false, 30, 20);
        camera.update();
    }

    @Override
    public void render() {
        if (isDashing && dashTime - System.nanoTime() < 3 * 1000000000) {
            isDashing = false;
            acceleration = 0;
        }
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
	    man1.tick();
        batch.draw(img, badlogic.x, badlogic.y);
	    batch.draw(man1.img, man1.x, man1.y);
	    if(item1 != null) {
            batch.draw(item1.img, item1.x, item1.y);
        }
        batch.end();

        hud.stage.act(Gdx.graphics.getDeltaTime());
	    hud.stage.draw();

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
            if(item1 != null) {
                if (badlogic.overlaps(item1.hitbox))
                {
                    double now = System.nanoTime();
                    if (now - lastHit > 1000000000)
                    {
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
                health--;
                lastHit = now;
                System.out.println(health);
            }
        }

        camera.position.x = badlogic.x + badlogic.width / 2f; 
        camera.position.y = badlogic.y + badlogic.height / 2f; 
        camera.update();
        hud.setHealth(health);
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        hud.stage.dispose();
    }
}
