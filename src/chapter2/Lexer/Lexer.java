package chapter2.Lexer;

import java.io.IOException;
import java.util.Hashtable;

public class Lexer {
    // 比 LIP 多记录了一个行号
    public int line;
    // 当前读取的字符，在 LIP 里面称为 Lookahead 预读字符, LIP 还有一个字段记录读取的字符号，这里没有
    private char peek = ' ';
    private int p = 0;
    private String input;

    // 用来保存读取过的 word 以及存放预先计算好的 reserved keywords
    private Hashtable<String, Token> words = new Hashtable<String, Token>();

    public Lexer(String input) {
        this.input = input;
        reserve(new Word(Tag.TRUE, "true"));
        reserve(new Word(Tag.FALSE, "false"));
        reserve(new Word(Tag.INT, "int"));
        reserve(new Word(Tag.BOOL, "bool"));
        reserve(new Word(Tag.CHAR, "char"));
        reserve(new Word(Tag.LEFT_BRACKET, "{"));
        reserve(new Word(Tag.RIGHT_BRACKET, "}"));
        reserve(new Word(Tag.COMMA, ";"));
    }

    void reserve(Word t) {
        words.put(t.lexeme, t);
    }

    public Token scan() throws IOException {
        // skip white spaces
        for (; ; consume()) {
            if (peek == '\n')
                line++;
            else if (peek != ' ' && peek != '\t')
                break;
        }

        // handle number
        if (Character.isDigit(peek)) {
            int value = 0;
            while (Character.isDigit(peek)) {
                value = value * 10 + Character.digit(peek, 10);
                consume();
            }
            return new Num(value);
        }

        // handle word
        if (Character.isLetter(peek)) {
            var b = new StringBuilder();
            while (Character.isLetterOrDigit(peek)) {
                b.append(peek);
                consume();
            }
            var s = b.toString();
            if (!words.containsKey(s))
                words.put(s, new Word(Tag.ID, s));
            return words.get(s);
        }

        switch (peek) {
            case '{':
                consume();
                return words.get("{");
            case '}':
                consume();
                return words.get("}");
            case ';':
                consume();
                return words.get(";");
        }

        var token = new Token(peek);
        peek = ' ';
        return token;
    }

    private void consume() throws IOException {
//        peek = (char) System.in.read();
        if (p >= input.length())
            throw new IOException("Got end");
        peek = input.charAt(p++);
    }

    public static void main(String args[]) throws IOException {
        //{ int x; char y; { bool y; x; y; } x; y; }
        var lexer = new Lexer("{ int x; char y; { bool y; x; y; } x; y; }");
        while (true) {
            var token = lexer.scan();
            if (token.tag == ' ')
                break;
            System.out.println(token);
        }
    }


}
