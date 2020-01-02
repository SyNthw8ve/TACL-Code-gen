package classes.nodes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;

import classes.other.Block;
import classes.other.GraphColor;
import classes.other.GraphNode;
import classes.other.Info;
import classes.other.InterferenceGraph;
import classes.other.PrintCode;
import classes.other.RegisterAlloc;
import classes.other.SymbolTable;

/**
 * IRDec
 */
public class IRDec {

    public Head head;
    public LinkedList<Statement> body;

    public LinkedList<Block> basic_blocks = new LinkedList<>();
    public HashMap<String, Integer> label_block = new HashMap<>();

    public HashMap<String, Temp> temp_range = new HashMap<>();

    public IRDec(Head h, LinkedList<Statement> b) {

        this.head = h;
        this.body = b;
    }

    public void prologue(int num_locals, int num_temps) {

        PrintCode.print_mem("sw", "$fp", -4, "$sp");
        PrintCode.print_binop("addiu", "$fp", "$sp", -4);
        PrintCode.print_mem("sw", "$ra", -4, "$fp");
        PrintCode.print_binop("addiu", "$sp", "$fp", num_locals + num_temps);
    }

    public void epilogue(int num_args, String head) {

        PrintCode.print_mem("lw", "$ra", -4, "$fp");
        PrintCode.print_binop("addiu", "$sp", "$fp", num_args);
        PrintCode.print_mem("lw", "$fp", 0, "$fp");

        if(head.compareTo("main") != 0) {

            PrintCode.print_jump("jr", "$ra");
        }

    }


    public void compute_basic_blocks() {

        int id = 0;

        Block first_block = new Block(id, 1);
        this.basic_blocks.add(first_block);

        for(int i = 1; i < this.body.size(); i++) {

            Statement s = this.body.get(i);

            if (s.has_label()) {

                id++;

                Block prev_block = this.basic_blocks.get(id - 1);

                if (!prev_block.is_closed()) {

                    prev_block.close(i);
                }

                Block new_block = new Block(id, i + 1);
                this.basic_blocks.add(new_block);

                for(Label l : s.labels) {

                    label_block.put(l.label, id);
                }
            }

            if (s.is_jump()) {

                Block curr_block = this.basic_blocks.get(id);
                curr_block.close(i + 1);
            }
        }

        Block last_block = this.basic_blocks.getLast();
        last_block.close(this.body.size());

        /* for(Block b : basic_blocks) {

            System.out.println(b.id);

            System.out.println("start " + b.start + " " + b.end);
        } */
    }

    public void live_analysis() {

        for(Block b : basic_blocks) {

            Statement last_statement = this.body.get(b.end - 1);

            last_statement.live_out = new HashSet<>();

            HashSet<String> ue_var = new HashSet<>(last_statement.ue_var);
            HashSet<String> var_kill = new HashSet<>(last_statement.var_kill);
            HashSet<String> live_out = new HashSet<>(last_statement.live_out);

            live_out.removeAll(var_kill);
            ue_var.addAll(live_out);

            last_statement.live_in = ue_var;

            for(int i = b.end - 2; i >= b.start - 1; i--) {

                Statement m = this.body.get(i + 1);
                Statement n = this.body.get(i);

                ue_var = new HashSet<>(m.ue_var);
                var_kill = new HashSet<>(m.var_kill);
                live_out = new HashSet<>(m.live_out);

                live_out.removeAll(var_kill);
                ue_var.addAll(live_out);

                n.live_out = ue_var;

                HashSet<String> n_ue_var = new HashSet<>(n.ue_var);
                HashSet<String> n_var_kill = new HashSet<>(n.var_kill);
                HashSet<String> n_live_out = new HashSet<>(n.live_out);

                n_live_out.removeAll(n_var_kill);
                n_ue_var.addAll(n_live_out);

                n.live_in = n_ue_var;
            }
        }

        /* for(Block b : basic_blocks) {

            System.out.println(b.id);

            for(int i = b.start - 1; i < b.end; i++) {

               Statement s = this.body.get(i);
               
               System.out.print("UE " + s.ue_var.toString() + " ");
               System.out.print("VAR " + s.var_kill.toString() + " ");
               System.out.print("LO " + s.live_out.toString() + " ");
               System.out.println("LI " + s.live_in.toString());
            }
        } */
    }

