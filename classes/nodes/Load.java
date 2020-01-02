package classes.nodes;

import java.util.HashSet;

import classes.other.Info;
import classes.other.PrintCode;
import classes.other.RegisterAlloc;
import classes.other.SymbolTable;

/**
 * Load
 */
public class Load implements Node {

    public enum Type { GLOBAL, LOCAL, ARG, };

    public Type op_type;
    public String id;
    public Temp t_target;

    public Load(Type type, String name, Temp t) {

        this.op_type = type;
        this.id = name;
        this.t_target = t;
    }

    @Override
    public void emit(SymbolTable s, Head h) {

        String tt = RegisterAlloc.get_alloc(this.t_target);
        Info i = s.get(h.id);
        int index;

        switch(this.op_type) {

            case GLOBAL:

                String g_name = this.id.split("@")[1];

                PrintCode.print_mem("lw", tt, g_name);
                break;

            case LOCAL:

                if (this.id.startsWith("_")) {

                    index = i.get_temp_pos(this.id);
                }

                else index = i.get_local_pos(this.id);

                PrintCode.print_mem("lw", tt, index, "$fp");
                break;

            case ARG:

                index = i.get_arg_pos(this.id);

                PrintCode.print_mem("lw", tt, index, "$fp");
                break;

            default:
                break;
        }
    }


    @Override
    public HashSet<String> get_ue_var() {

        HashSet<String> ue_var = new HashSet<>();

        //ue_var.add(this.id);

        return ue_var;
    }

    @Override
    public HashSet<String> get_var_kill() {

        HashSet<String> var_kill = new HashSet<>();
        
        var_kill.add(this.t_target.temp);

        return var_kill;
    }

    @Override
    public void change_ue_var(String t, Temp n_temp) {
        // TODO Auto-generated method stub

    }

    @Override
    public void change_var_kill(String t, Temp n_temp) {
        
        this.t_target = n_temp;

    }
}
