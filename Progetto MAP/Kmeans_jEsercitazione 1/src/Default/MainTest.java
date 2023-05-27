package Default;

import data.Data;
import data.OutOfRangeSampleSize;
import keyboardinput.Keyboard;
import mining.KMeansMiner;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.ServerException;

public class MainTest {
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public MainTest(String ip, int port) throws IOException {
        InetAddress addr = InetAddress.getByName(ip);
        System.out.println("addr = " + addr);
        Socket socket = new Socket(addr, port);
        System.out.println(socket);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    private static boolean getInput(String msg) {
        String choice;
        while (true) {
            System.out.print(msg);
            choice = Keyboard.readString().toLowerCase();
            if (choice.charAt(0) == 'n' && choice.length() == 1) {
                return false;
            } else if (choice.charAt(0) == 'y' && choice.length() == 1) {
                return true;
            }
            System.out.println("Valore non valido, ritentare");
        }
    }

    private int menu(){
        int answer;
        System.out.println("Scegli una opzione");
        do{
            System.out.println("(1) Carica Cluster da File");
            System.out.println("(2) Carica Dati");
            System.out.print("Risposta:");
            answer=Keyboard.readInt();
        }
        while(answer<=0 || answer>2);
        return answer;
    }

    private String learningFromFile() throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(3);
        System.out.print("Nome tabella:");
        String tabName = Keyboard.readString();
        out.writeObject(tabName);
        System.out.print("Numero iterate:");
        int k = Keyboard.readInt();
        out.writeObject(k);
        String result = (String) in.readObject();
        if (result.equals("OK"))
            return (String) in.readObject();
        else
            throw new ServerException(result);
    }

    private void storeTableFromDb() throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(0);
        System.out.print("Nome tabella:");
        String tabName = Keyboard.readString();
        out.writeObject(tabName);
        String result = (String) in.readObject();
        if (!result.equals("OK"))
            throw new ServerException(result);
    }

    private String learningFromDbTable() throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(1);
        System.out.print("Numero di cluster:");
        int k = Keyboard.readInt();
        out.writeObject(k);
        String result = (String) in.readObject();
        if (result.equals("OK")) {
            System.out.println("Clustering output:" + in.readObject());
            return (String) in.readObject();
        } else
            throw new ServerException(result);
    }

    private void storeClusterInFile() throws SocketException, ServerException, IOException, ClassNotFoundException {
        out.writeObject(2);
        String result = (String) in.readObject();
        if (!result.equals("OK"))
            throw new ServerException(result);
    }

    public static void main(String[] args) {
        Data data = new Data();
        System.out.println("Benvenuto");
        try {
            System.out.println("Caricamento...");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean run = true;
        while (run) {
            System.out.println("1. Eseguire clustering");
            System.out.println("2. Esci");
            System.out.print("Inserisci scelta: ");
            int input = Keyboard.readInt();
            switch (input) {
                case 1:
                    KMeansMiner kmeans = null;
                    while (true) {
                        System.out.println("Vuoi caricare un CluserSet esistente?(yes,no)");
                        String risposta = Keyboard.readString().toLowerCase();
                        if (risposta.equals("yes") || risposta.equals("YES") || risposta.equals("Yes")) {
                            try {
                                kmeans = new KMeansMiner("Save.dat");
                                System.out.println(kmeans.getC().toString(data));
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
                            } catch (ClassNotFoundException e) {
                                System.out.println("ERRORE DI CLASSE");

                            }
                        } else if (risposta.equals("no") || risposta.equals("NO") || risposta.equals("No")) {
                            int numIter;
                            int k;
                            boolean nrErrore;
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
                                    kmeans.salva("Save.dat");
                                } catch (FileNotFoundException e) {
                                    System.err.println("FILE NON TROVATO");
                                } catch (IOException e) {
                                    System.err.println("ERRORE DI INPUT/OUTPUT");
                                }
                            } else {
                                System.out.println("Vuoi uscire dal programma?(yes,no)");
                                String esc = Keyboard.readString().toLowerCase();
                                if (esc.equals("yes") || esc.equals("YES") || esc.equals("Yes")) {
                                    System.exit(0);
                                } else break;
                            }
                        } else {
                            System.out.println("Carattere non valido, riprovare a inserire il carattere");
                        }
                        break;
                    }
                    break;
                case 2:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Scelta non valida");
            }
        }
    }
}
