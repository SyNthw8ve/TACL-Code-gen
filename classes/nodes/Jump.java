package classes.nodes;

import classes.other.PrintCode;
import classes.other.SymbolTable;

/**
 * Jump
 */
public class Jump implements Node {

    public Label label;

    public Jump(Label l) {

        this.label = l;
    }

    @Override
    public void emit(SymbolTable s) {
        
        String l = this.label.emit();
        PrintCode.print_jump("j", l);
    }

    @Override
    public void emit(SymbolTable s, Head h) {
        // TODO Auto-generated method stub
        this.emit(s);
    }
}