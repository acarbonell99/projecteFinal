package com.projecte.tpv;

import com.projecte.tpv.model.Neteja;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import static com.projecte.tpv.Generals.openWindow;

/**
 * Controla la finestra de Neteja<br/>
 * Conte un taula amb taques a realitzar junt amb checkbox per anar marcan les taques relitzades<br/>
 * Aquesta classe no te cap altre funcionalitat, no ha hestat desenvolupada
 * @author Aida Carbonell Niubo
 */
public class NetejaController implements Initializable {

    public static ObservableList<Neteja> trebOl = FXCollections.observableArrayList();
    public TableView table;
    public TableColumn colTasca;

    /**
     * Tornar a la finestra anterior<br/>
     * Veure: {@link Generals#openWindow(String, String, boolean, Scene)}
     * @param event
     */
    public void back(ActionEvent event) {
        openWindow("postvenda.fxml", "Postvenda", true, table.getScene());
    }

    /**
     * Ompla la taula amb les tasques de neteja
     */
    public void omplirNeteja(){
        List<Neteja> neteja = new ArrayList<>();
        neteja.add(new Neteja("Neteja de les neveres i congeladors"));
        neteja.add(new Neteja("Neteja planxa, accessoris i paret"));
        neteja.add(new Neteja("Neteja màquines utilitzades"));
        neteja.add(new Neteja("Ordenar magatzem"));
        neteja.add(new Neteja("Netejar barra i frontal"));
        neteja.add(new Neteja("Netejar estanteries"));
        neteja.add(new Neteja("Escombrar terra"));
        neteja.add(new Neteja("Fregar terra"));
        neteja.add(new Neteja("Treure escombraries"));
        neteja.add(new Neteja("Precinta productes d'un sol ús oberts"));
        trebOl.addAll(neteja);
    }

    /**
     * Veure: {@link #omplirNeteja()}
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        trebOl.clear();
        omplirNeteja();
        colTasca.setCellValueFactory(new PropertyValueFactory<>("tasca"));
        TableColumn<Neteja, Boolean> colFet = new TableColumn<>("Fet");
        colFet.setPrefWidth(400);
        colFet.setMaxWidth(500);
        colFet.setCellValueFactory( c -> {
            CheckBox checkBox = new CheckBox();
            checkBox.setDisable(false);
            checkBox.selectedProperty().setValue(c.getValue().isFet());
            checkBox.selectedProperty().addListener((ov, old_val, new_val) -> c.getValue().setFet(new_val));
            return new SimpleObjectProperty(checkBox);
        });
        colFet.setCellFactory( tc -> new CheckBoxTableCell<>());
        colFet.setEditable(true);
        table.setItems(trebOl);
        table.getColumns().add(colFet);
        table.setEditable(true);
    }
}