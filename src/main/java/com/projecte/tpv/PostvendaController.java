package com.projecte.tpv;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import static com.projecte.tpv.Generals.openWindow;

public class PostvendaController {
    public Button btnBack;
    public void toStock(ActionEvent event) {
        openWindow("stock.fxml", "Stock", true, btnBack.getScene());
    }
    public void toNeteja(ActionEvent event) {
        openWindow("neteja.fxml", "Neteja", true, btnBack.getScene());
    }
    public void toRecompte(ActionEvent event) {
        openWindow("recompte.fxml", "Neteja", true, btnBack.getScene());
    }
    public void toPersonal(ActionEvent event) {
    }
    public void backInici(ActionEvent event) {
        openWindow("inici.fxml", "Inici", true, btnBack.getScene());
    }
    public void back(ActionEvent event) {
        openWindow("postvenda.fxml", "Postvenda", true, btnBack.getScene());
    }
    public void sendStcok(ActionEvent event) {
        openWindow("confirmarStock.fxml", "Confirmar Stock");
    }
}
