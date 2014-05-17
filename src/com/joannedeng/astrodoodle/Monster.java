package com.joannedeng.astrodoodle;

import java.util.ArrayList;
import java.util.Random;
import java.util.Random;

import com.joannedeng.framework.Graphics;

import android.graphics.Color;
import android.graphics.Rect;

public class Monster {
	private AstroShooter astroShooter = MainClass.getRobot();
	private int x, y, speedX, health, level, bulletCount;
	private boolean visible, switchFrame;
	private Rect r;

	public Monster(int startX, int startY) {
		x = startX;
		y = startY;
		speedX = 4;
		visible = true;
		health = 5;
		r = new Rect(0, 0, 0, 0);
		switchFrame = false;
		bulletCount = 0;
	}

	public void update() {
		follow();

		// used to check collision with the character
		r.set(x - 63, y - 51, x + 63, y + 51);

		// remove enemy when it moves off the screen
		if (x < -100) {
			visible = false;
		}

		// if the monster is nearing the character, check for collision
		if (x < 200) {
			checkCollision();
		}
	}

	// moves the monster towards the character
	private void follow() {
		x -= speedX;
		if (Math.abs(astroShooter.getCenterY() - y) > 5) {
			if (astroShooter.getCenterY() > y) {
				y += 1;
			} else {
				y -= 1;
			}
		}
	}

	private void checkCollision() {
		if (Rect.intersects(r, AstroShooter.rect)) {
			astroShooter.setCollide(true);
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getHealth() {
		return health;
	}

	public boolean isVisible() {
		return visible;
	}

	public Rect getR() {
		return r;
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

	public void setHealth(int health) {
		this.health = health;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void setR(Rect r) {
		this.r = r;
	}

	public boolean isSwitchFrame() {
		return switchFrame;
	}

	public void setSwitchFrame(boolean switchFrame) {
		this.switchFrame = switchFrame;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getBulletCount() {
		return bulletCount;
	}

	public void setBulletCount(int bulletCount) {
		this.bulletCount = bulletCount;
	}

}