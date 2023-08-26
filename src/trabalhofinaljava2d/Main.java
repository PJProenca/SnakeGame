package trabalhofinaljava2d;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {

	public Main() {
		
		GamePanel game = new GamePanel();
		add(game);		
		setTitle("Jogo Snake");
		setResizable(true);
		setVisible(true);		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();	
		setLocationRelativeTo(null);
	}
}
