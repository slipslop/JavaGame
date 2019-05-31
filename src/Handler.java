import java.awt.Graphics;
import java.util.LinkedList;


public class Handler {
/*LinkedList can be searched forward and backward 
 * but the time it takes to traverse
 *  the list is directly proportional to the size of the list.
 */
	
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	private boolean up = false, down = false, right = false, left= false;
	
	
	
	public void tick() {
		try {
			
		for(int i = 0; i < object.size();i++) {
			GameObject tempObject = object.get(i);
			
			
			tempObject.tick();
		}
		
		}catch ( Exception e) {
			System.out.println("try catch at Handler.java 27");
		}
		
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < object.size();i++) {
			GameObject tempObject = object.get(i);
			
			
			tempObject.render(g);
		}
	}
	
	
	public void addObject(GameObject tempObject) {
		object.add(tempObject);
	}
	
	public void removeObject(GameObject tempObject) {
		object.remove(tempObject);
	}

	
	public void clearAll() {
		object.clear();
		System.out.println("object linkedlist cleared!" + object.size());
	}
	
	
	//////////////////Getters And Setters //////////////////////////////////////////
	/**
	 * @return the up
	 */
	public boolean isUp() {
		
		return up;
	}

	/**
	 * @param up the up to set
	 */
	public void setUp(boolean up) {
		this.up = up;
	}

	/**
	 * @return the down
	 */
	public boolean isDown() {
		return down;
	}

	/**
	 * @param down the down to set
	 */
	public void setDown(boolean down) {
		this.down = down;
	}

	/**
	 * @return the right
	 */
	public boolean isRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(boolean right) {
		this.right = right;
	}

	/**
	 * @return the left
	 */
	public boolean isLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(boolean left) {
		this.left = left;
	}
	
	
}
