package com.projecte.tpv;

import com.projecte.tpv.model.Producte;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.bson.Document;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import static com.projecte.tpv.DatabaseMongo.*;
import static com.projecte.tpv.Generals.*;
import static com.projecte.tpv.Keyboard.eq;
import static com.projecte.tpv.VendaController.addTicket;
import static com.projecte.tpv.VendaController.prodObsList;

/**
 * Controla la finestra Pagar de Venda
 * @author Aida Carbonell Niubo
 */
public class PagarController implements Initializable {
    public ListView<Producte> listPagar;
    public ListView<Producte> llistaSeparada = new ListView<>();
    public ObservableList<Producte> obsListSeparada = FXCollections.observableArrayList();
    public static ObservableList<Producte> obsListFirts = FXCollections.observableArrayList();
    @FXML
    public VBox llilsta;
    @FXML
    public Button btnPagar;
    public Button b1;
    public Button b2;
    public Button b0;
    public Button b3;
    public Button b4;
    public Button b7;
    public Button bComa;
    public Button b6;
    public Button b8;
    public Button b5;
    public Button b9;
    public Button bAC;
    public Button bC;
    public Button bSumSub;
    private double importTotal = 0;
    public TextField total;
    public TextField entregat;
    public TextField canvi;
    String desc = "";
    static LocalDateTime data;
    public boolean div = false;

    /**
     * Fa la suma de cada Producte
     * @param obs llista de Productes del tiquet
     */
    public void calcularTotal(ObservableList<Producte> obs){
        importTotal = 0;
        if (obs.isEmpty()) obs = obsListFirts;
        for (Producte p : obs) importTotal += p.getCant()*p.getPreu_venda();
        total.setText(String.valueOf(df.format(importTotal)));
    }

    /**
     * Reinicia l'import entregat i el canvi a retornar
     */
    public void clear(){
        entregat.clear();
        canvi.clear();
    }

    /**
     * Borrar import introduit<br/>
     * Veure: {@link #clear()}
     * @param event
     */
    public void borrarUltim(ActionEvent event) {
        clear();
    }

    /**
     * Veure: {@link #calcularTotal(ObservableList)}
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        obsListFirts.clear();
        obsListFirts.addAll(prodObsList);
        listPagar.setItems(obsListFirts);
        listPagar.setCellFactory(producteListView -> new ProducteListViewCell());
        calcularTotal(obsListFirts);
    }

    /**
     * Dividir compte per ralitzar diferents pagaments<br/>
     * Veure: {@link #selItem(ListView, ListView, ObservableList, ObservableList)}
     * @param event
     */
    public void dividir(ActionEvent event) {
        listPagar.setOnMouseClicked(f -> selItem(listPagar, llistaSeparada, obsListFirts, obsListSeparada));
        llistaSeparada.setOnMouseClicked(f -> selItem(llistaSeparada, listPagar, obsListSeparada, obsListFirts));
        llilsta.getChildren().add(llistaSeparada);
        div = true;
    }

    /**
     * Seleccionar un Producte per canviar-lo de llista.<br/>
     * Funciona en ambdues direccions, un clic i es canvia el Producet de llista, nomes funciona de manera unitaria<br/>
     * Veure: {@link #calcularTotal(ObservableList)}
     * Veure: {@link VendaController#addTicket(int, String, double, ObservableList)}
     * @param l1 listView inicial
     * @param l2 listView final
     * @param o1 llista inicial
     * @param o2 llsita final
     */
    public void selItem(ListView<Producte> l1, ListView<Producte> l2, ObservableList<Producte> o1, ObservableList<Producte> o2){
        int index = l1.getSelectionModel().getSelectedIndex();
        if (index >= 0){
            Producte p = l1.getSelectionModel().getSelectedItem();
            addTicket(p.getId_prod(), p.getNom(), p.getPreu_venda(), o2);
            p.setCant(p.getCant()-1);
            l2.setItems(o2);
            l2.setCellFactory(producteListView -> new ProducteListViewCell());
            l1.getSelectionModel().clearSelection(index);
            if (p.getCant() == 0) o1.remove(index);
            calcularTotal(obsListSeparada);
        }
        canvi.setText(df.format(Double.parseDouble(entregat.getText())-importTotal));
    }

    /**
     * Selecciona el metode de pagament en efectiu<br/>
     * Queda reflectit en el tiquet<br/>
     * @param event
     */
    public void pagarEfectiu(ActionEvent event) {
        canvi.setText(df.format(Double.parseDouble(entregat.getText())-importTotal));
        desc = "Efectiu =\t" + df.format(Double.parseDouble(entregat.getText())) + "€\n" + "Canvi =\t" + canvi.getText() + "€\n";
    }

    /**
     * Selecciona el metode de pagament en targeta<br/>
     * Queda reflectit en el tiqquet<br/>
     * @param event
     */
    public void pagarTargeta(ActionEvent event) {
        desc = "Targeta =\t" + df.format(importTotal) + "€\n";
    }

