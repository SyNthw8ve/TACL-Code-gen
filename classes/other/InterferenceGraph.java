package classes.other;

import java.util.Collection;
import java.util.HashMap;

import classes.nodes.Temp;

/**
 * InterferenceGraph
 */
public class InterferenceGraph {

    public HashMap<String, GraphNode> nodes;

    public InterferenceGraph(HashMap<String, Temp> ranges) {

        nodes = new HashMap<>();

        for(HashMap.Entry<String, Temp> entry : ranges.entrySet()) {
            
            String key = entry.getKey();
            
            nodes.put(key, new GraphNode(key));
        }

        Collection<Temp> c_all = ranges.values();
        Temp[] all = c_all.toArray(new Temp[c_all.size()]);

        for(int i = 0; i < all.length; i++) {

            Temp t = all[i];

            int start = t.start;
            int end = t.end;

            for(int j = i + 1; j < all.length; j++) {

                Temp tc = all[j];
    
                int tc_start = tc.start;
                int tc_end = tc.end;
    
                if ((tc.is_copy && tc.copy.compareTo(t) == 0) || (t.is_copy && t.copy.compareTo(tc) == 0)) {


                } 

                else if ((start <= tc_start && tc_start < end) || (start < tc_end && tc_end <= end)) {

                    this.add_edge(t.temp, tc.temp);
                }

                else if ((start <= tc_start && tc_end <= end) || (tc_start <= start && tc_end >= end)) {

                    this.add_edge(t.temp, tc.temp);
                }
            }
        }

        /* for(HashMap.Entry<String, GraphNode> entry : nodes.entrySet()) {

            String key = entry.getKey();
            GraphNode value = entry.getValue();
        
            System.out.println(key);

            for(GraphNode v : value.interference) {

                System.out.print(v.temp + " ");
            }

            System.out.println();
        } */
    }

    public void add_edge(String t1, String t2) {

        GraphNode t1g = this.nodes.get(t1);
        GraphNode t2g = this.nodes.get(t2);

        t1g.interference.add(t2g);
        t2g.interference.add(t1g);

        t1g.degree++;
        t2g.degree++;
    }

    public GraphNode remove_node(String t) {

        GraphNode node = this.nodes.remove(t);

        for(GraphNode n : node.interference) {

            n.degree--;
        }

        return node;
    }

    public void add_node(GraphNode g) {
        
        this.nodes.put(g.temp, g);
    }

    public boolean is_empty() {

        return this.nodes.isEmpty();
    }

}