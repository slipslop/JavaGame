import java.awt.image.BufferedImage;

public class LevelLoader {

	
	private Handler handler;
	private Game game;
	
	public LevelLoader(Handler handler, Game game) {
		
		this.handler = handler;
		this.game = game;
		
	}
	
	public void loadLevel (BufferedImage image, SpriteSheet ssTiles, SpriteSheet ssPlayer, SpriteSheet ssMonster, SpriteSheet ssKey) {
		System.out.println("Hello from LevelLoader");
		
		
		int w = image.getWidth();
		int h = image.getHeight();
		
		
		for(int xx = 0; xx < w; xx++) {
			for (int yy = 0; yy < h; yy++) {
				int pixel = image.getRGB(xx,yy);
				int red = (pixel >> 16 ) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				//System.out.println("at x" + xx + " and y "+ yy);
				
				
				//if a pixel in the picture is red we render it as a block.
				if(red == 255 && green == 0 && blue == 0) {
					handler.addObject(new Block(xx*32, yy*32, ID.Block,ssTiles));
				}
				
				//if a pixel in the picture is blue, we render it as a player.
				if(blue == 255 && green == 0 && red == 0) {
					System.out.println("player object added");
					handler.addObject(new Wizard(xx*32,yy*32,ID.Player,handler,game,ssPlayer));
				}
				
				if(green == 255 && blue == 0 && red == 0) {
					
					
					handler.addObject(new Enemy(xx*32, yy*32, ID.Enemy, handler,ssMonster));
					game.enemies++;
					System.out.println("added enemy, total enemies: " + game.enemies);
				}
				
				
				if(green == 255 && blue == 255 && red == 0) {
					System.out.println("crate found");
					handler.addObject(new Crate(xx*32, yy*32, ID.Crate,ssTiles));
				}
				if(green == 255 && red == 255 && blue == 0) {
					System.out.println("Key found!");
					handler.addObject(new Key(xx*32, yy*32, ID.Key, ssKey));
				}
				
				//System.out.println("There is " + handler.object.size() + " objects loaded! ");
				
			}
			
		}
		
		
		
	}
	
	public boolean enemiesLeft () {
		for (int i = 0; i < handler.object.size();i++) {
			if(handler.object.get(i).id == ID.Enemy) {
				return true;
			}
		}
		return false;
	}
	
	
}
