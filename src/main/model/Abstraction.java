package main.model;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class Abstraction implements Expression {

  private final Variable parameter;
  private final Expression body;

  public Abstraction(Variable parameter, Expression body) {
    Objects.requireNonNull(parameter);
    Objects.requireNonNull(body);
    this.parameter = parameter;
    this.body = body;
  }

  private Expression etaRedex() {
    return ((Application) body).getEtaReduction(parameter);
  }

  Expression substituteForParam(Expression y) {
    return body.substitute(parameter, y);
  }

  @Override
  public Set<Variable> fv() {
    Set<Variable> res = new HashSet<>(body.fv());
    res.remove(parameter);
    return res;
  }

  @Override
  public Expression canonym(Map<String, String> m, String q) {
    m.put(parameter.toString(), q);
    return new Abstraction(new Variable(q), body.canonym(m, q + "N"));
  }

  @Override
  public Expression substitute(Variable x, Expression y) {
    if (x.equals(parameter)) {
      return this;
    } else {
      return new Abstraction(parameter, body.substitute(x, y));
    }
  }

  @Override
  public Expression eval() {
    if (inBetaNormalForm()) {
      return this;
    } else if (matchesEtaRedex()) {
      return etaRedex().eval();
    } else {
      return new Abstraction(parameter, body.eval());
    }
  }

  @Override
  public Expression apply() {
    return this;
  }

  @Override
  public boolean matchesEtaRedex() {
    return body instanceof Application &&
        ((Application) body).argumentEquals(parameter) && ((Application) body)
        .freeInFunction(parameter);
  }

  @Override
  public boolean inBetaNormalForm() {
    return !matchesEtaRedex() && body.inBetaNormalForm();
  }

  @Override
  public String toString() {
    return String.format("λ%s.%s", parameter, body);
  }

  @Override
  public String toJavaCreationString() {
    return String.format("new Abstraction(%s, %s)", parameter.toJavaCreationString(),
        body.toJavaCreationString());
  }
}
