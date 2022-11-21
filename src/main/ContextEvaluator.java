package main;

import main.model.Expression;

public interface ContextEvaluator {

  void addDefinition(String name, Expression e);

  Expression evaluate(Expression e);

  String getName(Expression e);
}
