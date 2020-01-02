package classes.nodes;

public class Temp implements Comparable<Temp> {

    public String temp;
    public int start;
    public int end;
    public boolean is_copy;
    public Temp copy;

    public Temp(String t) {

        this.temp = t;
    }

    public String emit() {

        return "$" + this.temp;
    }

    @Override
    public int compareTo(Temp tc) {
        
        if (tc.temp.compareTo(this.temp) == 0) {

            return 0;
        }

        return -1;
    }

}