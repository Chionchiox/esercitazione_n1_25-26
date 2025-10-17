import java.util.Scanner;
import java.util.Random;

/**
 * La classe {@code GiocaNumeri} è la classe principale che gestisce il gioco, nonché la classe che contiene il main.
 * <p>
 *     - L'utente sceglie il numero di giocatori (1-4) e i loro nomi; <br>
 *     - Iniziano i 3 round del gioco; <br>
 *     - Ogni round viene estratto un numero vincente casuale (da 1 a 20); <br>
 *     - Ogni giocatore sceglie il proprio numero fortunato e riceve punti in base a quanto si avvicina al numero estratto; <br>
 *     - Alla fine dei 3 round viene visualizzata la classifica dei giocatori.
 * </p>
 * */
public class GiocaNumeri {
    // --- ATTRIBUTI ---
    private static final Scanner scan = new Scanner(System.in);
    private static final Random rand = new Random();
    private static int NUM_PLAYER;

    /**
     * Legge in input il numero di giocatori partecipanti.
     * Il numero deve essere compreso tra 1 e 4.
     */
    public static void leggiNumeroGiocatori(){
        do {
            System.out.print("Inserisci il numero di giocatori (max: 4): ");         //Scegliamo il numero di giocatori (max 4)
            NUM_PLAYER = scan.nextInt();
            if (NUM_PLAYER > 4 || NUM_PLAYER < 1) System.out.println("Numero di giocatori non valido. Reinserire.");
        } while (NUM_PLAYER < 1 || NUM_PLAYER > 4);
        scan.nextLine();                                                             //Consumiamo il newline generato dal nextInt()
    }

    /**
     * Legge i nomi dei giocatori in input.
     *
     * @return array di stringhe contenente i nomi dei giocatori.
     */
    public static String[] leggiNomi(){
        String[] nomi = new String[NUM_PLAYER];
        for (int i = 0; i < NUM_PLAYER; i++) {
            System.out.print("Inserisci il nome del " + (i + 1) + " giocatore: ");
            nomi[i] = scan.next();
        }
        return nomi;
    }

    /**
     * Avvia i thread e richiama su di essi {@code .join()} per farli lavorare in serie.
     * */
    public static void inziaPartita(Giocatore[] giocatori){
        for(Giocatore g : giocatori)
            g.start();

        for (Giocatore g : giocatori) {
            try {
                g.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
    }

    /**
     * Metodo principale. Avvia e gestisce tutto il gioco.
     */
    // --- MAIN ---
    public static void main(String[] args) {

        //Inizia il gioco
        int numeroVincente = rand.nextInt(1, 21);                // Generiamo il numero vincente casualmetne (tra 1 e 20)
        System.out.println("=== INIZIA IL GIOCO ===");
        System.out.println("Tenta la tua fortuna!\nHai 3 round nei quali dovrai cercare di accumulare più punti possibili!");

        leggiNumeroGiocatori();                                               // Leggiamo in input il numero dei giocatori

        //Fase di setup dei giocatori e del gioco
        GestoreGioco gg = new GestoreGioco(numeroVincente);                   // Istanziamo il gestore gioco assegnandogli il numero vincente generato
        Giocatore[] giocatoriRound = new Giocatore[NUM_PLAYER];               // Creiamo un array di giocatori per gestire i vari round
        int[] punteggi = new int[NUM_PLAYER];                                 // Ci salviamo i punteggi di tutti i giocatori (se no non si salvano dato che usiamo sempre nuovi giocatori)
        String[] nomiGiocatori = leggiNomi();                                 // Leggiamo in input i nomi dei giocatori
        for (int round = 1; round <= 3; round++) {                            // Ciclo che gestisce i round
            System.out.printf("=== INIZIO ROUND %d ===%n", round);
            for(int i = 0; i < NUM_PLAYER; i++) {
                int numero_scelto = 0;
                do{                                                           // Inseriamo e verifichiamo la validità del numero scelto dal giocatore
                    System.out.print(nomiGiocatori[i] + " inserisci il tuo numero fortunato(1-20): ");
                    numero_scelto = scan.nextInt();
                    if(numero_scelto < 1 || numero_scelto > 20) System.out.println("Numero inserito non valido. Reinserire.");
                }while(numero_scelto < 1 || numero_scelto > 20);
                scan.nextLine();
                giocatoriRound[i] = new Giocatore(nomiGiocatori[i], numero_scelto, gg);
            }

            inziaPartita(giocatoriRound);                                     // Avviamo i thread (quindi il gioco)

            System.out.println("Il numero vincente era: " + gg.getNumeroVincente());
            System.out.printf("=== FINE ROUND %d ===%n", round);

            numeroVincente = rand.nextInt(1, 21);                 // Generiamo un nuovo numero vincente
            gg.setNumeroVincente(numeroVincente);                              // Impostiamo il nuovo numero vincente nel gestore gioco
            for (int i = 0; i < NUM_PLAYER; i++) {                             // Ciclo per aggiornare i punteggi dei giocatori
                punteggi[i] += giocatoriRound[i].getPunteggio();
            }
        }

        System.out.println("=== RISULTATI E CLASSIFICA ===");
        for(int i = 0; i < NUM_PLAYER; i++) {
            giocatoriRound[i].setPunteggio(punteggi[i]);
        }
        gg.mostraClassifica(giocatoriRound);                                   // Visualizziamo la classifica dei giocatori
        System.out.println("=== FINE GIOCO ===");
    }
}