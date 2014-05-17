package com.joannedeng.astrodoodle;

import android.graphics.Color;

import com.joannedeng.framework.Game;
import com.joannedeng.framework.Graphics;
import com.joannedeng.framework.Screen;
import com.joannedeng.framework.Graphics.ImageFormat;
import com.joannedeng.framework.implementation.AndroidGame;

//Load the assets
public class LoadScreen extends Screen {
	public LoadScreen(Game game) {

		super(game);
	}

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		Assets.menu = g.newImage("menu1.jpg", ImageFormat.ARGB4444);
		Assets.character = g.newImage("main1.png", ImageFormat.ARGB4444);
		Assets.character2 = g.newImage("main2.png", ImageFormat.ARGB4444);
		Assets.rock = g.newImage("rock.png", ImageFormat.ARGB4444);
		Assets.rock2 = g.newImage("rock2.png", ImageFormat.ARGB4444);
		Assets.rock3 = g.newImage("rock3.png", ImageFormat.ARGB4444);
		Assets.monsterBullet1 = g.newImage("bullet1.png", ImageFormat.ARGB4444);
		Assets.monsterBullet2 = g.newImage("bullet2.png", ImageFormat.ARGB4444);
		Assets.monster1 = g.newImage("monsterbig1.png", ImageFormat.ARGB4444);
		Assets.monster2 = g.newImage("monsterbig2.png", ImageFormat.ARGB4444);
		Assets.gameover = g.newImage("gameover.png", ImageFormat.ARGB4444);

		Assets.button = g.newImage("button.png", ImageFormat.ARGB4444);

		// Once loading is complete, the MenuScreen opens
		game.setScreen(new MenuScreen(game));

	}

	// As long as it takes to load these assets,
	// the game will call the paint() method, in which the splash screen is
	// drawn.
	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, AndroidGame.getWidth(), AndroidGame.getHeight(),
				Color.WHITE);
		g.drawImage(Assets.splash, (int) ((AndroidGame.getWidth() - 524) / 2),
				(int) ((AndroidGame.getHeight() - 87) / 2));
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