package classes.nodes;

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
        
        RegisterAlloc.check_spilled(this.to_print);

        RegisterAlloc.temp_use(1);
        String t_name = RegisterAlloc.get_alloc(this.to_print);

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
    public void pre_process() {
        
        RegisterAlloc.temp_used_pro(1);
    }
}