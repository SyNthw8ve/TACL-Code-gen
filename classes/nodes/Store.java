package classes.nodes;

import java.util.HashSet;

import classes.other.Info;
import classes.other.PrintCode;
import classes.other.RegisterAlloc;
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
    public void emit(SymbolTable s, Head h) {
        
        RegisterAlloc.check_spilled(this.t_target);
        RegisterAlloc.temp_use(1);

        String tt = RegisterAlloc.get_alloc(this.t_target);
        Info i = s.get(h.id);
        int index;

        switch(this.op_type) {

            case GLOBAL:

                String g_name = this.id.split("@")[1];

                PrintCode.print_mem("sw", tt, g_name);
                break;

            case LOCAL:

                index = i.get_local_pos(this.id);

                PrintCode.print_mem("sw", tt, index, "$fp");
                break;

            case ARG:

                index = i.get_arg_pos(this.id);

                PrintCode.print_mem("sw", tt, index, "$fp");
                break;

            default:
                break;
        }
    }

    @Override
    public void pre_process() {
        
        RegisterAlloc.temp_used_pro(1);

    }

    @Override
    public HashSet<String> get_ue_var() {

        HashSet<String> ue_var = new HashSet<>();

        ue_var.add(this.t_target.temp);

        return ue_var;
    }

    @Override
    public HashSet<String> get_var_kill() {

        HashSet<String> var_kill = new HashSet<>();
        
        //var_kill.add(this.id);

        return var_kill;
    }

    @Override
    public void change_ue_var(String t, Temp n_temp) {
        
        this.t_target = n_temp;

    }

    @Override
    public void change_var_kill(String t, Temp n_temp) {
        // TODO Auto-generated method stub

    }
}