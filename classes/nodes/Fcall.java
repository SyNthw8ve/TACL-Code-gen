package classes.nodes;

import java.util.HashSet;
import java.util.LinkedList;

import classes.other.PrintCode;
import classes.other.RegisterAlloc;
import classes.other.SymbolTable;

/**
 * Fcall
 */
public class Fcall implements Node {

    public String id;
    public LinkedList<Temp> arg_list;
    public Temp target_temp;

    public Fcall(String name, LinkedList<Temp> args, Temp t) {

        this.id = name;
        this.arg_list = args;
        this.target_temp = t;
    }

    @Override
    public void emit(SymbolTable s, Head h) {
        

        /* for(int i = 0; i < in_use; i++) {

            PrintCode.print_binop("addiu", "$sp", "$sp", -4);
            PrintCode.print_mem("sw", "$t" + i, 0, "$sp");
        } */

        for(int i = this.arg_list.size() - 1; i >= 0; i--) {

            String temp = RegisterAlloc.get_alloc(this.arg_list.get(i));

            PrintCode.print_binop("addiu", "$sp", "$sp", -4);
            PrintCode.print_mem("sw", temp, 0, "$sp");
        }

        String fname = this.id.split("@")[1];

        PrintCode.print_jump("jal", fname);
        
        if (this.target_temp != null) {

            String ret_t = RegisterAlloc.get_alloc(this.target_temp);
            PrintCode.print_binop("or", ret_t, "$0", "$v0");
        }

        /* for(int i = in_use - 1; i >= 0; i--) {

            PrintCode.print_mem("lw", "$t" + i, 0, "$sp");
            PrintCode.print_binop("addiu", "$sp", "$sp", 4);
        } */
    }

    @Override
    public HashSet<String> get_ue_var() {

        HashSet<String> ue_var = new HashSet<>();

        for(Temp t : this.arg_list) {

            ue_var.add(t.temp);
        }

        return ue_var;
    }

    @Override
    public HashSet<String> get_var_kill() {

        HashSet<String> var_kill = new HashSet<>();
        
        if (this.target_temp != null) {

            var_kill.add(this.target_temp.temp);

        }

        return var_kill;
    }

    @Override
    public void change_ue_var(String t, Temp n_temp) {
        
        for(Temp tt : this.arg_list) {

            if(tt.temp.compareTo(t) == 0) tt = n_temp;
        }

    }

    @Override
    public void change_var_kill(String t, Temp n_temp) {
       
        this.target_temp = n_temp;
    }
}