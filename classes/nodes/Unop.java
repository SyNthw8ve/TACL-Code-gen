package classes.nodes;

/**
 * Unop
 */
public class Unop extends Node {

    public enum Type { I_INV, NOT, I_COPY, };

    Type op_type;
    Temp t_store;
    Temp t_op;

    public Unop(Type t, Temp target, Temp t1) {

        this.op_type = t;
        this.t_store = target;
        this.t_op = t1;
    }
}