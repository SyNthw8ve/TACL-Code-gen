package classes.nodes;

import java.util.LinkedList;

import classes.other.Info;
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

        Info i = st.get(this.head.id);

        System.out.println("\tsw $fp, -4($sp)");
        System.out.println("\taddiu $fp, $sp, -4");
        System.out.println("\tsw $ra, -4($fp)");

        int num_locals = i.get_local_num();

        System.out.println("\taddiu $sp, $fp, " + num_locals);

        for(Statement s : this.body) {

            s.emit(st, this.head);
        }

        System.out.println("\tlw $ra, -4($fp)");
        System.out.println("\taddiu $sp, $fp, " + -1*num_locals);
        System.out.println("\tlw $fp, 0($fp)");
        System.out.println("\tjr $ra");
        
        System.out.println();
    }
}