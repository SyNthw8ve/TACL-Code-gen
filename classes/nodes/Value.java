package classes.nodes;

import java.util.HashSet;

import classes.other.PrintCode;
import classes.other.RegisterAlloc;
import classes.other.SymbolTable;

/**
 * Value
 */
public class Value implements Node {

    public Integer value;
    public Temp target;
    public static final int MAX = 65536;

    public Value(Temp t, Integer v) {

        this.target = t;
        this.value = v;
    }

    @Override
    public void emit(SymbolTable s, Head h) {
        
        String temp = RegisterAlloc.get_alloc(this.target.temp);

        int val = this.value.intValue();

        if (val > MAX) {

            int upper = val / MAX;
            int lower = val % MAX;

            PrintCode.print_unop("lui", temp, upper);
            PrintCode.print_binop("ori", temp, temp, lower);
        }

        else {

            PrintCode.print_binop("ori", temp, "$0", val);
        }

    }

    @Override
    public HashSet<String> get_ue_var() {

        HashSet<String> ue_var = new HashSet<>();

        return ue_var;
    }

    @Override
    public HashSet<String> get_var_kill() {

        HashSet<String> var_kill = new HashSet<>();
        
        var_kill.add(this.target.temp);

        return var_kill;
    }

    @Override
    public void change_ue_var(String t, Temp n_temp) {

    }

    @Override
    public void change_var_kill(String t, Temp n_temp) {
        
        this.target = n_temp;

    }
}