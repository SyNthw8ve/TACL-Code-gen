package classes.nodes;

import classes.other.SymbolTable;

/**
 * Head
 */
public class Head {

    public String id;

    public Head(String name) {

        this.id = name;
    }

    public void emit(SymbolTable s, Head h) {
        
        String name = this.id.split("@")[1];

        if (name.compareTo("main") == 0) {

            System.out.println("\t.globl main");
        }

        System.out.print(name + ":");
    }

    public String get_id() {

        return this.id.split("@")[1];
    }

}