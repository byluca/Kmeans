package Default;


import data.Data;
import data.OutOfRangeSampleSize;
import keyboardinput.Keyboard;
import mining.KMeansMiner;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainTest {
    /*
     * @param args
     */
    public static void main(String[] args) {

        Data data = new Data();
        System.out.println("Benvenuto");
        try {
            System.out.println("Caricamento...");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("ERRORE NEL CARICAMENTO");
            throw new RuntimeException(e);
        }
        System.out.println(data);
        KMeansMiner kMeansMiner = null;
        while (true) {
            System.out.println("Vuoi caricare un CluserSet esistente?(yes,no)");
            String risposta = Keyboard.readString().toLowerCase();
            if (risposta.equals("yes") || risposta.equals("YES") || risposta.equals("Yes"))
            {
                try {
                    kMeansMiner = new KMeansMiner("Save.dat");
                    System.out.println(kMeansMiner.getC().toString(data));
                } catch (FileNotFoundException e) {
                    System.out.println("FILE NON ESISTENTE!!!");
                    System.out.println("Vuoi uscire dal programma ?(yes,no)");
                    String esc = Keyboard.readString().toLowerCase();
                    if (esc.equals("yes") || esc.equals("YES") || esc.equals("Yes")) {
                        System.exit(0);
                    } else {
                        int numIter;
                        int k;
                        boolean nrErrore;
                        KMeansMiner kmeans = null;
                        do {
                            System.out.print("Inserisci k:");
                            nrErrore = false;
                            k = Keyboard.readInt();
                            try {
                                kmeans = new KMeansMiner(k);
                                numIter = kmeans.kmeans(data);
                                System.out.println("Numero di Iterazione:" + numIter);
                                System.out.println(kmeans.getC().toString(data));
                            } catch (OutOfRangeSampleSize ee) {
                                System.out.println(ee.getMessage());
                                nrErrore = true;
                            }
                        }
                        while (nrErrore);
                    }
                } catch (IOException e) {
                    System.out.println("ERRORE INPUT/OUTPUT!!!");
                    System.exit(0);
                } catch (ClassNotFoundException e) {
                    System.out.println("ERRORE DI CLASSE");
                    System.exit(0);
                }
            } else if (risposta.equals("no") || risposta.equals("NO") || risposta.equals("No")) {
                int numIter;
                int k;
                boolean nrErrore;
                KMeansMiner kmeans = null;
                do {
                    System.out.print("Inserisci k:");
                    nrErrore = false;
                    k = Keyboard.readInt();
                    try {
                        kmeans = new KMeansMiner(k);
                        numIter = kmeans.kmeans(data);
                        System.out.println("Numero di Iterazione:" + numIter);
                        System.out.println(kmeans.getC().toString(data));
                    } catch (OutOfRangeSampleSize ee) {
                        System.out.println(ee.getMessage());
                        nrErrore = true;
                    }
                }
                while (nrErrore);
                System.out.println("Vuoi salvare questo ClusterSet?(yes,no)");
                String save = Keyboard.readString().toLowerCase();
                if (save.equals("yes") || save.equals("YES") || save.equals("Yes")) {
                    try {
                        kMeansMiner.salva("Save.dat");
                    } catch (FileNotFoundException e) {
                        System.err.println("FILE NON TROVATO");
                        System.exit(0);
                    } catch (IOException e) {
                        System.err.println("ERRORE DI INPUT/OUTPUT");
                        System.exit(0);
                    }
                }else{
                    System.out.println("Vuoi uscire dal programma?(yes,no)");
                    String esc = Keyboard.readString().toLowerCase();
                    if (esc.equals("yes") || esc.equals("YES") || esc.equals("Yes")) {
                        System.exit(0);
                    }
                }
            } else {
                System.out.println("Carattere non valido, riprovare a inserire il carattere");
            }
        }
    }
}