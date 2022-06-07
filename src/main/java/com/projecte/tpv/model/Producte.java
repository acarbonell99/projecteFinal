package com.projecte.tpv.model;

import java.lang.String;
import java.sql.SQLException;
import java.util.Objects;

import javafx.scene.control.TextField;

import static com.projecte.tpv.DatabaseSql.queryWhereCat;

public class Producte {
    int id_prod;
    String nom;
    String descripcio;
    public int cant;
    int stockMin;
    String id_cat;
    double preu_venda;
    double preu_compra;
    int iva;
    String img;
    boolean consumible;
    boolean vendible;
    boolean comanda;
    boolean estat;
    String stockReal;


    public Producte(int id_prod, String nom, String descripcio, int cant, int stockMin, String id_cat, double preu_venda, double preu_compra, int iva, String img, boolean consumible, boolean vendible) {
        this.id_prod = id_prod;
        this.nom = nom;
        this.descripcio = descripcio;
        this.cant = cant;
        this.stockMin = stockMin;
        this.id_cat = id_cat;
        this.preu_venda = preu_venda;
        this.preu_compra = preu_compra;
        this.iva = iva;
        this.img = img;
        this.consumible = consumible;
        this.vendible = vendible;
    }

    public Producte(int id_prod, String nom, String descripcio, String id_cat, double preu_venda, boolean consumible, boolean vendible) {
        this.id_prod = id_prod;
        this.nom = nom;
        this.descripcio = descripcio;
        this.id_cat = id_cat;
        this.preu_venda = preu_venda;
        this.consumible = consumible;
        this.vendible = vendible;
    }

    public Producte(int id_prod, String nom, double preu_venda, String img) {
        this.id_prod = id_prod;
        this.nom = nom;
        this.preu_venda = preu_venda;
        this.img = img;
    }

    public Producte(int id_prod, String nom, int cant, double preu_venda) {
        this.id_prod = id_prod;
        this.nom = nom;
        this.cant = cant;
        this.preu_venda = preu_venda;
    }

    public Producte(int id_prod, String nom, String descripcio, int cant, int stockMin, String id_cat) {
        this.id_prod = id_prod;
        this.nom = nom;
        this.descripcio = descripcio;
        this.cant = cant;
        this.stockMin = stockMin;
        this.id_cat = id_cat;
        comanda = false;
        stockReal = "";

        if (cant < stockMin) estat = true;
        else estat = false;
    }

    public Producte(int id_prod, int cant) {
        this.id_prod = id_prod;
        this.cant = cant;
    }

    public int getId_prod() {
        return id_prod;
    }

    public void setId_prod(int id_prod) {
        this.id_prod = id_prod;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public String getId_cat() {
        return id_cat;
    }

    public void setId_cat(String id_cat) {
        this.id_cat = id_cat;
    }

    public double getPreu_venda() {
        return preu_venda;
    }

    public void setPreu_venda(double preu_venda) {
        this.preu_venda = preu_venda;
    }

    public double getPreu_compra() {
        return preu_compra;
    }

    public void setPreu_compra(double preu_compra) {
        this.preu_compra = preu_compra;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isConsumible() {
        return consumible;
    }

    public void setConsumible(boolean consumible) {
        this.consumible = consumible;
    }

    public boolean isVendible() {
        return vendible;
    }

    public void setVendible(boolean vendible) {
        this.vendible = vendible;
    }

    public int getStockMin() {
        return stockMin;
    }

    public void setStockMin(int stockMin) {
        this.stockMin = stockMin;
    }

    public static int nextId(String cat) throws SQLException {
        return queryWhereCat(cat)+1;
    }

    public boolean isComanda() {
        return comanda;
    }

    public void setComanda(boolean comanda) {
        this.comanda = comanda;
    }

    public boolean isEstat() {
        return estat;
    }

    public void setEstat(boolean estat) {
        this.estat = estat;
    }

    public String getStockReal() {
        return stockReal;
    }

    public void setStockReal(String stockReal) {
        this.stockReal = stockReal;
    }

    @Override
    public String toString() {
        return "Producte{" +
                "id_prod=" + id_prod +
                ", nom='" + nom + '\'' +
                ", descripcio='" + descripcio + '\'' +
                ", cant=" + cant +
                ", stockMin=" + stockMin +
                ", id_cat='" + id_cat + '\'' +
                ", preu_venda=" + preu_venda +
                ", preu_compra=" + preu_compra +
                ", iva=" + iva +
                ", img='" + img + '\'' +
                ", consumible=" + consumible +
                ", vendible=" + vendible +
                ", comanda=" + comanda +
                ", estat=" + estat +
                ", stockReal='" + stockReal + '\'' +
                '}';
    }

    public String toString2() {
        return "Producte{" +
                "id_prod=" + id_prod +
                ", cant=" + cant +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producte producte = (Producte) o;
        return id_prod == producte.id_prod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_prod);
    }
}
