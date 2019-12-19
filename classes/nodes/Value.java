package classes.nodes;

/**
 * Value
 */
public class Value extends Node {

    public Integer value;
    public Temp target;

    public Value(Temp t, Integer v) {

        this.target = t;
        this.value = v;
    }

}