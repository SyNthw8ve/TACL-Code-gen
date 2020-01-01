package classes.other;

import java.util.LinkedList;

/**
 * GraphNode
 */
public class GraphNode {

    public LinkedList<GraphNode> interference;
    public int color;
    public boolean is_colored;
    public String temp;
    public int degree;

    public GraphNode(String id) {

        this.temp = id;
        this.degree = 0;
        this.interference = new LinkedList<>();
    }
}