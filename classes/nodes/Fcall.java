package classes.nodes;

import java.util.LinkedList;

/**
 * Fcall
 */
public class Fcall extends Node {

    public String id;
    public LinkedList<Temp> arg_list;
    public Temp target_temp;

    public Fcall(String name, LinkedList<Temp> args, Temp t) {

        this.id = name;
        this.arg_list = args;
        this.target_temp = t;
    }
}