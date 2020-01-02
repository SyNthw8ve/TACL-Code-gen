package classes.other;

import java.util.HashMap;
import java.util.LinkedList;
import classes.nodes.Type;

/**
 * Info
 */
public class Info {

    public enum Types { VAR, FUN, VAR_INIT };

    Types kind;
    Type val_type;
    LinkedList<Pair> args;
    LinkedList<Pair> locals;
    public HashMap<String, Integer> temps;
    Integer val;

    public Info(Types kind, LinkedList<Pair> args, LinkedList<Pair> locals, Integer init, Type t) {

        this.kind = kind;
        this.args = args;
        this.locals = locals;
        this.val = init;
        this.val_type = t;
        this.temps = new HashMap<>();
    }

    public void emit(String name) {

        switch (this.kind) {

            case VAR:
                
                System.out.print(name + ":");

                this.val_type.emit();

                break;
        
            case VAR_INIT:

                System.out.print(name + ":");

                System.out.println("\t.word " + val.toString());

                break;

            default:
                break;
        }
    }

    public int get_local_pos(String name) {

        int pos = -1;

        for(int i = 0; i < this.locals.size(); i++) {

            if (this.locals.get(i).name.compareTo(name) == 0) {

                return -(i + 2)*4;
            }
        }

        return pos;
    }

    public int get_arg_pos(String name) {

        int pos = -1;

        for(int i = 0; i < this.args.size(); i++) {

            if (this.args.get(i).name.compareTo(name) == 0) {

                return (i + 1)*4;
            }
        }

        return pos;
    }

    public int get_local_num() {

        return -(1 + this.locals.size())*4;
    }

    public int get_args_num() {

        return (1 + this.args.size())*4;
    }

    public int get_temp_pos(String key) {

        return this.temps.get(key);
    }

    public void attribute_pos(int pos, int temps) {

        for(String k : this.temps.keySet()) {

            pos -= 4;

            this.temps.put(k, pos);
        }
    }
}