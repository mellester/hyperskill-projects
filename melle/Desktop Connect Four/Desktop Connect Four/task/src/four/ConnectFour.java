package four;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;
import javax.swing.*;

public class ConnectFour extends JFrame {

  static final int ROWS = 6;
  static final int COLUMNS = 7;

  boolean lastClick = false;
  Cell[][] cells = new Cell[COLUMNS][ROWS];

  public ConnectFour() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(350, 380);

    setLayout(new BorderLayout());
    setTitle("Connect Four");
    setBackground(Color.WHITE);
    // Add cells
    add(initAddCells(), BorderLayout.CENTER);

    // Add reset button
    JPanel southPanel = new JPanel();
    southPanel.add(
      new ResetButton((ActionEvent actionEvent) -> {
        reset();
      })
    );
    add(southPanel, BorderLayout.SOUTH);
    setVisible(true);
    changeCursor();
  }

  private JPanel initAddCells() {
    var panel = new JPanel();
    panel.setSize(350, 350);
    panel.setLayout(new GridLayout(ROWS, COLUMNS));
    // Reverse iteration
    for (int i = ROWS - 1; i >= 0; i--) {
      // Normal iteration
      for (int j = 0; j < COLUMNS; j++) {
        // Transposed creations
        var cell = new Cell(j, i);
        cells[j][i] = cell;
        panel.add(cell);
        // Add clicked listener
        cell.addActionListener((ActionEvent actionEvent) -> {
          cellClicked(cell);
        });
      }
    }
    return panel;
  }

  private void cellClicked(Cell cell) {
    System.out.println(cell.x + " " + cell.y);
    // Try and find the bottom most cell that hasn't been clicked yet.
    int row = 0;
    do {
      if (Objects.equals(cells[cell.x][row].clicked, " ")) {
        break;
      }
    }
    while(++row < ROWS);
    // Check if found
    if (row == ROWS) return;

    var bottomCell = cells[cell.x][row];
    bottomCell.click(lastClick);

    changeCursor();
    checkWinner();

  }

  private void checkWinner() {
    var winner = Cell.checkFourInARow(cells);
    if (winner != null) {
      setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
      //JOptionPane.showMessageDialog(this, "Winner is " + (lastClick.booleanValue() ? "O" : "X"));
      for (var winCell : winner) winCell.setColor(true);
      for (var column : cells) {
        for (var cell : column) {
          cell.setEnabled(false);
        }
      }
    }
  }

  private void reset() {
    for (var column : cells) {
      for (var cell : column) {
        cell.reset();
      }
    }
    lastClick = false;
    changeCursor();
  }

  private void changeCursor() {
    lastClick = !lastClick;
    setCursor(
      new Cursor(lastClick ? Cursor.CROSSHAIR_CURSOR : Cursor.WAIT_CURSOR)
    );
  }
}
