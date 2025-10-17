import java.util.Scanner;

public class Giocatore extends Thread {
    // --- ATTRIBUTI ---
    private String nome;
    private int punteggio;
    private int numero_scelto;
    private GestoreGioco gg;
    private final Scanner scan = new Scanner(System.in);

    // --- SETUP ---
    public Giocatore(String nome) {
        this.nome = nome;
        this.punteggio = 0;
        gg = new GestoreGioco(18);

        do {
            System.out.print(nome + " inserisci il tuo numero fortunato(1-20): ");
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
        this.punteggio = 0;
        this.gg = gg;
    }

    public String getNome(){return this.nome;}
    public int getPunteggio(){return this.punteggio;}

    // --- METODI VARI ---
    private void gioca() {
        punteggio += gg.assegnaPunteggio(numero_scelto);
        if(gg.verifica(numero_scelto) == 1) System.out.println(nome + " hai indovinato! +2pt.");
        else if(gg.verifica(numero_scelto) == 2) System.out.println(nome + " c'eri quasi! +1pt.");
        else System.out.println(nome + " hai sbagliato! +0pt.");
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

