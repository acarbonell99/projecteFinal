package com.projecte.tpv.model;

import org.w3c.dom.Document;

public class Pdv {
    String id_pdv;
    String nom;
    int num_tpv;

    public Pdv(String id_pdv, String nom, int num_tpv) {
        this.id_pdv = id_pdv;
        this.nom = nom;
        this.num_tpv = num_tpv;
    }

    /*public static Pdv personalPdv(String title) {
        return new Document()
    }*/
}
