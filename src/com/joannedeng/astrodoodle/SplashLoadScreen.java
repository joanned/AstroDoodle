package com.joannedeng.astrodoodle;

import com.joannedeng.framework.Game;
import com.joannedeng.framework.Graphics;
import com.joannedeng.framework.Screen;
import com.joannedeng.framework.Graphics.ImageFormat;

// As soon as the loading of the splash image is complete
//go to theLoadingScreen class
public class SplashLoadScreen extends Screen {
	public SplashLoadScreen(Game game) {
		super(game);
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		Assets.splash = g.newImage("jojogames.jpg", ImageFormat.RGB565);

		game.setScreen(new LoadScreen(game));
	}

	@Override
	public void paint(float deltaTime) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {

	}
}