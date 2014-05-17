package com.joannedeng.astrodoodle;

import java.util.ArrayList;
import java.util.Random;
import java.util.Random;

import com.joannedeng.framework.Graphics;
import com.joannedeng.framework.implementation.AndroidFileIO;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

public class Rock {
	private AstroShooter astroShooter = MainClass.getRobot();
	private int x, y, speedX, health, type;
	private boolean visible;

	private Rect r;

	public Rock(int startX, int startY) {
		x = startX;
		y = startY;
		speedX = 2;
		visible = true;
		health = 5;

		// randomizes which type of rock to spawn
		Random randBullet = new Random();
		type = randBullet.nextInt(3);

		r = new Rect(0, 0, 0, 0);
	}

	public void update() {
		x -= speedX;

		// set bounds; used to check collision
		r.set(x - 50, y - 55, x + 50, y + 60);

		if (x < -100) {
			visible = false;
		}
		if (x < 200) {
			checkCollision();
		}

	}

	private void checkCollision() {
		if (Rect.intersects(r, AstroShooter.rect)) {
			astroShooter.setCollide(true);
		}
	}

	public void wipe() {
		x = -100;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
