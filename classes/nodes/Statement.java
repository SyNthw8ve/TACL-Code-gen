package classes.nodes;

import java.util.LinkedList;

/**
 * Statement
 */
public class Statement implements Node {

    public LinkedList<Label> labels;
    public Node expr;

    public Statement(LinkedList<Label> l, Node e) {

        this.labels = l;
        this.expr = e;
    }

    @Override
    public void emit() {
        // TODO Auto-generated method stub

    }
}