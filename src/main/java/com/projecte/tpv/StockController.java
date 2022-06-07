package com.projecte.tpv;

import com.projecte.tpv.model.Categoria;
import com.projecte.tpv.model.Producte;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import static com.projecte.tpv.DatabaseMongo.prdStock;
import static com.projecte.tpv.DatabaseSql.queryProdXCat1;
import static com.projecte.tpv.DatabaseSql.updateCant;
import static com.projecte.tpv.Generals.openWindow;
import static com.projecte.tpv.Generals.selCat;
import static com.projecte.tpv.HelloApplication.st;

public class StockController implements Initializable {
    public static ObservableList<Producte> producteObservableList = FXCollections.observableArrayList();
    public TabPane tabPane;
    public static boolean exist = false;
    public static int row = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            tabs();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void back(ActionEvent event) {
        openWindow("postvenda.fxml", "Postvenda", true, tabPane.getScene());
    }
    public void sendStcok(ActionEvent event) {
        producteObservableList.forEach(p -> {
            try {
                if (p.getStockReal().equals(null) || p.getStockReal().equals("")) updateCant(p.getId_prod(), p.getCant());
                else updateCant(p.getId_prod(), Integer.parseInt(p.getStockReal()));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public TableView<Producte> addTable(Tab newTab){
        TableView<Producte> table = new TableView();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        producteObservableList = FXCollections.observableArrayList();
        try {
            ResultSet rs2 = queryProdXCat1(newTab.getId());
            while (rs2.next())  producteObservableList.add(new Producte(rs2.getInt(1), rs2.getString(2), rs2.getString(3), rs2.getInt(4), rs2.getInt(5), rs2.getString(6)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        prdStock.forEach(p -> producteObservableList.forEach(p2 -> {
            if (p.equals(p2)) p2.setCant(p2.getCant()-p.getCant());
            if (p2.getCant() < p2.getStockMin()) p2.setEstat(true);
        }));


        TableColumn colId = new TableColumn("Id");
        colId.setCellValueFactory(
                new PropertyValueFactory("id_prod"));
        TableColumn colNom = new TableColumn("Nom");
        colNom.setCellValueFactory(
                new PropertyValueFactory("nom"));
        TableColumn colDesc = new TableColumn("Descripcio");
        colDesc.setCellValueFactory(
                new PropertyValueFactory("descripcio"));
        TableColumn colCat = new TableColumn("Categoria");
        colCat.setCellValueFactory(
                new PropertyValueFactory("id_cat"));

        TableColumn colStockEsperat = new TableColumn("Stock esperat");
        colStockEsperat.setCellValueFactory(
                new PropertyValueFactory("cant"));
        TableColumn colStockMinim = new TableColumn("Stock minim");
        colStockMinim.setCellValueFactory(
                new PropertyValueFactory("stockMin"));

        /*TableColumn colPreu = new TableColumn("Preu");
        colPreu.setCellValueFactory(
                new PropertyValueFactory("preu_venda"));*/
        /*TableColumn<Producte, Boolean> colConsumible = new TableColumn("Consumible");
        colConsumible.setCellValueFactory(c -> new SimpleBooleanProperty(c.getValue().isConsumible()));
        colConsumible.setCellFactory( tc -> new CheckBoxTableCell<>());
        TableColumn<Producte, Boolean> colVendible = new TableColumn("Vendible");
        colVendible.setCellValueFactory(c -> new SimpleBooleanProperty(c.getValue().isVendible()));
        colVendible.setCellFactory( tc -> new CheckBoxTableCell<>());*/
        TableColumn colStockReal = new TableColumn("Stock real");
        colStockReal.setCellValueFactory(
                new PropertyValueFactory("stockReal"));
        colStockReal.setCellFactory((TextFieldTableCell.forTableColumn()));
        colStockReal.setEditable(true);
        TableColumn colEstat = new TableColumn("Estat");
        colEstat.setCellValueFactory(
                new PropertyValueFactory("estat"));
        TableColumn colComanda = new TableColumn("Comanda");
        colComanda.setCellValueFactory(
                new PropertyValueFactory("comanda"));
        colStockReal.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Producte, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Producte, String> t) {
                Producte pr = ((Producte) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                pr.setStockReal(t.getNewValue());
                if (Integer.parseInt(t.getNewValue()) < pr.getStockMin()) {
                    pr.setEstat(true);
                    table.getColumns().remove(colEstat);
                    refresh(table);
                    table.getColumns().add(7, colEstat);
                }
            }
        });



        //colComanda.setCellFactory(CheckBoxTableCell.forTableColumn(colComanda));
        refresh(table);
        //table.setItems(producteObservableList);
        table.getColumns().addAll(colId, colNom, colDesc, colCat, colStockEsperat, colStockReal, colStockMinim, colEstat, colComanda);
        table.setEditable(true);

        table.getSelectionModel().selectedItemProperty().addListener(e -> {
            prdComanda = table.getSelectionModel().getSelectedItem();
            if (prdComanda.isEstat()) openWindow("demandaStock.fxml", "Comanda");
        });

        /*table.getSelectionModel().selectedItemProperty().addListener(e ->{
            row = table.getSelectionModel().getSelectedItem().getId_prod();
            openWindow("fitxaTecnica.fxml", "Fitxa Tecnica");
        });*/
        return table;
    }
    static Producte prdComanda;
    public void refresh(TableView<Producte> tab){
        tab.setItems(producteObservableList);
    }

    TableView<Producte> table;
    public void tabs() throws SQLException {
        List<Categoria> cat = selCat();
        for (int i = 0; i < cat.size(); i++){
            Tab t = new Tab(cat.get(i).nom);
            t.setId(cat.get(i).id_cat);
            if (i == 0){
                table = addTable(t);
                t.setContent(table);
            }
            tabPane.getTabs().add(t);
        }
        tabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            table = addTable(newTab);
            newTab.setContent(table);
        });
    }
}
