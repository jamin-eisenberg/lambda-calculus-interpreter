import java.io.IOException;
import main.SimpleContextEvaluator;
import main.parser.ContextParser;
import main.parser.LISPStyleParser;

public class Test {

  ContextParser pa = new ContextParser(new SimpleContextEvaluator(), new LISPStyleParser());

//  private main.model.Variable x = new main.model.Variable("x");
//  private main.model.Variable y = new main.model.Variable("y");
//  private main.model.Variable z = new main.model.Variable("z");
//  private main.model.Expression I = new main.model.Abstraction(x, x);


  @org.junit.Test
  public void name() throws IOException {
//    List<List<main.model.Expression>> results = new ArrayList<>();
////    results.add(p.parse("((lambda x x) y)\r\n(define K (lambda x (lambda y x)))\r\n((K a) b)"));
//    main.model.Variable p = new main.model.Variable("p");
//    main.model.Variable q = new main.model.Variable("q");
//    main.model.Expression e = new main.model.Abstraction(q,
//        new main.model.Application(new main.model.Abstraction(p, new main.model.Abstraction(q, new main.model.Application(p, q))),
//            new main.model.Abstraction(p, new main.model.Abstraction(q, p))));
////    System.out.println(new main.LISPStyleParser().toJavaCreationString());
    System.out.println(
        pa.parse(new String(getClass().getResourceAsStream("test.lam").readAllBytes())));
//    System.out.println(new LISPStyleParser().getExpression("(\\ x (\\ y z))").canonym());
//    main.model.Variable f = new main.model.Variable("f");
//    main.model.Variable a = new main.model.Variable("a");
//    main.model.Variable x = new main.model.Variable("x");
//    main.model.Variable y = new main.model.Variable("y");
//    System.out.println(new main.model.Abstraction(f, new main.model.Abstraction(a, new main.model.Application(f,
//        new main.model.Application(new main.model.Application(new main.model.Abstraction(x, new main.model.Abstraction(y, y)), f), a)))).eval());
//    System.out.println(e.toJavaCreationString());
//    System.out.println(results.get(0).get(0).toJavaCreationString());
//    System.out.println(e);
//    System.out.println(results.get(0).get(0));
//    System.out.println("[" + e + "]");
//    System.out.println(p.parse("(define SUCC (\\ n (\\ f (\\ a (f ((n f) a))))))"));

  }
}