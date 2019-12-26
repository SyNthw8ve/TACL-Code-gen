package classes.nodes;

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
    public void emit(SymbolTable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void emit(SymbolTable s, Head h) {
        
        RegisterAlloc.temp_use(this.arg_list.size());

        int in_use = RegisterAlloc.get_temps_used();

        for(int i = 0; i < in_use; i++) {

            PrintCode.print_binop("addiu", "$sp", "$sp", -4);
            PrintCode.print_mem("sw", "$t" + i, 0, "$sp");
        }

        if (this.target_temp != null) {

            RegisterAlloc.new_alloc(this.target_temp);
        }

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

        for(int i = in_use - 1; i >= 0; i--) {

            PrintCode.print_mem("lw", "$t" + i, 0, "$sp");
            PrintCode.print_binop("addiu", "$sp", "$sp", 4);
        }
    }
}