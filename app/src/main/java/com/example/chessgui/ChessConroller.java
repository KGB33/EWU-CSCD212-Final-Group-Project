package com.example.chessgui;

import core.classes.*;
import core.enums.Color;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.text.ParseException;
import java.util.EventListener;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ChessConroller implements Initializable {
    Scanner kb = new Scanner(System.in);
    core.classes.Board b = new Board();
    /*Move m;
    String input;*/

    @FXML private TextField moveInput;
    @FXML private Button moveButton;
    @FXML private GridPane BoardPane;
    @FXML private Button button;
    @FXML private ImageView a1;
    @FXML private ImageView a2;
    @FXML private ImageView a3;
    @FXML private ImageView a4;
    @FXML private ImageView a5;
    @FXML private ImageView a6;
    @FXML private ImageView a7;
    @FXML private ImageView a8;
    @FXML private ImageView b1;
    @FXML private ImageView b2;
    @FXML private ImageView b3;
    @FXML private ImageView b4;
    @FXML private ImageView b5;
    @FXML private ImageView b6;
    @FXML private ImageView b7;
    @FXML private ImageView b8;
    @FXML private ImageView c1;
    @FXML private ImageView c2;
    @FXML private ImageView c3;
    @FXML private ImageView c4;
    @FXML private ImageView c5;
    @FXML private ImageView c6;
    @FXML private ImageView c7;
    @FXML private ImageView c8;
    @FXML private ImageView d1;
    @FXML private ImageView d2;
    @FXML private ImageView d3;
    @FXML private ImageView d4;
    @FXML private ImageView d5;
    @FXML private ImageView d6;
    @FXML private ImageView d7;
    @FXML private ImageView d8;
    @FXML private ImageView e1;
    @FXML private ImageView e2;
    @FXML private ImageView e3;
    @FXML private ImageView e4;
    @FXML private ImageView e5;
    @FXML private ImageView e6;
    @FXML private ImageView e7;
    @FXML private ImageView e8;
    @FXML private ImageView f1;
    @FXML private ImageView f2;
    @FXML private ImageView f3;
    @FXML private ImageView f4;
    @FXML private ImageView f5;
    @FXML private ImageView f6;
    @FXML private ImageView f7;
    @FXML private ImageView f8;
    @FXML private ImageView g1;
    @FXML private ImageView g2;
    @FXML private ImageView g3;
    @FXML private ImageView g4;
    @FXML private ImageView g5;
    @FXML private ImageView g6;
    @FXML private ImageView g7;
    @FXML private ImageView g8;
    @FXML private ImageView h1;
    @FXML private ImageView h2;
    @FXML private ImageView h3;
    @FXML private ImageView h4;
    @FXML private ImageView h5;
    @FXML private ImageView h6;
    @FXML private ImageView h7;
    @FXML private ImageView h8;
    private ImageView oldSelected;
    private ImageView selected = null;
    private ColorAdjust red = new ColorAdjust(0, .99, .4, 0);
    private ColorAdjust green = new ColorAdjust(.6, .99, .4, 0);
    private EventHandler<MouseEvent> clicked = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

        }
    };
    private ImageView[][] images = new ImageView[8][8];

    @FXML
    protected void imageArraySetup(){
        Integer row = 0;
        Integer col = 0;
        int count = 0;
        for(Node n : BoardPane.getChildren()){
            if(count < 64) {
                row = BoardPane.getRowIndex(n);
                col = BoardPane.getColumnIndex(n);
                if (row == null) row = 0;
                if (col == null) col = 0;
                images[7-row][col] = (ImageView) n;
                //System.out.println(n.toString()+ ": " + n.getId());
                count++;
            }
        }
    }

    @FXML
    protected void selectPiece(MouseEvent mouseEvent){
        oldSelected = selected;
        selected = (ImageView) mouseEvent.getSource();
        if(selected.getEffect() != red) {
            for (ImageView image[] : images) {
                for (ImageView i : image) {
                    i.setEffect(null);
                }
            }

            previewMoves(selected);
        }
        else{
            movePiece(oldSelected, selected);
        }
    }

    protected void movePiece(ImageView from, ImageView to){
        Integer fromRow = BoardPane.getRowIndex(from);
        if(fromRow == null){fromRow = 0;}
        fromRow = 7-fromRow;
        Integer fromCol = BoardPane.getColumnIndex(from);
        if(fromCol == null){ fromCol = 0;}
        Integer toRow = BoardPane.getRowIndex(to);
        if(toRow == null){toRow = 0;}
        toRow = 7-toRow;
        Integer toCol = BoardPane.getColumnIndex(to);
        if(toCol == null){ toCol = 0;}
        char fromFile = (char) (fromCol+97);
        int fromRank = fromRow +1;
        char toFile = (char) (toCol+97);
        int toRank = toRow +1;

        String input = "";
        Move m = null;

        if(b.getSquare((char)(fromCol+97),(char)(fromRow+49)) instanceof King){
            input = "K" + fromFile + fromRank;
        }
        else if(b.getSquare((char)(fromCol+97),(char)(fromRow +49)) instanceof Queen){
            input = "Q" + fromFile + fromRank;
        }
        else if(b.getSquare((char)(fromCol+97),(char)(fromRow+49)) instanceof Bishop){
            input = "B" + fromFile + fromRank;
        }
        else if(b.getSquare((char)(fromCol+97),(char)(fromRow+49)) instanceof Knight){
            input = "N" + fromFile + fromRank;
        }
        else if(b.getSquare((char)(fromCol+97),(char)(fromRow+49)) instanceof Rook){
            input = "R" + fromFile + fromRank;
        }
        System.out.println(input);
        input = input + toFile + toRank;
        System.out.println(input);

        try {
            m = Move.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        b.move(m);
        updateBoard();
    }

    @FXML
    protected void previewMoves(ImageView piece){
        String from = "";
        String input = "";
        Move preview = null;
        Integer row = BoardPane.getRowIndex(piece);
        Integer col = BoardPane.getColumnIndex(piece);
        if(row == null){ row = 0; }
        row = 7-row;
        if(col == null){ col = 0; }
        char file = (char) (col+97);
        int rank = row+1;

        if(b.getSquare((char)(col+97),(char)(row+49)) instanceof King){
            from = "K" + file + rank;
        }
        else if(b.getSquare((char)(col+97),(char)(row+49)) instanceof Queen){
            from = "Q" + file + rank;
        }
        else if(b.getSquare((char)(col+97),(char)(row+49)) instanceof Bishop){
            from = "B" + file + rank;
        }
        else if(b.getSquare((char)(col+97),(char)(row+49)) instanceof Knight){
            from = "N" + file + rank;
        }
        else if(b.getSquare((char)(col+97),(char)(row+49)) instanceof Rook){
            from = "R" + file + rank;
        }
        else{
            return;
        }
        piece.setEffect(green);
        for(char i = 97; i < 105; i++){
            for(int j = 1; j < 9; j ++){
                input = from + i + j;
                try {
                    preview = Move.parse(input);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(b.getSquare((char)(col+97),(char)(row+49)).isValidMove(b, preview)){
                    images[j-1][i-97].setEffect(red);
                }
            }
        }
    }

    @FXML
    protected void sendInput(){
        String input = moveInput.getText().trim();
        Move m = null;
        moveInput.setText("");

        try {
            m = Move.parse(input);
        } catch (ParseException e) {
            System.out.println("Could not parse notation, please try again (q to quit).\n");
        }
        if (!b.move(m)) {
            System.out.println("Could not move the peice, please try again (q to quit).\n");
        }
        updateBoard();
    }

    @FXML
    protected void updateBoard(){
        for (ImageView image[] : images) {
            for (ImageView i : image) {
                i.setEffect(null);
            }
        }

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++) {
                if(b.getSquare((char)(i+97),(char)(j+49)) == null){
                    images[j][i].setImage(new Image("images/blank.png"));
                }
                else{
                    String color;
                    if(b.getSquare((char)(i+97),(char)(j+49)).getColor() == Color.BLACK){
                        color = "b";
                    }
                    else color = "w";
                    if(b.getSquare((char)(i+97),(char)(j+49)) instanceof King){
                        images[j][i].setImage(new Image("images/" + color + "King.png"));
                    }
                    else if(b.getSquare((char)(i+97),(char)(j+49)) instanceof Queen){
                        images[j][i].setImage(new Image("images/" + color + "Queen.png"));
                    }
                    else if(b.getSquare((char)(i+97),(char)(j+49)) instanceof Bishop){
                        images[j][i].setImage(new Image("images/" + color + "Bishop.png"));
                    }
                    else if(b.getSquare((char)(i+97),(char)(j+49)) instanceof Knight){
                        images[j][i].setImage(new Image("images/" + color + "Knight.png"));
                    }
                    else if(b.getSquare((char)(i+97),(char)(j+49)) instanceof Rook){
                        images[j][i].setImage(new Image("images/" + color + "Rook.png"));
                    }
                }
            }
        }
        System.out.println(b.toString());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imageArraySetup();
        updateBoard();
    }
}