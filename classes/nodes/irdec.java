package classes.nodes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import classes.other.Block;
import classes.other.Info;
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

    public int get_temp_num() {

        RegisterAlloc.reset();

        for(Statement s : this.body) {

            s.pre_process();
        }

        return RegisterAlloc.temp_space();
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
    }

    public void compute_flow_graph() {

        for(Block b : basic_blocks) {

            Statement final_block_statement = this.body.get(b.end - 1);

            if (final_block_statement.expr instanceof Jump) {

                Jump j = (Jump) final_block_statement.expr;

                String label = j.label.label;
                
                Block block = this.basic_blocks.get(label_block.get(label));
                b.successors.add(block);
            }

            else if (final_block_statement.expr instanceof Cjump) {

                Cjump cj = (Cjump) final_block_statement.expr;

                String label_t = cj.l_true.label;
                String label_f = cj.l_false.label;
               
                int block_id_t = label_block.get(label_t).intValue();
                int block_id_f = label_block.get(label_f).intValue();

                Block block_t = this.basic_blocks.get(block_id_t);
                Block block_f = this.basic_blocks.get(block_id_f);
                
                b.successors.add(block_t);
                b.successors.add(block_f);
            }

            else {

                if (b.id < this.basic_blocks.size() - 1) {

                    Block next_block = this.basic_blocks.get(b.id + 1);

                    b.successors.add(next_block);
                }
            }
        }

        /* for(Block b : basic_blocks) {

            System.out.println(b.id);

            for(Block s : b.successors) {

                System.out.print(" " + s.id + 1);
            }

            System.out.println();
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

    public void block_analysis() {

        for(Block b : basic_blocks) {

            Statement s = this.body.get(b.start - 1);

            b.ue_var = new HashSet<>(s.live_in);

            HashSet<String> var_kill =  new HashSet<>();

            for(int i = b.start - 1; i < b.end; i++) {

                s = this.body.get(i);

                HashSet<String> s_var_kill = s.var_kill;

                var_kill.addAll(s_var_kill);
            }

            b.var_kill = new HashSet<>(var_kill);

            /* System.out.println(b.id);

            System.out.print("UE " + b.ue_var.toString() + " ");
            System.out.println("VAR " + b.var_kill.toString() + " "); */
               
        }

    }

    public void global_analysis() {

        boolean repeat = false;

        do {

            repeat = false;
            
            for(Block b : basic_blocks) {

                HashSet<String> lo = new HashSet<>(b.live_out_prime);

                b.live_out = lo;
            }

            for(Block b : basic_blocks) {

                HashSet<String> lo = new HashSet<>();

                for(Block succ : b.successors) {

                    HashSet<String> s_lo = new HashSet<>(succ.live_out);
                    HashSet<String> s_ue_var = new HashSet<>(succ.ue_var);
                    HashSet<String> s_var_kill = new HashSet<>(succ.var_kill);

                    s_lo.removeAll(s_var_kill);
                    s_ue_var.addAll(s_lo);

                    lo.addAll(s_ue_var);
                }

                b.live_out_prime = new HashSet<>(lo);

                if (!b.live_out.equals(b.live_out_prime)) repeat = true;
            }

        } while (repeat);

        for(Block b : basic_blocks) {

            HashSet<String> lo = new HashSet<>(b.live_out);
            HashSet<String> ue_var = new HashSet<>(b.ue_var);
            HashSet<String> var_kill = new HashSet<>(b.var_kill);

            lo.removeAll(var_kill);
            ue_var.addAll(lo);

            b.live_in = new HashSet<>(ue_var);
        }

        /* for(Block b : basic_blocks) {

            System.out.println(b.id);
               
            System.out.print("UE " + b.ue_var.toString() + " ");
            System.out.print("VAR " + b.var_kill.toString() + " ");
            System.out.print("LO " + b.live_out.toString() + " ");
            System.out.println("LI " + b.live_in.toString());
        } */ 
    }

    public void live_range() {


    }

    public void emit(SymbolTable st) {

        this.compute_basic_blocks();

        this.compute_flow_graph();

        this.live_analysis();

        this.block_analysis();

        this.global_analysis();

        int num_temps = this.get_temp_num();

        //this.head.emit(st, this.head);

        Info i = st.get(this.head.id);

        int num_locals = i.get_local_num();
        int num_args = i.get_args_num();

        RegisterAlloc.reset();
        RegisterAlloc.stack_pos = num_locals - 4;
        
       /*  this.prologue(num_locals, num_temps);

        for(Statement s : this.body) {

            s.emit(st, this.head);
        }

        this.epilogue(num_args, this.head.get_id());
        
        System.out.println(); */
    }
}