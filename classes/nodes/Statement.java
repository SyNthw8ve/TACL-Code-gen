package classes.nodes;

import java.util.LinkedList;

import classes.other.SymbolTable;

/**
 * Statement
 */
public class Statement implements Node {

    public LinkedList<Label> labels;
    public Node expr;

    public Statement(LinkedList<Label> l, Node e) {

        this.labels = l;
        this.expr = e;
    }

    @Override
    public void emit(SymbolTable s) {
        
        for(Label l : this.labels) {

            String name = l.emit();
            System.out.print(name + ":");
        }

        expr.emit(s);
    }

    @Override
    public void emit(SymbolTable s, Head h) {
        // TODO Auto-generated method stub

        for(Label l : this.labels) {

            String name = l.emit();
            System.out.print(name + ":");
        }

        expr.emit(s, h);
    }
}