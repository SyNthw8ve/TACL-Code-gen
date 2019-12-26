package classes.nodes;

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
    public void emit(SymbolTable s) {
        
        String temp = this.target.emit();

        int val = this.value.intValue();

        if (val > 65536) {

            int upper = val / 65536;
            int lower = val % 65536;

            System.out.println("\tlui " + temp + " ," + upper);
            System.out.println("\tori " + temp + " ," + temp + " ," + lower);
        }

        else {

            System.out.println("\tori " + temp + " ,$0" + " ," + val);
        }

    }

    @Override
    public void emit(SymbolTable s, Head h) {
        // TODO Auto-generated method stub

        this.emit(s);
    }

}