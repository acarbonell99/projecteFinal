package com.projecte.tpv;

import com.projecte.tpv.model.Producte;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import static com.projecte.tpv.DatabaseSql.*;
import static com.projecte.tpv.Generals.closeWindow;
import static com.projecte.tpv.ProducteController.row;
import static com.projecte.tpv.model.Producte.nextId;

/**
 * Controla la finestra Fitxa tecnica de Productes
 * Depenent del valor de row la fitxa s'obre en blanc per realitzar insercions, o obra la informacio del producte sel·leccionat
 * @author Aida Carbonell NIubo
 */
public class FitxaController implements Initializable {
    @FXML
    public Button btnCancel;
    public Button btnSave;
    public ImageView image;
    public Button btnAddPhoto;
    public Button btnDelPhoto;
    String cat = "";
    String imagePath = "img";
    public ChoiceBox selCat;
    public TextField fieldId;
    public TextField fieldNom;
    public TextField fieldDesc;
    public TextField fieldCost;
    public TextField fieldPvp;
    public CheckBox checkVendible;
    public CheckBox checkCons;
    public TextField fieldStock;
    public TextField fieldCant;

    /**
     * Crea una List amb el nom de totes les categories<br/>
     * Omple el ChoiseBox per seleccionar una categoria<br/>
     * Veure: {@link DatabaseSql#gueryGeneric(String)}
     * @throws SQLException
     */
    public void choiseCat() throws SQLException {
        List<String> cat = new ArrayList<>();
        ResultSet resultSet = gueryGeneric("categoria");
        while (resultSet.next()) {
            cat.add(resultSet.getString(2));
        }
        selCat.setItems(FXCollections.observableList(cat));

    }

    /**
     * Si row==0 la fitxa tecnica estara en blanc, en cas contrari, crida al metode ompirFitxa(Producte) per omplir la fitxa amb les dades del producte sel·leccionat<br/>
     * Veure: {@link #ompirFitxa(Producte)}<br/>
     * Veure: {@link #choiseCat()}<br/>
     * Veure: {@link Producte#nextId(String)}<br/>
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (row != 0) {
            try {
                ompirFitxa(selProd(row));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            choiseCat();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        selCat.getSelectionModel().selectedItemProperty().addListener(v -> {
            try {
                fieldId.setText(String.valueOf(nextId(String.valueOf(selCat.getSelectionModel().getSelectedItem()))));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Recupera les dades del Producte passat per parametre<br/>
     * Veure: {@link DatabaseSql#queryNomCat(String)}
     * @param p Producte sel·leccionat
     * @throws SQLException
     */
    public void ompirFitxa(Producte p) throws SQLException {
        btnSave.setText("Edit");
        active(true);
        fieldId.setText(String.valueOf(p.getId_prod()));
        fieldId.setDisable(true);
        fieldNom.setText(p.getNom());
        fieldDesc.setText(p.getDescripcio());
        fieldCost.setText(String.valueOf(p.getPreu_compra()));
        fieldPvp.setText(String.valueOf(p.getPreu_venda()));
        fieldCant.setText(String.valueOf(p.getCant()));
        fieldStock.setText(String.valueOf(p.getStockMin()));
        checkVendible.setSelected(p.isVendible());
        checkCons.setSelected(p.isConsumible());
        selCat.setValue(queryNomCat(p.getId_cat()));
        selCat.setDisable(true);
        if (!p.getImg().equals("img")) image.setImage(new Image(p.getImg()));
    }

    /**
     * Comprova si la fitxa es editable o no
     * @param b
     */
    public void active(boolean b) {
        fieldNom.setDisable(b);
        fieldDesc.setDisable(b);
        fieldCost.setDisable(b);
        fieldPvp.setDisable(b);
        fieldCant.setDisable(b);
        fieldStock.setDisable(b);
        checkVendible.setDisable(b);
        checkCons.setDisable(b);
        btnAddPhoto.setDisable(b);
        btnDelPhoto.setDisable(b);
        if (b) btnSave.setText("Edit");
        else btnSave.setText("Save");
    }

    /**
     * Si s'estacreant un Producte nou, s'inserteix a la base de dades<br/>
     * Si d'esta actualitzant un Producte, l'actualitza a la base de dades<br/>
     * Veure: {@link #active(boolean)}<br/>
     * Veure: {@link DatabaseSql#updateProd(Producte)}<br/>
     * Veure: {@link DatabaseSql#insertProd(Producte)}<br/>
     * Veure: {@link Generals#closeWindow(Button)}
     * @param event
     * @throws SQLException
     */
    public void save(ActionEvent event) throws SQLException {
        if (btnSave.getText().equals("Edit")) {
            active(false);
        } else if (btnSave.getText().equals("Save")) {
            updateProd(new Producte(Integer.parseInt(fieldId.getText()), fieldNom.getText(), fieldDesc.getText(), Integer.parseInt(fieldCant.getText()), Integer.parseInt(fieldStock.getText()), queryIdCat(String.valueOf(selCat.getSelectionModel().getSelectedItem())), Double.parseDouble(fieldPvp.getText()), Double.parseDouble(fieldCost.getText()), Integer.parseInt(String.valueOf(10)), imagePath, checkCons.isSelected(), checkVendible.isSelected()));
            closeWindow(btnCancel);
        } else {
            insertProd(new Producte(Integer.parseInt(fieldId.getText()), fieldNom.getText(), fieldDesc.getText(), Integer.parseInt(fieldCant.getText()), Integer.parseInt(fieldStock.getText()), queryIdCat(String.valueOf(selCat.getSelectionModel().getSelectedItem())), Double.parseDouble(fieldPvp.getText()), Double.parseDouble(fieldCost.getText()), Integer.parseInt(String.valueOf(10)), imagePath, checkCons.isSelected(), checkVendible.isSelected()));
            closeWindow(btnCancel);
        }
    }

    /**
     * Tanca la finastra de Fitxa tecnica sense gardad res<br/>
     * Veure: {@link Generals#closeWindow(Button)}
     * @param event
     */
    public void cancel(ActionEvent event) {
        row = 0;
        closeWindow(btnCancel);
    }

    /**
     * Afegeix o actualitza la foto de Producte<br/>
     * Obre el selector d'imatges que permet navegar i sel·leccionar-la
     * @param event
     * @throws IOException
     */
    public void addPhoto(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File("C:\\Users\\acarb\\Documents\\2WIAM\\projecteFinal\\tpv\\src\\main\\resources\\com\\projecte\\tpv\\img"));
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            imagePath = file.toURI().toString();
            image.setImage(new Image(imagePath));
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Please Select a File");
            alert.showAndWait();
        }
    }

    /**
     * Elimina la imatge del Producte
     * @param event
     */
    public void delPhoto(ActionEvent event) {
        image.setImage(null);
        imagePath = "img";
    }
}