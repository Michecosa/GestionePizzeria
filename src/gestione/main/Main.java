package gestione.main;

import gestione.decorator.*;
import gestione.observer.*;
import gestione.singleton.GestoreOrdini;

import javax.swing.*;
import java.awt.*;

public class Main {
    static Pizza pizza = null;
    static GestoreOrdini gestore = GestoreOrdini.getInstance();
    static Cucina cucina = new Cucina();
    static Forno forno = new Forno();
    static Consegna consegna = new Consegna();
    static String logText = "";

    public static void main(String[] args) {
        JFrame f = new JFrame("Pizzeria");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600, 500);

        JEditorPane pane = new JEditorPane("text/html", "");
        pane.setEditable(false);
        f.add(new JScrollPane(pane));

        render(pane);

        pane.addHyperlinkListener(e -> {
            if (e.getEventType() == javax.swing.event.HyperlinkEvent.EventType.ACTIVATED) {
                String cmd = e.getDescription();
                if (cmd.equals("margherita")) pizza = new Margherita();
                else if (cmd.equals("diavola")) pizza = new Diavola();
                else if (cmd.equals("mozzarella") && pizza != null) pizza = new MozzarellaDecorator(pizza);
                else if (cmd.equals("salame") && pizza != null) pizza = new SalameDecorator(pizza);
                else if (cmd.equals("funghi") && pizza != null) pizza = new FunghiDecorator(pizza);
                else if (cmd.equals("olive") && pizza != null) pizza = new OliveDecorator(pizza);
                else if (cmd.equals("ordine") && pizza != null) {
                    Ordine o = new Ordine(pizza);
                    o.aggiungiObserver(cucina); o.aggiungiObserver(forno); o.aggiungiObserver(consegna);
                    gestore.aggiungiOrdine(o);
                    log("Ordine #" + gestore.getOrdini().size() + " creato: " + o);
                    pizza = null;
                } else if (cmd.startsWith("stato:")) {
                    String[] parts = cmd.split(":");
                    int id = Integer.parseInt(parts[1]) - 1;
                    String stato = parts[2];
                    gestore.getOrdini().get(id).setStato(stato);
                    log("Ordine #" + (id + 1) + " -> " + stato);
                }
                render(pane);
            }
        });

        f.setVisible(true);
    }

    static void log(String s) { logText += s + "<br>"; }

    static void render(JEditorPane pane) {
        String pizzaInfo = pizza == null ? "nessuna" : pizza.getDescrizione() + " - " + pizza.getPrezzo() + " EUR";

        StringBuilder ordini = new StringBuilder();
        String statoStyle = "style='margin:1px 2px; padding:2px 6px; background:#4a90d9; color:white; border-radius:3px; text-decoration:none; font-size:11px;'";
        for (int i = 0; i < gestore.getOrdini().size(); i++) {
            Ordine o = gestore.getOrdini().get(i);
            int n = i + 1;
            ordini.append("<tr style='background:").append(n % 2 == 0 ? "#fff0e8" : "white").append(";'>");
            ordini.append("<td style='padding:5px 8px; border-bottom:1px solid #ddd;'>#").append(n).append("</td>");
            ordini.append("<td style='padding:5px 8px; border-bottom:1px solid #ddd;'>").append(o).append("</td>");
            ordini.append("<td style='padding:5px 8px; border-bottom:1px solid #ddd;'>");
            for (String s : new String[]{"IN PREPARAZIONE", "IN COTTURA", "PRONTO", "CONSEGNATO"})
                ordini.append("<a href='stato:").append(n).append(":").append(s).append("' ").append(statoStyle).append(">").append(s).append("</a> ");
            ordini.append("</td></tr>");
        }

        String btnStyle = "style='margin:2px 4px; padding:4px 10px; background:#e8390e; color:white; border-radius:4px; text-decoration:none; font-weight:bold; font-size:13px;'";
        String ingStyle = "style='margin:2px 4px; padding:3px 8px; background:#f5a623; color:white; border-radius:4px; text-decoration:none; font-size:12px;'";

        String html = "<html><body style='font-family:Arial; padding:14px; background:#fff8f0;'>"
            + "<h2 style='color:#c0392b; margin-bottom:6px;'>Pizzeria Gestionale</h2>"
            + "<hr style='border:1px solid #f0c0a0; margin-bottom:12px;'/>"

            + "<div style='background:#fff3e0; border:1px solid #f5a623; border-radius:6px; padding:8px 12px; margin-bottom:12px;'>"
            + "<b>Pizza corrente:</b> <span style='color:#c0392b;'>" + pizzaInfo + "</span>"
            + "</div>"

            + "<div style='margin-bottom:10px;'>"
            + "<b style='font-size:13px;'>Scegli base:</b><br>"
            + "<a href='margherita' " + btnStyle + ">Margherita</a>"
            + "<a href='diavola' " + btnStyle + ">Diavola</a>"
            + "</div>"

            + "<div style='margin-bottom:10px;'>"
            + "<b style='font-size:13px;'>Aggiungi ingrediente:</b><br>"
            + "<a href='mozzarella' " + ingStyle + ">+ Mozzarella</a>"
            + "<a href='salame' " + ingStyle + ">+ Salame</a>"
            + "<a href='funghi' " + ingStyle + ">+ Funghi</a>"
            + "<a href='olive' " + ingStyle + ">+ Olive</a>"
            + "</div>"

            + "<div style='margin-bottom:14px;'>"
            + "<a href='ordine' style='padding:6px 16px; background:#27ae60; color:white; border-radius:5px; text-decoration:none; font-weight:bold; font-size:14px;'>✔ CREA ORDINE</a>"
            + "</div>"

            + "<b style='font-size:13px;'>Ordini:</b><br>"
            + "<table style='border-collapse:collapse; margin-top:6px; width:100%;'>"
            + "<tr style='background:#c0392b; color:white;'>"
            + "<th style='padding:5px 8px; text-align:left;'>#</th>"
            + "<th style='padding:5px 8px; text-align:left;'>Dettaglio</th>"
            + "<th style='padding:5px 8px; text-align:left;'>Cambia stato</th></tr>"
            + ordini
            + "</table><br>"

            + "<div style='background:#f4f4f4; border-left:4px solid #c0392b; padding:8px 12px; border-radius:4px; font-size:12px; color:#555;'>"
            + "<b>Log:</b><br>" + logText
            + "</div>"
            + "</body></html>";

        pane.setText(html);
    }
}