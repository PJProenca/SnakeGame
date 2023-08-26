package trabalhofinaljava2d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.Menu;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;

import utils.Utils;

public class Splash extends JFrame {

	static JFrame splashFrame;

	public Splash() {
		JPanel panel = new MyPanel();
		getContentPane().add(panel);

	}

	public static void main(String[] args) {

		splashFrame = new JFrame();
		splashFrame.setUndecorated(true);
		splashFrame.setLayout(new BorderLayout());

		JPanel splashPanel = new SplashPanel();
		splashFrame.add(splashPanel, BorderLayout.CENTER);

		splashPanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				createApplicationFrame();

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		splashFrame.pack();
		splashFrame.setLocationRelativeTo(null);
		splashFrame.setVisible(true);
	}

	public static void createApplicationFrame() {

		splashFrame.dispose();
		JFrame frame = new Splash();
		frame.setTitle("Splash Screen");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}

class SplashPanel extends JPanel implements ActionListener {
	Timer timer;

	BufferedImage image = null;
	boolean grow = false;

	int width = 20;
	int height = 20;
	AffineTransform at = new AffineTransform();

	public SplashPanel() {
		setPreferredSize(new Dimension(600, 600));
		timer = new Timer(100, this);
		timer.start();
		image = Utils.getImage(this, "images/leather.jpg");
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.red);

		g2.drawImage(image, 0, 0, width, height, null);
		
		if (grow) {
			// ** Font
			Font font = new Font("Ink Free", Font.BOLD, 50);
			FontRenderContext frc = g2.getFontRenderContext();

			Shape string = font.createGlyphVector(frc, "Java 2D").getOutline();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			at.setToTranslation((getBounds().getWidth() - string.getBounds().getWidth()) / 2, 400);
			string = at.createTransformedShape(string);
			g2.setColor(Color.white);
			g2.fill(string);
			string = font.createGlyphVector(frc, "Press Mouse Button").getOutline();
			at.setToTranslation((getBounds().getWidth() - string.getBounds().getWidth()) / 2, 450);
			string = at.createTransformedShape(string);
			g2.setColor(Color.white);
			g2.fill(string);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		resizeImage();
		repaint();

	}

	public void resizeImage() {
		if (width <= getBounds().getWidth()) {
			width += 10;
			height += 10;
		}

		if (width == getBounds().getWidth()) {
			grow = true;
		}
	}
}

class MyPanel extends JPanel implements ActionListener, MouseWheelListener {
	int width = 50;
	int height = 50;
	int x = 150;
	int y = 150;
	int rectX = 400;
	int rectY = 400;
	int rectWidth = 50;
	int rectHeight = 50;
	boolean shapeApple = false;
	boolean twoSquares = false;
	AffineTransform at = new AffineTransform();
	double scale = 0.5f;
	boolean selected =false;
	int firstX, firstY;

	int deltaX, deltaY;
	Area a1;
	public MyPanel() {
		setPreferredSize(new Dimension(600, 600));
		Timer timer = new Timer(75, this);
		timer.start();
		addMouseWheelListener(this);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// paint - Texture paint creation
		BufferedImage paintDust = Utils.getImage(this, "images/GoldDust.jpg");
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.blue);
		
		
		Shape shape = Utils.getCircle(x, y, width, height);
		g2.fill(shape);

		if (shapeApple) {

			at.setToTranslation(100, 100);
			Shape apple = Utils.getApple(g2, 0, 0);
			apple = at.createTransformedShape(apple);
			g2.fill(apple);
			Shape stick = Utils.getAppleStick(g2, 0, 0);
			stick = at.createTransformedShape(stick);
			g2.fill(stick);
			Shape leaf = Utils.getLeaf(g2, 0, 0);
			leaf = at.createTransformedShape(leaf);
			g2.fill(leaf);
			twoSquares = true;
		}
		if (twoSquares) {

			at.setToIdentity();
			at.setToTranslation(100, 300);
			Shape sqr1 = new Rectangle2D.Double(0, 0, 100, 100);
			sqr1 = at.createTransformedShape(sqr1);
			//** Area 
			Area a1 = new Area(sqr1);
			//** shape transformations
			at.setToIdentity();
			at.setToTranslation(150, 280);
			at.rotate(Math.toRadians(45));
			
			Shape sqr2 = new Rectangle2D.Double(0, 0, 100, 100);
			sqr2 = at.createTransformedShape(sqr2);
			
			Area a2 = new Area(sqr2);
			//** add areas
			a1.add(a2);
			//** set texture Paint
			TexturePaint tp = new TexturePaint(paintDust, new Rectangle2D.Double(150, 150, 150, 150));
			g2.setPaint(tp);
			g2.fill(a1);
		}

		Shape rect = new Rectangle2D.Double(rectX, rectY, rectWidth, rectHeight);
		g2.setColor(Color.blue);
		//** shape transformations
		at.setToIdentity();
		at.translate(rectX, rectY);
		at.scale(scale, scale);
		at.translate(-rectX - (rectWidth / 2), -rectY - (rectHeight / 2));
		rect = at.createTransformedShape(rect);
		g2.fill(rect);

	}

	public void moveCircle() {
		if (width <= 100) {
			width += 5;
			height += 5;
		}

		if (width >= 100) {
			if (x < 450) {
				x += 10;
			}
			if (x == 450)
				x = x;
			shapeApple = true;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		moveCircle();
		repaint();

	}
	//** mouse interation
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		double delta = e.getPreciseWheelRotation();
		scale += delta / 10;

	}

	

}
