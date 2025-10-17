import java.util.Scanner;
import java.util.Random;

public class GiocaNumeri {
    // --- ATTRIBUTI ---
    private static final Scanner scan = new Scanner(System.in);
    private static final Random rand = new Random();
    private static int NUM_PLAYER;

    public static void leggiNumeroGiocatori(){
        do {
            System.out.print("Inserisci il numero di giocatori (max: 4): ");         //Scegliamo il numero di giocatori (max 4)
            NUM_PLAYER = scan.nextInt();
            if (NUM_PLAYER > 4 || NUM_PLAYER < 1) System.out.println("Numero di giocatori non valido. Reinserire.");
        } while (NUM_PLAYER < 1 || NUM_PLAYER > 4);
        scan.nextLine();                                                             //Consumiamo il newline generato dal nextInt()
    }
    public static String[] leggiNomi(){
        String[] nomi = new String[NUM_PLAYER];
        for (int i = 0; i < NUM_PLAYER; i++) {
            System.out.print("Inserisci il nome del " + (i + 1) + " giocatore: ");
            nomi[i] = scan.next();
        }
        return nomi;
    }

    // --- MAIN ---
    public static void main(String[] args) {
        //Inizia il gioco
        int numeroVincente = rand.nextInt(1, 21);
        System.out.println("=== INIZIA IL GIOCO ===");
        System.out.println("Tenta la tua fortuna!\nHai 3 round nei quali dovrai cercare di accumulare più punti possibili!");

        leggiNumeroGiocatori();

        GestoreGioco gg = new GestoreGioco(numeroVincente);
        Giocatore[] giocatoriRound = new Giocatore[NUM_PLAYER];
        String[] nomiGiocatori = leggiNomi();
        for (int round = 1; round <= 3; round++) {
            System.out.printf("=== INIZIO ROUND %d ===%n", round);
            for(int i = 0; i < NUM_PLAYER; i++) {
                int numero_scelto = 0;
                do{
                    System.out.print(nomiGiocatori[i] + " inserisci il tuo numero fortunato(1-20): ");
                    numero_scelto = scan.nextInt();
                    if(numero_scelto < 1 || numero_scelto > 20) System.out.println("Numero inserito non valido. Reinserire.");
                }while(numero_scelto < 1 || numero_scelto > 20);
                scan.nextLine();
                giocatoriRound[i] = new Giocatore(nomiGiocatori[i], numero_scelto, gg);
            }

            for(Giocatore g : giocatoriRound)
                g.start();

            for (Giocatore g : giocatoriRound) {
                try {
                    g.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Il numero vincente era: " + gg.getNumeroVincente());
            System.out.printf("=== FINE ROUND %d ===%n", round);

            numeroVincente = rand.nextInt(1, 21);
            gg.setNumeroVincente(numeroVincente);
        }

        System.out.println("=== RISULTATI E CLASSIFICA ===");
        gg.mostraClassifica(giocatoriRound);
        System.out.println("=== FINE GIOCO ===");
    }
}