package classes.nodes;

/**
 * Print
 */
public class Print implements Node {

    public enum Type { INT, BOOL, };

    Type print_type;
    Temp to_print;

    public Print(Type t, Temp target) {

        this.print_type = t;
        this.to_print = target;
    }

    @Override
    public void emit() {
        // TODO Auto-generated method stub

    }
}