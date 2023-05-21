package data;



import java.util.Arrays;
import java.util.Set;
import java.util.Iterator;
import java.util.TreeSet;

public class DiscreteAttribute extends Attribute implements Iterable<String>{

    private TreeSet<String> values;

    public DiscreteAttribute(String name, int index, String values[]) {
        super(name, index);
        this.values = new TreeSet<String>();
        this.values.addAll(Arrays.asList(values));//TODO: cambia da quello di fonty
    }

        public Iterator<String> iterator () {
            return this.values.iterator();
        }

        int frequency (Data data, Set < Integer > idList, String v)
        {
            int cont = 0;
            for (int i : idList) {
                // se il valore restituito dal metodo "getAttributeValue" dell'oggetto "data", passando come parametro l'i-esimo elemento dell'array "vector" e l'indice corrente dell'istanza di questo oggetto, è uguale alla stringa "v", allora viene incrementato il valore della variabile "count".
                if (data.getAttributeValue(i, this.getIndex()).equals(v)) {
                    cont++;
                }
            }
            return cont;
        }

}