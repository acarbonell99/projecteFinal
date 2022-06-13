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
import javafx.scene.Scene;
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

import static com.projecte.tpv.DatabaseMongo.*;
import static com.projecte.tpv.DatabaseSql.queryProdXCat;
import static com.projecte.tpv.Generals.*;
import static com.projecte.tpv.Keyboard.*;

/**
 * Controla totes les accions de la finestra de Venda
 * @author Aida Carbonell Niubo
 */
public class VendaController implements Initializable {
    public static ObservableList<Producte> prodObsList = FXCollections.observableArrayList();
    public static ObservableList<Producte> obsTemp = FXCollections.observableArrayList();
    public ListView<Producte> listView;
    public GridPane gridProduct;
    public Label itemCant;
    public Label itemPreu;
    public Label itemDesc;
    public Label itemTotal;
    public Button b1;
    public Button b2;
    public Button b0;
    public Button b3;
    public Button b4;
    public Button b7;
    public Button b6;
    public Button b8;
    public Button b5;
    public Button b9;
    public Button bC;
    public Button bSumSub;
    private boolean aparcat = false;
    static int num = 1;
    public Button btnAparcar;
    public VBox menuLateral;

    /**
     * Veure: {@link #menuLateral()}
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    /**
     * Torna a la pantalla d'Inici<br/>
     * Veure: {@link Generals#openWindow(String, String, boolean, Scene)}
     * @param event
     */
    public void backInici(ActionEvent event) {
        openWindow("inici.fxml", "Inici", true, btnAparcar.getScene());
    }

    /**
     * Navega a la finestra de Pagar<br/>
     * Veure: {@link Generals#openWindow(String, String)}
     * @param event
     */
    public void toPay(ActionEvent event) {
        openWindow("pagar.fxml", "Pagar");
    }

    /**
     * Reinicia la llsita que conte els Prductes
     * @param event
     */
    public void nouClient(ActionEvent event) {
        prodObsList.clear();
    }

    /**
     * Guarda els Productes del tiquet en un llista temporal ineteja la llista pricipal per poder iniciar una venda nova
     * Si ja hi ha una llsiat guardada la deixa anar i col·loca els seus elements en la llista principal sobreescribint els Productes previs
     * @param event
     */
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

    /**
     * Finalitza la venda i recull els tiquets generats durant aquesta venda  en un sol document
     * <br/>
     * Tanca la Finestra de Venda i obre la d'Inici<br/>
     * Venda queda bloquejat fins a inicialitzar una altra vegada la TPV i Postvenda queda desbloquejat<br/>
     * Veure: {@link DatabaseMongo#stockGastat()} <br/>
     * Veure: {@link DatabaseMongo#operacionZ()} <br/>
     * Veure: {@link Generals#openWindow(String, String, boolean, Scene)} <br/>
     *
     * @param event
     */
    public void opZ(ActionEvent event) {
        dateFinal = dtf.format(LocalDateTime.now());
        stockGastat();
        finalitzar = true;
        inicialitzar = false;
        openWindow("inici.fxml", "Inici", true, btnAparcar.getScene());
        operacionZ();

    }

    /**
     * Crea automaticament el menu lateral utilitzant les categories de la base de dades<br/>
     * Veure: {@link Generals#selCat()}<br/>
     * Veure: {@link #gridCentral(String)}
     */
    public void menuLateral(){
        List<Categoria> cat = selCat();
        Button btAll = new Button("Tot");
        btAll.setId("0");
        btAll.setPrefSize(128, 100);
        btAll.getStyleClass().setAll("btn","btn-primary", "btn1");
        btAll.setAlignment(Pos.CENTER);
        menuLateral.getChildren().add(btAll);
        menuLateral.setMargin(btAll, new Insets(4, 0, 4, -8));
        cat.forEach(c -> {
            Button bt = new Button(c.nom);
            bt.setId(c.id_cat);
            bt.setPrefSize(128, 100);
            bt.getStyleClass().setAll("btn","btn-info", "btn1");
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

    /**
     * Omple el grid ccentral amb tots els productes de la bse de dades <br/>
     * Per defecte es mostren tots els productes, en sel·leccionar una categoria nomes es mostren els productes d'aquella categoria<br/>
     * En clicar un Producte s'afegeix al tiquet<br/>
     * Veure: {@link #addTicket(int, String, double, ObservableList)}<br/>
     * Veure: {@link Keyboard#clic()}<br/>
     * Veure: {@link DatabaseSql#queryProdXCat(String)}
     * @param s
     * @throws SQLException
     */
    public void gridCentral(String s) throws SQLException {
        Keyboard k = new Keyboard(b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, bSumSub);
        gridProduct.getChildren().clear();
        List<Producte> prd = queryProdXCat(s);
        int c = 0, r = 0;
        for (Producte producte : prd) {
            VBox box = new VBox();
            box.setId("prodBox");
            box.getStyleClass().setAll("btn", "btn-default", "btn2", "prodcutsBox");
            box.setPrefWidth(100);
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
            box.setOnMouseClicked(v -> {
                num = (!k.clic().isEmpty()) ? Integer.parseInt(k.clic()) : 1;
                addTicket(producte.getId_prod(), producte.getNom(), producte.getPreu_venda(), prodObsList);
                eq = "";
            });
        }
    }

    /**
     * Comprova si ja existeix el Producte a la llista del tiquet<br/>
     * Si existeix, augmenta la quantitat a la llista<br/>
     * Si no existeix, afegeix le Producte a la llista<br/>
     * @param id id del Producte
     * @param nom nom del Producte
     * @param preu preu del Producte
     * @param obl llista on s'afegeix el Producte
     */
    public static void addTicket(int id, String nom, double preu, ObservableList<Producte> obl){
        int a = 0;
        if (obl.isEmpty()) obl.add(new Producte(id, nom, num, preu));
        else {
            for (int i = 0; i < obl.size(); i++) if (obl.get(i).getId_prod() == id) a = i;
            if (obl.get(a).getId_prod() == id) obl.set(a, new Producte(id, nom, obl.get(a).cant + num, preu));
            else obl.add(new Producte(id, nom, num, preu));
        }
    }

    /**
     * Si no hi ha cap Producte del tiquet seleccionat, borra l'ultim<br/>
     * Si hi ha algun Producte sel·leccionat, l'esborra (cant = 1) o resta 1 a cant (cant > 1)
     * @param event
     */
    public void borrarUltim(ActionEvent event) {
        Producte p  = listView.getSelectionModel().getSelectedItem();
        if (p != null){
            if (p.getCant() > 1) {
                p.setCant(p.getCant() - 1);
                prodObsList.set(listView.getSelectionModel().getSelectedIndex(), p);
            }
            else{
                prodObsList.remove((!listView.getSelectionModel().isEmpty()) ? listView.getSelectionModel().getSelectedIndex() : prodObsList.size()-1);
                listView.getSelectionModel().clearSelection();
            }
        } else {
            prodObsList.remove((!listView.getSelectionModel().isEmpty()) ? listView.getSelectionModel().getSelectedIndex() : prodObsList.size()-1);
            listView.getSelectionModel().clearSelection();
            itemTotal.setText(String.valueOf(0.00));
            itemPreu.setText(String.valueOf(0.00));
            itemCant.setText(String.valueOf(0));
            itemDesc.setText("--");
        }
    }
}