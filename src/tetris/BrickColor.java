package tetris;
import java.awt.Color;
import java.util.Random;

/**
 * Requires Java 1.6
 * Specifies tetris brick colors
 * @author Denis
 */
public enum BrickColor {
  BLUE, RED, GREEN, YELLOW;
  
  /**
   * Get random brick color
   * @return random brick color
   */
  public static BrickColor randomBrickColor() {
    int pick = new Random().nextInt(BrickColor.values().length);
    return BrickColor.values()[pick];
  }
  
  /**
   * Get color specified by object of this class
   * @return
   */
  public Color getColor(){
    switch (this) {
      case BLUE:
        return Color.BLUE;
      case RED:
        return Color.RED;
      case GREEN:
        return Color.GREEN;
      case YELLOW:
        return Color.YELLOW;
    }
    return null;
  }
}
