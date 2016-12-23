package tetris;
import java.awt.Color;
import java.util.Random;

/**
 * Requires Java 1.6
 * Tetris brick
 * @author Denis
 */
public class Brick{
  public static final int BRICK_WIDTH = 4;
  
  private CoordPoint[] points;//x is row, y is column
  private CoordPoint upperLeftCorner;
  private BrickType brickType;
  private Color color;
  BrickColor brickColor;
  private static Random rnd = new Random();
   
  /**
   * Create a brick
   * @param t brick type
   * @param bColor brick color
   * @param rowPos row position in the Cup
   * @param columnPos column position in the Cup
   */
  public Brick(BrickType t, BrickColor bColor, int rowPos, int columnPos){   
    brickType = t;
    brickColor = bColor;
    points = new CoordPoint[4];
    points = t.getPoints(rowPos, columnPos);
    upperLeftCorner = getUpperLeftCorner();
    
    color = bColor.getColor();
  }
  
  /**
   * Get random brick
   * @param rowPos row position in the Cup
   * @param columnPos column position in the Cup
   * @return random brick
   */
  public static Brick randomBrick(int rowPos, int columnPos){
    Brick brick = new Brick(BrickType.randomBrickType(), BrickColor.randomBrickColor(),
        rowPos, columnPos);
    
    int temp = rnd.nextInt(4);
    for(int i = 0; i <= temp; i++)
      brick.rotate();
    temp = rnd.nextInt(2);
    for(int i = 0; i <= temp; i++)
      brick.reflect();
    
    return brick;
  }
  
  /**
   * Get positions of all 4 square elements of the brick
   * @return positions of all 4 square elements of the brick in the Cup
   */
  public CoordPoint[] getPoints(){
    CoordPoint[] newPoints = new CoordPoint[4];
    for(int i = 0; i < 4; i++)
      newPoints[i] = points[i].copyOf();
    return newPoints;
  }
  
  /**
   * Set positions of all 4 square elements of the brick
   * @param pts positions of all 4 square elements of the brick in the Cup
   */
  public void setPoints(CoordPoint[] pts){
    if(pts.length != 4)
      throw new IllegalArgumentException("Array pts should contain 4 points\n");
    for(int i = 0; i < pts.length; i++)
      points[i] = pts[i];
  }
  
  /**
   * Get brick copy
   * @return brick deep copy
   */
  public Brick copyOf(){
    Brick brick = new Brick(brickType, brickColor, upperLeftCorner.getX(), upperLeftCorner.getY());
    brick.setPoints(getPoints());
    return brick;
  }
  
  /**
   * Get upper left corner of the brick
   * @return brick upper left corner position
   */
  private CoordPoint getUpperLeftCorner(){
    int ptX = points[0].getX();
    int ptY = points[0].getY();
    for(CoordPoint p : points){
      if(p.getX() < ptX)
        ptX = p.getX();
      if(p.getY() < ptY)
        ptY = p.getY();
    }
    return new CoordPoint(ptX, ptY);
  }
  
  /**
   * Check if position where the brick belongs is empty
   * @param c
   * @return
   */
  public boolean positionEmpty(Cup c){
    for(CoordPoint p : points)
      if(!c.positionEmpty(p))
        return false;
    return true;
  }
  
  /**
   * Move brick to position specified
   * @param aNewUpperLeftCorner new upper left corner of the brick
   */
  public void moveTo(CoordPoint aNewUpperLeftCorner){
    int incrX = aNewUpperLeftCorner.getX() - upperLeftCorner.getX();
    int incrY = aNewUpperLeftCorner.getY() - upperLeftCorner.getY();
    upperLeftCorner = aNewUpperLeftCorner;
    for(CoordPoint p : points)
      p.setLocation(p.getX() + incrX, p.getY() + incrY);
  }
  
  /**
   * Move brick one square down 
   */
  public void moveDown(){
    moveTo(new CoordPoint(upperLeftCorner.getX() + 1, upperLeftCorner.getY()));
  }
  
  /**
   * Move brick one square up 
   */
  public void moveUp(){
    moveTo(new CoordPoint(upperLeftCorner.getX() - 1, upperLeftCorner.getY()));
  }
  
  /**
   * Move brick one square left 
   */
  public void moveLeft(){
    moveTo(new CoordPoint(upperLeftCorner.getX(), upperLeftCorner.getY() - 1));
  }
  
  /**
   * Move brick one square right 
   */
  public void moveRight(){
    moveTo(new CoordPoint(upperLeftCorner.getX(), upperLeftCorner.getY() + 1));
  }
  
  /**
   * Draw brick
   * @param cup the brick is drawn inside a cup
   */
  public void draw(Cup cup){
    if(!positionEmpty(cup))
      throw new IllegalStateException("Position in the Cup occupied\n");
    for(CoordPoint p : points)
      cup.fillPosition(p, color);
  }
  
  /**
   * Erase brick
   * @param cup cup where brick was contained
   */
  public void erase(Cup cup){
    for(CoordPoint p : points)
      cup.freePosition(p);
  }
  
  /**
   * Rotate brick
   */
  public void rotate(){
    CoordPoint prevLeftCorner = upperLeftCorner.copyOf();
    for(CoordPoint p : points)
      p.setLocation(-p.getY(), p.getX()); 
    upperLeftCorner = getUpperLeftCorner();
    moveTo(prevLeftCorner);
  }
  
  /**
   * Reflect brick (used to obtain chiral bricks)
   */
  private void reflect(){
    CoordPoint prevLeftCorner = upperLeftCorner.copyOf();
    for(CoordPoint p : points)
      p.setLocation(-p.getX(), p.getY()); 
    upperLeftCorner = getUpperLeftCorner();
    moveTo(prevLeftCorner);
  }
  
}