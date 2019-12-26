package classes.nodes;

import classes.other.SymbolTable;

/**
 * Return
 */
public class Return implements Node {

    Temp return_temp;

    public Return(Temp t) {

        this.return_temp = t;
    }

    @Override
    public void emit(SymbolTable s) {
        // TODO Auto-generated method stub

    }

    @Override
    public void emit(SymbolTable s, Head h) {
        
        if(this.return_temp != null) {

            String t_ret = this.return_temp.emit();

            System.out.println("\tor $v0, $0, " + t_ret);
        }

    }
}