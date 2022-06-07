package com.projecte.tpv;

import com.projecte.tpv.model.Pdv;
import com.projecte.tpv.model.Rol;
import com.projecte.tpv.model.Treballador;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.projecte.tpv.DatabaseSql.gueryGeneric;

public class Administracio {
    public List<Treballador> listTreballador() throws SQLException {
        List<Treballador> lt = new ArrayList<>();
        ResultSet rs = gueryGeneric("treballador");
        while (rs.next()){
            lt.add(new Treballador(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6), rs.getInt(7), rs.getString(8), rs.getString(9)));
        }
        return lt;
    }

    public List<Pdv> listPdv() throws SQLException {
        List<Pdv> lp = new ArrayList<>();
        ResultSet rs = gueryGeneric("pdv");
        while (rs.next()){
            lp.add(new Pdv(rs.getString(1), rs.getString(2), rs.getInt(3)));
        }
        return lp;
    }

}
