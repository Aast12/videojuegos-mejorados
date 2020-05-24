package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

/**
 * Clase para la pantalla principal del juego
 * @author jeg99
 */
public class MainMenu implements Screen {
    private VMGame game;
    private SpriteBatch batch; // para dibujar la pantalla
    private TextButton startButton;
    private TextButton optionsButton;
    private TextButton loadButton;
    private TextButton exitButton;
    private Stage stage;
    private TextButtonStyle textButtonStyle;
    private Skin skin;
    private TextureAtlas buttonAtlas;
    private Texture background;
    private OrthographicCamera camera;
    private BitmapFont font;
    Music music; // musica de menu
    
    /**
     * Inicializa la pantalla principal del juego
     * @param game 
     */
    public MainMenu(final VMGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        
        this.batch = new SpriteBatch();
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        this.skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttons.pack"));
        skin.addRegions(buttonAtlas);
        this.font = new BitmapFont();
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("button-up");
        textButtonStyle.down = skin.getDrawable("button-down");
        textButtonStyle.checked = skin.getDrawable("button-checked");
        this.background = new Texture("main_menu_background.png");
        this.startButton = new TextButton("Start game", textButtonStyle);
        startButton.setPosition(30, Gdx.graphics.getHeight() / 2 - 200);
        startButton.setSize(100,30);
        startButton.addListener(
            new InputListener() { 
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    ScreenHandler.getInstance().showScreen(ScreenEnum.LEVEL_OVERLAY, game);
                    return true;
                }
            }
        );
	stage.addActor(startButton);
        
        this.loadButton = new TextButton("Load game", textButtonStyle);
        loadButton.setPosition(30, Gdx.graphics.getHeight() / 2 - 230);
        loadButton.setSize(100,30);
        loadButton.addListener(
            new InputListener() { 
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    ScreenHandler.getInstance().showScreen(ScreenEnum.LEVEL, game);
                    return true;	
                }
            }
        );
	stage.addActor(loadButton);
        
        this.optionsButton = new TextButton("Options", textButtonStyle);
        optionsButton.setPosition(30, Gdx.graphics.getHeight() / 2 - 260);
        optionsButton.setSize(100,30);
        optionsButton.addListener(
            new InputListener() { 
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    ScreenHandler.getInstance().showScreen(ScreenEnum.OPTIONS_MENU, game);
                    return true;	
                }
            }
        );
	stage.addActor(optionsButton);
        
        this.exitButton = new TextButton("Exit", textButtonStyle);
        exitButton.setPosition(30, Gdx.graphics.getHeight() / 2 - 290);
        exitButton.setSize(100,30);
        exitButton.addListener(
            new InputListener() { 
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    //ScreenHandler.getInstance().showScreen(ScreenEnum.OPTIONS_MENU, game);
                    Gdx.app.exit();
                    return true;	
                }
            }
        );
	stage.addActor(exitButton);

        music = Gdx.audio.newMusic(Gdx.files.internal("Manu.ogg"));
        music.setVolume((float) 0.25);

        music.setLooping(true);
        music.play();
        
    }

    @Override
    public void show() {

    }
    
    /**
     * Una función para dibujar esta pantalla
     * @param delta 
     */
    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(background, 0, 0);
        this.font.draw(this.batch, "THE GAME", Gdx.graphics.getWidth() / 2 - 60, Gdx.graphics.getBackBufferHeight()/ 2 + 100);
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
    
    /**
     * Función para cuando esta pantalla se destruya
     */
    @Override
    public void dispose() {
	music.stop();
    }
}
