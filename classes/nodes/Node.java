package classes.nodes;

import java.util.HashSet;

import classes.other.SymbolTable;

/**
 * Node
 */
public interface Node {

    public void emit(SymbolTable s, Head h);
    public void pre_process();

    public HashSet<String> get_ue_var();
    public HashSet<String> get_var_kill();
}