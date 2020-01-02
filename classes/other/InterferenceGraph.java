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

        this.nodes = new HashMap<>();

        for(String key : ranges.keySet()) {

            nodes.put(key, new GraphNode(key));
        }

        Collection<Temp> c_temps = ranges.values();
        Temp[] temps = c_temps.toArray(new Temp[c_temps.size()]);

        this.build_graph(temps);

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

    private boolean overlap(int start, int tc_start, int end, int tc_end) {

        boolean do_overlap = ((start <= tc_start && tc_start < end) ||
                              (start < tc_end && tc_end <= end))    ||
                             ((start <= tc_start && tc_end <= end)  ||
                              (tc_start <= start && tc_end >= end));

        return do_overlap;
    }

    private void build_graph(Temp[] temps) {

        for(int i = 0; i < temps.length; i++) {

            Temp t = temps[i];

            int start = t.start;
            int end = t.end;

            for(int j = i + 1; j < temps.length; j++) {

                Temp tc = temps[j];
    
                int tc_start = tc.start;
                int tc_end = tc.end;
    
                if ((tc.is_copy && tc.copy.compareTo(t) == 0) || (t.is_copy && t.copy.compareTo(tc) == 0)) {


                } 

                else if (this.overlap(start, tc_start, end, tc_end)) {

                    this.add_edge(t.temp, tc.temp);
                }
            }
        }
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