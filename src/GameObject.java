
import java.awt.Graphics;
import java.awt.Rectangle;



//Bones of our objects...


public abstract class GameObject {

	//Every game object has:
	//x and y values (location)
	protected int x,y;
	
	//velocities
	protected float velX = 0, velY = 0;
	
	protected ID id;
	protected SpriteSheet ss;
	
	public GameObject(int x, int y, ID id, SpriteSheet ss) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.ss = ss;
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	//Collision checking
	public abstract Rectangle getBounds();

	
	
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @return the velX
	 */
	public float getVelX() {
		return velX;
	}

	/**
	 * @return the velY
	 */
	public float getVelY() {
		return velY;
	}

	/**
	 * @return the id
	 */
	public ID getId() {
		return id;
	}
	
	
	
	
	
	
}
