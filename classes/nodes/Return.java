package classes.nodes;

import java.util.HashSet;

import classes.other.PrintCode;
import classes.other.RegisterAlloc;
import classes.other.SymbolTable;

/**
 * Return
 */
public class Return implements Node {

    Temp return_temp;

    public Return(Temp t) {

        this.return_temp = t;
    }


    @Override
    public void emit(SymbolTable s, Head h) {
        
        if(this.return_temp != null) {
            
            String t_ret = RegisterAlloc.get_alloc(this.return_temp.temp);
            PrintCode.print_binop("or", "$v0", "$0", t_ret);
        }

    }


    @Override
    public HashSet<String> get_ue_var() {

        HashSet<String> ue_var = new HashSet<>();

        if (this.return_temp != null) {

            ue_var.add(this.return_temp.temp);

        }

        return ue_var;
    }

    @Override
    public HashSet<String> get_var_kill() {

        HashSet<String> var_kill = new HashSet<>();
        
        return var_kill;
    }

    @Override
    public void change_ue_var(String t, Temp n_temp) {
        
        this.return_temp = n_temp;

    }

    @Override
    public void change_var_kill(String t, Temp n_temp) {

    }
}
