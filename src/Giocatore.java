import java.util.Scanner;

public class Giocatore extends Thread {
    // --- ATTRIBUTI ---
    private String nome;
    private int punteggio;
    private int numero_scelto;
    private GestoreGioco gg;
    private Scanner scan = new Scanner(System.in);

    // --- SETUP ---
    public Giocatore(String nome) {
        this.nome = nome;
        this.punteggio = 0;
        gg = new GestoreGioco(18);

        do {
            System.out.print(nome + " inserisci il tuo numero fortunato(1-10): ");
            numero_scelto = scan.nextInt();
            if (numero_scelto < 1 || numero_scelto > 10) System.out.println("Numero inserito non valido. Reinserire.");
        }while(numero_scelto < 1 || numero_scelto > 10);
    }

    public Giocatore(String nome, GestoreGioco gg) {
        this.nome = nome;
        this.gg = gg;
    }

    public Giocatore(String nome, int numero_scelto, GestoreGioco gg) {
        this.nome = nome;
        this.numero_scelto = numero_scelto;
        this.gg = gg;
    }

    // --- METODI VARI ---
    private void gioca() {
        if(gg.verifica(numero_scelto)) System.out.println(nome + " hai vinto!");
        else System.out.println(nome + " hai perso!");
    }

    public void comunica() {
        System.out.println("Sono il giocatore " + nome);
    }

    @Override
    public void run() {
//        try {
//            sleep(5000);
//        } catch (InterruptedException e) {
//            System.out.println("Errore nell'interruzione del thread.");
//        }
        gioca();
    }
}

