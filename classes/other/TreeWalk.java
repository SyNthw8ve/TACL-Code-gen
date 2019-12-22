package classes.other;

import java.util.LinkedList;

import classes.nodes.GlobalDec;
import classes.nodes.IRDec;

/**
 * TreeWalk
 */
public class TreeWalk {

    public SymbolTable symbol_table;
    public LinkedList<IRDec> tree;
    public LinkedList<GlobalDec> g_tree;

    public TreeWalk(SymbolTable s, LinkedList<IRDec> t, LinkedList<GlobalDec> g) {

        this.symbol_table = s;
        this.tree = t;
        this.g_tree = g;
    }

    public void walk() {


        this.emit_global();
        
    }

    public void emit_global() {

        System.out.println("\t.data");

        for(GlobalDec i : this.g_tree) {

            Info g_var = this.symbol_table.get(i.id);

            g_var.emit();
        }

        System.out.println("\t.text");
    }
}