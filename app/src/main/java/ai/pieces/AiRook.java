package ai.pieces;

import ai.CurrentSquare;
import ai.Score;
import core.classes.Rook;
import core.enums.Color;

public class AiRook extends AiBasePiece implements Score, CurrentSquare {

    private static final int score = 50;
    private char[] current;

    public AiRook() {}
}
