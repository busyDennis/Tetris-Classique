package tetris;
import java.awt.event.*; 
import java.awt.image.BufferedImage;
import java.awt.*; 
import java.applet.*; 
/**
 * Tetris game; 2012
 * Requires Java 1.6
 * @author Denis Bezverbnyy
 * Note: the structure of this class was taken from:
 * Beginning Java Game Programming, Second Edition by Jonathan S. Harbour.
 * (With changes)
 */

public class TetrisGame extends Applet implements KeyListener, Runnable {
  public static final int DELAY = 10;
  public static final double R_WDTH = 12;//rectangle width
  //position of a cup
  public static final double X = 10;
  public static final double Y = 10;
  public static final int ROWS = 25;
  public static final int COLUMNS = 12;
  //position of a small forecast cup
  public static final double F_X = 170;
  public static final double F_Y = 10;
  public static final int F_ROWS = 5;
  public static final int F_COLUMNS = 5;
  //position of a score field
  public static final double S_X = X;
  public static final double S_Y = 320;
  public static final double S_WIDTH = COLUMNS * R_WDTH;//same as for the forecast cup
  public static final double S_HEIGHT = 80;//same as for the forecast cup
  
  //score field color
  public static final Color S_COLOR = Color.LIGHT_GRAY ;
  
  private static int speedVar; //controls brick falling speed
  private static int speedControl = 40;//controls brick falling speed
  
  private boolean gameOver = false;//specifies if a game is over
  
  //the main thread becomes the game loop
  private Thread gameloop;
  //use this as a double buffer
  private BufferedImage backbuffer;
  //the main drawing object for the back buffer
  private Graphics2D g2d;
  
  private int brickColPos = (COLUMNS - Brick.BRICK_WIDTH) / 2;//initial column position of the brick
  private Brick brick;
  private Brick brickInNewPosition;
  private Brick forecastBrick;
  
  Cup cup;//game takes place in this "cup"
  Cup forecastCup;//predicts next shape
 
  private ScoreField scores;//displays scores
  
//===========================================================================
//Applet methods
  public void init() {
    this.setSize(500, 700);
    
    backbuffer = new BufferedImage(640, 980, BufferedImage.TYPE_INT_RGB);
    g2d = backbuffer.createGraphics();
    
    speedVar = 0;
    
    cup = new Cup(X, Y, R_WDTH, ROWS, COLUMNS);
    forecastCup = new Cup(F_X, F_Y, R_WDTH, F_ROWS, F_COLUMNS);
    
    brick = Brick.randomBrick(0, brickColPos);
    brick.draw(cup);
    forecastBrick = Brick.randomBrick(1, 1);
    forecastBrick.draw(forecastCup);
    
    scores = new ScoreField(S_X, S_Y, S_WIDTH, S_HEIGHT, S_COLOR);
    
    addKeyListener(this);
  }
  
  public void paint(Graphics g) {
    g.drawImage(backbuffer, 0, 0, this);
  }
  
  public void update(Graphics g) {
    g2d.setPaint(Color.WHITE);
    g2d.fillRect(0, 0, getSize().width, getSize().height);
    
    if(!gameOver){
      drawCups();
      drawScores();
    } else {
      g2d.setFont(new Font("", Font.BOLD, 16));
      g2d.setColor(Color.RED);
      g2d.drawString("GAME OVER", 60, 200);
    }
    paint(g);
  }
  
 public void start() {
   gameloop = new Thread(this);
   gameloop.start();
 }
 
 public void stop() {
     gameloop = null;
 }
//===========================================================================
//Thread methods
  /**
   Runs the game loop
   */
  public void run() {
    Thread t = Thread.currentThread();
    while (t == gameloop) {
      try {
        Thread.sleep(DELAY);
      }
      catch(InterruptedException e) {
          e.printStackTrace();
      }
      gameUpdate();
      repaint();
    }
  }
//=========================================================================
  /**
   Updates the game
   */
  private void gameUpdate() {
    //updateBrickShape();
    if(speedVar == 0){
      brickInNewPosition = brick.copyOf();
      brick.erase(cup);
      brickInNewPosition.moveDown();
      if(!brickInNewPosition.positionEmpty(cup)){
        brick.draw(cup);//redraw the deleted brick
        
        scores.incrementShapeCount();
        scores.incrementRowCount(cup.clearRows());
        
        forecastBrick.erase(forecastCup);//erase the forecasted brick
        brick = forecastBrick;//copy reference to the forecasted brick into the current brick var
        brick.moveTo(new CoordPoint(0, brickColPos));//set initial position
       
        if(!brick.positionEmpty(cup)){
          stop();
          gameOver = true;
          return;
        }
        
        brick.draw(cup);//draw the new brick
        
        forecastBrick = Brick.randomBrick(1, 1);//create new forecasted brick
        forecastBrick.draw(forecastCup);

      }else{
        brick = brickInNewPosition;
        brick.draw(cup);
      }
    }
    speedVar++;
    if(speedVar >= speedControl)
      speedVar %= speedControl;
  }
  
//=========================================================================
  
  public void drawCups(){
    cup.draw(g2d);
    forecastCup.draw(g2d);
  }
  
  public void drawScores(){
    scores.draw(g2d);
  }
//=========================================================================
// Key listeners  
  public void keyReleased(KeyEvent k) { }
  public void keyTyped(KeyEvent k) { }
  public void keyPressed(KeyEvent k) {
    int keyCode = k.getKeyCode();
    
    brickInNewPosition = brick.copyOf();
    brick.erase(cup);
      
    switch (keyCode) {

      case KeyEvent.VK_LEFT:
        brickInNewPosition.moveLeft();
        break;

      case KeyEvent.VK_RIGHT:
        brickInNewPosition.moveRight();
        break;

      case KeyEvent.VK_UP:
        brickInNewPosition.rotate();
        break;
        
      case KeyEvent.VK_SPACE:
        while(brickInNewPosition.positionEmpty(cup))
          brickInNewPosition.moveDown();
        brickInNewPosition.moveUp();
        break;
        
      case KeyEvent.VK_ENTER:        
      case KeyEvent.VK_CONTROL:
      case KeyEvent.VK_B:
          break;
    }
    if(brickInNewPosition.positionEmpty(cup)){
      brick = brickInNewPosition;
    }
    brick.draw(cup);
  }
}
