public class GestoreGioco {
    private int numero_vincente;

    // --- SETUP ---
    public GestoreGioco(int numero_vincente){
        this.numero_vincente = numero_vincente;
    }

    public int getNumeroVincente(){return numero_vincente;}

    // --- METODI VARI ---
    public boolean verifica(int num) {
        return num == numero_vincente;
    }
}


