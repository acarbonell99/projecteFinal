package com.projecte.tpv;

import com.projecte.tpv.model.Categoria;
import com.projecte.tpv.model.Producte;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import static com.projecte.tpv.DatabaseSql.queryProdXCat1;
import static com.projecte.tpv.Generals.openWindow;
import static com.projecte.tpv.Generals.selCat;

/**
 * Controla la finestra Producte de Prevenda
 * Mostra en diferents taules separades tots els productes segons la categoria a al que pertanyen
 * Des d'aquesta finestra es poden afegir nous productes des del boto "Afegir" abaix a la dreta
 * Si es clica un producte, s'obre la Fita tecnica amb les seves dades, fent clic a "Edit" es pot editar el producte i guardar el scanvis
 * @author Aida Carbonell Niubo
 */
public class ProducteController implements Initializable {
    public static ObservableList<Producte> producteObservableList = FXCollections.observableArrayList();
    public TabPane tabPane;
    public static boolean exist = false;
    public static int row = 0;
    TableView<Producte> table;

    /**
     * Tornar a la finestra anterior<br/>
     * Veure: {@link com.projecte.tpv.Generals#openWindow(String, String, boolean, Scene)}
     * @param event
     */
    public void back(ActionEvent event) {
        openWindow("prevenda.fxml", "Prevenda", true, tabPane.getScene());
    }

    /**
     * Obre la fitxa tecnica en blanc per afegir un nou producte<br/>
     * row = 0 -> Nou producte<br/>
     * row != 0 -> veure un producte existent<br/>
     * Veure: {@link com.projecte.tpv.Generals#openWindow(String, String)}
     * @param event
     */
    public void addProduct(ActionEvent event) {
        row = 0;
        openWindow("fitxaTecnica.fxml", "Fitxa Tecnica");
    }

    /**
     * Crea una taula en cada tab amb els Productes de la categoria<br/>
     * Veure: {@link com.projecte.tpv.Generals#openWindow(String, String)}
     * @param newTab pestanya
     * @return taula de productes
     */
    public TableView<Producte> addTable(Tab newTab){
        TableView<Producte> table = new TableView();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        producteObservableList = FXCollections.observableArrayList();
        try {
            ResultSet rs2 = queryProdXCat1(newTab.getId());
            while (rs2.next())  producteObservableList.add(new Producte(rs2.getInt(1), rs2.getString(2), rs2.getString(3), rs2.getString(6), rs2.getDouble(7), rs2.getBoolean(11), rs2.getBoolean(12)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TableColumn colId = new TableColumn("Id");
        colId.setCellValueFactory(new PropertyValueFactory("id_prod"));
        TableColumn colNom = new TableColumn("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory("nom"));
        TableColumn colDesc = new TableColumn("Descripcio");
        colDesc.setCellValueFactory(new PropertyValueFactory("descripcio"));
        TableColumn colCat = new TableColumn("Categoria");
        colCat.setCellValueFactory(new PropertyValueFactory("id_cat"));
        TableColumn colPreu = new TableColumn("Preu");
        colPreu.setCellValueFactory(new PropertyValueFactory("preu_venda"));
        TableColumn<Producte, Boolean> colConsumible = new TableColumn("Consumible");
        colConsumible.setCellValueFactory(c -> new SimpleBooleanProperty(c.getValue().isConsumible()));
        colConsumible.setCellFactory( tc -> new CheckBoxTableCell<>());
        TableColumn<Producte, Boolean> colVendible = new TableColumn("Vendible");
        colVendible.setCellValueFactory(c -> new SimpleBooleanProperty(c.getValue().isVendible()));
        colVendible.setCellFactory( tc -> new CheckBoxTableCell<>());
        table.setItems(producteObservableList);
        table.getColumns().addAll(colId, colNom, colDesc, colPreu, colCat, colConsumible, colVendible);
        table.getSelectionModel().selectedItemProperty().addListener(e ->{
            row = table.getSelectionModel().getSelectedItem().getId_prod();
            openWindow("fitxaTecnica.fxml", "Fitxa Tecnica");
        });
        return table;
    }

    /**
     * Crea els Tabs del panel utilitzant les categories de la base de dades<br/>
     * Veure: {@link Generals#selCat()}
     * @throws SQLException
     */
    public void tabs() throws SQLException {
        List<Categoria> cat = selCat();
        for (int i = 0; i < cat.size(); i++){
            Tab t = new Tab(cat.get(i).nom);
            t.setId(cat.get(i).id_cat);
            if (i == 0) t.setContent(addTable(t));
            tabPane.getTabs().add(t);
        }
        tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> newTab.setContent(addTable(newTab)));
    }

    /**
     * Inicialitza les pestanyes<br/>
     * Veure: {@link #tabs()}
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            tabs();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}