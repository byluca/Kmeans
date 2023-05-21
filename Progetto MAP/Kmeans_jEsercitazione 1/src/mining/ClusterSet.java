package mining;

import mining.Cluster;
import data.Data;
import data.OutOfRangeSampleSize;
import data.Tuple;

import java.io.Serializable;


public class ClusterSet implements Serializable {

   private Cluster[] C;
   private int i = 0; //posizione per creare un nuovo cluster

    ClusterSet(int k) throws OutOfRangeSampleSize {
        if (k < 0) throw new OutOfRangeSampleSize("Numero di cluster errato");
        C = new Cluster[k];
        i = 0; // reimposta i su 0
    }
    private void add(Cluster c)
    {
        C[i] = c;
        i++;
    }
    private Cluster get(int i) {
        return C[i];
    }
    void initializeCentroids(Data data) throws OutOfRangeSampleSize {
        //indici casuali
        int[] centroidIndexes = data.sampling(C.length);
        for (int centroidIndex : centroidIndexes) {
            //prende la tuple dalla riga centroidIndexs[i]
            Tuple centroidI = data.getItemSet(centroidIndex);
            add(new Cluster(centroidI)); //diventa il nuovo centroide del cluster i
        }
    }
     Cluster nearestCluster(Tuple tuple)
    {
        double minimo=tuple.getDistance(C[0].getCentroid());
        Cluster c=C[0]; //assegna il primo cluster come il cluster più vicino.
        double var;
        for(int i=1; i<C.length;i++) {
            var = tuple.getDistance(C[i].getCentroid());
            if (var < minimo) {
                minimo = var;
                c = C[i]; //assegna il cluster corrente come il nuovo cluster più vicino.
            }
        }
        return c;
    }
    /*Identifica  e  restituisce  il  cluster a  cui  la  tupla rappresentate l'esempio identificato da id. Se la tupla non è inclusa in
     nessun  cluster restituisce  null  (fare  uso  del  metodo  contain()  dellaclasse Cluster).*/
     Cluster currentCluster(int id)
    {
        for(int i=0; i<C.length;i++)
        {
            if(C[i].contain(id))
            {
                return C[i];
            }
        }
        return null;
    }
    void updateCentroids(Data data)
    {
        for(int i=0; i<C.length;i++)
        {
            C[i].computeCentroid(data);
        }
    }
    public String toString() {
        String str = " ";
        for (int i = 0; i < C.length; i++) {
            str+=C[i].toString();
        }
        return str;
    }
    public String toString(Data data)
    {
        String str="";
        for(int i=0;i<C.length;i++){
            if (C[i]!=null){
                str+=i+":"+C[i].toString(data)+"\n";
            }
        }
        return str;
    }

}



