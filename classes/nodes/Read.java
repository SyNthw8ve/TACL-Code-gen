package classes.nodes;

import classes.other.PrintCode;
import classes.other.RegisterAlloc;
import classes.other.SymbolTable;

/**
 * Read
 */
public class Read implements Node {

    public enum Type { INT, BOOL, };

    Type read_type;
    Temp store_temp;

    public Read(Type r, Temp t) {

        this.read_type = r;
        this.store_temp = t;
    }

    @Override
    public void emit(SymbolTable s, Head h) {
        
        RegisterAlloc.new_alloc(this.store_temp);
        String t_name = RegisterAlloc.get_alloc(this.store_temp);

        switch(this.read_type) {

            case INT:

                PrintCode.print_op("i_read$", t_name);
                break;

            case BOOL:

                PrintCode.print_op("b_read$", t_name);
                break;

            default:
                break;
        }

    }

    @Override
    public void pre_process() {

        RegisterAlloc.new_alloc();
    }
}