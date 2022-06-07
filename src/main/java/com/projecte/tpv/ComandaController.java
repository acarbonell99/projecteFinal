package com.projecte.tpv;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static com.projecte.tpv.DatabaseMongo.comandaProductes;
import static com.projecte.tpv.Generals.closeWindow;
import static com.projecte.tpv.StockController.prdComanda;

public class ComandaController implements Initializable {

    public TextField fieldCant;
    public Label labId;
    public Label labNom;
    public Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        labId.setText(String.valueOf(prdComanda.getId_prod()));
        labNom.setText(prdComanda.getNom());
    }

    public void confirmar(ActionEvent event) {
        comandaProductes(prdComanda.getId_prod(), Integer.parseInt(fieldCant.getText()));
        closeWindow(btnCancel);
    }

    public void caneclar(ActionEvent event) {
        closeWindow(btnCancel);
    }
}
