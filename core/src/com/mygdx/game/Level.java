package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;

import java.util.ArrayList;
import java.util.Iterator;

public class Level implements Screen {
    private boolean win, lost;
    private int levelSeconds;
    float timeSeconds = 0f;
    float period = 1f;
    private ArrayList<ArrayList<Item>> items;
    private ArrayList<ItemGroup> group;
    OrthographicCamera camera;

    Music levelMusic; // musica de menu
    Player player; // entidad que controlara el usuario

    SpriteBatch batch; // servira para hacer render de los objetos
    TiledMap map;
    MapHandler mymap;
    VMGame game;
    HUD hud; // aqui se hace el rendering del layout dle HUD
    ArrayList<Enemy> enemies;
    Item item1; // aqui se guarda un item
    Texture end; // luego sera un atributo de la clase Level

    /**
     * Constructor de nivel
     * @param seconds segundos para que se acabe el contrarreloj
	 * @param g
     */
    public Level (int seconds, VMGame g) {
        this.win = false;
        this.lost = false;
        this.levelSeconds = seconds;
        levelMusic = Gdx.audio.newMusic(Gdx.files.internal("TheJ.mp3"));
        levelMusic.setVolume((float) 0.5);
        // this.items.addAll(items);
        camera = new OrthographicCamera(800, 600);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        mymap = new MapHandler("mapa.tmx", camera);
        map = mymap.map;
	this.game = g;
        batch = new SpriteBatch();

        hud = new HUD();
        hud.setTime(getLevelSeconds());
        hud.setHealth(98);
        hud.setDash(player.getDashes());
        hud.setGel(0);
	//Empezando aqui todo debe ser por nivel
	//TOOD: la inicializacion de player deberia depender del nivel
        player = new Player(800 / 2 - 64 / 2, 136, this, game);
	//Items tambien se deberian crear por nivel
        item1 = new Item(200, 300, 26, 26, 1);
        ArrayList<Item> itemsfirst = new ArrayList<Item>();
        itemsfirst.add(item1);
        items = new ArrayList<ArrayList<Item>>();
        items.add(itemsfirst);


	    //create enemies
	    enemies = new ArrayList<Enemy>();
	    Enemy man1 = new RandomEnemy(700, 600, mymap);
	    Enemy man2 = new RandomEnemy(600, 600, mymap);
	    Enemy man3 = new RandomEnemy(600, 500, mymap);
	    enemies.add(man1);
	    enemies.add(man2);
	    enemies.add(man3);

        end = new Texture("end.png");


    }

    public void constructGroup(ArrayList<ArrayList<Item>> list) {

        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).get(0).getPickable() == 1) {
                group.add(new ItemGroup(list.get(i).get(0).getImg(), 1, i));
                for (int j = 1; j < list.get(i).size(); j++) {
                    group.get(i).setCounter(j+1);
                }
            }
        }
    }

    public ArrayList<ItemGroup> getGroup() {return group;}

    /**
     * getter de los segundos
     * @return segundos
     */
    public int getLevelSeconds() {return levelSeconds;}

    /**
     * getter de si gano
     * @return booleano si gano
     */
    public boolean getWin() {return win;}

    /**
     * getter de si perdio
     * @return booleano de si perdio
     */
    public boolean getLost() {return lost;}

    /**
     * getter del mapa
     * @return regresa el mapa
     */
    public MapHandler getMap() {return mymap;}

    /**
     * getter la lista de objetos
     * @return la lista de objetos
     */
    public ArrayList<ArrayList<Item>> getItems() {return items;}

    /**
     * setter de win
     * @param win cambia si gano o no
     */
    public void setWin(boolean win) { this.win = win;}

    /**
     * setter de lose
     * @param lost cambia si perdio o no
     */
    public void setLost(boolean lost) { this.lost = lost;}

    /**
     * checa si el usuario ha perdido si se acabo el tiempo
     * @return booleano de si perdio o no
     */
    public boolean checkLost() {
        /**
         * Se hace el contrarreloj y permite perder por tiempo
         */
        timeSeconds += Gdx.graphics.getRawDeltaTime();
        if(timeSeconds > period){
            timeSeconds-=period;
            levelSeconds--;
            if (levelSeconds <= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * hace el cheqeo de si perdio
     */
    public void tick() {
        lost = checkLost();
    }

    /**
     * renderiza los objetos
     * @param batch renderizador de dibujo
     */
    public void render(SpriteBatch batch) {
        for (int i = 0; i < items.size(); i++ ) {
            for (int j = 0; j < items.get(i).size(); j++) {
                if (items.get(i).get(j).getPickable() != 0) {
                    items.get(i).get(j).render(batch);
                }
            }
        }
    }

	@Override
	public void show() {
	}

	@Override
	public void render(float f) {
		
                levelMusic.play();

            camera.position.x = player.x + player.getHitbox().width / 2f;
            camera.position.y = player.y + player.getHitbox().height / 2f;
            camera.update();

            mymap.render(batch);
            batch.begin();
            
		// Sprites Render y tick
		for (Iterator<Enemy> it = enemies.iterator(); it.hasNext();) {
			Enemy e = it.next();
			e.tick();
		}
	        tick();
	        player.tick();

            player.render(batch);
		for (Iterator<Enemy> it = enemies.iterator(); it.hasNext();) {
			Enemy e = it.next();
			e.render(batch);
		}
	        render(batch);
            batch.draw(end, 128, 596);

            //batch.draw;
            batch.end();

            // HUD Render
            hud.stage.act(Gdx.graphics.getDeltaTime());
            hud.stage.draw();

            hud.setHealth(player.getHealth());
            hud.setDash(player.getDashes());
            hud.setTime(getLevelSeconds());
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
		batch.dispose();
		// map.dispose();
		mymap.dispose();
		//font.dispose();
		hud.stage.dispose();
	}
}