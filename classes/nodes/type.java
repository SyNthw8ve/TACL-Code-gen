package classes.nodes;

public class Type {

    public enum Types {

        INT, VOID, BOOL
    }
    
    Types val_type;

    public Type(Types t) {

        this.val_type = t;
    }
}