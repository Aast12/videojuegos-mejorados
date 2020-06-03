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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Align;

public class GameWon implements Screen {
    private VMGame game; // para dibujar la pantalla
    private Texture background; // el fondo de esta pantalla
    private OrthographicCamera camera; // para controlar visibilidad *PARA REEMPLAZAR bool visible*
    private BitmapFont font; // la fuente de esta pantalla *podemos cambiarla pq siempre es la misma para el juego*
    TextButton button;
    Stage stage;
    TextButtonStyle textButtonStyle;
    Skin skin;
    TextureAtlas buttonAtlas;
    SpriteBatch batch;
    
    /**
     * Inicaliza la pantalla de terminar el juego
     * @param game
     */
    public GameWon(final VMGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("buttons/buttons.pack"));
        skin.addRegions(buttonAtlas);
        font = new BitmapFont();
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("button-up");
        textButtonStyle.down = skin.getDrawable("button-down");
        textButtonStyle.checked = skin.getDrawable("button-checked");
        batch = new SpriteBatch();
        
        this.background = new Texture("win_screen.png");
        button = new TextButton("Reiniciar", textButtonStyle);
        button.addListener(new InputListener()
        {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button)
            {
                
            }
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
            {
                ScreenHandler.getInstance().showScreen(ScreenEnum.MAIN_MENU, game, 1);
                return true;
            }
        });

        
        Table contentTable = new Table(skin);
        contentTable.setHeight(Gdx.graphics.getHeight());
        contentTable.setWidth(Gdx.graphics.getWidth());
        contentTable.setPosition(0f, 0f);
        contentTable.align(Align.bottomLeft);
        
        Label scoreLabel = new Label("Puntaje : " + Integer.toString(game.globals.totalScore), skin);
        contentTable.row();
        contentTable.add(scoreLabel).pad(10);
        contentTable.row();
        contentTable.add(button).pad(10);

        stage.addActor(contentTable);
    }
    
    @Override
    public void show() {
    }
    
    @Override
    public void render(float f) {
        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();
        stage.draw();
        
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
        stage.dispose();
        batch.dispose();
    }
    
}