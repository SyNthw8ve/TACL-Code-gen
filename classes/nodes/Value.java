package classes.nodes;

import classes.other.PrintCode;
import classes.other.RegisterAlloc;
import classes.other.SymbolTable;

/**
 * Value
 */
public class Value implements Node {

    public Integer value;
    public Temp target;

    public Value(Temp t, Integer v) {

        this.target = t;
        this.value = v;
    }

    @Override
    public void emit(SymbolTable s, Head h) {
        
        RegisterAlloc.new_alloc(this.target);

        String temp = RegisterAlloc.get_alloc(this.target);

        int val = this.value.intValue();

        if (val > 65536) {

            int upper = val / 65536;
            int lower = val % 65536;

            PrintCode.print_unop("lui", temp, upper);
            PrintCode.print_binop("ori", temp, temp, lower);
        }

        else {

            PrintCode.print_binop("ori", temp, "$0", val);
        }

    }

    @Override
    public void pre_process() {
        
        RegisterAlloc.new_alloc();
    }

}