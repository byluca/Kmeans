package data;


import java.util.*;

public class Data {

    List <Example> data;
    int numberOfExamples;
    List<Attribute> attributeSet =new LinkedList<Attribute>(); //nomi degli attributi del dataset con il rispettivo indice


    public Data(){

        TreeSet<Example> tempData = new TreeSet<Example>();
        Example ex0=new Example();
        Example ex1=new Example();
        Example ex2=new Example();
        Example ex3=new Example();
        Example ex4=new Example();
        Example ex5=new Example();
        Example ex6=new Example();
        Example ex7=new Example();
        Example ex8=new Example();
        Example ex9=new Example();
        Example ex10=new Example();
        Example ex11=new Example();
        Example ex12=new Example();
        Example ex13=new Example();

        //ATTRIBUTI PRIMARI
        ex0.add("sunny");
        ex1.add("sunny");
        ex2.add("overcast");
        ex3.add("rain");
        ex4.add("rain");
        ex5.add("rain");
        ex6.add("overcast");
        ex7.add("sunny");
        ex8.add("sunny");
        ex9.add("rain");
        ex10.add("sunny");
        ex11.add("overcast");
        ex12.add("overcast");
        ex13.add("rain");

        //SECONDI ATTRIBUTI

        // VERSIONE OBSOLETA -> ex0.add(new Double (37,5));
        ex0.add(Double.valueOf(37.5));
        ex1.add(Double.valueOf(38.7));
        ex2.add(Double.valueOf(37.5));
        ex3.add(Double.valueOf(20.5));
        ex4.add(Double.valueOf(20.7));
        ex5.add(Double.valueOf(21.2));
        ex6.add(Double.valueOf(20.5));
        ex7.add(Double.valueOf(21.2));
        ex8.add(Double.valueOf(21.2));
        ex9.add(Double.valueOf(19.8));
        ex10.add(Double.valueOf(3.5));
        ex11.add(Double.valueOf(3.6));
        ex12.add(Double.valueOf(3.5));
        ex13.add(Double.valueOf(3.2));

        //TERZI ATTRIBUTI

        ex0.add("high");
        ex1.add("high");
        ex2.add("high");
        ex3.add("high");
        ex4.add("normal");
        ex5.add("normal");
        ex6.add("normal");
        ex7.add("high");
        ex8.add("normal");
        ex9.add("normal");
        ex10.add("normal");
        ex11.add("high");
        ex12.add("normal");
        ex13.add("high");

        //QUARTI ATTRIBUTI

        ex0.add("weak");
        ex1.add("strong");
        ex2.add("weak");
        ex3.add("weak");
        ex4.add("weak");
        ex5.add("strong");
        ex6.add("strong");
        ex7.add("weak");
        ex8.add("weak");
        ex9.add("weak");
        ex10.add("strong");
        ex11.add("strong");
        ex12.add("weak");
        ex13.add("strong");

        //QUINTI ATTRIBUTI

        ex0.add("no");
        ex1.add("no");
        ex2.add("yes");
        ex3.add("yes");
        ex4.add("yes");
        ex5.add("no");
        ex6.add("yes");
        ex7.add("no");
        ex8.add("yes");
        ex9.add("yes");
        ex10.add("yes");
        ex11.add("yes");
        ex12.add("yes");
        ex13.add("no");

        tempData.add(ex0);
        tempData.add(ex1);
        tempData.add(ex2);
        tempData.add(ex3);
        tempData.add(ex4);
        tempData.add(ex5);
        tempData.add(ex6);
        tempData.add(ex7);
        tempData.add(ex8);
        tempData.add(ex9);
        tempData.add(ex10);
        tempData.add(ex11);
        tempData.add(ex12);
        tempData.add(ex13);

        data=new ArrayList<Example>(tempData);
    //    System.out.println(data.size());
        numberOfExamples = data.size();

        //TODO avvalorare l'elemento di attributeSet con un oggetto della classe DiscreteAttribute
        //sotto si trova l'esempio per outlook

        String[] outLookValues=new String[3];
        outLookValues[0]="overcast";
        outLookValues[1]="rain";
        outLookValues[2]="sunny";
        attributeSet.add(new DiscreteAttribute("Outlook",0, outLookValues));

        attributeSet.add(new ContinuousAttribute("Temperature", 1, 3.2,38.7));

        String[] humidityLookValues = new String[2];
        humidityLookValues[0] = "high";
        humidityLookValues[1] = "normal";
        attributeSet.add(new DiscreteAttribute("Humidity",2,humidityLookValues));

        String[] windLookValues = new String[2];
        windLookValues[0] = "strong";
        windLookValues[1] = "week";
        attributeSet.add(new DiscreteAttribute("Wind",3,windLookValues));

        String[] playTennisValues = new String[2];
        playTennisValues[0] = "yes";
        playTennisValues[1] = "no";
        attributeSet.add( new DiscreteAttribute("PlayTennis", 4, playTennisValues));
    }
    public int getNumberOfExamples() {
        return this.numberOfExamples;
    }

