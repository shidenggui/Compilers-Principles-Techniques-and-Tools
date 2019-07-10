package chapter2.Lexer;

public class Token {
    public final int tag;

    Token(int t) {
        tag = t;
    }

    @Override
    public String toString() {
        return "<Token " + tag + " >";
    }
}
