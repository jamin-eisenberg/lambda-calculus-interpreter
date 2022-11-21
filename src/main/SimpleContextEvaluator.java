package main;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import main.model.Expression;
import main.model.Variable;

public class SimpleContextEvaluator implements ContextEvaluator {

  private final Map<String, Expression> definitions = new HashMap<>();

  public void addDefinition(String name, Expression expression) {
    try {
      evaluate(expression).canonym();
    } catch (NullPointerException e) {
      throw new IllegalArgumentException(
          "Free variable not allowed in top-level definition " + name);
    }
    definitions.put(name, evaluate(expression));
  }

  public Expression evaluate(Expression e) {
    if(e instanceof Variable && !definitions.containsKey(e.toString())){
      throw new IllegalArgumentException(e + ": this variable is not defined.");
    }
    for (var k : definitions.keySet()) {
      e = e.substitute(new Variable(k), definitions.get(k));
    }
    return e.eval();
  }

  @Override
  public String getName(Expression e) {
    return definitions.entrySet().stream().filter(entry -> e.canonym().toString().equals(entry.getValue().canonym().toString())).map(
        Entry::getKey).collect(Collectors.joining(" / "));
  }
}
