package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import java.util.LinkedList;

public class Settings implements Screen {

    private VMGame game; // para dibujar la pantalla
    private Menu settings; // para implementar la interfaz
    private LinkedList<Button> options; // para accesar a los botones de esta pantalla
    private Texture buttonMaterial; // el material para los botones de esta pantalla
    private boolean visible; // to control screen visibility *REEMPLAZARLA POR EL HANDLER DE LA CAMARA*
    private Texture background; // el fondo de esta pantalla
    private OrthographicCamera camera; // para controlar visibilidad *PARA REEMPLAZAR bool visible*
    private BitmapFont font; // la fuente de esta pantalla *podemos cambiarla pq siempre es la misma para el juego*

    private Texture backButtonMaterial;

    // variables de configuracion
    private int currentMusicVol;
    private int currentFxVol;
    private String[] difficulties = {"easy", "normal", "hard"};
    private int index = 1;
    private String currentDifficulty;

    // para el slider de volumen
    private Texture barMaterial;
    private Texture sliderMaterial;
    private Button bar;
    private Button slider;

    // para la seleccion de dificultad
    private Texture difficultyButton;
    private Button easier;
    private Button harder;

    private SpriteBatch batch;

    /**
     * El constructor para el men� de opciones.
     *
     * @param game
     */
    public Settings(VMGame game) {
        this.game = game;

        // la camara para esta pantalla
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        // configurando botones de la pantalla
        this.options = new LinkedList<Button>();
        this.buttonMaterial = new Texture("material1.png");
        this.backButtonMaterial = new Texture("back.png");
        Button musicVolume = new Button(200, 276, 128, 32, "MUSIC (buggy)", buttonMaterial);
        Button fxVolume = new Button(200, 338, 128, 32, "FX (buggy)", buttonMaterial);
        Button difficulty = new Button(200, 400, 128, 32, "DIFFICULTY (buggy)", buttonMaterial);
        Button back = new Button(6, 568, 70, 32, "BACK", backButtonMaterial);
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
        currentDifficulty = difficulties[index];

        // slider configurations
        barMaterial = new Texture("bar.png");
        sliderMaterial = new Texture("slider.png");
        bar = new Button(340, 276, 384, 32, " ", barMaterial);
        slider = new Button(340, 276, 32, 32, " ", sliderMaterial);
        this.options.add(bar);
        this.options.add(slider);

        // difficulty selector configs
        difficultyButton = new Texture("difficultyButton.png");
        easier = new Button(340, 400, 128, 32, "Easier", difficultyButton);
        harder = new Button(654, 400, 128, 32, "Harder!", difficultyButton);
        this.options.add(easier);
        this.options.add(harder);
    }

    /**
     * Regresa el men� que usa para construirse.
     *
     * @return settings
     */
    public Menu getSettings() {
        return settings;
    }

    @Override
    public void show() {

    }

    /**
     * Dibuja el men� de opciones y checa el estado de cada boton.
     *
     * @param delta
     */
    @Override
    public void render(float delta) {
        settings.render(delta); // dibujar materiales y botones
        game.font.draw(batch, currentDifficulty, 500, 195);
        if (index > 2) {
            index = 0;
        } else if (index < 0) {
            index = 2;
        }

        if (slider.getX() >= 690) {
            slider.getBox().setPosition(692, 276);
        } else if (slider.getX() <= 340) {
            slider.getBox().setPosition(330, 276);
        }

        /*
        Boton 0: volumen de la m�sica
        Boton 1: volumen de los efectos
        Boton 2: dificultad del juego
        Boton 3: regresar al menu principal
        Boton 4: -
        Boton 5: SLIDER
        Boton 6: +dificil
        Boton 7: -dificil
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
        if (Gdx.input.isTouched() && this.getSettings().getOptions().get(4).getBox().contains(Gdx.input.getX(), Gdx.input.getY())) {
            // NO HACERLE NADA
        }
        if (Gdx.input.isTouched() && this.getSettings().getOptions().get(5).getBox().contains(Gdx.input.getX(), Gdx.input.getY())) {
            slider.getBox().setPosition(Gdx.input.getX(), 276);
        }
        if (Gdx.input.isTouched() && this.getSettings().getOptions().get(6).getBox().contains(Gdx.input.getX(), Gdx.input.getY())) {
            currentDifficulty = difficulties[index++];
        }
        if (Gdx.input.isTouched() && this.getSettings().getOptions().get(7).getBox().contains(Gdx.input.getX(), Gdx.input.getY())) {
            currentDifficulty = difficulties[index--];
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
