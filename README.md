# Lambda Calculus Interpreter

See [test.lam](res/test.lam) for many examples of how to define and evaluate λ-calculus expressions
in my interpreted language. See [here](https://en.wikipedia.org/wiki/Lambda_calculus) for an 
explanation of what lambda calculus is if you're unfamiliar.

To compile, run the following:

`javac src/main/*.java src/main/model/*.java src/main/parser/*.java -d ./out/`

To start a REPL, navigate to the `out` directory and run the following:

`java main.Main`

To run a file with a λ-calculus program in it (like [test.lam](res/test.lam)), run the
following:

`java main.Main <filename>`

A REPL will then start after running the given files' definitions have been interpreted.