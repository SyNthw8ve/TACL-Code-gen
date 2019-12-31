package classes.nodes;

import java.util.LinkedList;

import classes.other.Info;
import classes.other.PrintCode;
import classes.other.RegisterAlloc;
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

    public void prologue(int num_locals, int num_temps) {

        PrintCode.print_mem("sw", "$fp", -4, "$sp");
        PrintCode.print_binop("addiu", "$fp", "$sp", -4);
        PrintCode.print_mem("sw", "$ra", -4, "$fp");
        PrintCode.print_binop("addiu", "$sp", "$fp", num_locals + num_temps);
    }

    public void epilogue(int num_args, String head) {

        PrintCode.print_mem("lw", "$ra", -4, "$fp");
        PrintCode.print_binop("addiu", "$sp", "$fp", num_args);
        PrintCode.print_mem("lw", "$fp", 0, "$fp");

        if(head.compareTo("main") != 0) {

            PrintCode.print_jump("jr", "$ra");
        }

    }

    public int get_temp_num() {

        RegisterAlloc.reset();

        for(Statement s : this.body) {

            s.pre_process();
        }

        return RegisterAlloc.temp_space();
    }

    public void emit(SymbolTable st) {

        int num_temps = this.get_temp_num();

        this.head.emit(st, this.head);

        Info i = st.get(this.head.id);

        int num_locals = i.get_local_num();
        int num_args = i.get_args_num();

        RegisterAlloc.reset();
        RegisterAlloc.stack_pos = num_locals - 4;
        
        this.prologue(num_locals, num_temps);

        for(Statement s : this.body) {

            s.emit(st, this.head);
        }

        this.epilogue(num_args, this.head.get_id());
        
        System.out.println();
    }
}