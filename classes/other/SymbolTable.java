package classes.other;

import java.util.Hashtable;

/**
 * symboltable
 */
public class SymbolTable {

    Hashtable<String, Info> h;

    public SymbolTable() {

        this.h = new Hashtable<String, Info>();
    }

    public void add(String name, Info entry) {

        this.h.put(name, entry);
        
    }

    public Info get(String name) {

        return this.h.get(name);
    }
}