package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.VMGame;

public class DesktopLauncher {

    public static void main(String[] arg) {
        System.setProperty("user.name", "Public");
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Game";
        config.resizable = false;
        config.width = 800;
        config.height = 600;
        new LwjglApplication(new VMGame(), config);
    }
}
