package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import java.util.LinkedList;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class Settings implements Screen {

    private VMGame game; // para dibujar la pantalla
    private SpriteBatch batch; // para dibujar la pantalla
    private TextButton easierButton;
    private TextButton harderButton;
    private TextButton currDiff;
    private TextButton backButton;
    private TextButton volumeUpButton;
    private TextButton volumeDownButton;
    private TextButton currVol;
    private Stage stage;
    private TextButton.TextButtonStyle textButtonStyle;
    private Skin skin;
    private TextureAtlas buttonAtlas;
    
    private Texture background;
    private OrthographicCamera camera;
    private BitmapFont font;
    
    // slider
    private Slider.SliderStyle sliderStyle;
    private Skin sliderSkin;
    private TextureAtlas sliderAtlas;
    private Slider slider;
    
    // variables de configuracion
    String musicVol;
    private int currentMusicVol;
    private int currentFxVol;
    private String[] difficulties = {"easy", "normal", "hard"};
    private int index = 1;
    private String currentDifficulty;
    /*
    private Menu settings; // para implementar la interfaz
    private LinkedList<Button> options; // para accesar a los botones de esta pantalla
    private Texture buttonMaterial; // el material para los botones de esta pantalla
    private boolean visible; // to control screen visibility *REEMPLAZARLA POR EL HANDLER DE LA CAMARA*
    private Texture background; // el fondo de esta pantalla
    private OrthographicCamera camera; // para controlar visibilidad *PARA REEMPLAZAR bool visible*
    private BitmapFont font; // la fuente de esta pantalla *podemos cambiarla pq siempre es la misma para el juego*

    private Texture backButtonMaterial;

    // para el slider de volumen
    private Texture barMaterial;
    private Texture sliderMaterial;
    private Button bar;
    private Button slider;

    // para la seleccion de dificultad
    private Texture difficultyButton;
    private Button easier;
    private Button harder;
    */
    /**
     * El constructor para el men� de opciones.
     *
     * @param game
     */
    public Settings(final VMGame game) {
        this.game = game;
        
        //skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        
        
        // la camara para esta pantalla
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        
        this.batch = new SpriteBatch();
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        this.skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttons.pack"));
        skin.addRegions(buttonAtlas);
        this.font = new BitmapFont();
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("button-up");
        textButtonStyle.down = skin.getDrawable("button-down");
        textButtonStyle.checked = skin.getDrawable("button-checked");
        
        this.background = new Texture("main_menu_background.png");

        this.easierButton = new TextButton("<", textButtonStyle);
        easierButton.setPosition(Gdx.graphics.getWidth() / 2 - 120 - 25, Gdx.graphics.getHeight() / 2 - 200);
        easierButton.setSize(50,30);
        easierButton.addListener(
            new InputListener() { 
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(index > 0) {
                        index--;
                    } else {
                        index = 2;
                    }
                    System.out.print(difficulties[index]);
                    return true;
                }
            }
        );
	stage.addActor(easierButton);
        
        this.harderButton = new TextButton(">", textButtonStyle);
        harderButton.setPosition(Gdx.graphics.getWidth() / 2 + 120 - 25, Gdx.graphics.getHeight() / 2 - 200);
        harderButton.setSize(50,30);
        harderButton.addListener(
            new InputListener() { 
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(index < 2) {
                        index++;
                    } else {
                        index = 0;
                    }
                    System.out.print(difficulties[index]);
                    return true;
                }
            }
        );
	stage.addActor(harderButton);
        
        this.backButton = new TextButton("Back", textButtonStyle);
        backButton.setPosition(30, Gdx.graphics.getHeight() / 2 - 290);
        backButton.setSize(100,30);
        backButton.addListener(
            new InputListener() { 
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    ScreenHandler.getInstance().showScreen(ScreenEnum.MAIN_MENU, game);
                    return true;
                }
            }
        );
	stage.addActor(backButton);
        
        this.volumeUpButton = new TextButton("+", textButtonStyle);
        volumeUpButton.setPosition(Gdx.graphics.getWidth() / 2 + 120 - 25, Gdx.graphics.getHeight() / 2 - 250);
        volumeUpButton.setSize(50,30);
        volumeUpButton.addListener(
            new InputListener() { 
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(currentMusicVol >= 100){
                        currentMusicVol = 100;
                    } else {
                        currentMusicVol += 2;
                    }
                    System.out.print(currentMusicVol);
                    System.out.print('\n');
                    return true;
                }
            }
        );
	stage.addActor(volumeUpButton);
        
        this.volumeDownButton = new TextButton("-", textButtonStyle);
        volumeDownButton.setPosition(Gdx.graphics.getWidth() / 2 - 120 - 25, Gdx.graphics.getHeight() / 2 - 250);
        volumeDownButton.setSize(50,30);
        volumeDownButton.addListener(
            new InputListener() { 
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(currentMusicVol <= 0){
                        currentMusicVol = 0;
                    } else {
                        currentMusicVol -= 2;
                    }
                    System.out.print(currentMusicVol);
                    System.out.print('\n');
                    return true;
                }
            }
        );
	stage.addActor(volumeDownButton);
        
        /*
        sliderAtlas = new TextureAtlas("clean-crispy/skin/clean-crispy-ui.pack");
        sliderSkin.addRegions(sliderAtlas);
        sliderStyle = new SliderStyle();
        slider = new Slider(0, 10, 1, false, sliderStyle);
        slider.setPosition(Gdx.graphics.getBackBufferWidth()/2, Gdx.graphics.getBackBufferHeight()/2);
	slider.setAnimateDuration(0.3f);
        slider.addListener(
            new ChangeListener() {
                @Override
                public void changed (ChangeEvent event, Actor actor) {
                    Gdx.app.log("UITest", "slider: " + slider.getValue());
                }
            }
        );
        stage.addActor(slider);
        */
        
        // configurando botones de la pantalla
        /*
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
        */
        // las configuraciones del juego por default
        currentMusicVol = 100;
        currentFxVol = 100;
        currentDifficulty = difficulties[index];
        /*
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
        */
    }

    /**
     * Regresa el men� que usa para construirse.
     *
     * @return settings
    
    public Menu getSettings() {
        return settings;
    }
    *  
    */

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
        //settings.render(delta); // dibujar materiales y botones
        batch.begin();
        font.draw(batch, currentDifficulty, 500, 195);
        batch.draw(background, 0, 0);
        batch.end();
        stage.draw();
        /*
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
        Boton 0: volumen de la m�sica
        Boton 1: volumen de los efectos
        Boton 2: dificultad del juego
        Boton 3: regresar al menu principal
        Boton 4: -
        Boton 5: SLIDER
        Boton 6: +dificil
        Boton 7: -dificil
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
            //game.mainMenu.getMenu().setVisible(true);
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
        */
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
