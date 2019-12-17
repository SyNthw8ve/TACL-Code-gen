package classes.nodes;

class GlobalDec {

    public GlobalDec dec;
    public GlobalDec sub_tree;

    public GlobalDec(GlobalDec d, GlobalDec t) {

        this.dec = d;
        this.sub_tree = t;
    }
}

class GlobalDecNoVal  {

    public ID name;
    public Type type;

    public GlobalDecNoVal(ID s, Type t) {

        this.name = s;
        this.type = t;
    }
}

class GlobalDecVal {

    public ID name;
    public Type type;

    public GlobalDecVal() {

        
    }
}