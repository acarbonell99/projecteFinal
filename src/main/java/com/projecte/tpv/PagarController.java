package com.projecte.tpv;

import com.projecte.tpv.model.Producte;
import javafx.application.Platform;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static com.projecte.tpv.DatabaseMongo.*;
import static com.projecte.tpv.Generals.*;
import static com.projecte.tpv.VendaController.addTicket;
import static com.projecte.tpv.VendaController.prodObsList;

public class PagarController implements Initializable {
    public ListView<Producte> listPagar;
    public ListView<Producte> llistaSeparada = new ListView<>();
    public ObservableList<Producte> obsListSeparada = FXCollections.observableArrayList();
    public static ObservableList<Producte> obsListFirts = FXCollections.observableArrayList();
    @FXML
    public VBox llilsta;
    @FXML
    public Button btnPagar;
    private double importTotal = 0;
    public TextField total;
    public TextField entregat;
    public TextField canvi;
    String desc = "";
    static LocalDateTime data;
    public boolean div = false;
    //public static DatabaseMongo dbMongo = new DatabaseMongo();

    public void calcularTotal(ObservableList<Producte> obs){
        importTotal = 0;
        if (obs.isEmpty()) obs = obsListFirts;
        for (Producte p : obs) importTotal += p.getCant()*p.getPreu_venda();

        total.setText(String.valueOf(df.format(importTotal)));
        /*entregat.setText(String.valueOf(0));
        SimpleDoubleProperty a = new SimpleDoubleProperty(Integer.parseInt(entregat.getText()));
        NumberBinding canvi1 = a.subtract(importTotal);
        //System.out.println(canvi1.getValue());
        //canvi.setText(String.valueOf(canvi1.getValue()));
        //canvi.textProperty().bind(canvi1.asString());
        //canvi.setText(String.valueOf(canvi1.getValue()));
        entregat.textProperty().bindBidirectional(canvi.textProperty().);

        */
        /*entregat.setText(String.valueOf(2));
        int a = Integer.parseInt(entregat.getText());
        canvi.setText(String.valueOf(a+10));*/

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        obsListFirts.clear();
        obsListFirts.addAll(prodObsList);
        listPagar.setItems(obsListFirts);
        listPagar.setCellFactory(producteListView -> new ProducteListViewCell());
        calcularTotal(obsListFirts);
    }

    public void dividir(ActionEvent event) {
        listPagar.setOnMouseClicked(f -> selItem(listPagar, llistaSeparada, obsListFirts, obsListSeparada));
        llistaSeparada.setOnMouseClicked(f -> selItem(llistaSeparada, listPagar, obsListSeparada, obsListFirts));
        llilsta.getChildren().add(llistaSeparada);
        div = true;
    }

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
    }

    public void pagarEfectiu(ActionEvent event) {
        canvi.setText(df.format(Double.parseDouble(entregat.getText())-importTotal));
        desc = "Efectiu =\t" + df.format(Double.parseDouble(entregat.getText())) + "€\n" +
                "Canvi =\t" + canvi.getText() + "€\n";

    }

    public void pagarTargeta(ActionEvent event) {
        desc = "Targeta =\t" + df.format(importTotal) + "€\n";
    }

    public void pagar(ActionEvent event) {
        generarTicket();
        prodObsList.clear();
        if (obsListFirts.isEmpty()) div = false;
        if (!div) closeWindow(btnPagar);
        obsListSeparada.clear();

    }



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
        /*try {
            dbMongo.connect();
            crearTicket();
        } catch (CannotConnectException e) {
            e.printStackTrace();
        }*/
        crearTicket();
        numTicket++;
        idticket++;

        //crearTicket(obsListFirts, importTotal);
    }

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


}
