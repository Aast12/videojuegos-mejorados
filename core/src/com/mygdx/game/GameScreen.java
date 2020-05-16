/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 *
 * @author JeG99
 */
public class GameScreen implements Screen {

    private VMGame game;
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

    Music music;

    public GameScreen(VMGame game) {
        this.game = game;

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float f) {

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
