package ai.pieces;

import ai.CurrentSquare;
import ai.Score;
import core.classes.Board;
import core.classes.Move;
import core.classes.Queen;


public class AiQueen extends AiBasePiece implements Score, CurrentSquare {

    private static final int score = 80;
    private char[] current;

    public AiQueen() {}

}
