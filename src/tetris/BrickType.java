package tetris;
import java.util.Random;

/**
 * Requires Java 1.6
 * Specifies shape of a tetris brick
 * @author Denis
 */
public enum BrickType {
  L, I, S, T, O; //specified shape resembles a letter
  
  /**
   * Get random brick type
   * @return random brick type
   */
  public static BrickType randomBrickType() {
    int pick = new Random().nextInt(BrickType.values().length);
    return BrickType.values()[pick];
  }
  
  /**
   * Get array of CoordPoints specifying positions in a cup of each BrickPiece element of the brick
   * @param rowPos row position of the brick upper left corner
   * @param columnPos column position of the brick upper left corner
   * @return array of CoordPoints specifying positions in a cup of each BrickPiece element
   */
  public CoordPoint[] getPoints(int rowPos, int columnPos){
    CoordPoint[] points = new CoordPoint[4];
    switch (this) {
      case L:
        points[0] = new CoordPoint(rowPos, columnPos);
        points[1] = new CoordPoint(rowPos + 1, columnPos);
        points[2] = new CoordPoint(rowPos + 2, columnPos);
        points[3] = new CoordPoint(rowPos + 2, columnPos + 1);
        break;
      case I:
        points[0] = new CoordPoint(rowPos, columnPos);
        points[1] = new CoordPoint(rowPos + 1, columnPos);
        points[2] = new CoordPoint(rowPos + 2, columnPos);
        points[3] = new CoordPoint(rowPos + 3, columnPos);
        break;
      case S:
        points[0] = new CoordPoint(rowPos, columnPos);
        points[1] = new CoordPoint(rowPos + 1, columnPos);
        points[2] = new CoordPoint(rowPos + 1, columnPos + 1);
        points[3] = new CoordPoint(rowPos + 2, columnPos + 1);
        break;
      case T:
        points[0] = new CoordPoint(rowPos, columnPos + 2);
        points[1] = new CoordPoint(rowPos + 1, columnPos + 1);
        points[2] = new CoordPoint(rowPos, columnPos + 1);
        points[3] = new CoordPoint(rowPos, columnPos);
        break;
      case O:
        points[0] = new CoordPoint(rowPos, columnPos);
        points[1] = new CoordPoint(rowPos, columnPos + 1);
        points[2] = new CoordPoint(rowPos + 1, columnPos);
        points[3] = new CoordPoint(rowPos + 1, columnPos + 1);
        break;
      default:
        return null;
    }
    return points;
  }
}
