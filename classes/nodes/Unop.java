package classes.nodes;

import java.util.HashSet;

import classes.other.PrintCode;
import classes.other.RegisterAlloc;
import classes.other.SymbolTable;

/**
 * Unop
 */
public class Unop implements Node {

    public enum Type { I_INV, NOT, I_COPY, };

    Type op_type;
    Temp t_store;
    Temp t_op;

    public Unop(Type t, Temp target, Temp t1) {

        this.op_type = t;
        this.t_store = target;
        this.t_op = t1;
    }

    @Override
    public void emit(SymbolTable s, Head h) {
        
        String tt = RegisterAlloc.get_alloc(this.t_store.temp);
        String t1 = RegisterAlloc.get_alloc(this.t_op.temp);

        switch(this.op_type) {

            case I_COPY:

                PrintCode.print_binop("or", tt, t1, "$0");
                break;

            case I_INV:

                PrintCode.print_binop("or", tt, "$0", t1);
                break;

            case NOT:

                PrintCode.print_binop("nor", tt, t1, t1);
                break;

            default:
                break;
        }

    }

    @Override
    public HashSet<String> get_ue_var() {

        HashSet<String> ue_var = new HashSet<>();

        ue_var.add(this.t_op.temp);

        return ue_var;
    }

    @Override
    public HashSet<String> get_var_kill() {

        HashSet<String> var_kill = new HashSet<>();
        
        var_kill.add(this.t_store.temp);

        return var_kill;
    }

    @Override
    public void change_ue_var(String t, Temp n_temp) {
        
        this.t_op = n_temp;

    }

    @Override
    public void change_var_kill(String t, Temp n_temp) {
        // TODO Auto-generated method stub

        this.t_store = n_temp;
    }
}