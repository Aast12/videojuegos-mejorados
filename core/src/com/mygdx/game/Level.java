package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Level implements Screen {
    private boolean win, lost;
    private int levelSeconds, points;
    float timeSeconds = 0f;
    float period = 1f;
    private HashMap<String, ArrayList<Item>> items;
    private HashMap<String, ItemGroup> group;
    OrthographicCamera camera;

    Music levelMusic; // musica de menu
    Player player; // entidad que controlara el usuario

    SpriteBatch batch; // servira para hacer render de los objetos
    TiledMap map;
    MapHandler mymap;
    VMGame game;
    HUD hud; // aqui se hace el rendering del layout dle HUD
    ArrayList<Enemy> enemies;
    Texture end; // luego sera un atributo de la clase Level

    Rectangle endpoint;

    /**
     * Constructor de nivel
     * @param seconds segundos para que se acabe el contrarreloj
	 * @param g
     */
    public Level (int seconds, VMGame g) {
        this.win = false;
        this.lost = false;
        this.levelSeconds = seconds;
        this.points = 0;
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

	    //Empezando aqui todo debe ser por nivel
	    //TOOD: la inicializacion de player deberia depender del nivel
        player = new Player(800 / 2 - 64 / 2, 136, this, game);
        
        
        // Inicialización del punto de salida
        float endX = Float.parseFloat(mymap.getObjectFromLayer("Other", "Exit").getProperties().get("x").toString());
        float endY = Float.parseFloat(mymap.getObjectFromLayer("Other", "Exit").getProperties().get("y").toString());
        endpoint = new Rectangle(endX, endY, 64, 64);
        
        items = new HashMap<String, ArrayList<Item>>();
        //Incialización de items desde los datos del mapa
        Lambda genItems = (mp) -> {
            MapProperties curr = (MapProperties) mp;
            int x = (int) Float.parseFloat(curr.get("x").toString());
            int y = (int) Float.parseFloat(curr.get("y").toString());
            String filename = curr.get("filename").toString();

            if (items.containsKey(filename)) {
                items.get(filename).add(new Item(x, y, 26, 26, filename, 1));
            }
            else {
                ArrayList<Item> itemsfirst = new ArrayList<Item>();
                itemsfirst.add(new Item(x, y, 26, 26, filename, 1));
                items.put(filename, itemsfirst);
            }
        };
        mymap.applyOnLayerObjects("Items", genItems, true);

        group = new HashMap<String, ItemGroup>();
        constructGroup(items);
        
        //Incialización de enemigos desde los datos del mapa
        enemies = new ArrayList<Enemy>();
        Lambda genEnemies = (mp) -> {
            MapProperties curr = (MapProperties) mp;
            int x = (int) Float.parseFloat(curr.get("x").toString());
            int y = (int) Float.parseFloat(curr.get("y").toString());
            enemies.add(new RandomEnemy(x, y, mymap));
        };

        mymap.applyOnLayerObjects("Enemies", genEnemies, true);

        end = new Texture("end.png");

        hud = new HUD(group);
        hud.setTime(levelSeconds);
        hud.setHealth(98);
        hud.setDash(player.getDashes());
        hud.setGel(0);


    }

    public void constructGroup(HashMap<String, ArrayList<Item>> list) {

        for(String key : list.keySet()) {
            if (list.get(key).get(0).getPickable() == 1) {
                group.put(key, new ItemGroup(list.get(key).get(0).getImg(), 1, key));
                for (int j = 1; j < list.get(key).size(); j++) {
                    group.get(key).setCounter(j + 1);
                }
            }
        }
    }

    public HashMap<String, ItemGroup> getGroup() {return group;}

    /**
     * getter de los segundos
     * @return segundos
     */
    public int getLevelSeconds() {return levelSeconds;}

    /**
     * getter de los puntos
     * @return puntos
     */
    public int getPoints() {return points;}

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
     * getter de game
     * @return juego
     */
    public VMGame getGame() {return game;}

    /**
     * getter del mapa
     * @return regresa el mapa
     */
    public MapHandler getMap() {return mymap;}

    /**
     * getter la lista de objetos
     * @return la lista de objetos
     */
    public HashMap<String, ArrayList<Item>> getItems() {return items;}

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
     * setter de puntos
     * @param points puntos actuales del juego
     */
    public void setPoints(int points) { this.points = points;}

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
        for (String key : items.keySet()) {
            for (Item item : items.get(key)) {
                if (item.getPickable() != 0) {
                    item.render(batch);
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
            batch.draw(end, endpoint.x, endpoint.y);

            //batch.draw;
            batch.end();

            // HUD Render
            hud.render();

            hud.setHealth(player.getHealth());
            hud.setDash(player.getDashes());
            hud.setTime(getLevelSeconds());
            hud.setPunctuation(getPoints());
            hud.updateItems();

        if (getWin()) 
        {
            ScreenHandler.getInstance().showScreen(ScreenEnum.GAME_WON, game);
        }

	    if (getLost())
	    {
		    ScreenHandler.getInstance().showScreen(ScreenEnum.GAME_OVER, game);
	    }
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
		mymap.dispose();
		hud.dispose();
		levelMusic.stop();
	}
}