//make compatible for all screen sizes**TEST
//comment code
//better buttons

package com.joannedeng.astrodoodle;

import java.io.InputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.graphics.Color;
import android.graphics.Paint;
import com.joannedeng.framework.Game;
import com.joannedeng.framework.Graphics;
import com.joannedeng.framework.Image;
import com.joannedeng.framework.Screen;
import com.joannedeng.framework.Sound;
import com.joannedeng.framework.Input.TouchEvent;
import com.joannedeng.framework.implementation.AndroidFileIO;
import com.joannedeng.framework.implementation.AndroidGame;

public class MainClass extends Screen {
	enum GameState {
		Running, GameOver, Paused
	}

	GameState state = GameState.Running;

	// variable declarations
	private static ArrayList<Rock> rocks = new ArrayList<Rock>();
	private static ArrayList<Monster> monsters = new ArrayList<Monster>();
	private static ArrayList<MonsterBullet> monsterBullet = new ArrayList<MonsterBullet>();

	private static AstroShooter astroShooter;
	private Image character, character2, rock, rock2, rock3, monster1,
			monster2, gameover, bullet1, bullet2, button;
	public static int score, randomBullet, width, height, buttonBound;
	private static long startRock, startMonster, startShoot, timeGameover,
			startMonsterBullet;
	private Animation animMonster, animAstro, animBullet;
	private static int touchY, touchX;
	private static Sound pop;

	Paint paint, paint2, paintScore;

	public MainClass(Game game) {
		super(game);

		// Initialize game objects
		startRock = System.currentTimeMillis();
		startMonster = System.currentTimeMillis();
		startShoot = System.currentTimeMillis();
		startMonsterBullet = System.currentTimeMillis();

		astroShooter = new AstroShooter();

		score = 0;

		height = AndroidGame.getHeight();
		width = AndroidGame.getWidth();

		character = Assets.character;
		character2 = Assets.character2;
		rock = Assets.rock;
		rock2 = Assets.rock2;
		rock3 = Assets.rock3;
		monster1 = Assets.monster1;
		monster2 = Assets.monster2;
		bullet1 = Assets.monsterBullet1;
		bullet2 = Assets.monsterBullet2;
		button = Assets.button;

		buttonBound = (int) (height * 0.16);

		gameover = Assets.gameover;

		// animation declarations
		animMonster = new Animation();
		animMonster.addFrame(monster1, 300);
		animMonster.addFrame(monster2, 300);

		animBullet = new Animation();
		animBullet.addFrame(bullet1, 200);
		animBullet.addFrame(bullet2, 200);

		animAstro = new Animation();
		animAstro.addFrame(character, 200);
		animAstro.addFrame(character2, 200);

		// paint declarations
		paint = new Paint();
		paint.setTextSize(50);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);

		paint2 = new Paint();
		paint2.setTextSize(100);
		paint2.setTextAlign(Paint.Align.CENTER);
		paint2.setAntiAlias(true);
		paint2.setColor(Color.WHITE);

