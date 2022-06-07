package com.projecte.tpv;

import com.projecte.tpv.model.Producte;
import eu.hansolo.tilesfx.colors.Bright;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.projecte.tpv.DatabaseSql.*;
import static com.projecte.tpv.Generals.*;
import static com.projecte.tpv.HelloApplication.st;

public class HelloController implements Initializable {
    public static DatabaseSql db = new DatabaseSql();
    public static DatabaseMongo dbMongo = new DatabaseMongo();
    public Button btnVenda;
    public Button btnPostvenda;

    public void toPrevenda(ActionEvent event) throws SQLException {
        openWindow("prevenda.fxml", "Prevenda", true, btnVenda.getScene());
        //dbMongo.prova();
    }

    public void toVenda(ActionEvent event) {
        openWindow("venda.fxml", "Venda");
        closeWindow(btnVenda);
    }

    public void toPsotvenda(ActionEvent event) {
        //dbMongo.stockGastat();
        openWindow("postvenda.fxml", "Postvenda", true, btnVenda.getScene());

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initDb();
        if (inicialitzar) btnVenda.setDisable(false);
        else btnVenda.setDisable(true);
        /*if (finalitzar) btnPostvenda.setDisable(false);
        else btnPostvenda.setDisable(true);*/
        btnPostvenda.setDisable(false);

        /*try {
            ResultSet rs2 = gueryGeneric("producte");
            while (rs2.next()){
                System.out.println(rs2.getInt(1) + rs2.getString(2) + rs2.getString(3) + rs2.getString(6) + rs2.getDouble(7) + rs2.getBoolean(11) + rs2.getBoolean(12));
            }
            //updateItem(10006, "c00001");
            //updateItem(10007, "c00001");
            //db.deleteProd(10008);
            //db.deleteProd(10007);
            //insertProd(new Producte(10003, "Cocacola 500ml", "Cocacola 500ml", 200, 100, "c00001", 3.90, 0.50, 10, "C:\\Users\\acarb\\Documents\\2WIAM\\projecteFinal\\tpv\\src\\main\\resources\\com\\projecte\\tpv\\img\\cocacola.png", true, true));
            //updateItem(30003, "C:\\Users\\acarb\\Documents\\2WIAM\\projecteFinal\\tpv\\src\\main\\resources\\com\\projecte\\tpv\\img\\haribo.jpg");
        } catch (SQLException e) {
            e.printStackTrace();
        }*/


    }

    private static void initDb(){
        try {
            db.connect();
            dbMongo.connect();
        } catch (CannotConnectException e) {
            e.printStackTrace();
        }
    }
}