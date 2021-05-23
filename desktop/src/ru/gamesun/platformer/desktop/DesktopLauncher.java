package ru.gamesun.platformer.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.gamesun.platformer.Platformer;

public class DesktopLauncher {
	public static void main (String[] arg) {
	LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new Platformer(), config);
	config.height = Platformer.SCREENHEIGHT;
	config.width = Platformer.SCREENWIDTH;
	}
}
