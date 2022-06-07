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

    public void toProducte(ActionEvent event) {
        try {
            Parent root2 = new FXMLLoader(HelloController.class.getResource("productes.fxml")).load();
            btnBack.getScene().setRoot(root2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toPersonal(ActionEvent event) {
        openWindow("prePersonal.fxml", "Personal", true, btnBack.getScene());
    }
    public void backInici(ActionEvent event) {
        openWindow("inici.fxml", "Inici", true, btnBack.getScene());
    }
    public void back(ActionEvent event) {
        openWindow("prevenda.fxml", "Prevenda", true, btnBack.getScene());
    }

    public void inizialitze(ActionEvent event) {
        dateInicial = dtf.format(LocalDateTime.now());
        inicialitzar = true;
        finalitzar = false;
        openWindow("inici.fxml", "Inici", true, btnBack.getScene());
        numTicket = 1;
    }
}
