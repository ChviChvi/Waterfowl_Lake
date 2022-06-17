package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.mygdx.game.gameclass;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class Waterfowl_Lake_Launcher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Waterfowl Lake");
		new Lwjgl3Application(new gameclass(), config);
	}
}