    public void live_range() {

        int i = 0;

        for(Statement s : this.body) {

            HashSet<String> lo = new HashSet<>(s.live_out);
            HashSet<String> li = new HashSet<>(s.live_in);
            HashSet<String> vk = new HashSet<>(s.var_kill);

            lo.removeAll(li);
            lo.addAll(vk);

            HashSet<String> new_vars = new HashSet<>(lo);

            lo = new HashSet<>(s.live_out);
            li = new HashSet<>(s.live_in);

            li.removeAll(lo);

            HashSet<String> dead_vars = new HashSet<>(li);

            for(String v : new_vars) {

                if(!temp_range.containsKey(v)) {
                   
                    Temp t = new Temp(v);
                    t.start = i + 1;

                    if(s.expr instanceof Unop) {

                        Unop e = (Unop) s.expr;

                        if (e.op_type == Unop.Type.I_COPY) {

                            t.is_copy = true;
                            t.copy = e.t_op;
                        }
                    }

                    temp_range.put(v, t);
                }

                else {

                    Temp t = temp_range.get(v);

                    if (t.start > i + 1 || t.start == 0) t.start = i + 1;
                }
            }

            for(String v : dead_vars) {

                Temp t = temp_range.get(v);

                if (t.end < i + 1 || t.end == 0) t.end = i + 1;
                
            }

            i++;
        }

        /* for(HashMap.Entry<String, Temp> entry : temp_range.entrySet()) {
            String key = entry.getKey();
            Temp value = entry.getValue();
        
            System.out.println(key);
            System.out.println(value.start + " -> " + value.end);
        } */
    }

    public int re_shape(LinkedList<GraphNode> spilled, Info inf) {

        for(GraphNode spill : spilled) {

            LinkedList<Statement> new_body = new LinkedList<>();

            int id = 1;
            String stack_name = "_" + spill.temp;

            for(int i = 0; i < this.body.size(); i++) {

                Statement s = this.body.get(i);

                HashSet<String> ue_vars = s.ue_var;
                HashSet<String> var_kill = s.var_kill;

                if (ue_vars.contains(spill.temp)) {

                    String new_name = spill.temp + "_" + id;
                    id++;

                    Temp t = new Temp(new_name);

                    Load l = new Load(Load.Type.LOCAL, stack_name, t);
                    s.expr.change_ue_var(spill.temp, t);

                    //TODO: change temps of statement node

                   new_body.add(new Statement(new LinkedList<>(), l));
                }

                new_body.add(s);

                if (var_kill.contains(spill.temp)) {

                    String new_name = spill.temp + "_" + id;
                    id++;
                    
                    Temp t = new Temp(new_name);

                    //TODO: change temps of statement node
                    s.expr.change_var_kill(spill.temp, t);

                    Store st = new Store(Store.Type.LOCAL, stack_name, t);

                    new_body.add(new Statement(new LinkedList<>(), st));
                }

                s.re_compute();
            }

            inf.temps.put(stack_name, 0);

            this.body = new_body;
        }

        return spilled.size();
    }

    public void emit(SymbolTable st) {

        LinkedList<GraphNode> to_spill = new LinkedList<>();
        GraphColor graph_color;

        int temps = 0;

        Info i = st.get(this.head.id);

        do {

            this.basic_blocks = new LinkedList<>();
            this.label_block = new HashMap<>();
            this.temp_range = new HashMap<>();

            this.compute_basic_blocks();

            this.live_analysis();

            this.live_range();

            InterferenceGraph IG = new InterferenceGraph(temp_range);

            graph_color = new GraphColor(IG);

            to_spill = graph_color.color_graph();

            if (!to_spill.isEmpty()) temps += this.re_shape(to_spill, i);

        } while(!to_spill.isEmpty());

        int num_temps = temps * -4;

        this.head.emit(st, this.head);

        int num_locals = i.get_local_num();
        int num_args = i.get_args_num();

        i.attribute_pos(num_locals, temps);

        RegisterAlloc.alloced = graph_color.get_register();

        this.prologue(num_locals, num_temps);

        for(Statement s : this.body) {

                if (s.expr instanceof Fcall) {

                    HashSet<String> li = new HashSet<>(s.live_in);
                    HashSet<String> uv = new HashSet<>(s.ue_var);

                    li.removeAll(uv);

                    Stack<String> saved = new Stack<>();

                    for(String var : li) {

                        String tt = RegisterAlloc.get_alloc(var);
                        saved.push(tt);

                        PrintCode.print_binop("addiu", "$sp", "$sp", -4);
                        PrintCode.print_mem("sw", tt, 0, "$sp");
                    }

                    s.emit(st, this.head);

                    while(!saved.empty()) {

                        String tt = saved.pop();

                        PrintCode.print_mem("lw", tt, 0, "$sp");
                        PrintCode.print_binop("addiu", "$sp", "$sp", 4);
                    }

                }

                else {

                    s.emit(st, this.head);
                }

            }

        this.epilogue(num_args, this.head.get_id());
        
        System.out.println();
    }
}