package classes.other;

import java.util.HashMap;

import classes.nodes.Temp;

/**
 * RegisterAlloc
 */
public class RegisterAlloc {

    public static final int K = 9;

    public static int n = 0;
    public static int spilled = 0;

    public static HashMap<String, TempRec> temps_alloced =  new HashMap<>();
    public static RegisterRec[] registers = new RegisterRec[K];

    public RegisterAlloc() {


    }

    public static void init_registers() {

        for(int i = 0; i < K; i++) {

            registers[i] = new RegisterRec(i);
        }
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

            n++;
        }

        else {

            
        }
    }

    public static String get_alloc(Temp t) {

        return  "$t" + temps_alloced.get(t.temp).ass_register;
    }

    public static void reset() {

        n = 0;
        temps_alloced = new HashMap<>();
    }
}