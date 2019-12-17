package classes.other;

/**
 * Pair
 */
public class Pair {

    enum Type { INT, BOOL };

    String name;
    Type type;

    public Pair(String name, Type t) {

        this.name = name;
        this.type = t;
    }
}