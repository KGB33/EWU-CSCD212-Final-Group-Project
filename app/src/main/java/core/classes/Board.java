package core.classes;

import core.enums.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/** Represents a chessboard. */
public class Board {
  private BasePiece[][] board;

  private int turnNumber;
  private boolean gameOver;
  private Color winner;

  private ArrayList<Move> moves;

  private static final Map<Character, Integer> rank;
  private static final Map<Character, Integer> file;

  // Called a static initialization block
  // https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
  static {
    final char[] ranks = {'8', '7', '6', '5', '4', '3', '2', '1'};
    final char[] files = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    final Map<Character, Integer> rMap = new HashMap<>();
    final Map<Character, Integer> fMap = new HashMap<>();
    for (Integer i = 0; i < 8; i++) {
      rMap.put(Character.valueOf(ranks[i]), i);
      fMap.put(Character.valueOf(files[i]), i);
    }
    rank = Collections.unmodifiableMap(rMap);
    file = Collections.unmodifiableMap(fMap);
  } // End static

  public Board() {
    this.turnNumber = 0;
    this.gameOver = false;
    this.winner = null;
    this.board = Board.createBoard();
    this.moves = new ArrayList<Move>();
  }

  /**
   * Creates a 2D array representing the starting posistions for a chessboard.
   *
   * <p>TODO: Add non-king pieces
   */
  private static BasePiece[][] createBoard() {
    final BasePiece[][] b = new BasePiece[8][8];
    final char[] files = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

    // White Pieces

    b[Board.rank.get('1')][Board.file.get('a')] = new Rook(Color.WHITE);
    b[Board.rank.get('1')][Board.file.get('a')].setCurrent('a', '1');
    b[Board.rank.get('1')][Board.file.get('b')] = new Knight(Color.WHITE);
    b[Board.rank.get('1')][Board.file.get('b')].setCurrent('b', '1');
    b[Board.rank.get('1')][Board.file.get('c')] = new Bishop(Color.WHITE);
    b[Board.rank.get('1')][Board.file.get('c')].setCurrent('c', '1');
    b[Board.rank.get('1')][Board.file.get('d')] = new Queen(Color.WHITE);
    b[Board.rank.get('1')][Board.file.get('d')].setCurrent('d', '1');
    b[Board.rank.get('1')][Board.file.get('e')] = new King(Color.WHITE);
    b[Board.rank.get('1')][Board.file.get('e')].setCurrent('e', '1');
    b[Board.rank.get('1')][Board.file.get('f')] = new Bishop(Color.WHITE);
    b[Board.rank.get('1')][Board.file.get('f')].setCurrent('f', '1');
    b[Board.rank.get('1')][Board.file.get('g')] = new Knight(Color.WHITE);
    b[Board.rank.get('1')][Board.file.get('g')].setCurrent('g', '1');
    b[Board.rank.get('1')][Board.file.get('h')] = new Rook(Color.WHITE);
    b[Board.rank.get('1')][Board.file.get('h')].setCurrent('h', '1');
    for (int file : Board.file.values()) {
      b[Board.rank.get('2')][file] = new Pawn(Color.WHITE);
      b[Board.rank.get('2')][file].setCurrent(files[file], '2');
    }

    // Black Pieces
    b[Board.rank.get('8')][Board.file.get('a')] = new Rook(Color.BLACK);
    b[Board.rank.get('8')][Board.file.get('a')].setCurrent('a', '8');
    b[Board.rank.get('8')][Board.file.get('b')] = new Knight(Color.BLACK);
    b[Board.rank.get('8')][Board.file.get('b')].setCurrent('b', '8');
    b[Board.rank.get('8')][Board.file.get('c')] = new Bishop(Color.BLACK);
    b[Board.rank.get('8')][Board.file.get('c')].setCurrent('c', '8');
    b[Board.rank.get('8')][Board.file.get('e')] = new King(Color.BLACK);
    b[Board.rank.get('8')][Board.file.get('e')].setCurrent('e', '8');
    b[Board.rank.get('8')][Board.file.get('d')] = new Queen(Color.BLACK);
    b[Board.rank.get('8')][Board.file.get('d')].setCurrent('d', '8');
    b[Board.rank.get('8')][Board.file.get('f')] = new Bishop(Color.BLACK);
    b[Board.rank.get('8')][Board.file.get('f')].setCurrent('f', '8');
    b[Board.rank.get('8')][Board.file.get('g')] = new Knight(Color.BLACK);
    b[Board.rank.get('8')][Board.file.get('g')].setCurrent('g', '8');
    b[Board.rank.get('8')][Board.file.get('h')] = new Rook(Color.BLACK);
    b[Board.rank.get('8')][Board.file.get('h')].setCurrent('h', '8');
    for (int file : Board.file.values()) {
      b[Board.rank.get('7')][file] = new Pawn(Color.BLACK);
      b[Board.rank.get('7')][file].setCurrent(files[file], '7');
    }

    return b;
  }

