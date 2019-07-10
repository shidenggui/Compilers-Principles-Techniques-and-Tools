package Lexer;

public class Num extends Token {
    public final int value;

    Num(int v) {
        super(Tag.NUM);
        value = v;
    }

    @Override
    public String toString() {
        return "<NUM " + value + " >";
    }
}
