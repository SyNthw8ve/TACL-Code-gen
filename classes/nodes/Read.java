package classes.nodes;

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
    public void emit() {
        
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
}