   public int getNumberOfExplanatoryAttributes()
    {
            return this.attributeSet.size();
    }

    public Object getAttributeValue(int exampleIndex, int attributeIndex){
        return this.data.get(exampleIndex).get(attributeIndex);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int lastIndex = attributeSet.size() - 1;
        int i = 0;
        for (Attribute at : attributeSet) {
            sb.append(at.getName());
            if (i < lastIndex) {
                sb.append(",");
            } else {
                sb.append("\n");
            }
            i++;
        }
        i = 0;
        for (Example ex : data) {
            sb.append(i).append(":").append(ex.toString()).append("\n");
            i++;
        }
        return sb.toString();
    }
    //TODO problema nella compilazione da errore nel cambio di tipo da double a string
    public Tuple getItemSet(int index)
    {
        Tuple tuple = new Tuple(attributeSet.size());
        for (Attribute at : attributeSet) {
            if(at instanceof DiscreteAttribute)
                tuple.add(new DiscreteItem((DiscreteAttribute) at, (String) data.get(index).get(at.getIndex())), at.getIndex());
            else
                tuple.add(new ContinuousItem((ContinuousAttribute) at, (Double) data.get(index).get(at.getIndex())), at.getIndex());
        }
        return tuple;
    }

   public int[] sampling(int k) throws OutOfRangeSampleSize {
        if (k<=0 || k>data.size()) throw new OutOfRangeSampleSize("Numero di cluster non valido");
        int[] centroidIndexes  =new int[k];
        //choose k random different centroids in data.
        Random rand=new Random();
        rand.setSeed(System.currentTimeMillis());
        for (int i=0;i<k;i++){
            boolean found=false;
            int c;
            do
            {
                found=false;
                c=rand.nextInt(getNumberOfExamples());
                // verify that centroid[c] is not equal to a centroide  already stored in CentroidIndexes
                for(int j=0;j<i;j++)
                    if(compare(centroidIndexes[j],c)){
                        found=true;
                        break;
                    }
            }
            while(found);
            centroidIndexes[i]=c;
        }
        return centroidIndexes;
    }

    private boolean compare(int i, int j) {
        for (Attribute at : attributeSet) {
            int attributeIndex = at.getIndex();
            Object dataI = data.get(i).get(attributeIndex);
            Object dataJ = data.get(j).get(attributeIndex);
            if (!dataI.equals(dataJ)) {
                return false;
            }
        }
        return true;
    }

    //Il metodo controlla il tipo dell'attributo passato
    //come parametro per determinare se si tratta di un attributo discreto o continuo.
    Object computePrototype(Set<Integer> idList, Attribute attribute)
    {
        if (attribute instanceof DiscreteAttribute)
        {
            return computePrototype(idList,(DiscreteAttribute) attribute);
        }
        else {
            return computePrototype(idList, (ContinuousAttribute) attribute);
        }
    }
    private String computePrototype(Set<Integer> idList, DiscreteAttribute attribute)
    {
        Iterator<String> it = attribute.iterator();

        String prototype = it.next();
        int maxFrequency = attribute.frequency(this, idList, prototype);
        while (it.hasNext()) {
            String currentAttribute = it.next();
            int currentFrequency = attribute.frequency(this, idList, currentAttribute);

            if (currentFrequency > maxFrequency) {
                maxFrequency = currentFrequency;
                prototype = currentAttribute;
            }
        }

        return prototype;
    }

    Double computePrototype(Set<Integer> idList, ContinuousAttribute attribute)
    {
        if(idList == null || idList.isEmpty() || attribute == null)
        {
            throw new IllegalArgumentException("ATTENZIONE!! idList,attribute non possono essere nulli o vuoti");
        }
        OptionalDouble average = idList.stream()
                                 .mapToDouble(i -> (Double) data.get(i).get(attribute.getIndex()))
                                 .average();
        return average.orElse(Double.NaN);
    }

    class Example implements  Comparable<Example> //classe interna alla classe primaria
    {
        private List<Object> example=new ArrayList<Object>();

        private void add (Object o)
        {
            example.add(o);
        }
        Object get(int i){ return example.get(i);}

        public int compareTo(Example example)
        {
            Iterator <Object> current=this.example.iterator();
            Iterator <Object> parameter=example.example.iterator();

            while(current.hasNext() && parameter.hasNext())
            {
                Object object1=current.next(),object2=parameter.next();
                if(!object1.equals(object2))
                {
                    return ((Comparable)object1).compareTo(object2);
                }
            }
            return 0;

        }

        public String toString()
        {
            StringBuilder stb = new StringBuilder();
            for (Object i : example) {
                stb.append(i.toString()).append(" ");
            }
            return stb.toString().trim(); // rimuove lo spazio finale
        }
    }

}