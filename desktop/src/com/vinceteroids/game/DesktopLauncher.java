package com.vinceteroids.game;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.vinceteroids.game.Vinceteroids;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("Vinceteroids");

		config.setForegroundFPS(60);
		config.useVsync(false);
		config.setWindowedMode(1024, 768);
		new Lwjgl3Application(new Vinceteroids(), config);
	}
}
