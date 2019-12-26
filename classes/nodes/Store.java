package classes.nodes;

import classes.other.Info;
import classes.other.SymbolTable;

/**
 * Store
 */
public class Store implements Node{

    public enum Type { GLOBAL, LOCAL, ARG, };

    public Type op_type;
    public String id;
    public Temp t_target;

    public Store(Type type, String name, Temp t) {

        this.op_type = type;
        this.id = name;
        this.t_target = t;
    }

    @Override
    public void emit(SymbolTable s) {
        

    }

    @Override
    public void emit(SymbolTable s, Head h) {
        // TODO Auto-generated method stub
        String tt = this.t_target.emit();
        Info i = s.get(h.id);
        int index;

        switch(this.op_type) {

            case GLOBAL:

                String g_name = this.id.split("@")[1];

                System.out.println("\tsw " + tt + " ," + g_name);

                break;

            case LOCAL:

                index = i.get_local_pos(this.id);

                System.out.println("\tsw " + tt + " ," + index + "($fp)");

                break;

            case ARG:

                index = i.get_arg_pos(this.id);

                System.out.println("\tsw " + tt + " ," + index + "($fp)");

                break;

            default:
                break;
        }
    }
}