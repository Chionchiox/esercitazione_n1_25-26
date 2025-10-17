/***
 * La classe GestoreGioco si occupa della logica principale del gioco.
 * Contiene la variabile numeroVincente, nella quale viene salvato il numero estratto randomicamente e le seguenti funzioni:
 *  - Verificare se i numeri inseriti sono uguali a quello estratto;
 *  - Assegnare e aggiornare il punteggio di ogni giocatore;
 *  - Crea e mostra la classifica dei gioccatori in base al loro punteggio.
 * */
public class GestoreGioco {
    // --- ATTRIBUTI ---
    private int numeroVincente;

    // --- SETUP ---
    /***
     * Costruisce un nuovo GestoreGioco con il numero vincente specificato
     *
     * @param numeroVincente il numero da indovinare
     */
    public GestoreGioco(int numeroVincente){
        this.numeroVincente = numeroVincente;
    }

    /***
     * Restituisce il numero vincente
     *
     * @return il numero vincente*/
    public int getNumeroVincente(){return numeroVincente;}

    /***
     * Imposta un nuovo numero vincente
     *
     * @param numero_vincente il nuovo numero vincente
     * */
    public void setNumeroVincente(int numero_vincente){this.numeroVincente = numero_vincente;}

    // --- METODI VARI ---
    /***
     * Verifica quanto il numero scelto dal giocatore si avvicina a quello vincente
     *
     * @param num nil numero scelto dal giocatore
     * @return 1 se è giusto, 2 se si avvicina (+/-1), 0 se è sbagliato*/
    public int verifica(int num) {
        int diff = Math.abs(num - this.numeroVincente);
        if(diff == 0) return 1;
        else if(diff == 1) return 2;
        return 0;
    }

    /***
     * Assegna i punti al giocatore in base al suo risultato.
     *
     * @param num il numero inserito dal giocatore
     * @return 2 punti se giusto, 1 se vicino, 0 se sbagliato
     */
    public int assegnaPunteggio(int num){
        int diff = Math.abs(num - this.numeroVincente);
        if(diff == 0) return 2;
        else if(diff == 1) return 1;
        return 0;
    }

    /***
     * Restituisce un array di giocatori ordinati in base al loro punteggio decrescente
     *
     * @param giocatori array dei giocatori da ordinare
     * @return array dei giocatori ordinato
     * */
    public Giocatore[] classifica(Giocatore[] giocatori) {
        Giocatore[] classifica = new Giocatore[giocatori.length];
        boolean[] usato = new boolean[giocatori.length];

        for (int i = 0; i < classifica.length; i++) {
            int maxIndex = -1;
            int maxPunteggio = -1;

            for (int j = 0; j < giocatori.length; j++) {
                if (!usato[j] && giocatori[j].getPunteggio() > maxPunteggio) {
                    maxPunteggio = giocatori[j].getPunteggio();
                    maxIndex = j;
                }
            }

            classifica[i] = giocatori[maxIndex];
            usato[maxIndex] = true;
        }

        return classifica;
    }

    /***
     * Stampa sulla console la classifica dei giocatori
     *
     * @param giocatori array dei giocatori (verrà ordinato per sicurezza)
     */
    public void mostraClassifica(Giocatore[] giocatori) {
        Giocatore[] giocatoriClassificati = classifica(giocatori);
        for(int i = 1; i<=giocatori.length; i++)
            System.out.printf("%d° + %s\n", i, giocatoriClassificati[i].getNome());
    }
}


