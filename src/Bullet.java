
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Bullet extends GameObject {

	private Handler handler;
	
	
	public Bullet(int x, int y, ID id, Handler handler, float mx, float my, SpriteSheet ss) {
		super(x, y, id,ss);
		this.handler = handler;
		
		
		
		
		//Calculating the bullet speed, otherwise it would be very
		//inconsistent
		float dx = (mx-x);
		float dy = (my -y);
		float d = (float) Math.pow((x*x+y*y), 0.5);
		int baseVel = 2;
		
		velX = (10 * dx/d);
		velY =  (10 * dy/d );
		
		System.out.println("velocity of bullet was: " + velX + " " + velY);
		
	}
	
	
	

	@Override
	public void tick() {
		x += velX;
		y += velY;
		
		
		//Collision with block
		try  {
			
		
		for(int i = 0; i < handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i) ;
			if(tempObject.getId() == ID.Block) {
				if(getBounds().intersects(tempObject.getBounds())) {
					handler.removeObject(this);
				}
			}
			
			//If we hit enemy, we remove bullet from game.
			if(tempObject.getId() == ID.Enemy) {
				if(getBounds().intersects(tempObject.getBounds())) {
					handler.removeObject(this);
					
				}
			}
			
		}
		} catch (Exception e) {
			System.out.println("try catch at Bullet.java 62");
		}
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillOval(x, y, 8, 8);
	}

	@Override
	public Rectangle getBounds() {
		
		return new Rectangle(x,y,8,8);
	}

}
