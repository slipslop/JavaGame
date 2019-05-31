
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Crate extends GameObject {

	
	private BufferedImage imageCrate;
	
	public Crate(int x, int y, ID id, SpriteSheet ss) {
		super(x, y, id,ss);
		imageCrate = ss.grabImage(3,1, 32, 32);
	}

	@Override
	public void tick() {
	
	}

	
	
	
	@Override
	public void render(Graphics g) {
		g.drawImage(imageCrate, x, y, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x,y,32,32);
	}

}
