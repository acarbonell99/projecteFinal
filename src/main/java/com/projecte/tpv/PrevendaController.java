package com.projecte.tpv;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.time.LocalDateTime;

import static com.projecte.tpv.Generals.*;
import static com.projecte.tpv.HelloApplication.st;

public class PrevendaController {
    public Button btnBack;

    /**
     * Navegar a la finestra de Productes
     * @param event
     */
    public void toProducte(ActionEvent event) {
        try {
            Parent root2 = new FXMLLoader(IniciController.class.getResource("productes.fxml")).load();
            btnBack.getScene().setRoot(root2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Navegar a la finestra de Productes
     * @param event
     */
    public void toPersonal(ActionEvent event) {
        openWindow("prePersonal.fxml", "Personal", true, btnBack.getScene());
    }

    /**
     * Tornar a la finestra anterior
     * @param event
     */
    public void backInici(ActionEvent event) {
        openWindow("inici.fxml", "Inici", true, btnBack.getScene());
    }
    /*public void back(ActionEvent event) {
        openWindow("prevenda.fxml", "Prevenda", true, btnBack.getScene());
    }*/

    /**
     * Inicialitza la venda i activa al el boto de Venda
     * Retorna a ala finestra d'Inici
     * @param event
     */
    public void inizialitze(ActionEvent event) {
        dateInicial = dtf.format(LocalDateTime.now());
        inicialitzar = true;
        finalitzar = false;
        openWindow("inici.fxml", "Inici", true, btnBack.getScene());
        numTicket = 1;
    }
}
