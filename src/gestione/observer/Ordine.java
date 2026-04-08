package gestione.observer;

import gestione.decorator.*;
import java.util.ArrayList;
import java.util.List;


public class Ordine {
  private static int counter = 0;

  private int id;
  private Pizza pizza;
  private String stato;
  private List<RepartoObserver> osservatori = new ArrayList<>();

  public Ordine(Pizza p) {
    this.id = ++counter;
    this.pizza = p;
    this.stato = "CREATO";
  }

  public void aggiungiObserver(RepartoObserver o) {
    osservatori.add(o);
  }

  public void setStato(String nuovoStato) {
    for(RepartoObserver o : osservatori) {
      o.aggiorna(id, nuovoStato);
    }
  }

  public String toString() {
    return "#"+id+" - "+pizza.getDescrizione()+"["+stato+"] - "+String.format("%.2f", pizza.getPrezzo()+" EUR");
  }

}
