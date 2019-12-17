package classes.nodes;

class Program {

    public GlobalDec global_dec;
    public IRDec ir_dec;

    public Program(GlobalDec g, IRDec i) {

        this.global_dec = g;
        this.ir_dec = i;
    }
}