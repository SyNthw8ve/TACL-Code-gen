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

        String name = this.id.split("@")[1];

        if (name.compareTo("main") == 0) {

            System.out.println("\t.globl main");
        }

        System.out.print(name + ":");
    }

    @Override
    public void emit(SymbolTable s, Head h) {
        // TODO Auto-generated method stub
        this.emit(s);
    }
}