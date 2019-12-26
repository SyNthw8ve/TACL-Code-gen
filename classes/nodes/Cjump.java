package classes.nodes;

import classes.other.SymbolTable;

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
    public void emit(SymbolTable s) {
        
        String tt = t_cond.emit();
        String lt = l_true.emit();
        String lf = l_false.emit();

        System.out.println("\tbne " + tt + ", $0" + ", " + lt);
        System.out.println("\tj " + lf);
    }

    @Override
    public void emit(SymbolTable s, Head h) {
        // TODO Auto-generated method stub
        this.emit(s);
    }
}