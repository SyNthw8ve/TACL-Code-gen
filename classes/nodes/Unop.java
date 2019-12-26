package classes.nodes;

import classes.other.SymbolTable;

/**
 * Unop
 */
public class Unop implements Node {

    public enum Type { I_INV, NOT, I_COPY, };

    Type op_type;
    Temp t_store;
    Temp t_op;

    public Unop(Type t, Temp target, Temp t1) {

        this.op_type = t;
        this.t_store = target;
        this.t_op = t1;
    }

    @Override
    public void emit(SymbolTable s) {
        
        String tt = this.t_store.emit();
        String t1 = this.t_op.emit();

        switch(this.op_type) {

            case I_COPY:

                System.out.println("\tor " + tt + " ," + t1 + " ,$0");

                break;

            case I_INV:

                System.out.println("\tsubu " + tt + " ,$0" + " ," + t1);

                break;

            case NOT:

                System.out.println("\tnor " + tt + " ," + t1 + " ," + t1);

                break;

            default:
                break;
        }

    }

    @Override
    public void emit(SymbolTable s, Head h) {
        // TODO Auto-generated method stub

        this.emit(s);
    }
}