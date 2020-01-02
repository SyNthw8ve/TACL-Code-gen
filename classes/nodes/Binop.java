package classes.nodes;

import java.util.HashSet;

import classes.other.PrintCode;
import classes.other.RegisterAlloc;
import classes.other.SymbolTable;

/**
 * Binop
 */
public class Binop implements Node {

    public enum Type { I_ADD, I_SUB, I_MUL, I_DIV, MOD, I_EQ, I_LT, I_NE, I_LE, };

    public Type op_type;
    public Temp t_target;
    public Temp t_t1;
    public Temp t_t2;

    public Binop(Type op, Temp t_store, Temp t1, Temp t2) {

        this.op_type = op;
        this.t_target = t_store;
        this.t_t1 = t1;
        this.t_t2 = t2;
    }

    @Override
    public void emit(SymbolTable s, Head h) {
        
        String t1 = RegisterAlloc.get_alloc(this.t_t1);
        String t2 = RegisterAlloc.get_alloc(this.t_t2);
        String dest = RegisterAlloc.get_alloc(this.t_target);

        switch(this.op_type) {

            case I_ADD:

                PrintCode.print_binop("addu", dest, t1, t2);
                break;

            case I_SUB:

                PrintCode.print_binop("subu", dest, t1, t2);
                break;
        
            case I_MUL:

                PrintCode.print_unop("mult", t1, t2);
                PrintCode.print_op("mflo", dest);
                break;

            case I_DIV:

                PrintCode.print_unop("div", t1, t2);
                PrintCode.print_op("mflo", dest);
                break;

            case MOD:

                PrintCode.print_unop("div", t1, t2);
                PrintCode.print_op("mfhi", dest);
                break;

            case I_EQ:

                PrintCode.print_binop("xor", dest, t1, t2);
                PrintCode.print_binop("sltiu", dest, dest, 1);
                break;

            case I_LE:

                PrintCode.print_binop("slt", dest, t2, t1);
                PrintCode.print_binop("nor", dest, dest, dest);
                break;

            case I_LT:

                PrintCode.print_binop("slt", dest, t1, t2);
                break;

            case I_NE:

                PrintCode.print_binop("xor", dest, t1, t2);
                PrintCode.print_binop("slt", dest, "$0", dest); 
                break;
        }

    }


    @Override
    public HashSet<String> get_ue_var() {

        HashSet<String> ue_var = new HashSet<>();

        ue_var.add(this.t_t1.temp);
        ue_var.add(this.t_t2.temp);

        return ue_var;
    }

    @Override
    public HashSet<String> get_var_kill() {

        HashSet<String> var_kill = new HashSet<>();
        
        var_kill.add(this.t_target.temp);
        
        return var_kill;
    }

    @Override
    public void change_ue_var(String t, Temp n_temp) {
        
        if(this.t_t1.temp.compareTo(t) == 0) {

            this.t_t1 = n_temp;
        }

        if(this.t_t2.temp.compareTo(t) == 0) {

            this.t_t2 = n_temp;
        }

    }

    @Override
    public void change_var_kill(String t, Temp n_temp) {
        
        this.t_target = n_temp;
    }

    
    
}