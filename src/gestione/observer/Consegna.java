package gestione.observer;

public class Consegna {
  public void aggiorna(int id, String stato) {
    if(stato.equals("PRONTO")) System.out.println("[CONSEGNA] Ordine #"+id+": Pizza pronta per il rider!");
  }
}
