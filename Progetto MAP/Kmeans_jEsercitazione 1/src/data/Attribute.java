package data;

import java.io.Serializable;

abstract class Attribute implements Serializable {
   private String name; //nome simbolico dell'attributo
   private int index;  //identificativo numerico dell'attributo

    //costruttore
     Attribute(String name, int index) {
        this.name = name;
        this.index = index;
    }
    //metodi get
     String getName() {
        return this.name;
    }

     int getIndex() {
        return this.index;
    }

    public String toString() {
        return this.name;
    }

}
