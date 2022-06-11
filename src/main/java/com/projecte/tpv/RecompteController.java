package com.projecte.tpv;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.bson.Document;

import java.net.URL;
import java.util.*;

import static com.projecte.tpv.DatabaseMongo.*;
import static com.projecte.tpv.Generals.*;
import static com.projecte.tpv.HelloApplication.st;

public class RecompteController implements Initializable {
    public TextField b5, b10, b20, b50, b100,b200, b500;
    public TextField m001, m002, m005, m01, m02, m05, m1, m2;
    public TextField bTotal, mTotal, bm, canvi, totalEfectiu, targeta, totalProd, totalZ;
    public TextField date, pdv, tpv, caixer, numZfield;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pdv.setText(pdvCode);
        tpv.setText(tpvCode);
        caixer.setText(treballador);
        date.setText(dateInicial);
        numZfield.setText(String.valueOf(numZ));
    }

    /**
     * Torna a al finestra anterior
     * @param event
     */
    public void back(ActionEvent event) {
        openWindow("postvenda.fxml", "Postvenda", true, b5.getScene());
    }

    /**
     * Genera un document amb el resum de contabilitat de la venda finalitzada
     * @param event
     */
    public void enviarRecompte(ActionEvent event) {
        totalEfectiu();
        Map<String, Integer> bitllets = new HashMap<>();
        bitllets.put("500", Integer.valueOf(b500.getText()));
        bitllets.put("200", Integer.valueOf(b200.getText()));
        bitllets.put("100", Integer.valueOf(b100.getText()));
        bitllets.put("50", Integer.valueOf(b50.getText()));
        bitllets.put("20", Integer.valueOf(b20.getText()));
        bitllets.put("10", Integer.valueOf(b10.getText()));
        bitllets.put("5", Integer.valueOf(b5.getText()));
        Map<String, Integer> monedes = new HashMap<>();
        monedes.put("2", Integer.valueOf(m2.getText()));
        monedes.put("1", Integer.valueOf(m2.getText()));
        monedes.put("0.50", Integer.valueOf(m2.getText()));
        monedes.put("0.20", Integer.valueOf(m2.getText()));
        monedes.put("0.10", Integer.valueOf(m2.getText()));
        monedes.put("0.05", Integer.valueOf(m2.getText()));
        monedes.put("0.02", Integer.valueOf(m2.getText()));
        monedes.put("0.01", Integer.valueOf(m2.getText()));
        collection = database.getCollection("caixa");
        Document doc = new Document();
        doc.append("id_pdv", pdv.getText());
        doc.append("id_tpv", tpv.getText());
        doc.append("data", date.getText());
        doc.append("caixer", caixer.getText());
        doc.append("numZ", numZfield.getText());
        doc.append("bitllets", bitllets);
        doc.append("monedes", monedes);
        doc.append("total bitllets", bTotal.getText());
        doc.append("total monedes", mTotal.getText());
        doc.append("calaix", bm.getText());
        doc.append("canvi", canvi.getText());
        doc.append("total efectiu", totalEfectiu.getText());
        doc.append("targeta", targeta.getText());
        doc.append("total produccio", totalProd.getText());
        doc.append("total Z", totalZ.getText());
        doc.append("diferencia", Double.parseDouble(totalZ.getText())-Double.parseDouble(totalProd.getText()));
        collection.insertOne(doc);
    }

    /**
     * Fa els calculs de diners introduits
     */
    public void totalEfectiu(){
        bTotal.setText(String.valueOf(Integer.parseInt(b500.getText())*500 + Integer.parseInt(b200.getText())*200 + Integer.parseInt(b100.getText())*100 + Integer.parseInt(b50.getText())*50 + Integer.parseInt(b20.getText())*20 + Integer.parseInt(b10.getText())*10 + Integer.parseInt(b5.getText())*5));
        mTotal.setText(String.valueOf(Double.parseDouble(m2.getText())*2 + Double.parseDouble(m1.getText())*1 + Double.parseDouble(m05.getText())*0.5 + Double.parseDouble(m02.getText())*0.2 + Double.parseDouble(m01.getText())*0.1 + Double.parseDouble(m005.getText())*0.05 + Double.parseDouble(m002.getText())*0.02 + Double.parseDouble(m001.getText())*0.01));
        bm.setText(String.valueOf(Double.parseDouble(bTotal.getText()) + Double.parseDouble(mTotal.getText())));
        canvi.setText(String.valueOf(261));
        totalEfectiu.setText(String.valueOf(Double.parseDouble(bm.getText()) - Double.parseDouble(canvi.getText())));
        totalProd.setText(String.valueOf(Double.parseDouble(totalEfectiu.getText()) + Double.parseDouble(targeta.getText())));
    }
}
