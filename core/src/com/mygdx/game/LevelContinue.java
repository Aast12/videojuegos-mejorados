package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.LinkedList;

public class LevelContinue implements Screen {

    private VMGame game; // para dibujar la pantalla
    private Menu levelContinue; // para implementar la interfaz
    private LinkedList<Button> options; // para accesar a los botones de esta pantalla
    private Texture buttonMaterial; // el material para los botones de esta pantalla
    private boolean visible; // to control screen visibility *REEMPLAZARLA POR EL HANDLER DE LA CÁMARA*
    private Texture background; // el fondo de esta pantalla
    private OrthographicCamera camera; // para controlar visibilidad *PARA REEMPLAZAR bool visible*
    private BitmapFont font; // la fuente de esta pantalla *podemos cambiarla pq siempre es la misma para el juego*

    /**
     * Constructor para el menú del level overlay.
     *
     * @param game
     */
    public LevelContinue(VMGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        this.options = new LinkedList<Button>();
        this.buttonMaterial = new Texture("material1.png");
        Button save = new Button(334, 500, 128, 32, "SAVE (under development)", buttonMaterial);
        Button quit = new Button(334, 542, 128, 32, "QUIT", buttonMaterial);
        this.options.add(save);
        this.options.add(quit);
        this.background = new Texture("main_menu_background.png");
        this.levelContinue = new Menu(game, "LEVEL START", this.options, background);

        this.font = new BitmapFont();
    }

    public Menu getLevelContinue() {
        return levelContinue;
    }

    @Override
    public void show() {

    }

    /**
     * Para dibujar el menú de level overlay.
     *
     * @param delta
     */
    @Override
    public void render(float delta) { // aquí va la lógica de los botones
        levelContinue.render(delta); // dibujar materiales y botones

        // dibujar instrucciones sobre los controles
        game.font.draw(game.batch, "W: Move up", 334, 460);
        game.font.draw(game.batch, "S: Move down", 334, 430);
        game.font.draw(game.batch, "A: Move left", 334, 400);
        game.font.draw(game.batch, "D: Move right", 334, 370);
        game.font.draw(game.batch, "E: Pick object", 334, 340);
        game.font.draw(game.batch, "SPACE BAR: Dash", 334, 310);

        /*
        Boton 0: save
        Boton 1: quit
         */
        // para save
        if (Gdx.input.isTouched() && this.getLevelContinue().getOptions().get(0).getBox().contains(Gdx.input.getX(), Gdx.input.getY())) {
            // guardar partida
        }
        // para quit
        if (Gdx.input.isTouched() && this.getLevelContinue().getOptions().get(1).getBox().contains(Gdx.input.getX(), Gdx.input.getY())) {
            // cambiar pantalla a MainMenu
            getLevelContinue().setVisible(false);
            game.mainMenu.getMenu().setVisible(true);
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
