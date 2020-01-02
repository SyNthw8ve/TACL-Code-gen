package classes.other;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

/**
 * GraphColor
 */
public class GraphColor {

    public final static int K = 2;
    public LinkedList<GraphNode> spill_candidates;
    public InterferenceGraph to_color;
    public Stack<GraphNode> stack;

    public GraphColor(InterferenceGraph IG) {

        this.to_color = IG;
        this.spill_candidates = new LinkedList<>();
        this.stack = new Stack<>();
    }

    public void simplify() {

        boolean nodes_less = true;

        while(nodes_less) {

            nodes_less = false;

            Set<String> keys = new HashSet<>(to_color.nodes.keySet());

            for(String key : keys) {

                GraphNode node = to_color.nodes.get(key);

                if (node.degree < K) {
                    
                    stack.push(to_color.remove_node(node.temp));
                    nodes_less = true;
                }
            }
        }

        this.spill();
    }

    public void spill() {

        if (!to_color.is_empty()) {

            int degree = 0;
            GraphNode to_spill = null;

            for(HashMap.Entry<String, GraphNode> entry : to_color.nodes.entrySet()) {

                GraphNode node = entry.getValue();
    
                if (node.degree > degree) {
    
                    degree = node.degree;
                    to_spill = node;
                }
            }

            stack.push(to_color.remove_node(to_spill.temp));
            spill_candidates.add(to_spill);

            this.simplify();
        }
    }

    public boolean select() {

        while(!stack.empty()) {

            GraphNode v = stack.pop();

            boolean colored = pick_color(v);

            if(!colored) {

                return false;
            }

            else {

                this.to_color.add_node(v);
            }
        }

        return true;
    }

    public boolean pick_color(GraphNode g) {

        HashSet<Integer> colors = new HashSet<>();

        for(int i = 0; i < K; i++) {

            colors.add(i);
        }

        for(GraphNode v : g.interference) {

            if (v.is_colored) {

                colors.remove(v.color);
            }
        }

        if (colors.isEmpty()) return false;

        g.is_colored = true;

        for (int i = 0; i < K; i++) {

            if (colors.contains(i)) {

                g.color = i;
                return true;  
            }
        }

        return true;
    }

    public LinkedList<GraphNode> color_graph() {

        this.simplify();

        boolean built = this.select();

        /* if (built) {

            for(HashMap.Entry<String, GraphNode> entry : to_color.nodes.entrySet()) {

                String key = entry.getKey();
                GraphNode value = entry.getValue();
            
                System.out.println(key + " " + value.color);
    
                System.out.println();
            }
        } */

        return this.spill_candidates;

    }

    public HashMap<String, GraphNode> get_register() {

        return this.to_color.nodes;
    }
    
}