package mining;

import data.Data;
import data.OutOfRangeSampleSize;

import java.io.*;

public class  KMeansMiner {
    ClusterSet C;
     public KMeansMiner(int k) throws OutOfRangeSampleSize {
        C = new ClusterSet(k);
    }
    public ClusterSet getC() {return C;}

    public KMeansMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
        try (ObjectInputStream pp = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)))) {
            C = (ClusterSet) pp.readObject();
            pp.close();
        }
    }

    public int kmeans(Data data) throws OutOfRangeSampleSize {
        int numberOfIterations = 0;
        //STEP 1
        C.initializeCentroids(data);
        boolean changedCluster = false;
        do {
            numberOfIterations++;
            //STEP 2
            changedCluster = false;
            for (int i = 0; i < data.getNumberOfExamples(); i++) {
                Cluster nearestCluster = C.nearestCluster(data.getItemSet(i));
                Cluster oldCluster = C.currentCluster(i);
                boolean currentChange = nearestCluster.addData(i);
                if (currentChange)
                    changedCluster = true;
            //rimuovo la tupla dal vecchio cluster
                if (currentChange && oldCluster != null)
            //il nodo va rimosso dal suo vecchio cluster
                    oldCluster.removeTuple(i);
            }
            //STEP 3
            C.updateCentroids(data);
        }
        while (changedCluster);
        return numberOfIterations;
    }

    public void salva(String fileName) throws FileNotFoundException, IOException {
        try (ObjectOutputStream salvataggio = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))){
            salvataggio.writeObject(C);
            salvataggio.close();
        }
    }
}
