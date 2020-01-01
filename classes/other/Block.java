package classes.other;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * Block
 */
public class Block {

    public int id;
    public int start;
    public int end;
    boolean closed;

    public LinkedList<Block> successors;
    
    public HashSet<String> live_out;
    public HashSet<String> live_out_prime;
    public HashSet<String> live_in;
    public HashSet<String> ue_var;
    public HashSet<String> var_kill;

    public Block(int id, int start) {

        this.id = id;
        this.start = start;
        this.closed = false;
        this.successors = new LinkedList<>();
        this.live_in = new HashSet<>();
        this.live_out = new HashSet<>();
        this.live_out_prime = new HashSet<>();
        this.ue_var = new HashSet<>();
        this.var_kill = new HashSet<>();
    }

    public void close(int end) {

        this.end = end;
        this.closed = true;
    }

    public boolean is_closed() {

        return this.closed;
    }
}