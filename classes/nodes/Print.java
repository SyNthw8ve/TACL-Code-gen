package classes.nodes;

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
    public void emit(SymbolTable s) {
        
        String t_name = this.to_print.emit();

        switch(this.print_type) {

            case INT:

                System.out.println("\ti_print$ " + t_name);

                break;

            case BOOL:

                System.out.println("\tb_print$ " + t_name);

                break;

            default:
                break;
        }

    }

    @Override
    public void emit(SymbolTable s, Head h) {
        // TODO Auto-generated method stub
        this.emit(s);
    }
}