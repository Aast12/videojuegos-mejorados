package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.LinkedList;

public class MainMenu implements Screen {

    private VMGame game;
    private Menu menu;
    private LinkedList<Button> options;
    private Texture buttonMaterial;
    private boolean visible;
    private Texture background;
    private OrthographicCamera camera;
    private BitmapFont font;

    public MainMenu(VMGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        this.options = new LinkedList<Button>();
        this.buttonMaterial = new Texture("material1.png");
        Button start = new Button(60, 450, 128, 32, "START", buttonMaterial);
        Button load = new Button(60, 485, 128, 32, "LOAD", buttonMaterial);
        Button options = new Button(60, 520, 128, 32, "OPTIONS", buttonMaterial);
        Button exit = new Button(60, 555, 128, 32, "EXIT", buttonMaterial);
        this.options.add(start);
        this.options.add(load);
        this.options.add(options);
        this.options.add(exit);
        this.background = new Texture("main_menu_background.png");
        this.menu = new Menu(game, "THE GAME", this.options, background);

        this.font = new BitmapFont();
    }

    public Menu getMenu() {
        return menu;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        menu.render(delta);
        /*
        Boton 0: start
        Boton 1: load
        Boton 2: options
        Boton 3: quit
         */
        if (Gdx.input.isTouched() && this.getMenu().getOptions().get(0).getBox().contains(Gdx.input.getX(), Gdx.input.getY())) {
            this.getMenu().setVisible(false);
        }
        if (Gdx.input.isTouched() && this.getMenu().getOptions().get(1).getBox().contains(Gdx.input.getX(), Gdx.input.getY())) {

        }
        if (Gdx.input.isTouched() && this.getMenu().getOptions().get(2).getBox().contains(Gdx.input.getX(), Gdx.input.getY())) {
            game.settings.getSettings().setVisible(true);
            getMenu().setVisible(false);
        }
        if (Gdx.input.isTouched() && this.getMenu().getOptions().get(3).getBox().contains(Gdx.input.getX(), Gdx.input.getY())) {
            Gdx.app.exit();
        }
    }

    @Override
    public void resize(int width, int height) {

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
