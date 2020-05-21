package com.mygdx.game;

import java.text.DecimalFormat;
import java.util.ArrayList;
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

public class HUD {
    Stage stage;
    Skin skin;

    ColorDrawable lightUIColor;
    ColorDrawable darkUIColor;
    ColorDrawable lightGreenColor;
    ColorDrawable darkGreenColor;
    ColorDrawable lightBlueColor;
    ColorDrawable darkBlueColor;

    Table bottomBar;
    Label healthLabel;
    Table itemSection;
    ArrayList<Table> items;
    Container<Label> timerField;
    Label timerLabel;    
    Container<Label> dayField;
    Label dayLabel;

    Container<Image> statusContainer;
    ArrayList<Container<Image>> dashes;
    ArrayList<Container<Image>> gel;

    String format;
    DecimalFormat decimalFormat;

    HUD() {
        format = "000";
        decimalFormat = new DecimalFormat(format);

        lightUIColor = new ColorDrawable((float) 86 / 255, (float) 108 / 255, (float) 134 / 255, (float) 0.9);
        darkUIColor = new ColorDrawable((float) 51 / 255, (float) 60 / 255, (float) 87 / 255, (float) 0.9);
        lightGreenColor = new ColorDrawable((float) 167 / 255, (float) 240 / 255, (float) 112 / 255, (float) 0.9);
        darkGreenColor = new ColorDrawable((float) 56 / 255, (float) 183 / 255, (float) 100 / 255, (float) 0.9);
        lightBlueColor = new ColorDrawable((float) 65 / 255, (float) 165 / 255, (float) 246 / 255, (float) 0.9);
        darkBlueColor = new ColorDrawable((float) 59 / 255, (float) 93 / 255, (float) 201 / 255, (float) 0.9);

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Item Section

        itemSection = new Table(skin);
        itemSection.setHeight(72);
        itemSection.setWidth(64 * 3 + 16);
        itemSection.setPosition(0, stage.getViewport().getScreenHeight() - 72);
        itemSection.align(Align.bottomLeft);
        itemSection.pad(4);
        itemSection.row().fill();
        items = new ArrayList<Table>();
        for (int i = 0; i < 3; i++) {
            items.add(new Table(skin));
            items.get(i).setBackground(darkUIColor);
            itemSection.add(items.get(i)).width(64).expandY().padRight(4);
        }

        stage.addActor(itemSection);
        
        // Time Fields

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

        // Bottom Bar
        
        bottomBar = new Table(skin);        
        bottomBar.setWidth(stage.getViewport().getScreenWidth());
        bottomBar.setHeight(80);
        bottomBar.setBackground(darkUIColor);
        bottomBar.align(Align.left);
        stage.addActor(bottomBar);

        bottomBar.row().fill();
        healthLabel = new Label("Health: 100%", skin);
        bottomBar.add(healthLabel).padLeft(16).padRight(64);
        
        Texture txt = new Texture("hudFace.png");
        statusContainer = new Container<Image>(new Image(txt));
        statusContainer.setBackground(lightUIColor);
        bottomBar.add(statusContainer).height(80).width(80).padLeft(16).padRight(48);

        dashes = new ArrayList<Container<Image>>();

        for (int i = 0; i < 3; i++) {
            dashes.add(new Container<Image>());
            dashes.get(i).setBackground(lightGreenColor);
            bottomBar.add(dashes.get(i)).height(21).width(21).pad(16);
        }

        gel = new ArrayList<Container<Image>>();

        for (int i = 0; i < 3; i++) {
            gel.add(new Container<Image>());
            gel.get(i).setBackground(lightBlueColor);
            bottomBar.add(gel.get(i)).height(55).width(40).pad(16);
        }
    }

    public void setTime(int seconds)  {
        timerLabel.setText(decimalFormat.format(seconds) + "s");
    }

    public void setDate(String date)  {
        dayLabel.setText(date);
    }

    public void setHealth(int health)  {
        healthLabel.setText("HEALTH : " + Integer.toString(health) + "%");
    }

    public void setDash(int available) {
        for (int i = 0; i < dashes.size(); i++) {
            dashes.get(i).setBackground(darkGreenColor);
        }
        for (int i = 0; i < available && i < dashes.size(); i++) {
            dashes.get(i).setBackground(lightGreenColor);
        }
    }

    public void setGel(int available) {
        for (int i = 0; i < dashes.size(); i++) {
            gel.get(i).setBackground(darkBlueColor);
        }
        for (int i = 0; i < available && i < dashes.size(); i++) {
            gel.get(i).setBackground(lightBlueColor);
        }
    }

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    
}
