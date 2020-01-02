package classes.other;

import java.util.HashMap;

import classes.nodes.Temp;

/**
 * RegisterAlloc
 */
public class RegisterAlloc {

    public static HashMap<String, GraphNode> alloced;

    public RegisterAlloc() {


    }


    public static String get_alloc(Temp t) {

        int tt = alloced.get(t.temp).color;

        return  "$t" + tt;
    }

}