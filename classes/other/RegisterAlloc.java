package classes.other;

import java.util.HashMap;

import classes.nodes.Temp;

/**
 * RegisterAlloc
 */
public class RegisterAlloc {

    public static final int K = 10;

    public static int n = 0;
    public static int spilled = 0;
    public static int stack_pos = 0;

    public static HashMap<String, TempRec> temps_alloced =  new HashMap<>();
    public static RegisterRec[] registers = new RegisterRec[K];

    public RegisterAlloc() {


    }

    public static void init_registers() {

        for(int i = 0; i < K; i++) {

            registers[i] = new RegisterRec(i);
        }
    }

    public static void temp_used_pro(int temps) {

        n -= temps;
    }

    public static void temp_use(int temps) {
        
        n -= temps;
    }

    public static int get_temps_used() {

        return n;
    }

    public static void new_alloc() {

        if (n < K) {

            n++;
        }

        else {

            spilled++;
        }
    }

    public static void new_alloc(Temp t) {

        if (n < K) {

            temps_alloced.put(t.temp, new TempRec(n));
            registers[n].assign_temp(t);

            n++;
        }

        else {

            RegisterRec to_spill = register_to_spill();
            TempRec spilled_temp = temps_alloced.get(to_spill.current_temp.temp);

            PrintCode.print_mem("sw", "$t" + to_spill.number, stack_pos, "$fp");
            spilled_temp.reg_spill(stack_pos);
            stack_pos -= 4;

            temps_alloced.put(t.temp, new TempRec(to_spill.number));

            to_spill.assign_temp(t);
            to_spill.spill();

            spilled++;
        }
    }

    public static void check_spilled(Temp t) {

        TempRec tt = temps_alloced.get(t.temp);

        if(tt.reg_spilled) {

            tt.ass_register = n;
            tt.reg_spilled = false;

            temps_alloced.put(t.temp, tt);
            registers[n].assign_temp(t);

            n++;

            PrintCode.print_mem("lw", "$t" + tt.ass_register, tt.stack_pos, "$fp");
        }
    }

    public static String get_alloc(Temp t) {

        TempRec tt = temps_alloced.get(t.temp);

        return  "$t" + tt.ass_register;
    }

    public static RegisterRec register_to_spill() {

        RegisterRec to_spill = new RegisterRec(-1);

        for(int i = 0; i < K; i++) {

            if(!registers[i].spilled) {

                return registers[i];
            }
        }

        return to_spill;
    }

    public static void reset() {

        n = 0;
        spilled = 0;
        stack_pos = 0;

        temps_alloced = new HashMap<>();
        init_registers();
    }

    public static int temp_space() {

        return -4*spilled;
    }
}