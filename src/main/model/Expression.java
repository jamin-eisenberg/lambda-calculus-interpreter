package main.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface Expression {

  Set<Variable> fv();

  default Expression canonym(){
    return canonym(new HashMap<>(), "B");
  }

  Expression canonym(Map<String, String> m, String q);

  Expression substitute(Variable x, Expression y);

  Expression eval();

  Expression apply();

  boolean matchesEtaRedex();

  boolean inBetaNormalForm();

//  String toString(boolean outerIsAbstraction);

  String toString();

  String toJavaCreationString();
}
