package classes.nodes;

import classes.other.PrintCode;
import classes.other.RegisterAlloc;
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
    public void emit(SymbolTable s, Head h) {
        
        RegisterAlloc.temp_use(1);

        String tt = RegisterAlloc.get_alloc(this.t_cond);
        String lt = l_true.emit();
        String lf = l_false.emit();

        PrintCode.print_cond_jump(tt, "$0", lt, lf);
    }

    @Override
    public void pre_process() {
        
        RegisterAlloc.temp_used_pro(1);
    }
}