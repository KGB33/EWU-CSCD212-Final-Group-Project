package ai.pieces;

import ai.CurrentSquare;
import ai.Score;
import core.classes.Pawn;
import core.enums.Color;

public class AiPawn extends AiBasePiece implements Score, CurrentSquare {

    private static final int score = 10;
    private char[] current;

    public AiPawn() {}
}
