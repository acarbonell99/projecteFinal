package com.projecte.tpv;

import com.projecte.tpv.model.Producte;
import com.projecte.tpv.model.Treballador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSql {
    final static String uri = "jdbc:mysql://localhost/tpv2?user=myuser&password=mypass";
    private static Connection conn;

    public void connect() throws CannotConnectException {
        try {
            conn = DriverManager.getConnection(uri);
        } catch (Exception e) {
            throw new CannotConnectException();
        }
    }

    /**
     * MEtode generic que a fer ocnsultes a la base de dades SQL
     * @param t nom de la taula a consultar
     * @return  ResultSet All result to table s
     * @throws SQLException
     */
    public static ResultSet gueryGeneric(String t) throws SQLException {
        ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM " + t);
        return resultSet;
    }

    /**
     * @return llista de treballadors
     * @throws SQLException
     * @deprecated no s'utilitza
     */
    public List<Treballador> queryTreballadors() throws SQLException {
        List<Treballador> lt = new ArrayList<>();
        ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM treballador;");
        while (rs.next()){
            lt.add(new Treballador(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6), rs.getInt(7), rs.getString(8), rs.getString(9)));
        }
        return lt;
    }

    /**
     *
     * @param s nom de la categoria
     * @return id de l'ultim producte d'una categoria pasada per parametre
     * @throws SQLException
     */
    public static int queryWhereCat(String s) throws SQLException {
        ResultSet resultSet = conn.createStatement().executeQuery("SELECT id_prod FROM producte As pr JOIN categoria AS cat ON pr.id_cat = cat.id_cat where cat.nom = '" + s + "' ORDER BY id_prod DESC LIMIT 1;");
        while (resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }

    /**
     * @param w id_cat
     * @return ResultSet where category equals to w
     * @throws SQLException
     */
   public static ResultSet queryProdXCat1(String w) throws SQLException {
       ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM producte WHERE id_cat = '" + w + "';");
       return resultSet;
   }

    /**
     * @param w id_cat
     * @return Llista de tots els producets que el seu id_cat es igual a w
     * @throws SQLException
     */
   public static List<Producte> queryProdXCat(String w) throws SQLException {
       List<Producte> prd = new ArrayList<>();
       ResultSet rs = null;
       try {
           if (w.equals("null")) rs = gueryGeneric("producte");
           else rs = conn.createStatement().executeQuery("SELECT * FROM producte WHERE id_cat = '" + w + "';");
           while (rs.next()){
               if (rs.getBoolean(12) == true){
                   prd.add(new Producte(rs.getInt(1), rs.getString(2), rs.getDouble(7), rs.getString(10)));
               }
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return prd;
   }

    /**
     * @param w id producte
     * @return Selecciona el Producte on id_prod es igual a w
     * @throws SQLException
     */
   public static Producte selProd(int w) throws SQLException {
       ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM producte WHERE id_prod = " + w + ";" );
       while (rs.next()){
           return new Producte(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getDouble(7), rs.getDouble(8), rs.getInt(9), rs.getString(10), rs.getBoolean(11), rs.getBoolean(11));
       }
       return null;
   }

    /**
     * @param w nom de la categoria
     * @return  id_cat igual a w (nom)
     * @throws SQLException
     */
   public static String queryIdCat(String w) throws SQLException {
       ResultSet resultSet = conn.createStatement().executeQuery("SELECT id_cat FROM categoria WHERE nom = '" + w + "';" );
       while (resultSet.next()){
           return  resultSet.getString(1);
       }
       return null;
   }

    /**
     * @param w id de categoria
     * @return  name igual a w (id_cat)
     * @throws SQLException
     */
   public static String queryNomCat(String w) throws SQLException {
       ResultSet resultSet = conn.createStatement().executeQuery("SELECT nom FROM categoria WHERE id_cat = '" + w + "';" );
       while (resultSet.next()){
           return  resultSet.getString(1);
       }
       return null;
   }


    /**
     * @param p Producte a insertar a la abse de dades
     * @throws SQLException
     */
   public static void insertProd(Producte p) throws SQLException {
       PreparedStatement statement = conn.prepareStatement("INSERT INTO producte(id_prod, nom, descripcio, cant, stock, id_cat, preu_venda, preu_compra, iva, img, consumible, vendible) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
       statement.setInt(1, p.getId_prod());
       statement.setString(2, p.getNom());
       statement.setString(3, p.getDescripcio());
       statement.setInt(4, p.getCant());
       statement.setInt(5, p.getStockMin());
       statement.setString(6, p.getId_cat());
       statement.setDouble(7, p.getPreu_venda());
       statement.setDouble(8, p.getPreu_compra());
       statement.setInt(9, p.getIva());
       statement.setString(10, p.getImg());
       statement.setBoolean(11, p.isConsumible());
       statement.setBoolean(12, p.isVendible());
       statement.executeUpdate();
   }

    /**
     * Actualitzar la informació d'un producte
     * @param p Producte a actualitar a la base de dades
     * @throws SQLException
     * @see FitxaController
     */
    public static void updateProd(Producte p) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("UPDATE producte SET nom = ?, descripcio = ?, cant = ?, stock = ?, id_cat = ?, preu_venda = ?, preu_compra = ?, iva = ?, img = ?, consumible = ?, vendible = ? WHERE id_prod = ?;");
        statement.setInt(12, p.getId_prod());
        statement.setString(1, p.getNom());
        statement.setString(2, p.getDescripcio());
        statement.setInt(3, p.getCant());
        statement.setInt(4, p.getStockMin());
        statement.setString(5, p.getId_cat());
        statement.setDouble(6, p.getPreu_venda());
        statement.setDouble(7, p.getPreu_compra());
        statement.setInt(8, p.getIva());
        statement.setString(9, p.getImg());
        statement.setBoolean(10, p.isConsumible());
        statement.setBoolean(11, p.isVendible());
        statement.executeUpdate();
    }

    /**
     * Actualitzar la categoria d'un producte
     * @param s id de producte
     * @param t id de categoria
     * @throws SQLException
     */
    public static void updateItem(int s, String t) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("UPDATE producte SET id_cat = ? WHERE id_prod = ?;");
        statement.setString(1, t);
        statement.setInt(2, s);
        statement.executeUpdate();
    }

    /**
     * Actualitzar la quantitat de productes que tenim
     * @param s id de producte
     * @param t quantitat a actualitzar
     * @throws SQLException
     */
    public static void updateCant(int s, int t) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("UPDATE producte SET cant = ? WHERE id_prod = ?;");
        statement.setInt(1, t);
        statement.setInt(2, s);
        statement.executeUpdate();
    }

    /**
     * Eliminar un producte
     * @param id id de producte a eliminar
     * @throws SQLException
     */
    public void deleteProd(int id) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("DELETE FROM producte WHERE id_prod = " + id + ";");
        statement.executeUpdate();
    }

    /**
     * Omplir la base de dades inicial
     * @throws SQLException
     */
    public void omplirDB() throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO empresa VALUES\n" +
                "\t('Grupo Serna', 'D12345678', 'SL', '+34', 943665514, 'www.gruposerna.com', 'Comandante Izarduy 46', 'Sant Joan Despí', 'Barcelona', 08970);");
        statement.executeUpdate();

        statement = conn.prepareStatement("INSERT INTO rol VALUES\n" +
                "\t('r00001', 'jefe', 9539),\n" +
                "\t('r00002', 'administratiu', 2649),\n" +
                "\t('r00003', 'supervisor', 4963),\n" +
                "\t('r00004', 'encarregat', 7410),\n" +
                "\t('r00005', 'personal barra', 8520);");
        statement.executeUpdate();

        statement = conn.prepareStatement("INSERT INTO treballador VALUES \n" +
                "\t('Daniela', 'Rocha', 'Martinez', '95631282L', '1989-12-09', '2020-01-01', 698720414, 'r00005', 'img'),\n" +
                "\t('Ivan', 'Gimeno', 'Griego', '74771782R', '1993-08-27', '2020-01-01', 656801147, 'r00005', 'img'),\n" +
                "\t('Marta', 'Gaitán', 'Cedillo', '99133788Q', '1978-12-06', '2018-01-01', 695542020, 'r00005', 'img'),\n" +
                "\t('Ismael', 'Marco', 'Coronado', '81479799E', '1999-07-18', '2018-01-01',  603322005, 'r00005', 'img'),\n" +
                "\t('Rodrigo', 'Córdova', 'Segundo', '23452424Z', '1992-06-21', '2018-01-01',  671813318, 'r00005', 'img'),\n" +
                "\t('Isaac', 'Padilla', 'Tercero', '21776967S', '1989-02-14', '2018-01-01',  659077333, 'r00004', 'img'),\n" +
                "\t('Carlota', 'Montalvo', 'Rojas', '14712073P', '1996-06-21', '2018-01-01',  694930687, 'r00004', 'img'),\n" +
                "\t('Celia', 'Garza', 'Rojas', '22934885K', '1989-08-30', '2018-01-01',  664563263, 'r00004', 'img');");
        statement.executeUpdate();

        statement = conn.prepareStatement("INSERT INTO pdv VALUES\n" +
                "\t('b00001', 'Frankfurt', 3),\n" +
                "\t('b00002', 'Grill', 2),\n" +
                "\t('b00003', 'Frankfurt', 1);");
        statement.executeUpdate();

        statement = conn.prepareStatement("INSERT INTO tpv VALUES\n" +
                "\t('t00001', 'b00001'),\n" +
                "\t('t00002', 'b00001'),\n" +
                "\t('t00003', 'b00001'),\n" +
                "\t('t00001', 'b00002'),\n" +
                "\t('t00002', 'b00002'),\n" +
                "\t('t00001', 'b00003');");
        statement.executeUpdate();

        statement = conn.prepareStatement("INSERT INTO categoria VALUES\n" +
                "\t('c00001', 'beguda'),\n" +
                "\t('c00002', 'cafe'),\n" +
                "\t('c00003', 'snack'),\n" +
                "\t('c00004', 'entrepa'),\n" +
                "\t('c00005', 'neteja'),\n" +
                "\t('c00006', 'desechables'),\n" +
                "\t('c00007', 'varis');");
        statement.executeUpdate();

        statement = conn.prepareStatement("INSERT INTO producte VALUES\n" +
                "\t(10001, 'Aigua 500ml', 'Aigua mitjana 500ml', 200, 100, 'c00001', 2.40, 0.50, 10, 'img', true, true),\n" +
                "\t(10002, 'Aigua 5l', 'Garraga igua 5l', 5, 1, 'c00001', null, 0.70, null, 'img', true, false),\n" +
                "\t(20001, 'Cafe', 'Cafe sol', 100, 50, 'c00002', 0.85, 0.1, 10, 'img', true, true),\n" +
                "\t(20002, 'Cafe amb llet', 'Cafe amb llet', 100, 50, 'c00002', 1.2, 0.20, 10, 'img', true, true),\n" +
                "\t(20003, 'Tallat', 'Tallat', 100, 50, 'c00002', 1, 0.5, 10, 'img', true, true),\n" +
                "\t(30001, 'Oreo', 'Oreo pack6', 50, 20, 'c00003', 2.2, 0.9, 10, 'img', true, true),\n" +
                "\t(30002, 'Kitkat', 'Kitkat', 50, 20, 'c00003', 2.1, 0.8, 10, 'img', true, true),\n" +
                "\t(40001, 'Entrepa Formatge', 'Entrepa Formatge', 15, 5, 'c00004', 5.5, 1, 10, 'img', true, true),\n" +
                "\t(40002, 'Entrepa Iberic', 'Entrepa Iberic', 15, 5, 'c00004', 8, 1.2, 10, 'img', true, true),\n" +
                "\t(50001, 'Escombra', 'Escombra', 1, 1, 'c00005', null, 0.5, null, 'img', false, false),\n" +
                "\t(50002, 'Fregona', 'Fregona', 1, 1, 'c00005', null, 0.5, null, 'img', false, false),\n" +
                "\t(50003, 'Bossa basura', 'Bossa basura negra 10u', 10, 2, 'c00005', null, 0.2, null, 'img', true, false),\n" +
                "\t(60001, 'Got', 'Got PP 500ml transparent', 200, 50, 'c00006', null, 0.01, null, 'img', true, false),\n" +
                "\t(60002, 'Tovallons', 'Tovallons 50x50', 200, 50, 'c00006', null, 0.01, null, 'img', true, false),\n" +
                "\t(70001, 'Ketchup', 'Sobres ketchup', 500, 100, 'c00007', null, 0.01, null, 'img', true, false),\n" +
                "\t(70002, 'Mayonesa', 'Sobres mayonesa', 500, 100, 'c00007', null, 0.01, null, 'img', true, false),\n" +
                "\t(70003, 'Mostassa', 'Sobres mostassa', 500, 100, 'c00007', null, 0.01, null, 'img', true, false);");
        statement.executeUpdate();
    }
}
