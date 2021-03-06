package com.projecte.tpv;

import com.projecte.tpv.model.Categoria;
import com.projecte.tpv.model.Producte;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import static com.projecte.tpv.DatabaseSql.gueryGeneric;

/**
 * Recull metodes generics que s'utilitzen de manera recurrent en altres classes
 * @author Aida Carbonell Niubbo
 */
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

    /**
     * Obrir una finsetra en un Stage diferent
     * @param field arxiu .fxml
     * @param title nom finestra
     * @param t naximizar finestra
     * @param scene scena
     */
    public static void openWindow(String field, String title, boolean t, Scene scene){
        try {
            Parent root2 = new FXMLLoader(IniciController.class.getResource(field)).load();
            scene.setRoot(root2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Obrir una finestra en el mateix Stage
     * @param field arxiu .fxml
     * @param title nom finestra
     */
    public static void openWindow(String field, String title){
        try {
            Stage st = new Stage();
            Scene scene = new Scene(new FXMLLoader(IniciController.class.getResource(field)).load());
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            st.setScene(scene);
            st.setTitle(title);
            st.getIcons().add(new Image(VendaController.class.getResourceAsStream("img/logo.png")));
            if (title.equals("Venda")) st.setMaximized(true);
            st.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metode generic per tancar  una finestra
     * @param btn serveix per identificar en quina escena s'est??
     */
    public static void closeWindow(Button btn){
        Stage st = (Stage) btn.getScene().getWindow();
        st.close();
    }

    /**
     * Genera una llista de les categories
     * Veure: {@link DatabaseSql#gueryGeneric(String)}
     * @return llista de categories
     */
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

    /**
     * Genera una llista de tots el productes
     * Veure: {@link DatabaseSql#gueryGeneric(String)}
     * @return llsiat de productes
     */
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