package classes.other;

import java.util.LinkedList;

/**
 * Info
 */
public class Info {

    public enum Type { VAR, FUNCTION };

    Type kind;
    LinkedList<Pair> args;
    LinkedList<Pair> locals;
    Integer val;

    public Info(Type kind, LinkedList<Pair> args, LinkedList<Pair> locals, Integer init) {

        this.kind = kind;
        this.args = args;
        this.locals = locals;
        this.val = init;
    }
}