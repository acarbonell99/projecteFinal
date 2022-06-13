package com.projecte.tpv;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import static com.projecte.tpv.Generals.openWindow;

/**
 * Es una finestra de navegació per a les accions de Prostvenda
 * Per defecte, en iniciar, esta inectiu i s'activa en quan es fa l'operacio Z des de Venda
 * @author Aida Carbonell Niubo
 */
public class PostvendaController {
    public Button btnBack;

    /**
     * Navegar a la finestra de Stock<br/>
     * Veure: {@link Generals#openWindow(String, String, boolean, Scene)}
     * @param event
     */
    public void toStock(ActionEvent event) {
        openWindow("stock.fxml", "Stock", true, btnBack.getScene());
    }

    /**
     * Navegar a la finestra de Neteja<br/>
     * Veure: {@link Generals#openWindow(String, String, boolean, Scene)}
     * @param event
     */
    public void toNeteja(ActionEvent event) {
        openWindow("neteja.fxml", "Neteja", true, btnBack.getScene());
    }

    /**
     * Navegar a la finestra de Recompte<br/>
     * Veure: {@link Generals#openWindow(String, String, boolean, Scene)}
     * @param event
     */
    public void toRecompte(ActionEvent event) {
        openWindow("recompte.fxml", "Neteja", true, btnBack.getScene());
    }

    /**
     * Navegar a la finestra de Personal<br/>
     * Veure: {@link Generals#openWindow(String, String, boolean, Scene)}
     * @param event
     */
    public void toPersonal(ActionEvent event) {
    }

    /**
     * Tornar a la finestra anterior<br/>
     * Veure: {@link Generals#openWindow(String, String, boolean, Scene)}
     * @param event
     */
    public void backInici(ActionEvent event) {
        openWindow("inici.fxml", "Inici", true, btnBack.getScene());
    }
}