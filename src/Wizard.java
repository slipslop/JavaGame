
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Wizard extends GameObject {
	Handler handler;
	private Game game;
	SpriteSheet ss;
	
	
	 BufferedImage standing;
	 BufferedImage walkRight;
	 BufferedImage walkUp;
	 BufferedImage walkLeft1;
	 BufferedImage walkLeft2;
	 BufferedImage walkLeft3;
	 BufferedImage walkLeft4;
	 
	 BufferedImage walkRight1;
	 BufferedImage walkRight2;
	 BufferedImage walkRight3;
	 BufferedImage walkRight4;
	 
	 
	 BufferedImage walkDown2;
	 BufferedImage walkDown3;
	 BufferedImage walkDown4;
	 
	 
	 
	 
	//ArrayList<BufferedImage> walkingLeft;
	private BufferedImage[] walkingUp;
	private BufferedImage[] walkingDown;
	
	
	
	public Wizard(int x, int y, ID id, Handler handler,Game game, SpriteSheet ss) {
		super(x, y, id,ss);
		// TODO Auto-generated constructor stub
		this.handler = handler;
		this.game = game;
		this.ss = ss;
		loadImages();
		
		
		
		//walkingLeft.add( ss.grabImage(2, 1, 37, 41)); 
		//walkingLeft.add( ss.grabImage(2, 2, 37, 41)); 
		//walkingLeft.add( ss.grabImage(2, 3, 37, 41));
		//walkingLeft.add( ss.grabImage(2, 4, 37, 41)); 
		//ss.grabImage(2, 2, 37, 41), ss.grabImage(2, 3, 37, 41), ss.grabImage(2, 4, 37, 41)  }; 
		//BufferedImage[] walkingRight = { ss.grabImage(4, 1, 37, 41),ss.grabImage(4, 2, 37, 41), ss.grabImage(4, 3, 37, 41), ss.grabImage(4, 4, 37, 41)};
		
		//Animator animation = new Animator( standing);
		
		
	}

	int counter = 0;
	
	
	private void loadImages() {
		System.out.println("Hello from loadimages");
		walkLeft1 =  ss.grabImage2(2, 1, 37, 50);
		walkLeft2 = ss.grabImage2(2, 2, 37, 45);
		walkLeft3 = ss.grabImage2(2, 3, 37, 45);
		walkLeft4 = ss.grabImage2(2, 4, 37, 42);
		
		walkRight1 =  ss.grabImage2(4, 1, 40, 50);
		walkRight2 = ss.grabImage2(4, 2, 40, 45);
		walkRight3 = ss.grabImage2(4, 3, 40, 45);
		walkRight4 = ss.grabImage2(4, 4, 40, 42);
		
		
		
		standing = ss.grabImage2(1, 1, 37, 41);
		
		walkRight = ss.grabImage2(4, 1, 37, 50);
		
		walkUp = ss.grabImage2(3, 1, 40, 50);
		
	}
	
	
	public void tick() {
		x+=velX;
		y+=velY;
		
		
		collision();
		//Movement!
		//animation.update(500);
		
		if(handler.isUp()) {
			System.out.println(x);
			velY =- 4;
			
		}else if (!handler.isDown()) {
			velY = 0;
		}
		
		if(handler.isDown()) {
			velY =+ 4;
			
		}else if (!handler.isUp()) {
			velY = 0;
		}
		
		if(handler.isRight()) {
			velX = 4;
			
		}else if (!handler.isLeft()) {
			velX = 0;
		}
		
		if(handler.isLeft()) {
			velX =- 4;
			
		}else if (!handler.isRight()) {
		
			velX = 0;
		}
		
	
	}

	
	private void checkBullets() {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Bullet) {
				System.out.println("Bullet found");
			}
		}
	}
	
	
	private void collision() {
		for (int i = 0; i < handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			
			//Collision with walls
			if(tempObject.getId() == ID.Block) {
				if(getBounds().intersects(tempObject.getBounds())) {
					//WE HAVE A COLLISION!
					System.out.println("x is " + getX() + " y is " + getY());
					x += velX * -1;
					y += velY * -1;
				}
			}
			//Collision with crate
			if(tempObject.getId() == ID.Crate) {
				if(getBounds().intersects(tempObject.getBounds())) {
					game.ammo += 100;
					handler.removeObject(tempObject);
					System.out.println("Found ammo!");
				}
			}
			if(tempObject.getId() == ID.Enemy) {
				if(getBounds().intersects(tempObject.getBounds())) {
					game.hp -= 0.2;
					
					System.out.println("hp lost!");
				}
			}
			
			
			
			
		}
	}
	int display = 0;
	
	public void render(Graphics g) {
		if(game.state == 1 && handler.isLeft()) {
		
			counter++;
			
			if(counter % 50
					== 1) {
				//System.out.println("display % 300");
				display++;
				System.out.println("display is " + display);
			}
				if(display == 0) {
					
					g.drawImage(walkLeft1, x, y, null);
				}else if (display == 2) {
					g.drawImage(walkLeft2, x, y, null);
				}else if (display == 3){
					g.drawImage(walkLeft3, x, y, null);
				}else {
					g.drawImage(walkLeft4
							, x, y, null);
				}
				if(display > 4) {
					System.out.println("DISPLAY IS 0");
					display = 0;
					
				}
			}
			//System.out.println(counter++);
			//g.drawImage(walkLeft2, x, y, null);
	
			
		else if (game.state == 1 && handler.isRight()) {
			counter++;
			
			if(counter % 50
					== 1) {
				//System.out.println("display % 300");
				display++;
				System.out.println("display is " + display);
			}
				if(display == 0) {
					
					g.drawImage(walkRight1, x, y, null);
				}else if (display == 2) {
					g.drawImage(walkRight2, x, y, null);
				}else if (display == 3){
					g.drawImage(walkRight3, x, y, null);
				}else {
					g.drawImage(walkRight4
							, x, y, null);
				}
				if(display > 4) {
					System.out.println("DISPLAY IS 0");
					display = 0;
					
				}
			}
			//System.out.println(counter++);
			//g.drawImage(walkLeft2, x, y, null);
		else if (game.state == 1 && handler.isUp()) {
			g.drawImage(walkUp, x, y, null);
		}else {
			g.drawImage(standing, x, y, null);
		}
	}


	private void animation(BufferedImage img) {
		
		
		
		
	}
	
	public Rectangle getBounds() {
	
		
		//Mask for our character...
		return new Rectangle(x,y,40,40);
	}

}
