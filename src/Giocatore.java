/**
 * La classe {@code Giocatore} rappresnta un giocatore con tutte le sue caratteristiche, quali: nome, punteggio e numero scelto.
 * Ogni giocatore è implementato come un {@link Thread} che verrà eseguito indipendentemente dagli altri giocatori.
 *
 * @author Francesco Chionchio
 */
public class Giocatore extends Thread {
    // --- ATTRIBUTI ---
    private String nome;
    private int punteggio;
    private int numero_scelto;
    private GestoreGioco gg;

    // --- SETUP ---
    /**
     * Crea un nuovo Giocatore specificando nome, numero scelto e gestore gioco.
     *
     * @param nome il nome del giocatore
     * @param numero_scelto il numero scelto in input dal giocatore
     * @param gg il gestore gioco
     */
    public Giocatore(String nome, int numero_scelto, GestoreGioco gg){
        this.nome = nome;
        this.numero_scelto = numero_scelto;
        this.punteggio = 0;
        this.gg = gg;
    }

    /**
     * Imposta un nuovo punteggio
     *
     * @param punteggio il nuovo punteggio
     * */
    public void setPunteggio(int punteggio) {this.punteggio = punteggio;}

    /**
     * Restituisce il nome del giocatore
     *
     * @return il nome del giocatore
     * */
    public String getNome(){return this.nome;}

    /**
     * Restituisce il punteggio del giocatore
     *
     * @return il punteggio del giocatore
     * */
    public int getPunteggio(){return this.punteggio;}

    // --- METODI VARI ---
    /**
     * Esegue un turno del giocatore, quindi
     * aggiorna il punteggio e stampa sulla console l'esito del tentativo
     */
    private void gioca() {
        punteggio += gg.assegnaPunteggio(numero_scelto);
        int numeroConfronto = gg.verifica(numero_scelto);
        if(numeroConfronto == 1) System.out.println(nome + " hai indovinato! +2pt.");
        else if(numeroConfronto == 2) System.out.println(nome + " c'eri quasi! +1pt.");
        else System.out.println(nome + " hai sbagliato! +0pt.");
    }

    /**
     *Stampa sulla console il nome e punteggio del giocatore
     */
    public void comunica() {
        System.out.println("Sono il giocatore " + nome + " -> punteggio: " + punteggio);
    }

    /**
     * Metodo eseguito quando viene avviato il thread.
     * Richiama {@link #gioca()} per far iniziare il gioco
     */
    @Override
    public void run() {
        gioca();
    }
}

