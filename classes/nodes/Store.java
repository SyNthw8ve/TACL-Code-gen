package classes.nodes;

/**
 * Store
 */
public class Store extends Node{

    public enum Type { GLOBAL, LOCAL, ARG, };

    public Type op_type;
    public String id;
    public Temp t_target;

    public Store(Type type, String name, Temp t) {

        this.op_type = type;
        this.id = name;
        this.t_target = t;
    }
}