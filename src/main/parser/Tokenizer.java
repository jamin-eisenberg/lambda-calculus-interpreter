package main.parser;

import java.util.List;

public interface Tokenizer {

  List<String> tokenize(String program);

  String untokenize(List<String> program);
}
