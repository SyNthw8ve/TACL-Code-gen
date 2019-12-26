package classes.nodes;

import java.util.LinkedList;

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
        
        for(int i = this.arg_list.size() - 1; i >= 0; i--) {

            String temp = this.arg_list.get(i).emit();

            System.out.println("\taddiu $sp, $sp, -4");
            System.out.println("\tsw " + temp + ", 0($sp)");
        }

        String fname = this.id.split("@")[1];

        System.out.println("\tjal " + fname);
        
        if (this.target_temp != null) {

            String ret_t = this.target_temp.emit();

            System.out.println("\tor " + ret_t + " , $0, $v0");
        }
    }
}