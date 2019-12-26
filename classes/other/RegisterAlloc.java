package classes.other;

import java.util.HashMap;

import classes.nodes.Temp;

/**
 * RegisterAlloc
 */
public class RegisterAlloc {

    public static HashMap<String, Integer> alloced =  new HashMap<>();
    public final int K = 10;
    public static int n = 0;

    public RegisterAlloc() {


    }

    public static void temp_use(int temps) {

        n -= temps;
    }

    public static int get_temps_used() {

        return n;
    }

    public static void new_alloc(Temp t) {

        alloced.put(t.temp, n);

        n++;
    }

    public static String get_alloc(Temp t) {

        return  "$t" + alloced.get(t.temp).toString();
    }

    public static void reset() {

        n = 0;
    }
}