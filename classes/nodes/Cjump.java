package classes.nodes;

/**
 * Cjump
 */
public class Cjump implements Node {

    public Temp t_cond;
    public Label l_true;
    public Label l_false;

    public Cjump(Temp t, Label lt, Label lf) {

        this.t_cond = t;
        this.l_true = lt;
        this.l_false = lf;
    }

    @Override
    public void emit() {
        
        String tt = t_cond.emit();
        String lt = l_true.emit();
        String lf = l_false.emit();

        System.out.println("\tbne " + tt + ", $0" + ", " + lt);
        System.out.println("\tj " + lf);
    }
}