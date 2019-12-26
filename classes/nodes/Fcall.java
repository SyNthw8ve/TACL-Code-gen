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
        
        

    }
}