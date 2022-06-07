package com.projecte.tpv.model;

import javafx.scene.control.CheckBox;

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
