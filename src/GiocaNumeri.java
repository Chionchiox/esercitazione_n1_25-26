import java.util.Scanner;
import java.util.Random;

public class GiocaNumeri {
    // --- ATTRIBUTI ---
    public static final Scanner scan = new Scanner(System.in);
    public static final Random rand = new Random();

    // --- MAIN ---
    public static void main(String[] args) {
        int numero_vincente = rand.nextInt(1, 11);                      //Genera numero vinccente (1-10)
        int NUM_PLAYER = 0;
        //Inizia il gioco
        System.out.println("=== INIZIA IL GIOCO ===");
        do {
            System.out.print("Inserisci il numero di giocatori (max: 4): ");         //Scegliamo il numero di giocatori (max 4)
            NUM_PLAYER = scan.nextInt();
            if (NUM_PLAYER > 4 || NUM_PLAYER < 1) System.out.println("Numero di giocatori non valido. Reinserire.");
        } while (NUM_PLAYER < 1 || NUM_PLAYER > 4);

        scan.nextLine();                                                             //Consumiamo il newline generato dal nextInt()

        GestoreGioco gg = new GestoreGioco(numero_vincente);
        Giocatore[] giocatori = new Giocatore[NUM_PLAYER];                           //Per rendere il codice generico
        for (int i = 0; i < NUM_PLAYER; i++) {                                       //Ciclo per scegliere i nomi di tutti i giocatori
            System.out.print("Inserisci il nome del " + (i + 1) + " giocatore: ");
            String nome = scan.next();

            int numero_scelto = 0;
            do{
                System.out.print(nome + " inserisci il tuo numero fortunato: ");
                numero_scelto = scan.nextInt();
                if(numero_scelto < 1 || numero_scelto > 10) System.out.println("Numero inserito non valido. Reinserire.");
            }while(numero_scelto < 1 || numero_scelto > 10);
            giocatori[i] = new Giocatore(nome, numero_scelto, gg);
        }

        for (int i = 0; i < NUM_PLAYER; i++) {
            giocatori[i].start();
            try {
                giocatori[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("=== FINE GIOCO ===");
        System.out.println("Il numero vincente era il " + gg.getNumeroVincente());
    }
}