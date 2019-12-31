package classes.nodes;

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
}