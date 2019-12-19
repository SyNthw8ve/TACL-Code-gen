package classes.other;

import java.util.LinkedList;
import classes.nodes.Type;

/**
 * Info
 */
public class Info {

    public enum Types { VAR, FUN };

    Types kind;
    Type val_type;
    LinkedList<Pair> args;
    LinkedList<Pair> locals;
    Integer val;

    public Info(Types kind, LinkedList<Pair> args, LinkedList<Pair> locals, Integer init, Type t) {

        this.kind = kind;
        this.args = args;
        this.locals = locals;
        this.val = init;
        this.val_type = t;
    }
}