    /**
     * Finalitza la venda<br/>
     * Reinicia la observableList de Venda, queda per inicaiar una nova venda<br/>
     * Veure: {@link #generarTicket()}
     * Veure: {@link #clear()}
     * Veure: {@link Generals#closeWindow(Button)}
     * @param event
     */
    public void pagar(ActionEvent event) {
        generarTicket();
        if (obsListFirts.isEmpty()) {
            div = false;
            prodObsList.clear();
        }
        if (!div) {
            prodObsList.clear();
            closeWindow(btnPagar);
        }
        obsListSeparada.clear();
        clear();
        eq = "1";
    }

    /**
     * Imprimeix el tiquet per terminal (que seria el tique que s'imprimeix)<br/>
     * Veure: {@link #crearTicket()}
     * Veure: {@link DatabaseMongo#nextIdTicket2()}
     */
    public void generarTicket(){
        nextIdTicket2();
        data = LocalDateTime.now();
        System.out.printf("\n|%-12s%-5s%-10s%-23s|\n", "Num.Ticket:", numTicket, "Data:", dtf.format(data));
        System.out.println("=".repeat(52));
        System.out.printf("|%-20s%-10s%-10s%-10s|\n", "Desc", "Cant", "Preu", "Total");
        System.out.println("=".repeat(52));
        if (!div) obsListFirts.forEach(t -> System.out.printf("|%-20s%-10s%-10s%-10s|\n", t.getNom(), t.getCant(), df.format(t.getPreu_venda()), df.format(t.getCant()*t.getPreu_venda())));
        else obsListSeparada.forEach(t -> System.out.printf("|%-20s%-10s%-10s%-10s|\n", t.getNom(), t.getCant(), df.format(t.getPreu_venda()), df.format(t.getCant()*t.getPreu_venda())));
        System.out.println("\n" + desc + "\n");
        System.out.println("=".repeat(52));
        System.out.printf("%-40s%-10s\n\n", "Total: ", df.format(importTotal));
        crearTicket();
        numTicket++;
        idticket++;
    }

    /**
     * Enregistra el tiquet a la base de dades
     */
    public void crearTicket(){
        List<Document> pr = new ArrayList<>();
        for(Producte p: obsListFirts){
            pr.add(new Document().append("id_prod", p.getId_prod()).append("nom", p.getNom()).append("cant", p.getCant()).append("preu", p.getPreu_venda()));
        }
        collection = database.getCollection("ticket");
        Document doc = new Document();
        doc.append("id_ticket", idticket);
        doc.append("num_ticket", numTicket);
        doc.append("productes", pr);
        doc.append("IVA", df.format(importTotal/10));
        doc.append("total-iva", df.format(importTotal-(importTotal/10)));
        doc.append("total", df.format(importTotal));
        doc.append("data", dtf.format(data));
        doc.append("treballdor", treballador);
        collection.insertOne(doc);
    }

    public void btn1(ActionEvent event) {
        entregat.setText(entregat.getText() + b1.getText());
        canvi.setText(df.format(Double.parseDouble(entregat.getText())-importTotal));
    }
    public void btn4(ActionEvent event) {
        entregat.setText(entregat.getText() + b4.getText());
        canvi.setText(df.format(Double.parseDouble(entregat.getText())-importTotal));
    }
    public void btn3(ActionEvent event) {
        entregat.setText(entregat.getText() + b3.getText());
        canvi.setText(df.format(Double.parseDouble(entregat.getText())-importTotal));
    }
    public void btn2(ActionEvent event) {
        entregat.setText(entregat.getText() + b2.getText());
        canvi.setText(df.format(Double.parseDouble(entregat.getText())-importTotal));
    }
    public void btn6(ActionEvent event) {
        entregat.setText(entregat.getText() + b6.getText());
        canvi.setText(df.format(Double.parseDouble(entregat.getText())-importTotal));
    }
    public void btn5(ActionEvent event) {
        entregat.setText(entregat.getText() + b5.getText());
        canvi.setText(df.format(Double.parseDouble(entregat.getText())-importTotal));
    }
    public void btn9(ActionEvent event) {
        entregat.setText(entregat.getText() + b9.getText());
        canvi.setText(df.format(Double.parseDouble(entregat.getText())-importTotal));
    }
    public void btn8(ActionEvent event) {
        entregat.setText(entregat.getText() + b8.getText());
        canvi.setText(df.format(Double.parseDouble(entregat.getText())-importTotal));
    }
    public void btn7(ActionEvent event) {
        entregat.setText(entregat.getText() + b7.getText());
        canvi.setText(df.format(Double.parseDouble(entregat.getText())-importTotal));
    }
    public void btnComa(ActionEvent event) {
        entregat.setText(entregat.getText() + ".");
        canvi.setText(df.format(Double.parseDouble(entregat.getText())-importTotal));
    }
    public void btn0(ActionEvent event) {
        entregat.setText(entregat.getText() + b0.getText());
        canvi.setText(df.format(Double.parseDouble(entregat.getText())-importTotal));
    }

    /**
     * Tanca la finestra de Pagar i retorna a Vanda per seguir afegint productes al tiquet<br/>
     * Veure: {@link Generals#closeWindow(Button)}
     * @param event
     */
    public void addProduct(ActionEvent event) {
        prodObsList.clear();
        prodObsList.setAll(obsListFirts);
        closeWindow(btnPagar);
    }
}