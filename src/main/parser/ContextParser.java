package main.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import main.ContextEvaluator;
import main.model.Expression;
import main.model.Variable;

public class ContextParser {

  private final ContextEvaluator evaluator;
  private final LambdaExpressionParser expressionParser;

  public ContextParser(ContextEvaluator evaluator, LambdaExpressionParser expressionParser) {
    this.evaluator = evaluator;
    this.expressionParser = expressionParser;
  }

  public List<Expression> parse(String program) {
    List<Expression> exprs = new ArrayList<>();

    program = removeComments(program);
    program = putExprsOnOwnLine(program);

    for (String line : program.split("\\r?\\n")) {
      if (!line.contains("(")) {
        for (String name : line.split("\\s")) {
          exprs.add(evaluator.evaluate(new Variable(name)));
        }
      } else {
        List<String> lineTokens = expressionParser.getTokenizer().tokenize(line);
        final String defineSymbol = "define";
        if (lineTokens.get(0).equals("(") && lineTokens.get(1).equals(defineSymbol)) {
          evaluator.addDefinition(lineTokens.get(2),
              expressionParser.getExpression(lineTokens.subList(3, lineTokens.size() - 1)));
        } else {
          Expression normalForm = evaluator.evaluate(expressionParser.getExpression(lineTokens));
          String name = evaluator.getName(normalForm);
          if (name.equals("")) {
            exprs.add(normalForm);
          } else {
            exprs.add(new Variable(name));
          }
        }
      }
    }

    return exprs;
  }

  private String removeComments(String program) {
    String[] lines = program.split("\\r?\\n");
    lines = Arrays.stream(lines).filter(l -> !(l == null || l.equals(""))).toArray(String[]::new);

    for (int i = 0; i < lines.length; i++) {
      String line = lines[i];
      lines[i] = line.substring(0, line.contains(";") ? line.indexOf(";") : line.length());
    }

    return String.join(System.lineSeparator(), lines);
  }

  private String putExprsOnOwnLine(String program) {
    program = program.replaceAll("\\r?\\n", " ");

    int openParens = 0;
    StringBuilder exprSoFar = new StringBuilder();
    List<String> exprs = new ArrayList<>();
    for (char ch : program.toCharArray()) {
      if (ch == '(') {
        openParens++;
      } else if (ch == ')') {
        openParens--;
      }
      exprSoFar.append(ch);
      if (openParens == 0) {
        if (!exprSoFar.toString().matches("^\\s+$")) {
          exprs.add(exprSoFar.toString());
        }
        exprSoFar = new StringBuilder();
      } else if (openParens < 0) {
        throw new IllegalArgumentException("Parentheses expected. ");
      }
    }

    return String.join(System.lineSeparator(), exprs);
  }
}
