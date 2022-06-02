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
import javafx.scene.control.TextArea;
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
  @FXML private TextArea wHistory;
  @FXML private TextArea bHistory;

  private ImageView selected = null;
  private final ColorAdjust RED = new ColorAdjust(0, .99, .4, 0);
  private final ColorAdjust GREEN = new ColorAdjust(.6, .99, .4, 0);

  private ImageView[][] images = new ImageView[8][8];

  // Initial setup of images 2d array containing all the image tiles for the gui
  @FXML
  protected void imageArraySetup() {
    Integer row = 0;
    Integer col = 0;
    int count = 0;
    // Populates array with each image in the gridpane
    for (Node n : BoardPane.getChildren()) {
      if (count < 64) {
        row = BoardPane.getRowIndex(n);
        col = BoardPane.getColumnIndex(n);
        if (row == null) {
          row = 0;
        }
        if (col == null) {
          col = 0;
        }
        images[7 - row][col] = (ImageView) n;
        count++;
      }
    }
  }

  // Selects a piece square
  @FXML
  protected void selectPiece(MouseEvent mouseEvent) {
    ImageView oldSelected = selected;
    selected = (ImageView) mouseEvent.getSource();

    // Allows a new piece to be selected or a piece to be deselected by clicking a blank space
    if (selected.getEffect() != RED) {
      // Clears board of any previous move previews
      for (ImageView[] image : images) {
        for (ImageView i : image) {
          i.setEffect(null);
        }
      }

      // Previews the moves of the selected piece
      previewMoves(selected);
    }
    // A piece has already been selected and a red space(valid move) has been clicked. Selected
    // piece moved to clicked space
    else {
      movePiece(oldSelected, selected);
    }
  }

  // Moves a selected piece from one square to another.
  protected void movePiece(ImageView from, ImageView to) {
    // Initializing information needed to move. Where the piece is from and where it's going
    // Each variable has to be checked for null, when getting column or row 0, null is returned
    // instead of 0 for some reason
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

    // Converting col/row to rank/file
    // Col + 97 converts 0-7 int to a-h char
    // Row + 49 coverts 0-7 int to 1-8 char
    char fromFile = (char) (fromCol + 97);
    char fromRank = (char) (fromRow + 49);
    char toFile = (char) (toCol + 97);
    char toRank = (char) (toRow + 49);

    String fromStr = "";
    String toStr = "" + toFile + toRank;
    Move m = null;

    // Building from portion of the notions based on type of piece selected
    if (b.getSquare(fromFile, fromRank) instanceof King) {
      fromStr = "K" + fromFile + fromRank;
    } else if (b.getSquare(fromFile, fromRank) instanceof Queen) {
      fromStr = "Q" + fromFile + fromRank;
    } else if (b.getSquare(fromFile, fromRank) instanceof Bishop) {
      fromStr = "B" + fromFile + fromRank;
    } else if (b.getSquare(fromFile, fromRank) instanceof Knight) {
      fromStr = "N" + fromFile + fromRank;
    } else if (b.getSquare(fromFile, fromRank) instanceof Rook) {
      fromStr = "R" + fromFile + fromRank;
    } else if (b.getSquare(fromFile, fromRank) instanceof Pawn) {
      fromStr = "" + fromFile + fromRank;
    }

    // Building and trying all possible moves going from 'fromStr' to 'toStr'
    String[] moves = buildMoves(fromStr, toStr);
    int count = 0;
    boolean moved = false;
    while (count < moves.length && !moved) {
      try {
        m = Move.parse(moves[count]);
      } catch (ParseException e) {
        e.printStackTrace();
      }
      moved = b.move(m);
      count++;
    }
    updateHistory(moves[count - 1], toRank, toFile);
    updateBoard();
  }

  // Previews all valid moves of a selected piece
  @FXML
  protected void previewMoves(ImageView piece) {
    // Pretty much the same initial processes as movePiece
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
    char rank = (char) (row + 49);

    if (b.getSquare(file, rank) instanceof King) {
      from = "K" + file + rank;
    } else if (b.getSquare(file, rank) instanceof Queen) {
      from = "Q" + file + rank;
    } else if (b.getSquare(file, rank) instanceof Bishop) {
      from = "B" + file + rank;
    } else if (b.getSquare(file, rank) instanceof Knight) {
      from = "N" + file + rank;
    } else if (b.getSquare(file, rank) instanceof Rook) {
      from = "R" + file + rank;
    } else if (b.getSquare(file, rank) instanceof Pawn) {
      from = "" + file + rank;
    } else {
      return;
    }

    // Highlights selected piece in green
    piece.setEffect(GREEN);
    int count;
    // Loops through the whole board and checks if the selected piece can move to each space
    for (char i = 97; i < 105; i++) {
      for (int j = 1; j < 9; j++) {
        count = 0;
        String to = "" + i + j;
        String[] moves = buildMoves(from, to);
        // Checks each potential move until a move is valid. If no move is found, nothing is done.
        while (count < moves.length) {
          try {
            preview = Move.parse(moves[count]);
          } catch (ParseException e) {
            e.printStackTrace();
          }
          // If the piece can move to the square, it is highlighted in red on the gui board
          if (b.getSquare((char) (col + 97), (char) (row + 49)).isValidMove(b, preview)) {
            images[j - 1][i - 97].setEffect(RED);
            break;
          }
          count++;
        }
      }
    }
  }

  // Allows movement through manual notation entry. Will most likely be deleted when the project is
  // finished.
  // Gets input from a text field and sends the input when a button is pressed
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

  // Updates the board after a move is sent with the current board state
  @FXML
  protected void updateBoard() {
    // Un-highlights all the pieces
    for (ImageView[] image : images) {
      for (ImageView i : image) {
        i.setEffect(null);
      }
    }

    // Gets each square and updates its image on the gui board to match its piece on the board
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        // No piece sets the tile to a blank image
        // Has to be set to an image in order to be highlighted. Null images can't have their colors
        // changed
        if (b.getSquare((char) (i + 97), (char) (j + 49)) == null) {
          images[j][i].setImage(new Image(ChessController.imagePath + "blank.png"));
        }
        // Gets the piece's type and color, then set's its image appropriately
        else {
          String color;
          if (b.getSquare((char) (i + 97), (char) (j + 49)).getColor() == Color.BLACK) {
            color = "b";
          } else {
            color = "w";
          }
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
          } else if (b.getSquare((char) (i + 97), (char) (j + 49)) instanceof Pawn) {
            images[j][i].setImage(new Image(ChessController.imagePath + color + "Pawn.png"));
          }
        }
      }
    }
    // Prints the board to the console to make sure the gui board is accurate to the board
    System.out.println(b.toString());
  }

  // Updates the move history of each player
  public void updateHistory(String move, char rank, char file) {
    if (b.getSquare(file, rank).getColor() == Color.BLACK) {
      bHistory.setText(bHistory.getText() + move + "\n");
    } else {
      wHistory.setText(wHistory.getText() + move + "\n");
    }
    // if(wHistory.getText() == null) { wHistory.setText(""); }

  }

  // Builds each variation of a move notation (checkmate, check, capture, plane move)
  public String[] buildMoves(String from, String to) {
    String[] moves = new String[] {from + "x" + to, from + to};

    return moves;
  }

  // Sets up the gui board
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    wHistory.setText("");
    bHistory.setText("");
    imageArraySetup();
    updateBoard();
  }
}
