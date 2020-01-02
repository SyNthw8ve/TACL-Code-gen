package classes.nodes;

import java.util.HashSet;

import classes.other.PrintCode;
import classes.other.RegisterAlloc;
import classes.other.SymbolTable;

/**
 * Print
 */
public class Print implements Node {

    public enum Type { INT, BOOL, };

    Type print_type;
    Temp to_print;

    public Print(Type t, Temp target) {

        this.print_type = t;
        this.to_print = target;
    }

    @Override
    public void emit(SymbolTable s, Head h) {
        
        String t_name = RegisterAlloc.get_alloc(this.to_print.temp);

        switch(this.print_type) {

            case INT:

                PrintCode.print_op("i_print$", t_name);
                break;

            case BOOL:

                PrintCode.print_op("b_print$", t_name);
                break;

            default:
                break;
        }

    }

    @Override
    public HashSet<String> get_ue_var() {

        HashSet<String> ue_var = new HashSet<>();

        ue_var.add(this.to_print.temp);

        return ue_var;
    }

    @Override
    public HashSet<String> get_var_kill() {

        HashSet<String> var_kill = new HashSet<>();
        
        return var_kill;
    }

    @Override
    public void change_ue_var(String t, Temp n_temp) {
        
        this.to_print = n_temp;
    }

    @Override
    public void change_var_kill(String t, Temp n_temp) {
        // TODO Auto-generated method stub

    }
}