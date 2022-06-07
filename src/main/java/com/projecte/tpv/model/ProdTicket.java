package com.projecte.tpv.model;

public class ProdTicket extends Producte{
    public int num = 1;

    public ProdTicket(int id_prod, String nom, double preu_venda, String img, int num) {
        super(id_prod, nom, preu_venda, img);
        this.num = num;
    }

    public ProdTicket(int id_prod, String nom, double preu_venda, String img) {
        super(id_prod, nom, preu_venda, img);
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
