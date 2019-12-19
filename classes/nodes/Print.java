package classes.nodes;

/**
 * Print
 */
public class Print extends Node {

    public enum Type { INT, BOOL, };

    Type print_type;
    Temp to_print;

    public Print(Type t, Temp target) {

        this.print_type = t;
        this.to_print = target;
    }
}