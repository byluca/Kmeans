package data;

import java.io.Serializable;
import java.util.Set;

public abstract class Item implements Serializable {

     Attribute attribute; //attributo coninvolto nell'item
     Object value;        //valore assegnato all'attributo

    //costruttore
     Item(Attribute attribute, Object value) {
        this.attribute = attribute;
        this.value = value;
    }

    private Attribute getAttribute() {
        return this.attribute;
    }

    Object getValue() {return this.value;}

    public String toString() {return this.value.toString();}

   abstract double distance (Object a);

   public void update(Data data, Set<Integer>clusterData)
   {
       this.value=data.computePrototype(clusterData,this.attribute);
   }
}


