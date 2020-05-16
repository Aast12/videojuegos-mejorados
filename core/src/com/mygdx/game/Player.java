/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
public class Player extends Entity {

    private double acceleration; // velocidad aumentada por el dash
    private int health; // la vida del jugador
    private VMGame game; // cambar a futuro por Level
    private boolean isDashing; // checa si esta haciendo dash
    long dashTime; // checa el tiempo del dash
    double lastHit;

    public Player(int x, int y, VMGame game)
    {
        super(x, y);
        this.game = game;
        this.img = new Texture("player.png");
        this.hitbox = new Rectangle();
        hitbox.width = 40;
        hitbox.height = 64;
        hitbox.x = x + 12;
        hitbox.y = y;
        acceleration = 0;
        health = 100;
        isDashing = false;
        lastHit = System.nanoTime();
    }

    /**
     * getter de la vida
     * @return la vida del jugador
     */
    public int getHealth() {return health;}

    /**
     * Aqui se hace el movimiento del jugador
     */
    public void tick()
    {
        double dx = 0, dy = 0;

        hitbox.x = x + 12;
        hitbox.y = y;
        //Deja el dash despues de cierto tiempo
        if (isDashing && dashTime - System.nanoTime() < 3 * 1000000000) {
            isDashing = false;
            acceleration = 0;
        }
        // Se mueve a la izquierda
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            dx = -(200f + acceleration) * Gdx.graphics.getDeltaTime();
        }
        //Se mueve a la derecha
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            dx = (200 + acceleration) * Gdx.graphics.getDeltaTime();
        }
        // Se mueve hacia arriba
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            dy = (200 + acceleration) * Gdx.graphics.getDeltaTime();
        }
        // Se mueve hacia abajo
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            dy = -(200 + acceleration) * Gdx.graphics.getDeltaTime();
        }
        // Checa que no haya chocado con alguna pared del nivel
        Rectangle test = new Rectangle(getHitbox());
        test.x += dx;
        test.y += dy;
        boolean collision = game.mymap.collidesOnLayer("Walls", test);
        // Avanza si no ha chocado con alguna pared
        if (!collision) {
            x += dx;
            y += dy;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.F)) {// GELES

        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {// RECOGER
            if(game.item1 != null) { //Later implementation will be with each of the elements of the array of items
                if (getHitbox().overlaps(game.item1.hitbox))
                {
                    double now = System.nanoTime();
                    if (now - lastHit > 1000000000) {
                        game.item1 = null;
                        lastHit = now;
                        game.pointsInLevel = game.pointsInLevel + 1; // Implementar obtencion de items en nivel
                    }
                }
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {// DASH
            dashTime = System.nanoTime();
            isDashing = true;
            acceleration = 200;
        }
        if (getHitbox().overlaps(game.man1.hitbox)) {
            double now = System.nanoTime();
            if (now - lastHit > 1000000000) {
                health -= 25; //quick death for testing
                lastHit = now;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(img, x, y);
    }
}