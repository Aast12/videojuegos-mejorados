package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

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
    
    /**
     * El constructor para el men� de opciones.
     *
     * @param game
     */
    public Settings(final VMGame game) {
        this.game = game;
        
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
        easierButton.setPosition(Gdx.graphics.getWidth() / 2 - 120 - 25, Gdx.graphics.getHeight() / 2 - 100);
        easierButton.setSize(50,30);
        easierButton.addListener(
            new InputListener() { 
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(game.globals.index > 0) {
                        //index--;
                        game.globals.index--;
                    } else {
                        //index = 2;
                        game.globals.index = 2;
                    }
                    //game.globals.diff = difficulties[index];
                    return true;
                }
            }
        );
	stage.addActor(easierButton);
        
        this.harderButton = new TextButton(">", textButtonStyle);
        harderButton.setPosition(Gdx.graphics.getWidth() / 2 + 120 - 25, Gdx.graphics.getHeight() / 2 - 100);
        harderButton.setSize(50,30);
        harderButton.addListener(
            new InputListener() { 
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(game.globals.index < 2) {
                        //index++;
                        game.globals.index++;
                    } else {
                        //index = 0;
                        game.globals.index = 0;
                    }
                    //game.globals.diff = difficulties[index];
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
                    ScreenHandler.getInstance().showScreen(ScreenEnum.MAIN_MENU, game, 1);
                    return true;
                }
            }
        );
	stage.addActor(backButton);
        
        this.volumeUpButton = new TextButton("+", textButtonStyle);
        volumeUpButton.setPosition(Gdx.graphics.getWidth() / 2 + 120 - 25, Gdx.graphics.getHeight() / 2 - 150);
        volumeUpButton.setSize(50,30);
        volumeUpButton.addListener(
            new InputListener() { 
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(game.globals.musicVolume >= 100){
                        //currentMusicVol = 100;
                        game.globals.musicVolume = 100;
                    } else {
                        //currentMusicVol += 2;
                        game.globals.musicVolume += 2;
                    }
                    //game.globals.musicVolume = currentMusicVol;
                    return true;
                }
            }
        );
	stage.addActor(volumeUpButton);
        
        this.volumeDownButton = new TextButton("-", textButtonStyle);
        volumeDownButton.setPosition(Gdx.graphics.getWidth() / 2 - 120 - 25, Gdx.graphics.getHeight() / 2 - 150);
        volumeDownButton.setSize(50,30);
        volumeDownButton.addListener(
            new InputListener() { 
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if(game.globals.musicVolume <= 0){
                        //currentMusicVol = 0;
                        game.globals.musicVolume = 0;
                    } else {
                        //currentMusicVol -= 2;
                        game.globals.musicVolume -= 2;
                    }
                    //game.globals.musicVolume = currentMusicVol;                    
                    return true;
                }
            }
        );
	stage.addActor(volumeDownButton);
        // las configuraciones del juego por default
        // currentMusicVol = 50;
        // currentFxVol = 50;
        // currentDifficulty = difficulties[index];
        // currentMusicVol = game.globals.musicVolume;
    }

    @Override
    public void show() {
    }

    /**
     * Dibuja el menu de opciones y checa el estado de cada boton.
     *
     * @param delta
     */
    @Override
    public void render(float delta) {
        // dibujar materiales y botones
        //currentDifficulty = difficulties[index];
        batch.begin();
        batch.draw(background, 0, 0);
        font.draw(batch, "SETTINGS", Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 + 100);
        font.draw(batch, "Difficulty:", Gdx.graphics.getWidth() / 2 - 120 - 105, Gdx.graphics.getHeight() / 2 - 100 + 20);
        // font.draw(batch, currentDifficulty, Gdx.graphics.getWidth() / 2 - 20, Gdx.graphics.getHeight() / 2 - 100 + 20);
        font.draw(batch, game.globals.difficulties[game.globals.index], Gdx.graphics.getWidth() / 2 - 20, Gdx.graphics.getHeight() / 2 - 100 + 20);    
        font.draw(batch, "Music volume:", Gdx.graphics.getWidth() / 2 - 120 - 140, Gdx.graphics.getHeight() / 2 - 150 + 20);
        // font.draw(batch, Integer.toString(currentMusicVol), Gdx.graphics.getWidth() / 2 - 10, Gdx.graphics.getHeight() / 2 - 150 + 20);
        font.draw(batch, Integer.toString(game.globals.musicVolume), Gdx.graphics.getWidth() / 2 - 10, Gdx.graphics.getHeight() / 2 - 150 + 20);
        batch.end();
        stage.draw();
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
	    batch.dispose();
	    stage.dispose();
    }
}
