package gestione.main;

import gestione.decorator.*;
import gestione.observer.*;
import gestione.singleton.GestoreOrdini;
import javax.swing.*;

public class Main {
    static Pizza pizza = null;
    static GestoreOrdini gestore = GestoreOrdini.getInstance();
    static Cucina cucina = new Cucina();
    static Forno forno = new Forno();
    static Consegna consegna = new Consegna();
    static String logText = "";

    public static void main(String[] args) {
        // Look & Feel di sistema per rendere la finestra più nativa
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}

        JFrame f = new JFrame("Pizzeria Pro - Dashboard");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(700, 650);
        f.setLocationRelativeTo(null); // Centra la finestra

        JEditorPane pane = new JEditorPane("text/html", "");
        pane.setEditable(false);
        // Rende lo scroll più fluido
        JScrollPane scrollPane = new JScrollPane(pane);
        scrollPane.setBorder(null);
        f.add(scrollPane);

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
                    o.aggiungiObserver(cucina);
                    o.aggiungiObserver(forno);
                    o.aggiungiObserver(consegna);
                    gestore.aggiungiOrdine(o);
                    log("<b style='color:#27ae60;'>[SISTEMA]</b> Ordine #" + gestore.getOrdini().size() + " inviato con successo.");
                    pizza = null;
                } else if (cmd.startsWith("stato:")) {
                    String[] parts = cmd.split(":");
                    int id = Integer.parseInt(parts[1]) - 1;
                    String stato = parts[2];
                    gestore.getOrdini().get(id).setStato(stato);
                    log("<b style='color:#2980b9;'>[INFO]</b> Ordine #" + (id + 1) + " aggiornato a: " + stato);
                }
                render(pane);
            }
        });

        f.setVisible(true);
    }

    static void log(String s) { 
        logText = "• " + s + "<br>" + logText; // Log invertito per vedere subito l'ultimo
    }

    static void render(JEditorPane pane) {
        String pizzaInfo = pizza == null ? "<i>Nessuna pizza selezionata</i>" : 
                           "<b style='font-size:14px;'>" + pizza.getDescrizione().toUpperCase() + "</b><br>" +
                           "<span style='color:#27ae60; font-size:16px;'>€ " + String.format("%.2f", (double)pizza.getPrezzo()) + "</span>";

        StringBuilder ordiniHTML = new StringBuilder();
        
        // Stile per i badge degli stati
        String baseBadge = "display:inline-block; margin:2px; padding:3px 7px; text-decoration:none; font-size:10px; color:white; font-weight:bold; border-radius:3px;";

        for (int i = 0; i < gestore.getOrdini().size(); i++) {
            Ordine o = gestore.getOrdini().get(i);
            int n = i + 1;
            String rowColor = (i % 2 == 0) ? "#ffffff" : "#fcfcfc";
            
            ordiniHTML.append("<tr bgcolor='").append(rowColor).append("'>")
                      .append("<td style='padding:10px; border-bottom:1px solid #eee;'><b>#").append(n).append("</b></td>")
                      .append("<td style='padding:10px; border-bottom:1px solid #eee;'>").append(o).append("</td>")
                      .append("<td style='padding:10px; border-bottom:1px solid #eee;'>");
            
            // Pulsanti di stato colorati diversamente
            String[] stati = {"IN PREPARAZIONE", "IN COTTURA", "PRONTO", "CONSEGNATO"};
            String[] colori = {"#f39c12", "#e67e22", "#27ae60", "#7f8c8d"};
            
            for (int j = 0; j < stati.length; j++) {
                ordiniHTML.append("<a href='stato:").append(n).append(":").append(stati[j])
                          .append("' style='").append(baseBadge).append("background:").append(colori[j]).append(";'> ")
                          .append(stati[j]).append(" </a> ");
            }
            ordiniHTML.append("</td></tr>");
        }

        // CSS "Inline" per i pulsanti principali
        String btnBase = "display:block; padding:8px 12px; text-decoration:none; color:white; font-weight:bold; text-align:center; border-radius:5px;";
        String btnRed = btnBase + "background:#c0392b;";
        String btnOrange = btnBase + "background:#d35400;";
        String btnGreen = "display:block; padding:12px; text-decoration:none; color:white; font-weight:bold; text-align:center; background:#27ae60; border-radius:8px; font-size:14px;";

        String html = "<html><body style='font-family:Sans-Serif; background:#f4f7f6; margin:0; padding:20px;'>"
            + "<table width='100%' cellspacing='0' cellpadding='0'>"
            + "<tr><td>"
            + "  <h1 style='color:#2c3e50; margin:0;'>🍕 Pizzeria Dashboard</h1>"
            + "  <p style='color:#7f8c8d; margin-top:5px;'>Gestione ordini e produzione in tempo reale</p>"
            + "</td></tr>"
            + "</table>"
            
            + "<hr size='1' color='#dcdde1' style='margin:20px 0;'>"

            // Sezione Superiore: Creazione Pizza
            + "<table width='100%' cellspacing='10' cellpadding='0'>"
            + "<tr>"
            + "  <td width='30%' valign='top' bgcolor='#ffffff' style='padding:15px; border:1px solid #dcdde1; border-radius:10px;'>"
            + "    <b style='color:#7f8c8d;'>1. SELEZIONA BASE</b><br><br>"
            + "    <a href='margherita' style='" + btnRed + "'>MARGHERITA</a><br>"
            + "    <a href='diavola' style='" + btnRed + "'>DIAVOLA</a>"
            + "  </td>"
            + "  <td width='40%' valign='top' bgcolor='#ffffff' style='padding:15px; border:1px solid #dcdde1; border-radius:10px;'>"
            + "    <b style='color:#7f8c8d;'>2. AGGIUNGI EXTRA</b><br><br>"
            + "    <table width='100%'><tr>"
            + "      <td><a href='mozzarella' style='" + btnOrange + "'>+ Mozzarella</a></td>"
            + "      <td><a href='salame' style='" + btnOrange + "'>+ Salame</a></td>"
            + "    </tr><tr>"
            + "      <td><a href='funghi' style='" + btnOrange + "'>+ Funghi</a></td>"
            + "      <td><a href='olive' style='" + btnOrange + "'>+ Olive</a></td>"
            + "    </tr></table>"
            + "  </td>"
            + "  <td width='30%' valign='top' bgcolor='#f9f9f9' style='padding:15px; border:2px dashed #bdc3c7; border-radius:10px;'>"
            + "    <b style='color:#7f8c8d;'>RIEPILOGO</b><br><br>"
            +      pizzaInfo + "<br><br>"
            + (pizza != null ? "<a href='ordine' style='" + btnGreen + "'>CONFERMA ORDINE</a>" : "")
            + "  </td>"
            + "</tr>"
            + "</table><br>"

            // Sezione Ordini
            + "<div style='background:#ffffff; border:1px solid #dcdde1; border-radius:10px; padding:0;'>"
            + "  <div style='background:#2c3e50; color:white; padding:10px 15px; border-radius:10px 10px 0 0;'>"
            + "    <b style='font-size:14px;'>ELENCO ORDINI ATTIVI</b>"
            + "  </div>"
            + "  <table width='100%' cellspacing='0' cellpadding='0'>"
            + "    <tr bgcolor='#f8f9fa'>"
            + "      <th align='left' style='padding:10px; color:#7f8c8d; border-bottom:2px solid #eee;'>ID</th>"
            + "      <th align='left' style='padding:10px; color:#7f8c8d; border-bottom:2px solid #eee;'>DETTAGLI PIZZA</th>"
            + "      <th align='left' style='padding:10px; color:#7f8c8d; border-bottom:2px solid #eee;'>AZIONI STATO</th>"
            + "    </tr>"
            +      ordiniHTML.toString()
            + "  </table>"
            + "</div><br>"

            // Sezione Log
            + "<div style='background:#2d3436; color:#dfe6e9; padding:15px; border-radius:8px; font-family:Monospaced; font-size:11px;'>"
            + "  <b style='color:#fab1a0;'>CONSOLE EVENTI:</b><br><br>"
            +    (logText.isEmpty() ? "In attesa di operazioni..." : logText)
            + "</div>"
            
            + "</body></html>";

        pane.setText(html);
    }
}