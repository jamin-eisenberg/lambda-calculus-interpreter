package main.model;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class Variable implements Expression {

  private final String name;

  public Variable(String name) {
    this.name = Objects.requireNonNull(name);
  }

  @Override
  public Set<Variable> fv() {
    return Set.of(this);
  }

  @Override
  public Expression canonym(Map<String, String> m, String q) {
    return new Variable(m.get(name));
  }

  @Override
  public Expression substitute(Variable x, Expression y) {
    return this.equals(x) ? y : this;
  }

  @Override
  public Expression eval() {
    return this;
  }

  @Override
  public Expression apply() {
    return this;
  }

  @Override
  public boolean matchesEtaRedex() {
    return false;
  }

  @Override
  public boolean inBetaNormalForm() {
    return true;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public String toJavaCreationString() {
    return String.format("new Variable(\"%s\")", name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Variable variable = (Variable) o;

    return name.equals(variable.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}
