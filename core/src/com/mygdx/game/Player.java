/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.mygdx.game;

import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Player extends Entity {
    
    private double acceleration; // velocidad aumentada por el dash
    private int health; // la vida del jugador
    private Level level; // cambar a futuro por Level
    private boolean isDashing; // checa si esta haciendo dash
    long dashTime, gelTime; // checa el tiempo del dash y geles
    double lastHit;
    private int dashes, geles, gelShield;
    private long timeUnit = 1000000000;
    private double dashTimeProportion = 0.2;
    
    Vector<Animation<TextureRegion>> walkAnimation; // Lista de animaciones de caminata
    int animationState = -1;
    // Estatico
    // 0 frontal
    // 1 derecha
    // 2 izquierda
    // 3 atras
    
    Texture walkSheet; // Spritesheet de animacion
    float stateTime; // Tiempo de animacion
    
    /**
     * constructor de player
     * @param x coordenada en x
     * @param y coordenada en y
     * @param level nivel en el que esta jugando
     */
    public Player(int x, int y, Level level)
    {
        super(x, y);
        this.level = level;
        this.img = new Texture("player.png");
        this.hitbox = new Rectangle();
        hitbox.x = x;
        hitbox.y = y;
        acceleration = 0;
        health = 100;
        isDashing = false;
        lastHit = System.nanoTime();
        
        int spritesheetRows = 4;
        int spritesheetCols = 4;
        
        walkSheet = new Texture(Gdx.files.internal("main_spritesheet.png"));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / spritesheetCols,
                walkSheet.getHeight() / spritesheetRows);
        
        // Asignación del hitbox con el tamaño de la sprite
        // La spritesheet tiene 4 frames por fila y columna, sin separación
        hitbox.width = walkSheet.getWidth() / spritesheetCols;
        hitbox.height = walkSheet.getHeight() / spritesheetRows;
        
        // Asignacion de animaciones
        walkAnimation = new Vector<Animation<TextureRegion>>();
        for (int i = 0; i < spritesheetRows; i++) {
            TextureRegion[] walkFrames = new TextureRegion[4];
            for (int j = 0; j < spritesheetCols; j++) {
                walkFrames[j] = tmp[i][j];
            }
            walkAnimation.add(new Animation<TextureRegion>(0.25f, walkFrames));
        }
        
        stateTime = 0f;
        
        geles = 3;
        gelShield = 0;
        dashes = 3;
        
    }
    
    /**
     * getter de la vida
     * @return la vida del jugador
     */
    public int getHealth() {return health;}
    
    /**
     * getter de los dashes
     * @return cantidad de dashes
     */
    public int getDashes() {return dashes;}
    
    /**
     * getter del escudo
     * @return magnitud del escudo
     */
    public int getShield() {return gelShield;}
    
    /**
     * getter de los geles
     * @return cantidad de geles
     */
    public int getGel() {return geles;}
    
    /**
     * Aqui se hace el movimiento del jugador
     */
    public void tick()
    {
        stateTime += Gdx.graphics.getDeltaTime();
        double dx = 0, dy = 0;
        
        hitbox.x = x;
        hitbox.y = y;
        // Reinicia la animacion a posicion estatica
        animationState = -1;
        //Deja el dash despues de cierto tiempo
        if (isDashing && System.nanoTime() - dashTime > dashTimeProportion * timeUnit) {
            isDashing = false;
            acceleration = 0;
        }
        
        if ( dashes < 3 && !isDashing && System.nanoTime() - dashTime > 2 * timeUnit) {
            dashes++;
            dashTime = System.nanoTime();
            level.regenDash.play((float) 0.3);
        }
        
        if (gelShield > 0 && System.nanoTime() - gelTime > timeUnit ) {
            gelTime = System.nanoTime();
            gelShield -= 5;
            if (gelShield < 0) {
                gelShield = 0;
            }
        }
        // Se mueve a la izquierda
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            dx = -(200f + acceleration) * Gdx.graphics.getDeltaTime();
            animationState = 1;
        }
        //Se mueve a la derecha
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            dx = (200 + acceleration) * Gdx.graphics.getDeltaTime();
            animationState = 2;
        }
        // Se mueve hacia arriba
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            dy = (200 + acceleration) * Gdx.graphics.getDeltaTime();
            animationState = 3;
        }
        // Se mueve hacia abajo
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            dy = -(200 + acceleration) * Gdx.graphics.getDeltaTime();
            animationState = 0;
        }
        // Checa que no haya chocado con alguna pared del nivel
        Rectangle test = new Rectangle(getHitbox());
        test.x += dx;
        test.y += dy;
        boolean collision = level.mymap.collidesOnLayer("Walls", test);
        
        // Avanza si no ha chocado con alguna pared
        if (!collision) {
            x += dx;
            y += dy;
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {// GELES
            if (geles > 0) {
                geles--;
                gelTime = System.nanoTime();
                gelShield += 30 / (level.getGame().globals.index + 1);
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {// RECOGER
            for (String key : level.getItems().keySet()) {
                for (Item item : level.getItems().get(key)) {
                    if (hitbox.overlaps(item.hitbox)
                            && item.getPickable() == 1) {
                        double now = System.nanoTime();
                        if (now - lastHit > timeUnit) {
                            lastHit = now;
                            item.setPickable(0);
                            level.setPoints(level.getPoints() + 100 * (level.getGame().globals.index + 1));
                            for (String k : level.getGroup().keySet()) {
                                if (level.getGroup().get(k).getKey() == key) {
                                    level.getGroup().get(k).setCounter(level.getGroup().get(k).getCounter() - 1);
                                }
                            }
                        }
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
        for (Enemy e : level.enemies)
        {
            if (getHitbox().overlaps(e.getCovidZone())) {
                double now = System.nanoTime();
                if (now - lastHit > timeUnit) {
                    int minusShield = gelShield;
                    if (minusShield > 25) {
                        minusShield = 25;
                    }
                    int minusHealth = 25 - minusShield;
                    gelShield -= minusShield;
                    health -= minusHealth; //quick death for testing
                    lastHit = now;
                }
            }
        }
        // Se puede perder por baja vida, implementar en level
        if (health <= 0){
            level.setLost(true);
        }
        
        // Se gana teniendo el item y yendo al final
        if (level.endpoint.overlaps(hitbox)) { // goes to ending point
            int itemsRemaining = 0;
            for (String key : level.getGroup().keySet()) {
                itemsRemaining += level.getGroup().get(key).getCounter();
            }
            if (itemsRemaining <= level.getMinimumItems()) {
                level.setWin(true);
            }
        }
    }
    
    @Override
    public void render(SpriteBatch batch) {
        if (animationState == -1) {
            // El frame 0 es una posicion estatica
            batch.draw(walkAnimation.get(0).getKeyFrame(0), x, y);
        } else {
            // Dibuja el frame correspondiente a la animacion
            batch.draw(walkAnimation.get(animationState).getKeyFrame(stateTime, true), x, y);
        }
    }
}
