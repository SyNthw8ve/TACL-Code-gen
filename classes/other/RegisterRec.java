package classes.other;

import classes.nodes.Temp;

/**
 * RegisterRec
 */
public class RegisterRec {

    Temp current_temp;
    public int number;
    public boolean spilled = false;
    boolean available = false;

    public RegisterRec(int id) {

        this.number = id;
    }

    public void assign_temp(Temp t) {

        this.current_temp = t;
    }

    public void spill() {

        this.spilled = true;
    }

    public boolean is_spilled() {

        return this.spilled;
    }
}