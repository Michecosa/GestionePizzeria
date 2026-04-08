package gestione.singleton;
import gestione.decorator.*;
import java.util.ArrayList;
import java.util.List;

public class GestoreOrdini 
{
    
    private static GestoreOrdini istanza;

    //ordine attuale
    private List<Pizza> ordineCorrente;
    
     private String statoOrdine;
    
     private GestoreOrdini() 
     {
        ordineCorrente=new ArrayList<>();
        statoOrdine="Nessun ordine";
        
    }

     public static GestoreOrdini getInstance() {
        if (istanza == null) {
            istanza = new GestoreOrdini();
        }
        return istanza;
    }


    public void aggiungiOrdine(Pizza pizza) {
        ordineCorrente.add(pizza);
        System.out.println("Aggiunto: " + pizza.getDescrizione());
    }

    public void modificaStato(String nuovoStato)
    {
        if(ordineCorrente.isEmpty())
        {
            System.out.println("Nessun ordine corrente");
        }
        else
        {
            statoOrdine = nuovoStato;
        }

    }


    public void visualizzaOrdine()
    {
         if(ordineCorrente.isEmpty())
        {
            System.out.println("Nessun ordine corrente");
        }
        else
        {
             System.out.println("Ordine Corrente: ");
            double tot=0;
            
            for(Pizza p: ordineCorrente)
            {
                System.out.println(" - " + p.getDescrizione() + 
                                   " | Prezzo: " + p.getPrezzo());
                tot += p.getPrezzo();
            }
            
        }


    }

}

