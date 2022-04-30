package chess.classes;

import chess.enums.Color;

/** Represents a chessboard. */
public class Board {
  private BasePiece[][] board;

  private int turnNumber;

  public Board() {
    this.turnNumber = 0;
    this.board = Board.createBoard();
  }

  /**
   * Creates a 2D array representing the starting posistions for a chessboard.
   *
   * <p>TODO: Add non-king pieces
   */
  private static BasePiece[][] createBoard() {
    BasePiece[][] b = new BasePiece[8][8];

    b[7][4] = new King(Color.WHITE);
    b[0][3] = new King(Color.BLACK);

    return b;
  }

  /**
   * Moves a piece on the board.
   *
   * @param m The move to do
   * @return True if the move is valid, False otherwise
   */
  public boolean move(Move m) {
    // TODO: this whole method
    Color currentPlayer = this.turnNumber % 2 == 0 ? Color.WHITE : Color.BLACK;
    // Find the piece that's being be moved

    // verify that the move is valid
    // piece.isValidMove(fromFile, fromRank, toFile, toRank);

    // Check that the move does not hop over any pieces (unless piece is a
    // knight).

    // Move the piece
    this.turnNumber++;
    return true;
  }

  /**
   * String representation of the board.
   *
   * @implNote Uses terminal escape codes to highlight each square.
   */
  public String toString() {
    String rankDividor = "\n|---|---|---|---|---|---|---|---|";
    String output = "" + rankDividor;
    for (BasePiece[] rank : this.board) {
      output += "\n|";
      for (BasePiece piece : rank) {
        output += " " + (piece == null ? " " : piece.toString()) + " |";
      }
      output += rankDividor;
    }
    return output;
  }
}
