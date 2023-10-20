package battleship;

public class Point {

  int x, y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public static Point parseCoordinate(String coordinate) {
    int x = letterToNumber(coordinate.charAt(0));
    int y = Integer.parseInt(coordinate.substring(1)) - 1;
    return new Point(x, y);
  }

  public static int letterToNumber(char letter) {
    return letter - 'A';
  }
}