		paintScore = new Paint();
		paintScore.setTextSize(35);
		paintScore.setTextAlign(Paint.Align.CENTER);
		paintScore.setAntiAlias(true);
		paintScore.setColor(Color.BLACK);
	}

	// update game according to what state it's in
	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		if (state == GameState.Running)
			updateRunning(touchEvents, deltaTime);
		if (state == GameState.Paused)
			updatePaused(touchEvents);
		if (state == GameState.GameOver)
			updateGameOver(touchEvents);
	}

	// update the game if it's running
	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {

		Graphics g = game.getGraphics();

		// timers for spawning the enemies
		if (System.currentTimeMillis() - startRock > 1300 - height * 0.5) {
			spawnRock();
			startRock = System.currentTimeMillis();
		}

		if (System.currentTimeMillis() - startMonster > 2300) {
			spawnMonster();
			startMonster = System.currentTimeMillis();
		}

		if (System.currentTimeMillis() - startMonsterBullet > 900) {
			spawnBullet();
			startMonsterBullet = System.currentTimeMillis();
		}

		/*
		 * if(astroShooter.isPressedDown()==false){ astroShooter.moveDown(); }
		 */

		// handle touch
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);

			// check if the player is pressing the up and down buttons
			if (event.type == TouchEvent.TOUCH_DOWN
					|| event.type == TouchEvent.TOUCH_DRAGGED) {

				if (inBounds(event, 0, (int) (height * 0.71), buttonBound,
						buttonBound)) {
					astroShooter.moveUp();
					// astroShooter.setPressedDown(true);
				}

				else if (inBounds(event, 0, (int) (height * 0.875),
						buttonBound, buttonBound)) {
					astroShooter.moveDown();
				}
			}

			// checks if the player wants to shoot
			if (event.type == TouchEvent.TOUCH_DOWN
					|| event.type == TouchEvent.TOUCH_DRAGGED) {
				if (inBounds(event, (int) (width * 0.2), 0,
						(int) (width * 0.75), (int) (width * 0.625))) {
					touchY = event.y;
					touchX = event.x;
					if (System.currentTimeMillis() - startShoot > 130) {
						astroShooter.shoot();
						startShoot = System.currentTimeMillis();
					}
				}
			}

			// stop going up/down when user stops pressing the up/down buttons
			if (event.type == TouchEvent.TOUCH_UP) {
				if (inBounds(event, 0, (int) (height * 0.71), buttonBound,
						buttonBound)
						|| inBounds(event, 0, (int) (height * 0.875),
								buttonBound, buttonBound)) {
					astroShooter.stop();
				}
			}

		}

		// Check for death
		if (astroShooter.isCollide() == true) {
			timeGameover = System.currentTimeMillis();
			state = GameState.GameOver;
		}

		astroShooter.update();

		// update all the list of enemies and bullets,
		// removing them from their arrays if needed
		ArrayList projectiles = astroShooter.getProjectiles();
		for (int i = 0; i < projectiles.size(); i++) {
			PewPew p = (PewPew) projectiles.get(i);
			if (p.isVisible() == true) {
				p.update();
			} else {
				projectiles.remove(i);
			}
		}

		for (int i = 0; i < monsterBullet.size(); i++) {
			MonsterBullet p = (MonsterBullet) monsterBullet.get(i);
			if (p.isVisible() == true) {
				p.update();
			} else {
				monsterBullet.remove(i);
			}
		}

		for (int i = 0; i < rocks.size(); i++) {
			Rock r = (Rock) rocks.get(i);
			if (r.isVisible() == true) {
				r.update();
			} else {
				rocks.remove(i);
			}

		}

		for (int i = 0; i < monsters.size(); i++) {
			Monster m = (Monster) monsters.get(i);
			if (m.isVisible() == true) {
				m.update();
			} else {
				monsters.remove(i);
			}

		}

		animate();
	}

	private void animate() {
		animMonster.update(30);
		animBullet.update(30);
		animAstro.update(30);
	}

	// update the game when it's paused
	private void updatePaused(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (inBounds(event, 0, 0, 800, 240)) {

					if (!inBounds(event, 0, 0, 35, 35)) {
						resume();
					}
				}

				if (inBounds(event, 0, 240, 800, 240)) {
					nullify();
					goToMenu();
				}
			}
		}
	}

	// update the game when the player loses
	private void updateGameOver(List<TouchEvent> touchEvents) {
		// makes sure that the gameover screen is shown for 2.5 seconds
		// before resetting the game
		if (System.currentTimeMillis() - timeGameover < 2500) {
			drawGameOverUI();
		} else {
			nullify();
			game.setScreen(new MenuScreen(game));
			return;
		}
	}

	// function to check if user pressed inside the chosen boundaries
	private boolean inBounds(TouchEvent event, int x, int y, int width,
			int height) {
		if (event.x > x && event.x < x + width - 1 && event.y > y
				&& event.y < y + height - 1)
			return true;
		else
			return false;
	}

	// draw all the game elements
	@Override
	public void paint(float deltaTime) {
		Graphics g = game.getGraphics();

		// fill the screen with a white background
		g.drawRect(0, 0, width, height, Color.WHITE);

		// draw the bullets
		ArrayList projectiles = astroShooter.getProjectiles();
		for (int i = 0; i < projectiles.size(); i++) {
			PewPew p = (PewPew) projectiles.get(i);
			g.drawOval(p.getX(), p.getY(), 12, 9, Color.BLACK);
		}

		for (int i = 0; i < rocks.size(); i++) {
			Rock r = (Rock) rocks.get(i);
			// g.drawRect(r.getX()-50, r.getY()-50, 100, 80, Color.BLACK);
			// ^to check if the images are drawn at correct/accurate locations

			// draw the different types of rocks
			if (r.getType() == 1) {
				g.drawImage(rock, r.getX() - 65, r.getY() - 65);
			} else if (r.getType() == 2) {
				g.drawImage(rock2, r.getX() - 65, r.getY() - 65);
			} else {
				g.drawImage(rock3, r.getX() - 65, r.getY() - 65);
			}
		}

		for (int i = 0; i < monsters.size(); i++) {
			Monster r = (Monster) monsters.get(i);
			// g.drawRect(r.getX()-60, r.getY()-65, 120, 110,Color.BLACK);
			// ^to check if the images are drawn at correct/accurate locations

			g.drawImage(animMonster.getImage(), r.getX() - 50, r.getY() - 40);
		}

		for (int i = 0; i < monsterBullet.size(); i++) {
			MonsterBullet p = (MonsterBullet) monsterBullet.get(i);
			// .drawRect(p.getX(), p.getY(), p.getX()+32, p.getY()+32,
			// Color.BLACK);
			// ^to check if the images are drawn at correct/accurate locations

			g.drawImage(animBullet.getImage(), p.getX(), p.getY());
		}

		// g.drawRect(astroShooter.getCenterX() - 65, astroShooter.getCenterY()
		// - 55, 130, 110,Color.BLACK);
		// ^to check if the images are drawn at correct/accurate locations

		g.drawImage(animAstro.getImage(), astroShooter.getCenterX() - 86,
				astroShooter.getCenterY() - 65);

		g.drawString(Integer.toString(score), (int) (width * 0.9),
				(int) (height * 0.075), paintScore);

		g.drawImage(button, (int) (width * 0.01), height - 140);

		if (state == GameState.Paused)
			drawPausedUI();
		if (state == GameState.GameOver)
			drawGameOverUI();

	}

	// functions to spawn the enemies, randomizing their starting positions
	public void spawnRock() {
		Random rand = new Random();
		Rock r = new Rock(width + 70, rand.nextInt(height));
		rocks.add(r);
	}

	public void spawnMonster() {
		Random rand = new Random();
		Monster m = new Monster(width + 70, rand.nextInt(height));
		monsters.add(m);
	}

	public void spawnBullet() {
		for (int i = 0; i < monsters.size(); i++) {
			Monster r = (Monster) monsters.get(i);
			if (r.getBulletCount() < 1) {
				MonsterBullet b = new MonsterBullet(r.getX() - 30, r.getY() - 5);
				monsterBullet.add(b);
				r.setBulletCount(r.getBulletCount() + 1);
			}
		}
	}

	private void nullify() {
		paint = null;
		paint2 = null;
		paintScore = null;
		astroShooter = null;
		rock = null;
		monster1 = null;
		monster2 = null;
		bullet1 = null;
		bullet2 = null;
		character = null;
		character2 = null;
		gameover = null;
		animMonster = null;
		animAstro = null;
		animBullet = null;

		rocks.clear();
		monsters.clear();
		monsterBullet.clear();
		System.gc();

	}

	private void drawPausedUI() {
		Graphics g = game.getGraphics();
		g.drawARGB(155, 0, 0, 0);
		g.drawString("Resume", 400, 165, paint2);
		g.drawString("Menu", 400, 360, paint2);
	}

	private void drawGameOverUI() {
		Graphics g = game.getGraphics();
		g.drawRect(0, 0, width, height, Color.WHITE);
		g.drawImage(gameover, (int) ((width - 575) / 2),
				(int) ((height - 264) / 2));
		g.drawString(Integer.toString(score), (int) ((width - 575) / 2 + 300),
				(int) ((height - 264) / 2 + 245), paint);
	}

	@Override
	public void pause() {
		if (state == GameState.Running)
			state = GameState.Paused;
	}

	@Override
	public void resume() {
		if (state == GameState.Paused)
			state = GameState.Running;
	}

	@Override
	public void dispose() {

	}

	@Override
	public void backButton() {

	}

	private void goToMenu() {
		game.setScreen(new MenuScreen(game));

	}

	// getters and setters
	public static AstroShooter getRobot() {
		return astroShooter;
	}

	public static ArrayList<Rock> getRocks() {
		return rocks;
	}

	public void setRocks(ArrayList<Rock> rocks) {
		this.rocks = rocks;
	}

	public static ArrayList<Monster> getMonsters() {
		return monsters;
	}

	public static void setMonsters(ArrayList<Monster> monsters) {
		MainClass.monsters = monsters;
	}

	public List<TouchEvent> getTouchEvents2() {
		return game.getInput().getTouchEvents();
	}

	public static int getTouchY() {
		return touchY;
	}

	public void setTest(int touchY) {
		this.touchY = touchY;
	}

	public static int getTouchX() {
		return touchX;
	}

	public static void setTouchX(int touchX) {
		MainClass.touchX = touchX;
	}

	public static ArrayList<MonsterBullet> getMonsterBullet() {
		return monsterBullet;
	}

	public void setMonsterBullet(ArrayList<MonsterBullet> monsterBullet) {
		this.monsterBullet = monsterBullet;
	}

	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}

}