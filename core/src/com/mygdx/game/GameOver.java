package com.mygdx.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

		this.options = new LinkedList<>();
		this.buttonMaterial = new Texture("material1.png");
		Button save = new Button(334, 500, 128, 32, "SAVE", buttonMaterial);
		Button quit = new Button(334, 542, 128, 32, "QUIT", buttonMaterial);
		this.options.add(save);
		this.options.add(quit);
		gameWon = won;
		if (gameWon)
			this.background = new Texture("win_game.png");
		else
			this.background = new Texture("game_over.png");
		this.levelContinue = new Menu(game, "LEVEL START", this.options, background);

		this.font = new BitmapFont();
	}
    
	@Override
	public void show() {
	}

	@Override
	public void render(float f) {
		
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
