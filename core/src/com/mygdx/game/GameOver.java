package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import java.util.LinkedList;

public class GameOver implements Screen {
	private VMGame game; // para dibujar la pantalla
	private Menu levelContinue; // para implementar la interfaz
	private LinkedList<Button> options; // para accesar a los botones de esta pantalla
	private Texture buttonMaterial; // el material para los botones de esta pantalla
	private boolean visible; // to control screen visibility *REEMPLAZARLA POR EL HANDLER DE LA CAMARA*
	private Texture background; // el fondo de esta pantalla
	private OrthographicCamera camera; // para controlar visibilidad *PARA REEMPLAZAR bool visible*
	private BitmapFont font; // la fuente de esta pantalla *podemos cambiarla pq siempre es la misma para el juego*
	TextButton button;
	Stage stage;
	TextButtonStyle textButtonStyle;
	Skin skin;
	TextureAtlas buttonAtlas;
	private boolean gameWon;

	/**
	 * Inicaliza la pantalla de terminar el juego
	 * @param game 
	 * @param won 
	 */
        public GameOver(VMGame game, boolean won) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
		gameWon = won;
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

		if (gameWon)
		{
			this.background = new Texture("win_game.png");
			button = new TextButton("Continue", textButtonStyle);
		}
		else
		{
			this.background = new Texture("game_over.png");
			button = new TextButton("Restart", textButtonStyle);
		}
		this.levelContinue = new Menu(game, "LEVEL START", this.options, background);

		this.font = new BitmapFont();
		stage.addActor(button);
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean vis)
	{
		visible = vis;
	}

    
	@Override
	public void show() {
	}

	@Override
	public void render(float f) {
		stage.draw();
		
	}

	@Override
	public void resize(int i, int i1) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void pause() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void resume() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void hide() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void dispose() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
