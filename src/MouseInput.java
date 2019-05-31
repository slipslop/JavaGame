import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;


public class MouseInput extends MouseAdapter implements MouseMotionListener{

	private Handler handler;
	private Camera camera;
	private Game game;
	private SpriteSheet ss;
	
	//shoot when its 60
	
	
	public MouseInput(Handler handler, Camera camera, Game game,SpriteSheet ss)  {
		this.handler = handler;
		this.camera = camera;
		this.game = game;
		this.ss = ss;
	}
	
	//Method used by MouseInput
	public void mousePressed(MouseEvent e) {
		
		if(game.state == 0) {
			int mx = e.getX();
			int my = e.getY();
			System.out.println(mx + " " + my);
			//This is for play again button;
			if((mx >=  467 && mx <= 536) && (my >= 231 && my <= 252)) {
				game.resetGame();
				
				
			}
			
			if((mx >=  463 && mx <= 536) && (my >= 351 && my <= 374)) {
				System.out.println("you want to quit");
				game.quit(); //quits the game.
			}
			
		}
		
		
		if(game.state == 2) {
			int mx = e.getX();
			int my = e.getY();
			
			//This is for play again button;
			if((mx >=  425 && mx <= 575) && (my >= 220 && my <= 240)) {
				game.resetGame();
				
				
			}
			//This is for quit button:
			if((mx >=  460 && mx <= 525) && (my >= 320 && my <= 340)) {
				System.out.println("you want to quit");
				game.quit(); //quits the game.
			}
			
			
			System.out.println(mx + " " + my);
		}
		
		
		
		
		
		
		
			//state 1 = game.
		if(game.state == 1) {
			int ammo = game.ammo;
			System.out.println("mouse pressed, and ammo is: " +ammo);
			//Coordinates of the mouse
			float mx = (e.getX() + camera.getX() +30);
			float my = (e.getY() + camera.getY() + 30);
			
			//System.out.println(mx + " " + my);
			if(game.ammo > 0) {
				
				game.ammo -= 5;
				for(int i = 0; i < handler.object.size();i++) {
					GameObject tempObject = handler.object.get(i);
					
					if(tempObject.getId() == ID.Player) {
						Bullet l = new Bullet(tempObject.getX()+16, tempObject.getY()+24, ID.Bullet, handler, mx, my, ss);
						handler.addObject(l);
						timer(l);
					}
					
				}
			}else {
				System.out.println("Out of bullets!");
			}
			
			
		}
		
		
	}
	
	
	private void timer(GameObject a) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			  @Override
			  public void run() {
				  for(int i = 0; i < handler.object.size();i++) {
					  GameObject temp = handler.object.get(i);
					  
					  if(temp  == a) {
						  handler.removeObject(temp); 
					  }
				  }
				  
			    System.out.println("Removed bullet, handler.object.size is : " + handler.object.size());
			  }
			}, 12345);
	}
	
	public void mouseMoved(MouseEvent e) {
		
		if(game.godMode) {
			
			System.out.println(game.whenToShoot);
			if(game.whenToShoot > 80) {
				
				int ammo = game.ammo;
				System.out.println("mouse pressed, and ammo is: " +ammo);
				//Coordinates of the mouse
				float mx = (e.getX() + camera.getX() +30);
				float my = (e.getY() + camera.getY() + 30);
				
				//System.out.println(mx + " " + my);
				if(game.ammo > 0) {
					
					game.ammo -= 0;
					for(int i = 0; i < handler.object.size();i++) {
						GameObject tempObject = handler.object.get(i);
						
						if(tempObject.getId() == ID.Player) {
							handler.addObject(new Bullet(tempObject.getX()+16, tempObject.getY()+24, ID.Bullet, handler,mx,my,ss));
							
						}
						
					}
				}
				
			game.whenToShoot = 0;
			
			
			}
			System.out.println(e.getX() + " " + e.getY());
			}
			
			
	}

	
}
