package tetris;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * Score field shows total number of figures that appeared in the game and total number of rows cleared
 * @author Denis
 */
public class ScoreField {
  private double x, y, width, height;
  private Rectangle2D.Double scoreBackground;
  private Color color;
  private int shapes;
  private int rows;
  
  /**
   * Create ScoreField
   * @param x ScoreField x location
   * @param y ScoreField y location
   * @param width ScoreField width
   * @param height ScoreField height
   * @param color ScoreField color
   */
  public ScoreField(double x, double y, double width, double height, Color color){
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.color = color;
    shapes = rows = 0;
    scoreBackground = new Rectangle2D.Double(x, y, width, height);
  }
  
  /**
   * Increment shape count
   */
  public void incrementShapeCount(){ shapes++; }
  
  /**
   * Increment row count
   * @param inc number to add to current row count
   */
  public void incrementRowCount(int inc){ rows += inc; }
  
  /**
   * Draw ScoreField
   * @param g2d
   */
  public void draw(Graphics2D g2d){
    g2d.setColor(color);
    g2d.fill(scoreBackground);
    
    g2d.setColor(Color.BLACK);
    g2d.draw(scoreBackground);
    g2d.drawString("Shapes:" + shapes, (float)x + 5, (float)y + 20);
    g2d.drawString("Rows:" + rows, (float)x + 5, (float)y + 40);
  }
}
