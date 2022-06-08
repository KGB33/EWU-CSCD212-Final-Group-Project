package chess;

import ai.AiMoveStream;
import cli.CliMoveStream;
import core.interfaces.MoveStream;
import java.util.Scanner;

public class MoveStreamFactory {

  /**
   * Creates MoveStreams.
   *
   * @param type -- Literal value: {"cli", "ai", "gui"}
   */
  public static MoveStream create(String type) {
    switch (type) {
      case "cli":
        return new CliMoveStream(new Scanner(System.in));
      case "ai":
        return new AiMoveStream();
      case "gui":
        // FIXME: Change Cli -> GUI
        return new CliMoveStream(new Scanner(System.in));
      default:
        throw new IllegalArgumentException("Invalid type: '" + type + "'.");
    }
  }
}
