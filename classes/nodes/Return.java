package classes.nodes;

/**
 * Return
 */
public class Return implements Node {

    Temp return_temp;

    public Return(Temp t) {

        this.return_temp = t;
    }

    @Override
    public void emit() {
        // TODO Auto-generated method stub

    }
}