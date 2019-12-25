package classes.nodes;

/**
 * Label
 */
public class Label {

    public String label;

    public Label(String l) {

        this.label = l;
    }

    public String emit() {

        String l_number = this.label.split("l")[1];

        return "l$" + l_number;
    }
}