package classes.nodes;

/**
 * Binop
 */
public class Binop extends Node {

    public enum Type { I_ADD, I_SUB, I_MUL, I_DIV, MOD, I_EQ, I_LT, I_NE, I_LE, };

    public Type op_type;
    public Temp t_target;
    public Temp t_t1;
    public Temp t_t2;

    public Binop(Type op, Temp t_store, Temp t1, Temp t2) {

        this.op_type = op;
        this.t_target = t_store;
        this.t_t1 = t1;
        this.t_t2 = t2;
    }
}