package com.projecte.tpv;
import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.Filters;
import com.projecte.tpv.model.Producte;
import javafx.collections.ObservableList;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import static com.mongodb.client.model.Aggregates.limit;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static com.projecte.tpv.Generals.*;
import static com.projecte.tpv.PagarController.data;

public class DatabaseMongo {
    final static String uri = "mongodb://localhost";
    public static MongoDatabase database;
    public static MongoCollection<Document> collection;

    public void connect() throws CannotConnectException {
        /*String uri = "mongodb://localhost";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            database = mongoClient.getDatabase("projecteTpv");
        }*/
        try{
            MongoClient mongoClient = MongoClients.create(uri);
            database = mongoClient.getDatabase("projecteTpv");
        } catch (Exception e) {
            throw new CannotConnectException();
        }


    }

    public void omplir(){
        List<Document> tr = new ArrayList<>();
        tr.add(new Document().append("dni", "22934885K").append("id_rol", "r00004"));
        tr.add(new Document().append("dni", "95631282L").append("id_rol", "r00005"));
        tr.add(new Document().append("dni", "74771782R").append("id_rol", "r00005"));
        List<String> tpv = new ArrayList<>();
        tpv.add("b0001");
        tpv.add("b0002");
        tpv.add("b0003");
        collection = database.getCollection("pdv");
        Document doc = new Document();
        doc.append("pdv", "b00002");
        doc.append("numTpv", 10);
        doc.append("personal", tr);
        doc.append("tpv", tpv);
        collection.insertOne(doc);
    }

    public void crearTicket(ObservableList<Producte> obs, double total){
        List<Document> pr = new ArrayList<>();
        for(Producte p: obs){
            pr.add(new Document().append("id_prod", p.getId_prod()).append("nom", p.getNom()).append("cant", p.getCant()).append("preu", p.getPreu_venda()));
        }
        collection = database.getCollection("ticket");
        Document doc = new Document();
        doc.append("id_ticket", idticket);
        doc.append("num_ticket", numTicket);
        doc.append("productes", pr);
        doc.append("IVA", total/10);
        doc.append("total-iva", total-(total/10));
        doc.append("total", total);
        doc.append("data", dtf.format(data));
        doc.append("treballdor", treballador);
        collection.insertOne(doc);
    }

    public static void operacionZ(){
        numZ = nextZ();
        collection = database.getCollection("ticket");
        List<Document> docs = new ArrayList<>();
        Iterator it = collection.find().iterator();
        double r = 0;
        while (it.hasNext()){
            Document d = (Document) it.next();
            docs.add(d);
            r = r + Double.parseDouble(d.getString("total").replace(",", "."));
        }
        collection.drop();
        collection = database.getCollection("recopilacio");
        collection.insertOne(new Document()
                .append("numZ", numZ)
                .append("data_inici", dateInicial)
                .append("data_final", dateFinal)
                .append("recaptacioZ", df.format(r))
                .append("tickets", docs));
    }

    public static int nextZ(){
        collection = database.getCollection("recopilacio");
        if (collection.find().sort(descending("numZ")).first() == null) return 1;
        return collection.find().sort(descending("numZ")).first().getInteger("numZ")+1;
    }

    public static int nextIdTicket(){
        collection = database.getCollection("ticket");
        if (collection.find().sort(descending("id_ticket")).first() == null) return 1;
        return collection.find().sort(descending("id_ticket")).first().getInteger("id_ticket")+1;
    }

    public static void nextIdTicket2(){
        System.out.println(idticket);
        collection = database.getCollection("recopilacio");
        if (collection.find().sort(descending("numZ")).first() == null) idticket = nextIdTicket();
        else {
            ArrayList<Document> doc = (ArrayList<Document>) collection.find().sort(descending("numZ")).first().get("tickets");
            doc.forEach(d -> idticket = Math.max(idticket, d.getInteger("id_ticket")));
        }
    }
    /*public static void prova(){
        collection = database.getCollection("recopilacio");
        ArrayList<Document> doc = (ArrayList<Document>) collection.find().sort(descending("numZ")).first().get("tickets");
        doc.forEach(d -> System.out.println(d.getInteger("id_ticket") + " -- " + d));
        int a = 0;
        for (Document d : doc){
            System.out.println(a + " -- " + d.getInteger("id_ticket"));
            a = Math.max(a, d.getInteger("id_ticket"));
        }

    }*/
    static ArrayList<Producte> prdStock = new ArrayList<>();
    public static void stockGastat(){
        ArrayList<Document> d = new ArrayList<>();
        ArrayList<Document> d2 = new ArrayList<>();
        ArrayList<Producte> p = new ArrayList<>();

        collection = database.getCollection("ticket");
        collection.find(Filters.empty())
                .projection(fields(include("productes.id_prod", "productes.cant"), exclude("_id")))
                .forEach(d::add);
        d.forEach(f -> d2.addAll((Collection<? extends Document>) f.get("productes")));
        d2.forEach(l -> p.add(new Producte(l.getInteger("id_prod"), l.getInteger("cant"))));
        p.forEach(r -> {
            if (!prdStock.contains(r)) prdStock.add(r);
            else prdStock.forEach(q -> {if (q.getId_prod() == r.getId_prod()) q.setCant(q.getCant() + r.getCant());});
        });
        prdStock.forEach(System.out::println);
        prdStock.forEach(q -> comandaProductes(q.getId_prod(), q.getCant()));
    }

    public static void comandaProductes(int id, int cant){
        collection = database.getCollection("comanda");
        Document doc = new Document();
        doc.append("id_comanda", 1);
        doc.append("data", dtf.format(LocalDateTime.now()));
        doc.append("id_prod", id);
        doc.append("quantitat", cant);
        collection.insertOne(doc);

    }

}
