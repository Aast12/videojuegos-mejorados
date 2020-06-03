package com.mygdx.game;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Clase para administrar los elementos del HUD del juego.
 * 
 * 
 * @author Alam Sanchez
 * @author davidg
 */
public class HUD {
    private Stage stage;
    private Skin skin;

    private ColorDrawable lightUIColor;
    private ColorDrawable darkUIColor;
    private ColorDrawable lightGreenColor;
    private ColorDrawable darkGreenColor;
    private ColorDrawable lightBlueColor;
    private ColorDrawable darkBlueColor;

    private Table bottomBar;
    private Label healthLabel;
    private Table itemSection;
    private Vector<Table> items;
    private Container<Label> timerField;
    private Label timerLabel;    
    private Container<Label> dayField;
    private Label dayLabel;
    private Container<Label> punctuationField;
    private Label punctuationLabel;
    private Container<Label> popupField;
    private Label popupLabel;
    private boolean hasPopup;
    private float popupTime;
    private ArrayList<Float> popupTimestamps = new ArrayList<Float>() {
        {
            add(3f); // aparición
            add(9f); // visible
            add(12f); // desaparición
        }
    };

    private Container<Image> statusContainer;
    Image statusIndicators[];
    private Vector<Container<Image>> dashes;
    private Vector<Container<Image>> gel;
    private HashMap<String, ItemGroup> itemsData;

    private String format;
    private DecimalFormat decimalFormat;

    /**
     * Constructor del HUD
     * 
     * @param itemMap lista de items a mostrar en el HUD
     */
    HUD(HashMap<String, ItemGroup> itemMap) {
        format = "000";
        decimalFormat = new DecimalFormat(format);

        // Declaración de colores para UI
        lightUIColor = new ColorDrawable(86f / 255, 108f / 255, 134f / 255, 0.9f);
        darkUIColor = new ColorDrawable(51f / 255, 60f / 255, 87f / 255, 0.9f);
        lightGreenColor = new ColorDrawable(167f / 255, 240f / 255, 112f / 255, 0.9f);
        darkGreenColor = new ColorDrawable(56f / 255, 183f / 255, 100f / 255, 0.9f);
        lightBlueColor = new ColorDrawable(65f / 255, 165f / 255, 246f / 255, 0.9f);
        darkBlueColor = new ColorDrawable(59f / 255, 93f / 255, 201f / 255, 0.9f);

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Sección de items en esquina superior izquierda
        itemsData = itemMap;

        itemSection = new Table(skin);
        itemSection.setHeight(72);
        itemSection.setWidth(64 * 3 + 16);
        itemSection.setPosition(0, stage.getViewport().getScreenHeight() - 72);
        itemSection.align(Align.bottomLeft);
        itemSection.pad(4);
        itemSection.row().fill();
        items = new Vector<Table>();
        for (int i = 0; i < 3; i++) {
            items.add(new Table(skin));
            items.get(i).setBackground(darkUIColor);
            itemSection.add(items.get(i)).width(64).expandY().padRight(4);
        }
        int idx = 0;
        for (String key : itemsData.keySet()) {
            Image im = new Image(new Texture(key));
            Label counter = new Label("x0", skin);
            counter.setScale(0.5f);
            im.setColor(1f, 1f, 1f, 0.2f);
            items.get(idx).add(im);
            items.get(idx).add(counter).bottom().right();
            itemsData.get(key).setIndexList(idx);
            idx++;
        }

        stage.addActor(itemSection);
        
        // Campos de datos de tiempo
        timerLabel = new Label("035 s", skin);
        timerField = new Container<Label>(timerLabel);
        timerField.setPosition(stage.getViewport().getScreenWidth() - 145 * 2, stage.getViewport().getWorldHeight() - 40);
        timerField.setWidth(145);
        timerField.setHeight(40);
        timerField.setBackground(lightUIColor);
        stage.addActor(timerField);

        dayLabel = new Label("Apr. 5", skin);
        dayField = new Container<Label>(dayLabel);
        dayField.setPosition(stage.getViewport().getScreenWidth() - 145, stage.getViewport().getWorldHeight() - 40);
        dayField.setWidth(145);
        dayField.setHeight(40);
        dayField.setBackground(darkUIColor);
        stage.addActor(dayField);

        // Campo de puntuación
        punctuationLabel = new Label("0", skin);
        punctuationField = new Container<Label>(punctuationLabel);
        punctuationField.setPosition(stage.getViewport().getScreenWidth() - 145*2, stage.getViewport().getWorldHeight() - 80);
        punctuationField.setWidth(145*2);
        punctuationField.setHeight(40);
        punctuationField.setBackground(lightUIColor);
        stage.addActor(punctuationField);

        // Pop-Up
        popupLabel = new Label("Pop Up", skin);
        popupField = new Container<Label>(popupLabel);
        popupField.setPosition(0, stage.getViewport().getWorldHeight() - 200);
        popupField.setWidth(stage.getViewport().getWorldWidth());
        popupField.setHeight(40);
        popupField.setBackground(darkBlueColor);
        stage.addActor(popupField);
        popupField.setVisible(false);

        hasPopup = false;

        // Barra inferior 
        bottomBar = new Table(skin);        
        bottomBar.setWidth(stage.getViewport().getScreenWidth());
        bottomBar.setHeight(80);
        bottomBar.setBackground(darkUIColor);
        bottomBar.align(Align.left);
        stage.addActor(bottomBar);

        bottomBar.row().fill();
        healthLabel = new Label("Health: 100%", skin);
        bottomBar.add(healthLabel).padLeft(16).padRight(64);
        
        // Imagen de estado
        statusIndicators = new Image[5];
        for (int i = 0; i < 5; i++) {
            statusIndicators[i] = new Image(new Texture("hudFaceL" + Integer.toString(i) + ".png"));
        }
        statusContainer = new Container<Image>(statusIndicators[0]);
        statusContainer.setBackground(lightUIColor);
        bottomBar.add(statusContainer).height(80).width(80).padLeft(16).padRight(48);

        // Declaración de dashes
        dashes = new Vector<Container<Image>>();

        for (int i = 0; i < 3; i++) {
            dashes.add(new Container<Image>());
            dashes.get(i).setBackground(lightGreenColor);
            bottomBar.add(dashes.get(i)).height(21).width(21).pad(16);
        }

        // Declaración de geles
        gel = new Vector<Container<Image>>();

        for (int i = 0; i < 3; i++) {
            Image gelImg = new Image(new Texture("gel.png"));
            gelImg.setWidth(20f);
            gelImg.setHeight(5f);
            gel.add(new Container<Image>(gelImg));
            bottomBar.add(gel.get(i)).height(55).width(40).pad(16);
        }
    }

