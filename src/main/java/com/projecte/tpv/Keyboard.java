package com.projecte.tpv;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class Keyboard {
    Button b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, bMenys, bBorrar, bMult, bComa, bSuma1;
    static String eq = "";


    public Keyboard(Button b0, Button b1, Button b2, Button b3, Button b4, Button b5, Button b6, Button b7, Button b8, Button b9, Button bMenys, Button bBorrar, Button bMult, Button bComa, Button bSuma1) {
        this.b0 = b0;
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        this.b4 = b4;
        this.b5 = b5;
        this.b6 = b6;
        this.b7 = b7;
        this.b8 = b8;
        this.b9 = b9;
        this.bMenys = bMenys;
        this.bBorrar = bBorrar;
        this.bMult = bMult;
        this.bComa = bComa;
        this.bSuma1 = bSuma1;
    }

    public int clic(){
        b0.setOnMouseClicked(v -> eq = eq + b0.getText());
        b1.setOnMouseClicked(v -> eq = eq + b1.getText());
        b2.setOnMouseClicked(v -> eq = eq + b2.getText());
        b3.setOnMouseClicked(v -> eq = eq + b3.getText());
        b4.setOnMouseClicked(v -> eq = eq + b4.getText());
        b5.setOnMouseClicked(v -> eq = eq + b5.getText());
        b6.setOnMouseClicked(v -> eq = eq + b6.getText());
        b7.setOnMouseClicked(v -> eq = eq + b7.getText());
        b8.setOnMouseClicked(v -> eq = eq + b8.getText());
        b9.setOnMouseClicked(v -> eq = eq + b9.getText());
        /*bMenys.setOnMouseClicked(v -> {
            System.out.println(eq);
            eq = "";
        });*/
        return Integer.parseInt(eq);
    }
}
