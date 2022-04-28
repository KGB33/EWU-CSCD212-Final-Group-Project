package chess;

/** Entry point for the chess app. */
public class Main {
  public String getGreeting() {
    return "Hello World!";
  }

  public static void main(final String[] args) {
    System.out.println(new Main().getGreeting());
  }
}
