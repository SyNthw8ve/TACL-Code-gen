package classes.other;

/**
 * PrintCode
 */
public class PrintCode {

    public PrintCode() {


    }

    public static void print_binop(String op, String rd, String rs, String rt) {

        System.out.printf("\t%-7s %s, %s, %s\n", op, rd, rs, rt);
    }

    public static void print_binop(String op, String rd, String rs, int rt) {

        System.out.printf("\t%-7s %s, %s, %-5d\n", op, rd, rs, rt);
    }

    public static void print_unop(String op, String rd, String rs) {

        System.out.printf("\t%-7s %s, %s\n", op, rd, rs);
    }

    public static void print_unop(String op, String rd, int rs) {

        System.out.printf("\t%-7s %s, %-5d\n", op, rd, rs);
    }

    public static void print_op(String op, String rd) {

        System.out.printf("\t%-7s %s\n", op, rd);
    }

    public static void print_mem(String op, String rd, int off, String pointer) {

        System.out.printf("\t%-7s %s, %d(%s)\n", op, rd, off, pointer);
    }

    public static void print_mem(String op, String rd, String g_mem) {

        System.out.printf("\t%-7s %s, %s\n", op, rd, g_mem);
    }

    public static void print_jump(String op, String label) {

        System.out.printf("\t%-7s %s\n", op, label);
    }

    public static void print_cond_jump(String rd, String rs, String lt, String lf) {

        System.out.printf("\t%-7s %s, %s, %s\n", "bne", rd, rs, lt);
        System.out.printf("\t%-7s %s\n", "j", lf);
    }
}