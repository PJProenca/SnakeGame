package trabalhofinaljava2d;

import java.awt.event.*;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.RescaleOp;
import java.awt.*;
import java.awt.color.ColorSpace;

import javax.swing.*;

import utils.Utils;

import java.util.Random;

public class GamePanel extends JPanel implements ActionListener,KeyListener{



    static  int panelWidth = 610;
    static  int panelHeight = 610;
    static  int bodyPointSize = 20;
    static  int nPoints = (panelWidth*panelHeight)/bodyPointSize;
    static  int delay = 75;

    int x[] = new int[nPoints];
    int y[]  =new int[nPoints];

    int bodyParts = 6;
    int foodAte;
    int foodX;
    int foodY;
    char direction = 'R';
    boolean isRunning = false;

    Timer timer;
    Random random;
    BufferedImage imageBg =null;
    BufferedImageOp op =null;
    
    boolean paused =false;
    
    int boardwidth;
    int boardHeight;
    // items color
    Color borderColor = Color.green;
    Color snakeColor = Color.green;
    Color foodColor = Color.red;
    //stroke Pattern
    float[] dash= {5.0f,5.0f};
    float dashphase=5.0f;
    
    GamePanel(){
        random  = new Random();
        this.setPreferredSize(new Dimension(panelWidth, panelHeight));
        this.setFocusable(true);
        addKeyListener(this);
        boardwidth = panelWidth;
        boardHeight = panelHeight;


        startGame();
    }
    public void startGame() {
        newFood();
        isRunning = true;
        timer =new Timer(delay, this);
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        //image transformations
        int newWidth = (int)getBounds().getWidth();
		int newHeight = (int)getBounds().getHeight();	
        imageBg = Utils.getImage(this, "images/Snake_BG.jpg");
        //** image resizing on expand panel 
		BufferedImage resizeImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
		Graphics2D imgGraph = resizeImage.createGraphics();
		//** change image to gray
		op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
		imageBg = op.filter(imageBg, imageBg);	
		// ** change image contrast
		float val = (float) MenuPrincipal.sliderValue;
		op = new RescaleOp(0.08f, 0, null);
		imageBg = op.filter(imageBg, imageBg);
		
		//draw resized image
		g2.drawImage(imageBg,0,0,newWidth,newHeight,null);
		gameDraw(g2);
		
		// create game border
		Shape border = new Rectangle2D.Double(getBounds().getCenterX() - (boardwidth/2),getBounds().getCenterY() - (boardHeight/2),boardwidth-1,boardHeight-1);
		if(!MenuPrincipal.borderColorSelected) {
			g2.setColor(borderColor);
		}else {
			g2.setColor(MenuPrincipal.borderColor);
		}
		
		//** stroke - Dash
		Stroke stroke = new BasicStroke(3,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL,0,dash,dashphase);
		g2.setStroke(stroke);
		g2.draw(border);
    }
    public void gameDraw(Graphics2D g2) {
        if (isRunning) {
           if(!MenuPrincipal.foodColorSelected) {
        	   g2.setColor(foodColor);
           }else {
        	   g2.setColor(MenuPrincipal.foodcolor);
           }
            // Primitive - oval
            g2.fillOval(foodX, foodY, bodyPointSize, bodyPointSize);
    
            for(int i = 0; i<bodyParts; i++){
                if(i == 0){
                	if(!MenuPrincipal.snakeColorSelected) {
                		g2.setColor(snakeColor);
                	}else {
                		g2.setColor(MenuPrincipal.snakeColor);
                	}
                    // Primitive - rect
                    g2.fillRect(x[i], y[i], bodyPointSize, bodyPointSize);
                }else{
                    g2.fillRect(x[i], y[i], bodyPointSize, bodyPointSize);
                }
            }
            g2.setColor(Color.WHITE);
            //** Font
            String score = "Score: "+foodAte;
            Font font = new Font("Verdana",Font.ITALIC,30);
            g2.setFont(font);
            FontMetrics fm = g2.getFontMetrics(font);
             int stringSize = fm.stringWidth(score);
            g2.drawString(score,(panelWidth-stringSize)/2, g2.getFont().getSize());
        }
        
        else{
            gameOver(g2);
        }
    }
    public void move() {
        for(int i = bodyParts; i>0; i--){
            x[i] = x[i-1]; 
            y[i] = y[i-1]; 
        }
        switch(direction){
            case 'U':
                y[0] = y[0] - bodyPointSize;
                break;
            case 'D':
                y[0] = y[0] + bodyPointSize;
                break;
            case 'L':
                x[0] = x[0] - bodyPointSize;
                break;
            case 'R':
                x[0] = x[0] + bodyPointSize;
                break;
        }
    }
    public void newFood(){
        foodX =random.nextInt((int)(panelWidth/bodyPointSize))*bodyPointSize;
        foodY =random.nextInt((int)(panelHeight/bodyPointSize))*bodyPointSize;
    }
    public void checkFood(){
        if((x[0]==foodX)&&(y[0]==foodY)){
            bodyParts++;
            foodAte++;
            newFood();
        }
    }
    public void checkCollision() {
        //check if snake collides with itself
        for(int i= bodyParts; i>0; i--){
            if((x[0] == x[i])&&y[0]==y[i]){
                isRunning = false;
            }
        }
        //check if the snake touchs the left border
        if(x[0]<0){
            isRunning = false;
        }
        //check if the snake touchs the right border
        if(x[0]>=panelWidth){
            isRunning = false;
        }
        //check if the snake touchs the top border
        if(y[0]<0){
            isRunning =false;
        }
        //check if the snake touchs the buttom border
        if(y[0]>=panelHeight){
            isRunning =false;
        }
        if (!isRunning) {
            timer.stop();
        }
        

    }
    public void gameOver(Graphics2D g2) {
    	 g2.setColor(Color.RED);
    	 // ** Font
         String gameOver="You're dead!!Best luck next time";
         Font font = new Font("Seriff",Font.BOLD,40);
         g2.setFont(font);
         FontMetrics fm = g2.getFontMetrics(font);
         int stringSize = fm.stringWidth(gameOver);
         g2.drawString(gameOver,(panelWidth-stringSize)/2, panelHeight/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(isRunning){
            move();
            checkFood();
            checkCollision();
        }
        repaint();

    }


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if(direction!='R'){
                    direction = 'L';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(direction!='L'){
                    direction = 'R';
                }
                break;
            case KeyEvent.VK_UP:
                if(direction!='D'){
                    direction = 'U';
                }
                break;
            case KeyEvent.VK_DOWN:
                if(direction!='U'){
                    direction = 'D';
                }
                break;
            case KeyEvent.VK_ESCAPE:
            	if(!paused) {
            		paused=true;
            		timer.stop();
            		
            	}else {
            		paused = false;
            		timer.restart();
            	}
            	break;

        }
    }
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}