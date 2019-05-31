
public class Camera {

	private float x, y;
	
	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
		
	}
	
	
	public void tick (GameObject object) { 
		
		//tweening algorithm, does smooth transition.
								//1000 because our screen width is 1000, and /2 is half of it
		//System.out.println("Hello from camera tick() !");
		x += ((object.getX() - x ) - 1000/2) * 0.05f;
		y += ((object.getY() - y ) - 563/2) * 0.05f;
		
		if(x <= 0) {
			x = 0;
		}
		
		if( x >= 1032+32) {
			x = 1032+32;
		}
		if(y <= 0) {
			y = 0;
		}
		if(y >= 563+64) {
			y=563+64;
		}
		
		
	}


	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}


	/**
	 * @param x the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}


	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}


	/**
	 * @param y the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	
	
	
}
