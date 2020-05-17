package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import java.util.LinkedList;

public class Settings implements Screen {

    private VMGame game; // para dibujar la pantalla
    private Menu settings; // para implementar la interfaz
    private LinkedList<Button> options; // para accesar a los botones de esta pantalla
    private Texture buttonMaterial; // el material para los botones de esta pantalla
    private boolean visible; // to control screen visibility *REEMPLAZARLA POR EL HANDLER DE LA CÁMARA*
    private Texture background; // el fondo de esta pantalla
    private OrthographicCamera camera; // para controlar visibilidad *PARA REEMPLAZAR bool visible*
    private BitmapFont font; // la fuente de esta pantalla *podemos cambiarla pq siempre es la misma para el juego*

    // variables de configuracion
    private int currentMusicVol;
    private int currentFxVol;
    private String[] difficulties = {"easy", "normal", "hard"};
    private String currentDifficulty;

    // para el slider de volumen
    private Button bar;
    private Button slider;

    // para la seleccion de dificultad
    private Button easier;
    private Button harder;

    public Settings(VMGame game) {
        this.game = game;

        // la camara para esta pantalla
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        // configurando botones de la pantalla
        this.options = new LinkedList<Button>();
        this.buttonMaterial = new Texture("material1.png");
        Button musicVolume = new Button(200, 276, 128, 32, "MUSIC", buttonMaterial);
        Button fxVolume = new Button(200, 338, 128, 32, "FX", buttonMaterial);
        Button difficulty = new Button(200, 400, 128, 32, "DIFFICULTY", buttonMaterial);
        Button back = new Button(6, 568, 128, 32, "BACK", buttonMaterial);
        this.options.add(musicVolume);
        this.options.add(fxVolume);
        this.options.add(difficulty);
        this.options.add(back);

        // generando el menu para esta pantalla
        this.background = new Texture("main_menu_background.png");
        this.settings = new Menu(game, "OPTIONS", this.options, background);

        // fuente
        this.font = new BitmapFont();

        // las configuraciones del juego por default
        currentMusicVol = 100;
        currentFxVol = 100;
        currentDifficulty = difficulties[1];

        bar = new Button(340, 276, 384, 32, "", buttonMaterial);
        slider = new Button(340, 276, 32, 32, "", buttonMaterial);

    }

    public Menu getSettings() {
        return settings;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        settings.render(delta); // dibujar materiales y botones

        /*
        Boton 0: volumen de la música
        Boton 1: volumen de los efectos
        Boton 2: dificultad del juego
        Boton 3: regresar al menu principal
         */
        // para music
        if (Gdx.input.isTouched() && this.getSettings().getOptions().get(0).getBox().contains(Gdx.input.getX(), Gdx.input.getY())) {
            // TODO
        }
        // para fx
        if (Gdx.input.isTouched() && this.getSettings().getOptions().get(1).getBox().contains(Gdx.input.getX(), Gdx.input.getY())) {
            // TODO
        }
        // para difficulty
        if (Gdx.input.isTouched() && this.getSettings().getOptions().get(2).getBox().contains(Gdx.input.getX(), Gdx.input.getY())) {
            // TODO
        }
        if (Gdx.input.isTouched() && this.getSettings().getOptions().get(3).getBox().contains(Gdx.input.getX(), Gdx.input.getY())) {
            settings.setVisible(false);
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
