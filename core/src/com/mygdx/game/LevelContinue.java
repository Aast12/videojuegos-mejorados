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
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;

/**
 * Una clase para el Overlay de la primera pantalla de juego, incluye insturcciones de controles
 * @author jeg99
 * @author Alam Sanchez
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
    public int nextLevel;
    /**
     * Constructor para el men� del level overlay.
     *
     * @param game
     */
    public LevelContinue(final VMGame game, int nextlvl) {
        this.game = game;
        nextLevel = nextlvl;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        this.batch = new SpriteBatch();
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttons.pack"));
        skin.addRegions(buttonAtlas);
        textButtonStyle = new TextButton.TextButtonStyle();
        font = new BitmapFont(Gdx.files.internal("font18.fnt"));
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("button-up");
        textButtonStyle.down = skin.getDrawable("button-down");
        textButtonStyle.checked = skin.getDrawable("button-checked");
        
        // this.background = new Texture("main_menu_background.png");
        
        this.continueButton = new TextButton("Continue", textButtonStyle);
        continueButton.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 - 140);
        continueButton.setHeight(32);
        continueButton.addListener(
            new InputListener() { 
                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    ScreenHandler.getInstance().showScreen(ScreenEnum.LEVEL, game, nextLevel);
                    return true;
                }
            }
        );
        
        
        this.saveButton = new TextButton("Save game", textButtonStyle);
        saveButton.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 - 170);
        saveButton.setHeight(32);
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
        
        
        this.quitButton = new TextButton("Quit game", textButtonStyle);
        quitButton.setPosition(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() / 2 - 200);
        quitButton.setHeight(32);
        quitButton.addListener(
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
        

        Table contentTable = new Table(skin);
        contentTable.setHeight(Gdx.graphics.getHeight());
        contentTable.setWidth(Gdx.graphics.getWidth());
        contentTable.setPosition(0f, 0f);
        contentTable.align(Align.top);
        Label headerLabel = new Label("LEVEL START", skin);
        headerLabel.setWrap(true);
        headerLabel.setAlignment(Align.center);
        Label instructionsLabel = new Label("¡Recoge todos los articulos de primera necesidad antes de que se te termine el tiempo!\nNo olvides respetar la sana distancia de los demas clientes.", skin);
        instructionsLabel.setFontScale(0.75f);
        instructionsLabel.setWrap(true);
        instructionsLabel.setAlignment(Align.left);
        Label controlsLabel = new Label("P: Pausar el juego\n W: Caminar hacia arriba\n S: Caminar hacia abajo\n A: Caminar hacia la izquierda\n D: Caminar hacia la derecha\n E: Recoger objeto\n SPACE BAR: Impulso", skin);
        controlsLabel.setFontScale(0.75f);
        controlsLabel.setWrap(true);
        controlsLabel.setAlignment(Align.center);

        contentTable.row().fillX();
        contentTable.add(headerLabel).height(32).expandX();
        contentTable.row().fillX();
        contentTable.add(instructionsLabel).pad(10, 64, 10, 64).expandX();
        contentTable.row().fillX();
        contentTable.add(controlsLabel).expandX();
        
        Table buttonsTable = new Table(skin);
        buttonsTable.align(Align.center);
        buttonsTable.row().fill();
        buttonsTable.add(continueButton).height(32);
        buttonsTable.row().fill();
        buttonsTable.add(saveButton).height(32);
        buttonsTable.row().fill();
        buttonsTable.add(quitButton).height(32);

        contentTable.row().fillX();
        contentTable.add(buttonsTable).expandX().pad(16, 0, 16, 0);
        
        stage.addActor(contentTable);
    }
    
    @Override
    public void show() {

    }

    /**
     * Para dibujar el menu de level overlay.
     *
     * @param delta
     */
    @Override
    public void render(float delta) { // aqui va la logica de los botones
        // dibujar instrucciones sobre los controles
        batch.begin();
        // batch.draw(background, 0, 0);
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
