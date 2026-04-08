package gestione.observer;

public class Forno implements RepartoObserver {
  public void aggiorna(int id, String stato) {
    if(stato.equals("IN COTTURA")) System.out.println("[CUCINA] Ordine #"+id+": Pizza infornata");
  }
}
