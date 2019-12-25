package classes.nodes;

public class Type {

    public enum Types {

        INT, VOID, BOOL
    }
    
    Types val_type;

    public Type(Types t) {

        this.val_type = t;
    }

    public void emit() {

        switch (val_type) {
            
            case INT:
                
                System.out.println("\t.space 4");

                break;

            case BOOL:
                
                System.out.println("\t.space 4");
                
                break;
        
            default:
                break;
        }
    }
}