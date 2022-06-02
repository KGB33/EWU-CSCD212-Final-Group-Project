package core.classes;

import core.enums.CheckmateStatus;
import core.enums.Color;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/** Represents a chessboard. */
public class Board {
  private BasePiece[][] board;

  private int turnNumber;
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

    // White Pieces

    b[Board.rank.get('1')][Board.file.get('a')] = new Rook(Color.WHITE);
    b[Board.rank.get('1')][Board.file.get('b')] = new Knight(Color.WHITE);
    b[Board.rank.get('1')][Board.file.get('c')] = new Bishop(Color.WHITE);
    b[Board.rank.get('1')][Board.file.get('d')] = new Queen(Color.WHITE);
    b[Board.rank.get('1')][Board.file.get('e')] = new King(Color.WHITE);
    b[Board.rank.get('1')][Board.file.get('f')] = new Bishop(Color.WHITE);
    b[Board.rank.get('1')][Board.file.get('g')] = new Knight(Color.WHITE);
    b[Board.rank.get('1')][Board.file.get('h')] = new Rook(Color.WHITE);
    for (int file : Board.file.values()) {
      b[Board.rank.get('2')][file] = new Pawn(Color.WHITE);
    }

    // Black Pieces
    b[Board.rank.get('8')][Board.file.get('a')] = new Rook(Color.BLACK);
    b[Board.rank.get('8')][Board.file.get('b')] = new Knight(Color.BLACK);
    b[Board.rank.get('8')][Board.file.get('c')] = new Bishop(Color.BLACK);
    b[Board.rank.get('8')][Board.file.get('e')] = new King(Color.BLACK);
    b[Board.rank.get('8')][Board.file.get('d')] = new Queen(Color.BLACK);
    b[Board.rank.get('8')][Board.file.get('f')] = new Bishop(Color.BLACK);
    b[Board.rank.get('8')][Board.file.get('g')] = new Knight(Color.BLACK);
    b[Board.rank.get('8')][Board.file.get('h')] = new Rook(Color.BLACK);

    for (int file : Board.file.values()) {
      b[Board.rank.get('7')][file] = new Pawn(Color.BLACK);
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
    this.board[Board.rank.get(m.getRank())][Board.file.get(m.getFile())] = p;
    this.board[Board.rank.get(m.getFromRank())][Board.file.get(m.getFromFile())] = null;

    BasePiece[][] tmpBoard = this.board.clone();

    // Move the piece
    this.moves.add(m);
    this.turnNumber++;

    System.out.println(this.checkCheckmateStatus('a', '1'));
    return true;
  }

  private CheckmateStatus checkCheckmateStatus(char kingFile, char kingRank) {
    // walk every square on the board.
    Move m;
    for (char rank = 'a'; rank < 'h' + 1; rank++) {
      for (char file = '1'; file < '8'; file++) {
        BasePiece square = this.getSquare(file, rank);
        try {
          m = Move.parse(square.getShortName() + rank + file + "x" + kingRank + kingFile);
        } catch (ParseException e) {
          throw new IllegalArgumentException(e.toString());
        }
        if (square.isValidMove(this, m)) {
          return CheckmateStatus.CHECK;
        }
      }
    }

    return CheckmateStatus.OK;
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

  public ArrayList<Move> getMoves() {
    return this.moves;
  }
}
