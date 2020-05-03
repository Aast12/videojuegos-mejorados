package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class VMGame extends ApplicationAdapter {

    private Rectangle badlogic;
    SpriteBatch batch;
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
    
    HUD hud;
    

    Texture gameOver;
    Texture winScreen;
    boolean lost;
    boolean win;

    @Override
    public void create() {
        badlogic = new Rectangle();
        badlogic.x = 800 / 2 - 64 / 2;
        badlogic.y = 20;
        badlogic.width = 64;
        badlogic.height = 64;
        batch = new SpriteBatch();
        img = new Texture("badlogic.png");
        end = new Texture("end.png");
        man1 = new Enemy(400, 300);
        item1 = new Item(200, 300);
        lastHit = System.nanoTime();
      	gameOver = new Texture("game_over.png");
      	winScreen = new Texture("win_screen.png");
        health = 100;
        acceleration = 0;
        pointsInLevel = 0;
      
        lost = false;
        win = false;

        hud = new HUD();
        hud.setTime(123);
        hud.setHealth(98);
        hud.setDash(2);
        hud.setGel(0);
    }

    @Override
    public void render() {
        if (isDashing && dashTime - System.nanoTime() < 3 * 1000000000) {
            isDashing = false;
            acceleration = 0;
        }
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

	    man1.tick();
	    if (!lost && !win)
	    {
        batch.draw(end, 100, 200 );
        if(item1 != null) {
            batch.draw(item1.img, item1.x, item1.y);
        }
        batch.draw(img, badlogic.x, badlogic.y);
	    batch.draw(man1.img, man1.x, man1.y);

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
            if(item1 != null) { //Later implementation will be with each of the elements of the array of items
                if (badlogic.overlaps(item1.hitbox))
                {
                    double now = System.nanoTime();
                    if (now - lastHit > 1000000000)
                    {
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
            if (now - lastHit > 1000000000) {
                //health--;
		        health -= 25; //quick death for testing
                lastHit = now;
		        if (health <= 0){
			        lost = true;
		        }
            }
        }
        if (pointsInLevel == 1) { //The 1 will later be an attribute for needed points to win in that level
            if ((badlogic.x + badlogic.width) > 132 && badlogic.x < 132 &&
                        (badlogic.y + badlogic.height) > 232 && badlogic.y < 232 ) {
                win = true;
            }
        }

        hud.setHealth(health);
	    }
	    else if (win) {
            batch.draw(winScreen, 0, 0);
            batch.end();
        }
	    else
	    {
		    batch.draw(gameOver, 0, 0);
		    batch.end();
	    }
    }


    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        hud.stage.dispose();
    }
}
