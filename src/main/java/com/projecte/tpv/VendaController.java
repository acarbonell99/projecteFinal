package com.projecte.tpv;

import com.projecte.tpv.model.Categoria;
import com.projecte.tpv.model.Producte;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import static com.projecte.tpv.DatabaseMongo.operacionZ;
import static com.projecte.tpv.DatabaseMongo.stockGastat;
import static com.projecte.tpv.DatabaseSql.queryProdXCat;
import static com.projecte.tpv.Generals.*;
//import static com.projecte.tpv.Keyboard.*;

public class VendaController implements Initializable {
    public static ObservableList<Producte> prodObsList = FXCollections.observableArrayList();
    public static ObservableList<Producte> obsTemp = FXCollections.observableArrayList();
    public ListView<Producte> listView;
    public GridPane gridProduct;
    public Label itemCant;
    public Label itemPreu;
    public Label itemDesc;
    public Label itemTotal;
    @FXML
    public Button bMenys;
    @FXML
    public Button b1;
    @FXML
    public Button b2;
    @FXML
    public Button b0;
    @FXML
    public Button bSuma1;
    @FXML
    public Button bMult;
    @FXML
    public Button bBorrar;
    @FXML
    public Button b3;
    @FXML
    public Button b4;
    @FXML
    public Button b7;
    @FXML
    public Button bComa;
    @FXML
    public Button b6;
    @FXML
    public Button b8;
    @FXML
    public Button b5;
    @FXML
    public Button b9;
    static int num = 1;
    private boolean aparcat = false;

    @FXML
    public Button btnAparcar;
    @FXML
    public VBox menuLateral;
    //Keyboard k = new Keyboard(b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, bMenys, bBorrar, bMult, bComa, bSuma1);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

       // k.clic();
        //System.out.println(eq);
        //System.out.println(k.clic());
        menuLateral();
        try {
            gridCentral("null");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listView.setItems(prodObsList);
        listView.setCellFactory(producteListView -> new ProducteListViewCell());
        listView.getSelectionModel().selectedItemProperty().addListener(v -> {
            if (listView.getSelectionModel().getSelectedItem() == null){
                itemCant.setText(String.valueOf(0));
                itemPreu.setText(String.valueOf(0.00));
                itemDesc.setText("--");
                itemTotal.setText(String.valueOf(0.00));
            } else {
                itemCant.setText(String.valueOf(listView.getSelectionModel().getSelectedItem().getCant()));
                itemPreu.setText(String.valueOf(listView.getSelectionModel().getSelectedItem().getPreu_venda()));
                itemDesc.setText(listView.getSelectionModel().getSelectedItem().getNom());
                itemTotal.setText(df.format(listView.getSelectionModel().getSelectedItem().getCant() * listView.getSelectionModel().getSelectedItem().getPreu_venda()));
            }
        });
    }

