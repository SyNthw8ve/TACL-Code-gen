package classes.nodes;

import java.util.LinkedList;

/**
 * Statement
 */
public class Statement extends Node {

    public LinkedList<Label> labels;
    public Node expr;

    public Statement(LinkedList<Label> l, Node e) {

        this.labels = l;
        this.expr = e;
    }
}