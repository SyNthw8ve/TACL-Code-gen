package classes.nodes;

import java.util.HashSet;

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
        
        String tt = RegisterAlloc.get_alloc(this.t_cond.temp);
        String lt = l_true.emit();
        String lf = l_false.emit();

        PrintCode.print_cond_jump(tt, "$0", lt, lf);
    }


    @Override
    public HashSet<String> get_ue_var() {

        HashSet<String> ue_var = new HashSet<>();

        ue_var.add(this.t_cond.temp);

        return ue_var;
    }

    @Override
    public HashSet<String> get_var_kill() {

        HashSet<String> var_kill = new HashSet<>();
        
        return var_kill;
    }

    @Override
    public void change_ue_var(String t, Temp n_temp) {
        
        this.t_cond = n_temp;

    }

    @Override
    public void change_var_kill(String t, Temp n_temp) {
        

    }
}