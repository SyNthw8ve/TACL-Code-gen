package classes.nodes;

/**
 * Jump
 */
public class Jump implements Node {

    public Label label;

    public Jump(Label l) {

        this.label = l;
    }

    @Override
    public void emit() {
        // TODO Auto-generated method stub

    }
}