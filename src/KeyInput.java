import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



//HERE WE CALL handler.set methods to make object move!!!




public class KeyInput extends KeyAdapter{

	Handler handler;
	Game game;
	
	public KeyInput(Handler handler, Game game) {
		this.handler = handler;
		this.game = game;
	}
	
	public void keyPressed(KeyEvent e) {
		
		
		
		int key = e.getKeyCode();
		
		
		//Pause
		int p = KeyEvent.VK_P;
		if(game.state == 1 && (key == p)) {
			System.out.println("You want to pause eh?");
			game.state = 3;
			game.godMode = false;
			
			
		}
		//unpause
		int u = KeyEvent.VK_U;
		if(game.state == 3 && (key == u)) {
			unpause();
		}
		
		
		
		
		//dev tool:
		int g = KeyEvent.VK_G;
		if(game.state == 1 & (key == g)) {
			System.out.println("ammo reset");
			game.ammo = 100;
		}
		//godmode
		int l = KeyEvent.VK_L;
		if (game.state == 1 && (key == l)) {
			System.out.println("gamemode is set to" + game.godMode);
			game.godMode = true;
		}
		
		
		
		
		
		
		for(int i = 0; i < handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player) {
				if(key == KeyEvent.VK_W) handler.setUp(true) ;
				if(key == KeyEvent.VK_S) handler.setDown(true);
				if(key == KeyEvent.VK_D) handler.setRight(true);
				if(key == KeyEvent.VK_A) handler.setLeft(true);
				
			}
			
		}
		
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		
		
		
		for(int i = 0; i < handler.object.size();i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Player) {
				if(key == KeyEvent.VK_W) handler.setUp(false);
				if(key == KeyEvent.VK_S) handler.setDown(false);
				if(key == KeyEvent.VK_D) handler.setRight(false);
				if(key == KeyEvent.VK_A) handler.setLeft(false);
				
			}
			
		}
		
	}
	
	private void unpause() {
		System.out.println("unpaused");
		game.state = 1;
	}

	
	
}
