package com.joannedeng.astrodoodle;

import java.util.ArrayList;
import java.util.List;

import com.joannedeng.framework.Input.TouchEvent;
import com.joannedeng.framework.implementation.AndroidGame;

import android.graphics.Rect;

public class PewPew {

	private int x, y, temp, touchY, touchX;
	private boolean visible;
	private double angle, speedX;
	private Rect p;
	private int width = AndroidGame.getWidth();

	public PewPew(int startX, int startY) {
		x = startX;
		y = startY;
		speedX = 7;
		visible = true;
		touchY = MainClass.getTouchY();
		touchX = MainClass.getTouchX();

		// calculations to determine which direction the bullet shoots
		angle = Math.toDegrees(((Math.atan2((y - touchY), (touchX - x)))));
		temp = (int) ((Math.tan(Math.toRadians(angle))) * speedX);

		p = new Rect(0, 0, 0, 0);
	}

	public void update() {
		y -= temp;
		x += speedX;

		// set bounds for the bullets; used to check collision
		p.set(x, y, x + 15, y + 10);

		// remove bullets if it moves off the screen
		if (x > width) {
			visible = false;
			p = null;
		}

		// check for collision while the bullets are in view
		if (x < width) {
			checkCollision();
		}
	}

	// check if the bullets collide with the enemies,
	// decreases enemy health or kills them
	private void checkCollision() {

		ArrayList rocks = MainClass.getRocks();
		for (int i = 0; i < rocks.size(); i++) {
			Rock r = (Rock) rocks.get(i);
			if (Rect.intersects(p, r.getR())) {
				visible = false;

				if (r.getHealth() > 0) {
					r.setHealth(r.getHealth() - 1);
				}
				if (r.getHealth() == 0) {
					r.setVisible(false);
					MainClass.score += 1;
				}
			}

		}

		ArrayList monsters = MainClass.getMonsters();
		for (int i = 0; i < monsters.size(); i++) {
			Monster m = (Monster) monsters.get(i);
			if (Rect.intersects(p, m.getR())) {
				visible = false;

				if (m.getHealth() > 0) {
					m.setHealth(m.getHealth() - 1);
				}
				if (m.getHealth() == 0) {
					m.setVisible(false);
					MainClass.score += 5;
				}
			}

		}

		ArrayList monsterBullet = MainClass.getMonsterBullet();
		for (int i = 0; i < monsterBullet.size(); i++) {
			MonsterBullet m = (MonsterBullet) monsterBullet.get(i);
			if (Rect.intersects(p, m.getP())) {
				visible = false;

				if (m.getHealth() > 0) {
					m.setHealth(m.getHealth() - 1);
				}
				if (m.getHealth() == 0) {
					m.setVisible(false);
					MainClass.score += 1;
				}
			}
		}
	}

	//getters and setters
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public double getSpeedX() {
		return speedX;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
