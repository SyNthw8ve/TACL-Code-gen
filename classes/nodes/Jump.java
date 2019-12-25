package classes.nodes;

/**
 * Jump
 */
public class Jump implements Node {

    public Label label;

    public Jump(Label l) {

        this.label = l;
    }

    @Override
    public void emit() {
        
        String l = this.label.emit();

        System.out.println("\tj " + l);

    }
}