package classes.nodes;

import java.util.HashSet;

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
    public HashSet<String> get_ue_var() {

        HashSet<String> ue_var = new HashSet<>();

        return ue_var;
    }

    @Override
    public HashSet<String> get_var_kill() {

        HashSet<String> var_kill = new HashSet<>();
        
        var_kill.add(this.store_temp.temp);

        return var_kill;
    }

    @Override
    public void change_ue_var(String t, Temp n_temp) {
        // TODO Auto-generated method stub

    }

    @Override
    public void change_var_kill(String t, Temp n_temp) {
        
        this.store_temp = n_temp;

    }
}