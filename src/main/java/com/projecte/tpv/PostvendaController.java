package com.projecte.tpv;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import static com.projecte.tpv.Generals.openWindow;

public class PostvendaController {
    public Button btnBack;

    /**
     * Navegar a la finestra de Stock
     * @param event
     */
    public void toStock(ActionEvent event) {
        openWindow("stock.fxml", "Stock", true, btnBack.getScene());
    }

    /**
     * Navegar a la finestra de Neteja
     * @param event
     */
    public void toNeteja(ActionEvent event) {
        openWindow("neteja.fxml", "Neteja", true, btnBack.getScene());
    }

    /**
     * Navegar a la finestra de Recompte
     * @param event
     */
    public void toRecompte(ActionEvent event) {
        openWindow("recompte.fxml", "Neteja", true, btnBack.getScene());
    }

    /**
     * Navegar a la finestra de Personal
     * @param event
     */
    public void toPersonal(ActionEvent event) {
    }

    /**
     * Tornar a la finestra anterior
     * @param event
     */
    public void backInici(ActionEvent event) {
        openWindow("inici.fxml", "Inici", true, btnBack.getScene());
    }
}
