package ai;
import core.classes.*;
import core.enums.Color;
import core.classes.Board;

import java.text.ParseException;
import java.util.ArrayList;
import java.lang.Math;

public class Computer {

    public ArrayList<BasePiece> pieces;
    private BasePiece[][] map;

    private ArrayList<ArrayList<Move>> allMoves;

    final char[] ranks = {'8', '7', '6', '5', '4', '3', '2', '1'};
    final char[] files = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};

    private final Board b;

    public Computer(Board b)
    {
        pieces = new ArrayList<BasePiece>(16);
        allMoves = new ArrayList<ArrayList<Move>>(1000);
        this.b = b;
        this.map = b.getBoardMap();
        getPieces();
    }

    public void getPieces()
    {
        if (!this.pieces.isEmpty()) {
            this.pieces.clear();
        }

        for (char file : files)
        {
            for (char rank : ranks)
            {
                if (b.getSquare(file, rank) == null){
                    continue;
                }
                if (((b.getSquare(file, rank)).getColor()).equals(Color.BLACK)) {
                    pieces.add(b.getSquare(file, rank));
                }
            }
        }
    }

    public void boardUpdate(Board b)
    {
        this.map = b.getBoardMap();
    }

    public void genAllMoves() throws ParseException
    {
        if (!this.allMoves.isEmpty()) {
            this.allMoves.clear();
        }

        for (BasePiece piece : pieces)
        {
            allMoves.add(piece.validMoves(b));
        }
    }

    public Move bestMove()
    {
        int bestMoveScore = 0;
        Move bestMove = null;



        for (ArrayList<Move> pieceMoves : allMoves)
        {
            for (Move move : pieceMoves)
            {
                if (move.isCapture()) {
                    move.setMoveScore((b.getSquare(move.getFile(), move.getRank())).getScore());
                }
                if (move.getMoveScore() > bestMoveScore) {
                    bestMoveScore = move.getMoveScore();
                    bestMove = move;

                }
            }
        }

        if(bestMoveScore == 0) {
            int randPiece = (int) (Math.random() * allMoves.size());
            int randMove = (int) (Math.random() * ((allMoves.get(randPiece)).size()));

            while ((allMoves.get(randPiece)).isEmpty()) {
                randPiece = (int) (Math.random() * allMoves.size());
                randMove = (int) (Math.random() * ((allMoves.get(randPiece)).size()));
            }
            bestMove = (allMoves.get(randPiece)).get(randMove);
        }

        return bestMove;
    }
    public void playMove()
    {
        Move m = bestMove();
        b.move(m);
    }
}
