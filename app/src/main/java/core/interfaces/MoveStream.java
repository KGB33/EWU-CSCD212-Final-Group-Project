package core.interfaces;

import core.classes.Board;
import core.classes.Move;

/**
 * Implemented by various parts to provide decoupled move sourcing.
 *
 * <p>I.e. The CLI might implement a version that fetches moves from stdin.
 *
 * <p>Whereas the AI would implement on that fetches moves from the algorithm.
 */
public interface MoveStream {
  public Move getNextMove(Board b);
}
