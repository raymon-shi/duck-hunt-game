import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Game implements Runnable {	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}

	public void run() {
		
		JFrame frame = new JFrame("DUCKHUNT");
		//where it pops up
		frame.setLocation(300, 300);
		
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);
		
		Game_Canvas gameBoard = new Game_Canvas();
		frame.add(gameBoard, BorderLayout.CENTER);
		
		frame.pack();
		frame.getContentPane().addMouseListener(new MouseClickListener());
//		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		gameBoard.reset();
	}
}
