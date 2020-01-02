package classes.other;

import java.util.HashMap;

/**
 * AllocInfo
 */
public class AllocInfo {

    public HashMap<String, GraphNode> registers;
    public int temps_needed;

    public AllocInfo(HashMap<String, GraphNode> reg, int temps) {

        this.registers = reg;
        this.temps_needed = temps;
    }
}