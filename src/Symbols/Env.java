package Symbols;


import java.util.Hashtable;

public class Env {
    private Hashtable<String, Symbol> table = new Hashtable<>();
    private Env prev;

    public Env(Env env) {
        prev = env;
    }

    public void put(String s, Symbol symbol) {
        table.put(s, symbol);
    }

    public Symbol get(String s) {
        for (var env = this; env != null; env = env.prev) {
            if (env.table.containsKey(s))
                return env.table.get(s);
        }
        return null;
    }
}
