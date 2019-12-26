package classes.nodes;

import classes.other.SymbolTable;

/**
 * Head
 */
public class Head implements Node {

    public String id;

    public Head(String name) {

        this.id = name;
    }

    public void emit(SymbolTable s) {

        System.out.println(this.id.split("@")[1] + ":");
    }

    @Override
    public void emit(SymbolTable s, Head h) {
        // TODO Auto-generated method stub
        this.emit(s);
    }
}