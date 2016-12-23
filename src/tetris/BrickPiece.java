package tetris;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Rectangular brick piece for the tetris game
 * @author Denis
 */
public class BrickPiece {
  public static final Color DEFAULT_COL = Color.BLUE;
  
  Rectangle2D r;
  Color color;
  
  /**
   * Create new BrickPiece
   * @param xc BrickPiece x coordinate
   * @param yc BrickPiece y coordinate
   * @param w BrickPiece width
   */
  public BrickPiece(double xc, double yc, double w){
    r = new Rectangle2D.Double(xc, yc, w, w);
    color = DEFAULT_COL;
  }
  
  /**
   * Draw brick
   * @param g2 Graphics2D object to draw on
   */
  public void draw(Graphics2D g2)
  {
    g2.setColor(color);
    g2.fill(r);
    g2.setColor(Color.BLACK);
    g2.draw(r);
  }
  
  /**
   * Get x position of the BrickPiece
   * @return x position of the BrickPiece
   */
  public double getX() { return r.getX(); }
  
  /**
   * Get y position of the BrickPiece
   * @return y position of the BrickPiece
   */
  public double getY() { return r.getY(); }
  
  /**
   * Relocate the BrickPiece
   * @param xLoc new x position of the BrickPiece
   * @param yLoc new y position of the BrickPiece
   */
  public void moveTo (double xLoc, double yLoc)
  {
    r.setRect(xLoc, yLoc, r.getWidth(), r.getWidth());
  }
  
  /**
   * Set BrickPiece color
   * @param c BrickPiece color
   */
  public void setColor(Color c){
    color = c;
  }
  
}
