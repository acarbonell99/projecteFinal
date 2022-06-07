package com.projecte.tpv;

import com.projecte.tpv.model.Categoria;
import com.projecte.tpv.model.Producte;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.projecte.tpv.DatabaseMongo.nextZ;
import static com.projecte.tpv.DatabaseSql.gueryGeneric;

public class Generals {
    static boolean inicialitzar = false;
    static boolean finalitzar = false;
    static final String pdvCode = "b00001";
    static final String tpvCode = "t00001";
    static int idticket = 1;
    static int numTicket = 1;
    static final String treballador = "22934885K";
    static String dateInicial = "";
    static String dateFinal = "";
    static int numZ = 0;

    static DecimalFormat df = new DecimalFormat("#0.00");
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static void openWindow(String field, String title, boolean t, Scene scene){
        try {
            Parent root2 = new FXMLLoader(HelloController.class.getResource(field)).load();
            scene.setRoot(root2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void openWindow(String field, String title){
        try {
            Stage st = new Stage();
            Scene scene = new Scene(new FXMLLoader(HelloController.class.getResource(field)).load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            st.setScene(scene);
            st.setTitle(title);
            if (title.equals("Venda")) st.setMaximized(true);
            st.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeWindow(Button btn){
        Stage st = (Stage) btn.getScene().getWindow();
        st.close();
    }

    public static List<Categoria> selCat(){
        List<Categoria> cat = new ArrayList<>();
        try {
            ResultSet rs = gueryGeneric("categoria");
            while (rs.next()){
                cat.add(new Categoria(rs.getString(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cat;
    }

    public static List<Producte> selAllProd(){
        List<Producte> prd = new ArrayList<>();
        try {
            ResultSet rs = gueryGeneric("producte");
            while (rs.next()){
                prd.add(new Producte(rs.getInt(1), rs.getString(2), rs.getDouble(7), rs.getString(10)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prd;
    }
}
