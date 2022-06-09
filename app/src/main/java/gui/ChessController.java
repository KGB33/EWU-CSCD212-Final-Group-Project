package gui;

import ai.AiFacade;
import core.classes.*;
import core.enums.Color;
import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/** Class for controlling all the elements of the GUI */
public class ChessController implements Initializable {
  public static String imagePath = ChessController.class.getResource("/gui/images/").toString();
  core.classes.Board b = new Board();

  @FXML private GridPane BoardPane;
  @FXML private Label winText;
  @FXML private Button pvpButton;
  @FXML private Button pveButton;
  private boolean aiGame;
  private AiFacade cpu;
  private URL initURL;
  private ResourceBundle initResource;

  private ImageView selected = null;
  private final ColorAdjust RED = new ColorAdjust(0, .99, .4, 0);
  private final ColorAdjust GREEN = new ColorAdjust(.6, .99, .4, 0);

  private ImageView[][] images = new ImageView[8][8];

  /** Implemented from the Initializable interface. Runs automatically on startup. */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initURL = location;
    initResource = resources;
    b = new Board();
    if (aiGame) {
      cpu = new AiFacade(b);
    }

    imageArraySetup();
    updateBoard();
  }

  /** Initial setup of images 2d array containing all the image tiles for the gui */
  @FXML
  protected void imageArraySetup() {
    Integer row;
    Integer col;
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
  /** Recalls initialize at will */
  @FXML
  public void restart() {
    initialize(initURL, initResource);
  }

  /**
   * Allows user to choose to either play against another player or and AI
   *
   * @param event Represents the button that was clicked
   */
  @FXML
  public void gameModeSelect(ActionEvent event) {
    if (event.getSource() == pvpButton) {
      winText.setText("   PVP Game");
      aiGame = false;
    }
    if (event.getSource() == pveButton) {
      winText.setText("   PvAI Game");
      aiGame = true;
    }
    pvpButton.setDisable(true);
    pvpButton.setVisible(false);
    pveButton.setDisable(true);
    pveButton.setVisible(false);

    BoardPane.setDisable(false);
    restart();
  }

  /**
   * Selects a square to be for a piece to be moved to or have its moves previewed
   *
   * @param mouseEvent When represents the square clicked on
   */
  @FXML
  protected void selectPiece(MouseEvent mouseEvent) throws ParseException {
    if (!b.isGameOver()) {
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

        // Previews the moves of the selected piece. Ensures deselection does not throw an
        // exception.
        if (!selected.getImage().getUrl().equals(imagePath + "blank.png")) {
          previewMoves(selected);
        }
      }
      // A piece has already been selected and a red space(valid move) has been clicked. Selected
      // piece moved to clicked space
      else {
        movePiece(oldSelected, selected);
      }
    }
  }

  /**
   * Previews all valid moves of a selected piece
   *
   * @param piece Represents the piece whose moves will be previewed
   */
  @FXML
  protected void previewMoves(ImageView piece) {
    // Pretty much the same initial processes as movePiece
    String from;
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
    BasePiece previewPiece = b.getSquare(file, rank);

    if (previewPiece.getColor() == Color.WHITE && b.getTurnNumber() % 2 == 0
        || previewPiece.getColor() == Color.BLACK && b.getTurnNumber() % 2 == 1) {

      if (previewPiece instanceof King) {
        from = "K" + file + rank;
      } else if (previewPiece instanceof Queen) {
        from = "Q" + file + rank;
      } else if (previewPiece instanceof Bishop) {
        from = "B" + file + rank;
      } else if (previewPiece instanceof Knight) {
        from = "N" + file + rank;
      } else if (previewPiece instanceof Rook) {
        from = "R" + file + rank;
      } else if (previewPiece instanceof Pawn) {
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
  }

  /**
   * Moves a selected piece from one square to another by sending a move to the backend.
   *
   * @param from Represents where the piece is currently
   * @param to Where the piece is being moved
   */
  protected void movePiece(ImageView from, ImageView to) throws ParseException {
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
    BasePiece movePiece = b.getSquare(fromFile, fromRank);

    String fromStr = "";
    String toStr = "" + toFile + toRank;
    Move m = null;

    // Building from portion of the notions based on type of piece selected
    if (movePiece instanceof King) {
      fromStr = "K" + fromFile + fromRank;
    } else if (movePiece instanceof Queen) {
      fromStr = "Q" + fromFile + fromRank;
    } else if (movePiece instanceof Bishop) {
      fromStr = "B" + fromFile + fromRank;
    } else if (movePiece instanceof Knight) {
      fromStr = "N" + fromFile + fromRank;
    } else if (movePiece instanceof Rook) {
      fromStr = "R" + fromFile + fromRank;
    } else if (movePiece instanceof Pawn) {
      fromStr = "" + fromFile + fromRank;
    }

    // Building and trying all possible moves going from 'fromStr' to 'toStr'
    String[] moves = buildMoves(fromStr, toStr);
    int count = 0;
    boolean moved = false;
    while (count < moves.length && !moved) {
      m = Move.parse(moves[count]);
      moved = b.move(m);
      count++;
    }
    updateBoard();
    if (aiGame && b.getTurnNumber() % 2 == 1) {
      cpu.playTurn(b);
      updateBoard();
    }
  }

  /**
   * Updates the board after a move is sent with the current board state by setting each square's
   * image to match the board's state.
   */
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

    if (b.isGameOver()) {
      winText.setText(b.getWinner().toString().toUpperCase() + " WINS!");
      pvpButton.setDisable(false);
      pvpButton.setVisible(true);
      pveButton.setDisable(false);
      pveButton.setVisible(true);
    }
  }

  /**
   * Builds all the possible moves going from one space to another. Was originally made in
   * preparation for more complex moves that we did not have time to implement(castling, pawn
   * promotions, force response to check)
   *
   * @param from The starting position of a piece
   * @param to The ending position of a piece
   * @return String[]
   */
  public String[] buildMoves(String from, String to) {
    return new String[] {from + "x" + to, from + to};
  }
}
