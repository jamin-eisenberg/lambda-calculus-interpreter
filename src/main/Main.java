package main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import main.parser.ContextParser;
import main.parser.LISPStyleParser;

public class Main {

  public static void main(String[] args) throws IOException {
    ContextParser pa = new ContextParser(new SimpleContextEvaluator(), new LISPStyleParser());

    for (String filename : args) {
      System.out.println(pa.parse(new String(
          new FileInputStream(filename).readAllBytes())));
    }

    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println("Starting REPL...");
      System.out.print(">> ");
      String read = scanner.nextLine();
      try {
        System.out.println(pa.parse(read));
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
      }
    }
  }
}
