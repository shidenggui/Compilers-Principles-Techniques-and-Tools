package Lexer;

public class Word extends Token {
    public final String lexeme;

    Word(int t, String s) {
        super(t);
        lexeme = s;
    }

    @Override
    public String toString() {
        return "<Word " + tag + " " + lexeme + " >";
    }

}
