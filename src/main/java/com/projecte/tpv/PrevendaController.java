package com.projecte.tpv;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.time.LocalDateTime;
import static com.projecte.tpv.Generals.*;

/**
 * Es una finestra ed navegació per a les accions de Prevenda
 * @author Aida Carbonell Niubo
 */
public class PrevendaController {
    public Button btnBack;

    /**
     * Navegar a la finestra de Productes<br/>
     * Veure: {@link Generals#openWindow(String, String, boolean, Scene)}
     * @param event
     */
    public void toProducte(ActionEvent event) {
        openWindow("prePersonal.fxml", "Personal", true, btnBack.getScene());
    }

    /**
     * Navegar a la finestra de Productes<br/>
     * Veure: {@link Generals#openWindow(String, String, boolean, Scene)}
     * @param event
     */
    public void toPersonal(ActionEvent event) {
        openWindow("prePersonal.fxml", "Personal", true, btnBack.getScene());
    }

    /**
     * Tornar a la finestra anterior<br/>
     * Veure: {@link Generals#openWindow(String, String, boolean, Scene)}
     * @param event
     */
    public void backInici(ActionEvent event) {
        openWindow("inici.fxml", "Inici", true, btnBack.getScene());
    }

    /**
     * Inicialitza la venda i activa al el boto de Venda<br/>
     * Retorna a ala finestra d'Inici<br/>
     * Veure: {@link Generals#openWindow(String, String, boolean, Scene)}
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