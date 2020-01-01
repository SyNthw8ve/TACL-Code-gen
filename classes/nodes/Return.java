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
            
            RegisterAlloc.check_spilled(this.return_temp);
            RegisterAlloc.temp_use(1);

            String t_ret = RegisterAlloc.get_alloc(this.return_temp);
            PrintCode.print_binop("or", "$v0", "$0", t_ret);
        }

    }

    @Override
    public void pre_process() {
       
        if(this.return_temp != null) {

            RegisterAlloc.temp_used_pro(1);
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
}
