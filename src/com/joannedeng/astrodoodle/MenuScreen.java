package com.joannedeng.astrodoodle;

import java.util.List;

import android.graphics.Color;
import android.graphics.AvoidXfermode.Mode;

import com.joannedeng.framework.Game;
import com.joannedeng.framework.Graphics;
import com.joannedeng.framework.Screen;
import com.joannedeng.framework.Input.TouchEvent;
import com.joannedeng.framework.implementation.AndroidGame;

//main menu
public class MenuScreen extends Screen {
	public MenuScreen(Game game) {
		super(game);
	}

	int height = AndroidGame.getHeight();
	int width = AndroidGame.getWidth();

	@Override
	public void update(float deltaTime) {
		Graphics g = game.getGraphics();
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {

				// If the user touches the screen, we open the MainClass.
				if (inBounds(event, 0, 0, width, height)) {

					game.setScreen(new MainClass(game));
				}

			}
		}
	}

	// defined an inbounds method that allows us to check if
	// the user touches inside a rectangle.
	private boolean inBounds(TouchEvent event, int x, int y, int width,
			int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}

	@Override
	// paint the menu Image.
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, width, height, Color.WHITE);
		g.drawImage(Assets.menu, (int) ((width - 550) / 2),
				(int) ((height - 303) / 2));
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
	// If the user touches the back button, the game exits.
	public void backButton() {
		android.os.Process.killProcess(android.os.Process.myPid());

	}
}