package com.projecte.tpv.model;

public class Empresa {
    String nom;
    String cif;
    String forma_juridica;
    String prefix;
    int telefon;
    String web;
    String carrer;
    String ciutat;
    String area;
    int zip_code;

    public Empresa(String nom, String cif, String forma_juridica, String prefix, int telefon, String web, String carrer, String ciutat, String area, int zip_code) {
        this.nom = nom;
        this.cif = cif;
        this.forma_juridica = forma_juridica;
        this.prefix = prefix;
        this.telefon = telefon;
        this.web = web;
        this.carrer = carrer;
        this.ciutat = ciutat;
        this.area = area;
        this.zip_code = zip_code;
    }
}
