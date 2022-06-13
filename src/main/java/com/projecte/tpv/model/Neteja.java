package com.projecte.tpv.model;

/**
 * Representa a les tasques de neteja
 * @author Aida Carbonell Niubo
 */
public class Neteja {
    String tasca;
    boolean fet = false;

    public Neteja(String tasca) {
        this.tasca = tasca;
    }

    public String getTasca() {
        return tasca;
    }
    public void setTasca(String tasca) {
        this.tasca = tasca;
    }

    public boolean isFet() {
        return fet;
    }
    public void setFet(boolean fet) {
        this.fet = fet;
    }
}