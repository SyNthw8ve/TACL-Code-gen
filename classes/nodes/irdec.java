package classes.nodes;

import java.util.LinkedList;

import classes.other.SymbolTable;

/**
 * IRDec
 */
public class IRDec {

    public Head head;
    public LinkedList<Statement> body;

    public IRDec(Head h, LinkedList<Statement> b) {

        this.head = h;
        this.body = b;
    }

    public void emit(SymbolTable st) {

        this.head.emit(st);

        //TODO: print prologue function

        for(Statement s : this.body) {

            s.emit(st, this.head);
        }

        //TODO: print epilogue function

        System.out.println();
    }
}