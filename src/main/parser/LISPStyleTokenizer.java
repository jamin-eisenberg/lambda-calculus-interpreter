package main.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LISPStyleTokenizer implements Tokenizer {

  @Override
  public List<String> tokenize(String program) {
    String[] tokens = program.replaceAll("\\(", " ( ").replaceAll("\\)", " ) ").split(" ");
    tokens = Arrays.stream(tokens).filter(s -> !(s == null || s.equals(""))).toArray(String[]::new);

    return new ArrayList<>(Arrays.asList(tokens));
  }

  @Override
  public String untokenize(List<String> program) {
    return String.join(" ", program).replaceAll("\\( ", "(").replaceAll(" \\)", ")");
  }
}
