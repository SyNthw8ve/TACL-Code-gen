package classes.nodes;

import java.util.HashSet;
import java.util.LinkedList;

import classes.other.SymbolTable;

/**
 * Statement
 */
public class Statement {

    public LinkedList<Label> labels;
    public Node expr;

    public HashSet<String> live_out;
    public HashSet<String> live_in;
    public HashSet<String> ue_var;
    public HashSet<String> var_kill;

    public Statement(LinkedList<Label> l, Node e) {

        this.labels = l;
        this.expr = e;

        this.ue_var = e.get_ue_var();
        this.var_kill = e.get_var_kill();
        
        this.live_in = new HashSet<>();
        this.live_out = new HashSet<>();
    }

    public void emit(SymbolTable s, Head h) {

        for(Label l : this.labels) {

            String name = l.emit();
            System.out.println(name + ":");
        }

        expr.emit(s, h);
    }


    public boolean has_label() {

        return !this.labels.isEmpty();
    }

    public boolean is_jump() {

        return this.expr instanceof Cjump || this.expr instanceof Jump || this.expr instanceof Return;
    }

    public void re_compute() {

        this.ue_var = expr.get_ue_var();
        this.var_kill = expr.get_var_kill();
    }
}