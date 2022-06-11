package com.projecte.tpv;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.projecte.tpv.Generals.*;

public class IniciController implements Initializable {
    public static DatabaseSql db = new DatabaseSql();
    public static DatabaseMongo dbMongo = new DatabaseMongo();
    public Button btnVenda;
    public Button btnPostvenda;

    /**
     * Nevega a la pantalla de Prevenda
     * @param event
     * @throws SQLException
     */
    public void toPrevenda(ActionEvent event) throws SQLException {
        openWindow("prevenda.fxml", "Prevenda", true, btnVenda.getScene());
    }

    /**
     * Nevega a la pantalla de venda
     * @param event
     */
    public void toVenda(ActionEvent event) {
        openWindow("venda.fxml", "Venda");
        closeWindow(btnVenda);
    }

    /**
     * Nevega a la pantalla de Postvenda
     * @param event
     */
    public void toPsotvenda(ActionEvent event) {
        openWindow("postvenda.fxml", "Postvenda", true, btnVenda.getScene());
    }

    /**
     * Crida al metode initDb() i controla activació dels botons VEnda i Postvenda
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
     * Inicialitza les bases de dades
     */
    private static void initDb(){
        try {
            db.connect();
            dbMongo.connect();
        } catch (CannotConnectException e) {
            e.printStackTrace();
        }
    }
}