  protected static Board createEmtpyBoard() {
    final Board b = new Board();
    b.board = new BasePiece[8][8];
    return b;
  }

  /**
   * getSquare returns the BasePiece (or null) of the square with the given rank or file.
   *
   * @param rankIn the rank that the square is at.
   * @param fileIn the file that the square is at.
   * @return The piece at the given rank and file, or null.
   * @throws IllegalArgumentException if rank is not in @link #board.rank
   * @throws IllegalArgumentException if file is not in @link #board.file
   */
  public BasePiece getSquare(final char fileIn, final char rankIn) {
    final Integer file = Board.file.get(fileIn);
    final Integer rank = Board.rank.get(rankIn);
    if (rank == null || file == null) {
      throw new IllegalArgumentException(fileIn + "/" + rankIn + " is not a valid file/rank.");
    }

    return this.board[rank][file];
  }

  /**
   * Used to set a square to the given piece.
   *
   * <p>Should only be used in testing, hence the protected modifier.
   *
   * @param rankIn the rank that the square is at.
   * @param fileIn the file that the square is at.
   * @param pieceIn the piece to place on the square.
   * @throws IllegalArgumentException if rank is not in @link #board.rank
   * @throws IllegalArgumentException if file is not in @link #board.file
   */
  protected void setSquare(final char fileIn, final char rankIn, final BasePiece pieceIn) {
    final Integer file = Board.file.get(fileIn);
    final Integer rank = Board.rank.get(rankIn);
    if (rank == null || file == null) {
      throw new IllegalArgumentException(fileIn + "/" + rankIn + " is not a valid file/rank.");
    }

    this.board[rank][file] = pieceIn;
  }

  /**
   * Moves a piece on the board.
   *
   * @param m The move to do
   * @return True if the move is valid, False otherwise
   */
  public boolean move(final Move m) {
    if (m.isSurrender()) {
      this.surrenderMove();
      return true;
    }
    // Find the piece that's being be moved
    final BasePiece p =
        this.board[Board.rank.get(m.getFromRank())][Board.file.get(m.getFromFile())];
    if (p == null) {
      return false;
    }
    // verify that the move is valid
    if (!p.isValidMove(this, m)) {
      return false;
    }

    // Special Case: En-passant doesn't capture on the end-square
    if ((p instanceof Pawn) && ((Pawn) p).isEnPassant(this, m)) {
      this.board[Board.rank.get(m.getFromRank())][Board.file.get(m.getFile())] = null;
    }
    // FIXME: Pawn promtions don't do anything
    BasePiece capturedPeice = this.getSquare(m.getFile(), m.getRank());
    this.board[Board.rank.get(m.getRank())][Board.file.get(m.getFile())] = p;
    this.board[Board.rank.get(m.getFromRank())][Board.file.get(m.getFromFile())] = null;

    // Move the piece
    this.moves.add(m);
    this.turnNumber++;

    if (capturedPeice != null && capturedPeice instanceof King) {
      this.gameOver = true;
      this.winner = capturedPeice.color.equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
    }
    p.setCurrent(m.getFile(), m.getRank());
    return true;
  }

  private void surrenderMove() {
    this.winner = this.turnNumber % 2 == 0 ? Color.BLACK : Color.WHITE;
    this.gameOver = true;
  }

  /**
   * String representation of the board.
   *
   * <p>Note Uses terminal escape codes to highlight each square.
   */
  public String toString() {
    final String rankDividor = "\n--|---|---|---|---|---|---|---|---|";
    String output = "  | a | b | c | d | e | f | g | h |" + rankDividor;
    for (int i = 0; i < 8; i++) {
      final BasePiece[] rank = this.board[i];
      output += "\n" + String.valueOf(8 - i) + " |";
      for (final BasePiece piece : rank) {
        output += " " + (piece == null ? " " : piece.toString()) + " |";
      }
      output += rankDividor;
    }
    return output;
  }

  public int getTurnNumber() {
    return this.turnNumber;
  }

  public boolean isGameOver() {
    return this.gameOver;
  }

  public Color getWinner() {
    return this.winner;
  }

  public ArrayList<Move> getMoves() {
    return this.moves;
  }
}
