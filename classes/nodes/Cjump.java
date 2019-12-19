package classes.nodes;

/**
 * Cjump
 */
public class Cjump extends Node {

    public Temp t_cond;
    public Label l_true;
    public Label l_false;

    public Cjump(Temp t, Label lt, Label lf) {

        this.t_cond = t;
        this.l_true = lt;
        this.l_false = lf;
    }
}