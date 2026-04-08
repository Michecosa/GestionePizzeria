package gestione.observer;

public class Cucina implements RepartoObserver {
  public void aggiorna(int id, String stato) {
    if(stato.equals("IN PREPARAZIONE")) {
      System.out.println("[CUCINA] Ordine #"+id+": Iniziamo a stendere l'impasto");
    }
  }
}
