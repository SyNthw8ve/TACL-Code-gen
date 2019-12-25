package classes.nodes;

/**
 * Binop
 */
public class Binop implements Node {

    public enum Type { I_ADD, I_SUB, I_MUL, I_DIV, MOD, I_EQ, I_LT, I_NE, I_LE, };

    public Type op_type;
    public Temp t_target;
    public Temp t_t1;
    public Temp t_t2;

    public Binop(Type op, Temp t_store, Temp t1, Temp t2) {

        this.op_type = op;
        this.t_target = t_store;
        this.t_t1 = t1;
        this.t_t2 = t2;
    }

    @Override
    public void emit() {
        
        String dest = this.t_target.emit();
        String t1 = this.t_t1.emit();
        String t2 = this.t_t2.emit();

        switch(this.op_type) {

            case I_ADD:

                System.out.println("\taddu " + dest + " ," + t1 + " ," + t2);

                break;

            case I_SUB:

                System.out.println("\tsubu " + dest + " ," + t1 + " ," + t2);

                break;
        
            case I_MUL:

                System.out.println("\tmulu " + t1 + " ," + t2);
                System.out.println("\tmflo " + dest);

                break;

            case I_DIV:

                System.out.println("\tdiv " + t1 + " ," + t2);
                System.out.println("\tmflo " + dest);

                break;

            case MOD:

                System.out.println("\tdiv " + t1 + " ," + t2);
                System.out.println("\tmfhi " + dest);

                break;

            case I_EQ:

                break;

            case I_LE:

                System.out.println("\tslt " + dest + " ," + t2 + " ," + t1);
                System.out.println("\tnor " + dest + " ," + dest + " ," + dest);

                break;

            case I_LT:

                System.out.println("\tslt " + dest + " ," + t1 + " ," + t2);

                break;

            case I_NE:

                break;
        }

    }
    
}