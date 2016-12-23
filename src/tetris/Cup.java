package tetris;
import java.math.BigDecimal;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * Square bordered space for the tetris game
 * @author Denis
 */
public class Cup {
  
  //coordinates in a plain and a single square block width
  private double x, y, blockWidth;
  private double coordX[][], coordY[][];
  
  private int rows, columns;
  
  private BrickPiece[][] brickPieces;//keeps all the bricks
  
  Rectangle2D.Double cupSpace; //background
  
  /**
   * Create cup
   * @param cX x position
   * @param cY y position
   * @param givenBlockWidth width of a tetris square
   * @param r number of rows in the cup
   * @param c number of columns in the cup
  */
  public Cup(double cX, double cY, double givenBlockWidth, int r, int c){
    x = cX;
    y = cY;
    blockWidth = givenBlockWidth;
    rows = r;
    columns = c;
    
    BigDecimal currX = BigDecimal.valueOf(x);
    BigDecimal currY = BigDecimal.valueOf(y);
    coordX = new double[rows + 1][columns + 1];
    coordY = new double[rows + 1][columns + 1];
    for(int i = 0; i <= rows; i++){
      for(int j = 0; j <= columns; j++){
        coordX[i][j] = currX.doubleValue();
        coordY[i][j] = currY.doubleValue();
        currX = currX.add(BigDecimal.valueOf(blockWidth));
      }
      currX = BigDecimal.valueOf(x);
      currY = currY.add(BigDecimal.valueOf(blockWidth));
    }
    
    cupSpace = new Rectangle2D.Double(coordX[0][0], coordY[0][0], columns * blockWidth, rows * blockWidth);
    
    brickPieces = new BrickPiece[rows][columns];
  }
  
  /**
   * Fill specified position
   * @param p location in the cup (row, column)
   * @param c color
   */
  public void fillPosition(CoordPoint p, Color c){
    int ptX = p.getX();
    int ptY = p.getY();
    brickPieces[ptX][ptY] = new BrickPiece(coordX[ptX][ptY], coordY[ptX][ptY], blockWidth);
    brickPieces[ptX][ptY].setColor(c);
  }
  
  /**
   * Fill specified position
   * @param p location in the cup (row, column)
   */
  public void freePosition(CoordPoint p){
    brickPieces[p.getX()][p.getY()] = null;
  }
  
  /**
   * Check if specified position is empty
   * @param p position (row, column)
   * @return true if position empty
   */
  public boolean positionEmpty(CoordPoint p){
    int ptX = p.getX();
    int ptY = p.getY();
    if(ptX < 0 || ptX >= rows)
      return false;
    if(ptY < 0 || ptY >= columns)
      return false;
    return (brickPieces[ptX][ptY] == null) ? true :  false;
  }
  
  /**
   * Clear filled rows
   * @return number of rows cleared
  */
  public int clearRows(){
    int rCount = 0;
    for(int i = 0; i < rows; i++){
      for(int j = 0; j < columns; j++){
        if(brickPieces[i][j] == null)
          break;
        else if(j == columns - 1){
          rCount++;
          
          for(int k = i; k > 0; k--){
            for(int l = 0; l < columns; l++){
              brickPieces[k][l] = brickPieces[k - 1][l];
              if(brickPieces[k - 1][l] != null)
                brickPieces[k][l].moveTo(coordX[k][l], coordY[k][l]);
            }
          }
        }
      }
    }
    return rCount;
  }
  
  /**
   * Draw cup
   * @param g2d Graphics2D object to draw on
   */
  public void draw(Graphics2D g2d){
    g2d.setColor(Color.GRAY);
    g2d.fill(cupSpace);
    
    g2d.setColor(Color.BLACK);
    g2d.draw(cupSpace); 
    Line2D.Double line = new Line2D.Double();
    for(int i = 0; i <= rows; i++){
      line.setLine(coordX[i][0], coordY[i][0], coordX[i][columns], coordY[i][columns]);
      g2d.draw(line);
    }
    for(int i = 0; i <= columns; i++){
      line.setLine(coordX[0][i], coordY[0][i], coordX[rows][i], coordY[rows][i]);
      g2d.draw(line);
    }
    
    for(int i = 0; i < brickPieces.length; i++)
      for(int j = 0; j < brickPieces[0].length; j++)
        if(brickPieces[i][j] != null)
          brickPieces[i][j].draw(g2d);
  }
}
