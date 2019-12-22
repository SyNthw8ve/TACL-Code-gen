package classes.nodes;

/**
 * Read
 */
public class Read implements Node {

    public enum Type { INT, BOOL, };

    Type read_type;
    Temp store_temp;

    public Read(Type r, Temp t) {

        this.read_type = r;
        this.store_temp = t;
    }

    @Override
    public void emit() {
        // TODO Auto-generated method stub

    }
}