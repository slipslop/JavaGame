import java.awt.Dimension;

import javax.swing.JFrame;

public class Window {

									//We add Game class to our JFrame
	public Window(int width, int height, String title, Game game) {
		
		JFrame frame = new JFrame(title);
		
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setResizable(false);
		
		frame.add(game);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		
		
	}
	
	
}
