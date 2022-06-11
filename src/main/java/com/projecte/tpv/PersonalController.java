package com.projecte.tpv;

import com.projecte.tpv.model.Producte;
import com.projecte.tpv.model.Treballador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.projecte.tpv.DatabaseSql.gueryGeneric;
import static com.projecte.tpv.Generals.openWindow;
import static com.projecte.tpv.HelloApplication.st;

public class PersonalController implements Initializable {
    public static ObservableList<Treballador> trebOl = FXCollections.observableArrayList();
    public TableView<Treballador> table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            listTreballador();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TableColumn colCarrec = new TableColumn("Carrec");
        colCarrec.setCellValueFactory(new PropertyValueFactory("id_rol"));
        TableColumn colNom = new TableColumn("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory("nom"));
        TableColumn colDni = new TableColumn("Dni");
        colDni.setCellValueFactory(new PropertyValueFactory("dni"));
        table.setItems(trebOl);
        table.getColumns().addAll(colCarrec, colNom, colDni);
    }

    /**
     * Tornar a la finestra anterior
     * @param event
     */
    public void back(ActionEvent event) {
        openWindow("prevenda.fxml", "Prevenda", true, table.getScene());
    }

    /**
     * Emplena la taula de Treballadors
     * @throws SQLException
     * @deprecated no esta desenvolupat
     */
    public void listTreballador() throws SQLException {
        ResultSet rs = gueryGeneric("treballador");
        while (rs.next()){
            trebOl.add(new Treballador(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6), rs.getInt(7), rs.getString(8), rs.getString(9)));
        }
    }
}