    public void backInici(ActionEvent event) {
        openWindow("inici.fxml", "Inici", true, btnAparcar.getScene());
    }
    public void toPay(ActionEvent event) {
        openWindow("pagar.fxml", "Pagar");
    }
    public void nouClient(ActionEvent event) {
        prodObsList.clear();
    }
    public void parcar(ActionEvent event) {
        if (!aparcat){
            btnAparcar.setText("Deixar ticket");
            obsTemp.addAll(prodObsList);
            prodObsList.clear();
            aparcat = true;
        } else {
            btnAparcar.setText("Retenir Ticket");
            prodObsList.clear();
            prodObsList.addAll(obsTemp);
            obsTemp.clear();
            listView.setItems(prodObsList);
            aparcat = false;
        }
    }
    public void opZ(ActionEvent event) {
        dateFinal = dtf.format(LocalDateTime.now());
        stockGastat();
        finalitzar = true;
        inicialitzar = false;
        openWindow("inici.fxml", "Inici", true, btnAparcar.getScene());
        operacionZ();
    }
    public void menuLateral(){
        List<Categoria> cat = selCat();
        Button btAll = new Button("Tot");
        btAll.setId("0");
        btAll.setPrefSize(128, 100);
        btAll.getStyleClass().setAll("btn","btn-primary", "btn1");
        //btAll.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");
        btAll.setAlignment(Pos.CENTER);
        menuLateral.getChildren().add(btAll);
        menuLateral.setMargin(btAll, new Insets(4, 0, 4, -8));
        cat.forEach(c -> {
            Button bt = new Button(c.nom);
            bt.setId(c.id_cat);
            bt.setPrefSize(128, 100);
            bt.getStyleClass().setAll("btn","btn-info", "btn1");
            //bt.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");
            bt.setAlignment(Pos.CENTER);
            menuLateral.getChildren().add(bt);
            menuLateral.setMargin(bt, new Insets(4, 0, 0, -8));
            bt.setOnMouseClicked(v -> {
                try {
                    gridCentral(bt.getId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            btAll.setOnMouseClicked(v -> {
                try {
                    gridCentral("null");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
    }
    public void gridCentral(String s) throws SQLException {
        gridProduct.getChildren().clear();
        List<Producte> prd = queryProdXCat(s);
        int c = 0;
        int r = 0;
        for (Producte producte : prd) {
            VBox box = new VBox();
            box.setId("prodBox");
            box.getStyleClass().setAll("btn", "btn-default", "btn2", "prodcutsBox");
            box.setPrefWidth(100);
            //box.setPadding(new Insets(4));
            //box.setAlignment(Pos.CENTER);
            ImageView img = new ImageView();
            img.setId("prodImg");
            img = (producte.getImg().equals("img")) ? new ImageView(new Image(VendaController.class.getResourceAsStream("img/imgFail.png"))) : new ImageView(new Image(producte.getImg()));
            img.setFitWidth(90);
            img.setFitHeight(90);
            img.fitWidthProperty();
            Rectangle clip = new Rectangle(img.getFitWidth(), img.getFitHeight());
            clip.setArcWidth(30);
            clip.setArcHeight(30);
            img.setClip(clip);
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(Color.TRANSPARENT);
            img.setImage(img.snapshot(parameters, null));
            Label label = new Label(producte.getNom());
            label.getStyleClass().setAll("products");
            label.setPrefHeight(100);


            box.getChildren().addAll(img, label);
            gridProduct.add(box, c, r);
            if (c < 6) c++;
            else if (c == 6) {
                c = 0;
                r++;
            }
            producte.setCant(1);
            box.setOnMouseClicked(v -> {

                addTicket(producte.getId_prod(), producte.getNom(), producte.getPreu_venda(), prodObsList);
            });
        }
    }
    String eq = "";
    public int clic(){

        b0.setOnMouseClicked(v -> eq = eq + b0.getText());
        b1.setOnMouseClicked(v -> eq = eq + b1.getText());
        b2.setOnMouseClicked(v -> eq = eq + b2.getText());
        b3.setOnMouseClicked(v -> eq = eq + b3.getText());
        b4.setOnMouseClicked(v -> eq = eq + b4.getText());
        b5.setOnMouseClicked(v -> eq = eq + b5.getText());
        b6.setOnMouseClicked(v -> eq = eq + b6.getText());
        b7.setOnMouseClicked(v -> eq = eq + b7.getText());
        b8.setOnMouseClicked(v -> eq = eq + b8.getText());
        b9.setOnMouseClicked(v -> eq = eq + b9.getText());
        /*bMenys.setOnMouseClicked(v -> {
            System.out.println(eq);
            eq = "";
        });*/
        return Integer.parseInt(eq);
    }
    public static void addTicket(int id, String nom, double preu, ObservableList<Producte> obl){

        int a = 0;
        if (obl.isEmpty()) obl.add(new Producte(id, nom, 1, preu));
        else {
            for (int i = 0; i < obl.size(); i++) if (obl.get(i).getId_prod() == id) a = i;
            if (obl.get(a).getId_prod() == id) obl.set(a, new Producte(id, nom, obl.get(a).cant + 1, preu));
            else obl.add(new Producte(id, nom, num, preu));
        }
    }
    public void borrar(ActionEvent event) {
        prodObsList.remove((!listView.getSelectionModel().isEmpty()) ? listView.getSelectionModel().getSelectedIndex() : prodObsList.size()-1);
        listView.getSelectionModel().clearSelection();
        itemTotal.setText(String.valueOf(0.00));
        itemPreu.setText(String.valueOf(0.00));
        itemCant.setText(String.valueOf(0));
        itemDesc.setText("--");
    }
}