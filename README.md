# PLXC: a PLX to three-address code compiler

PLXC is a compiler written in Java, [JFlex](https://jflex.de/) and [CUP](https://github.com/ultimate-pa/javacup). It translates programs written in **PLX**, a small statically typed, C-like language, into **TAC**, a low-level *three-address code* (TAC) for an abstract machine with unlimited registers.

```
prog.plx  ──►  PLXC  ──►  prog.tac  ──►  tac  ──►  output
(source)      (compiler)  (TAC)         (interpreter)
```

Each TAC instruction has at most one operator and three operands (`x = a + b;`). The compiler is a single pass: the CUP grammar emits TAC while parsing, using a symbol table for scoping and type checking.

## The PLX language

### Types
| Type | Description |
|---|---|
| `int` | Integers |
| `float` | Real numbers, written with a decimal point and optional exponent (`3.1416`, `3.1416E-2`) |
| `char` | Characters in single quotes, with the escapes `\b \n \f \r \t \" \\ \'` and unicode escapes such as `'\u0043'` |
| `boolean` | `true` or `false` |

### Declarations
- Every variable must be declared before use. Using an undeclared variable, or declaring the same name twice in the same scope, is an error.
- Several variables can be declared on one line, each with an optional initialiser: `int x = 1, y = 1 + 2*3, z;`
- Uninitialised variables start at zero (`0`, `0.0`, the character with code 0, or `false`).
- Blocks `{ ... }` open a new scope, and declarations can appear anywhere in the code, not only at the start.

### Expressions
- Arithmetic: `+ - * /` and unary minus, with the usual Java precedence. All binary operators are left-associative.
- Assignment is an expression with the lowest precedence and is right-associative, so `x = y = 3` works and assigns both.
- Integers and floats can be mixed. Implicit conversions are inserted where needed, and the compiler emits the integer or real TAC operation (`*` or `*r`) that matches the expression type.
- Assigning a float to an `int` variable is a type error.
- `char` values can be added to and subtracted from integers (`'A' + 1`, `10 + ch - 'a'`).

### Conditions
- Relational operators: `== != < > <= >=`
- Logical operators: `&&`, `||` and `!`, with **lazy** evaluation.
- Parentheses group conditions.

### Statements
- `print(expr)` prints a value according to its type: integers and floats with `print`, characters with `printc`, and booleans as the text `true` or `false`.
- `if` / `else if` / `else`
- `while`
- `do ... while`
- `for (init; condition; update)`, where any of the three parts can be empty
- Compound statements `{ ... }`, nested to any depth

```
int i, sum;
for (i = 1; i < 10; i = i + 1) {
    sum = sum + i;
}
print(sum);                  // 45
```

## Boolean features

### Boolean variables and initialisation
- Booleans are declared like any other type and can be initialised in the declaration with any boolean expression: `boolean p = x < 15 && y < 20 || z < 20;`
- Several can be declared at once: `boolean p = true, q, r = false;`
- Assigning a non-boolean value to a boolean variable is an error.
- Booleans can be assigned from conditions, constants or other booleans, and can be used as conditions in `if`, `while`, `do`, and `for`.

### Casts between booleans and integers
- `(int) condition` gives `1` for true and `0` for false.
- `(boolean) intExpr` gives `false` for `0` and `true` for any other value.

```
boolean p;
int x = (int) (p || !p);              // 1
p = (boolean) (x*x - x + x);
```

### Double implication `<-->`
- True when both sides have the same truth value.
- Right-associative, with lower priority than every other boolean operator, so `a <--> b <--> c` means `a <--> (b <--> c)`.

### `exists` over booleans
- `exists p, condition` is true if the condition holds for at least one value of `p`.
- It nests on the right and combines with other conditions.

```
boolean p, q;
if (exists p, exists q, p && q <--> p || q) print(1);   // prints 1
```

### `exists` over integers
- `exists x from A to B [step S], condition` is true if the condition holds for some value of `x` in the range. The step defaults to 1.
- On success `x` holds the first satisfying value. Otherwise it holds the end of the range plus the step.
- It nests on the right and combines with other conditions.

```
int x;
if (exists x from 1 to 10, x*x > 10 && x*x < 25) print(x);   // prints 4
```

## Error detection
On an error the compiler writes `error;` and `halt;` into the generated code, so running it stops there. There is no error recovery. Errors detected include undeclared or redeclared variables, type mismatches, and invalid casts or assignments.

## Usage

**Building:**

Requires Java, [JFlex](https://www.jflex.de/manual.html#installing-jflex) and [CUP](https://www.skenz.it/compilers/install_linux_bash#cup).

```
java java_cup.MainDrawTree PLXC.cup
jflex PLXC.flex
javac *.java
```

**Execution:**

```
java PLXC prog.plx prog.tac
```

With no arguments the compiler reads from standard input and writes to standard output. Additionally you can invoke the `tac` interpreter that runs the generated code:

```
./tac prog.tac
```

**Cleanup:**

```
rm parser.java Yylex.java sym.java
rm *.class
rm prog.tac
```

## Project layout

| File | Purpose |
|---|---|
| `PLXC.flex` | Lexer specification (JFlex) |
| `PLXC.cup` | Grammar and code-generation actions (CUP) |
| `PLXC.java` | Entry point and error helper |
| `TablaS.java` | Symbol table, scoping, and helpers |
| `Tipo.java`, `Tint.java`, `Tfloat.java`, `Tchar.java`, `Tboolean.java` | Type classes: operations and code emission per type |
| `Objeto.java`, `Instancia.java` | Symbol-table entries (variables and values) |
| `tac` | *three-address code* interpreter |
| `testcases/` | some testcases written in PLX |
