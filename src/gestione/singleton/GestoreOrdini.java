package gestione.singleton;

import gestione.observer.Ordine;
import gestione.decorator.*;
import java.util.ArrayList;
import java.util.List;

public class GestoreOrdini {
    private static GestoreOrdini istanza;

    // ordine attuale
    private List<Ordine> storicoOrdini = new ArrayList<>();
    private String statoOrdine;

    private GestoreOrdini() {}

    public static GestoreOrdini getInstance() {
        if (istanza == null) {
            istanza = new GestoreOrdini();
        }
        return istanza;
    }

    public void aggiungiOrdine(Ordine ordine) {
        storicoOrdini.add(ordine);
    }

    public List<Ordine> getOrdini() {
        return storicoOrdini;
    }

    /*     
    public void modificaStato(String nuovoStato) {
        if (storicoOrdini.isEmpty()) {
            System.out.println("Nessun ordine corrente");
        } else {
            statoOrdine = nuovoStato;
        }

    } 
    
    public void visualizzaOrdine() {
        if (storicoOrdini.isEmpty()) {
            System.out.println("Nessun ordine corrente");
        } else {
            System.out.println("Ordine Corrente: ");
        double tot = 0;
        
        for (Pizza p : storicoOrdini) {
            System.out.println(" - " + p.getDescrizione() +
            " | Prezzo: " + p.getPrezzo());
            tot += p.getPrezzo();
        }
        
    }
    
}
*/

}
