package main.model;

import java.util.function.Function;

public enum EvalStrategy {
  LAZY(Function.identity()), EAGER(Expression::eval);

  private final Function<Expression, Expression> f;

  EvalStrategy(Function<Expression, Expression> f) {
    this.f = f;
  }

  Expression apply(Expression e){
    return f.apply(e);
  }
}
