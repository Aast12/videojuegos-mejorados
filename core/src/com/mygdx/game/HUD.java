package com.mygdx.game;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class HUD {
    Stage stage;
    Skin skin;

    Table bottomBar;
    Label healthLabel;
    Table itemSection;
    Table item1;
    Table item2;
    Table item3;
    Container<Label> timerField;
    Label timerLabel;    
    Container<Label> dayField;
    Label dayLabel;

    String format;
    DecimalFormat decimalFormat;

    HUD() {
        format = "000";
        decimalFormat = new DecimalFormat(format);

        ColorDrawable lightUIColor = new ColorDrawable((float) 86 / 255, (float) 108 / 255, (float) 134 / 255, (float) 0.9);
        ColorDrawable darkUIColor = new ColorDrawable((float) 51 / 255, (float) 60 / 255, (float) 87 / 255, (float) 0.9);

        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Item Section

        itemSection = new Table(skin);
        itemSection.setHeight(72);
        itemSection.setWidth(64 * 3 + 16);
        itemSection.setPosition(0, stage.getViewport().getScreenHeight() - 72);
        
        item1 = new Table(skin);
        item2 = new Table(skin);
        item3 = new Table(skin);
        item1.setBackground(darkUIColor);
        item2.setBackground(darkUIColor);
        item3.setBackground(darkUIColor);
        itemSection.align(Align.bottomLeft);
        itemSection.pad(4);
        itemSection.row().fill();
        itemSection.add(item1).width(64).expandY().padRight(4);
        itemSection.add(item2).width(64).expandY().padRight(4);
        itemSection.add(item3).width(64).expandY().padRight(4);

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
        stage.addActor(bottomBar);

        healthLabel = new Label("Health: 100%", skin);
        bottomBar.add(healthLabel);

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

    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    
}
