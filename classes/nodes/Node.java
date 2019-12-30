package classes.nodes;

import classes.other.SymbolTable;

/**
 * Node
 */
public interface Node {

    public void emit(SymbolTable s, Head h);
    public void pre_process();
}