package classes.nodes;

/**
 * Value
 */
public class Value implements Node {

    public Integer value;
    public Temp target;

    public Value(Temp t, Integer v) {

        this.target = t;
        this.value = v;
    }

    @Override
    public void emit() {
        // TODO Auto-generated method stub

    }

}