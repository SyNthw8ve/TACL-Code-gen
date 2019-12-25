package classes.nodes;

import java.util.LinkedList;

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

    public void emit() {

        this.head.emit();

        //TODO: print prologue function

        for(Statement s : this.body) {

            s.emit();
        }

        //TODO: print epilogue function

        System.out.println();
    }
}