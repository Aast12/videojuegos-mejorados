package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

/**
 * Clase auxiliar para renderizar mapas en pantalla y manejar las interacciones
 * con los diferentes tipos de objetos del mapa.</p>
 *
 * Usa tilemaps en formato .tmx </p>
 *
 * @author Alam Sanchez
 */
public class MapHandler {
    public OrthographicCamera camera;
    public OrthogonalTiledMapRenderer renderer;
    public TiledMap map;
    public ArrayList<MapObject> solidObjects;
    
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
     * Aplica una función en todos los objetos de una capa del mapa.
     *
     * @param layerName nombre de la capa de objetos
     * @param fn objeto Lambda con la función a aplicar
     * @param onProperties bandera determina si se mandan las propiedades el objeto del mapa a la lambda o el mismo objeto
     */
    public void applyOnLayerObjects(String layerName, Lambda fn, boolean onProperties) {
        //Se genera un iterador para recorrer todos los objetos
        Iterator<MapObject> it =  map.getLayers().get(layerName).getObjects().iterator();
        while (it.hasNext()) {
            MapObject curr = it.next();
            // Se aplica la función sobre el objeto o las propiedades
            if (onProperties) {
                fn.apply(curr.getProperties());
            } else {
                fn.apply(curr);
            }
        }
    }
    
    /**
     * Regresa un objeto del mapa dado su nombre y la capa en la que se encuentra.
     *
     * @param layerName nombre de la capa para buscar
     * @param objectName nombre del objeto dentro de la capa
     */
    public MapObject getObjectFromLayer(String layerName, String objectName) {
        return map.getLayers().get(layerName).getObjects().get(objectName);
    }
    
    
    /**
     * Indica si un rectangulo colisiona con alguno de los objetos
     * rectangulares de una capa del mapa.
     *
     * @param layerName nombre de la capa de la colision
     * @param rect objecto Rectangle de la colision
     * @return booleano que representa si hay o no una colision
     */
    public boolean collidesOnLayer(String layerName, Rectangle rect) {
        MapLayer layer = map.getLayers().get(layerName);
        for (RectangleMapObject wall : layer.getObjects().getByType(RectangleMapObject.class)) {
            if (wall.getRectangle().overlaps(rect)) {
                return true;
            }
        }
        return false;
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