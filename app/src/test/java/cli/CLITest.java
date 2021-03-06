package cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class CLITest {

  @Test
  void TestFoolsMate() throws IOException, URISyntaxException {
    Scanner moves = new Scanner("1\nf2f3\ne7e6\ng2g4\nQd8h4\na2a4\nQh4xe1");
    ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(outputContent);
    CLI.run(moves, output);

    String expected =
        Files.readString(Path.of(CLITest.class.getResource("/cli/expected/FoolsMate.txt").toURI()));
    assertEquals(expected.toString(), outputContent.toString());
  }
}
