package chapter2;

import Symbols.Env;
import Symbols.Symbol;
import chapter2.Lexer.Lexer;
import chapter2.Lexer.Tag;
import chapter2.Lexer.Token;

import java.io.IOException;

/*
decls -> decls decl | e
decls -> decl declRest | e
declRest -> decl declRest | e

stmts -> stmts stmt | e
stmts -> stmt stmtRest | e
stmtRest -> stmt stmtRest | e
 */

public class Parser {
    private Lexer input;
    private Token lookahead;
    private Symbols.Env env;

    public Parser(Lexer lexer) throws IOException {
        input = lexer;
        consume();
    }

    public void program() throws IOException {
        block();
    }

    public void block() throws IOException {
        System.out.println("Parse block");
        match(Tag.LEFT_BRACKET);
        decls();
        stmts();
        match(Tag.RIGHT_BRACKET);
    }

    public void decls() throws IOException {
        System.out.println("Parse decls");
        if (lookahead.tag == Tag.BOOL || lookahead.tag == Tag.CHAR || lookahead.tag == Tag.INT) {
            decl();
            declRest();
        }
    }

    public void declRest() throws IOException {
        System.out.println("Parse declRest");
        if (lookahead.tag == Tag.BOOL || lookahead.tag == Tag.CHAR || lookahead.tag == Tag.INT) {
            decl();
            declRest();
        }
    }

    public void decl() throws IOException {
        System.out.println("Parse decl");
        switch (lookahead.tag) {
            case Tag.ID:
            case Tag.BOOL:
            case Tag.INT:
            case Tag.CHAR:
                match(lookahead.tag);
                break;
            default:
                throw new Error("Expect type " + "get " + lookahead.tag);
        }
        id();
        match(Tag.COMMA);
    }

    public void stmts() throws IOException {
        System.out.println("Parse stmts");
        if (lookahead.tag == Tag.LEFT_BRACKET || lookahead.tag == Tag.ID) {
            stmt();
            stmtRest();
        }
    }

    public void stmtRest() throws IOException {
        System.out.println("Parse stmtRest");
        if (lookahead.tag == Tag.LEFT_BRACKET || lookahead.tag == Tag.ID) {
            stmt();
            stmtRest();
        }
    }

    public void stmt() throws IOException {
        System.out.println("Parse stmt");
        if (lookahead.tag == Tag.LEFT_BRACKET)
            block();
        else if (lookahead.tag == Tag.ID) {
            factor();
            match(Tag.COMMA);
        } else
            throw new Error("Expect stmt got " + lookahead.tag);
    }

    public void factor() throws IOException {
        System.out.println("Parse factor");
        id();
    }

    public void id() throws IOException {
        System.out.println("Parse id");
        System.out.println(lookahead.toString());
        match(Tag.ID);
    }


    private void match(int type) throws IOException {
        if (lookahead.tag != type)
            throw new Error("Expect " + type + " get " + lookahead.tag);
        consume();
    }

    private void consume() throws IOException {
        lookahead = input.scan();
    }

    public static void main(String args[]) throws IOException {
        var lexer = new Lexer("{ int x; char y; { bool y; x; y; } x; y; }");
        var parser = new Parser(lexer);
        try {

            parser.program();
        } catch (IOException e) {
            System.out.println("Scan finished");
        }
    }
}
