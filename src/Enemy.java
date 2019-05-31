
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;



public class Enemy extends GameObject {

	private Handler handler;
	Random r = new Random();
	int hp = 100;
	
	
	//Direction
		float dx = 3;
		float dy = 3;
		
		
		boolean changeDir = false;
		
		
	
	private BufferedImage monsterImage;
	
	
	public Enemy(int x, int y, ID id, Handler handler, SpriteSheet ss) {
		super(x, y, id,ss);
		this.handler = handler;
		monsterImage = ss.grabImage(1, 1, 32, 32);
		
		
		//a random number between 4 and -4
		velX = r.nextInt((4- -4) + -2 )+1;
		velY = r.nextInt((4- -4) + -1 )+1;
	}

	@Override
	public void tick() {
		x = (int) (x+dx);
		y = (int) (y+dy);
		//System.out.println(velX + " " + velY);
		
		
		collision();
		
		
		
	}
	
	
	private void collision() {
		try {
			
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Block) {
				if(getBoundsBig().intersects(tempObject.getBounds())) {
					//WE HAVE A COLLISION!
					//System.out.println("I think you are colliding!" + getX());
				
					
					dx = -dx;
					dy = -dy;
					
				}
				
			}
			if(tempObject.getId() == ID.Bullet) {
				if(getBounds().intersects(tempObject.getBounds())) {
					System.out.println("Hit bullet!");
					hp -= 20 ;
					if(hp == 0) {
						handler.object.remove(this);
					}
				}
				
			}
		}
		}catch (Exception e) {
			System.out.println("try catch at Enemy.java 82");
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(monsterImage, x, y, null);
		
		Graphics g2d = (Graphics2D) g;
		g.setColor(Color.white);
		((Graphics2D) g2d).draw(getBoundsBig());
		
	}

	
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return new Rectangle(x,y,24,24);
	}
	public Rectangle getBoundsBig() {
		// TODO Auto-generated method stub
		return new Rectangle(x-12,y-12,48,48);
	}

}
