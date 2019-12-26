package classes.nodes;

import classes.other.SymbolTable;

/**
 * Node
 */
public interface Node {

    public void emit(SymbolTable s);
    public void emit(SymbolTable s, Head h);
}