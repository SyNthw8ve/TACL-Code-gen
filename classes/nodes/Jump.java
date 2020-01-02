package classes.nodes;

import java.util.HashSet;

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
    public void emit(SymbolTable s, Head h) {
        
        String l = this.label.emit();
        PrintCode.print_jump("j", l);
    }


    @Override
    public HashSet<String> get_ue_var() {

        HashSet<String> ue_var = new HashSet<>();

        return ue_var;
    }

    @Override
    public HashSet<String> get_var_kill() {

        HashSet<String> var_kill = new HashSet<>();
        
        return var_kill;
    }

    @Override
    public void change_ue_var(String t, Temp n_temp) {
        

    }

    @Override
    public void change_var_kill(String t, Temp n_temp) {
        

    }
}
