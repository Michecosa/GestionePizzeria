package gestione.main;

import gestione.decorator.*;
import gestione.observer.*;
import gestione.singleton.GestoreOrdini;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GestoreOrdini gestore = GestoreOrdini.getInstance();
        Pizza pizzaInCreazione = null;

        // Inizializzo gli osservatori
        Cucina cucina = new Cucina();
        Forno forno = new Forno();
        Consegna consegna = new Consegna();

        boolean continua = true;
        while (continua) {
            System.out.println("\n--- PIZZERIA GESTIONALE ---");
            System.out.println("1. CREA NUOVA PIZZA (Margherita/Diavola)");
            System.out.println("2. AGGIUNGI INGREDIENTE");
            System.out.println("3. VISUALIZZA PIZZA CORRENTE");
            System.out.println("4. CREA ORDINE");
            System.out.println("5. CAMBIA STATO ORDINE");
            System.out.println("6. VISUALIZZA ORDINI");
            System.out.println("0. ESCI");
            int scelta = sc.nextInt();

            switch (scelta) {
                case 1:
                    System.out.println("1.Margherita, 2.Diavola");
                    pizzaInCreazione = (sc.nextInt() == 1) ? new Margherita() : new Diavola();
                    break;
                case 2:
                    if (pizzaInCreazione == null)
                        break;
                    System.out.println("1.MozzarellaExtra, 2.Salame, 3.Funghi, 4.Olive");
                    int ing = sc.nextInt();
                    if (ing == 1)
                        pizzaInCreazione = new MozzarellaDecorator(pizzaInCreazione);
                    else if (ing == 2)
                        pizzaInCreazione = new SalameDecorator(pizzaInCreazione);
                    else if (ing == 3)
                        pizzaInCreazione = new FunghiDecorator(pizzaInCreazione);
                    else if (ing == 4)
                        pizzaInCreazione = new OliveDecorator(pizzaInCreazione);
                    break;

                case 3:
                    if (pizzaInCreazione != null)
                        System.out.println(
                                pizzaInCreazione.getDescrizione() + " - " + pizzaInCreazione.getPrezzo() + " EUR");
                    break;
                case 4:
                    if (pizzaInCreazione == null)
                        break;
                    Ordine nuovo = new Ordine(pizzaInCreazione);
                    // Registro i reparti all'ordine
                    nuovo.aggiungiObserver(cucina);
                    nuovo.aggiungiObserver(forno);
                    nuovo.aggiungiObserver(consegna);
                    gestore.aggiungiOrdine(nuovo);
                    System.out.println("Ordine creato!");
                    pizzaInCreazione = null;
                    break;

                case 5:
                    System.out.println("Quale ID ordine?");
                    int id = sc.nextInt();
                    System.out.println("Stato: 1.IN PREPARAZIONE, 2.IN COTTURA, 3.PRONTO, 4.CONSEGNATO");
                    int s = sc.nextInt();
                    String[] stati = { "", "IN PREPARAZIONE", "IN COTTURA", "PRONTO", "CONSEGNATO" };
                    gestore.getOrdini().get(id - 1).setStato(stati[s]);
                    break;

                case 6:
                    for (Ordine o : gestore.getOrdini()) {
                        System.out.println(o.toString());
                    }
                    break;

                case 0:
                    continua = false;
                    break;
            }
        }

        System.out.println("\nArrivederci!\n");
    }
}