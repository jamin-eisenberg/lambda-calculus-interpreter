package main.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import main.model.Abstraction;
import main.model.Application;
import main.model.Expression;
import main.model.Variable;

public class LISPStyleParser implements main.parser.LambdaExpressionParser {

  private static final List<String> lambdaSymbols = Arrays.asList("λ", "\\", "lambda");

  @Override
  public Tokenizer getTokenizer() {
    return new LISPStyleTokenizer();
  }

  private int getParenthesizedSubexpressionEndIndex(List<String> program) {
    int openParenCount = 0;
    int i;
    for (i = 0; i < program.size(); i++) {
      if (program.get(i).equals("(")) {
        openParenCount++;
      } else if (program.get(i).equals(")")) {
        openParenCount--;
      }
      if (openParenCount < 0) {
        throw new IllegalArgumentException("Mismatched parentheses.");
      }
      if (openParenCount == 0) {
        break;
      }
    }

    return i + 1;
  }

  private List<String> getParenthesizedSubexpression(List<String> program) {
    return new ArrayList<>(program.subList(0, getParenthesizedSubexpressionEndIndex(program)));
  }

  private Abstraction constructMultivarAbstraction(List<Variable> parameters, Expression body) {
    parameters = new ArrayList<>(parameters);
    if (parameters.size() == 1) {
      return new Abstraction(parameters.get(0), body);
    } else {
      return new Abstraction(parameters.remove(0), constructMultivarAbstraction(parameters, body));
    }
  }

  private Expression constructMultiExprApplication(List<Expression> subExprs) {
    subExprs = new ArrayList<>(subExprs);
    if (subExprs.size() == 1) {
      return subExprs.get(0);
    } else {
      return new Application(
          constructMultiExprApplication(subExprs.subList(0, subExprs.size() - 1)),
          subExprs.get(subExprs.size() - 1));
    }
  }

  @Override
  public Expression getExpression(List<String> program) {
    program = new ArrayList<>(program);

    if (program.isEmpty()) {
      throw new IllegalArgumentException();
    }

    if (program.get(0).equals("(")) {

      List<String> inner = program.subList(1, program.size() - 1);
      List<List<String>> subExpressions = new ArrayList<>();

      while (!inner.isEmpty()) {
        int endI = getParenthesizedSubexpressionEndIndex(inner);
        subExpressions.add(getParenthesizedSubexpression(inner));
        inner.subList(0, endI).clear();
      }

      if (subExpressions.get(0).size() == 1 && lambdaSymbols
          .contains(subExpressions.get(0).get(0))) {
        List<Variable> parameters = new ArrayList<>();
        for (List<String> paramSubExp : subExpressions.subList(1, subExpressions.size() - 1)) {
          if (paramSubExp.size() != 1) {
            throw new IllegalArgumentException(
                "Syntax error in expression \"" + getTokenizer().untokenize(program)
                    + "\". Parameters cannot be expressions.");
          }
          parameters.add(new Variable(paramSubExp.get(0)));
        }

        return constructMultivarAbstraction(parameters,
            getExpression(subExpressions.get(subExpressions.size() - 1)));
      } else {
        return constructMultiExprApplication(
            subExpressions.stream().map(this::getExpression).collect(
                Collectors.toList()));
      }
    } else {
      return new Variable(program.get(0));
    }
  }
}
