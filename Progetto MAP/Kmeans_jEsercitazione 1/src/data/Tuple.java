package data;

import java.io.Serializable;
import java.util.Set;

public class Tuple implements Serializable {
    Item[] tuple;
   Tuple(int size)
   {
       tuple = new Item[size];
   }
   public int getLength() {return tuple.length;}
   public Item get(int i)
   {
       return tuple[i];
   }
   void add(Item c, int i)
   {
      tuple[i] = c;
   }
   public double getDistance(Tuple obj) {
      double distanza = 0;
      for (int i = 0; i < tuple.length; i++) {
         if (obj.get(i) != null) {
            distanza += tuple[i].distance(obj.get(i).getValue());
         }
      }
      return distanza;
   }


   public double avgDistance(Data data, Set<Integer> clusteredData) {
      double p, sumD = 0.0;
      for (int i:clusteredData)
      {
         double d = getDistance(data.getItemSet(i));
         sumD += d;
      }
      p = sumD / clusteredData.size();
      return p;
   }
}


