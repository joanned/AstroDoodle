package com.joannedeng.astrodoodle;

import java.util.ArrayList;

import com.joannedeng.framework.implementation.AndroidGame;

import android.graphics.Rect;

//Class for the main character
public class AstroShooter {

	final int MOVESPEED = 4;
	private int centerX = 100;
	private int centerY = 225;
	private boolean collide = false;
	// private boolean pressedDown = false;
	private int height = AndroidGame.getHeight();
	private int speedY = 0;
	public static Rect rect = new Rect(0, 0, 0, 0);

	// array for the bullet projectiles
	private ArrayList<PewPew> pewPews = new ArrayList<PewPew>();

	public void update() {

		// makes sure the character doesn't go off the screen
		if (centerY <= 70) {
			centerY = 71;
		} else if (centerY >= height - 80) {
			centerY = height - 79;
		}

		// set bounds for the character; used to check for collision
		rect.set(centerX - 50, centerY - 55, centerX + 40, centerY + 60);

		centerY += speedY;
	}

	public void moveUp() {
		speedY = -MOVESPEED;
	}

	public void moveDown() {
		speedY = MOVESPEED - 1;

	}

	public void stop() {
		speedY = 0;
		// pressedDown = false;
	}

	public void shoot() {
		// adding new projectiles to the array
		PewPew p = new PewPew(centerX + 86, centerY - 9);
		pewPews.add(p);
	}

	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public ArrayList getProjectiles() {
		return pewPews;
	}

	public boolean isCollide() {
		return collide;
	}

	public void setCollide(boolean collide) {
		this.collide = collide;
	}

	/*
	 * public boolean isPressedDown() { return pressedDown; }
	 * 
	 * public void setPressedDown(boolean pressedDown) { this.pressedDown =
	 * pressedDown; }
	 */

}
