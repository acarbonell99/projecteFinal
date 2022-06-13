package com.projecte.tpv;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import static com.projecte.tpv.Generals.*;

/**
 * Controla la finestra d'Inici, serveix per navegar a Prevenda, Venda, i Postvenda
 * @author Aida Carbonell Niubo
 */
public class IniciController implements Initializable {
    public static DatabaseSql db = new DatabaseSql();
    public static DatabaseMongo dbMongo = new DatabaseMongo();
    public Button btnVenda;
    public Button btnPostvenda;

    /**
     * Crida al metode initDb() i controla activacio dels botons Venda i Postvenda<br/>
     * Veure: {@link #initDb()}
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initDb();
        btnVenda.setDisable(!inicialitzar);
        btnPostvenda.setDisable(!finalitzar);
    }

    /**
     * Inicialitza les bases de dades<br/>
     * Veure: {@link DatabaseSql#connect()}<br/>
     * Veure: {@link DatabaseMongo#connect()}
     */
    private static void initDb(){
        try {
            db.connect();
            dbMongo.connect();
        } catch (CannotConnectException e) {
            e.printStackTrace();
        }
    }

    /**
     * Nevega a la pantalla de Prevenda<br/>
     * Veure: {@link Generals#openWindow(String, String, boolean, Scene)}
     * @param event
     * @throws SQLException
     */
    public void toPrevenda(ActionEvent event) throws SQLException {
        openWindow("prevenda.fxml", "Prevenda", true, btnVenda.getScene());
    }

    /**
     * Nevega a la pantalla de venda<br/>
     * Veure: {@link Generals#openWindow(String, String, boolean, Scene)}<br/>
     * Veure: {@link Generals#closeWindow(Button)}
     * @param event
     */
    public void toVenda(ActionEvent event) {
        openWindow("venda.fxml", "Venda");
        closeWindow(btnVenda);
    }

    /**
     * Nevega a la pantalla de Postvenda<br/>
     * Veure: {@link Generals#openWindow(String, String, boolean, Scene)}
     * @param event
     */
    public void toPsotvenda(ActionEvent event) {
        openWindow("postvenda.fxml", "Postvenda", true, btnVenda.getScene());
    }
}