import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Key extends GameObject{
	
	private BufferedImage keyImage;

	public Key(int x, int y, ID id, SpriteSheet ss) {
		super(x, y, id, ss);
		// TODO Auto-generated constructor stub
		keyImage = ss.grabImage(1, 1, 32, 32);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(keyImage, x, y, null);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

}
