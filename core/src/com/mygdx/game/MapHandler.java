package com.mygdx.game;

import java.util.Vector;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Clase auxiliar para renderizar mapas en pantalla y manejar las interacciones
 * con los diferentes tipos de objetos del mapa.</p>
 * 
 * Usa tilemaps en formato .tmx </p> 
 * 
 * @author Alam Sánchez
 */
public class MapHandler {
    public OrthographicCamera camera;
    public OrthogonalTiledMapRenderer renderer;
    public TiledMap map;
    public Vector<MapObject> solidObjects;
    
    /**
     * Crea un objeto MapHandler para manejar un tilemap.
     * 
     * @param file ruta del archivo .tmx
     * @param camera camara para visualizar el mapa
     */
    public MapHandler(String file, OrthographicCamera camera) {
        this.map = new TmxMapLoader().load(file);
        this.renderer = new OrthogonalTiledMapRenderer(map, 1);
        this.camera = camera;
    }

    /**
     * Asigna una camara para ver el mapa.
     * 
     * @param camera nueva camara para visualizar el mapa
     */
    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    /**
     * Renderiza el mapa en pantalla.
     * 
     * @param batch objeto Batch usado para dibujar en la pantalla
     */
    public void render(Batch batch) {
        renderer.render();
        renderer.setView(camera);
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        batch.end();
    }

    /**
     * Libera los recursos utilizados por los miembros de la instancia.
     */
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}