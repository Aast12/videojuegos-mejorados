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
    private Level level; // cambar a futuro por Level
    private boolean isDashing; // checa si esta haciendo dash
    long dashTime; // checa el tiempo del dash
    double lastHit;
    private VMGame game;
    private int dashes;

    public Player(int x, int y, Level level, VMGame game)
    {
        super(x, y);
        this.level = level;
        this.img = new Texture("player.png");
        this.game = game;
        this.hitbox = new Rectangle();
        hitbox.width = 40;
        hitbox.height = 64;
        hitbox.x = x + 12;
        hitbox.y = y;
        acceleration = 0;
        health = 100;
        isDashing = false;
        lastHit = System.nanoTime();
        dashes = 3;
    }

    /**
     * getter de la vida
     * @return la vida del jugador
     */
    public int getHealth() {return health;}

    /**
     * getter de los dashes
     * @return
     */
    public int getDashes() {return dashes;}

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
            for (int i = 0; i < level.getItems().size(); i++ ) {
                if (hitbox.overlaps(level.getItems().get(i).hitbox))
                {
                    double now = System.nanoTime();
                    if (now - lastHit > 1000000000) {
                        System.out.println(2);
                        lastHit = now;
                        level.getItems().remove(i);
                    }
                }
            }

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {// DASH
            if (dashes > 0) {
                dashes--;
                dashTime = System.nanoTime();
                isDashing = true;
                acceleration = 200;
            }
        }
        if (getHitbox().overlaps(game.man1.hitbox)) {
            double now = System.nanoTime();
            if (now - lastHit > 1000000000) {
                health -= 25; //quick death for testing
                lastHit = now;
            }
        }
        // Se puede perder por baja vida, implementar en level
        if (health <= 0){
            level.setLost(true);
        }

        // Se gana teniendo el item y yendo al final
        if (level.getItems().size() == 0) { //The 1 will later be an attribute for needed points to win in that level
            if ((x + hitbox.width) > 160 && x < 160 && (y + hitbox.height) > 628 && y < 628 ) {
                level.setWin(true);
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(img, x, y);
    }
}