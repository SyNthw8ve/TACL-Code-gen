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
        
        RegisterAlloc.check_spilled(this.t_op);

        RegisterAlloc.temp_use(1);
        RegisterAlloc.new_alloc(this.t_store);

        String tt = RegisterAlloc.get_alloc(this.t_store);
        String t1 = RegisterAlloc.get_alloc(this.t_op);

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
    public void pre_process() {
        
        RegisterAlloc.temp_used_pro(1);
        RegisterAlloc.new_alloc();
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
}