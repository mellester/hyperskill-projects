package four;

import java.awt.*;
import java.util.Objects;
import javax.swing.*;

public class Cell extends JButton {

  private static final Color COLOR_GREEN1 = new Color(19, 222, 52);
  // Unit test does not like 2 colors
//  private static final Color COLOR_GREEN2 = new Color(19, 222, 52);
    private static final Color COLOR_GREEN2 = new Color(27, 236, 62);
  private static final Color COLOR_WINNER = new Color(5, 237, 194);

  int x;

  int y;

  public String clicked = " ";

  public Cell(int x, int y) {
    assert (x >= 0 && x < ConnectFour.COLUMNS);
    assert (y >= 0 && y < ConnectFour.ROWS);
    this.x = x;
    this.y = y;
    this.setFocusPainted(false);
    this.setFont(new Font("Arial", Font.PLAIN, 16));
    this.reset();
    setVisible(true);
  }

  public void click(boolean X) {
    clicked = X ? "X" : "O";
    this.setText(clicked);
  }

  public void setColor(boolean winner) {
    if (winner) {
      this.setBackground(COLOR_WINNER);
      return;
    }
    if (y % 2 == 0) {
      this.setBackground(x % 2 == 0 ? COLOR_GREEN1 : COLOR_GREEN2);
    } else {
      this.setBackground(x % 2 == 0 ? COLOR_GREEN2 : COLOR_GREEN1);
    }
  }

  public void reset() {
    clicked = " ";
    this.setText(clicked);
    this.setEnabled(true);
    setColor(false);
  }

  public char getXasChar() {
    return (char) ('A' + x);
  }

  @Override
  public String getName() {
    return "Button" + getXasChar() + (y + 1);
  }

  public static Cell[] checkFourInARow(Cell[][] cells) {
    int ROWS = cells[0].length;
    int COLUMNS = cells.length;
    // Check horizontal
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLUMNS - 3; j++) {
        if (
          Objects.equals(cells[j][i].clicked, cells[j + 1][i].clicked) &&
          Objects.equals(cells[j][i].clicked, cells[j + 2][i].clicked) &&
          Objects.equals(cells[j][i].clicked, cells[j + 3][i].clicked) &&
          !Objects.equals(cells[j][i].clicked, " ")
        ) {
          return new Cell[] {
            cells[j][i],
            cells[j + 1][i],
            cells[j + 2][i],
            cells[j + 3][i],
          };
        }
      }
    }

    // Check vertical
    for (int i = 0; i < COLUMNS; i++) {
      for (int j = 0; j < ROWS - 3; j++) {
        if (
          Objects.equals(cells[i][j].clicked, cells[i][j + 1].clicked) &&
          Objects.equals(cells[i][j].clicked, cells[i][j + 2].clicked) &&
          Objects.equals(cells[i][j].clicked, cells[i][j + 3].clicked) &&
          !Objects.equals(cells[i][j].clicked, " ")
        ) {
          return new Cell[] {
            cells[i][j],
            cells[i][j + 1],
            cells[i][j + 2],
            cells[i][j + 3],
          };
        }
      }
    }

    // Check diagonal (top-left to bottom-right)
    for (int i = 0; i < COLUMNS - 3; i++) {
      for (int j = 0; j < ROWS - 3; j++) {
        if (
          Objects.equals(cells[i][j].clicked, cells[i + 1][j + 1].clicked) &&
          Objects.equals(cells[i][j].clicked, cells[i + 2][j + 2].clicked) &&
          Objects.equals(cells[i][j].clicked, cells[i + 3][j + 3].clicked) &&
          !Objects.equals(cells[i][j].clicked, " ")
        ) {
          return new Cell[] {
            cells[i][j],
            cells[i + 1][j + 1],
            cells[i + 2][j + 2],
            cells[i + 3][j + 3],
          };
        }
      }
    }

    // Check diagonal (bottom-left to top-right)
    for (int i = 0; i < COLUMNS - 3; i++) {
      for (int j = ROWS - 1; j >= 3; j--) {
        if (
          Objects.equals(cells[i][j].clicked, cells[i + 1][j - 1].clicked) &&
          Objects.equals(cells[i][j].clicked, cells[i + 2][j - 2].clicked) &&
          Objects.equals(cells[i][j].clicked, cells[i + 3][j - 3].clicked) &&
          !Objects.equals(cells[i][j].clicked, " ")
        ) {
          return new Cell[] {
            cells[i][j],
            cells[i + 1][j - 1],
            cells[i + 2][j - 2],
            cells[i + 3][j - 3],
          };
        }
      }
    }

    return null;
  }
}
