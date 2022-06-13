package com.projecte.tpv;

import com.projecte.tpv.model.Producte;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;

/**
 * Estableix la interfaç dels items dins el ListView a les pantelles de Venda i Pagar
 * @author Aida Carbonell Niubo
 */
public class ProducteListViewCell extends ListCell<Producte> {
    @FXML
    private Label cant;
    @FXML
    private Label nom;
    @FXML
    private Label preu;
    @FXML
    private AnchorPane hBox;
    private FXMLLoader mLoader;
    @Override
    protected void updateItem(Producte producte, boolean b) {
        super.updateItem(producte, b);
        if (b || producte == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (mLoader == null) {
                mLoader = new FXMLLoader(getClass().getResource("ticketRow.fxml"));
                mLoader.setController(this);
                try {
                    mLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            cant.setText(String.valueOf(producte.getCant()));
            nom.setText(producte.getNom());
            preu.setText(String.valueOf(producte.getPreu_venda()));
            setText(null);
            setGraphic(hBox);
        }
    }
}