package main.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class Application implements Expression {

  private static final EvalStrategy defaultEvalStrategy = EvalStrategy.LAZY;

  private final Expression function;
  private final Expression argument;
  private final EvalStrategy evalStrategy;

  public Application(Expression function, Expression argument,
      EvalStrategy evalStrategy) {
    this.function = Objects.requireNonNull(function);
    this.argument = Objects.requireNonNull(argument);
    this.evalStrategy = evalStrategy;
  }

  public Application(Expression function, Expression argument) {
    this(function, argument, defaultEvalStrategy);
  }

  private Expression betaRedex() {
    return ((Abstraction) function).substituteForParam(argument);
  }

  boolean argumentEquals(Variable v) {
    return v.equals(argument);
  }

  boolean freeInFunction(Variable v){
    return function.fv().contains(v);
  }

  Expression getEtaReduction(Variable v) {
    if (function.fv().contains(v)) {
      throw new IllegalArgumentException(String.format("%s does not occur free in %s", v, function));
    }
    return function;
  }

  @Override
  public Set<Variable> fv() {
    Set<Variable> res = new HashSet<>(function.fv());
    res.addAll(argument.fv());
    return res;
  }

  @Override
  public Expression canonym(Map<String, String> m, String q) {
    return new Application(function.canonym(m, q + "F"), argument.canonym(m, q + "S"));
  }

  @Override
  public Expression substitute(Variable x, Expression y) {
    return new Application(function.substitute(x, y), argument.substitute(x, y));
  }

  @Override
  public Expression eval() {
    if (inBetaNormalForm()) {
      return this;
    } else {
      return new Application(function.eval(), evalStrategy.apply(argument.eval())).apply().eval();
    }
  }

  @Override
  public Expression apply() {
    if (function instanceof Abstraction) {
      return betaRedex();
    } else {
      return this;
    }
  }

  @Override
  public boolean matchesEtaRedex() {
    return false;
  }

  @Override
  public boolean inBetaNormalForm() {
    return !(function instanceof Abstraction) && function.inBetaNormalForm() && argument
        .inBetaNormalForm();
  }

  @Override
  public String toString() {
    return String.format("(%s %s)", function, argument);
  }

  @Override
  public String toJavaCreationString() {
    return String
        .format("new main.model.Application(%s, %s, main.model.EvalStrategy.%s)", function.toJavaCreationString(),
            argument.toJavaCreationString(), evalStrategy.toString());
  }
}
