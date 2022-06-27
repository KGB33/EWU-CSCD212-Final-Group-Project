package core.classes;

import core.enums.Color;
import core.interfaces.MoveStream;

public class GameControler {
  MoveStream player1;
  MoveStream player2;
  Board board;

  /** Initializes the GameController */
  public GameControler(MoveStream p1, MoveStream p2) {
    if (p1 == null || p2 == null) {
      throw new IllegalArgumentException("No MoveStream can be null.");
    }
    this.player1 = p1;
    this.player2 = p2;
    this.board = new Board();
  }

  /** Resets the GameBoard. */
  public void resetGame() {
    this.board = new Board();
  }

  /**
   * Plays the game.
   *
   * <p>Takes moves from player 1 and 2 MoveStreams.
   *
   * @return The winning color.
   */
  public Color play() {
    MoveStream currentPlayer = player1;
    do {
      Boolean mFlag = false;
      do {
        mFlag = this.board.move(currentPlayer.getNextMove(this.board));
      } while (!mFlag);
      currentPlayer = currentPlayer == player1 ? player2 : player1;

    } while (!this.board.isGameOver());
    return this.board.getWinner();
  }

  public Board getBoard() {
    return this.board;
  }
}
