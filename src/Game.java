
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.plaf.basic.BasicTabbedPaneUI.MouseHandler;

//Main class. This is where our game loop etc. will be

//Timer for bullets. when they have been flying too long, delete them. This is working, but it "deletes" bullets that are deleted already. Not sure if it matters.
//Added some try-catch blocks to increase error resistance.
//Godmode bullets do not get removed! (intentional)

//movement animation atleast for player.
//Add food.
//When all enemies are shot, move to another level. 

public class Game extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean isRunning = false;
	private Thread thread;
	private Handler handler;
	private Camera camera;
	private LevelLoader levelLoader;

	private SpriteSheet ss; // at this moment, this is like a placeholder
	private SpriteSheet ssPlayer;
	private SpriteSheet ssTiles;
	private SpriteSheet ssMonster;
	private SpriteSheet ssKeys;

	private SpriteSheet ssMenuScreen;
	private SpriteSheet ssGameOver;

	// level is the game map.
	public BufferedImage level1 = null;
	public BufferedImage level2 = null;

	private BufferedImage playerSprite = null;
	private BufferedImage tilesSprite = null; // floor, crate etc..
	private BufferedImage monsterSprite = null;
	private BufferedImage floor = null;
	private BufferedImage keysSprite = null;

	// Menuscreen:
	private BufferedImage menuScreenSprite = null;
	private BufferedImage menuText = null;
	private BufferedImage menuPlayText = null;
	private BufferedImage menuQuitText = null;

	// Game over screen:
	private BufferedImage gameOverSprite = null;
	private BufferedImage gameOverText = null;
	private BufferedImage playAgainText = null;
	private BufferedImage quitText = null;

	// We initialize ammo here, because we want to draw it sometime...
	public int ammo = 100;
	public int hp = 100;
	// this is variable that holds the amount of enemies.
	public int enemies = 0;
	public int stage = 1;
	/*
	 * 0 = menu
	 * 1 = game
	 * 2 = game over screen
	 * 3 = pause
	 */
	public int state = 0;
	public boolean fromAnotherScreen = false;
	public int clearOnce = 0;

	// These are for godMode. Press l to start godmode. Warning: may crash.
	// If you want to make shooting faster or slower, go to MouseInput.java
	// And modify the values there. (game.whentoshoot)
	// pause and unpause to set godMode = false;
	public boolean godMode = false;
	public int addMouseMotionListenerOnce = 0;
	public int whenToShoot = 0;

	public Game() {

		new Window(1000, 563, "Wizard Game", this);
		start();

		// Only time we create a new handler instance.

		handler = new Handler();
		camera = new Camera(0, 0);
		levelLoader = new LevelLoader(handler, this);

		// sh = new StageHandler(this,handler);

		BufferedImageLoader loader = new BufferedImageLoader();

		level1 = loader.loadImage("/level1.png");
		level2 = loader.loadImage("/level2.png");

		// Load picture before initializing ss's
		playerSprite = loader.loadImage("/george.png");
		tilesSprite = loader.loadImage("/tiles.png");
		monsterSprite = loader.loadImage("/monster.png");
		keysSprite = loader.loadImage("/keys.png");

		menuScreenSprite = loader.loadImage("/menuScreen.png");
		gameOverSprite = loader.loadImage("/gameOver.png");

		// SpriteSheet class takes a bufferedImage as input.
		ssPlayer = new SpriteSheet(playerSprite);
		ssTiles = new SpriteSheet(tilesSprite);
		ssMonster = new SpriteSheet(monsterSprite);
		ssKeys = new SpriteSheet(keysSprite);

		ssMenuScreen = new SpriteSheet(menuScreenSprite);
		ssGameOver = new SpriteSheet(gameOverSprite);

		floor = ssTiles.grabImage(1, 1, 32, 32);

		// Menu screen
		menuText = ssMenuScreen.grabImage(1, 1, 500, 64);
		menuPlayText = ssMenuScreen.grabImage(1, 3, 500, 64);
		menuQuitText = ssMenuScreen.grabImage(1, 5, 500, 64);

		// Game over screen
		gameOverText = ssGameOver.grabImage(1, 1, 500, 64);
		playAgainText = ssGameOver.grabImage(1, 3, 500, 64);
		quitText = ssGameOver.grabImage(1, 5, 500, 64);

		this.addMouseListener(new MouseInput(handler, camera, this, ss));

		if (state == 1) {

			loadLevel(level1);
		}

		KeyInput a = new KeyInput(handler, this);
		this.addKeyListener(a);

	}

	public void start() {
		// isRunning has to be set to true for the gameloop to run.

		isRunning = true;
		thread = new Thread(this);
		thread.start();

	}

	private void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		// Notch's Game loop from Minecraft
		if (state != 3) {
			this.requestFocus();
			long lastTime = System.nanoTime();
			double amountOfTicks = 60.0;
			double ns = 1000000000 / amountOfTicks;
			double delta = 0;
			long timer = System.currentTimeMillis();
			int frames = 0;
			while (isRunning) {

				long now = System.nanoTime();

				delta += (now - lastTime) / ns;
				lastTime = now;

				while (delta >= 1) {
					tick();
					delta--;
				}
				render();
				frames++;

				if (System.currentTimeMillis() - timer > 1000) {
					timer += 1000;
					frames = 0;
				}

			}
			stop();

		}

	}

	// Updates everything (60 times/sec)
	public void tick() {

		// Loops through the array and if the id is player's id, it will call the
		// tick method of camera.
		if (state == 1) {

			for (int i = 0; i < handler.object.size(); i++) {
				if (handler.object.get(i).getId() == ID.Player) {
					camera.tick(handler.object.get(i));
				}
			}

			handler.tick();

		}
	}

	// Renders everything
	public void render() {

		// This already loads 3 frames to the buffer.
		// So this works as kind of a buffer for frames
		// When you show one frame, it has 3 more already loaded.

		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			// 3 is best performace wise
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		Graphics2D g2d = (Graphics2D) g;
		//////////////////////////////////////////////////////
		/*
		 * g.setColor(Color.red);
		 * g.fillRect(0, 0, 1000, 563);
		 */

		// Menu screen
		if (state == 0) {

			// System.out.println("state is 0");

			g.setColor(Color.black);
			g.fillRect(0, 0, 1000, 563);

			g.drawImage(menuText, 250, 70, null);

			g.drawImage(menuPlayText, 250, 220, null);

			g.drawImage(menuQuitText, 250, 340, null);

			g.dispose();

			bs.show();

		}

		// state 1 = game screen
		if (state == 1) {

			if (godMode) {
				whenToShoot++;
				if (addMouseMotionListenerOnce == 0) {
					this.addMouseMotionListener(new MouseInput(handler, camera, this, ss));
					addMouseMotionListenerOnce++;
				}
				// System.out.println("god mode is on");

			}
			// If game is started from game over screen only.
			if (fromAnotherScreen) {
				// this is used to loadLevel only once.

				System.out.println("command came from another screen");

				loadLevel(level1);
				fromAnotherScreen = false;
			}

			g2d.translate(-camera.getX(), -camera.getY());

			for (int xx = 0; xx < 30 * 70; xx += 32) {
				for (int yy = 0; yy < 30 * 70; yy += 32) {
					g.drawImage(floor, xx, yy, null);

				}
			}

			handler.render(g);
			// System.out.println("did we get here?");

			// Is this needed?
			g2d.translate(camera.getX(), camera.getY());

			g.setColor(Color.black);
			g.fillRect(4, 4, 202, 34);

			g.setColor(Color.gray);
			g.fillRect(5, 5, 200, 32);
			g.setColor(Color.green);
			g.fillRect(5, 5, hp * 2, 32);

			g.setColor(Color.black);
			g.fillRect(4, 486, 202, 34);

			g.setColor(Color.gray);
			g.fillRect(4, 486, 200, 32);

			if (ammo <= 100) {
				g.setColor(Color.blue);
				g.fillRect(4, 486, ammo * 2, 32);
			} else if (ammo > 100 && ammo < 200) {
				g.setColor(Color.cyan);
				g.fillRect(4, 486, 200, 32);
			} else {
				g.setColor(Color.pink);
				g.fillRect(4, 486, 200, 32);
			}

			if (hp == 0) {

				g.dispose();
				state = 2;
			}

			// WORKS!
			if (!levelLoader.enemiesLeft()) {
				handler.clearAll();
				loadLevel(level2);
			}

			g.dispose();
			bs.show();
			// System.out.println("did we get here?");
		}

		if (state == 2) {

			if (clearOnce == 0) {

				// Remove everything from object linkedlist.
				handler.clearAll();
				// Also, that we do the clearing only once.
				clearOnce++;
			}

			g.setColor(Color.black);
			g.fillRect(0, 0, 1000, 532);

			g.drawImage(gameOverText, 264, 100, null);
			g.drawImage(playAgainText, 264, 200, null);
			g.drawImage(quitText, 264, 300, null);
			g.dispose();

			bs.show();
		}

		//////////////////////////////////////////////////////
		g.dispose();

	}

	// loading the level goes here. And the actual loading happens in
	// LevelLoader.java

	public void loadLevel(BufferedImage image) {

		if (stage == 1) {
			levelLoader.loadLevel(image, ssTiles, ssPlayer, ssMonster, ssKeys);
		}

		/*
		 * System.out.println("Hello from loadLevel");
		 * 
		 * 
		 * int w = image.getWidth();
		 * int h = image.getHeight();
		 * 
		 * 
		 * for(int xx = 0; xx < w; xx++) {
		 * for (int yy = 0; yy < h; yy++) {
		 * int pixel = image.getRGB(xx,yy);
		 * int red = (pixel >> 16 ) & 0xff;
		 * int green = (pixel >> 8) & 0xff;
		 * int blue = (pixel) & 0xff;
		 * //System.out.println("at x" + xx + " and y "+ yy);
		 * 
		 * 
		 * //if a pixel in the picture is red we render it as a block.
		 * if(red == 255) {
		 * handler.addObject(new Block(xx*32, yy*32, ID.Block,ssTiles));
		 * }
		 * 
		 * //if a pixel in the picture is blue, we render it as a player.
		 * if(blue == 255 && green == 0) {
		 * System.out.println("player object added");
		 * handler.addObject(new Wizard(xx*32,yy*32,ID.Player,handler,this,ssPlayer));
		 * }
		 * 
		 * if(green == 255 && blue == 0) {
		 * 
		 * 
		 * handler.addObject(new Enemy(xx*32, yy*32, ID.Enemy, handler,ssMonster));
		 * enemies++;
		 * System.out.println("added enemy, total enemies: " + enemies);
		 * }
		 * 
		 * 
		 * if(green == 255 && blue == 255) {
		 * System.out.println("crate found");
		 * handler.addObject(new Crate(xx*32, yy*32, ID.Crate,ssTiles));
		 * }
		 * 
		 * //System.out.println("There is " + handler.object.size() +
		 * " objects loaded! ");
		 * 
		 * }
		 * 
		 * }
		 */

	}

	// called from MouseInput.java
	// Quits game.
	public void quit() {
		System.exit(0);
	}

	public void setState(int number) {
		this.state = number;
	}

	// Called from MouseInput.java. Sets everything so, that we can restart our
	// game.
	public void resetGame() {
		handler.setDown(false);
		handler.setLeft(false);
		handler.setRight(false);
		handler.setUp(false);

		clearOnce = 0;
		hp = 100;
		fromAnotherScreen = true;
		setState(1);
	}

	private void setMenuState() {

	}

	public static void main(String[] args) {

		// constructor call
		new Game();

	}

}
