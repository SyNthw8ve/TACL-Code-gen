package classes.nodes;

/**
 * Head
 */
public class Head implements Node {

    public String id;

    public Head(String name) {

        this.id = name;
    }

    public void emit() {

        System.out.println(this.id.split("@")[1] + ":");
    }
}