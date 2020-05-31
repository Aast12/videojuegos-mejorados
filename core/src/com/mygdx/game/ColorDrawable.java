package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Clase para generar una textura para rellenar de color a elementos de UI.
 * 
 * @author Alam Sanchez
 */
public class ColorDrawable extends TextureRegionDrawable {
    private float r, g, b, a;
    public Pixmap bgPixmap;

    /**
     * Constructor de la textura de color
     * 
     * @param r valor del canal rojo
     * @param g valor del canal verde
     * @param b valor del canal azul
     * @param a valor del canal alpha
     */
    public ColorDrawable(float r, float g, float b, float a) {
        super();
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        bgPixmap = new Pixmap(1,1, Pixmap.Format.RGBA4444);
        bgPixmap.setColor(new Color(r, g, b, a));
        bgPixmap.fill();

        this.setRegion(new TextureRegion(new Texture(bgPixmap)));
    }
}