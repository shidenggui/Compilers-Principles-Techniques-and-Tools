import java.io.IOException;

/*
expr -> expr + term {print('+')} | expr - term {print('-')} | term
term -> 0 | 1 | ... | 9

because left recursive so we rewrite production by
A -> Ax | Ay | z
A -> zR
R -> xR | yR | e

the result is below
expr -> expr rest
rest -> + term {print('+')} rest | - term {print('-')} rest | e

*/
class Parser {
    static int lookahead;

    public Parser() throws IOException {
        lookahead = System.in.read();
    }

    public void expr() throws IOException {
        term();
        while (true) {
            if ((char)lookahead == '+') {
                match('+'); term(); System.out.print('+');
            } else if ((char)lookahead == '-') {
                match('-'); term(); System.out.print('-');
            } else return;
        }
    }

    public void term() throws IOException {
        if (Character.isDigit((char)lookahead)) {
            // should print after match, but for convenient
            // we print before
            System.out.print((char)lookahead); match(lookahead);
        } else
            throw new Error("Syntax error");
    }

    public void match(int target) throws IOException {
        if (lookahead == target)
            lookahead = System.in.read();
        else
            throw new Error("Syntax error");

    }


}
public class Postfix {
    public static void main(String args[]) throws IOException {
        var parser = new Parser();
        parser.expr();
        System.out.println();
    }

}

/*
7 + 5 + 1
75+1+
*/
