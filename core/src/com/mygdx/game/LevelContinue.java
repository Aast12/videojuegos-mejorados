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

/**
 * Una clase para el Overlay de la primera pantalla de juego, incluye insturcciones de controles
 * @author jeg99
 */
public class LevelContinue implements Screen {

    private VMGame game; // para dibujar la pantalla
    private SpriteBatch batch; // para dibujar la pantalla
    private TextButton continueButton;
    private TextButton saveButton;
    private TextButton quitButton;
    private Stage stage;
    private TextButton.TextButtonStyle textButtonStyle;
    private Skin skin;
    private TextureAtlas buttonAtlas;
    private Texture background; // el fondo de esta pantalla
    private OrthographicCamera camera; // para controlar visibilidad *PARA REEMPLAZAR bool visible*
    private BitmapFont font; // la fuente de esta pantalla *podemos cambiarla pq siempre es la misma para el juego*

    /**
     * Constructor para el men� del level overlay.
     *
     * @param game
     */
    public LevelContinue(final VMGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        this.batch = new SpriteBatch();
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        this.skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttons.pack"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        font = new BitmapFont();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("button-up");
        textButtonStyle.down = skin.getDrawable("button-down");
        textButtonStyle.checked = skin.getDrawable("button-checked");
        
        this.background = new Texture("main_menu_background.png");
        
        this.continueButton = new TextButton("Continue", textButtonStyle);
        continueButton.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 - 140);
        continueButton.setSize(100,30);
        continueButton.addListener(
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
        stage.addActor(continueButton);
        
        this.saveButton = new TextButton("Save game", textButtonStyle);
        saveButton.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 - 170);
        saveButton.setSize(100,30);
        saveButton.addListener(
            new InputListener() { 
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            }
        );
        stage.addActor(saveButton);
        
        this.quitButton = new TextButton("Quit game", textButtonStyle);
        quitButton.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 - 200);
        quitButton.setSize(100,30);
        quitButton.addListener(
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
        stage.addActor(quitButton);
        
    }

    @Override
    public void show() {

    }

    /**
     * Para dibujar el men� de level overlay.
     *
     * @param delta
     */
    @Override
    public void render(float delta) { // aqu� va la l�gica de los botones
        // dibujar instrucciones sobre los controles
        batch.begin();
        batch.draw(background, 0, 0);
        font.draw(batch, "LEVEL START:\n "
 + "                      ¡Recoge todos los artículos de primera necesidad antes de que se te termine el tiempo!\n"
 + "                      No olvides respetar la sana distancia de los demás clientes.", 60, 500);
        font.draw(batch, "W: Move up\n S: Move down\n A: Move left\n D: Move right\n E: Pick object\n SPACE BAR: Dash", 300, 420);
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

    }
}
