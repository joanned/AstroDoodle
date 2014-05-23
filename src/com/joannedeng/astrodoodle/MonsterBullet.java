package com.joannedeng.astrodoodle;

import java.util.ArrayList;
import java.util.List;

import com.joannedeng.framework.Input.TouchEvent;

import android.graphics.Rect;

public class MonsterBullet {
	private AstroShooter astroShooter = MainClass.getRobot();
	private int x, y, temp, health, robotX, robotY;
	private double angle, speedX;
	private Rect p;
	private boolean visible;

	public MonsterBullet(int startX, int startY) {
		x = startX;
		y = startY;
		speedX = 5;
		robotX = astroShooter.getCenterX();
		robotY = astroShooter.getCenterY();
		visible = true;
		health = 1;

		//calculations to aim the fireballs at the character
		angle = Math.toDegrees(((Math.atan2((robotY - y), (x - robotX)))));
		temp = (int) ((Math.tan(Math.toRadians(angle))) * speedX);

		p = new Rect(0, 0, 0, 0);
	}

	public void update() {
		y += temp;
		x -= speedX;

		// set bounds for monster; used to check collision
		p.set(x, y, x + 32, y + 32);

		// if monsters move off the screen, remove them
		if (x < -50) {
			visible = false;
		}

		// check for collision for the character when it's getting close to it
		if (x > 0 && x < 200) {
			checkCollision();
		}
	}

	private void checkCollision() {
		if (Rect.intersects(p, AstroShooter.rect)) {
			astroShooter.setCollide(true);
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

	public Rect getP() {
		return p;
	}

	public void setP(Rect p) {
		this.p = p;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
