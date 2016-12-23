package tetris;
/**
 * Requires Java 1.6
 * Coordinate point has x, y - coordinates. Objects of this class are used to specify positions of a BrickPiece
 * in a Cup
 * @author Denis
 */
public class CoordPoint {
  private int x, y;
  
  public CoordPoint(int newX, int newY){
    x = newX;
    y = newY;
  }
  
  public int getX() { return x; }
  
  public int getY() { return y; }
  
  public void setLocation(int newX, int newY){
    x = newX;
    y = newY;
  }
  
  public CoordPoint copyOf(){
    return new CoordPoint(x, y);
  }
}
