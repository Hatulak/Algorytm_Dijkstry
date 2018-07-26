import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
	    File plik_in = new File("In0304.txt");
        Scanner scanner = new Scanner(plik_in);
        int n,m;
        n = scanner.nextInt();
        m = scanner.nextInt() -1; //zmniejszenie m o 1 dla łatwiejszego operowania na macierzy
        int infinity = 0;
        //WCZYTANIE MACIERZY WAG KRAWEDZI GRAFU
        int tab[][] = new int[n][n];
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                tab[i][j] = scanner.nextInt();
                if(tab[i][j] > infinity) infinity = tab[i][j];
            }
        }
        infinity++;

        //Zamienianie -1 na "infinity"
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(tab[i][j] == -1) tab[i][j] = infinity;
            }
        }

        //TWORZENIE DIST I PRED
        int dist[] = new int[n];
        for(int i=0; i<n;i++){
            dist[i] = infinity;
            if(i==m) dist[i] = 0;
        }

        int pred[] = new int[n];
        for(int i=0; i<n;i++)
            pred[i] = -1;


        LinkedList<Integer> wierzcholki = new LinkedList<Integer>();
        for(int i=0; i<n; i++){
            wierzcholki.add(i);
        }

        while(wierzcholki.size() != 0){
            int min_dist = dist[wierzcholki.get(0)];
            int wierz = wierzcholki.get(0);
            int indeks_wierz = 0;
            int i;
            for(i=0; i< wierzcholki.size();i++){
                if(dist[wierzcholki.get(i)] < min_dist){
                    min_dist = dist[wierzcholki.get(i)];
                    wierz = wierzcholki.get(i);
                    indeks_wierz = i;
                }
            }
            wierzcholki.remove(indeks_wierz);

            for(int j=0; j<n; j++){
                if(tab[wierz][j] == infinity) continue;

                boolean czy_jest_na_liscie = false;
                for(int k=0; k<wierzcholki.size(); k++){
                    if(wierzcholki.get(k) == j) {
                        czy_jest_na_liscie = true;
                        break;
                    }
                }
                if(czy_jest_na_liscie == false) continue;

                if(dist[j] > dist[wierz] + tab[wierz][j]){
                    dist[j] = dist[wierz] + tab[wierz][j];
                    pred[j] = wierz;
                }
            }
        }
        //zwiekszanie każdego poprzednika o 1 aby odwrocic
        for(int i=0; i<n; i++){
          if(pred[i] == -1) continue;
          pred[i]++;
        }

        FileWriter plik_out = new FileWriter("Out0304.txt");
        plik_out.write("dist[");
        for(int i=0; i<n; i++){
            if(dist[i] == infinity){
                plik_out.write( "\u221E ");
                continue;
            }
            plik_out.write(dist[i] + " ");
        }
        plik_out.write("]\npred[");
        for(int i=0; i<n; i++){
            plik_out.write(pred[i] + " ");
        }
        plik_out.write("]");

        plik_out.close();
    }
}
