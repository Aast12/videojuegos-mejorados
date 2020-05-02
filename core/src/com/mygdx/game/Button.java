/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import static com.brashmonkey.spriter.Spriter.dispose;

/**
 *
 * @author JeG99
 */
public class Button {

    private int x;
    private int y;
    private int width;
    private int height;
    private String message;
    private Texture material;
    private boolean pressed;
    private Rectangle box;

    public Button(int x, int y, int width, int height, String message, Texture material) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.message = message;
        this.material = material;
        box = new Rectangle(x, y, width, height);
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    public String getMessage() {
        return message;
    }

    public Rectangle getBox() {
        return box;
    }

    public void render(SpriteBatch batch) {
        batch.draw(material, x, y);

        if (Gdx.input.isTouched()) {
            if (box.contains(Gdx.input.getX(), Gdx.input.getY())) {
                pressed = true;

                //game.setScreen(new GameScreen(game));
                //dispose();
            }
        }

    }

}
