package utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Utils {
	public static BufferedImage getImage(Object obj, String imageFileName) {
		BufferedImage bi = null;
		URL imageSource = null;
		imageSource = obj.getClass().getClassLoader().getResource(imageFileName);
		try {
			bi = ImageIO.read(imageSource);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Image couldn´t be readed!!");
		}
		return bi;
	}

	public static void drawAxis(Graphics2D g2, Color c, int lenght, int width) {
		g2.setColor(c);
		g2.setStroke(new BasicStroke(width));
		g2.drawLine(-lenght, 0, lenght, 0);
		g2.drawLine(0, -lenght, 0, lenght);
	}
	
	public static void drawBG(Graphics2D g2,BufferedImage bg, BufferedImageOp op,int x, int y) {
		g2.drawImage(bg, op,x,y);	
	}
	
	public static BufferedImage RGBToGray(BufferedImage imageIn) {
		BufferedImage imageOut = new BufferedImage(imageIn.getWidth(), imageIn.getHeight(), imageIn.getType());
		WritableRaster rasteImageIn = imageIn.getRaster();
		WritableRaster rasterImageOut = imageOut.getRaster();
		int[] rgba = new int[3];
		for (int col = 0; col < imageIn.getWidth(); col++) {
			for (int line = 0; line < imageIn.getHeight(); line++) {
				rasteImageIn.getPixel(col, line, rgba);
				int gray = (int)(rgba[0] + rgba[1]+rgba[2]/3f);
				rgba[0] = rgba[1] = rgba[2] = gray;			
				rasterImageOut.setPixel(col, line, rgba);
			}
		}
		return imageOut;
	}
	
	public static Shape getApple(Graphics2D g2, int x, int y) {
		int x0 = x;
		int y0 = y;
		int x1 = x0 + 25;
		int y1 = y0 - 15;
		int x2 = x1 + 25;
		int y2 = y1 + 15;
		int x3 = x2 + 25;
		int y3 = y2 + 25;
		int x4 = x3 - 25;
		int y4 = y3 + 45;
		int x5 = x4 - 50;
		int y5 = y4 + 55;
		int x6 = x5 - 50;
		int y6 = y5 - 55;
		int x7 = x6 - 25;
		int y7 = y6 - 45;
		int x8 = x7 + 25;
		int y8 = y7 - 25;
		int x9 = x8 + 25;
		int y9 = y8 - 15;

		g2.setColor(new Color(224, 19, 36));
		GeneralPath path = new GeneralPath();

		path.moveTo(x0, y0);
		path.quadTo(x1, y1, x2, y2);
		path.quadTo(x3, y3, x4, y4);
		path.quadTo(x5, y5, x6, y6);
		path.quadTo(x7, y7, x8, y8);
		path.quadTo(x9, y9, x0, y0);
		path.closePath();
		return path;
	}
	
	public static Shape getAppleStick(Graphics2D g2, int x, int y) {
		int x0 = x;
		int y0 = y;
		int x1 = x0 - 5;
		int y1 = y0 - 20;
		int x2 = x1 + 15;
		int y2 = y1 - 20;
		int x3 = x2 + 5;
		int y3 = y2 + 2;
		int x4 = x3 - 15;
		int y4 = y3 + 18;

		g2.setColor(new Color(155, 103, 60));
		GeneralPath path = new GeneralPath();
		
		path.moveTo(x0, y0);
		path.quadTo(x1, y1, x2, y2);
		path.lineTo(x3, y3);
		path.quadTo(x4, y4, -x0, y0);
		path.closePath();
		return path;
	}

	public static Shape getLeaf(Graphics2D g2, int x, int y) {
		int x0 = x;
		int y0 = y;
		int x1 = x0+20;
		int y1 = y0 - 22;
		int x2 = x1 + 25;
		int y2 = y1 + 30;
		int x3 = x2 + 30;
		int y3 = y2 - 30;
		int x4 = x3 - 40;
		int y4 = y3 + 40;
		
		GeneralPath path = new GeneralPath();
		g2.setColor(Color.green);
		
		path.moveTo(x0, y0);
		path.curveTo(x1, y1, x2,y2,x3, y3);
		path.quadTo(x4, y4, x0, y0);
		path.closePath();		
		return path;
	}
	
	public static Shape getCircle(double xCenter,double yCenter, double r,int points) {
		
		GeneralPath path = new GeneralPath();
		
		for(int i=0;i<points;i++) {
			double angle = i /(double) points * Math.PI *2;
			double x = r * Math.cos(angle) + xCenter;
			double y = r * Math.sin(angle) + yCenter;
			if(i == 0) {
				path.moveTo(x, y);
			}else {
				path.lineTo(x, y);
			}
		}
		path.closePath();
		return path;
	}
}
	
