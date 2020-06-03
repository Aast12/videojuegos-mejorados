/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

/**
 *
 * @author kaetahr
 */
public class Enemy extends Entity {
    //Disease is the damage dealing circlu
    //hitbox is just used for collision detection
    SafeDistance covidBox;
    int speed;
    int direction; //1D movement
    MapHandler map;
    int covidOffset;
    int covidOffsetX;
    int covidOffsetY;
    BitmapFont font;
    ArrayList<String> dialogue;
    String diagLine;
    protected boolean willTalk;
    
    Vector<Animation<TextureRegion>> walkAnimation; // Lista de animaciones de caminata
    int animationState = -1;
    // Estatico
    // 0 frontal
    // 1 derecha
    // 2 izquierda
    // 3 atras
    
    Texture walkSheet; // Spritesheet de animacion
    float stateTime; // Tiempo de animacion
    
    float lastX;
    float lastY;
    
    
    /**
     * Inicializa un enemigo
     * @param x Posicion inicial
     * @param y Posicion inicial
     * @param map Mapa donde esta el enemigo
     */
    public Enemy(int x, int y, MapHandler map)
    {
        super(x, y);
        this.img = new Texture("man.png");
        this.hitbox = new Rectangle();
        hitbox.x = x;
        hitbox.y = y;
        speed = 1;
        direction = -1;
        this.map = map;
        font = new BitmapFont();
        font.setColor(Color.YELLOW);
        dialogue = new ArrayList<>();
        dialogue.add("hmm...");
        dialogue.add("No quiero regresar a casa...");
        dialogue.add("¡Ah, Se me olvidaba!");
        dialogue.add("He estado aquí todo el día y aún no termino");
        dialogue.add("Me duele la garganta");
        dialogue.add("Me duelen los pies");
        dialogue.add("Todavía no hay internet en la casa");
        dialogue.add("Okay, sodas papas y me largo");
        dialogue.add("Me tosieron en la cara");
        
        diagLine = "";
        willTalk = false;
        
        int spritesheetRows = 4;
        int spritesheetCols = 4;
        
        walkSheet = new Texture(Gdx.files.internal("enemy_spritesheet.png"));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / spritesheetCols,
                walkSheet.getHeight() / spritesheetRows);
        
        // Asignación del hitbox con el tamaño de la sprite
        // La spritesheet tiene 4 frames por fila y columna, sin separación
        hitbox.width = walkSheet.getWidth() / spritesheetCols;
        hitbox.height = walkSheet.getHeight() / spritesheetRows;
        
        covidOffsetX = (128 - (int) hitbox.width) / 2;
        covidOffsetY = (128 - (int) hitbox.height) / 2;
        covidBox = new SafeDistance(x - covidOffsetX, y - covidOffsetY, 128, 128);
        
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
        
    }
    
    /**
     * Regresa area no segura
     * @return
     */
    public Rectangle getCovidZone()
    {
        return covidBox.getHitbox();
    }
    
    /**
     * Revisa si un enemigo va a chocar con una pared.
     * @param x Diferencia en X (0) es no se mueve en X
     * @param y Diferencia en Y (0)es no se mueve en Y
     * @return
     */
    public boolean checkCollision(int x, int y)
    {
        Rectangle test = new Rectangle(hitbox);
        test.y += y;
        test.x += x;
        return  map.collidesOnLayer("Walls", test);
    }
    
    
    /**
     * Define moviemiento de en Y hasta chochar con una pared
     */
    private void moveUpDown()
    {
        boolean collision = checkCollision(0, speed *direction);
        if (collision)
        {
            direction *= -1;
        }
        y += speed * direction;
    }
    
    /**
     * Define movimiento en X hasta chocar con una pared
     */
    private void moveLeftRight()
    {
        boolean collision = checkCollision(speed*direction, 0);
        if (collision)
        {
            direction *= -1;
        }
        x += speed * direction;
    }
    
    protected void talk()
    {
        int index = (int)(Math.random() * ((dialogue.size())));
        diagLine = dialogue.get(index);
    }
    
    
    /**
     * Se corre cada frame.
     */
    public void tick()
    {
        moveLeftRight();
        hitbox.x = x;
        hitbox.y = y;
        covidBox.tick(x - covidOffsetX, y - covidOffsetY);
    }
    
    @Override
    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        
        if (lastX > x) animationState = 1;
        else if (lastX < x) animationState = 2;
        else if (lastY > y) animationState = 0;
        else if (lastY < y) animationState = 3;
        else animationState = -1;
        
        if (animationState == -1) {
            // El frame 0 es una posicion estatica
            batch.draw(walkAnimation.get(0).getKeyFrame(0), x, y);
        } else {
            // Dibuja el frame correspondiente a la animacion
            batch.draw(walkAnimation.get(animationState).getKeyFrame(stateTime, true), x, y);
        }
        
        covidBox.render(batch);
        
        if (willTalk)
            font.draw(batch, diagLine , x-32,y+80 );
        
        lastX = x;
        lastY = y;
    }
}
