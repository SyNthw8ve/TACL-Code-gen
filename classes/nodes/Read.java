package classes.nodes;

import classes.other.SymbolTable;

/**
 * Read
 */
public class Read implements Node {

    public enum Type { INT, BOOL, };

    Type read_type;
    Temp store_temp;

    public Read(Type r, Temp t) {

        this.read_type = r;
        this.store_temp = t;
    }

    @Override
    public void emit(SymbolTable s) {
        
        String t_name = this.store_temp.emit();

        switch(this.read_type) {

            case INT:

                System.out.println("\ti_read$ " + t_name);

                break;

            case BOOL:

                System.out.println("\tb_read$ " + t_name);

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