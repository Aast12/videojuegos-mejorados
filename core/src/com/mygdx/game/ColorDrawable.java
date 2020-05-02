package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ColorDrawable extends TextureRegionDrawable {
    private float r, g, b, a;

    public ColorDrawable(float r, float g, float b, float a) {
        super();
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGB565);
        bgPixmap.setColor(new Color(r, g, b, a));
        bgPixmap.fill();

        this.setRegion(new TextureRegion(new Texture(bgPixmap)));
    }
}