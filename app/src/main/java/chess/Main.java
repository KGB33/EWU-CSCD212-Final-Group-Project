package chess;

import cli.CLI;
import java.util.Scanner;

/** Entry point for the chess app. */
public class Main {

  public static void main(final String[] args) {
    Scanner kb = new Scanner(System.in);
    CLI.run(kb);
  }
}

