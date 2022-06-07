package com.projecte.tpv.model;

import java.util.Date;

public class Treballador {
    String nom;
    String cognom1;
    String cognom2;
    String dni;
    Date data_naixement;
    Date data_alta;
    int telefon;
    String id_rol;
    String img;

    public Treballador(String nom, String cognom1, String cognom2, String dni, Date data_naixement, Date data_alta, int telefon, String id_rol, String img) {
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.dni = dni;
        this.data_naixement = data_naixement;
        this.data_alta = data_alta;
        this.telefon = telefon;
        this.id_rol = id_rol;
        this.img = img;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCognom1() {
        return cognom1;
    }

    public void setCognom1(String cognom1) {
        this.cognom1 = cognom1;
    }

    public String getCognom2() {
        return cognom2;
    }

    public void setCognom2(String cognom2) {
        this.cognom2 = cognom2;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getData_naixement() {
        return data_naixement;
    }

    public void setData_naixement(Date data_naixement) {
        this.data_naixement = data_naixement;
    }

    public Date getData_alta() {
        return data_alta;
    }

    public void setData_alta(Date data_alta) {
        this.data_alta = data_alta;
    }

    public int getTelefon() {
        return telefon;
    }

    public void setTelefon(int telefon) {
        this.telefon = telefon;
    }

    public String getId_rol() {
        return id_rol;
    }

    public void setId_rol(String id_rol) {
        this.id_rol = id_rol;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Treballador{" +
                "nom='" + nom + '\'' +
                ", cognom1='" + cognom1 + '\'' +
                ", cognom2='" + cognom2 + '\'' +
                ", dni='" + dni + '\'' +
                ", data_naixement=" + data_naixement +
                ", data_alta=" + data_alta +
                ", telefon=" + telefon +
                ", id_rol='" + id_rol + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
