package trabalhofinaljava2d;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Menu;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.WritableRaster;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


import utils.Utils;

public class MenuPrincipal extends JFrame implements KeyListener, ActionListener {
		MenuPanel panel;
	public static void main(String[] args) {
		JFrame frame = new MenuPrincipal();
		frame.setTitle("MenuPrincipal");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		JPanel panel = new MenuPanel();
//		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

	}

	public static Color borderColor;
	public static Color snakeColor;
	public static Color foodcolor;
	public static boolean borderColorSelected=false;
	public static boolean snakeColorSelected=false;
	public static boolean foodColorSelected=false;
	public static double sliderValue;
	PrinterJob pj;
	public MenuPrincipal() {

		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		
		panel = new MenuPanel();
		cp.add(panel,BorderLayout.CENTER);
		
		addKeyListener(this);
		this.setFocusable(true);

		JMenuBar jb = new JMenuBar();
		setJMenuBar(jb);

		JMenu menu = new JMenu("Game Settings");
		jb.add(menu);
		
		

		JMenu submenu = new JMenu("Change Color");
		menu.add(submenu);
		JMenuItem item = new JMenuItem("Game Board Color");
		item.addActionListener(this);
		submenu.add(item);
		item = new JMenuItem("Snake Color");
		item.addActionListener(this);
		submenu.add(item);
		item = new JMenuItem("Food Color");
		item.addActionListener(this);
		submenu.add(item);
		item = new JMenuItem("Background Contrast");
		item.addActionListener(this);
		menu.add(item);
		
		JMenu menuPrint = new JMenu("Print");
		jb.add(menuPrint);
		
		item = new JMenuItem("Print Panel");
		item.addActionListener(this);
		menuPrint.add(item);
		
		pj = PrinterJob.getPrinterJob();
		pj.setPrintable(panel);

	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_SPACE) {
			new Main();
			this.dispose();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String options = e.getActionCommand();

		if (options.equals("Game Board Color")) {
			new JColorChooser();
			borderColor = JColorChooser.showDialog(null, "Choose a Color", Color.black);
			borderColorSelected=true;
			repaint();
		} else if (options.equals("Snake Color")) {
			new JColorChooser();
			snakeColor = JColorChooser.showDialog(null, "Choose a Color", Color.black);
			snakeColorSelected = true;
			repaint();
		} else if (options.equals("Food Color")) {
			new JColorChooser();
			foodcolor = JColorChooser.showDialog(null, "Choose a Color", Color.black);
			foodColorSelected = true;
			repaint();
		} else if (options.equals("Background Contrast")) {
			JOptionPane optionPane = new JOptionPane();
			JSlider slider = getSlider(optionPane);
			optionPane.setMessage(new Object[] { "Select  Value",slider});
			optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
			JDialog dialog  = optionPane.createDialog(this, "Game Background Contrast");
			dialog.setVisible(true);
			String value = optionPane.getInputValue().toString();
			double valueDouble =Double.parseDouble(value);
			sliderValue= valueDouble/100;
			System.out.println(sliderValue);
		}else if(options.equals("Print Panel")) {
			if(pj.printDialog()) {
				try {
					pj.print();
				} catch (PrinterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	private JSlider getSlider(JOptionPane optionPane) {
		
		JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 9);
		

		slider.setMinorTickSpacing(1);
		slider.setMajorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		ChangeListener changeListener = new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider jslider = (JSlider) e.getSource();
				if(!jslider.getValueIsAdjusting()) {
					optionPane.setInputValue(jslider.getValue());
				}
			}
		};
		slider.addChangeListener(changeListener);
		return slider;
	}

}

class MenuPanel extends JPanel implements Printable {
	
	BufferedImage imageBg = null;
	//** ConvolveOp smooth
	float data[] = { 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f, 1 / 9f };
	Kernel k = new Kernel(3, 3, data);
	BufferedImageOp op = new ConvolveOp(k);
	Color stringColor;

	AffineTransform at = new AffineTransform();

	public MenuPanel() {
		imageBg = Utils.getImage(this, "images/Snake_BG.jpg");
		setPreferredSize(new Dimension(imageBg.getWidth(), imageBg.getHeight()));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		GradientPaint stringColor;
		Graphics2D g2 = (Graphics2D) g;

		Utils.drawBG(g2, imageBg, op, 0, 0);


		//** Font
		Font font = new Font("Verdana", Font.ITALIC, 30);
		FontRenderContext frc = g2.getFontRenderContext();

		Shape string = font.createGlyphVector(frc, "Press Space to Play").getOutline();
		
		//**Paint - Gradient
		stringColor = new GradientPaint((int) (string.getBounds().getX()), (int) (string.getBounds().getHeight() / 2),
				Color.blue, (int) (string.getBounds().getWidth()), (int) (string.getBounds().getHeight() / 2),
				Color.green);

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//** shape geom transf
		at.setToTranslation((getBounds().getWidth() - string.getBounds().getWidth()) / 2, 450);
		string = at.createTransformedShape(string);
		g2.setColor(Color.black);
		//Basic stroke
		Stroke bs = new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
	
		g2.setStroke(bs);

		g2.draw(string);
		g2.setPaint(stringColor);
		g2.fill(string);

	}
	//**print
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		
		
		switch (pageIndex) {
	      case 0:
	    	  paintComponent(graphics);
	            break;
	      default:
	        return NO_SUCH_PAGE;
	    }
	    return PAGE_EXISTS;
	
	}
}


