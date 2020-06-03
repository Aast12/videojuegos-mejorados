package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Level implements Screen {
    private boolean win, lost;
    private int level;
    private int levelSeconds, points, minItems = 0;
    float timeSeconds = 0f;
    float period = 1f;
    private HashMap<String, ArrayList<Item>> items;
    private HashMap<String, ItemGroup> group;
    OrthographicCamera camera;
    boolean paused;
    private SpriteBatch pauseBatch;
    private Texture background;
    
    private TextButton continueButton;
    private TextButton quitButton;
    private Stage stage;
    private TextButton.TextButtonStyle textButtonStyle;
    private Skin skin;
    private TextureAtlas buttonAtlas;
    private BitmapFont font;
    
    
    Music levelMusic; // musica de menu
    Sound regenDash; // sonido de regeneracion de dash
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
    public Level (int seconds, VMGame g, String mapFile, int lvl) {
        
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
        
        //this.background = new Texture("main_menu_background.png");
        paused = false;
        this.game = g;
        level = lvl;
        this.win = false;
        this.lost = false;
        this.levelSeconds = seconds;
        this.points = 0;
        regenDash = Gdx.audio.newSound(Gdx.files.internal("obtDash.mp3"));
        levelMusic = Gdx.audio.newMusic(Gdx.files.internal("TheJ.mp3"));
        levelMusic.setVolume((float) (0.1 * game.globals.musicVolume));
        
        Table contentTable = new Table(skin);
        contentTable.setHeight(Gdx.graphics.getHeight());
        contentTable.setWidth(Gdx.graphics.getWidth());
        contentTable.setPosition(0f, 0f);
        contentTable.align(Align.top);

        contentTable.setBackground(new ColorDrawable(0f, 0f, 0f, 0.7f));
        
        Label headerLabel = new Label("PAUSA", skin);
        headerLabel.setWrap(true);
        headerLabel.setAlignment(Align.center);
        contentTable.add(headerLabel).height(200).expandX();
        
        stage.addActor(contentTable);
        
        // this.items.addAll(items);
        camera = new OrthographicCamera(800, 600);
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        mymap = new MapHandler(mapFile, camera);
        map = mymap.map;
        batch = new SpriteBatch();
        pauseBatch = new SpriteBatch();
        //Empezando aqui todo debe ser por nivel
        
        int playerX = (int) Float.parseFloat(mymap.getObjectFromLayer("Other", "Player").getProperties().get("x").toString());
        int playerY = (int) Float.parseFloat(mymap.getObjectFromLayer("Other", "Player").getProperties().get("y").toString());
        player = new Player(playerX, playerY, this);
        
        
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
        
        
        for (String key : this.getGroup().keySet()) {
            minItems += this.getGroup().get(key).getCounter();
        }
        
        minItems = minItems / 2;
        
        //Incialización de enemigos desde los datos del mapa
        enemies = new ArrayList<Enemy>();
        Lambda genEnemies = (mp) -> {
            MapProperties curr = (MapProperties) mp;
            int x = (int) Float.parseFloat(curr.get("x").toString());
            int y = (int) Float.parseFloat(curr.get("y").toString());
            try {
                String type = curr.get("type").toString();
                if (type == "big") {
                    enemies.add(new RandomEnemyBig(x, y, mymap));
                }
                else {
                    enemies.add(new RandomEnemy(x, y, mymap));
                }
            }
            catch (Exception e) {
                enemies.add(new RandomEnemy(x, y, mymap));
            }
        };
        
        mymap.applyOnLayerObjects("Enemies", genEnemies, true);
        
        end = new Texture("end.png");
        
        hud = new HUD(group);
        hud.setTime(levelSeconds);
        hud.setHealth(98, player.getShield());
        hud.setDash(player.getDashes());
        hud.setGel(player.getGel());
        
        hud.triggerPopup("Level " + Integer.toString(lvl));
        
        this.continueButton = new TextButton("Continuar", textButtonStyle);
        continueButton.setPosition(Gdx.graphics.getWidth() / 2 - 130, Gdx.graphics.getHeight() / 2 - 140);
        continueButton.setHeight(32);
        continueButton.addListener(
                new InputListener() {
                    @Override
                    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                        
                    }
                    
                    @Override
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        paused = false;
                        return true;
                    }
                }
        );
        stage.addActor(continueButton);
        
        this.quitButton = new TextButton("Salir del juego", textButtonStyle);
        quitButton.setPosition(Gdx.graphics.getWidth() / 2 - 130, Gdx.graphics.getHeight() / 2 - 220);
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
        stage.addActor(quitButton);
        
        
        
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
    
    public int getLevel(){
        return level;
    }
    
    public void setLevel(int l){
        this.level = l;
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
     * getter del minimo de items para acabar el nivel
     * @return regresa el minimo
     */
    public int getMinimumItems() {return minItems;}
    
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
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {// PAUSA
            paused = true;
        }
        
        
        levelMusic.play();
        camera.position.x = player.x + player.getHitbox().width / 2f;
        camera.position.y = player.y + player.getHitbox().height / 2f;
        camera.update();
        mymap.render(batch);
        batch.begin();
        if(!paused){
            // Sprites Render y tick
            for (Iterator<Enemy> it = enemies.iterator(); it.hasNext();) {
                Enemy e = it.next();
                e.tick();
            }
            tick();
            player.tick();
        }
        
        player.render(batch);
        for (Iterator<Enemy> it = enemies.iterator(); it.hasNext();) {
            Enemy e = it.next();
            e.render(batch);
        }
        render(batch);
        batch.draw(end, endpoint.x, endpoint.y);
        batch.end();
        
        // HUD Render
        hud.render();
        hud.setHealth(player.getHealth(), player.getShield());
        hud.setDash(player.getDashes());
        hud.setGel(player.getGel());
        hud.setTime(getLevelSeconds());
        hud.setPunctuation(getPoints());
        hud.tick();
        if (getWin())
        {
            setLevel(getLevel() + 1);
            points += player.getHealth() * (game.globals.index + 1) * 2;
            points += levelSeconds * 3 * (game.globals.index + 1);
            game.globals.totalScore += points;
            if(getLevel() <= 10 ){
                ScreenHandler.getInstance().showScreen(ScreenEnum.LEVEL_OVERLAY, game, getLevel());
            } else{
                ScreenHandler.getInstance().showScreen(ScreenEnum.GAME_WON, game, getLevel());
            }
        }
        if (getLost())
        {
            ScreenHandler.getInstance().showScreen(ScreenEnum.GAME_OVER, game, 1);
        }
        if(paused){
            stage.draw();
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
        stage.dispose();
        mymap.dispose();
        hud.dispose();
        levelMusic.stop();
    }
}