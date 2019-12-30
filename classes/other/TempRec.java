package classes.other;

/**
 * TempRec
 */
public class TempRec {

    int ass_register;
    boolean reg_spilled = false;
    int stack_pos;

    public TempRec(int reg) {

        this.ass_register = reg;
    }

    public void assign_register(int id) {

        this.ass_register = id;
    }

    public void reg_spill(int pos) {

        this.reg_spilled = true;
        this.stack_pos = pos;
    }
}