    /**
     * Actualiza la etiqueta de tiempo
     * 
     * @param seconds segundos a mostrar en la etiqueta
     */
    public void setTime(int seconds)  { timerLabel.setText(decimalFormat.format(seconds) + "s"); }

    /**
     * Actualiza la etiqueta de puntuación
     * 
     * @param points puntos a asignar
     */
    public void setPunctuation(int points) { punctuationLabel.setText("Score: " + points); }

    /**
     * Actualiza la etiqueta de la fecha
     * 
     * @param date nueva fecha a asignar
     */
    public void setDate(String date)  { dayLabel.setText(date); }

    /**
     * Actualiza la etiqueta de la vida del jugador y su escudo
     * 
     * @param health puntos de vida del jugador
     * @param shield puntos de escudo del jugador
     */
    public void setHealth(int health, int shield)  {
        if (shield > 0) {
            healthLabel.setColor(lightBlueColor.getColor());
        } else {
            healthLabel.setColor(Color.WHITE);
        }
        if (health > 80) {
            statusContainer.setActor(statusIndicators[0]);
        }
        else if (health > 60) {
            statusContainer.setActor(statusIndicators[1]);
        }
        else if (health > 40) {
            statusContainer.setActor(statusIndicators[2]);
        }
        else if (health > 15) {
            statusContainer.setActor(statusIndicators[3]);
        }
        else {
            statusContainer.setActor(statusIndicators[4]);
        }
        healthLabel.setText("HEALTH : " + Integer.toString(health + shield) + "%");
    }
  

    /**
     * Actualiza el contador visual de dashes
     * 
     * @param available la cantidad de dashes disponibles
     */
    public void setDash(int available) {
        for (int i = 0; i < dashes.size(); i++) {
            dashes.get(i).setBackground(darkGreenColor);
        }
        for (int i = 0; i < available && i < dashes.size(); i++) {
            dashes.get(i).setBackground(lightGreenColor);
        }
    }

    /**
     * Actualiza el contador visual de geles antibacteriales
     * 
     * @param available la cantidad
     */
    public void setGel(int available) {
        for (int i = 0; i < dashes.size(); i++) {
            gel.get(i).setColor(1f, 1f, 1f, 0.2f);
        }
        for (int i = 0; i < available && i < dashes.size(); i++) {
            gel.get(i).setColor(1f, 1f, 1f, 1f);
        }
    }

    /**
     * Actualiza el estado del HUD
     */
    public void tick() {
        for (String key : itemsData.keySet()) {
            int idx = itemsData.get(key).getIndexList();
            for (int i = 0; i < items.get(idx).getChildren().size; i++) {
                if (items.get(idx).getChild(i).getClass() == Image.class && itemsData.get(key).getCounter() == 0) {
                    items.get(idx).getChild(i).setColor(1f, 1f, 1f, 1f);
                }
                if (items.get(idx).getChild(i).getClass() == Label.class) {
                    ((Label) items.get(idx).getChild(i)).setText("x" + Integer.toString(itemsData.get(key).getCounter()));
                }
            }
            if (hasPopup) {
                popupTime += Gdx.graphics.getDeltaTime();
                if (popupTime < popupTimestamps.get(0)) {
                    popupField.setColor(1f, 1f, 1f, Globals.map(popupTime, 0f, popupTimestamps.get(0), 0f, 1f));
                }
                else if (popupTime > popupTimestamps.get(1) && popupTime < popupTimestamps.get(2)) {
                    popupField.setColor(1f, 1f, 1f, Globals.map(popupTime, popupTimestamps.get(1), popupTimestamps.get(2), 1f, 0f));
                }
                else if (popupTime >= popupTimestamps.get(2)) {
                    popupField.setVisible(false);
                    popupTime = 0;
                    hasPopup = false;
                }
            }
        }
    }

    public void triggerPopup(String message) {
        popupField.setVisible(true);
        popupLabel.setText(message);
        popupField.setColor(1f, 1f, 1f, 0f);
        popupTime = 0;
        hasPopup = true;
    }

    /**
     * Muestra el HUD en pantalla
     */
    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    /**
     * Libera los recursos utilizados por el HUD
     */
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    
}
