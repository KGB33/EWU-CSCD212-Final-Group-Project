package gui;

import core.classes.*;
import core.enums.Color;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class ChessController implements Initializable {
  public static String imagePath = ChessController.class.getResource("/gui/images/").toString();
  core.classes.Board b = new Board();

  @FXML private TextField moveInput;
  @FXML private Button moveButton;
  @FXML private GridPane BoardPane;
  private ImageView oldSelected;
  private ImageView selected = null;
  private final ColorAdjust RED = new ColorAdjust(0, .99, .4, 0);
  private final ColorAdjust GREEN = new ColorAdjust(.6, .99, .4, 0);

  private ImageView[][] images = new ImageView[8][8];

  @FXML
  protected void imageArraySetup() {
    Integer row = 0;
    Integer col = 0;
    int count = 0;
    for (Node n : BoardPane.getChildren()) {
      if (count < 64) {
        row = BoardPane.getRowIndex(n);
        col = BoardPane.getColumnIndex(n);
        if (row == null) row = 0;
        if (col == null) col = 0;
        images[7 - row][col] = (ImageView) n;
        count++;
      }
    }
  }

  @FXML
  protected void selectPiece(MouseEvent mouseEvent) {
    oldSelected = selected;
    selected = (ImageView) mouseEvent.getSource();
    if (selected.getEffect() != RED) {
      for (ImageView[] image : images) {
        for (ImageView i : image) {
          i.setEffect(null);
        }
      }

      previewMoves(selected);
    } else {
      movePiece(oldSelected, selected);
    }
  }

  protected void movePiece(ImageView from, ImageView to) {
    Integer fromRow = BoardPane.getRowIndex(from);
    if (fromRow == null) {
      fromRow = 0;
    }
    fromRow = 7 - fromRow;
    Integer fromCol = BoardPane.getColumnIndex(from);
    if (fromCol == null) {
      fromCol = 0;
    }
    Integer toRow = BoardPane.getRowIndex(to);
    if (toRow == null) {
      toRow = 0;
    }
    toRow = 7 - toRow;
    Integer toCol = BoardPane.getColumnIndex(to);
    if (toCol == null) {
      toCol = 0;
    }
    char fromFile = (char) (fromCol + 97);
    int fromRank = fromRow + 1;
    char toFile = (char) (toCol + 97);
    int toRank = toRow + 1;

    String fromStr = "";
    String toStr = "" + toFile + toRank;
    Move m = null;

    if (b.getSquare((char) (fromCol + 97), (char) (fromRow + 49)) instanceof King) {
      fromStr = "K" + fromFile + fromRank;
    } else if (b.getSquare((char) (fromCol + 97), (char) (fromRow + 49)) instanceof Queen) {
      fromStr = "Q" + fromFile + fromRank;
    } else if (b.getSquare((char) (fromCol + 97), (char) (fromRow + 49)) instanceof Bishop) {
      fromStr = "B" + fromFile + fromRank;
    } else if (b.getSquare((char) (fromCol + 97), (char) (fromRow + 49)) instanceof Knight) {
      fromStr = "N" + fromFile + fromRank;
    } else if (b.getSquare((char) (fromCol + 97), (char) (fromRow + 49)) instanceof Rook) {
      fromStr = "R" + fromFile + fromRank;
    } else if (b.getSquare((char) (fromCol + 97), (char) (fromRow + 49)) instanceof Pawn) {
      fromStr = "" + fromFile + fromRank;
    }
    /*System.out.println(input);
    input = input + toFile + toRank;
    System.out.println(input);*/
    String[] moves = buildMoves(fromStr, toStr);
    int count = 0;
    boolean moved = false;
    while(count < moves.length && !moved) {
      try {
        m = Move.parse(moves[count]);
      } catch (ParseException e) {
        e.printStackTrace();
      }
      moved = b.move(m);
      count++;
    }
    updateBoard();
  }

  @FXML
  protected void previewMoves(ImageView piece) {
    String from = "";
    String input = "";
    Move preview = null;
    Integer row = BoardPane.getRowIndex(piece);
    Integer col = BoardPane.getColumnIndex(piece);
    if (row == null) {
      row = 0;
    }
    row = 7 - row;
    if (col == null) {
      col = 0;
    }
    char file = (char) (col + 97);
    int rank = row + 1;

    if (b.getSquare((char) (col + 97), (char) (row + 49)) instanceof King) {
      from = "K" + file + rank;
    } else if (b.getSquare((char) (col + 97), (char) (row + 49)) instanceof Queen) {
      from = "Q" + file + rank;
    } else if (b.getSquare((char) (col + 97), (char) (row + 49)) instanceof Bishop) {
      from = "B" + file + rank;
    } else if (b.getSquare((char) (col + 97), (char) (row + 49)) instanceof Knight) {
      from = "N" + file + rank;
    } else if (b.getSquare((char) (col + 97), (char) (row + 49)) instanceof Rook) {
      from = "R" + file + rank;
    } else if (b.getSquare((char) (col + 97), (char) (row + 49)) instanceof Pawn) {
      from = "" + file + rank;
    } else {
      return;
    }
    piece.setEffect(GREEN);
    int count = 0;
    for (char i = 97; i < 105; i++) {
      for (int j = 1; j < 9; j++) {
        count = 0;
        String to = "" + i + j;
        String[] moves = buildMoves(from, to);
        while (count < moves.length) {
          try {
            preview = Move.parse(moves[count]);
          } catch (ParseException e) {
            e.printStackTrace();
          }
          if (b.getSquare((char) (col + 97), (char) (row + 49)).isValidMove(b, preview)) {
            images[j - 1][i - 97].setEffect(RED);
          }
          count++;
        }
      }
    }
  }

  @FXML
  protected void sendInput() {
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
  protected void updateBoard() {
    for (ImageView[] image : images) {
      for (ImageView i : image) {
        i.setEffect(null);
      }
    }

    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (b.getSquare((char) (i + 97), (char) (j + 49)) == null) {
          // images[j][i].setImage(new Image("gui/images/blank.png"));
          images[j][i].setImage(new Image(ChessController.imagePath + "blank.png"));
        } else {
          String color;
          if (b.getSquare((char) (i + 97), (char) (j + 49)).getColor() == Color.BLACK) {
            color = "b";
          } else color = "w";
          if (b.getSquare((char) (i + 97), (char) (j + 49)) instanceof King) {
            images[j][i].setImage(new Image(ChessController.imagePath + color + "King.png"));
          } else if (b.getSquare((char) (i + 97), (char) (j + 49)) instanceof Queen) {
            images[j][i].setImage(new Image(ChessController.imagePath + color + "Queen.png"));
          } else if (b.getSquare((char) (i + 97), (char) (j + 49)) instanceof Bishop) {
            images[j][i].setImage(new Image(ChessController.imagePath + color + "Bishop.png"));
          } else if (b.getSquare((char) (i + 97), (char) (j + 49)) instanceof Knight) {
            images[j][i].setImage(new Image(ChessController.imagePath + color + "Knight.png"));
          } else if (b.getSquare((char) (i + 97), (char) (j + 49)) instanceof Rook) {
            images[j][i].setImage(new Image(ChessController.imagePath + color + "Rook.png"));
          }else if (b.getSquare((char) (i + 97), (char) (j + 49)) instanceof Pawn) {
            images[j][i].setImage(new Image(ChessController.imagePath + color + "Pawn.png"));
          }
        }
      }
    }
    System.out.println(b.toString());
  }
  public String[] buildMoves(String from, String to){
    String[] moves = new String[]{from + "x" + to, from + to};

    return moves;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    imageArraySetup();
    updateBoard();
  }
}
