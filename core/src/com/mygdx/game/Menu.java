/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.LinkedList;

/**
 *
 * @author JeG99
 */
public class Menu implements Screen {

    private VMGame game;
    private String title;
    private LinkedList<Button> options;
    private boolean visible;
    private Texture background;
    private SpriteBatch batch;
    //OrthographicCamera camera;

    public Menu(VMGame game, String title, LinkedList<Button> options, Texture background) {
        this.title = title;
        this.game = game;
        this.options = options;
        this.background = background;

        //camera = new OrthographicCamera();
        //camera.setToOrtho(false, 800, 600);
    }

    public LinkedList<Button> getOptions() {
        return options;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setBackground(Texture background) {
        this.background = background;
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //camera.update();
        //game.batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0);
        for (Button button : options) {
            button.render(batch);
            game.font.draw(batch, button.getMessage(), button.getBox().getX() + 20, 600 - button.getBox().getY() - 5);
        }
        game.font.draw(batch, title, 350, 520);
        batch.end();
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
