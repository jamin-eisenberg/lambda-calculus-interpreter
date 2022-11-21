package main.parser;

import java.util.List;
import main.model.Expression;

public interface LambdaExpressionParser {

  default Expression getExpression(String program){
    return getExpression(getTokenizer().tokenize(program));
  }

  Expression getExpression(List<String> program) throws IllegalArgumentException;

  Tokenizer getTokenizer();
}
